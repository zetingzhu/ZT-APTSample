package com.jjshome.mobile.datastatistics.marquee;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewInfoEntity implements Serializable {
    public Bitmap screenshotUri;
    public Bitmap viewUri;

    public Bitmap uploadUri;

    public String page;
    public String path;
    public String index;
    public String obj;
    public int width;
    public int height;
    public ArrayList<String> position;
    public int[] location;
}
