package com.hengrunjiankang.health.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.IdRes;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.adapter.RecordListAdapter;
import com.hengrunjiankang.health.adapter.RecordMenuAdapter;
import com.hengrunjiankang.health.entity.BloodFatEntity;
import com.hengrunjiankang.health.entity.EcgEntity;
import com.hengrunjiankang.health.entity.RecordEntity;
import com.hengrunjiankang.health.entity.UREntity;
import com.hengrunjiankang.health.fragment.CellFragment;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.util.CommonUtils;
import com.hengrunjiankang.health.widget.CustomDatePicker;
import com.hengrunjiankang.health.widget.MyView;

import org.json.JSONException;
import org.json.JSONObject;

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
    private LinearLayout llBottomView;
    private LinearLayout llRecordList;
    private PullToRefreshListView lvRecordList;
    private RecordListAdapter mRecordListAdapter;
    private int pageIndex=1;
    private LinearLayout llBloodPushIndexView;
    @Override
    protected int setLayout() {
        return R.layout.activity_record;
    }

    @Override
    protected void findView() {
        pager = (ViewPager) findViewById(R.id.pager);
        rgChangeScope = (RadioGroup) findViewById(R.id.rg_record_scope);
        tvStratTime = (TextView) findViewById(R.id.tv_record_starttime);
        tvEndTime = (TextView) findViewById(R.id.tv_record_endtime);
        tvTitleRight = (TextView) findViewById(R.id.title_right);
        tvHistroyHigh = (TextView) findViewById(R.id.tv_record_hhigh);
        tvHistroyLow = (TextView) findViewById(R.id.tv_record_hlow);
        tvNearOnce = (TextView) findViewById(R.id.tv_record_near);
        tvHistroyHigh2 = (TextView) findViewById(R.id.tv_record_hhigh2);
        tvHistroyLow2 = (TextView) findViewById(R.id.tv_record_hlow2);
        tvNearOnce2 = (TextView) findViewById(R.id.tv_record_near2);
        llBottomView = (LinearLayout) findViewById(R.id.ll_bommom_view);
        llRecordList = (LinearLayout) findViewById(R.id.ll_record_list);
        lvRecordList = (PullToRefreshListView) findViewById(R.id.lv_record_list);
        lvRecordList.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        llBloodPushIndexView=(LinearLayout)findViewById(R.id.ll_blood_push_indexview);
//        lvRecordList.setLoader(new MyListViewLoaderMore.OnLoader() {
//            @Override
//            public void onLoader() {
//                lvRecordList.addFooterView(footerView);
//                handler.sendEmptyMessageDelayed(0,3000);
//                lvRecordList.setLoadFinish();
//            }
//        });
    }

    private SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd");

    private ArrayList<Fragment> list;

    @Override
    protected void createObject() {
        Calendar nowCl = Calendar.getInstance();
        nowCl.setTimeInMillis(System.currentTimeMillis());
        nowCl.set(Calendar.HOUR_OF_DAY, 0);
        nowCl.set(Calendar.MINUTE, 0);
        nowCl.set(Calendar.SECOND, 0);
        nowCl.set(Calendar.MILLISECOND, 0);
        nowtime = nowCl.getTimeInMillis();

        setStratTime(nowtime);
        setEndTime(nowtime);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 1, 1);
        list = new ArrayList<>();
        list.add(new CellFragment());
        list.add(new CellFragment());
        list.add(new CellFragment());
        list.add(new CellFragment());
        pageAdapter = new ViewAdapter(getSupportFragmentManager(), list);
        pager.setAdapter(pageAdapter);
        pickerStartTime = new CustomDatePicker(RecordActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(long time) {
                nowtime = time;
                if(type<5) {
                    setStratTime(time);
                    setEndTime(getEndTime(time, CellFragment.mode));
                }else{
                    setStratTime(time);
                }

                changeData();
            }
        }, calendar.getTimeInMillis(), nowtime);
        pickerStartTime.showSpecificTime(false);

        pickerEndTime = new CustomDatePicker(RecordActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(long time) {
                time = time + 24 * 60 * 60 * 1000 - 1;
                if(type<5) {
                    setEndTime(time);
                    nowtime = getStartTime(time, CellFragment.mode);
                    setStratTime(nowtime);
                }else{
                    setEndTime(time);
                }
                changeData();
            }
        }, calendar.getTimeInMillis(), nowtime);
        pickerEndTime.showSpecificTime(false);
        mRecordListAdapter = new RecordListAdapter(this);
    }

    private void setStratTime(long time) {
        tvStratTime.setText("自" + formatTime.format(time));
    }

    private void setEndTime(long time) {
        endtime=time;
        tvEndTime.setText("至" + formatTime.format(time));
    }

    /**
     * 刷新activity，切换类型
     */
    private void changeActivity(){
        if(type<5) {
            setEndTime(getEndTime(nowtime, CellFragment.mode));
            splashCell();
            splashData();
        }else{
            splashData();
        }
    }

    /**
     * 刷新数据，改变时间以后
     */
    private void changeData(){
        if (type < 5) {
            splashCell();
        }else{
            splashData();
        }
    }
    private void splashCell(){
        CellFragment nowFragment = (CellFragment) list.get(nowpos % list.size());
        nowFragment.splash(nowtime, type);
    }
    @Override
    protected void setListener() {
//        change.setOnClickListener(this);
        lvRecordList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if(getListByPageUrl!=null)
                    getListData(getListByPageUrl,pageIndex+1);
            }
        });
        tvStratTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (CellFragment.mode) {
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
                setEndTime(getEndTime(nowtime, CellFragment.mode));
                nowpos = position;
                splashCell();
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
                        CellFragment.mode = MyView.DAY;
                        break;
                    case R.id.rb_record_week:
                        CellFragment.mode = MyView.WEEK;
                        break;
                    case R.id.rb_record_mouth:
                        CellFragment.mode = MyView.MONTH;
                        break;
                }
                setStratTime(nowtime);
                setEndTime(getEndTime(nowtime, CellFragment.mode));
               splashCell();
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
        menuList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            menuList.add(getResources().getString(CommonUtils.getStringID("record_menu_" + i, this)));
        }
        adapter.setData(menuList);
        lvMenu.setAdapter(adapter);
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                // int position = (Integer) v.getTag();
                initTitleString(menuList.get(position));
                tvTitleRight.setText(menuList.get(position));
                type = position;
                changeActivity();
                windowMenu.dismiss();
            }
        });
        windowMenu = new PopupWindow(view);
        windowMenu.setWidth(CommonUtils.dip2px(this, 108));
        windowMenu.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        windowMenu.setFocusable(true);
        windowMenu.setBackgroundDrawable(new BitmapDrawable());
        windowMenu.setOutsideTouchable(true);
        windowMenu.setAnimationStyle(R.style.style_anim_for_scale_top);

    }
    private String getListByPageUrl;
    private void splashData() {
        getListByPageUrl=null;
        pageIndex=1;
        String url = null;
        String urlParamHigh = "&IsGetOne=true";
        String urlParamLow = "&IsGetOne=true";
        String urlParamNear = "&Sort=Collectdate desc&IsGetOne=true";
        SimpleDateFormat  formatTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTimeStr= formatTime.format(endtime);
        String stratTimeStr=formatTime.format(nowtime);
        String urlParam="&Sort=Collectdate&CollectdateMin="+stratTimeStr+"&CollectdateMax="+endTimeStr;
        switch (type) {
            case 0://体脂
                tvHistroyHigh2.setVisibility(View.GONE);
                tvHistroyLow2.setVisibility(View.GONE);
                tvNearOnce2.setVisibility(View.GONE);
                urlParamHigh += "&Sort=Bmi desc";
                url = UrlObject.QUERYFATDATAURL + urlParamHigh + "&SpecifyProperty=Collectdate;Bmi";
                getTvData(url, "历史最高值:", "%", tvHistroyHigh, false);
                urlParamLow += "&Sort=Bmi";
                url = UrlObject.QUERYFATDATAURL + urlParamLow + "&SpecifyProperty=Collectdate;Bmi";
                getTvData(url, "历史最低值:", "%", tvHistroyLow, false);
                url = UrlObject.QUERYFATDATAURL + urlParamNear + "&SpecifyProperty=Collectdate;Bmi";
                getTvData(url, "最近一次测量:", "%", tvNearOnce, true);
                llBottomView.setVisibility(View.VISIBLE);
                llRecordList.setVisibility(View.INVISIBLE);
                llBloodPushIndexView.setVisibility(View.GONE);
                break;
            case 1://血糖
                tvHistroyHigh2.setVisibility(View.GONE);
                tvHistroyLow2.setVisibility(View.GONE);
                tvNearOnce2.setVisibility(View.GONE);
                urlParamHigh += "&Sort=Bloodsugar desc";
                url = UrlObject.QUERYBGDATA + urlParamHigh + "&SpecifyProperty=Collectdate;Bloodsugar";
                getTvData(url, "历史最高值:", "mmol/l", tvHistroyHigh, false);
                urlParamLow += "&Sort=Bloodsugar";
                url = UrlObject.QUERYBGDATA + urlParamLow + "&SpecifyProperty=Collectdate;Bloodsugar";
                getTvData(url, "历史最低值:", "mmol/l", tvHistroyLow, false);
                url = UrlObject.QUERYBGDATA + urlParamNear + "&SpecifyProperty=Collectdate;Bloodsugar";
                getTvData(url, "最近一次测量:", "mmol/l", tvNearOnce, true);
                llBottomView.setVisibility(View.VISIBLE);
                llRecordList.setVisibility(View.INVISIBLE);
//                url=UrlObject.QUERYBGDATA+urlParam+"&SpecifyProperty=Collectdate;Bloodsugar";
                llBloodPushIndexView.setVisibility(View.GONE);
                break;
            case 2://血氧
                tvHistroyHigh2.setVisibility(View.GONE);
                tvHistroyLow2.setVisibility(View.GONE);
                tvNearOnce2.setVisibility(View.GONE);
                urlParamHigh += "&Sort=Oxygen desc";
                url = UrlObject.QUERYOXYGENDATAURL + urlParamHigh + "&SpecifyProperty=Collectdate;Oxygen";
                getTvData(url, "历史最高值:", "%", tvHistroyHigh, false);
                urlParamLow += "&Sort=Oxygen";
                url = UrlObject.QUERYOXYGENDATAURL + urlParamLow + "&SpecifyProperty=Collectdate;Oxygen";
                getTvData(url, "历史最低值:", "%", tvHistroyLow, false);
                url = UrlObject.QUERYOXYGENDATAURL + urlParamNear + "&SpecifyProperty=Collectdate;Oxygen";
                getTvData(url, "最近一次测量:", "%", tvNearOnce, true);
                llBottomView.setVisibility(View.VISIBLE);
                llRecordList.setVisibility(View.INVISIBLE);
                llBloodPushIndexView.setVisibility(View.GONE);
//                url=UrlObject.QUERYOXYGENDATAURL+urlParam+"&SpecifyProperty=Collectdate;Oxygen";
                break;
            case 3://体重
                tvHistroyHigh2.setVisibility(View.GONE);
                tvHistroyLow2.setVisibility(View.GONE);
                tvNearOnce2.setVisibility(View.GONE);
                urlParamHigh += "&Sort=weight desc";
                url = UrlObject.QUERYWEIGHTDATAURL + urlParamHigh + "&SpecifyProperty=Collectdate;weight";
                getTvData(url, "历史最高值:", "KG", tvHistroyHigh, false);
                urlParamLow += "&Sort=weight";
                url = UrlObject.QUERYWEIGHTDATAURL + urlParamLow + "&SpecifyProperty=Collectdate;weight";
                getTvData(url, "历史最低值:", "KG", tvHistroyLow, false);
                url = UrlObject.QUERYWEIGHTDATAURL + urlParamNear + "&SpecifyProperty=Collectdate;weight";
                getTvData(url, "最近一次测量:", "KG", tvNearOnce, true);
                llBottomView.setVisibility(View.VISIBLE);
                llRecordList.setVisibility(View.INVISIBLE);
                llBloodPushIndexView.setVisibility(View.GONE);
//                url=UrlObject.QUERYWEIGHTDATAURL+urlParam+"&SpecifyProperty=Collectdate;Weight";
                break;
            case 4://血压
                tvHistroyHigh2.setVisibility(View.VISIBLE);
                tvHistroyLow2.setVisibility(View.VISIBLE);
                tvNearOnce2.setVisibility(View.VISIBLE);
                urlParamHigh += "&Sort=Diastolicpressure desc";
                url = UrlObject.QUERYBLOODPRESSURE + urlParamHigh + "&SpecifyProperty=Collectdate;Diastolicpressure";
                getTvData(url, "舒张压历史最高值:", "mmHg", tvHistroyHigh2, false, 12);
                urlParamLow += "&Sort=Diastolicpressure";
                url = UrlObject.QUERYBLOODPRESSURE + urlParamLow + "&SpecifyProperty=Collectdate;Diastolicpressure";
                getTvData(url, "舒张压历史最低值:", "mmHg", tvHistroyLow2, false, 12);
                url = UrlObject.QUERYBLOODPRESSURE + urlParamNear + "&SpecifyProperty=Collectdate;Diastolicpressure";
                getTvData(url, "/舒张压:", "mmHg", tvNearOnce2, true, 12);
                urlParamHigh = "&IsGetOne=true";
                urlParamLow = "&IsGetOne=true";
                urlParamNear = "&Sort=Collectdate desc&IsGetOne=true";
                urlParamHigh += "&Sort=Systolicpressure desc";
                url = UrlObject.QUERYBLOODPRESSURE + urlParamHigh + "&SpecifyProperty=Collectdate;Systolicpressure";
                getTvData(url, "收缩压历史最高值:", "mmHg", tvHistroyHigh, false, 12);
                urlParamLow += "&Sort=Systolicpressure";
                url = UrlObject.QUERYBLOODPRESSURE + urlParamLow + "&SpecifyProperty=Collectdate;Systolicpressure";
                getTvData(url, "收缩压历史最低值:", "mmHg", tvHistroyLow, false, 12);
                url = UrlObject.QUERYBLOODPRESSURE + urlParamNear + "&SpecifyProperty=Collectdate;Systolicpressure";
                getTvData(url, "最近一次:收缩压", "mmHg", tvNearOnce, false, 12);
                llBottomView.setVisibility(View.VISIBLE);
                llRecordList.setVisibility(View.INVISIBLE);
                llBloodPushIndexView.setVisibility(View.VISIBLE);
//                url=UrlObject.QUERYBLOODPRESSURE+urlParam+"&SpecifyProperty=Collectdate;Diastolicpressure;Systolicpressure";
                break;
            case 5:
                llBottomView.setVisibility(View.INVISIBLE);
                llRecordList.setVisibility(View.VISIBLE);
                llBloodPushIndexView.setVisibility(View.GONE);
                url=UrlObject.QUREYECG+urlParam;
                getListData(url,pageIndex);
                getListByPageUrl=url;
                break;
            case 6:
                llBottomView.setVisibility(View.INVISIBLE);
                llRecordList.setVisibility(View.VISIBLE);
                llBloodPushIndexView.setVisibility(View.GONE);
                url=UrlObject.QUERYUR+urlParam;
                getListData(url,pageIndex);
                getListByPageUrl=url;
                break;
            case 7:
                llBottomView.setVisibility(View.INVISIBLE);
                llRecordList.setVisibility(View.VISIBLE);
                llBloodPushIndexView.setVisibility(View.GONE);
                url=UrlObject.QUERYBLOODFAT+urlParam;
                getListData(url,pageIndex);
                getListByPageUrl=url;
                break;

        }
    }

    private void getTvData(String url, final String pre, String suf, TextView tv, final boolean showt) {
        getTvData(url, pre, suf, tv, showt, 14);
    }
    ArrayList<UREntity> recordListUR;
    ArrayList<BloodFatEntity> recordListBF;
    ArrayList<EcgEntity> recordListEcg;
    private void getListData(String url,int page) {
        final int requestPage=page;
        if(page==1)
        showPDialog();
        new CommonHttp(RecordActivity.this,new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                dismissPDialog();
                try {
                    JSONObject jsonObject=new JSONObject(json);
                    json=jsonObject.getJSONArray("Items").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                    lvRecordList.onRefreshComplete();
                }

                ArrayList<RecordEntity> recordListData = (ArrayList<RecordEntity>) JSON.parseArray(json, RecordEntity.class);
                    if(recordListData!=null&&recordListData.size()>0) {
                        if (requestPage == 1) {

                            mRecordListAdapter.setData(recordListData);
                            lvRecordList.setAdapter(mRecordListAdapter);
                            switch (type) {
                                case 5:
                                    recordListEcg = (ArrayList<EcgEntity>) JSON.parseArray(json, EcgEntity.class);
                                    break;
                                case 6:
                                    recordListUR = (ArrayList<UREntity>) JSON.parseArray(json, UREntity.class);
                                    break;
                                case 7:
                                    recordListBF = (ArrayList<BloodFatEntity>) JSON.parseArray(json, BloodFatEntity.class);
                                    break;
                            }
                        } else {
                            pageIndex=requestPage;
                            mRecordListAdapter.getData().addAll(recordListData);
                            mRecordListAdapter.notifyDataSetChanged();
                            lvRecordList.onRefreshComplete();
                            switch (type) {
                                case 5:
                                    recordListEcg.addAll( (ArrayList<EcgEntity>) JSON.parseArray(json, EcgEntity.class));
                                    break;
                                case 6:
                                    recordListUR.addAll((ArrayList<UREntity>) JSON.parseArray(json, UREntity.class));
                                    break;
                                case 7:
                                    recordListBF.addAll ((ArrayList<BloodFatEntity>) JSON.parseArray(json, BloodFatEntity.class));
                                    break;
                            }
                        }
                    }else{
//                        Toast.makeText(RecordActivity.this,"没有更多",Toast.LENGTH_SHORT).show();
                        lvRecordList.onRefreshComplete();
                    }


            }

            @Override
            public void requestFail(String msg) {
                Toast.makeText(RecordActivity.this,msg,Toast.LENGTH_SHORT).show();
                dismissPDialog();
                lvRecordList.onRefreshComplete();
                Log.e("data", msg);
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(RecordActivity.this,R.string.net_error+code,Toast.LENGTH_SHORT).show();
                dismissPDialog();
                lvRecordList.onRefreshComplete();
            }


        }).doRequest(url+"&QueryLimit=15&PageIndex="+page);

    }

    private void getTvData(String url, final String pre, String suf, TextView tv, final boolean showt, int size) {
        final TextView tvView = tv;
        final String prefix = pre;
        final String suffix = suf;
        final boolean showtime = showt;
        final int textSize = size;
        new CommonHttp(RecordActivity.this,new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {

//                        initTest(mode, stratTime);
                String temp = null;
                ArrayList<String> strList = new ArrayList<String>();
                ArrayList<Integer> colorList = new ArrayList<Integer>();
                HashMap<String, Object> hashMap = (HashMap<String, Object>) JSON.parseObject(json, HashMap.class);
                if (hashMap != null) {
                    for (HashMap.Entry<String, Object> entry : hashMap.entrySet()) {
                        if (!entry.getKey().equals("Collectdate")) {

                            strList.add(prefix);
                            colorList.add(getResources().getColor(R.color.gray3));
                            colorList.add(getResources().getColor(R.color.orange1));
                            strList.add(entry.getValue() + suffix);

                        } else {
                            temp = " (" + ((String) entry.getValue()).substring(0, 10) + ")";
                        }
                    }
                    if (showtime) {
                        strList.add(temp);
                        colorList.add(R.color.gray2);
                    }
                    setTextViewColor(tvView, strList, colorList, textSize);
//                    Log.e("data", json);
                }
            }

            @Override
            public void requestFail(String msg) {
                Log.e("data", msg);
            }

            @Override
            public void requestAbnormal(int code) {

            }
        }).doRequest(url);

    }
    public void goToDetails(int position){
        Intent intent=new Intent();
        if(type==6) {
            intent.putExtra("title", "尿常规档案");
            intent.putExtra("type",1);
            intent.putExtra("data",recordListUR.get(position));
            intent.setClass(RecordActivity.this,RecordDetailsActivity.class);
            startActivity(intent);
        }
        if(type==7){
            intent.putExtra("title", "血脂档案");
            intent.putExtra("type",0);
            intent.putExtra("data",recordListBF.get(position));
            intent.setClass(RecordActivity.this,RecordDetailsActivity.class);
            startActivity(intent);
        }
        if(type==5){
            intent.putExtra("title", "心电档案");
            intent.putExtra("data",recordListEcg.get(position));
            intent.setClass(RecordActivity.this,EcgDetailsActivity.class);
            startActivity(intent);
        }
        overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
    }

    public static int nowpos = 10000;
    public static long nowtime;
    public static long endtime;
    public static int type = 0;

    @Override
    protected void getData() {
        initMenu();
        initTitle(menuList.get(type));
        pager.setCurrentItem(nowpos);
        rgChangeScope.check(R.id.rb_record_day);
        splashData();
    }

    int count;
    private SimpleDateFormat showFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
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

    public static long getEndTime(long time, int mode) {
        switch (mode) {
            case MyView.DAY:
                return time + 24 * 60 * 60 * 1000 - 1;
            case MyView.WEEK:
                return time + 7 * 24 * 60 * 60 * 1000 - 1;
            case MyView.MONTH:
                long temp = 30l * 24l * 60l * 60l * 1000l - 1;
                return time + temp;
        }
        return -1;
    }

    public static long getStartTime(long time, int mode) {
        switch (mode) {
            case MyView.DAY:
                return time - 24 * 60 * 60 * 1000 + 1;
            case MyView.WEEK:
                return time - 7 * 24 * 60 * 60 * 1000 + 1;
            case MyView.MONTH:
                long temp = 30l * 24l * 60l * 60l * 1000l - 1;
                return time - temp;
        }
        return -1;
    }

    /**
     * 设置文字分段显示不同颜色
     */
    private void setTextViewColor(TextView tv, ArrayList<String> list, ArrayList<Integer> listColors, int size) {
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
