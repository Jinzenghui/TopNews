package com.example.biac.newstop;

import android.app.Application;

/**
 * Created by BIAC on 2016/9/17.
 */
public class MyApplication extends Application {

    public static MyApplication myApplication;

    public static Application getContext(){
        return myApplication;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        myApplication = this;
    }
}
