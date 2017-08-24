package com.hengrunjiankang.health.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.adapter.AddDrugRecordAdapter;
import com.hengrunjiankang.health.adapter.ProfileListAdapter;
import com.hengrunjiankang.health.entity.AddDrugRecordEntity;
import com.hengrunjiankang.health.entity.ProfileListEntity;
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

public class AddDrugRecordActivity extends BaseActivity {
    private CustomDatePicker pickerStartTime;
    private CustomDatePicker pickerEndTime;
    private ListView lvRecordList;
    private TextView tvStratTime;
    private TextView tvEndTime;
    private long starttime=-1;
    private long endtime=-1;
    private int pageIndex=1;
    private SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd");
    private  AddDrugRecordAdapter addDrugRecordAdapter;
    private LinearLayout llNodata;
    private  TextView tvAdd;



    @Override
    protected int setLayout() {
        return R.layout.activity_add_drug;
    }

    @Override
    protected void findView() {
        lvRecordList = (ListView) findViewById(R.id.lv_record_list);
        tvStratTime=findViewById(R.id.tv_record_starttime);
        tvEndTime=findViewById(R.id.tv_record_endtime);
        llNodata=(LinearLayout)findViewById(R.id.ll_nodata);
        tvAdd=(TextView)findViewById(R.id.tv_drug_add);
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
        pickerStartTime = new CustomDatePicker(AddDrugRecordActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(long time) {
                starttime = time;
                setStratTime(time);
            }
        }, calendar.getTimeInMillis(), nowtime);
        pickerStartTime.showSpecificTime(false);

        pickerEndTime = new CustomDatePicker(AddDrugRecordActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(long time) {
                time = time + 24 * 60 * 60 * 1000 - 1;
                endtime=time;
                setEndTime(time);
            }
        }, calendar.getTimeInMillis(), nowtime);
        pickerEndTime.showSpecificTime(false);
        addDrugRecordAdapter=new AddDrugRecordAdapter(this);
        lvRecordList.setAdapter(addDrugRecordAdapter);

    }
    private void setStratTime(long time) {
        tvStratTime.setText("自" + showFormat.format(time));
    }

    private void setEndTime(long time) {
        tvEndTime.setText("至" + showFormat.format(time));
    }


    public void deleteRecord(int pos){
        HashMap<String,String> params=new HashMap<>();
        new CommonHttp(AddDrugRecordActivity.this,new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                dismissPDialog();
                Toast.makeText(AddDrugRecordActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestFail(String msg) {
                Toast.makeText(AddDrugRecordActivity.this,msg,Toast.LENGTH_SHORT).show();
                dismissPDialog();
                Toast.makeText(AddDrugRecordActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                Log.e("data", msg);
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(AddDrugRecordActivity.this,getString(R.string.net_error)+code,Toast.LENGTH_SHORT).show();
                dismissPDialog();
            }


        }).doRequest(UrlObject.DELETEHEALTHRECORD,params);
    }
    @Override
    protected void setListener() {
        tvEndTime.setOnClickListener(this);
        tvStratTime.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        initTitle("添加服药记录");

    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<AddDrugRecordEntity> list=new ArrayList<>();
        addDrugRecordAdapter.setData(list);
        for(int i=0;i<100;i++){
            AddDrugRecordEntity entity=new AddDrugRecordEntity();
            list.add(entity);
        }
        addDrugRecordAdapter.notifyDataSetChanged();
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
                for(int i=0;i<addDrugRecordAdapter.getData().size();i++) {
                    View convertview=lvRecordList.getChildAt(i);
                    EditText et=convertview.findViewById(R.id.tv_item_date);
                    addDrugRecordAdapter.getData().get(i).setStr(et.getText().toString());
                    Log.e("tag",et.getText().toString());
                }
                break;
        }

    }
}
