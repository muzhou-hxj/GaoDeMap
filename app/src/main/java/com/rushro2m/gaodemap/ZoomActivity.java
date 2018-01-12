package com.rushro2m.gaodemap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;

public class ZoomActivity extends AppCompatActivity implements View.OnClickListener {

    MapView mapView;
    AMap aMap;

    ToggleButton animate;
    Button zoomIn, zoomOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);

        mapView = findViewById(R.id.zoomMap);
        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        animate = findViewById(R.id.animate);
        zoomIn = findViewById(R.id.zoom_in);
        zoomOut = findViewById(R.id.zoom_out);

        zoomIn.setOnClickListener(this);
        zoomOut.setOnClickListener(this);

    }

    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {
        boolean isAnimate = animate.isChecked();
        if (isAnimate) {
            aMap.animateCamera(update, 1000, callback);
        } else {
            aMap.moveCamera(update);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zoom_in:
                changeCamera(CameraUpdateFactory.zoomIn(),null);
                break;

            case R.id.zoom_out:
                changeCamera(CameraUpdateFactory.zoomOut(),null);
                break;
        }
    }
}
