package com.hengrunjiankang.health.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.util.CommonUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * activity 的基类
 *
 * @author YuXiu 2016-7-14
 */
public abstract class BaseFramentActivity extends FragmentActivity implements OnClickListener {
    protected AlertDialog progressView;
    protected  int progressCount=0;
    protected Intent intent;
    protected int screenHeight;// 屏幕高度
    protected int screenWidth;// 屏幕宽度

    /**
     * 获取屏幕宽度高度
     */
    protected void getScreenParam() {
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        screenWidth = dm.widthPixels;

        screenHeight = dm.heightPixels;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//        if(Build.VERSION.SDK_INT>=21) {
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        setStatusBarDarkMode(true,this);
        findView();
        createObject();
        setListener();
        getData();
    }
    public void setStatusBarDarkMode(boolean darkmode, Activity activity) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean setStatusBarDarkIcon(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
//                Log.e("MeiZu", "setStatusBarDarkIcon: failed");
            }
        }
        return result;
    }


    // 抽象方法，用于子类覆盖

    /**
     * 设置layout布局文件
     */
    abstract protected int setLayout();

    /**
     * 查找页面组件
     */
    abstract protected void findView();

    /**
     * 创建对象
     */
    abstract protected void createObject();

    /**
     * 给组件设置点击事件
     */
    abstract protected void setListener();

    /**
     * 给页面添加数据
     */
    abstract protected void getData();



    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
    protected void initTitle(String str) {
        ((TextView) findViewById(R.id.title_text)).setText(str);
        findViewById(R.id.title_back).setOnClickListener(this);
    }
    protected  void initTitleString(String str){
        ((TextView) findViewById(R.id.title_text)).setText(str);
    }
    protected void showPDialog(){
        progressCount++;
        if(progressView==null) {
            progressView = CommonUtils.getProgressDialog(this);
        }
        else{
            progressView.show();
        }

    }
    protected void dismissPDialog(){
        progressCount--;
        if(progressCount<=0) {
            if (progressView != null) {
                progressView.dismiss();
                progressCount=0;
            }
        }
    }
}
