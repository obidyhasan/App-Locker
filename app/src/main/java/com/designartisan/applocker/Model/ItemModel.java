package com.designartisan.applocker.Model;

import android.graphics.drawable.Drawable;

public class ItemModel {

    String appName, packageName;
    int status;
    Drawable appIcon;

    public ItemModel(String appName, String packageName, int status, Drawable appIcon) {
        this.appName = appName;
        this.packageName = packageName;
        this.status = status;
        this.appIcon = appIcon;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
}
