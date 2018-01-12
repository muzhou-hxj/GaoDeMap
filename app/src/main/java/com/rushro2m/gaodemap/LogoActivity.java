package com.rushro2m.gaodemap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;

public class LogoActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    MapView mapView;
    AMap aMap;
    RadioGroup rg_logo;
    UiSettings mUiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        mapView = findViewById(R.id.logoMap);
        mapView.onSaveInstanceState(savedInstanceState);

        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
        }

        rg_logo = findViewById(R.id.rg_logo);
        rg_logo.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.bottom_left:
                //设置地图Logo在左下方
                mUiSettings
                        .setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
                break;
            case R.id.bottom_center:
                //设置地图Logo居中
                mUiSettings
                        .setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);
                break;
            case R.id.bottom_right:
                //设置地图Logo在右下方
                mUiSettings
                        .setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
                break;
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
