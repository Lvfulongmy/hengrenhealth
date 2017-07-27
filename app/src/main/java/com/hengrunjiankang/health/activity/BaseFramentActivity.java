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
import android.view.WindowManager;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.util.CommonUtils;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if(Build.VERSION.SDK_INT>=21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        findView();
        createObject();
        setListener();
        getData();
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
