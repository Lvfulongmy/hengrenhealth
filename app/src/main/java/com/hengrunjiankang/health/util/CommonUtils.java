package com.hengrunjiankang.health.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.util.Log;

import com.hengrunjiankang.health.R;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/17.
 */

public class CommonUtils {
    /**
     * 重新设置图片大小
     *
     * @param bitmap
     * @param newHeight
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int newHeight) {
        if (bitmap == null)
            return null;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Log.e("Jarvis", w + "~" + h);

        float temp = ((float) w) / ((float) h);
        int newWidth = (int) (newHeight * temp);
        float scaleWidth = ((float) newWidth) / w;
        float scaleHeight = ((float) newHeight) / h;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix,
                true);
        return resizedBitmap;
    }
    public static AlertDialog getProgressDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.AlertDialog);
        // LayoutInflater inflater = (LayoutInflater) context
        // .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // View view = inflater.inflate(R.layout.layout_progress_dialog, null);
        // builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setContentView(R.layout.layout_progress_view);
        return dialog;
    }

    public static int getStringID(String strName, Context context) {
        return context.getResources().getIdentifier(strName, "string",
                context.getPackageName());
    }
    //
    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    public static boolean checkPhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11) && isMobileNO(phoneNums)) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个字符串的位数
     *
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }


    /**
     * 验证手机号格式
     */
    public static boolean isMobileNO(String mobileNums) {
        String telRegex = "[1]\\d{10}";
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
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
