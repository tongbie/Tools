package com.example.myapplication;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.myapplication.UI.BackActivity;
import com.example.myapplication.UI.SlipBackClass;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MapActivity extends BackActivity {
    public LocationClient mLocationClient;
    private TextView positionText;
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        /* 设置右滑退出 */
        SlipBackClass slideBackLayout;
        slideBackLayout = new SlipBackClass(this);
        slideBackLayout.bind();
        /* 设置右滑退出 */
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        setTitleName("地图");
        setWindowColor("#fafafa");
        setTitleBackground(R.drawable.map_bg);
        mapView=(MapView)findViewById(R.id.bmapView) ;
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        Button renovate=(Button)findViewById(R.id.renotive);
        renovate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLocation();
            }
        });
        positionText=(TextView)findViewById(R.id.position_text_view);
        Button backButton=(Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(MapActivity.this, ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MapActivity.this, READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MapActivity.this, WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapActivity.this,permissions,1);
        }else{
            requestLocation();
        }
    }

    private void navigeteTo(BDLocation location){
        //if(isFirstLocate){
            LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(ll);
            if(isFirstLocate) {
                Toast.makeText(this, ll.toString(), Toast.LENGTH_SHORT).show();
            }
            baiduMap.animateMapStatus(update);
            //update=MapStatusUpdateFactory.zoomTo(16f);
            //baiduMap.animateMapStatus(update);
            isFirstLocate=false;
        //}
        MyLocationData.Builder locationBuilder=new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData=locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    public void requestLocation(){
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setScanSpan(5000);
        //option.setScanSpan(LocationClientOption.MIN_SCAN_SPAN);
        //option.setLocationNotify(false);    //回調的時候會按照設置的span值來定時定位
        option.setIsNeedAddress(true);    //获取详细地址信息
        //option.setLocationNotify(true);    //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int result:grantResults){
                        if(result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else{
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {

    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition=new StringBuilder();
                    //currentPosition.append("维度: ").append(location.getLatitude()).append("\n");
                    //currentPosition.append("经线: ").append(location.getLongitude()).append("\n");
                    //currentPosition.append("国家: ").append(location.getCountry()).append("\n");
                    /*currentPosition.append("省: ").append(location.getProvince()).append("\n");
                    currentPosition.append("市: ").append(location.getCity()).append("\n");
                    currentPosition.append("区: ").append(location.getDistrict()).append("\n");
                    currentPosition.append("街道: ").append(location.getStreet()).append("\n");
                    currentPosition.append("定位方式: ");*/
                    currentPosition.append(location.getProvince()).append(".").append(location.getCity()).append(".").append(location.getDistrict()).append(".").append(location.getStreet()).append(" - ").append("by:");
                    if(location.getLocType()== BDLocation.TypeGpsLocation){
                        currentPosition.append("GPS");
                    }else if(location.getLocType()== BDLocation.TypeNetWorkLocation){
                        currentPosition.append("网络");
                    }
                    if(location.getLocType()== BDLocation.TypeGpsLocation||location.getLocType()== BDLocation.TypeNetWorkLocation){
                        navigeteTo(location);
                    }
                    positionText.setText(currentPosition);
                }
            });
        }

        /*@Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation.getLocType()==BDLocation.TypeGpsLocation||bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
                navigeteTo(bdLocation);
            }
        }*/
    }
}
