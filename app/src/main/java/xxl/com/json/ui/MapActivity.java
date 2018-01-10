package xxl.com.json.ui;

import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

import java.util.ArrayList;
import java.util.List;

import xxl.com.json.R;

public class MapActivity extends BaseActivity implements AMap.OnMyLocationChangeListener, AMap.OnMarkerClickListener {

    private MapView mMapView;
    //初始化地图控制器对象
    private AMap aMap;

    //marker金纬度集合
    private List<LatLng> latLngs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
        initData();

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
    public void onMyLocationChange(Location location) {//定位金纬度回调
        Log.e("aaa", "onMyLocationChange: " + "latitude==" + location.getLatitude() + "---longitude" + location.getLongitude());
    }

    @Override
    public boolean onMarkerClick(Marker marker) {//marker覆盖物点击事件
        MarkerOptions options = marker.getOptions();
        LatLng latLng = options.getPosition();
        double longitude = latLng.longitude;//经度
        double latitude = latLng.latitude;//纬度

        marker.setTitle("longitude:" + longitude + " latitude:" + latitude);//marker上显示金纬度

        return false;
    }
}
