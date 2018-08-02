package com.xianglian.love;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


import com.xianglian.love.utils.Trace;

import java.util.List;

import base.BaseApplication;

/**
 * Created by wl on 17/12/25.
 * 定位
 */

public class UserLocation {

    private static final String TAG = "locationUtils";

    /**
     *
     * 更新经纬度，逗号隔开，保存到share，eg: 23,117
     */
    public static void updateLocation() {
        if (BaseApplication.baseApplication == null) {
            Trace.d(TAG, "Context is null");
            return;
        }
        String locationProvider = null;
        //1.获取位置管理器
        LocationManager locationManager = (LocationManager) BaseApplication.baseApplication.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return;
        }
        try {
            //2.获取位置提供器，GPS或是NetWork
            List<String> providers = locationManager.getProviders(true);
            if (providers == null || providers.isEmpty()) {
                AppService.startDeviceInfo(BaseApplication.baseApplication);
                return;
            }
            if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                //如果是网络定位
                locationProvider = LocationManager.NETWORK_PROVIDER;
            } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
                //如果是GPS定位
                locationProvider = LocationManager.GPS_PROVIDER;
            } else if (providers.contains(LocationManager.PASSIVE_PROVIDER)) {
                locationProvider = LocationManager.PASSIVE_PROVIDER;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (locationProvider == null) return;
        Location location = null;

        try {
            //3.获取上次的位置，一般第一次运行，此值为null
            location = locationManager.getLastKnownLocation(locationProvider);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (location != null) {
//            DQDAds.saveLocation(dealLocation(location));
            AppService.startDeviceInfo(BaseApplication.baseApplication, dealLocation(location));
        } else {
            // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
            locationManager.requestLocationUpdates(locationProvider, 0, 0, new MyLocationListener());
        }
    }

    private static String dealLocation(Location location) {
        String address = location.getLongitude() + "," + location.getLatitude();
        Trace.d("TAG", "===address===" + address);
        return address;
    }

    private static class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            AppService.startDeviceInfo(BaseApplication.baseApplication, dealLocation(location));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }



}
