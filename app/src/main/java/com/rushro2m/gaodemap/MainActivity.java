package com.rushro2m.gaodemap;

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
import com.rushro2m.gaodemap.mapmarker.CustomLocationActivity;
import com.rushro2m.gaodemap.mapmarker.InfoWindowActivity;
import com.rushro2m.gaodemap.mapmarker.LocationModeSourceActivity;
import com.rushro2m.gaodemap.mapmarker.LocationModeSourceActivity_Old;
import com.rushro2m.gaodemap.mapmarker.MarkerActivity;
import com.rushro2m.gaodemap.mapmarker.MarkerAnimationActivity;
import com.rushro2m.gaodemap.mapmarker.MarkerClickActivity;
import com.rushro2m.gaodemap.mapsearch.PoiKeywordSearchActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void checkPermissions(String... permissions) {
        //获取权限列表
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            //list.toarray将集合转化为数组
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        //for (循环变量类型 循环变量名称 : 要被遍历的对象)
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {      //没有授权
                showMissingPermissionDialog();              //显示提示信息
                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");
        builder.setMessage("Message");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
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

            case R.id.infoWindowMap://infoWindow功能
                startActivity(new Intent(this, InfoWindowActivity.class));
                break;

            case R.id.locationOldMap://5.0.0之前的Location实现
                startActivity(new Intent(this, LocationModeSourceActivity_Old.class));
                break;

            case R.id.locationMap://5.0.0之后的Location实现
                startActivity(new Intent(this, LocationModeSourceActivity.class));
                break;

            case R.id.customLocationMap://Location小蓝点自定义功能
                startActivity(new Intent(this, CustomLocationActivity.class));
                break;


//---------------------------------------------------------------------------------------------------------
            case R.id.poiKeywordSearchMap://Location小蓝点自定义功能
                startActivity(new Intent(this, PoiKeywordSearchActivity.class));
                break;
        }
    }
}
