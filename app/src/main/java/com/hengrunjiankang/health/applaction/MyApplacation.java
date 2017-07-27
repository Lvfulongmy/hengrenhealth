package com.hengrunjiankang.health.applaction;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Administrator on 2017/7/17.
 */

public class MyApplacation extends Application {
   public static  String cookie;

   @Override
   public void onCreate() {
      super.onCreate();
      CrashReport.initCrashReport(this, "662587242e", false);
   }
}
