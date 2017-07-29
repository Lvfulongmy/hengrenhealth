package com.hengrunjiankang.health.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.activity.LoginActivity;
import com.hengrunjiankang.health.activity.MyAccountActivity;
import com.hengrunjiankang.health.activity.ResetpwdActivity;
import com.hengrunjiankang.health.activity.WebViewActivity;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.util.CommonUtils;

/**
 * Created by Administrator on 2017/7/27.
 */

public class MyFragment extends BaseFragment {
    private LinearLayout llAccount;
    private LinearLayout llResetPwd;
    private LinearLayout llHelp;
    private TextView tvQuit;

    @Override
    protected int setLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void findView(View view) {
        llAccount = (LinearLayout) view.findViewById(R.id.ll_my_account_info);
        llResetPwd = (LinearLayout) view.findViewById(R.id.ll_my_account_reset_pwd);
        llHelp = (LinearLayout) view.findViewById(R.id.ll_my_account_help);
        tvQuit = (TextView) view.findViewById(R.id.tv_my_quit);
    }

    @Override
    protected void createObject() {

    }

    @Override
    protected void setListener() {
        llAccount.setOnClickListener(this);
        llResetPwd.setOnClickListener(this);
        llHelp.setOnClickListener(this);
        tvQuit.setOnClickListener(this);
    }

    @Override
    protected void getData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_my_account_info:
                startActivity(new Intent(mActivity, MyAccountActivity.class));
                break;
            case R.id.ll_my_account_reset_pwd:
                startActivity(new Intent(mActivity, ResetpwdActivity.class));
                break;
            case R.id.ll_my_account_help:
                Intent intent = new Intent(mActivity, WebViewActivity.class);
                intent.putExtra("title", "用户帮助");
                intent.putExtra("url", UrlObject.USERHELPURL);
                startActivity(intent);

                break;
            case R.id.tv_my_quit:
                startActivity(new Intent(mActivity, LoginActivity.class));
                mActivity.finish();
                break;

        }
        mActivity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);

    }
}
