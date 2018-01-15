package com.rushro2m.gaodemap.mapcreate;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.CameraPosition;
import com.rushro2m.gaodemap.utils.Constants;

/**
 * Created by 16918 on 2018/1/11.
 */

public class MapOptionActivity extends FragmentActivity {

    private static final String MAP_FRAGMENT_TAG = "map";
    //相机位置，包含了所有可视区域的位置参数
    static final CameraPosition LUJIAZUI = new CameraPosition
            .Builder()
            //目标位置的屏幕中心点经纬度坐标
            .target(Constants.SHANGHAI)
            //目标可视区域的缩放级别
            .zoom(18)
            //可视区域指定的方向，从正北向逆时针计算，0-360
            .bearing(0)
            //目标可视区域的倾斜度，以角度为单位
            .tilt(30)
            .build();

    private AMap aMap;

    private SupportMapFragment aMapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        //MapView初始化选项
        AMapOptions aOptions = new AMapOptions();
        //禁止通过手势缩放地图
        aOptions.zoomGesturesEnabled(false);
        //禁止通过手势移动地图
        aOptions.scrollGesturesEnabled(false);
        //禁止通过手势倾斜地图
        aOptions.tiltGesturesEnabled(false);
        aOptions.camera(LUJIAZUI);
        if (aMapFragment == null) {
            aMapFragment = SupportMapFragment.newInstance(aOptions);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, aMapFragment, MAP_FRAGMENT_TAG);
            ft.commit();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        initMap();
    }

    private void initMap() {
        if (aMap == null) {
            aMap = aMapFragment.getMap();
        }
    }


}
