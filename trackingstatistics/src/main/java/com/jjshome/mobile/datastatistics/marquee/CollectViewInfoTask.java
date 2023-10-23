package com.jjshome.mobile.datastatistics.marquee;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import android.util.Log;
import android.view.View;

import com.jjshome.mobile.datastatistics.utils.Common;
import com.jjshome.mobile.datastatistics.utils.FileUtil;
import com.jjshome.mobile.datastatistics.utils.ViewUtil;

import java.io.File;

public class CollectViewInfoTask extends AsyncTask<Bitmap, Void, ViewInfoEntity> {

    private static final String TAG = CollectViewInfoTask.class.getSimpleName();

    private static final String SCREENSHOT_DIRECTORY = "/datastatic";

    private final Activity activity;
    private final Callback callback;
    private final View selectView;

    public CollectViewInfoTask(@NonNull Activity activity, View selectView, @NonNull Callback callback) {
        this.activity = activity;
        this.callback = callback;
        this.selectView = selectView;
    }

    @Override
    protected ViewInfoEntity doInBackground(Bitmap... params) {
        String screenshotDirectoryRoot = Common.getScreenshotDirectoryRoot(activity);
        if (screenshotDirectoryRoot == null) {
            return null;
        }
        Log.d("=====2.5",System.currentTimeMillis()+"");
        ViewInfoEntity result = new ViewInfoEntity();
        File screenshotDirectory = new File(screenshotDirectoryRoot);
        if (screenshotDirectory.exists()) {
            File[] oldScreenshots = screenshotDirectory.listFiles();
            for (File oldScreenshot : oldScreenshots) {
                if (!oldScreenshot.delete()) {
                    Log.e(TAG, "Could not delete old screenshot:" + oldScreenshot);
                }
            }
        }
//        activity.getWindow().getDecorView()
        Uri screenshotUri = null;
        Bitmap bitmapScreen = params != null && params.length != 0 ? params[0] : null;
        if (bitmapScreen != null) {
            File screenshotFile = FileUtil.writeBitmapToDirectory(bitmapScreen, screenshotDirectory);
            screenshotUri = Uri.fromFile(screenshotFile);
//            result.screenshotUri = screenshotUri;


        }
        Uri viewUri = null;
        Bitmap bitmapView = params != null && params.length > 1 ? params[1] : null;
        if (bitmapView != null) {
            File viewFile = FileUtil.writeBitmapToDirectory(bitmapView, screenshotDirectory);
            viewUri = Uri.fromFile(viewFile);
//            result.viewUri = viewUri;
        }

        Uri uploadUri = null;
        Bitmap bitmapuploadUri = params != null && params.length > 2 ? params[2] : null;
        if (bitmapuploadUri != null) {
            File viewFile = FileUtil.writeBitmapToDirectory(bitmapuploadUri, screenshotDirectory);
            uploadUri = Uri.fromFile(viewFile);
//            result.uploadUri = uploadUri;
        }
        String obj = ViewUtil.getViewText(selectView);
        result.obj = obj;
        return result;
    }

    @Override
    protected void onPostExecute(ViewInfoEntity result) {
        super.onPostExecute(result);
        callback.onDataReady(result);
    }

    @Nullable
    @WorkerThread
    private static String getScreenshotDirectoryRoot(@NonNull Context context) {
        File filesDir = context.getFilesDir();
        if (filesDir == null) {
            return null;
        }
        return filesDir.getAbsolutePath() + SCREENSHOT_DIRECTORY;
    }

    public interface Callback {
        void onDataReady(@Nullable ViewInfoEntity result);
    }
}
