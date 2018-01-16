package com.rushro2m.gaodemap.mapmarker;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.rushro2m.gaodemap.R;

public class CustomLocationActivity extends AppCompatActivity {

    private AMap aMap;
    private MapView mapView;

    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);

    private static final int FULL_COLOR = Color.argb(10, 0, 0, 180);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_location);

        mapView = findViewById(R.id.customLocationMap);
        mapView.onCreate(savedInstanceState);

        init();

    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    private void setUpMap() {
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        setUpLocationStyle();
    }

    private void setUpLocationStyle() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle
                //设置Icon
                .myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point))
                //自定义精度范围的圆形边框颜色
                .strokeColor(STROKE_COLOR)
                //圆形边框宽度
                .strokeWidth(5)
                //设置圆形的填充颜色
                .radiusFillColor(FULL_COLOR);
        aMap.setMyLocationStyle(myLocationStyle);

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
