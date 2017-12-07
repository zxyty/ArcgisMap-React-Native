
package com.sisdanger.maps;
import android.util.Log;

import com.esri.android.map.TiledServiceLayer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zxt on 2017/5/12.
 */

public class TianDiTuTiledMapServiceLayer extends TiledServiceLayer {
    private TileInfo tiandituTileInfo;
    private String mUrl;
    private String mUnit;

    public TianDiTuTiledMapServiceLayer() {
        this("", "");
    }

    public TianDiTuTiledMapServiceLayer(String url, String Unit) {
        super("");
        mUrl = url;
        mUnit = Unit;
        try {
            getServiceExecutor().submit(new Runnable() {

                public final void run() {
                    a.initLayer();
                }

                final TianDiTuTiledMapServiceLayer a;


                {
                    a = TianDiTuTiledMapServiceLayer.this;
                    //super();
                }
            });
            return;
        } catch (Exception _ex) {
        }
    }

    protected void initLayer() {
        this.buildTileInfo();
//        this.setFullExtent(new Envelope(-180, -90, 180, 90));
        this.setFullExtent(new Envelope(95, 24, 110, 35));
        //this.setDefaultSpatialReference(SpatialReference.create(4326));   //CGCS2000
        this.setDefaultSpatialReference(SpatialReference.create(4326));
        this.setInitialExtent(new Envelope(95, 24, 110, 35));
//        珙县数据
//        Initial Extent:
//        XMin: 104.69229123103534
//        YMin: 28.412848313350565
//        XMax: 104.73474612673695
//        YMax: 28.437349861942394
//        Spatial Reference: 4490  (4490)
//
//        Full Extent:
//        XMin: 104.70613118600005
//        YMin: 28.399988671000074
//        XMax: 104.72883884500004
//        YMax: 28.437110965000045
//        Spatial Reference: 4490  (4490)

        // 龙泉数据
//        Initial Extent:
//        XMin: 104.11522135542174
//        YMin: 30.508012133200157
//        XMax: 104.31646766210093
//        YMax: 30.628404078944396
//        Spatial Reference: 4490  (4490)
//
//        Full Extent:
//        XMin: 104.11522135542175
//        YMin: 30.49383532271438
//        XMax: 104.30144337987343
//        YMax: 30.58248376052007
//        Spatial Reference: 4490  (4490)
        this.setInitialExtent(new Envelope(104.11522135542174, 30.508012133200157, 104.31646766210093, 30.628404078944396));
//        try {
//            MapData tempItem = ((MapData)ApiConfig.SzMapData.get(this.mUnit));
//            this.setInitialExtent(new Envelope(tempItem.XMin, tempItem.YMin, tempItem.XMax, tempItem.YMax));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        super.initLayer();
    }

    public void refresh() {
        try {
            getServiceExecutor().submit(new Runnable() {

                public final void run() {
                    if (a.isInitialized())
                        try {
                            a.b();
                            a.clearTiles();
                            return;
                        } catch (Exception exception) {
                            Log.e("ArcGIS", "Re-initialization of the layer failed.", exception);
                        }
                }

                final TianDiTuTiledMapServiceLayer a;


                {
                    a = TianDiTuTiledMapServiceLayer.this;
                    //super();
                }
            });
            return;
        } catch (Exception _ex) {
            return;
        }
    }

    final void b()
            throws Exception {

    }

    @Override
    protected byte[] getTile(int level, int col, int row) throws Exception {
        /**
         *
         * */

        byte[] result = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            URL sjwurl = new URL(this.getTianDiMapUrl(level, row, col));
            HttpURLConnection httpUrl = null;
            BufferedInputStream bis = null;
            byte[] buf = new byte[1024];

            httpUrl = (HttpURLConnection) sjwurl.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());

            while (true) {
                int bytes_read = bis.read(buf);
                if (bytes_read > 0) {
                    bos.write(buf, 0, bytes_read);
                } else {
                    break;
                }
            }
            ;
            bis.close();
            httpUrl.disconnect();

            result = bos.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }


    @Override
    public TileInfo getTileInfo() {
        return this.tiandituTileInfo;
    }

    /**
     *
     * */
    private String getTianDiMapUrl(int level, int col, int row) {

        return mUrl + "/tile/" + level + "/" + col + "/" + row + "?token=ZapmAu3mQohbR0V_cti_Vt9ipy1fDb-Nkgo3AWo9NIeKIew99f9bcVvfPelxsgAuu3Cpvdjx7dIFpzTQxJtSXw..";
    }

    private void buildTileInfo() {
        Point originalPoint = new Point(-180, 90);

        double[] res = {
                1.40625,
                0.703125,
                0.3515625,
                0.17578125,
                0.087890625,
                0.0439453125,
                0.02197265625,
                0.010986328125,
                0.0054931640625,
                0.00274658203125,
                0.001373291015625,
                0.0006866455078125,
                0.00034332275390625,
                0.000171661376953125,
                8.58306884765629E-05,
                4.29153442382814E-05,
                2.14576721191407E-05,
                1.07288360595703E-05,
                5.36441802978515E-06,
                2.68220901489258E-06,
                1.34110450744629E-06
        };
        double[] scale = {
                400000000,
                295497598.5708346,
                147748799.285417,
                73874399.6427087,
                36937199.8213544,
                18468599.9106772,
                9234299.95533859,
                4617149.97766929,
                2308574.98883465,
                1154287.49441732,
                577143.747208662,
                288571.873604331,
                144285.936802165,
                72142.9684010827,
                36071.4842005414,
                18035.7421002707,
                9017.87105013534,
                4508.93552506767,
                2254.467762533835,
                1127.2338812669175,
                563.616940
        };
        int levels = 21;
        int dpi = 96;
        int tileWidth = 256;
        int tileHeight = 256;
        this.tiandituTileInfo = new com.esri.android.map.TiledServiceLayer.TileInfo(originalPoint, scale, res, levels, dpi, tileWidth, tileHeight);
        this.setTileInfo(this.tiandituTileInfo);
    }

}
