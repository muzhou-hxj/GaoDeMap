package com.rushro2m.gaodemap.indoor;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.IndoorBuildingInfo;
import com.amap.api.maps.model.LatLng;
import com.rushro2m.gaodemap.R;

public class RoomMapActivity extends AppCompatActivity {

    private MapView mapView;
    private AMap aMap;

    //楼层控制器
    IndoorFloorSwitchView floorSwitchView;

    private Handler handler = new Handler();

    //室内地图的属性类，包含了室内地图的POIID，楼层总数和当前显示的楼层等
    IndoorBuildingInfo mIndoorBuildingInfo = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_map);

        mapView = findViewById(R.id.snMap);
        mapView.onCreate(savedInstanceState);

        floorSwitchView = findViewById(R.id.indoor_switchView);

        init();

        //设置楼层控制控件监听
        floorSwitchView.setOnIndoorFloorSwitchListener(new MyIndoorSwitchViewAdapter());

        //设置室内地图状态监听接口
        aMap.setOnIndoorBuildingActiveListener(new AMap.OnIndoorBuildingActiveListener() {
            @Override
            public void OnIndoorBuilding(final IndoorBuildingInfo indoorBuildingInfo) {
                if (indoorBuildingInfo != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mIndoorBuildingInfo == null || mIndoorBuildingInfo.poiid != indoorBuildingInfo.poiid) {
                                floorSwitchView
                                        .setItems(indoorBuildingInfo.floor_names);
                            }
                            floorSwitchView
                                    .setSeletion(indoorBuildingInfo.activeFloorName);
                            mIndoorBuildingInfo = indoorBuildingInfo;
                        }
                    });
                }
            }
        });

        //设置地图加载完成监听接口
        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            //地图加载完成后回调的方法
            public void onMapLoaded() {
                //设置为显示室内地图
                aMap.showIndoorMap(true);
                //关闭SDK自带的室内地图控件
                aMap.getUiSettings().setIndoorSwitchEnabled(false);

                /**
                 *  moveCamera(CameraUpdate update)
                 *  按照传入的CameraUpdate参数改变地图状态，
                 *  可以通过CameraUpdateFactory.zoomIn()
                 *  等方法来生成对应的CameraUpdate对象
                 */
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.91095, 116.37296), 19));
            }
        });

    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            //设置比例尺控件为可见
            aMap.getUiSettings().setScaleControlsEnabled(true);
        }
    }

    /**
     *  必须写的方法
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

    }

    /**
     *  必须写的方法
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

    }

    /**
     *  必须写的方法
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);

    }

    /**
     *  必须写的方法
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 室内地图信息
     * activeFloorIndex：当前显示的楼层
     * activeFloorName：当前显示的楼层名称
     * floor_indexs：室内地图楼层数组
     * floor_names：室内地图楼层名称数组
     * poiid：室内楼层的poiid，是室内地图的唯一标识
     */

    private class MyIndoorSwitchViewAdapter implements IndoorFloorSwitchView.OnIndoorFloorSwitchListener {

        @Override
        public void onSelected(int selectedIndex) {
            if (mIndoorBuildingInfo != null) {
                mIndoorBuildingInfo.activeFloorIndex = mIndoorBuildingInfo.floor_indexs[selectedIndex];
                mIndoorBuildingInfo.activeFloorName = mIndoorBuildingInfo.floor_names[selectedIndex];

                aMap.setIndoorBuildingInfo(mIndoorBuildingInfo);
            }
        }
    }
}
