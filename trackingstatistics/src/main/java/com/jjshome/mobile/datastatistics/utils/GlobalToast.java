package com.jjshome.mobile.datastatistics.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class GlobalToast {

    private static Toast toast = null;

    @SuppressLint("ShowToast")
    public static void show(Context context,CharSequence text, int toastDuration){
        try{
            toast.getView().isShown();
            toast.setText(text);
        } catch (Exception e) {
            toast = Toast.makeText(context, text, toastDuration);
        }
        toast.show();
    }

    public static void show(Context context,CharSequence text) {
        show(context,text, Toast.LENGTH_SHORT);
    }
}
