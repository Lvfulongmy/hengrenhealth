package com.hengrunjiankang.health.fragment;

import android.view.View;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.entity.LineData;
import com.hengrunjiankang.health.entity.PointData;
import com.hengrunjiankang.health.entity.YTextData;
import com.hengrunjiankang.health.widget.MyView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/13.
 */

public class MyTestFragment extends BaseFragment {
    private TextView change;
    private MyView cellView;
    public static int mode;
    YTextData yTextData=new YTextData();

    @Override
    protected int setLayout() {
        return R.layout.layout_cellview;
    }

    @Override
    protected void findView(View view) {
        change=(TextView)view.findViewById(R.id.change);
        cellView=(MyView)view.findViewById(R.id.cellview);
        initTest(mode,System.currentTimeMillis());
        ArrayList<String> yTextList=new ArrayList<String>();
        for (int i = 100; i > 0; i -= 10) {
            yTextList.add(i + "%");
        }

        yTextData.setYtextlist(yTextList);
        yTextData.setYstep(10);
        cellView.setMode(MyView.DAY,System.currentTimeMillis(),yTextData);
    }
    private void initTest(int mode, long starttime){
        Calendar cl=Calendar.getInstance();
        cl.setTimeInMillis(starttime);
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
                    pointData.setDate(cl.getTimeInMillis());
                    pointData.setIndex(new Random().nextInt(100));
                    pointDatas.add(pointData);
                    cl.setTimeInMillis(cl.getTimeInMillis()+60*60*1000*24);
                }
                break;

        }
        cellView.setLineData(data);
    }

    @Override
    protected void createObject() {
        count=mode;
    }

    @Override
    protected void setListener() {
        change.setOnClickListener(this);
    }

    @Override
    protected void getData() {


    }
    public void splash(long time){
        if(cellView!=null) {
            initTest(mode, time);
            cellView.setMode(mode, time,yTextData);
            cellView.invalidate();
        }
    }
    int count;
    @Override
    public void onClick(View v) {

    }
}
