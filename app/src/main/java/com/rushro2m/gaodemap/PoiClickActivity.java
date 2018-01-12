package com.rushro2m.gaodemap;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.NaviPara;
import com.amap.api.maps.model.Poi;

public class PoiClickActivity extends AppCompatActivity implements AMap.OnPOIClickListener, AMap.OnMarkerClickListener {

    MapView mapView;
    AMap aMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_click);

        mapView = findViewById(R.id.poiClickMap);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.setOnPOIClickListener(this);
        aMap.setOnMarkerClickListener(this);
    }


    /**
     * 底图POI点击事件回调
     * @param poi
     */
    @Override
    public void onPOIClick(Poi poi) {
        aMap.clear();
        Log.e("MY", poi.getPoiId()+poi.getName() );
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(poi.getCoordinate());
        TextView textView = new TextView(getApplicationContext());
        textView.setText("到"+poi.getName()+"去");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundResource(R.drawable.custom_info_bubble);
        markerOptions.icon(BitmapDescriptorFactory.fromView(textView));
        aMap.addMarker(markerOptions);
    }

    //Marker点击回调
    @Override
    public boolean onMarkerClick(Marker marker) {
        //构建导航参数
        NaviPara naviPara = new NaviPara();
        //设置终点位置
        naviPara.setTargetPoint(marker.getPosition());
        //设置导航策略，这里是避免拥堵
        naviPara.setNaviStyle(AMapUtils.DRIVING_AVOID_CONGESTION);
        try {
            //调起高德地图导航
            AMapUtils.openAMapNavi(naviPara,getApplicationContext());
        } catch (AMapException e) {
           //如果没安装就会进入异常，调起下载界面
            AMapUtils.getLatestAMapApp(getApplicationContext());
        }
        aMap.clear();
        return false;
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
