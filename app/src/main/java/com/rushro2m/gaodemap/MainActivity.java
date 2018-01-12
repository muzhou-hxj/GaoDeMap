package com.rushro2m.gaodemap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rushro2m.gaodemap.indoor.RoomMapActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void mapMethod(View view) {
        switch (view.getId()) {

            case R.id.showMap://显示地图
                startActivity(new Intent(this, ShowMapActivity.class));
                break;

            case R.id.moreMap://多地图实例
                startActivity(new Intent(this, MoreMapActivity.class));
                break;

            case R.id.roomMap://室内地图功能
                startActivity(new Intent(this, RoomMapActivity.class));
                break;

            case R.id.optionMap://AMapOptions实现功能
                startActivity(new Intent(this, MapOptionActivity.class));
                break;

            case R.id.settingMap://UiSettings功能
                startActivity(new Intent(this, UISettingActivity.class));
                break;


            case R.id.logoMap://Logo位置
                startActivity(new Intent(this, LogoActivity.class));
                break;

            case R.id.layersMap://Layers图层功能
                startActivity(new Intent(this, LayersActivity.class));
                break;

            case R.id.gestureMap://手势交互功能
                startActivity(new Intent(this, GestureActivity.class));
                break;

            case R.id.eventsMap://手势交互功能
                startActivity(new Intent(this, EventsActivity.class));
                break;

            case R.id.poiClickMap://手势交互功能
                startActivity(new Intent(this, PoiClickActivity.class));
                break;

            case R.id.cameraMap://手势交互功能
                startActivity(new Intent(this, CameraActivity.class));
                break;

            case R.id.animateMap://手势交互功能
                startActivity(new Intent(this, AnimateCameraActivity.class));
                break;

            case R.id.zoomMap://手势交互功能
                startActivity(new Intent(this, ZoomActivity.class));
                break;
        }
    }
}
