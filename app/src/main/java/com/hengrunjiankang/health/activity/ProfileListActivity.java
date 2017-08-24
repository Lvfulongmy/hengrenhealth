package com.hengrunjiankang.health.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.adapter.ProfileListAdapter;
import com.hengrunjiankang.health.entity.BloodFatEntity;
import com.hengrunjiankang.health.entity.EcgEntity;
import com.hengrunjiankang.health.entity.ProfileListEntity;
import com.hengrunjiankang.health.entity.RecordEntity;
import com.hengrunjiankang.health.entity.UREntity;
import com.hengrunjiankang.health.fragment.CellFragment;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.widget.CustomDatePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/17.
 */

public class ProfileListActivity extends BaseActivity {
    private CustomDatePicker pickerStartTime;
    private CustomDatePicker pickerEndTime;
    private PullToRefreshListView lvRecordList;
    private TextView tvStratTime;
    private TextView tvEndTime;
    private long starttime=-1;
    private long endtime=-1;
    private int pageIndex=1;
    private SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd");
    private ProfileListAdapter mProfileListAdapter;
    private LinearLayout llNodata;
    private  TextView tvAdd;



    @Override
    protected int setLayout() {
        return R.layout.activity_profile_list;
    }

    @Override
    protected void findView() {
        lvRecordList = (PullToRefreshListView) findViewById(R.id.lv_record_list);
        lvRecordList.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        tvStratTime=findViewById(R.id.tv_record_starttime);
        tvEndTime=findViewById(R.id.tv_record_endtime);
        llNodata=(LinearLayout)findViewById(R.id.ll_nodata);
        tvAdd=(TextView)findViewById(R.id.tv_profile_add);
    }

    @Override
    protected void createObject() {
        Calendar nowCl = Calendar.getInstance();
        nowCl.setTimeInMillis(System.currentTimeMillis());
        nowCl.set(Calendar.HOUR_OF_DAY, 0);
        nowCl.set(Calendar.MINUTE, 0);
        nowCl.set(Calendar.SECOND, 0);
        nowCl.set(Calendar.MILLISECOND, 0);
        long nowtime=nowCl.getTimeInMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 1, 1);
        pickerStartTime = new CustomDatePicker(ProfileListActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(long time) {
                starttime = time;
                setStratTime(time);
            }
        }, calendar.getTimeInMillis(), nowtime);
        pickerStartTime.showSpecificTime(false);

        pickerEndTime = new CustomDatePicker(ProfileListActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(long time) {
                time = time + 24 * 60 * 60 * 1000 - 1;
                endtime=time;
                setEndTime(time);
            }
        }, calendar.getTimeInMillis(), nowtime);
        pickerEndTime.showSpecificTime(false);
        mProfileListAdapter=new ProfileListAdapter(this);
        lvRecordList.setAdapter(mProfileListAdapter);

    }
    private void setStratTime(long time) {
        tvStratTime.setText("自" + showFormat.format(time));
        initListData();
    }

    private void setEndTime(long time) {
        tvEndTime.setText("至" + showFormat.format(time));
        initListData();
    }
    private String url;
    private void initListData(){

        if(endtime!=-1&&starttime!=-1) {
            url=UrlObject.QUERYHEALTHRECORD+"&SpecifyProperty=FriendlyId;Id;RecordTime";
            SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String endTimeStr = formatTime.format(endtime);
            String stratTimeStr = formatTime.format(starttime);
            url+="&Sort=RecordTime desc&RecordTimeMin=" + stratTimeStr + "&RecordTimeMax=" + endTimeStr;
            getListData(url,1);
        }else{
            url=UrlObject.QUERYHEALTHRECORD+"&SpecifyProperty=FriendlyId;Id;RecordTime&Sort=RecordTime desc";
            getListData(url,1);

        }
    }
    private void getListData(String url,int page) {
        final int requestPage=page;
        if(page==1)
            showPDialog();
        new CommonHttp(ProfileListActivity.this,new CommonHttpCallback() {
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

                ArrayList<ProfileListEntity> recordListData = (ArrayList<ProfileListEntity>) JSON.parseArray(json, ProfileListEntity.class);
                if(recordListData!=null&&recordListData.size()>0) {
                    if (requestPage == 1) {

                        mProfileListAdapter.setData(recordListData);
                        mProfileListAdapter.notifyDataSetChanged();

                    } else {
                        pageIndex=requestPage;
                        mProfileListAdapter.getData().addAll(recordListData);
                        mProfileListAdapter.notifyDataSetChanged();
                        lvRecordList.onRefreshComplete();
                    }
                    llNodata.setVisibility(View.GONE);
                }else{
                    if(requestPage==1){
                        if(endtime!=-1&&starttime!=-1) {
                            mProfileListAdapter.setData(null);
                            mProfileListAdapter.notifyDataSetChanged();
                            Toast.makeText(ProfileListActivity.this, "没有查询结果", Toast.LENGTH_SHORT).show();
                        }else{
                            mProfileListAdapter.setData(null);
                            mProfileListAdapter.notifyDataSetChanged();
                            llNodata.setVisibility(View.VISIBLE);
                        }
                    }
//                        Toast.makeText(RecordActivity.this,"没有更多",Toast.LENGTH_SHORT).show();
                    lvRecordList.onRefreshComplete();
                }


            }

            @Override
            public void requestFail(String msg) {
                Toast.makeText(ProfileListActivity.this,msg,Toast.LENGTH_SHORT).show();
                dismissPDialog();
                lvRecordList.onRefreshComplete();
                Log.e("data", msg);
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(ProfileListActivity.this, getString(R.string.net_error)+code,Toast.LENGTH_SHORT).show();
                dismissPDialog();
                lvRecordList.onRefreshComplete();
            }


        }).doRequest(url+"&QueryLimit=15&PageIndex="+page);

    }
    public void goToDetails(int postition){
        String id= mProfileListAdapter.getData().get(postition).getId();
        Intent intent=new Intent(ProfileListActivity.this,ProfileActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);

    }

    public void deleteRecord(int pos){
        HashMap<String,String> params=new HashMap<>();
        params.put("Id",mProfileListAdapter.getData().get(pos).getId());
        new CommonHttp(ProfileListActivity.this,new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                dismissPDialog();
                initListData();
                Toast.makeText(ProfileListActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestFail(String msg) {
                Toast.makeText(ProfileListActivity.this,msg,Toast.LENGTH_SHORT).show();
                dismissPDialog();
                Toast.makeText(ProfileListActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                Log.e("data", msg);
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(ProfileListActivity.this,getString(R.string.net_error)+code,Toast.LENGTH_SHORT).show();
                dismissPDialog();
                lvRecordList.onRefreshComplete();
            }


        }).doRequest(UrlObject.DELETEHEALTHRECORD,params);
    }
    @Override
    protected void setListener() {
        lvRecordList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if(url!=null)
                    getListData(url,pageIndex+1);
            }
        });
        tvEndTime.setOnClickListener(this);
        tvStratTime.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        initTitle("健康档案");

    }

    @Override
    protected void onResume() {
        super.onResume();
        initListData();
    }

    private SimpleDateFormat showFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_record_starttime:
                pickerStartTime.show(showFormat.format(System.currentTimeMillis()));
                break;
            case R.id.tv_record_endtime:
                pickerEndTime.show(showFormat.format(System.currentTimeMillis()));
                break;
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                break;
            case R.id.tv_profile_add:
                Intent intent=new Intent(ProfileListActivity.this,AddHealthProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                break;
        }

    }
}
