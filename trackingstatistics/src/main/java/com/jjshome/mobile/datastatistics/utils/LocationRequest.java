package com.jjshome.mobile.datastatistics.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

/**
 * 请求获取定位信息
 * WQ on 2017/4/13
 * wq@jjshome.com
 */

public class LocationRequest {
    @SuppressWarnings("MissingPermission")
    public static LatLng getLocation(Context context) {
        if (!Common.checkPermissions(context, "android.permission.ACCESS_FINE_LOCATION") || !Common.checkPermissions(
                context, "android.permission.ACCESS_COARSE_LOCATION")) {
            return null;
        }
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
            String locationProvider = locationManager.getBestProvider(getCriteria(), true);
            if(!TextUtils.isEmpty(locationProvider)){
                Location location = locationManager.getLastKnownLocation(locationProvider);
                LatLng latLng = new LatLng();
                if (location != null) {
                    //不为空,显示地理位置经纬度
                    latLng.lat = location.getLatitude();
                    latLng.lng = location.getLongitude();
                    return latLng;
                } else {
                    return null;
                    //   locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
                }
            }else {
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 设置定位参数
     */
    private static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        //设置是否需要方位信息
        criteria.setBearingRequired(false);
        //设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    public static class LatLng {
        public double lat;
        public double lng;
    }

    private static LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.d("log", location.toString());
        }
    };
}
