package com.rushro2m.gaodemap.mapmarker;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.rushro2m.gaodemap.R;

public class LocationModeSourceActivity extends AppCompatActivity implements AMap.OnMyLocationChangeListener, AdapterView.OnItemSelectedListener {

    private MapView mapView;
    private AMap aMap;
    private Spinner spinnerGps;
    private String[] itemLocationTypes = {"展示", "定位", "追随", "旋转", "旋转位置", "追随不移动到中心点", "旋转不移动到中心点", "旋转位置不移动到中心点"};

    private MyLocationStyle myLocationStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_mode_source);

        mapView = findViewById(R.id.locationMap);
        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        spinnerGps = findViewById(R.id.spinner_gps);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                itemLocationTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGps.setAdapter(adapter);

        spinnerGps.setOnItemSelectedListener(this);

        aMap.setOnMyLocationChangeListener(this);

    }

    private void setUpMap() {
        myLocationStyle = new MyLocationStyle();
        aMap.setMyLocationStyle(myLocationStyle);

        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        //显示定位曾并可触发定位，默认未false
        aMap.setMyLocationEnabled(true);
    }


    //定位回调监听
    @Override
    public void onMyLocationChange(Location location) {
        if (location != null) {
            Bundle bundle = location.getExtras();

            if (bundle != null) {
                int errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE);
                String errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO);
                int locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE);

                Log.e("amap", "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType);
            } else {
                Log.e("amap", "定位信息， bundle is null ");
            }
        } else {
            Log.e("amap", "定位失败");
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                //只定位，不进行其他操作
                aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW));
                break;

            case 1:
                aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE));
                break;

            case 2:
                //设置定位类型未跟随模式
                aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW));
                break;

            case 3:
                //设置定位的类型未根据地图面向方向旋转
                aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE));
                break;

            case 4:
                //定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且跟随设备移动
                aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE));
                break;

            case 5:
                //定位/但不会移动到地图中心点，并且跟随设备移动
                aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER));
                break;

            case 6:
                //定位，但不会移动到地图中心点，地图依照设备方向旋转，并且跟随设备移动
                aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER));
                break;

            case 7:
                //定位、但不会移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。
                aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
