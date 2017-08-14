package com.hengrunjiankang.health.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.applaction.MyApplication;
import com.hengrunjiankang.health.entity.EcgEntity;
import com.hengrunjiankang.health.entity.EcgIndexEntity;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.util.CommonUtils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


/**
 * Created by Administrator on 2017/7/21.
 */

public class EcgDetailsActivity extends BaseActivity {
    protected RequestManager glide;

    private ImageView pic;
    private LinearLayout llContent;

    @Override
    protected int setLayout() {
        return R.layout.activity_ecg_index;
    }

    @Override
    protected void findView() {
        pic=findViewById(R.id.iv_index_pic);
        llContent = (LinearLayout) findViewById(R.id.ll_index_content);
    }

    @Override
    protected void createObject() {
        glide = Glide.with(this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void getData() {
        initTitle(getIntent().getStringExtra("title"));
//        String temp=bitmaptoString(BitmapFactory.decodeResource(getResources(),R.mipmap.img_loading));
//        pic.setImageBitmap(stringtoBitmap(temp));
        initData();
    }
    private void initData(){
        LayoutInflater inflater = LayoutInflater.from(this);
        EcgEntity ecgEntity =(EcgEntity) getIntent().getSerializableExtra("data");
        String url= UrlObject.QUREYECGPIC+"&EcgStructV1Id="+ecgEntity.getId();
        ArrayList<EcgIndexEntity> listEcg=new ArrayList<>();
        listEcg.add(new EcgIndexEntity("检量日期:",ecgEntity.getCollectdate().substring(0,10)));
        listEcg.add(new EcgIndexEntity("检量时间:",ecgEntity.getCollectdate().substring(11,19)));
        listEcg.add(new EcgIndexEntity("记录长度:","20s"));
        listEcg.add(new EcgIndexEntity("平均心率:",ecgEntity.getAvr_heartrate()));
        listEcg.add(new EcgIndexEntity("PR间期 \n(120-200MS)",ecgEntity.getAvr_pr()+"MS"));
        listEcg.add(new EcgIndexEntity("QT间期 \n(340-430MS)",ecgEntity.getAvr_qt()+"MS"));
        listEcg.add(new EcgIndexEntity("QTC间期 \n(350-460MS)",ecgEntity.getAvr_qtc()+"MS"));


        listEcg.add(new EcgIndexEntity("RR间期 \n(600-1000MS)",ecgEntity.getAvr_rr()+"MS"));
        listEcg.add(new EcgIndexEntity("P波幅度 \n(0-0.25MV)",ecgEntity.getAvr_pvolt()+"MV"));
        listEcg.add(new EcgIndexEntity("R波幅度 \n(0.5-2.0MV)",ecgEntity.getAvr_rvolt()+"MV"));
        listEcg.add(new EcgIndexEntity("T波幅度 \n(0.1-0.5MV)",ecgEntity.getAvr_tvolt()+"MV"));
        for (int i = 0; i < listEcg.size(); i++) {
            LinearLayout sub = (LinearLayout) inflater.inflate(R.layout.item_ecg_list, null);
            TextView tvName = sub.findViewById(R.id.tv_item_name);
            tvName.setText(listEcg.get(i).getName());
            TextView tvIndex = sub.findViewById(R.id.tv_item_index);
            tvIndex.setText(listEcg.get(i).getIndex());
            llContent.addView(sub);
        }
        getScreenParam();
//        getPic(url);
        new CommonHttp(EcgDetailsActivity.this,new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                Log.e("pic",json);
                try {
                    JSONObject jsonObj=new JSONObject(json);
                   Bitmap bitmap =stringtoBitmap(jsonObj.getString("Picturedata"));
                    bitmap=CommonUtils.resizeBitmap(bitmap,screenHeight);
                    pic.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFail(String msg) {

            }

            @Override
            public void requestAbnormal(int code) {

            }
        }).doRequest(url);
    }
    public String bitmaptoString(Bitmap bitmap){
//将Bitmap转换成字符串
        String string=null;
        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bStream);
        byte[]bytes=bStream.toByteArray();
        string= Base64.encodeToString(bytes,Base64.DEFAULT);
        return string;
    }
    public Bitmap stringtoBitmap(String string){
        //将字符串转换成Bitmap类型
        Bitmap bitmap=null;
        try {
            byte[]bitmapArray;
            bitmapArray=Base64.decode(string, Base64.DEFAULT);
            bitmap=BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    private  void getPic(String url){
        glide.load(new GlideUrl(url,new LazyHeaders.Builder().addHeader("Cookie", MyApplication.cookie).build())).into(pic);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                this.finish();
                overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
                break;
        }
    }
}
