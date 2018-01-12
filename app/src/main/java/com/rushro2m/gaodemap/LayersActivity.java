package com.rushro2m.gaodemap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyTrafficStyle;

public class LayersActivity extends AppCompatActivity implements View.OnClickListener {

    private AMap aMap;
    private MapView mapView;
    private CheckBox traffic, building, mapText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layers);

        mapView = findViewById(R.id.layersMap);
        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        traffic = findViewById(R.id.traffic);
        building = findViewById(R.id.building);
        mapText = findViewById(R.id.mapText);

        traffic.setOnClickListener(this);
        building.setOnClickListener(this);
        mapText.setOnClickListener(this);

        //自定义实施交通信息的颜色样式
        MyTrafficStyle myTrafficStyle = new MyTrafficStyle();
        //设置严重拥堵路段的标记颜色
        myTrafficStyle.setSeriousCongestedColor(0xff92000a);
        //设置拥堵路段的标记颜色
        myTrafficStyle.setCongestedColor(0xffea0312);
        //设置行驶缓慢路段的标记颜色
        myTrafficStyle.setSlowColor(0xffff7508);
        //设置行驶畅通路段的标记颜色
        myTrafficStyle.setSmoothColor(0xff00a209);
        aMap.setMyTrafficStyle(myTrafficStyle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.traffic:
                //显示实时交通状况
                aMap.setTrafficEnabled(((CheckBox) v).isChecked());
                break;
            case R.id.building:
                //显示3D楼快
                aMap.showBuildings(((CheckBox) v).isChecked());
                break;
            case R.id.mapText:
                //显示底图文字
                aMap.showMapText(((CheckBox) v).isChecked());
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
