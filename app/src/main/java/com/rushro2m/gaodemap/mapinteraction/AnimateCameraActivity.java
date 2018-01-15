package com.rushro2m.gaodemap.mapinteraction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.MarkerOptions;
import com.rushro2m.gaodemap.R;
import com.rushro2m.gaodemap.utils.Constants;

public class AnimateCameraActivity extends AppCompatActivity implements
        View.OnClickListener, AMap.CancelableCallback {

    private MapView mapView;
    private AMap aMap;
    public static final int SCROLL_BY_PX = 100;

    private ToggleButton animate;
    private Button stopAnimation, ljz, zgc, left, up, right, down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_camera);

        mapView = findViewById(R.id.animateMap);
        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        stopAnimation = findViewById(R.id.stop_animation);
        animate = findViewById(R.id.animate);
        ljz = findViewById(R.id.ljz);
        zgc = findViewById(R.id.zgc);
        left = findViewById(R.id.scroll_left);
        right = findViewById(R.id.scroll_right);
        up = findViewById(R.id.scroll_up);
        down = findViewById(R.id.scroll_down);

        stopAnimation.setOnClickListener(this);
        animate.setOnClickListener(this);
        ljz.setOnClickListener(this);
        zgc.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        up.setOnClickListener(this);
        down.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.animate:

                break;
            case R.id.stop_animation:
                aMap.stopAnimation();
                break;

            case R.id.ljz:
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(
                                new CameraPosition(Constants.SHANGHAI, 18, 30, 0)),
                        null);
                aMap.clear();
                aMap.addMarker(
                        new MarkerOptions().position(Constants.SHANGHAI)
                                .icon(BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_BLUE)));
                break;
            case R.id.zgc:
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(
                                new CameraPosition(Constants.ZHONGGUANCUN, 18, 0, 30)),
                        null);
                aMap.clear();
                aMap.addMarker(
                        new MarkerOptions().position(Constants.ZHONGGUANCUN)
                                .icon(BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_RED)));
                break;
            case R.id.scroll_left:
                changeCamera(CameraUpdateFactory.scrollBy(-SCROLL_BY_PX, 0), null);
                break;
            case R.id.scroll_right:
                changeCamera(CameraUpdateFactory.scrollBy(SCROLL_BY_PX, 0), null);
                break;
            case R.id.scroll_up:
                changeCamera(CameraUpdateFactory.scrollBy(0, -SCROLL_BY_PX), null);
                break;
            case R.id.scroll_down:
                changeCamera(CameraUpdateFactory.scrollBy(0, SCROLL_BY_PX), null);
                break;
        }
    }

    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {
        boolean animated = animate.isChecked();
        if (animated) {
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
     * 地图动画效果终止回调方法
     */
    @Override
    public void onFinish() {
        Toast.makeText(this, "Animation to 陆家嘴 canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "Animation to 陆家嘴 complete", Toast.LENGTH_SHORT).show();
    }
}
