package com.jjshome.mobile.datastatistics.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jjshome.mobile.datastatistics.DSManager;
import com.jjshome.mobile.datastatistics.GlobalRequest;
import com.jjshome.mobile.datastatistics.luban.Luban;
import com.jjshome.mobile.datastatistics.luban.OnCompressListener;
//import com.qiniu.android.http.ResponseInfo;
//import com.qiniu.android.storage.UpCompletionHandler;
//import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * 上传图片至七牛云存储
 * WQ on 2016/1/16
 * wq@jjshome.com
 */
public class UploadUtils {
//    /**
//     * 上传监听器
//     */
//    public interface UploadListener {
//        /**
//         * 服务器请求失败
//         */
//        void serverError();
//
//        /**
//         * 没获取到token值
//         */
//        void noToken();
//
//        /**
//         * 上传成功获取到url
//         *
//         * @param url
//         */
//        void complete(File file, String url);
//
//        /**
//         * 上传失败
//         */
//        void error();
//    }
//
//    /**
//     * 上传七牛云存储
//     *
//     * @param isSync   是否同步方式获取数据
//     * @param name     一般传null
//     * @param listener 回调监听器
//     */
//    public static void getUploadUrl(final Context context, final boolean isSync, final File filepath, final String name, final UploadListener listener) {
//        Luban.get(context)
//                .load(filepath)                     //传人要压缩的图片
//                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
//                .setCompressListener(new OnCompressListener() { //设置回调
//
//                    @Override
//                    public void onStart() {
//                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                    }
//
//                    @Override
//                    public void onSuccess(final File file) {
//                        // TODO 压缩成功后调用，返回压缩后的图片文件
//                        Map<String, String> map = new HashMap<>();
//                        String url = Common.getBaseApi() + "analysis/marquee/buildToken";
//                        GlobalRequest.getInstance(DSManager.mContext).addToRequestQueue(
//                                new StringRequest(Request.Method.GET,
//                                        url,
//                                        new Response.Listener<String>() {
//                                            @Override
//                                            public void onResponse(String response) {
//
//                                                if (!TextUtils.isEmpty(response)) {
//
//                                                    try {
//                                                        JSONObject jsonObject = JSON.parseObject(response);
//                                                        boolean success = jsonObject.getBoolean("success");
//                                                        String errorInfo = jsonObject.getString("errorInfo");
//                                                        if (success) {
//                                                            String token = jsonObject.getJSONObject("data").getString("token");
//                                                            final String qiniuDomain = jsonObject.getJSONObject("data").getString("qiniuDomain");
//                                                            if (!TextUtils.isEmpty(token)) {
//                                                                UploadManager uploadManager = new UploadManager();
//                                                                uploadManager.put(file, name, token,
//                                                                        new UpCompletionHandler() {
//                                                                            @Override
//                                                                            public void complete(String s, ResponseInfo responseInfo, org.json.JSONObject jsonObject) {
//                                                                                String url = null;
//                                                                                try {
//                                                                                    if (jsonObject != null) {
//                                                                                        url = jsonObject.getString("key");
//                                                                                    }
//                                                                                } catch (JSONException e) {
//                                                                                    e.printStackTrace();
//                                                                                    listener.error();
//                                                                                }
//                                                                                if (TextUtils.isEmpty(url)) {
//                                                                                    listener.error();
//                                                                                } else {
//                                                                                    listener.complete(file, qiniuDomain + url);
//                                                                                }
//
//                                                                            }
//                                                                        }, null);
//                                                            } else {
//                                                                listener.noToken();
//                                                            }
//                                                        } else {
//                                                            listener.noToken();
////                                                            Toast.makeText(context, errorInfo, Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    } catch (com.alibaba.fastjson.JSONException e) {
//                                                        e.printStackTrace();
//                                                        listener.error();
//                                                    }
//
//                                                } else {
//                                                    listener.error();
////                                                    Toast.makeText(context, "参数错误", Toast.LENGTH_SHORT).show();
//                                                }
//
//                                            }
//                                        },
//                                        new Response.ErrorListener() {
//                                            @Override
//                                            public void onErrorResponse(VolleyError error) {
//                                                listener.serverError();
//                                                Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
//                                            }
//                                        })
//                        );
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        // TODO 当压缩过去出现问题时调用
//                        listener.error();
//                    }
//                }).launch();    //启动压缩
//    }
//
//    /**
//     * 异步方式上传七牛云存储
//     *
//     * @param file     上传的文件
//     * @param name     一般传null
//     * @param listener 回调监听器
//     */
//    public static void getUploadUrl(Context context, final File file, final String name, final UploadListener listener) {
//        getUploadUrl(context, false, file, name, listener);
//    }


}
