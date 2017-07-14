package com.hengrunjiankang.health.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.adapter.CellPagerAdapter;
import com.hengrunjiankang.health.entity.LineData;
import com.hengrunjiankang.health.entity.PointData;
import com.hengrunjiankang.health.fragment.MyTestFragment;
import com.hengrunjiankang.health.widget.MyView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/12.
 */

public class TestPagerCellActivity extends BaseFramentActivity {
//    private TextView change;
    private ViewPager pager;
    private ViewAdapter pageAdapter;
    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void findView() {
//        change=(TextView)findViewById(R.id.change);
//        cellView=(MyView)findViewById(R.id.cell);
//        initTest(MyView.DAY,System.currentTimeMillis());
//        cellView.setMode(MyView.DAY,System.currentTimeMillis());
        pager=(ViewPager) findViewById(R.id.pager);

    }
//    private void initTest(int mode, long starttime){
//        Calendar cl=Calendar.getInstance();
//        cl.set(Calendar.HOUR_OF_DAY, 0);
//        cl.set(Calendar.MINUTE, 0);
//        cl.set(Calendar.SECOND, 0);
//        cl.set(Calendar.MILLISECOND, 0);
//        ArrayList<LineData> data=new ArrayList<>();
//        LineData lineData=new LineData();
//        ArrayList<PointData> pointDatas=new ArrayList<>();
//        lineData.setDate(pointDatas);
//        lineData.setColor(R.color.colorAccent);
//        data.add(lineData);
//        switch (mode){
//            case MyView.DAY:
//
//                for(int i=0;i<24;i++){
//                    PointData pointData=new PointData();
//                    pointData.setDate(cl.getTimeInMillis()+(i*60*60*1000));
//                    pointData.setIndex(new Random().nextInt(100));
//                    pointDatas.add(pointData);
//                }
//                break;
//            case MyView.WEEK:
//                for(int i=0;i<7;i++){
//                    PointData pointData=new PointData();
//                    pointData.setDate(cl.getTimeInMillis()+(i*60*60*1000*24));
//                    pointData.setIndex(new Random().nextInt(100));
//                    pointDatas.add(pointData);
//                }
//                break;
//            case MyView.MONTH:
//                for(int i=0;i<30;i++){
//                    PointData pointData=new PointData();
//                    pointData.setDate(cl.getTimeInMillis()+(i*60*60*1000*24));
//                    pointData.setIndex(new Random().nextInt(100));
//                    pointDatas.add(pointData);
//                }
//                break;
//
//        }
//        cellView.setLineData(data);
//    }
ArrayList<Fragment> list;
    @Override
    protected void createObject() {
            list=new ArrayList<>();
            list.add(new MyTestFragment());
            list.add(new MyTestFragment());
            list.add(new MyTestFragment());
            list.add(new MyTestFragment());
            pageAdapter=new ViewAdapter(getSupportFragmentManager(),list);
            pager.setAdapter(pageAdapter);
    }

    @Override
    protected void setListener() {
//        change.setOnClickListener(this);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                    switch (MyTestFragment.mode){
                        case MyView.DAY:
                            if(position>nowpos){
                                nowtime=nowtime+24*60*60*1000;
                            }else if(position<nowpos){
                                nowtime=nowtime-24*60*60*1000;
                            }
                            break;
                        case MyView.WEEK:
                            if(position>nowpos){
                                nowtime=nowtime+7*24*60*60*1000;
                            }else if(position<nowpos){
                                nowtime=nowtime-7*24*60*60*1000;
                            }
                            break;
                        case MyView.MONTH:
                             long temp=30* 24 * 60 * 60 * 1000;
                            if(position>nowpos){
                                    nowtime = nowtime +temp;
                            }else if(position<nowpos){
                                    nowtime = nowtime - temp;
                            }
                            break;
                    }

                MyTestFragment fragment=(MyTestFragment) list.get(position%list.size());
                fragment.splash(nowtime);
                nowpos=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    int nowpos=10000;
    long nowtime;
    @Override
    protected void getData() {
        nowtime=System.currentTimeMillis();
        pager.setCurrentItem(nowpos);
    }
    int count;
    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.change:
//                int i=count++%3;
//                mode=i;
//                initTest(i,System.currentTimeMillis());
////                 cellView.setMode(i,System.currentTimeMillis());
////                cellView.invalidate();
//                break;
//        }
    }
    class ViewAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentsList;

        public ViewAdapter(FragmentManager fm) {
            super(fm);
        }

        public ViewAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragmentsList = fragments;
        }

        @Override
        public int getCount() {
//            return fragmentsList.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public Fragment getItem(int position) {
//            return fragmentsList.get(position);
             Fragment fragment= fragmentsList.get(position%fragmentsList.size());
            return fragment;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub

            return super.instantiateItem(container,position%fragmentsList.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            // super.destroyItem(container, position, object);
           super.destroyItem(container,position,object);
        }
    }
}
