package com.jjshome.mobile.datastatistics.marquee;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;

public class FrameInfo {

    String page;

    String path;

    int width;

    int height;

    ArrayList<String> position;

    int[] location;

    FrameInfo(){}

    Activity activity;
    View view;

    FrameInfo(String page, String path, int width, int height, ArrayList<String> position, int[] location,Activity activity,View view) {
        this.page = page;
        this.path = path;
        this.width = width;
        this.height = height;
        this.position = position;
        this.location = location;
        this.activity=activity;
        this.view=view;
    }



    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ArrayList<String> getPosition() {
        return position;
    }

    public void setPosition(ArrayList<String> position) {
        this.position = position;
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }
}
