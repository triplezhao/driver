package potato.driver.ui.map;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.potato.library.net.RequestManager;
import com.potato.library.net.RequestWraper;
import com.potato.library.util.L;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import potato.driver.R;
import potato.driver.chips.base.BaseActivity;
import potato.driver.chips.util.UIUtils;
import potato.driver.data.bean.DriverBean;
import potato.driver.data.db.DriverBeanDao;
import potato.driver.data.request.DriverRequestBuilder;

/**
 * AMapV1地图中简单介绍显示定位小蓝点
 */
public class MapActivity extends BaseActivity implements LocationSource,
        AMapLocationListener {
    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    //司机实体
    DriverBean mDriver;
    @InjectView(R.id.bt_start)
    View bt_start;
    @InjectView(R.id.bt_stop)
    View bt_stop;
    //开始统计坐标,默认不统计
    public static boolean isStarted = false;
    DriverBeanDao dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.inject(this);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        init();

    }


    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        dao = new DriverBeanDao(getApplication());
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.car_y));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        CameraUpdateFactory.zoomTo(15);
        float mZoom = aMap.getCameraPosition().zoom;
        L.d("mZoom=" + mZoom);
        // aMap.setMyLocationType()
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        mDriver = dao.getLoginedDiver();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
//        mapView.onPause();
//        deactivate();
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
    public void onDestroy() {
        super.onDestroy();
        deactivate();
        mapView.onDestroy();
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                String errText = "定位成功," + amapLocation.getLatitude() + "," + amapLocation.getLongitude();
                L.d(errText);

                if (isStarted) {
                    updateLocation(amapLocation);
                }

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                L.d(errText);
            }
        }
    }

    /**
     * @param amapLocation 向服务器上报位置信息
     */
    private void updateLocation(AMapLocation amapLocation) {
        String errText = "统计坐标信息," + amapLocation.getLatitude() + "," + amapLocation.getLongitude();
        L.d(errText);
        RequestWraper requestWraper = DriverRequestBuilder.location(mDriver.getLogin_id(), (amapLocation.getLatitude() + ""), (amapLocation.getLongitude() + ""));
        RequestManager.requestData(requestWraper, RequestManager.CACHE_TYPE_NOCACHE, new RequestManager.DataLoadListener() {

            @Override
            public void onCacheLoaded(String content) {

            }

            @Override
            public void onSuccess(int statusCode, String content) {
                L.d("上传统计坐标信息成功，" + content);
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                L.d(errMsg);
            }
        });
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(1000 * 5);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    @OnClick(R.id.bt_start)
    public void clickStart(View view) {
        if (mDriver == null) {
            UIUtils.toast(mContext, "请先登录");
            return;
        }
        bt_stop.setVisibility(View.VISIBLE);
        bt_start.setVisibility(View.GONE);
        isStarted = true;
    }

    @OnClick(R.id.bt_stop)
    public void clickStop(View view) {
        bt_stop.setVisibility(View.GONE);
        bt_start.setVisibility(View.VISIBLE);
        isStarted = false;
    }
}
