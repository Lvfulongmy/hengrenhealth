package com.hengrunjiankang.health.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.adapter.WelPagerAdapter;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.util.CommonUtils;


/**
 * 欢迎页面
 *
 * @author YuXiu 2016-7-14
 */
public class SplashActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGHT = 2000;// 跳转延时
    private SharedPreferences share;// 用于存储用户相关信息
    private RelativeLayout rlS;
    private ViewPager vp;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    protected int setLayout() {
        // TODO Auto-generated method stub
        // if (getIntent().getAction().equals("android.intent.action.VIEW"))
        // finish();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    protected void findView() {
        // TODO Auto-generated method stub
        rlS = (RelativeLayout) findViewById(R.id.layout_wel);
        vp = (ViewPager) findViewById(R.id.view_pager);

    }

    @Override
    protected void createObject() {
        // TODO Auto-generated method stub
//		Intent intent=new Intent(SplashActivity.this, MsgInfoService.class);
//		intent.setFlags(Service.START_STICKY);
//		startService(intent);
    }

    @Override
    protected void setListener() {
        // TODO Auto-generated method stub

    }

    private int getAppVersionCode(Context context) {
        int versionCode = -1;
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            versionCode = info.versionCode;
        } catch (NameNotFoundException e) {
        }
        return versionCode;
    }

    @Override
    protected void getData() {
        getScreenParam();
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            SplashActivity.this.finish();
            return;
        }
        int code = getAppVersionCode(getApplicationContext());
        share = getSharedPreferences("first", 0);
        boolean isFirst = share.getBoolean("first" + code, true);

        // 延时跳转到主页面
        if (isFirst) {
            initViewPager();
        } else {
            rlS.setVisibility(View.VISIBLE);

            if (CommonUtils.getUserShare(this).getString("username", null) != null) {
                login();
            } else {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }

                }, SPLASH_DISPLAY_LENGHT);
            }


        }


    }

    private void login() {
        showPDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("MobileNumber", CommonUtils.getUserShare(this).getString("username", ""));
        params.put("Password", CommonUtils.getUserShare(this).getString("pwd", ""));

        new CommonHttp(this, new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                dismissPDialog();
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
//				overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
            }

            @Override
            public void requestFail(String msg) {
                dismissPDialog();
                Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(SplashActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                dismissPDialog();
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }

        }).doRequest(UrlObject.LOGINURL, params);
    }

    private ArrayList<View> list;

    private void initViewPager() {
        vp.setVisibility(View.VISIBLE);
        list = new ArrayList<View>();
        View v1 = getLayoutInflater().inflate(R.layout.layout_wel_1, null);
        View v2 = getLayoutInflater().inflate(R.layout.layout_wel_2, null);
        View v3 = getLayoutInflater().inflate(R.layout.layout_wel_3, null);
        Button iv = (Button) v3.findViewById(R.id.iv_wel_start);
        iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int code = getAppVersionCode(getApplicationContext());
                share.edit().putBoolean("first" + code, false).commit();
                Intent intent = new Intent(SplashActivity.this,
                        LoginActivity.class);
                startActivity(intent);

                finish();
            }
        });
        list.add(v1);
        list.add(v2);
        list.add(v3);
        vp.setAdapter(new WelPagerAdapter(list));
    }
}
