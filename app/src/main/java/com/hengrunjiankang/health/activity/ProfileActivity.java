package com.hengrunjiankang.health.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ClobSeriliazer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.applaction.MyApplication;
import com.hengrunjiankang.health.entity.ProfileEntity;
import com.hengrunjiankang.health.entity.ProfileListEntity;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/17.
 */

public class ProfileActivity extends BaseActivity {
    private ImageView ivYYJC1, ivYYJC2, ivYYJC3;
    private ImageView ivBL1, ivBL2, ivBL3;
    private ImageView ivYYFA1, ivYYFA2, ivYYFA3;
    private LinearLayout llYYJC,llYYFA,llBL,llillness,llother;
    private TextView tvTime,tvNumber,tvIllness,tvOther;

    @Override
    protected int setLayout() {
        return R.layout.activity_profile_details;
    }

    @Override
    protected void findView() {
        ivYYJC1 = (ImageView) findViewById(R.id.iv_profile_yyjc1);
        ivYYJC2 = (ImageView) findViewById(R.id.iv_profile_yyjc2);
        ivYYJC3 = (ImageView) findViewById(R.id.iv_profile_yyjc3);
        ivYYFA1 = (ImageView) findViewById(R.id.iv_profile_yyfa1);
        ivYYFA2 = (ImageView) findViewById(R.id.iv_profile_yyfa2);
        ivYYFA3 = (ImageView) findViewById(R.id.iv_profile_yyfa3);
        ivBL1 = (ImageView) findViewById(R.id.iv_profile_bl1);
        ivBL2 = (ImageView) findViewById(R.id.iv_profile_bl2);
        ivBL3 = (ImageView) findViewById(R.id.iv_profile_bl3);
        llYYJC=(LinearLayout)findViewById(R.id.ll_profile_yyjc);
        llYYFA=(LinearLayout)findViewById(R.id.ll_profile_yyfa);
        llBL=(LinearLayout)findViewById(R.id.ll_profile_bl);
        llillness=(LinearLayout)findViewById(R.id.ll_profile_illness);
        llother=(LinearLayout)findViewById(R.id.ll_profile_other_illness);
        tvTime=(TextView)findViewById(R.id.tv_profile_time);
        tvNumber=(TextView)findViewById(R.id.tv_profile_num);
        tvIllness=(TextView)findViewById(R.id.tv_profile_illness);
        tvOther=(TextView)findViewById(R.id.tv_profile_other_illness);

    }
    private HashMap<String,String> mapString;
    @Override
    protected void createObject() {
        glide = Glide.with(this);

    }

    @Override
    protected void setListener() {

    }
    protected RequestManager glide;

    @Override
    protected void getData() {
        initTitle("健康档案");
        String url=UrlObject.QUERYHEALTHRECORD+"&id="+getIntent().getStringExtra("id")+"&IsGetOne=true";
        new CommonHttp(ProfileActivity.this,new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                dismissPDialog();
                ProfileEntity entity=JSON.parseObject(json,ProfileEntity.class) ;
                tvTime.setText( entity.getRecordTime().substring(0,10)+" "+entity.getRecordTime().substring(11,19));
                tvNumber.setText(entity.getFriendlyId());


                if(entity.getId().equals("")){
                    llillness.setVisibility(View.GONE);
                }else{
                    tvIllness.setText(entity.getIllStr());
                }
                if(entity.getRemark()!=null&&entity.getRemark().length()>0){
                    tvOther.setText(entity.getRemark());
                }else{
                    llother.setVisibility(View.GONE);
                }
                if(entity.getDalx_Yyjc()!=null&&entity.getDalx_Yyjc().length()>0){
                    String []yyjc=entity.getDalx_Yyjc().split(";");
                    if(yyjc.length>0){
                        setImage(UrlObject.BASEURL+yyjc[0],ivYYJC1);
                    }
                    if(yyjc.length>1){
                        setImage(UrlObject.BASEURL+yyjc[1],ivYYJC2);
                    }
                    if(yyjc.length>2){
                        setImage(UrlObject.BASEURL+yyjc[2],ivYYJC3);
                    }
                }else{
                    llYYJC.setVisibility(View.GONE);
                }
                if(entity.getDalx_Bl()!=null&&entity.getDalx_Bl().length()>0){
                    String []bl=entity.getDalx_Bl().split(";");
                    if(bl.length>0){
                        setImage(UrlObject.BASEURL+bl[0],ivBL1);
                    }
                    if(bl.length>1){
                        setImage(UrlObject.BASEURL+bl[1],ivBL2);
                    }
                    if(bl.length>2){
                        setImage(UrlObject.BASEURL+bl[2],ivBL3);
                    }
                }else{
                    llBL.setVisibility(View.GONE);
                }
                if(entity.getDalx_Yyfa()!=null&&entity.getDalx_Yyfa().length()>0){
                    String []yyfa=entity.getDalx_Yyfa().split(";");
                    if(yyfa.length>0){
                        setImage(UrlObject.BASEURL+yyfa[0],ivYYFA1);
                    }
                    if(yyfa.length>1){
                        setImage(UrlObject.BASEURL+yyfa[1],ivYYFA2);
                    }
                    if(yyfa.length>2){
                        setImage(UrlObject.BASEURL+yyfa[2],ivYYFA3);
                    }
                }else{
                    llYYFA.setVisibility(View.GONE);
                }

            }

            @Override
            public void requestFail(String msg) {
                Toast.makeText(ProfileActivity.this,msg,Toast.LENGTH_SHORT).show();
                dismissPDialog();
                Log.e("data", msg);
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(ProfileActivity.this, getString(R.string.net_error)+code,Toast.LENGTH_SHORT).show();
                dismissPDialog();
            }
        }).doRequest(url);
    }
    private  void setImage(String url,ImageView view){
        glide.load(new GlideUrl(url,new LazyHeaders.Builder().addHeader("Cookie", MyApplication.cookie).addHeader("X-Requested-With","XMLHttpRequest").build())).into(view);
//        final ImageView pic=view;
//        new CommonHttp(ProfileActivity.this,new CommonHttpCallback() {
//            @Override
//            public void requestSeccess(String json) {
//                Log.e("pic",json);
//                try {
//                    JSONObject jsonObj=new JSONObject(json);
//                    Bitmap bitmap =stringtoBitmap(jsonObj.getString("Picturedata"));
////                    bitmap= CommonUtils.resizeBitmap(bitmap,screenHeight);
//                    pic.setImageBitmap(bitmap);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void requestFail(String msg) {
//
//            }
//
//            @Override
//            public void requestAbnormal(int code) {
//
//            }
//        }).doRequest(url);
    }
    public Bitmap stringtoBitmap(String string){
        //将字符串转换成Bitmap类型
        Bitmap bitmap=null;
        try {
            byte[]bitmapArray;
            bitmapArray= Base64.decode(string, Base64.DEFAULT);
            bitmap= BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                break;
        }
    }
}
