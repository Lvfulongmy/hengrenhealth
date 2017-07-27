package com.hengrunjiankang.health.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.fragment.HomeFragment;
import com.hengrunjiankang.health.fragment.MyFragment;
import com.hengrunjiankang.health.fragment.SetFragment;

/**
 * Created by Administrator on 2017/7/27.
 */

public class HomeActivity extends BaseFramentActivity {
    private RadioGroup rgNavigation;
    @Override
    protected int setLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void findView() {
        rgNavigation=(RadioGroup)findViewById(R.id.home_rg_navigation);
    }

    @Override
    protected void createObject() {

    }

    @Override
    protected void setListener() {
        rgNavigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if(pageid==checkedId)
                    return;

                switch (checkedId) {
                    case R.id.home_rb_home:
                        pageid=checkedId;
                        switchFragment(new HomeFragment());
                        initTitleString("恒润健康");
                        break;

                    case R.id.home_rb_set:
                        pageid=checkedId;
                        switchFragment(new SetFragment());
                        initTitleString("系统设置");
                        break;
                    case R.id.home_rb_my:
                        pageid=checkedId;
                        switchFragment(new MyFragment());
                        initTitleString("个人中心");
                        break;
                    default:
                        break;
                }
            }
        });

    }
    int pageid=-1;
    @Override
    protected void getData() {
        rgNavigation.check(R.id.home_rb_home);
    }
    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.home_frame, fragment).commit();
    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private static boolean isExit = false;
    /**
     * 点击返回按钮的消息处理器
     */
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;

        }
    };

    /**
     * 连续点击两次按钮退出程序
     */
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

}
