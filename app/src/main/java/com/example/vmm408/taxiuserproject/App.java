package com.example.vmm408.taxiuserproject;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context appBaseContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appBaseContext = this;
    }

    public static Context getAppBaseContext() {
        return appBaseContext;
    }
}
