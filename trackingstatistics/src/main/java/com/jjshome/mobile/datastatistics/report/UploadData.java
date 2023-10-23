package com.jjshome.mobile.datastatistics.report;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.jjshome.mobile.datastatistics.DSManager;
import com.jjshome.mobile.datastatistics.GlobalRequest;
import com.jjshome.mobile.datastatistics.StatisticsRequest;

/**
 * 上报数据
 * WQ on 2018/6/6
 * wq@jjshome.com
 */
public class UploadData {

    private static String TAG = UploadData.class.getSimpleName();

    public static void upload(IReport upload) {
        Log.e(TAG, "上报给服务器,upload=" + upload.getParams());
        String url = upload.getUrInfo();
        Log.e(TAG, "上报给服务器,upload url="+url);
        GlobalRequest.getInstance(DSManager.mContext).addToRequestQueue(new StatisticsRequest(url));
    }

    public static void upload(IReport upload , StatisticsRequest.NetworkRequestListener mListener) {
        Log.e(TAG, "上报给服务器,upload=" + upload.getParams());
        String url = upload.getUrInfo();
        Log.e(TAG, "上报给服务器,upload url="+url);
        GlobalRequest.getInstance(DSManager.mContext).addToRequestQueue(new StatisticsRequest(url ,mListener));
    }
}
