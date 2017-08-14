package com.hengrunjiankang.health.applaction;

import android.app.Application;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Administrator on 2017/7/17.
 */

public class MyApplication extends Application {
   public static  String cookie;
   public static final String packageName="com.hengrunjiankang.health";

   @Override
   public void onCreate() {
      super.onCreate();
//      CrashReport.initCrashReport(this, "662587242e", false);
      Bugly.init(getApplicationContext(), "662587242e", false);
   }
}
