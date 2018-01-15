package com.rushro2m.gaodemap.mapmarker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.rushro2m.gaodemap.R;

public class InfoWindowActivity extends AppCompatActivity implements View.OnClickListener {

    private MapView mapView;
    private AMap aMap;
    private Marker marker;
    private MarkerOptions markerOptions;
    private LatLng latlng = new LatLng(39.91746, 116.396481);
    private Button clear, reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_window);

        mapView = findViewById(R.id.infoWindowMap);
        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        clear = findViewById(R.id.marker_clear);
        reset = findViewById(R.id.marker_reset);
        clear.setOnClickListener(this);
        reset.setOnClickListener(this);

        if (aMap == null) {
            aMap = mapView.getMap();
            addMarkerToMap();
        }
    }

    private void addMarkerToMap() {
        markerOptions = new MarkerOptions()
                //设置覆盖物的位置坐标
                .position(latlng)
                //设置覆盖物的图标
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                //设置覆盖物的标题
                .title("标题")
                //设置覆盖物的文字描述
                .snippet("详细信息")
                //设置为可拖拽
                .draggable(true);
        marker = aMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.marker_clear:
                if (aMap != null) {
                    aMap.clear();
                }
                break;
            case R.id.marker_reset:
                if (aMap != null) {
                    aMap.clear();
                    addMarkerToMap();
                }
                break;

            default:
                break;
        }
    }
}
