package com.hengrunjiankang.health.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.fragment.MyTestFragment;
import com.hengrunjiankang.health.widget.CustomDatePicker;
import com.hengrunjiankang.health.widget.MyView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */

public class RecordActivity extends BaseFramentActivity {
    //    private TextView change;
    private ViewPager pager;
    private ViewAdapter pageAdapter;
    private RadioGroup rgChangeScope;
    private TextView tvStratTime;
    private TextView tvEndTime;
    private CustomDatePicker pickerStartTime;
    private CustomDatePicker pickerEndTime;

    @Override
    protected int setLayout() {
        return R.layout.activity_record;
    }

    @Override
    protected void findView() {
        pager = (ViewPager) findViewById(R.id.pager);
        rgChangeScope = (RadioGroup) findViewById(R.id.rg_record_scope);
        tvStratTime=(TextView)findViewById(R.id.tv_record_starttime);
        tvEndTime=(TextView)findViewById(R.id.tv_record_endtime);

    }
    private SimpleDateFormat formatTime;
   private  ArrayList<Fragment> list;

    @Override
    protected void createObject() {
        nowtime = System.currentTimeMillis();
        formatTime=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.set(2010,1,1);
        list = new ArrayList<>();
        list.add(new MyTestFragment());
        list.add(new MyTestFragment());
        list.add(new MyTestFragment());
        list.add(new MyTestFragment());
        pageAdapter = new ViewAdapter(getSupportFragmentManager(), list);
        pager.setAdapter(pageAdapter);
        pickerStartTime=new CustomDatePicker(RecordActivity.this,new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(long time) {
                setStratTime(time);
                nowtime=time;
                MyTestFragment nowFragment = (MyTestFragment) list.get(nowpos % list.size());
                nowFragment.splash(nowtime);
            }
        },calendar.getTimeInMillis(),nowtime);
        pickerStartTime.showSpecificTime(false);
    }
    private void setStratTime(long time){
        tvStratTime.setText("自"+formatTime.format(time));
    }

    @Override
    protected void setListener() {
//        change.setOnClickListener(this);
        tvStratTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (MyTestFragment.mode) {
                    case MyView.DAY:
                        if (position > nowpos) {
                            nowtime = nowtime + 24 * 60 * 60 * 1000;
                        } else if (position < nowpos) {
                            nowtime = nowtime - 24 * 60 * 60 * 1000;
                        }
                        break;
                    case MyView.WEEK:
                        if (position > nowpos) {
                            nowtime = nowtime + 7 * 24 * 60 * 60 * 1000;
                        } else if (position < nowpos) {
                            nowtime = nowtime - 7 * 24 * 60 * 60 * 1000;
                        }
                        break;
                    case MyView.MONTH:
                        long temp = 30l * 24l * 60l * 60l * 1000l;
                        if (position > nowpos) {
                            nowtime = nowtime + temp;
                        } else if (position < nowpos) {
                            nowtime = nowtime - temp;
                        }
                        break;
                }
                setStratTime(nowtime);
                MyTestFragment nowFragment = (MyTestFragment) list.get(position % list.size());
                nowFragment.splash(nowtime);
                nowpos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rgChangeScope.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_record_day:
                        MyTestFragment.mode = MyView.DAY;
                        break;
                    case R.id.rb_record_week:
                        MyTestFragment.mode = MyView.WEEK;
                        break;
                    case R.id.rb_record_mouth:
                        MyTestFragment.mode = MyView.MONTH;
                        break;
                }
                MyTestFragment nowFragment = (MyTestFragment) list.get(nowpos % list.size());
                setStratTime(nowtime);
                nowFragment.splash(nowtime);
            }
        });

    }

    int nowpos = 10000;
    long nowtime;

    @Override
    protected void getData() {
        initTitle("血脂档案");
        pager.setCurrentItem(nowpos);
        rgChangeScope.check(R.id.rb_record_day);
        MyTestFragment nowFragment = (MyTestFragment) list.get(nowpos % list.size());
        nowFragment.splash(nowtime);
    }

    int count;
    private SimpleDateFormat showFormat=new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_record_starttime:
                pickerStartTime.show(showFormat.format(nowtime));
                break;
        }
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
            Fragment fragment = fragmentsList.get(position % fragmentsList.size());
            return fragment;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub

            return super.instantiateItem(container, position % fragmentsList.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            // super.destroyItem(container, position, object);
            super.destroyItem(container, position, object);
        }
    }
}
