package com.hengrunjiankang.health.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.adapter.HomeListAdapter;
import com.hengrunjiankang.health.entity.DoctorEntity;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/24.
 */

public class DoctorListActivity extends BaseActivity {
    private PullToRefreshListView lvDoctorList;
    private RadioGroup rgSort;
    private int pageIndex;
    private String url;
    private HomeListAdapter mListAdapter;
    @Override
    protected int setLayout() {
        return R.layout.activity_doctor_list;
    }

    @Override
    protected void findView() {
        rgSort=findViewById(R.id.rg_home_sort);
        lvDoctorList=findViewById(R.id.lv_doctor_list);
        lvDoctorList.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
    }

    @Override
    protected void createObject() {
        mListAdapter=new HomeListAdapter(this);
        lvDoctorList.setAdapter(mListAdapter);
    }

    @Override
    protected void setListener() {
        rgSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
                switch (id){
                    case R.id.rb_home_1:
                        initList("Evaluate");
                        break;
                    case R.id.rb_home_2:
                        initList("ConsultPrice");
                        break;
                    case R.id.rb_home_3:
                        initList("DutiesGrade");
                        break;
                }
            }
        });
        lvDoctorList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if(url!=null)
                    getListData(pageIndex+1);
            }
        });
        lvDoctorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent intent=new Intent(DoctorListActivity.this,DoctorDetailsActivity.class);
                intent.putExtra("id",mListAdapter.getData().get(pos-1).getId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
            }
        });
    }
    private void initList(String sort){
       url= UrlObject.QUERYDOCTORINFO+"?QueryLimit=10&Sort="+sort;
        pageIndex=1;
        new CommonHttp(this, new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    json = jsonObject.getJSONArray("Items").toString();
                    ArrayList<DoctorEntity> listData = (ArrayList<DoctorEntity>) JSON.parseArray(json, DoctorEntity.class);
                    if (listData != null && listData.size() > 0) {
                        mListAdapter.setData(listData);
                        mListAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    Toast.makeText(DoctorListActivity.this,"数据出错",Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void requestFail(String msg) {
                Toast.makeText(DoctorListActivity.this,msg,Toast.LENGTH_SHORT);
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(DoctorListActivity.this,getString(R.string.net_error)+code,Toast.LENGTH_SHORT);
            }
        }).doRequest(url+"&PageIndex=1");
    }
    private void getListData(int page){
        final int tempPage=page;
        new CommonHttp(this, new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                lvDoctorList.onRefreshComplete();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    json = jsonObject.getJSONArray("Items").toString();
                    ArrayList<DoctorEntity> listData = (ArrayList<DoctorEntity>) JSON.parseArray(json, DoctorEntity.class);
                    if (listData != null && listData.size() > 0) {
                        mListAdapter.getData().addAll(listData);
                        mListAdapter.notifyDataSetChanged();
                    }
                    pageIndex=tempPage;
                }catch (Exception e){
                    Toast.makeText(DoctorListActivity.this,"数据出错",Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void requestFail(String msg) {
                lvDoctorList.onRefreshComplete();
                Toast.makeText(DoctorListActivity.this,msg,Toast.LENGTH_SHORT);
            }

            @Override
            public void requestAbnormal(int code) {
                lvDoctorList.onRefreshComplete();
                Toast.makeText(DoctorListActivity.this,getString(R.string.net_error)+code,Toast.LENGTH_SHORT);
            }
        }).doRequest(url+"&PageIndex="+page);
    }



    @Override
    protected void getData() {
        initTitle("专家库");
        rgSort.check(R.id.rb_home_1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                break;

        }
    }
}
