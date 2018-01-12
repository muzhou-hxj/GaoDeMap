package com.rushro2m.gaodemap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.VisibleRegion;

public class EventsActivity extends AppCompatActivity implements AMap.OnMapClickListener, AMap.OnMapLongClickListener, AMap.OnCameraChangeListener, AMap.OnMapTouchListener {

    private MapView mapView;
    private AMap aMap;
    private TextView tap, camera, touch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        mapView = findViewById(R.id.eventsMap);
        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUp();
        }

        tap = findViewById(R.id.tap_text);
        camera = findViewById(R.id.camera_text);
        touch = findViewById(R.id.touch_text);
    }

    private void setUp() {
        //添加单击地图事件监听器
        aMap.setOnMapClickListener(this);
        //长按地图事件监听器
        aMap.setOnMapLongClickListener(this);
        //移动地图事件监听器
        aMap.setOnCameraChangeListener(this);
        //触摸地图事件监听器
        aMap.setOnMapTouchListener(this);
    }

    /**
     * 单击地图事件回调
     *
     * @param latLng
     */
    @Override
    public void onMapClick(LatLng latLng) {
        tap.setText("tapped,point" + latLng);
    }

    /**
     * 长按地图事件回调
     *
     * @param latLng
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        tap.setText("long pressed,point" + latLng);
    }

    /**
     * 地图移动事件回调
     *
     * @param cameraPosition
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        camera.setText("onCameraChange:" + cameraPosition.toString());
    }

    /**
     * 移动地图结束事件回调
     *
     * @param cameraPosition
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        camera.setText("onCameraChangeFinished:" + cameraPosition.toString());

        //获取可视区域
        VisibleRegion visibleRegion = aMap.getProjection().getVisibleRegion();
        //可视区域边界的Bounds
        LatLngBounds latLngBounds = visibleRegion.latLngBounds;
        //判断上海是否在可视区域内
        boolean isContain = latLngBounds.contains(Constants.SHANGHAI);
        if (isContain) {
            Toast.makeText(this, "上海在当前可见区域内", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "上海不在当前可见区域内", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 对触摸地图事件的回调
     *
     * @param motionEvent
     */
    @Override
    public void onTouch(MotionEvent motionEvent) {
        touch.setText("触摸事件：屏幕位置：" + motionEvent.getX() + "," + motionEvent.getY());
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
