package com.sisdanger.maps;

import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.Map;

/**
 * Created by zxy on 2017/12/6.
 */

public class ArcgisMapView extends ViewGroupManager<MapView> {
    private ThemedReactContext _context;

    private MapTouchListener mapTouchListener = null;

    public static final String REACT_CLASS = "RCTArcgisMapView";
    public MapView _MapView;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected MapView createViewInstance(ThemedReactContext reactContext) {
        _context = reactContext;
        _MapView = new MapView(reactContext);
        //TianDiTuTiledMapServiceLayer mapBase = new TianDiTuTiledMapServiceLayer("http://www.scgis.net.cn/iMap/iMapServer/DefaultRest/services/newtianditudlg", "");

        // 构造天地图 服务
        WebTiledLayer webTiledLayer = TianDiTuTiledMapServiceLayer.CreateTianDiTuTiledLayer(TianDiTuTiledMapServiceLayer.LayerType.TIANDITU_VECTOR_2000);
        Basemap tdtBasemap = new Basemap(webTiledLayer);
        WebTiledLayer webTiledLayer1 = TianDiTuTiledMapServiceLayer.CreateTianDiTuTiledLayer(TianDiTuTiledMapServiceLayer.LayerType.TIANDITU_VECTOR_ANNOTATION_CHINESE_2000);
        tdtBasemap.getBaseLayers().add(webTiledLayer1);
        ArcGISMap arcGISMap = new ArcGISMap();
        _MapView.setMap(arcGISMap);
        _MapView.getMap().setBasemap(tdtBasemap);

        mapTouchListener = new MapTouchListener(_MapView, _context);

        _MapView.setOnTouchListener(new DefaultMapViewOnTouchListener(_context, _MapView){
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                mapTouchListener.onSingleTapUp(e);
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                android.graphics.Point screenPoint = new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY()));
                Point clickPoint = _MapView.screenToLocation(screenPoint);
                mapTouchListener.onDoubleTapEvent(e);

                WritableMap map = Arguments.createMap();
                map.putDouble("x", clickPoint.getX());
                map.putDouble("y", clickPoint.getY());
                map.putString("event", "onDoubleTap");
                // "topChange"事件在JS端映射到"onChange"，参考UIManagerModuleConstants.java
                _context.getJSModule(RCTEventEmitter.class).receiveEvent(
                        _MapView.getId(),
                        "topChange",
                        map
                );
                return super.onDoubleTapEvent(e);
            }
        });
        return _MapView;
    }

    @Override
    public void addView(MapView parent, View child, int index) {
        super.addView(parent, child, index);
    }

//    @ReactProp(name = "enableTool")
//    public void setEnableTool(MapView view, @Nullable boolean enableTool) {
//        if (enableTool == true) {
//            ImageButton zoomInBtn = new ImageButton(_context);
//            zoomInBtn.setBackgroundResource(R.drawable.mapfangda);
//
//            RelativeLayout rl = new RelativeLayout(_context);
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////            RelativeLayout relativeLayout = new RelativeLayout(_context);
////
////            relativeLayout.setLayoutParams(params);
////
////            ViewGroup.LayoutParams groupParams = new ViewGroup.LayoutParams(40, 40);
////
////            zoomInBtn.setLayoutParams(groupParams);
////
////            relativeLayout.addView(zoomInBtn);
////            view.setOrientation(LinearLayout.HORIZONTAL);
////            params.leftMargin = 100;
////            zoomInBtn.setLayoutParams(params);
////            _MapView.addView(zoomInBtn);
////            params.addRule(RelativeLayout.CENTER_IN_PARENT, -1);
////            params.setMargins(10, 10, 10, 10);//设置左，上，右，下，的距离
//
//            //zoomInBtn.setLayoutParams(params);
//            rl.addView(zoomInBtn);
//
//
//            _MapView.addView(rl, params);
//        }
//    }

    private static final int COMMAND_ZOOMIN = 1;
    private static final int COMMAND_ZOOMOUT = 2;
    private static final int COMMAND_BIAOXUAN = 3;
    private static final int COMMAND_DINGWEI = 4;
    private static final int COMMAND_CLEAR = 5;
    private static final int COMMAND_LENGTH = 6;
    private static final int COMMAND_AREA = 7;

    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
                "zoomIn", COMMAND_ZOOMIN,
                "zoomOut", COMMAND_ZOOMOUT,
                "biaoXuan", COMMAND_BIAOXUAN,
                "dingWei", COMMAND_DINGWEI,
                "clear", COMMAND_CLEAR,
                "length", COMMAND_LENGTH,
                "area", COMMAND_AREA
        );
    }

    @Override
    public void receiveCommand(final MapView view, int commandId, @Nullable ReadableArray args) {
        switch (commandId) {
            case COMMAND_ZOOMIN: {
                double currZoomScale = view.getMapScale();
                view.setViewpointScaleAsync(currZoomScale * 0.5);
//                view.zoomin(true);
            }
            break;
            case COMMAND_ZOOMOUT: {
                double currZoomScale = view.getMapScale();
                view.setViewpointScaleAsync(currZoomScale * 2);
            }
            break;
            case COMMAND_BIAOXUAN: {
//                view.zoomin(true);
            }
            break;
            case COMMAND_DINGWEI: {
//                view.zoomout(false);
            }
            break;
            case COMMAND_CLEAR: {
//                view.zoomin(true);
            }
            break;
            case COMMAND_LENGTH: {
                mapTouchListener.clearDrawLayer();
//
//                // 设置激活 画线
                mapTouchListener.setGeoType("Polyline");
            }
            break;
            case COMMAND_AREA: {
//                _MapTouchListener.clearDrawLayer();
//                _MapTouchListener.createDrawLayer();
//
//                // 设置激活 画线
//                _MapTouchListener.setType(Geometry.Type.POLYGON);
                mapTouchListener.clearDrawLayer();
//
//                // 设置激活 画线
                mapTouchListener.setGeoType("Polygon");
            }
            break;
        }
    }




}
