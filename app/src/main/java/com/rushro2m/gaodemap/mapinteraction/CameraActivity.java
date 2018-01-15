package com.rushro2m.gaodemap.mapinteraction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.MarkerOptions;
import com.rushro2m.gaodemap.R;
import com.rushro2m.gaodemap.utils.Constants;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private MapView mapView;
    private AMap aMap;
    private Button ljz, zgc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmaera);

        mapView = findViewById(R.id.cameraMap);
        mapView.onCreate(savedInstanceState);

        init();

    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        ljz = findViewById(R.id.ljz);
        zgc = findViewById(R.id.zgc);

        ljz.setOnClickListener(this);
        zgc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ljz:
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(
                                new CameraPosition(
                                        Constants.SHANGHAI, 18, 30, 0)));
                aMap.clear();
                aMap.addMarker(new MarkerOptions().position(Constants.SHANGHAI)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                break;
            case R.id.zgc:
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(
                                new CameraPosition(
                                        Constants.ZHONGGUANCUN, 18, 30, 30)));
                aMap.clear();
                aMap.addMarker(
                        new MarkerOptions().position(
                                Constants.ZHONGGUANCUN)
                                .icon(BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_RED)));
                break;
        }
    }

    private void changeCamera(CameraUpdate update) {
        aMap.moveCamera(update);
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
