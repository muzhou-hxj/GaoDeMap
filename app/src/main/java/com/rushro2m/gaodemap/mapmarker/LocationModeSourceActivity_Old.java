package com.rushro2m.gaodemap.mapmarker;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.rushro2m.gaodemap.R;

public class LocationModeSourceActivity_Old extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, LocationSource, AMapLocationListener {

    private MapView mapView;
    private AMap aMap;
    private RadioGroup mGPSModeGroup;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    private TextView mLocationErrText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_mode_source__old);

        mapView = findViewById(R.id.locationOldMap);
        mapView.onCreate(savedInstanceState);

        init();
    }



    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        mGPSModeGroup = findViewById(R.id.gps_radio_group);
        mGPSModeGroup.setOnCheckedChangeListener(this);
        mLocationErrText = findViewById(R.id.location_errInfo_text);
        mLocationErrText.setVisibility(View.GONE);

    }

    private void setUpMap() {
        //设置定位监听
        aMap.setLocationSource(this);
        //设置默认定位按钮为显示
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        //显示定位层并可触发定位，默认为false
        aMap.setMyLocationEnabled(true);
        //普通定位模式常量，只在第一次定位移动到地图中心点。
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (mLocationOption == null || mLocationClient == null) {
            return;
        }
        switch (checkedId) {
            case R.id.gps_locate_button:
                //设置定位为普通模式
                aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
                mLocationOption.setOnceLocation(true);
                mLocationClient.setLocationOption(mLocationOption);
                mLocationClient.startLocation();
                break;
            case R.id.gps_follow_button:
                //设置定位模式为跟随模式
                aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
                mLocationOption.setOnceLocation(false);
                mLocationClient.setLocationOption(mLocationOption);
                mLocationClient.startLocation();
                break;

            case R.id.gps_rotate_button:
                aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
                mLocationOption.setOnceLocation(false);
                mLocationClient.setLocationOption(mLocationOption);
                mLocationClient.startLocation();
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
        deactivate();
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
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    /**
     * 激活定位
     *
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);

            mLocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    /**
     * 定位成功后 回调函数
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mLocationErrText.setVisibility(View.GONE);
                mListener.onLocationChanged(aMapLocation);
            } else {
                String errText = "定位失败" + aMapLocation.getErrorCode() + aMapLocation.getErrorInfo();
                mLocationErrText.setVisibility(View.VISIBLE);
                mLocationErrText.setText(errText);
            }
        }
    }
}
