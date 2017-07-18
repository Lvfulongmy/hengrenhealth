package com.hengrunjiankang.health.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/17.
 */

public class CommonUtils {
    public static int getStringID(String strName, Context context) {
        return context.getResources().getIdentifier(strName, "string",
                context.getPackageName());
    }
    /**
     * dp转px方法
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp方法
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    public static void showButtonDialog(Context context, String message,
                                        String title, HashMap<String, DialogInterface.OnClickListener> map) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        boolean bool = true;
        boolean bool2=true;
        for (String key : map.keySet()){
            if (bool) {
                builder.setPositiveButton(key, map.get(key));
                bool = false;
            } else if(bool2){
                builder.setNegativeButton(key, map.get(key));
                bool2=false;
            }else{
                builder.setNeutralButton(key, map.get(key));
            }
        }
        builder.setCancelable(false);
        builder.create().show();
    }
}
