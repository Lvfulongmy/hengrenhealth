package com.hengrunjiankang.health.activity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.adapter.BegpockJudgeAdapter;
import com.hengrunjiankang.health.applaction.MyApplication;
import com.hengrunjiankang.health.entity.DoctorEntity;
import com.hengrunjiankang.health.entity.ReservationExpertEntity;
import com.hengrunjiankang.health.entity.TagEntity;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.util.CommonUtils;
import com.hengrunjiankang.health.widget.FlowLayout;
import com.hengrunjiankang.health.widget.RatingBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2017/8/21.
 */

public class DoctorDetailsActivity extends BaseActivity {
    private PullToRefreshListView mListView;
    private LinearLayout llYSJJ, llSCLY, llYSZZ;
    private TextView tvYSJJ, tvSCLY, tvName, tvDuties, tvUnit,tvBespock;
    private BegpockJudgeAdapter mJudgeAdapter;
    private ImageView ivHeadPic;
    protected RequestManager glide;
    private FlowLayout flTagGroup;

    @Override
    protected int setLayout() {
        return R.layout.activity_doctor_details;
    }

    @Override
    protected void findView() {
        mListView = (PullToRefreshListView) findViewById(R.id.lv_judge_list);
        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_doctor_details_header, null);
        mListView.getRefreshableView().addHeaderView(headerView);
        mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        llYSJJ = (LinearLayout) findViewById(R.id.ll_doctor_ysjj);
        llSCLY = (LinearLayout) findViewById(R.id.ll_doctor_scly);
        llYSZZ = (LinearLayout) findViewById(R.id.ll_doctor_yszz);
        tvYSJJ = (TextView) findViewById(R.id.tv_doctor_ysjj);
        tvSCLY = (TextView) findViewById(R.id.tv_doctor_scly);
        tvName = (TextView) findViewById(R.id.tv_doctor_name);
        tvDuties = (TextView) findViewById(R.id.tv_doctor_duties);
        tvUnit = (TextView) findViewById(R.id.tv_doctor_unit);
        tvBespock=(TextView)findViewById(R.id.tv_doctor_bespock);
        ivHeadPic = (ImageView) findViewById(R.id.iv_doctor_header);
        flTagGroup=(FlowLayout)findViewById(R.id.fl_doctor_taggroup);
//        tvYSJJ.setText("就这些内容\n就这些内容\n就这些内容\n就这些内容\n就这些内容\n就这些内容\n就这些内容\n就这些内容\n就这些内容\n就这些内容");
//        tvSCLY.setText("就这些内容\n就这些内容\n就这些内容\n就这些内容\n就这些内容\n就这些内容\n就这些内容\n就这些内容\n就这些内容\n就这些内容");
    }

    private void toShow(View view) {
        view.setVisibility(View.VISIBLE);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
//        TranslateAnimation animation=new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
//        animation.setDuration(500);
        scaleAnimation.setDuration(50);
        view.setAnimation(scaleAnimation);
        scaleAnimation.startNow();
    }

    private void toDismiss(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1, 1, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        TranslateAnimation animation = new TranslateAnimation(0, 0, view.getTop(), view.getBottom());
        scaleAnimation.setDuration(50);
        view.setAnimation(scaleAnimation);
        scaleAnimation.startNow();
        view.setVisibility(View.GONE);
    }


    @Override
    protected void createObject() {
        mJudgeAdapter = new BegpockJudgeAdapter(this);
        mListView.setAdapter(mJudgeAdapter);
        glide = Glide.with(this);
        ExpertId=getIntent().getStringExtra("id");

//        RatingBar ratingBar = (RatingBar) findViewById(R.id.rb);
//        ratingBar.setClickable(true);//设置可否点击
//        ratingBar.setStar(2.5f);
//        ratingBar.setStepSize(RatingBar.StepSize.Half);//设置每次点击增加一颗星还是半颗星
//        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
//            @Override
//            public void onRatingChange(float ratingCount) {//点击星星变化后选中的个数
//                Log.d("RatingBar", "RatingBar-Count=" + ratingCount);
//            }
//        });
//

    }

    @Override
    protected void setListener() {
        llSCLY.setOnClickListener(this);
        llYSJJ.setOnClickListener(this);
        llYSZZ.setOnClickListener(this);
        tvBespock.setOnClickListener(this);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                    initJudgeList(pageIndex+1);
            }
        });
    }
    private int pageIndex=1;
    @Override
    protected void getData() {
            initTitle("专家详情");
            initDoctor();
            initJudgeTotle();
            initJudgeList(pageIndex);

    }
    private String ExpertId="";
    private void initDoctor() {
        new CommonHttp(this, new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                DoctorEntity entity = JSON.parseObject(json, DoctorEntity.class);
                setImage(UrlObject.BASEURL+entity.getSmallImageUrl(),ivHeadPic);
                tvName.setText(entity.getName());
                tvDuties.setText(entity.getDuties());
                tvUnit.setText(entity.getHospital());
                tvYSJJ.setText(entity.getProfile());
                tvSCLY.setText(entity.getExpertise());
            }

            @Override
            public void requestFail(String msg) {

            }

            @Override
            public void requestAbnormal(int code) {

            }
        }).doRequest(UrlObject.QUERYDOCTORINFO + "?Id=" + ExpertId+ "&IsGetOne=true");
    }
    private void initJudgeTotle(){
        new CommonHttp(this, new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                ArrayList<TagEntity> taglist=(ArrayList<TagEntity>) JSON.parseArray(json,TagEntity.class);
//                DoctorEntity entity = JSON.parseObject(json, DoctorEntity.class);
//                setImage(UrlObject.BASEURL+entity.getSmallImageUrl(),ivHeadPic);
//                tvName.setText(entity.getName());
//                tvDuties.setText(entity.getDuties());
//                tvUnit.setText(entity.getHospital());
//                tvYSJJ.setText(entity.getProfile());
//                tvSCLY.setText(entity.getExpertise());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                int margin= CommonUtils.dip2px(DoctorDetailsActivity.this, 10);
                layoutParams.setMargins(margin,margin/2,0,margin/2);
                for(int i = 0; i<taglist.size(); i++){
                    TextView tv=(TextView) LayoutInflater.from(DoctorDetailsActivity.this).inflate(R.layout.item_judge_tag,null);
                    tv.setText(taglist.get(i).getTag()+"("+taglist.get(i).getTagCount()+")");
                    tv.setLayoutParams(layoutParams);
                    flTagGroup.addView(tv);
                }

            }

            @Override
            public void requestFail(String msg) {

            }

            @Override
            public void requestAbnormal(int code) {

            }
        }).doRequest(UrlObject.QUERYEXPERTEVALUATETAG + "?ExpertId=" + ExpertId);
    }
    private void initJudgeList(int page){
        final int tempIndex=page;
        new CommonHttp(this, new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                try {
                    JSONObject jsonObject=new JSONObject(json);
                    json=jsonObject.getJSONArray("Items").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                    mListView.onRefreshComplete();
                }
                ArrayList<ReservationExpertEntity> data = (ArrayList<ReservationExpertEntity>) JSON.parseArray(json, ReservationExpertEntity.class);
                if(tempIndex==1) {

                    mJudgeAdapter.setData(data);
                    mJudgeAdapter.notifyDataSetChanged();
                    if(data.size()==0){
                        mListView.setMode(PullToRefreshBase.Mode.DISABLED);
                    }
                }else{
                    if(data.size()>0) {
                        pageIndex = tempIndex;
                        mJudgeAdapter.getData().addAll(data);
                        mJudgeAdapter.notifyDataSetChanged();
                    }
                    mListView.onRefreshComplete();
                }
            }

            @Override
            public void requestFail(String msg) {
                Toast.makeText(DoctorDetailsActivity.this,msg,Toast.LENGTH_SHORT).show();
                mListView.onRefreshComplete();
            }

            @Override
            public void requestAbnormal(int code) {
                mListView.onRefreshComplete();
                Toast.makeText(DoctorDetailsActivity.this, R.string.net_error+code,Toast.LENGTH_SHORT).show();
            }
        }).doRequest(UrlObject.QUERYRESERVATIONEXPERT + "?ExpertId=" + getIntent().getStringExtra("id")+"&QueryLimit=10&PageIndex="+page);
    }
    private void setImage(String url, ImageView view) {
        glide.load(new GlideUrl(url, new LazyHeaders.Builder().addHeader("Cookie", MyApplication.cookie).addHeader("X-Requested-With", "XMLHttpRequest").build())).into(view);
    }

    private boolean isYysjShow = true;
    private boolean isSclyShow = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                break;

            case R.id.ll_doctor_scly:
                if (isSclyShow) {
//                    toDismiss(tvSCLY);
                    tvSCLY.setVisibility(View.GONE);
                    isSclyShow = false;
                } else {
//                    toShow(tvSCLY);
                    tvSCLY.setVisibility(View.VISIBLE);
                    isSclyShow = true;
                }
                break;
            case R.id.ll_doctor_ysjj:
                if (isYysjShow) {
//                    toDismiss(tvYSJJ);
                    tvYSJJ.setVisibility(View.GONE);
                    isYysjShow = false;
                } else {
//                    toShow(tvYSJJ);
                    tvYSJJ.setVisibility(View.VISIBLE);
                    isYysjShow = true;
                }
                break;
            case R.id.ll_doctor_yszz:

                break;
            case R.id.tv_doctor_bespock:
                Intent intent=new Intent(DoctorDetailsActivity.this,BespockDoctorActivity.class);
                intent.putExtra("ExpertId",ExpertId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                break;
        }
    }
}
