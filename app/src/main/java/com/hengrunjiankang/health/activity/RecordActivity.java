package com.hengrunjiankang.health.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Short3;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.adapter.RecordMenuAdapter;
import com.hengrunjiankang.health.fragment.MyTestFragment;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.util.CommonUtils;
import com.hengrunjiankang.health.widget.CustomDatePicker;
import com.hengrunjiankang.health.widget.MyView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
    private ArrayList<String> menuList;
    private TextView tvTitleRight;
    private TextView tvHistroyHigh;
    private TextView tvHistroyLow;
    private TextView tvNearOnce;
    private TextView tvHistroyHigh2;
    private TextView tvHistroyLow2;
    private TextView tvNearOnce2;

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
        tvTitleRight=(TextView)findViewById(R.id.title_right);
        tvHistroyHigh=(TextView)findViewById(R.id.tv_record_hhigh);
        tvHistroyLow=(TextView)findViewById(R.id.tv_record_hlow);
        tvNearOnce=(TextView)findViewById(R.id.tv_record_near);
        tvHistroyHigh2=(TextView)findViewById(R.id.tv_record_hhigh2);
        tvHistroyLow2=(TextView)findViewById(R.id.tv_record_hlow2);
        tvNearOnce2=(TextView)findViewById(R.id.tv_record_near2);
    }
    private SimpleDateFormat formatTime=new SimpleDateFormat("yyyy-MM-dd");;
   private  ArrayList<Fragment> list;

    @Override
    protected void createObject() {
        Calendar nowCl=Calendar.getInstance();
        nowCl.setTimeInMillis(System.currentTimeMillis());
        nowCl.set(Calendar.HOUR_OF_DAY, 0);
        nowCl.set(Calendar.MINUTE, 0);
        nowCl.set(Calendar.SECOND, 0);
        nowCl.set(Calendar.MILLISECOND, 0);
        nowtime =nowCl.getTimeInMillis();

        setStratTime(nowtime);
        setEndTime(nowtime);

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
                setEndTime(getEndTime(time,MyTestFragment.mode));
                nowtime=time;
                MyTestFragment nowFragment = (MyTestFragment) list.get(nowpos % list.size());
                nowFragment.splash(nowtime,type);
            }
        },calendar.getTimeInMillis(),nowtime);
        pickerStartTime.showSpecificTime(false);

        pickerEndTime=new CustomDatePicker(RecordActivity.this,new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(long time) {
                setEndTime(time);

                nowtime=getStartTime(time,MyTestFragment.mode);
                setStratTime(nowtime);
                MyTestFragment nowFragment = (MyTestFragment) list.get(nowpos % list.size());
                nowFragment.splash(nowtime,type);
            }
        },calendar.getTimeInMillis(),nowtime);
        pickerEndTime.showSpecificTime(false);
    }
    private void setStratTime(long time){
        tvStratTime.setText("自"+formatTime.format(time));
    }

    private void setEndTime(long time){
        tvEndTime.setText("至"+formatTime.format(time));
    }

    @Override
    protected void setListener() {
//        change.setOnClickListener(this);
        tvStratTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
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
                setEndTime(getEndTime(nowtime,MyTestFragment.mode));
                MyTestFragment nowFragment = (MyTestFragment) list.get(position % list.size());
                nowFragment.splash(nowtime,type);
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
                setEndTime(getEndTime(nowtime,MyTestFragment.mode));
                nowFragment.splash(nowtime,type);
            }
        });

    }


    private ListView topMenu;
    private PopupWindow windowMenu;
    /**
     * 初始化标题栏菜单
     */
    private void initMenu() {

        View view = View.inflate(RecordActivity.this, R.layout.layout_record_menu,
                null);
         ListView lvMenu = (ListView) view.findViewById(R.id.lv_main_menu);
        RecordMenuAdapter adapter = new RecordMenuAdapter(this);
        menuList=new ArrayList<>();
        for(int i=0;i<8;i++){
            menuList.add(getResources().getString( CommonUtils.getStringID("record_menu_"+i,this)));
        }
        adapter.setData(menuList);
        lvMenu.setAdapter(adapter);
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                // int position = (Integer) v.getTag();
                initTitle(menuList.get(position));
                tvTitleRight.setText(menuList.get(position));
                MyTestFragment nowFragment = (MyTestFragment) list.get(nowpos % list.size());
                setStratTime(nowtime);
                setEndTime(getEndTime(nowtime,MyTestFragment.mode));
                nowFragment.splash(nowtime,position);
                type=position;
                splashData(type);
                windowMenu.dismiss();
            }
        });
        windowMenu = new PopupWindow(view);
        windowMenu.setWidth(CommonUtils.dip2px(this,108));
        windowMenu.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        windowMenu.setFocusable(true);
        windowMenu.setBackgroundDrawable(new BitmapDrawable());
        windowMenu.setOutsideTouchable(true);
        windowMenu.setAnimationStyle(R.style.style_anim_for_scale_top);

    }
    private void splashData(int type){
        String url=null;
        String urlParamHigh="&IsGetOne=true";
        String urlParamLow="&IsGetOne=true";
        String urlParamNear="&Sort=Collectdate desc&IsGetOne=true";
        switch (type) {
            case 0://体脂
                tvHistroyHigh2.setVisibility(View.GONE);
                tvHistroyLow2.setVisibility(View.GONE);
                tvNearOnce2.setVisibility(View.GONE);
                urlParamHigh+="&Sort=Bmi desc";
                url=UrlObject.QUERYFATDATAURL+urlParamHigh+"&SpecifyProperty=Collectdate;Bmi";
                getTvData(url,"历史最高值:","%",tvHistroyHigh,false);
                urlParamLow+="&Sort=Bmi";
                url=UrlObject.QUERYFATDATAURL+urlParamLow+"&SpecifyProperty=Collectdate;Bmi";
                getTvData(url,"历史最低值:","%",tvHistroyLow,false);
                url=UrlObject.QUERYFATDATAURL+urlParamNear+"&SpecifyProperty=Collectdate;Bmi";
                getTvData(url,"最近一次测量:","%",tvNearOnce,true);
                break;
            case 1://血糖
                tvHistroyHigh2.setVisibility(View.GONE);
                tvHistroyLow2.setVisibility(View.GONE);
                tvNearOnce2.setVisibility(View.GONE);
                urlParamHigh+="&Sort=Bloodsugar desc";
                url=UrlObject.QUERYBGDATA+urlParamHigh+"&SpecifyProperty=Collectdate;Bloodsugar";
                getTvData(url,"历史最高值:","mmol/l",tvHistroyHigh,false);
                urlParamLow+="&Sort=Bloodsugar";
                url=UrlObject.QUERYBGDATA+urlParamLow+"&SpecifyProperty=Collectdate;Bloodsugar";
                getTvData(url,"历史最低值:","mmol/l",tvHistroyLow,false);
                url=UrlObject.QUERYBGDATA+urlParamNear+"&SpecifyProperty=Collectdate;Bloodsugar";
                getTvData(url,"最近一次测量:","mmol/l",tvNearOnce,true);

//                url=UrlObject.QUERYBGDATA+urlParam+"&SpecifyProperty=Collectdate;Bloodsugar";
                break;
            case 2://血氧
                tvHistroyHigh2.setVisibility(View.GONE);
                tvHistroyLow2.setVisibility(View.GONE);
                tvNearOnce2.setVisibility(View.GONE);
                urlParamHigh+="&Sort=Oxygen desc";
                url=UrlObject.QUERYOXYGENDATAURL+urlParamHigh+"&SpecifyProperty=Collectdate;Oxygen";
                getTvData(url,"历史最高值:","%",tvHistroyHigh,false);
                urlParamLow+="&Sort=Oxygen";
                url=UrlObject.QUERYOXYGENDATAURL+urlParamLow+"&SpecifyProperty=Collectdate;Oxygen";
                getTvData(url,"历史最低值:","%",tvHistroyLow,false);
                url=UrlObject.QUERYOXYGENDATAURL+urlParamNear+"&SpecifyProperty=Collectdate;Oxygen";
                getTvData(url,"最近一次测量:","%",tvNearOnce,true);
//                url=UrlObject.QUERYOXYGENDATAURL+urlParam+"&SpecifyProperty=Collectdate;Oxygen";
                break;
            case 3://体重
                tvHistroyHigh2.setVisibility(View.GONE);
                tvHistroyLow2.setVisibility(View.GONE);
                tvNearOnce2.setVisibility(View.GONE);
                urlParamHigh+="&Sort=Weight desc";
                url=UrlObject.QUERYWEIGHTDATAURL+urlParamHigh+"&SpecifyProperty=Collectdate;Weight";
                getTvData(url,"历史最高值:","KG",tvHistroyHigh,false);
                urlParamLow+="&Sort=Weight";
                url=UrlObject.QUERYWEIGHTDATAURL+urlParamLow+"&SpecifyProperty=Collectdate;Weight";
                getTvData(url,"历史最低值:","KG",tvHistroyLow,false);
                url=UrlObject.QUERYWEIGHTDATAURL+urlParamNear+"&SpecifyProperty=Collectdate;Weight";
                getTvData(url,"最近一次测量:","KG",tvNearOnce,true);

//                url=UrlObject.QUERYWEIGHTDATAURL+urlParam+"&SpecifyProperty=Collectdate;Weight";
                break;
            case 4://血压
                tvHistroyHigh2.setVisibility(View.VISIBLE);
                tvHistroyLow2.setVisibility(View.VISIBLE);
                tvNearOnce2.setVisibility(View.VISIBLE);
                urlParamHigh+="&Sort=Diastolicpressure desc";
                url=UrlObject.QUERYBLOODPRESSURE+urlParamHigh+"&SpecifyProperty=Collectdate;Diastolicpressure";
                getTvData(url,"舒张压历史最高值:","mmHg",tvHistroyHigh,false,12);
                urlParamLow+="&Sort=Diastolicpressure";
                url=UrlObject.QUERYBLOODPRESSURE+urlParamLow+"&SpecifyProperty=Collectdate;Diastolicpressure";
                getTvData(url,"舒张压历史最低值:","mmHg",tvHistroyLow,false,12);
                url=UrlObject.QUERYBLOODPRESSURE+urlParamNear+"&SpecifyProperty=Collectdate;Diastolicpressure";
                getTvData(url,"最近一次:舒张压","mmHg",tvNearOnce,false,12);
                urlParamHigh="&IsGetOne=true";
                urlParamLow="&IsGetOne=true";
                urlParamNear="&Sort=Collectdate desc&IsGetOne=true";
                urlParamHigh+="&Sort=Systolicpressure desc";
                url=UrlObject.QUERYBLOODPRESSURE+urlParamHigh+"&SpecifyProperty=Collectdate;Systolicpressure";
                getTvData(url,"收缩压历史最高值:","mmHg",tvHistroyHigh2,false,12);
                urlParamLow+="&Sort=Systolicpressure";
                url=UrlObject.QUERYBLOODPRESSURE+urlParamLow+"&SpecifyProperty=Collectdate;Systolicpressure";
                getTvData(url,"收缩压历史最低值:","mmHg",tvHistroyLow2,false,12);
                url=UrlObject.QUERYBLOODPRESSURE+urlParamNear+"&SpecifyProperty=Collectdate;Systolicpressure";
                getTvData(url,"/收缩压:","mmHg",tvNearOnce2,true,12);

//                url=UrlObject.QUERYBLOODPRESSURE+urlParam+"&SpecifyProperty=Collectdate;Diastolicpressure;Systolicpressure";
                break;
        }
    }
    private void getTvData(String url, final String pre, String suf, TextView tv, final boolean showt){
        getTvData(url, pre,suf, tv,showt,14);
    }
    private void getTvData(String url, final String pre, String suf, TextView tv, final boolean showt,int size){
        final TextView tvView=tv;
        final String prefix=pre;
        final String suffix=suf;
        final boolean showtime=showt;
        final int textSize=size;
        new CommonHttp(new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {

//                        initTest(mode, stratTime);
                String temp=null;
                ArrayList<String> strList=new ArrayList<String>();
                ArrayList<Integer> colorList=new ArrayList<Integer>();
                HashMap<String,Object> hashMap= (HashMap< String,Object>)JSON.parseObject(json,HashMap.class);
                for (HashMap.Entry<String,Object> entry:hashMap.entrySet()) {
                    if(!entry.getKey().equals("Collectdate")){

                        strList.add(prefix);
                        colorList.add(getResources().getColor(R.color.gray3));
                        colorList.add(getResources().getColor(R.color.orange1));
                        strList.add(entry.getValue()+suffix);

                    }else{
                       temp=" ("+((String)entry.getValue()).substring(0,10)+")";
                    }
                }
                if(showtime) {
                     strList.add(temp);
                   colorList.add(R.color.gray2);
                }
                setTextViewColor(tvView, strList, colorList,textSize);
                Log.e("data", json);
            }

            @Override
            public void requestFail(String msg) {
                Log.e("data", msg);
            }

            @Override
            public void requestAbnormal(int code) {

            }

            @Override
            public void requestFile(InputStream stream) {

            }
        }, false).doRequest(url);

    }
    private void getHighData(){

    }
    private void getNearData(){

    }

    public static int nowpos = 10000;
    public static long nowtime;
    public static  int type=0;

    @Override
    protected void getData() {
        initMenu();
        initTitle(menuList.get(0));
        pager.setCurrentItem(nowpos);
        rgChangeScope.check(R.id.rb_record_day);
        splashData(type);
    }

    int count;
    private SimpleDateFormat showFormat=new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_record_starttime:
                pickerStartTime.show(showFormat.format(nowtime));
                break;
            case R.id.tv_record_endtime:
                pickerEndTime.show(showFormat.format(nowtime));
                break;
            case R.id.title_right:
                if (windowMenu != null)
                    windowMenu.showAsDropDown(v);
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
    public static long getEndTime(long time ,int mode) {
        switch (mode) {
            case MyView.DAY:
                    return time+24*60*60*1000;
            case MyView.WEEK:
                return time + 6 * 24 * 60 * 60 * 1000;
            case MyView.MONTH:
                long temp = 30l * 24l * 60l * 60l * 1000l;
                 return   time  + temp;
        }
        return -1;
    }
    public static long getStartTime(long time,int mode){
        switch (mode) {
            case MyView.DAY:
                return time-24*60*60*1000;
            case MyView.WEEK:
                return time - 6 * 24 * 60 * 60 * 1000;
            case MyView.MONTH:
                long temp = 30l * 24l * 60l * 60l * 1000l;
                return   time  - temp;
        }
        return -1;
    }
    /**
     * 设置文字分段显示不同颜色
     */
    private void setTextViewColor(TextView tv,ArrayList<String> list,ArrayList<Integer> listColors,int size) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        SpannableStringBuilder builer = new SpannableStringBuilder(
                sb.toString());
        int begin = 0;
        for (int i = 0; i < list.size(); i++) {
            int color = listColors.get(i);
            builer.setSpan(new ForegroundColorSpan(color), begin, begin
                    + list.get(i).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            begin += list.get(i).length();
        }
        tv.setText(builer);
        tv.setTextSize(size);
    }

}
