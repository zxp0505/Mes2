package cn.com.ethank.mylibrary.resourcelibrary.application;

import android.app.Application;
import android.content.Context;

public abstract class DefaultApplication extends Application {

    public static DefaultApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static DefaultApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public abstract void exit() ;

}
