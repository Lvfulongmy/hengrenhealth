package com.hengrunjiankang.health.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

/**
 * activity 的基类
 *
 * @author YuXiu 2016-7-14
 */
public abstract class BaseActivity extends Activity implements OnClickListener {
    protected AlertDialog progressDialog;
    protected Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
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


}
