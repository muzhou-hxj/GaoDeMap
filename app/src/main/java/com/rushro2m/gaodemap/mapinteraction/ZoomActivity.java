package com.rushro2m.gaodemap.mapinteraction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.rushro2m.gaodemap.R;

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

    /**
     * @param update：描述地图状态将要发生的改变
     * @param callback：来监听update是否执行完成或者中断
     */
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

    /**
     * zoomIn()：放大地图缩放级别，在当前地图显示的级别基础上加1
     * zoomOut():  缩小地图缩放级别，在当前地图显示的级别基础上减1
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zoom_in:
                changeCamera(CameraUpdateFactory.zoomIn(), null);
                break;

            case R.id.zoom_out:
                changeCamera(CameraUpdateFactory.zoomOut(), null);
                break;
        }
    }
}
