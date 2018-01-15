package com.rushro2m.gaodemap.mapinteraction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.rushro2m.gaodemap.R;

public class MinMaxZoomActivity extends AppCompatActivity {

    MapView mapView;
    AMap aMap;

    private EditText text_min, text_max;
    private TextView textCurrentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_min_max_zoom);

        mapView = findViewById(R.id.minMaxZoomMap);
        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        text_min = findViewById(R.id.text_min);
        text_max = findViewById(R.id.text_max);
        textCurrentLevel = findViewById(R.id.text_current_level);

        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                textCurrentLevel.setText("当前地图缩放级别为：" + cameraPosition.zoom);
            }
        });

        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                textCurrentLevel.setText("当前的缩放级别为：" + aMap.getCameraPosition().zoom);
            }
        });
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


    public void set(View view) {
        String minZoomLevel = text_min.getText().toString();
        String maxZoomLevel = text_max.getText().toString();
        if (minZoomLevel.length() > 0) {
            aMap.setMinZoomLevel(Float.valueOf(minZoomLevel));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(Float.valueOf(minZoomLevel)));
        }

        if (maxZoomLevel.length() > 0) {
            aMap.setMaxZoomLevel(Float.valueOf(maxZoomLevel));
        }
    }

    /**
     * 重置最大最小缩放级别
     */

    public void reset(View view) {
        aMap.resetMinMaxZoomPreference();
        text_min.setText("");
        text_max.setText("");
    }
}
