package com.sisdanger.maps.components;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Callout;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.sisdanger.maps.TianDiTuTiledMapServiceLayer;

/**
 * Created by zxy on 2017/12/11.
 */

public class MapView extends ViewGroupManager<com.esri.arcgisruntime.mapping.view.MapView> {
    public static final String REACT_CLASS = "ArcgisMapView";
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected com.esri.arcgisruntime.mapping.view.MapView createViewInstance(ThemedReactContext reactContext) {

        com.esri.arcgisruntime.mapping.view.MapView _MapView = new com.esri.arcgisruntime.mapping.view.MapView(reactContext);
        WebTiledLayer webTiledLayer = TianDiTuTiledMapServiceLayer.CreateTianDiTuTiledLayer(TianDiTuTiledMapServiceLayer.LayerType.TIANDITU_VECTOR_2000);
        Basemap tdtBasemap = new Basemap(webTiledLayer);
        WebTiledLayer webTiledLayer1 = TianDiTuTiledMapServiceLayer.CreateTianDiTuTiledLayer(TianDiTuTiledMapServiceLayer.LayerType.TIANDITU_VECTOR_ANNOTATION_CHINESE_2000);
        tdtBasemap.getBaseLayers().add(webTiledLayer1);
        ArcGISMap arcGISMap = new ArcGISMap();
        _MapView.setMap(arcGISMap);
        _MapView.getMap().setBasemap(tdtBasemap);

        return _MapView;
    }
}
