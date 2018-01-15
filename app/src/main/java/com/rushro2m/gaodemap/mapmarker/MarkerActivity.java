package com.rushro2m.gaodemap.mapmarker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.rushro2m.gaodemap.R;

public class MarkerActivity extends AppCompatActivity {

    private MapView mapView;
    private AMap aMap;

    private MarkerOptions markerOptions;

    Marker marker;

    private LatLng latLng = new LatLng(39.761, 116.434);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        mapView = findViewById(R.id.markerMap);
        mapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mapView.getMap();
            addMarkersToMap();//往地图上添加Marker
        }

    }

    private void addMarkersToMap() {
        markerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_car))
                .position(latLng)
                .draggable(true);

        marker = aMap.addMarker(markerOptions);
    }

    public void clear(View view) {
        if (aMap != null) {
            marker.destroy();
        }
    }


    public void reset(View view) {
        if (aMap != null) {
            aMap.clear();
            addMarkersToMap();
        }
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
