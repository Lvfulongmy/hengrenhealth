package com.hengrunjiankang.health.activity;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.entity.LineData;
import com.hengrunjiankang.health.entity.PointData;
import com.hengrunjiankang.health.widget.MyView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/12.
 */

public class TestCellActivity extends BaseActivity {
    private TextView change;
    private MyView cellView;
    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void findView() {
        change=(TextView)findViewById(R.id.change);
        cellView=(MyView)findViewById(R.id.cell);
        initTest(MyView.DAY,System.currentTimeMillis());
        cellView.setMode(MyView.DAY,System.currentTimeMillis());
    }
    private void initTest(int mode, long starttime){
        Calendar cl=Calendar.getInstance();
        cl.set(Calendar.HOUR_OF_DAY, 0);
        cl.set(Calendar.MINUTE, 0);
        cl.set(Calendar.SECOND, 0);
        cl.set(Calendar.MILLISECOND, 0);
        ArrayList<LineData> data=new ArrayList<>();
        LineData lineData=new LineData();
        ArrayList<PointData> pointDatas=new ArrayList<>();
        lineData.setDate(pointDatas);
        lineData.setColor(R.color.colorAccent);
        data.add(lineData);
        switch (mode){
            case MyView.DAY:

                for(int i=0;i<24;i++){
                    PointData pointData=new PointData();
                    pointData.setDate(cl.getTimeInMillis()+(i*60*60*1000));
                    pointData.setIndex(new Random().nextInt(100));
                    pointDatas.add(pointData);
                }
                break;
            case MyView.WEEK:
                for(int i=0;i<7;i++){
                    PointData pointData=new PointData();
                    pointData.setDate(cl.getTimeInMillis()+(i*60*60*1000*24));
                    pointData.setIndex(new Random().nextInt(100));
                    pointDatas.add(pointData);
                }
                break;
            case MyView.MONTH:
                for(int i=0;i<30;i++){
                    PointData pointData=new PointData();
                    pointData.setDate(cl.getTimeInMillis()+(i*60*60*1000*24));
                    pointData.setIndex(new Random().nextInt(100));
                    pointDatas.add(pointData);
                }
                break;

        }
        cellView.setLineData(data);
    }

    @Override
    protected void createObject() {

    }

    @Override
    protected void setListener() {
        change.setOnClickListener(this);
    }

    @Override
    protected void getData() {
//        SharedPreferences sPreferences = getBaseContext().getSharedPreferences("UserInfo", MODE_PRIVATE);
//        String cookie=sPreferences.getString("cookie",null);
//        HashMap<String,String > params=new HashMap<>();
//        params.put("MobileNumber",etUsername.getText().toString());
//        params.put("Password",etPwd.getText().toString());
//
//        new CommonHttpPostRequest(new CommonHttpCallback() {
//            @Override
//            public void requestSeccess(String json) {
//
//                SharedPreferences sPreferences = getBaseContext().getSharedPreferences("UserInfo", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sPreferences.edit();
//                //当然sharepreference会对一些特殊的字符进行转义，使得读取的时候更加准确
////                editor.putString("username", username);
////                editor.putString("password", password);
//                editor.putString("cookie", json);
//                editor.putBoolean("islog", true);
//                editor.commit();
//
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
//        },null).execute(UrlObject.LOGINURL,params);

    }
    int count;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change:
                int i=count++%3;
                initTest(i,System.currentTimeMillis());
                 cellView.setMode(i,System.currentTimeMillis());
                cellView.invalidate();
                break;
        }
    }
}
