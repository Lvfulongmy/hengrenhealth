package com.hengrunjiankang.health.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.activity.RecordActivity;
import com.hengrunjiankang.health.entity.BloodPressureDataEntity;
import com.hengrunjiankang.health.entity.BloodSugarDataEntity;
import com.hengrunjiankang.health.entity.FatDataEntity;
import com.hengrunjiankang.health.entity.LineData;
import com.hengrunjiankang.health.entity.OxygenDataEntity;
import com.hengrunjiankang.health.entity.PointData;
import com.hengrunjiankang.health.entity.WeightEntity;
import com.hengrunjiankang.health.entity.YTextData;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.util.CommonUtils;
import com.hengrunjiankang.health.widget.MyView;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/13.
 */

public class CellFragment extends BaseFragment {
    private MyView cellView;
    public static int mode;
    YTextData yTextData = new YTextData();

    @Override
    protected int setLayout() {
        return R.layout.layout_cellview;
    }

    @Override
    protected void findView(View view) {
        cellView = (MyView) view.findViewById(R.id.cellview);
        cellView.setTextYSize(CommonUtils.dip2px(mActivity,12));
        cellView.setTextXSize(CommonUtils.dip2px(mActivity,8));
        cellView.setXleftoffset(CommonUtils.dip2px(mActivity,30));


    }

