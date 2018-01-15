package com.rushro2m.gaodemap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rushro2m.gaodemap.mapcreate.MapOptionActivity;
import com.rushro2m.gaodemap.mapcreate.MoreMapActivity;
import com.rushro2m.gaodemap.mapcreate.ShowMapActivity;

import com.rushro2m.gaodemap.mapcreate.indoor.RoomMapActivity;
import com.rushro2m.gaodemap.mapinteraction.AnimateCameraActivity;
import com.rushro2m.gaodemap.mapinteraction.CameraActivity;
import com.rushro2m.gaodemap.mapinteraction.EventsActivity;
import com.rushro2m.gaodemap.mapinteraction.GestureActivity;
import com.rushro2m.gaodemap.mapinteraction.LayersActivity;
import com.rushro2m.gaodemap.mapinteraction.LimitBoundsActivity;
import com.rushro2m.gaodemap.mapinteraction.LogoActivity;
import com.rushro2m.gaodemap.mapinteraction.MinMaxZoomActivity;
import com.rushro2m.gaodemap.mapinteraction.PoiClickActivity;
import com.rushro2m.gaodemap.mapinteraction.ScreenShotActivity;
import com.rushro2m.gaodemap.mapinteraction.UISettingActivity;
import com.rushro2m.gaodemap.mapinteraction.ZoomActivity;
import com.rushro2m.gaodemap.mapmarker.MarkerActivity;
import com.rushro2m.gaodemap.mapmarker.MarkerAnimationActivity;
import com.rushro2m.gaodemap.mapmarker.MarkerClickActivity;

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

            case R.id.eventsMap://Events功能
                startActivity(new Intent(this, EventsActivity.class));
                break;

            case R.id.poiClickMap://地图POI点击功能
                startActivity(new Intent(this, PoiClickActivity.class));
                break;

            case R.id.cameraMap://改变地图中心点功能
                startActivity(new Intent(this, CameraActivity.class));
                break;

            case R.id.animateMap://地图动画效果
                startActivity(new Intent(this, AnimateCameraActivity.class));
                break;

            case R.id.zoomMap://自定义缩放功能
                startActivity(new Intent(this, ZoomActivity.class));
                break;

            case R.id.screenShotMap://地图截屏功能
                startActivity(new Intent(this, ScreenShotActivity.class));
                break;

            case R.id.minMaxZoomMap://限制缩放级别功能
                startActivity(new Intent(this, MinMaxZoomActivity.class));
                break;

            case R.id.limitBoundsMap://限制显示区域功能
                startActivity(new Intent(this, LimitBoundsActivity.class));
                break;

            case R.id.markerMap://marker功能
                startActivity(new Intent(this, MarkerActivity.class));
                break;

            case R.id.markerClickMap://marker点击功能
                startActivity(new Intent(this, MarkerClickActivity.class));
                break;

            case R.id.markerAnimationMap://marker动画功能
                startActivity(new Intent(this, MarkerAnimationActivity.class));
                break;
        }
    }
}
