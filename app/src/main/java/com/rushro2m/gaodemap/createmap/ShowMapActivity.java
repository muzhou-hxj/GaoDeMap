package com.rushro2m.gaodemap.createmap;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.rushro2m.gaodemap.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ShowMapActivity extends AppCompatActivity implements View.OnClickListener {
    MapView mMapView = null;
    AMap aMap = null;

    private Button basicmap;
    private Button wxmap;
    private Button nightmap;
    private Button navimap;

    private CheckBox mStyleCheckbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);

        /**
         *  设置离线地图存储目录，在下载离线地图或初始化地图设置
         *  使用过程中可自行设置，若自行设置了离线地图存储的路径
         *  则需要在离线地图下载和使用地图页面都进行路径设置
         */
        mMapView = findViewById(R.id.map);
        //当前Activity唤醒时调用地图唤醒
        mMapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        setMapCustomStyleFile(this);

        //对控件进行初始化
        basicmap = findViewById(R.id.basicmap);
        wxmap = findViewById(R.id.wxmap);
        nightmap = findViewById(R.id.nightmap);
        navimap = findViewById(R.id.navimap);
        mStyleCheckbox = findViewById(R.id.check_style);

        //绑定点击事件
        basicmap.setOnClickListener(this);
        wxmap.setOnClickListener(this);
        nightmap.setOnClickListener(this);
        navimap.setOnClickListener(this);

        mStyleCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                aMap.setMapCustomEnable(isChecked);
            }
        });
    }

    //下载离线地图
    private void setMapCustomStyleFile(Context context) {
        String styleName = "style_json.json";
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String filePath = null;

        try {
            inputStream = context.getAssets().open(styleName);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            filePath = context.getFilesDir().getAbsolutePath();
            File file = new File(filePath + "/" + styleName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            outputStream.write(b);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        aMap.setCustomMapStylePath(filePath + "/" + styleName);
        aMap.showMapText(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    /**
     * 几种地图模式，通过setMapType来设置，可以写成常量
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.basicmap:
                //普通地图
//                aMap.setMapType(1);
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);

                break;
            case R.id.wxmap:
                //卫星地图
//                aMap.setMapType(2);
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);

                break;
            case R.id.nightmap:
//                aMap.setMapType(3);
                aMap.setMapType(AMap.MAP_TYPE_NIGHT);

                break;
            case R.id.navimap:
//                aMap.setMapType(4);
                aMap.setMapType(AMap.MAP_TYPE_NAVI);
                break;
        }
        mStyleCheckbox.setChecked(false);
    }
}