    private void initTest(int mode, long starttime) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(starttime);
        cl.set(Calendar.HOUR_OF_DAY, 0);
        cl.set(Calendar.MINUTE, 0);
        cl.set(Calendar.SECOND, 0);
        cl.set(Calendar.MILLISECOND, 0);
        ArrayList<LineData> data = new ArrayList<>();
        LineData lineData = new LineData();
        ArrayList<PointData> pointDatas = new ArrayList<>();
        lineData.setDate(pointDatas);
        lineData.setColor(R.color.colorAccent);
        data.add(lineData);
        switch (mode) {
            case MyView.DAY:

                for (int i = 0; i < 24; i++) {
                    PointData pointData = new PointData();
                    pointData.setDate(cl.getTimeInMillis() + (i * 60 * 60 * 1000));
                    pointData.setIndex(new Random().nextInt(100));
                    pointDatas.add(pointData);
                }
                break;
            case MyView.WEEK:
                for (int i = 0; i < 7; i++) {
                    PointData pointData = new PointData();
                    pointData.setDate(cl.getTimeInMillis() + (i * 60 * 60 * 1000 * 24));
                    pointData.setIndex(new Random().nextInt(100));
                    pointDatas.add(pointData);
                }
                break;
            case MyView.MONTH:
                for (int i = 0; i < 30; i++) {
                    PointData pointData = new PointData();
                    pointData.setDate(cl.getTimeInMillis());
                    pointData.setIndex(new Random().nextInt(100));
                    pointDatas.add(pointData);
                    cl.setTimeInMillis(cl.getTimeInMillis() + 60 * 60 * 1000 * 24);
                }
                break;

        }
        cellView.setLineData(data);
    }

    @Override
    protected void createObject() {
        count = mode;
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void getData() {
        splash(RecordActivity.nowtime,RecordActivity.type);
    }

    public void splash(long time, int type) {
        if(mActivity!=null){
            showPDialog();
        }
        final long stratTime=time;
        final int checkType=type;
        ArrayList<String> yTextList = new ArrayList<String>();
       SimpleDateFormat  formatTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTimeStr= formatTime.format(RecordActivity.getEndTime(time,mode));
        String stratTimeStr=formatTime.format(time);
        String url=null;
        String urlParam="&Sort=Collectdate&CollectdateMin="+stratTimeStr+"&CollectdateMax="+endTimeStr;
        switch (type) {
            case 0://体脂
                for (int i = 100; i >= 0; i -= 10) {
                    yTextList.add(i + "%");
                }
                yTextData.setYtextlist(yTextList);
                yTextData.setYstep(100);
                yTextData.setyTextStep(yTextList.size()-1);
                url=UrlObject.QUERYFATDATAURL+urlParam+"&SpecifyProperty=Collectdate;Bmi";

                break;
            case 1://血糖
                for (int i = 15; i >=0; i -= 3) {
                    yTextList.add(i+"");
                }
                yTextData.setYtextlist(yTextList);
                yTextData.setYstep(150);
                yTextData.setyTextStep(yTextList.size()-1);
                url=UrlObject.QUERYBGDATA+urlParam+"&SpecifyProperty=Collectdate;Bloodsugar";
                break;
            case 2://血氧
                for (int i = 100; i >=70; i -= 10) {
                    yTextList.add(i+"%");
                }
                yTextData.setYtextlist(yTextList);
                yTextData.setYstep(100);
                yTextData.setyTextStep(yTextList.size()-1);
                url=UrlObject.QUERYOXYGENDATAURL+urlParam+"&SpecifyProperty=Collectdate;Oxygen";
                break;
            case 3://体重
                for (int i = 130; i >=30; i -= 20) {
                    yTextList.add(i+"");
                }
                yTextData.setYtextlist(yTextList);
                yTextData.setYstep(100);
                yTextData.setyTextStep(yTextList.size()-1);
                url=UrlObject.QUERYWEIGHTDATAURL+urlParam+"&SpecifyProperty=Collectdate;weight";
                break;
            case 4://血压
                for (int i = 200; i >=60; i -= 20) {
                    yTextList.add(i+"");
                }
                yTextData.setYtextlist(yTextList);
                yTextData.setYstep(140);
                yTextData.setyTextStep(yTextList.size()-1);
                url=UrlObject.QUERYBLOODPRESSURE+urlParam+"&SpecifyProperty=Collectdate;Diastolicpressure;Systolicpressure";
                break;
        }
        if (cellView != null) {
            try {
                new CommonHttp(mActivity,new CommonHttpCallback() {
                    @Override
                    public void requestSeccess(String json) {
                        dismissPDialog();
//                        initTest(mode, stratTime);
                        initData(json,checkType);
                        cellView.setMode(mode, stratTime, yTextData);
                        cellView.invalidate();
                        Log.e("data", json);
                    }

                    @Override
                    public void requestFail(String msg) {
                        dismissPDialog();
                        Log.e("data", msg);
                    }

                    @Override
                    public void requestAbnormal(int code) {
                        dismissPDialog();
                    }

                }).doRequest(url);

            } catch (Exception e){
                dismissPDialog();
            }


        }
    }
    private void initData(String json,int type){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        ArrayList<LineData> allLineData = new ArrayList<>();
        LineData lineData = new LineData();
        ArrayList<PointData> pointDatas = new ArrayList<>();
        lineData.setDate(pointDatas);
        lineData.setColor(R.color.orange1);
        allLineData.add(lineData);
        switch (type) {
            case 0:
            ArrayList<FatDataEntity> data=   (ArrayList<FatDataEntity>) JSON.parseArray(json, FatDataEntity.class);

            for (int i = 0; i < data.size(); i++) {
                try {
                    String temp = data.get(i).getCollectdate().substring(0, 19).replace('T', ' ');
                    calendar.setTime(format.parse(temp));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                PointData pointData = new PointData();
                pointData.setDate(calendar.getTimeInMillis());
                pointData.setIndex((int) Math.floor(data.get(i).getBmi()));
                pointData.setRealIndex(data.get(i).getBmi());
                pointDatas.add(pointData);
            }
            break;
            case 1:
                ArrayList<BloodSugarDataEntity> data1=   (ArrayList<BloodSugarDataEntity>) JSON.parseArray(json, BloodSugarDataEntity.class);

                for (int i = 0; i < data1.size(); i++) {
                    try {
                        String temp = data1.get(i).getCollectdate().substring(0, 19).replace('T', ' ');
                        calendar.setTime(format.parse(temp));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    PointData pointData = new PointData();
                    pointData.setDate(calendar.getTimeInMillis());
                    pointData.setIndex((int) Math.floor(data1.get(i).getBloodsugar()*10));
                    pointData.setRealIndex(data1.get(i).getBloodsugar());
                    pointDatas.add(pointData);
                }
                break;
            case 2:
                ArrayList<OxygenDataEntity> data2=   (ArrayList<OxygenDataEntity>) JSON.parseArray(json, OxygenDataEntity.class);

                for (int i = 0; i < data2.size(); i++) {
                    try {
                        String temp = data2.get(i).getCollectdate().substring(0, 19).replace('T', ' ');
                        calendar.setTime(format.parse(temp));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    PointData pointData = new PointData();
                    pointData.setDate(calendar.getTimeInMillis());
                    pointData.setIndex(data2.get(i).getOxygen());
                    pointData.setRealIndex(data2.get(i).getOxygen());
                    pointDatas.add(pointData);
                }
                break;
            case 3:
                ArrayList<WeightEntity> data3=   (ArrayList<WeightEntity>) JSON.parseArray(json, WeightEntity.class);

                for (int i = 0; i < data3.size(); i++) {
                    try {
                        String temp = data3.get(i).getCollectdate().substring(0, 19).replace('T', ' ');
                        calendar.setTime(format.parse(temp));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    PointData pointData = new PointData();
                    pointData.setDate(calendar.getTimeInMillis());
                    pointData.setIndex((int) Math.floor(data3.get(i).getWeight()-30));
                    pointData.setRealIndex(data3.get(i).getWeight());
                    pointDatas.add(pointData);
                }
                break;
            case 4:
                LineData lineData2 = new LineData();
                ArrayList<PointData> pointDatas2= new ArrayList<>();
                lineData2.setDate(pointDatas2);
                lineData2.setColor(R.color.blue);
                allLineData.add(lineData2);
                ArrayList<BloodPressureDataEntity> data4=   (ArrayList<BloodPressureDataEntity>) JSON.parseArray(json, BloodPressureDataEntity.class);

                for (int i = 0; i < data4.size(); i++) {
                    try {
                        String temp = data4.get(i).getCollectdate().substring(0, 19).replace('T', ' ');
                        calendar.setTime(format.parse(temp));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    PointData pointData = new PointData();
                    pointData.setDate(calendar.getTimeInMillis());
                    pointData.setIndex(data4.get(i).getSystolicpressure()-60);
                    pointData.setRealIndex(data4.get(i).getSystolicpressure());
                    pointDatas.add(pointData);
                    PointData pointData2 = new PointData();
                    pointData2.setDate(calendar.getTimeInMillis());
                    pointData2.setIndex(data4.get(i).getDiastolicpressure()-60);
                    pointData2.setRealIndex(data4.get(i).getDiastolicpressure());
                    pointDatas2.add(pointData2);
                }
                break;
        }
        cellView.setLineData(allLineData);

    }

    int count;

    @Override
    public void onClick(View v) {

    }
}
