package com.hengrunjiankang.health.applaction;

import android.app.Application;

import com.hengrunjiankang.health.util.StorageUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

/**
 * Created by Administrator on 2017/7/17.
 */

public class MyApplication extends Application {
   public static  String cookie;
   public static final String packageName="com.hengrunjiankang.health";
   public static File cacheDir;
   public static int ScreenWidth;
   @Override
   public void onCreate() {
      super.onCreate();
//      CrashReport.initCrashReport(this, "662587242e", false);
      Bugly.init(getApplicationContext(), "662587242e", false);
      if (cacheDir == null)
         cacheDir = StorageUtils.getOwnCacheDirectory(
                 getApplicationContext(), "HengruiHealth/cache");
   }
}
