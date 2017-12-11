package com.sisdanger.maps;

import android.support.annotation.Nullable;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.sisdanger.R;

/**
 * Created by zxy on 2017/12/7.
 */

public class MapImageBtn extends SimpleViewManager<ImageButton> {

    public static final String REACT_CLASS = "ImageButton";

    @Override
    public String getName() {
        return REACT_CLASS;
    }
    @Override
    protected ImageButton createViewInstance(ThemedReactContext reactContext) {
        ImageButton zoomInBtn = new ImageButton(reactContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
        zoomInBtn.setLayoutParams(params);
        return  zoomInBtn;
    }

    @ReactProp(name = "zoomIn")
    public void setEnableZoomIn(ImageButton view, @Nullable boolean zoomIn) {
        view.setBackgroundResource(R.drawable.mapfangda);
    }
}
