package com.thebiggestsaver;

import android.app.Application;

import com.thebiggestsaver.utils.MyVolley;

/**
 * Created by patriciaestridge on 9/22/14.
 */
public class Dimes extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        MyVolley.init(getApplicationContext());
    }
}
