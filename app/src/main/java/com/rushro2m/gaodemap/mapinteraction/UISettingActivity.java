package com.rushro2m.gaodemap.mapinteraction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.rushro2m.gaodemap.R;

public class UISettingActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private MapView mapView;
    private AMap aMap;
    private UiSettings mUiSettings;
    private RadioGroup zoomRadioGroup;

    private Button btnScale;
    private CheckBox
            //比例尺
            scaleToggle,
    //
    zoomToggle,
    //指南针
    compassToggle,
    //我的图层
    myLocationToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uisetting);

        mapView = findViewById(R.id.settingMap);
        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        //AMap与UiSettings实例化
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
        }
        //控件实例化
        btnScale = findViewById(R.id.buttonScale);
        scaleToggle = findViewById(R.id.scale_toggle);
        zoomToggle = findViewById(R.id.zoom_toggle);
        zoomRadioGroup = findViewById(R.id.zoom_position);
        compassToggle = findViewById(R.id.compass_toggle);
        myLocationToggle = findViewById(R.id.mylocation_toggle);

        //绑定事件
        btnScale.setOnClickListener(this);
        scaleToggle.setOnClickListener(this);
        zoomToggle.setOnClickListener(this);
        zoomRadioGroup.setOnCheckedChangeListener(this);
        compassToggle.setOnClickListener(this);
        myLocationToggle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonScale:
                float scale = aMap.getScalePerPixel();
                Toast.makeText(this, "每像素代表" + scale + "米", Toast.LENGTH_SHORT).show();
                break;
            /**
             * 设置地图默认的比例尺是否显示
             */
            case R.id.scale_toggle:
                mUiSettings.setScaleControlsEnabled(((CheckBox) v).isChecked());
                break;
            /**
             * 设置地图默认的缩放按钮是否显示
             */

            case R.id.zoom_toggle:
                mUiSettings.setZoomControlsEnabled(((CheckBox) v).isChecked());
                zoomRadioGroup.setVisibility(((CheckBox) v).isChecked() ? View.VISIBLE : View.GONE);
                break;
            /**
             * 设置地图默认的指南针是否显示
             */
            case R.id.compass_toggle:
                mUiSettings.setCompassEnabled(((CheckBox) v).isChecked());
                break;

            /**
             * 设置地图默认的定位按钮是否显示
             */
            case R.id.mylocation_toggle:
                //是否显示系统默认的定位按钮
                mUiSettings.setMyLocationButtonEnabled(((CheckBox) v).isChecked());
                //是否可触发定位并显示定位曾
                aMap.setMyLocationEnabled(((CheckBox) v).isChecked());
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (aMap != null) {
            switch (checkedId) {
                case R.id.zoom_bottom_right:
                    mUiSettings
                            .setZoomPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
                    break;
                case R.id.zoom_center_right:
                    mUiSettings
                            .setZoomPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
