package com.winter.library.utils;

import android.app.Application;

public class App {
    public static Application getApp(){
        Application sApplication = null;
        try{
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            sApplication  = (Application) activityThread.getMethod("getApplication").invoke(thread);
        }catch (Exception e){
            e.printStackTrace();
        }
        return sApplication;
    }
}
