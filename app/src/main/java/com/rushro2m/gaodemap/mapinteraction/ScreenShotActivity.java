package com.rushro2m.gaodemap.mapinteraction;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MarkerOptions;
import com.rushro2m.gaodemap.R;
import com.rushro2m.gaodemap.utils.Constants;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShotActivity extends AppCompatActivity
        implements AMap.OnMapScreenShotListener {

    MapView mapView;
    AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);

        mapView = findViewById(R.id.screenShotMap);
        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    /**
     * 对地图添加一个marker
     */
    private void setUpMap() {
        aMap.addMarker(new MarkerOptions().position(Constants.FANGHENG)
                .title("方恒").snippet("方恒国际中心大楼A座"));
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

    /**
     * 截屏时回调的方法
     *
     * @param bitmap
     */
    @Override
    public void onMapScreenShot(Bitmap bitmap) {

    }

    /**
     * 带有地图渲染状态的截屏回调方法
     * 根据返回的状态码，可以判断当前视图渲染是否成功。
     *
     * @param bitmap ：截屏接口返回的对象
     * @param i      ：地图渲染状态：1：地图渲染成功，0：未绘制完成
     */
    @Override
    public void onMapScreenShot(Bitmap bitmap, int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        if (bitmap == null) {
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(
                    Environment.getExternalStorageDirectory() + "/Demo"
                            + sdf.format(new Date()) + ".png");
            boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuffer buffer = new StringBuffer();
            if (b)
                buffer.append("截屏成功 ");
            else {
                buffer.append("截屏失败 ");
            }
            if (i != 0)
                buffer.append("地图渲染完成，截屏无网格");
            else {
                buffer.append("地图未渲染完成，截屏有网格");
            }
            Toast.makeText(this, buffer.toString(), Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getMap(View view) {
        switch (view.getId()) {
            case R.id.map_screenshot:
                aMap.getMapScreenShot(this);
                break;
        }
    }
}
