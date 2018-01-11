package xxl.com.json.ui;

import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;

import java.util.ArrayList;
import java.util.List;

import xxl.com.json.R;

public class MapActivity extends BaseActivity implements View.OnClickListener,
        AMap.OnMyLocationChangeListener, AMap.OnMarkerClickListener,
        PoiSearch.OnPoiSearchListener,WeatherSearch.OnWeatherSearchListener {

    private MapView mMapView;
    //初始化地图控制器对象
    private AMap aMap;

    //marker金纬度集合
    private List<LatLng> latLngs = new ArrayList<>();
    private EditText mEtSearch;
    private Button mBtnSearch;
    private WeatherSearchQuery mWeatherSearchQuery;
    private WeatherSearch mWeatherSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
        initData();
        initEvent();

        initMap(savedInstanceState);
        initLocation();
        initMarker();
    }

    /**
     * 初始化标记物
     */
    private void initMarker() {

        MarkerOptions markerOption = new MarkerOptions();
        for (LatLng latLng : latLngs) {
            markerOption.position(latLng);
            //markerOption.title("某某地方").snippet("某某景区");
            markerOption.draggable(true);//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.mipmap.ic_launcher)));
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(true);//设置marker平贴地图效果
            aMap.addMarker(markerOption);
        }

        aMap.setOnMarkerClickListener(this);
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        aMap.setOnMyLocationChangeListener(this);//金纬度回调监听
    }

    /**
     * 初始化地图显示
     *
     * @param savedInstanceState
     */
    private void initMap(Bundle savedInstanceState) {
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
    }

    private void initEvent() {
        mBtnSearch.setOnClickListener(this);
    }


    private void initData() {
        latLngs.add(new LatLng(26.57, 106.71));//贵阳
        latLngs.add(new LatLng(26.58, 104.82));//六盘水
        latLngs.add(new LatLng(27.7, 106.9));//遵义
        latLngs.add(new LatLng(27.73, 109.21));//铜仁
        latLngs.add(new LatLng(27.76, 107.5));//湄潭
        latLngs.add(new LatLng(27.24, 108.91));//玉屏
        latLngs.add(new LatLng(26.66, 105.76));//织金

    }


    private void initView() {
        mMapView = (MapView) findViewById(R.id.mapView);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mBtnSearch = (Button) findViewById(R.id.btn_search);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                String keyWord = mEtSearch.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    Toast.makeText(this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    search(keyWord, null);
                }
                break;
        }
    }


    /**
     * 搜索
     */
    private void search(String keyWord, String cityCode) {
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", cityCode);
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码

        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);

        poiSearch.searchPOIAsyn();
    }


    @Override
    public void onMyLocationChange(Location location) {//定位金纬度回调
        Log.e("aaa", "onMyLocationChange: " + "latitude==" + location.getLatitude() + "---longitude" + location.getLongitude());
    }

    /**
     * 删除所有marker
     */
    private void deleteMarker(){
        List<Marker> markers = aMap.getMapScreenMarkers();
        for (int i = 0; i < markers.size(); i++) {
            Marker marker = markers.get(i);
            marker.remove();
        }
        mMapView.invalidate();//地图重绘
    }

    @Override
    public boolean onMarkerClick(Marker marker) {//marker覆盖物点击事件
        MarkerOptions options = marker.getOptions();
        LatLng latLng = options.getPosition();
        double longitude = latLng.longitude;//经度
        double latitude = latLng.latitude;//纬度
        wearthSearch(marker.getTitle());
        marker.setTitle("longitude:" + longitude + " latitude:" + latitude);//marker上显示金纬度
        return false;
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        deleteMarker();
        //解析result获取POI信息
        ArrayList<PoiItem> pois = poiResult.getPois();
        MarkerOptions markerOptions = new MarkerOptions();
        for (PoiItem poiItem : pois) {
            LatLonPoint latLonPoint = poiItem.getLatLonPoint();
            LatLng latLng = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
            markerOptions.position(latLng);
            markerOptions.title(poiItem.getCityName());
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.mipmap.ic_launcher)));
            markerOptions.draggable(false);
            aMap.addMarker(markerOptions);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }


    /**
     * 天气查询
     */
    private void wearthSearch(String city) {
        //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
        mWeatherSearchQuery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        mWeatherSearch = new WeatherSearch(this);
        mWeatherSearch.setOnWeatherSearchListener(this);
        mWeatherSearch.setQuery(mWeatherSearchQuery);
        mWeatherSearch.searchWeatherAsyn(); //异步搜索
    }


    /**
     * 实时天气查询回调
     */
    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {//天气查询
        if (rCode == 1000) {
            if (weatherLiveResult != null&&weatherLiveResult.getLiveResult() != null) {
                LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                Toast.makeText(this, weatherlive.getCity()+weatherlive.getReportTime()+"发布"+weatherlive.getWeather()+weatherlive.getTemperature()+weatherlive.getWindDirection()+"风     "+weatherlive.getWindPower()+"级", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "noResult", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "erro"+rCode, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }
}
