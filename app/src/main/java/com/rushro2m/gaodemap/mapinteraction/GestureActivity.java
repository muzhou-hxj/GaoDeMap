package com.rushro2m.gaodemap.mapinteraction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.rushro2m.gaodemap.R;

public class GestureActivity extends AppCompatActivity implements View.OnClickListener {

    private MapView mapView;
    private AMap aMap;
    private CheckBox scroll, zoom, tilt, rotate;
    UiSettings mUiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);

        mapView = findViewById(R.id.gestureMap);
        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
        }

        scroll = findViewById(R.id.scroll_gesture_toggle);
        zoom = findViewById(R.id.zoom_gesture_toggle);
        tilt = findViewById(R.id.tilt_gesture_toggle);
        rotate = findViewById(R.id.rotate_gesture_toggle);

        scroll.setOnClickListener(this);
        zoom.setOnClickListener(this);
        tilt.setOnClickListener(this);
        rotate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scroll_gesture_toggle:
                //设置地图是否可以使用滑动手势
                mUiSettings.setScrollGesturesEnabled(((CheckBox) v).isChecked());
                break;
            case R.id.zoom_gesture_toggle:
                //设置地图是否可以手势缩放大小
                mUiSettings.setZoomGesturesEnabled(((CheckBox) v).isChecked());
                break;
            case R.id.tilt_gesture_toggle:
                //设置地图是否可以倾斜
                mUiSettings.setTiltGesturesEnabled(((CheckBox) v).isChecked());
                break;
            case R.id.rotate_gesture_toggle:
                //设置地图是否可以旋转
                mUiSettings.setRotateGesturesEnabled(((CheckBox) v).isChecked());
                break;
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
