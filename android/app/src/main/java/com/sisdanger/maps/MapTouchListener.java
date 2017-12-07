package com.sisdanger.maps;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Line;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.facebook.react.ReactActivity;

import java.util.ArrayList;

/**
 * Created by zxy on 2017/12/7.
 */

public class MapTouchListener extends MapOnTouchListener {

    private Context _context;

    private ArrayList<Point> drawPoints = null; // 绘制点
    private Point ptStart = null;               // 绘制开始点
    private Point ptEnd = null;                 // 绘制结束点
    private Point ptPrevious = null;            // 记录绘制之前的点
    private Geometry.Type drawGeoType = null;   // 绘制类型
    private String currToastText = null;        // 绘制描述

    // static
    static GraphicsLayer drawLayer = null;              // 绘制图层
    static MapView targetMap = null;                    // MapView
    static SimpleLineSymbol lineSymbol = null;          // 绘制线样式
    static SimpleMarkerSymbol markerSymbol = null;      //
    static SimpleFillSymbol fillSymbol = null;          // 绘制区域填充样式



    public MapTouchListener(Context context, MapView view) {
        super(context, view);
        _context = context;
        targetMap = view;

        fillSymbol = new SimpleFillSymbol(Color.argb(100, 0, 225, 255));
        fillSymbol.setOutline(new SimpleLineSymbol(Color.TRANSPARENT, 0));
        markerSymbol = new SimpleMarkerSymbol(Color.BLUE, 10, SimpleMarkerSymbol.STYLE.DIAMOND);
        lineSymbol = new SimpleLineSymbol(Color.YELLOW, 3);

        drawPoints = new ArrayList<Point>();
    }

    // 清除绘制信息
    public void clearDrawLayer() {
        drawPoints.clear();          // 绘制点
        ptStart = null;               // 绘制开始点
        ptEnd = null;                 // 绘制结束点
        ptPrevious = null;            // 记录绘制之前的点
        drawGeoType = null;          // 绘制类型

        targetMap.getCallout().hide();
        if (drawLayer != null) {
            drawLayer.removeAll();
            targetMap.removeLayer(drawLayer);
            drawLayer = null;
        }
    }

    // 根据用户选择设置当前绘制的几何图形类型
    public void setType(String geometryType) {
        if(geometryType.equalsIgnoreCase("Point"))
            this.drawGeoType = Geometry.Type.POINT;
        else if(geometryType.equalsIgnoreCase("Polyline"))
            this.drawGeoType = Geometry.Type.POLYLINE;
        else if(geometryType.equalsIgnoreCase("Polygon"))
            this.drawGeoType = Geometry.Type.POLYGON;
    }

    public void setType(Geometry.Type type) {
        this.drawGeoType = type;
    }

    //  得到当前选择的geoType
    public Geometry.Type getType() {
        return this.drawGeoType;
    }

