package com.hengrunjiankang.health.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.hengrunjiankang.health.R;

/**
 * activity 的基类
 *
 * @author YuXiu 2016-7-14
 */
public abstract class BaseFramentActivity extends FragmentActivity implements OnClickListener {
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
    protected void initTitle(String str) {
        ((TextView) findViewById(R.id.title_text)).setText(str);
        findViewById(R.id.title_back).setOnClickListener(this);
    }


}
