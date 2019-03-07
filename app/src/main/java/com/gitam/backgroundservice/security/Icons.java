package com.gitam.backgroundservice.security;

import android.graphics.drawable.Drawable;

public class Icons {

    String packagename, lable;
    Drawable icon;

    public Icons(String packagename, String lable, Drawable icon) {
        this.packagename = packagename;
        this.lable = lable;
        this.icon = icon;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
