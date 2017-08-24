package com.hengrunjiankang.health.fragment;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.activity.AddDrugRecordActivity;
import com.hengrunjiankang.health.activity.AddHealthProfileActivity;
import com.hengrunjiankang.health.activity.DoctorDetailsActivity;
import com.hengrunjiankang.health.activity.DoctorListActivity;
import com.hengrunjiankang.health.activity.ProfileListActivity;
import com.hengrunjiankang.health.activity.RecordActivity;
import com.hengrunjiankang.health.entity.AddDrugRecordEntity;
import com.hengrunjiankang.health.entity.DoctorEntity;
import com.hengrunjiankang.health.adapter.HomeListAdapter;
import com.hengrunjiankang.health.adapter.ImagePagerAdapter;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.photo.SelectPhotoActivity;
import com.hengrunjiankang.health.widget.CirclePageIndicator;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/27.
 */

public class HomeFragment extends BaseFragment {
    private ViewPager banner;
    private CirclePageIndicator mIndicator;
    private ListView mListView;
    private ArrayList<DoctorEntity> listData;
    private HomeListAdapter mListAdapter;
    private RadioGroup rgSort;

    @Override
    protected int setLayout() {
        return R.layout.fragment_new_home;
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
        mListView=view.findViewById(R.id.lv_home_list);
        View listFooter= LayoutInflater.from(mActivity).inflate(R.layout.layout_home_list_footer,null);
        listFooter.findViewById(R.id.tv_home_listmore).setOnClickListener(this);
        mListView.addFooterView(listFooter);
        rgSort=view.findViewById(R.id.rg_home_sort);
//        view.findViewById(R.id.ll_home_6).setOnClickListener(this);
//        view.findViewById(R.id.ll_home_7).setOnClickListener(this);
//        view.findViewById(R.id.ll_home_8).setOnClickListener(this);
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
        mListAdapter=new HomeListAdapter(mActivity);
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent intent=new Intent(mActivity,DoctorDetailsActivity.class);
                intent.putExtra("id",listData.get(pos).getId());
                startActivity(intent);
                mActivity.overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
            }
        });
    }

    @Override
    protected void getData() {
        mListView.setAdapter(mListAdapter);
        rgSort.check(R.id.rb_home_1);
    }
    private void initList(String sort){
        new CommonHttp(mActivity, new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    json = jsonObject.getJSONArray("Items").toString();
                    listData = (ArrayList<DoctorEntity>) JSON.parseArray(json, DoctorEntity.class);
                    if (listData != null && listData.size() > 0) {
                        mListAdapter.setData(listData);
                        mListAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    Toast.makeText(mActivity,"数据出错",Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void requestFail(String msg) {

            }

            @Override
            public void requestAbnormal(int code) {

            }
        }).doRequest(UrlObject.QUERYDOCTORINFO+"?QueryLimit=5&PageIndex=1&Sort="+sort);
    }
    @Override
    public void onClick(View view) {
//        int pos=Integer.parseInt((String)view.getTag());
//        RecordActivity.type=pos-1;
//        startActivity(new Intent(mActivity,RecordActivity.class));
//        mActivity.overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
        switch (view.getId()){
            case R.id.ll_home_1:
                startActivity(new Intent(mActivity,RecordActivity.class));
                mActivity.overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                break;
            case R.id.ll_home_2:
                startActivity(new Intent(mActivity,AddDrugRecordActivity.class));
                mActivity.overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                break;
            case R.id.ll_home_3:
                startActivity(new Intent(mActivity,ProfileListActivity.class));
                mActivity.overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                break;
            case R.id.tv_home_listmore:
                Intent intent=new Intent(mActivity,DoctorListActivity.class);
                startActivity(intent);
                mActivity.overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                break;
        }

    }
}
