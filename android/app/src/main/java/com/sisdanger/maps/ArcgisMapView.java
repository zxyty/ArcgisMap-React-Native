package com.sisdanger.maps;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.esri.android.map.MapView;
import com.esri.core.geometry.Geometry;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.sisdanger.R;
import com.sisdanger.maps.TianDiTuTiledMapServiceLayer;

import java.util.Map;

/**
 * Created by zxy on 2017/12/6.
 */

public class ArcgisMapView extends SimpleViewManager<MapView> {
    private ThemedReactContext _context;

    public static final String REACT_CLASS = "RCTArcgisMapView";
    public MapView _MapView;
    public MapTouchListener _MapTouchListener;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected MapView createViewInstance(ThemedReactContext reactContext) {
        _context = reactContext;
        _MapView = new MapView(reactContext);
        TianDiTuTiledMapServiceLayer mapBase = new TianDiTuTiledMapServiceLayer("http://www.scgis.net.cn/iMap/iMapServer/DefaultRest/services/newtianditudlg", "");
        _MapView.addLayer(mapBase);

        // 注册点击事件
        _MapTouchListener = new MapTouchListener(_context, _MapView);
        return _MapView;
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
                view.zoomin(true);
            }
            break;
            case COMMAND_ZOOMOUT: {
                view.zoomout(false);
            }
            break;
            case COMMAND_BIAOXUAN: {
                view.zoomin(true);
            }
            break;
            case COMMAND_DINGWEI: {
                view.zoomout(false);
            }
            break;
            case COMMAND_CLEAR: {
                view.zoomin(true);
            }
            break;
            case COMMAND_LENGTH: {
//                view.zoomout(false);
                _MapTouchListener.clearDrawLayer();
                _MapTouchListener.createDrawLayer();

                // 设置激活 画线
                _MapTouchListener.setType(Geometry.Type.POLYLINE);
            }
            break;
            case COMMAND_AREA: {
//                view.zoomin(true);
                _MapTouchListener.clearDrawLayer();
                _MapTouchListener.createDrawLayer();

                // 设置激活 画线
                _MapTouchListener.setType(Geometry.Type.POLYGON);
            }
            break;
        }
    }




}
