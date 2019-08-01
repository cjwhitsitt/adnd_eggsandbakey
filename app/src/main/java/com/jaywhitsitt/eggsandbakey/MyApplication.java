package com.jaywhitsitt.eggsandbakey;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication singleton;
    public static MyApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        singleton = this;
        super.onCreate();
    }

}
