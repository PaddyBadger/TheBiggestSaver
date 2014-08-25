package com.thebiggestsaver.models;

import android.graphics.Bitmap;

/**
 * Created by patriciaestridge on 7/21/14.
 */
public class Savings {
    private String title;
    private Bitmap icon;

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setIcon(Bitmap icon)
    {
        this.icon = icon;
    }

    public Bitmap getIcon()
    {
        return icon;
    }
}
