package com.rushro2m.gaodemap.mapsearch;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.NaviPara;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import com.rushro2m.gaodemap.R;
import com.rushro2m.gaodemap.utils.AMapUtil;
import com.rushro2m.gaodemap.utils.PoiOverlay;

import java.util.ArrayList;
import java.util.List;

public class PoiKeywordSearchActivity extends AppCompatActivity implements
        View.OnClickListener,
        TextWatcher,
        AMap.OnMarkerClickListener,
        AMap.InfoWindowAdapter,
        PoiSearch.OnPoiSearchListener,
        Inputtips.InputtipsListener {

    private AMap aMap;
    private MapView mapView;
    //输入搜索关键字
    private AutoCompleteTextView searchText;
    //输入poi关键字
    private String keyWord = "";
    //搜索时进度条
    private ProgressDialog progressDialog = null;
    //要搜索的城市
    private EditText editCity;
    //poi返回的结果
    private PoiResult poiResult;
    //当前页码，从0开始计数
    private int currentPage = 0;
    //Poi查询条件类
    private PoiSearch.Query query;
    //POI搜索
    private PoiSearch poiSearch;

    private Button search, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_keyword_search);

        mapView = findViewById(R.id.poiKeywordSearchMap);
        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    private void setUpMap() {
        search = findViewById(R.id.searchButton);
        next = findViewById(R.id.nextButton);
        searchText = findViewById(R.id.keyWord);
        editCity = findViewById(R.id.city);

        search.setOnClickListener(this);
        next.setOnClickListener(this);
        searchText.addTextChangedListener(this);
        aMap.setOnMarkerClickListener(this);
        aMap.setInfoWindowAdapter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchButton:
                searchButton();
                break;
            case R.id.nextButton:
                nextButton();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        if (!AMapUtil.IsEmptyOrNullString(newText)) {
            InputtipsQuery inputtipsQuery = new InputtipsQuery(newText, editCity.getText().toString());
            Inputtips inputtips = new Inputtips(this, inputtipsQuery);
            inputtips.setInputtipsListener(this);
            inputtips.requestInputtipsAsyn();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.poikeywordsearch_uri, null);

        TextView title = view.findViewById(R.id.title);
        title.setText(marker.getTitle());

        TextView snippet = view.findViewById(R.id.snippet);
        snippet.setText(marker.getSnippet());

        ImageButton button = view.findViewById(R.id.start_amap_app);
        //调起高德地图
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAMapNavi(marker);
            }
        });
        return view;
    }

    private void startAMapNavi(Marker marker) {
        //构造导航参数
        NaviPara naviPara = new NaviPara();
        //设置终点位置
        naviPara.setTargetPoint(marker.getPosition());
        //设置导航策略，这里是避免拥堵
        naviPara.setNaviStyle(NaviPara.DRIVING_AVOID_CONGESTION);
        try {
            AMapUtils.openAMapNavi(naviPara, getApplicationContext());
        } catch (com.amap.api.maps.AMapException e) {
            //如果没有安装进入异常，调起下载页面
            AMapUtils.getLatestAMapApp(getApplicationContext());
        }
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    public void searchButton() {
        keyWord = AMapUtil.checkEditText(searchText);
        if ("".equals(keyWord)) {
            Toast.makeText(this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
            return;
        } else {
            doSearchQuery();
        }

    }

    public void nextButton() {
        if (query != null && poiSearch != null && poiResult != null) {
            if (poiResult.getPageCount() - 1 > currentPage) {
                currentPage++;
                query.setPageNum(currentPage);
                poiSearch.searchPOIAsyn();
            } else {
                Toast.makeText(this, "未检索到相关数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在搜索：\n" + keyWord);
        progressDialog.show();
    }

    /**
     * 隐藏进度框
     */

    private void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 开始进行POI搜索
     */
    private void doSearchQuery() {
        showProgressDialog();

        currentPage = 0;
        //第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域
        query = new PoiSearch.Query(keyWord, "", editCity.getText().toString());
        //设置每页最多返回多少条poiItem
        query.setPageSize(10);
        //设置查第一页
        query.setPageNum(currentPage);

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * POI信息查询回调方法
     *
     * @param result
     * @param rCode
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dismissProgressDialog();

        if (rCode == com.amap.api.services.core.AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {
                if (result.getQuery().equals(query)) {
                    poiResult = result;
                    List<PoiItem> poiItems = poiResult.getPois();
                    List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();
                    if (poiItems != null && poiItems.size() > 0) {
                        aMap.clear();
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        Toast.makeText(this, "对不起，没有搜索到相关数据！", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "对不起，没有搜索到相关数据！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "对不起，没有搜索到相关数据！", Toast.LENGTH_SHORT).show();
        }

    }

    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称" + cities.get(i).getCityName()
                    + "城市区号" + cities.get(i).getCityCode()
                    + "城市编码：" + cities.get(i).getAdCode();
        }
        Toast.makeText(this, infomation, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onGetInputtips(List<Tip> list, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            List<String> stringList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                stringList.add(list.get(i).getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.route_inputs, stringList);

            searchText.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "rCode:" + rCode, Toast.LENGTH_SHORT).show();
        }
    }
}
