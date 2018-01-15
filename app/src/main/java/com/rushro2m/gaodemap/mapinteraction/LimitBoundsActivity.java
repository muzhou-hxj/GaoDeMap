package com.rushro2m.gaodemap.mapinteraction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.rushro2m.gaodemap.R;

public class LimitBoundsActivity extends AppCompatActivity {

    private MapView mapView;
    private AMap aMap;

    //西南坐标
    private LatLng southWestLatLng = new LatLng(39.674949, 115.932873);

    //东北坐标
    private LatLng northeastLatLng = new LatLng(40.159453, 116.767834);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit_bounds);

        mapView = findViewById(R.id.limitBoundsMap);
        mapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mapView.getMap();
        }

        aMap.addMarker(new MarkerOptions().position(southWestLatLng).title("西南坐标"));
        aMap.addMarker(new MarkerOptions().position(northeastLatLng).title("东北坐标"));

        aMap.moveCamera(CameraUpdateFactory.zoomTo(8.0f));
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

    //设置限制区域
    public void set(View view) {
        LatLngBounds bounds = new LatLngBounds(southWestLatLng, northeastLatLng);
        aMap.setMapStatusLimits(bounds);
    }
}
