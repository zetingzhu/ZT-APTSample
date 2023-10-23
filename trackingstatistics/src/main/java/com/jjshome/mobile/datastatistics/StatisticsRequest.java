package com.jjshome.mobile.datastatistics;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.jjshome.mobile.datastatistics.utils.DataLog;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 新版的大数据接口请求只是请求一个0kb的图片，无返回数据，因此不用解析返回数据，节省cpu运算
 * 同时实时请求数据也不用处理ErrorListener
 * WQ on 2018/5/25
 * wq@jjshome.com
 */
public class StatisticsRequest extends Request<Integer> {
    private NetworkRequestListener mListener;

    public StatisticsRequest(String url) {
//        super(Request.Method.GET, url, null);
        super(Request.Method.GET, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (DataLog.isDebug) {
                    String result = String.format(Locale.CHINA, "error:%s", error.networkResponse);
                    DataLog.e(result);
                }
            }

        });
    }

    public StatisticsRequest(String url, NetworkRequestListener lis) {
        super(Request.Method.GET, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (DataLog.isDebug) {
                    String result = String.format(Locale.CHINA, "error:%s", error.networkResponse);
                    DataLog.e(result);
                }
            }

        });
        this.mListener = lis;
    }

    @Override
    public void cancel() {
        super.cancel();
    }

    @Override
    protected void deliverResponse(Integer response) {
        if (DataLog.isDebug) {
            String result = String.format(Locale.CHINA, "statusCode:%d url:%s", response, getUrl());
            DataLog.e(result);
        }
        if (mListener != null) {
            mListener.deliverResponse(response);
        }
    }

    @Override
    @SuppressWarnings("DefaultCharset")
    protected Response<Integer> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response.statusCode, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.putAll(DSAgent.getCommonHeaders());
        return params;
    }

    public interface NetworkRequestListener {
        void deliverResponse(Integer response);
    }
}
