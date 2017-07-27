package com.hengrunjiankang.health.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.activity.MyAccountActivity;

/**
 * Created by Administrator on 2017/7/27.
 */

public class MyFragment extends BaseFragment {
    LinearLayout llAccount;
    @Override
    protected int setLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void findView(View view) {
        llAccount=(LinearLayout)view.findViewById(R.id.ll_my_account_info);
    }

    @Override
    protected void createObject() {

    }

    @Override
    protected void setListener() {
        llAccount.setOnClickListener(this);
    }

    @Override
    protected void getData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_my_account_info:
                startActivity(new Intent(mActivity, MyAccountActivity.class));
                mActivity.overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                break;
        }
    }
}
