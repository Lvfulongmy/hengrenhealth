package com.hengrunjiankang.health.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.activity.RecordActivity;
import com.hengrunjiankang.health.adapter.ImagePagerAdapter;
import com.hengrunjiankang.health.adapter.ImagePagerNoWhileAdapter;
import com.hengrunjiankang.health.widget.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/27.
 */

public class HomeFragment extends BaseFragment {
    private ViewPager banner;
    private CirclePageIndicator mIndicator;

    @Override
    protected int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void findView(View view) {
        banner=(ViewPager)view.findViewById(R.id.vp_home_banner);
        mIndicator=(CirclePageIndicator)view.findViewById(R.id.cpi_home_indicator);
        view.findViewById(R.id.ll_home_1).setOnClickListener(this);
        view.findViewById(R.id.ll_home_2).setOnClickListener(this);
        view.findViewById(R.id.ll_home_3).setOnClickListener(this);
        view.findViewById(R.id.ll_home_4).setOnClickListener(this);
        view.findViewById(R.id.ll_home_5).setOnClickListener(this);
        view.findViewById(R.id.ll_home_6).setOnClickListener(this);
        view.findViewById(R.id.ll_home_7).setOnClickListener(this);
        view.findViewById(R.id.ll_home_8).setOnClickListener(this);
    }

    @Override
    protected void createObject() {
        ArrayList<Integer> ids=new ArrayList<>();
        ids.add(R.mipmap.banner1);
        ids.add(R.mipmap.banner2);
        ids.add(R.mipmap.banner3);
        banner.setAdapter(new ImagePagerAdapter(mActivity,ids));
        mIndicator.setViewPager(banner);
        banner.setCurrentItem(999);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void getData() {

    }

    @Override
    public void onClick(View view) {
        int pos=Integer.parseInt((String)view.getTag());
        RecordActivity.type=pos-1;
        startActivity(new Intent(mActivity,RecordActivity.class));
        mActivity.overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
    }
}
