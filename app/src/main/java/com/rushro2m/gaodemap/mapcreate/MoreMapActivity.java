package com.rushro2m.gaodemap.mapcreate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.rushro2m.gaodemap.R;

public class MoreMapActivity extends AppCompatActivity {

    private MapView mapView1, mapView2;
    private AMap aMap1, aMap2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_map);

        mapView1 = findViewById(R.id.map_more_1);
        mapView2 = findViewById(R.id.map_more_2);

        mapView1.onCreate(savedInstanceState);
        mapView2.onCreate(savedInstanceState);

        aMap1 = mapView1.getMap();
        aMap2 = mapView2.getMap();

        init();

    }

    private void init() {
        aMap1.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                setUp(aMap1);
            }
        });

        aMap2.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                setUp(aMap2);
            }
        });
    }

    /**
     *  地图内置UI及手势控制器
     */
    private void setUp(AMap aMap) {

        UiSettings settings = aMap.getUiSettings();
        //设置是否显示室内地图，默认不显示
        aMap.showIndoorMap(true);
        //设置指针为可见
        settings.setCompassEnabled(true);
        // 设置比例尺控件为可见
        settings.setScaleControlsEnabled(true);
        //设置定位按钮为可见
        settings.setMyLocationButtonEnabled(true);
    }


    /**
     * 必须重写onResume，onStart，onPause，onDestroy，onSaveInstance几个方法
     */

    @Override
    protected void onResume() {
        super.onResume();
        mapView1.onResume();
        mapView2.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView1.onPause();
        mapView2.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView1.onSaveInstanceState(outState);
        mapView2.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView1.onDestroy();
        mapView2.onDestroy();
    }
}