    public void createDrawLayer() {
        try {
            if (targetMap != null) {

                drawLayer = new GraphicsLayer();
                targetMap.addLayer(drawLayer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onSingleTap(MotionEvent point) {

//        return super.onSingleTap(point);

        if (targetMap == null || drawLayer == null) {
            return true;
        }
        // 当前点
        this.ptPrevious = this.ptStart;
        Point ptCurrent = targetMap.toMapPoint(new Point(point.getX(), point.getY()));
        this.ptStart = ptCurrent;
        this.drawPoints.add(ptCurrent);

        //直接画点
        if (this.drawGeoType == Geometry.Type.POINT) {
            // 画一个新的点在地图上
            return true;
            // 画线
        } else if (this.drawGeoType != null){
            // 清除之前
            drawLayer.removeAll();
            // 加入当前点
            Graphic graphic = new Graphic(ptCurrent, this.markerSymbol);
            MapTouchListener.drawLayer.addGraphic(graphic);

            // 如果是画线
            if (this.drawGeoType == Geometry.Type.POLYLINE) {
                Polyline polyline = new Polyline();
                for (int i = 1; i < this.drawPoints.size(); i++) {
                    Point startPoint = this.drawPoints.get(i - 1);
                    Point endPoint = this.drawPoints.get(i);
                    Line line = new Line();
                    line.setStart(startPoint);
                    line.setEnd(endPoint);
                    polyline.addSegment(line, false);
                }
                Graphic g = new Graphic(polyline, this.lineSymbol);
                drawLayer.addGraphic(g);
                // 计算当前线段的长度
                GeometryEngine geometryEngine = new GeometryEngine();
                double result = GeometryEngine.geodesicLength(polyline, targetMap.getSpatialReference(), null);

                currToastText = String.format("%.1f", result) + " 米";
            }
            // 绘制多边形
            else  if (this.drawGeoType == Geometry.Type.POLYGON) {

                // 1、连线
                Polyline polyline = new Polyline();
                for (int i = 1; i < this.drawPoints.size(); i++) {
                    Point startPoint = this.drawPoints.get(i - 1);
                    Point endPoint = this.drawPoints.get(i);
                    Line line = new Line();
                    line.setStart(startPoint);
                    line.setEnd(endPoint);
                    polyline.addSegment(line, false);
                }
                // 补充最后一个点与起点重合
                if (this.drawPoints.size() >= 3) {
                    Point endPoint = this.drawPoints.get(0);
                    Point startPoint = this.drawPoints.get((this.drawPoints.size() - 1));
                    Line line = new Line();
                    line.setStart(startPoint);
                    line.setEnd(endPoint);
                    polyline.addSegment(line, false);
                }
                Graphic g = new Graphic(polyline, this.lineSymbol);
                drawLayer.addGraphic(g);

                // 2、填充
                Polygon polygon = new Polygon();
                for (int i = 1; i < this.drawPoints.size(); i++) {
                    Point startPoint = this.drawPoints.get(i - 1);
                    Point endPoint = this.drawPoints.get(i);
                    Line line = new Line();
                    line.setStart(startPoint);
                    line.setEnd(endPoint);
                    polygon.addSegment(line, false);
                }
                Graphic pg = new Graphic(polygon, fillSymbol);
                drawLayer.addGraphic(pg);

                //计算当前面积
                GeometryEngine geometryEngine = new GeometryEngine();
                double result = GeometryEngine.geodesicArea(polygon, targetMap.getSpatialReference(), null);

                currToastText = getAreaString(result);
            }
        }
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent point) {
        try {
            if (drawLayer == null || targetMap == null) {
                return true;
            }
            if (this.drawGeoType != Geometry.Type.POINT && this.drawGeoType != null) {
                // create a textview for the callout
                TextView calloutContent = new TextView(_context);
                calloutContent.setTextColor(Color.BLACK);
                calloutContent.setSingleLine();
                calloutContent.setText("测量结果为: " + currToastText);

                // get callout, set content and show
                Callout mCallout = targetMap.getCallout();
                mCallout.setCoordinates(this.ptStart);
                mCallout.setContent(calloutContent);
                mCallout.show();
                Toast.makeText(targetMap.getContext(), currToastText, Toast.LENGTH_SHORT).show();
            } else if (this.drawGeoType == Geometry.Type.POINT){
                // 纪录标选的坐标点
//                if (this.ptStart != null)
//                {
//                    MapTouchListener.biaoXuanResult[0] = this.ptStart.getX();
//                    MapTouchListener.biaoXuanResult[1] = this.ptStart.getY();
//                }
            }

            this.ptStart = null;
            this.ptPrevious = null;
            this.drawPoints.clear();
            this.drawPoints = new ArrayList<Point>();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getAreaString(double dValue){
//        long area = Math.abs(Math.round(dValue));
        String sArea = "";
        // 顺时针绘制多边形，面积为正，逆时针绘制，则面积为负
//        DecimalFormat df = new DecimalFormat("#0.0");
        if(dValue >= 1000000){
            double dArea = dValue / 1000000.0;
            sArea = Double.toString(dArea) + " 平方公里";
        }
        else {
            sArea = String.format("%.1f", dValue) + " 平方米";
        }
        return sArea;
    }
}
