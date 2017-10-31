package com.example.administrator.havingdate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/19 0019.
 */

public class MapActivity extends AppCompatActivity{
   private MapView mapView;

    public LocationClient mLocationClient;

    private TextView positionText;

    private BaiduMap baiduMap;

    private boolean isFirstLocate = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_layout);
        positionText = (TextView) findViewById(R.id.location_text_view);
        mapView = (MapView) findViewById(R.id.bmapview);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(16).build()));

        /*-------------------------------------------------------------*/
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.
                PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.
                PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.
                PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!permissionList.isEmpty()){
            String[] permission = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapActivity.this,permission,1);}
        else{
            requestLocation();}
        }

    /*-------------------------------------------------------------------*/
        private void requestLocation(){
            initLocation();
            mLocationClient.start();
}
    /*-------------------------------------------------------------------*/
/*------------------复写--------------------------------------*/
     @Override
     protected void onDestroy(){
         super.onDestroy();
         mLocationClient.stop();
         mapView.onDestroy();
         baiduMap.setMyLocationEnabled(false);
     }

     @Override
     protected void onResume(){
         super.onResume();
         mapView.onResume();
     }
     @Override
     protected void onPause(){
         super.onPause();
         mapView.onPause();
     }
/*------------------------------------------------------------------------*/
/*---------------------------权限---------------------------------*/


    public void onRequestPermissionResult(int requestCode, String[] permission, int[] grantResults){

    switch (requestCode){
        case 1:
            if(grantResults.length>0){
                for(int result :  grantResults){
                    if (result!=PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"必须同意所有权限才能使用本程序",
                        Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                requestLocation();
            }
            else{
                Toast.makeText(this, "发生未知错误",Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
        default:

    }

}
/*-----------------------------------------------------------------------------------*/

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }


    private void navigateTo (BDLocation location){
        if(isFirstLocate){
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());

            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }

        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

public class MyLocationListener implements BDLocationListener{

    @Override
    public void onReceiveLocation(BDLocation location){
        if(location.getLocType() == BDLocation.TypeGpsLocation||
                location.getLocType()==BDLocation.TypeNetWorkLocation){
                  navigateTo(location);
        }


    }
}





}
