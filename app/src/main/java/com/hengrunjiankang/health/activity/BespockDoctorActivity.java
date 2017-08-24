package com.hengrunjiankang.health.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.StringCodec;
import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.adapter.ProfileListAdapter;
import com.hengrunjiankang.health.adapter.SelectProfileAdapter;
import com.hengrunjiankang.health.entity.ProfileListEntity;
import com.hengrunjiankang.health.entity.ProfileListSelectEntity;
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
import java.util.Map;

/**
 * Created by Administrator on 2017/8/24.
 */

public class BespockDoctorActivity extends BaseActivity {
    private TextView selectBirthday,selectDate,tvRemarkNum,tvBespock;
    private EditText etRemark;
    private SelectProfileAdapter mProfileListAdapter;
    private ListView lvProfle;
    private CustomDatePicker datePicker,birthdayPicter;
    private SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd");
    private RadioGroup rgSex;
    @Override
    protected int setLayout() {
        return R.layout.activity_bespock_doctor;
    }

    @Override
    protected void findView() {
        lvProfle=(ListView)findViewById(R.id.lv_bespock_profile);
        selectDate=(TextView)findViewById(R.id.tv_bespock_date);
        selectBirthday=(TextView)findViewById(R.id.tv_bespock_birthday);
        rgSex = (RadioGroup) findViewById(R.id.rg_bespock_sex);
        etRemark=(EditText) findViewById(R.id.et_bespock_remark);
        tvRemarkNum=(TextView)findViewById(R.id.tv_bespock_txtnums);
        tvBespock=(TextView)findViewById(R.id.tv_bespock_submit);
    }

    @Override
    protected void createObject() {
        mProfileListAdapter=new SelectProfileAdapter(this);
        lvProfle.setAdapter(mProfileListAdapter);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900, 1, 1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2020, 1, 1);
        birthdayPicter = new CustomDatePicker(BespockDoctorActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(long time) {
                selectBirthday.setText(formatTime.format(time));
            }
        }, calendar.getTimeInMillis(), System.currentTimeMillis());
        birthdayPicter.showSpecificTime(false);
        datePicker = new CustomDatePicker(BespockDoctorActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(long time) {
                selectDate.setText(formatTime.format(time));
            }
        },  System.currentTimeMillis(),calendar2.getTimeInMillis());
        datePicker.showSpecificTime(false);
    }

    @Override
    protected void setListener() {
        selectBirthday.setOnClickListener(this);
        selectDate.setOnClickListener(this);
        tvBespock.setOnClickListener(this);
        etRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvRemarkNum.setText(charSequence.length() + "/500");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void getData() {
        initTitle("预约专家");
        getListData();
        rgSex.check(R.id.rb_bespock_male);

    }
    String strHealthRecord="";
    private void submit(){
        HashMap<String, String> params = new HashMap<>();
//        params.put("UserId", ));
        params.put("ExpertId",getIntent().getStringExtra("ExpertId"));


        if(strHealthRecord.length()>0){
            params.put("HealthRecordFriendlyId",strHealthRecord.substring(0,strHealthRecord.length()-1));
        }
        params.put("Birthday",selectBirthday.getText().toString());
        params.put("ReservationTime",selectDate.getText().toString()+" 00:00:00");
        if (rgSex.getCheckedRadioButtonId() == R.id.rb_bespock_male) {
            params.put("Sex", "男");
        } else {
            params.put("Sex", "女");
        }
        if(etRemark.length()>0) {
            params.put("Remark", etRemark.getText().toString());
        }





        new CommonHttp(BespockDoctorActivity.this, new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                dismissPDialog();
                Toast.makeText(BespockDoctorActivity.this, "预约成功立即付款", Toast.LENGTH_SHORT).show();
                String id= JSON.parseArray(json,String.class).get(0);
//                Intent intent=new Intent(BespockDoctorActivity.this,ProfileActivity.class);
//                intent.putExtra("id",id);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                finish();
            }

            @Override
            public void requestFail(String msg) {
                dismissPDialog();
                Toast.makeText(BespockDoctorActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(BespockDoctorActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                dismissPDialog();
            }

        }).doRequest(UrlObject.SAVERESERVATIONEXPERT, params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                break;
            case R.id.tv_bespock_submit:
                if(check()){
                    submit();
                }
                break;
            case R.id.tv_bespock_birthday:
                birthdayPicter.show(formatTime.format(System.currentTimeMillis()));
                break;
            case R.id.tv_bespock_date:
                datePicker.show(formatTime.format(System.currentTimeMillis()));
                break;

        }
    }
    public void goToDetails(int postition){
        String id= mProfileListAdapter.getData().get(postition).getId();
        Intent intent=new Intent(BespockDoctorActivity.this,ProfileActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);

    }
    private boolean check(){
        if(selectDate.length()==0){
            Toast.makeText(this,"请选择最晚回复时间",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(selectBirthday.length()==0){
            Toast.makeText(this,"请选择出生日期",Toast.LENGTH_SHORT).show();
            return  false;
        }
        for(int i=0;i<mProfileListAdapter.getData().size();i++){
            if(mProfileListAdapter.getData().get(i).isSelect()){
                strHealthRecord+=mProfileListAdapter.getData().get(i).getFriendlyId()+";";
            }
        }
        if(strHealthRecord.length()==0&&etRemark.length()==0){
            Toast.makeText(this,"请填写需要咨询的症状或选择健康档案",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void getListData() {
     String url=UrlObject.QUERYHEALTHRECORD+"&SpecifyProperty=FriendlyId;Id;RecordTime&Sort=RecordTime desc";
            showPDialog();
        new CommonHttp(BespockDoctorActivity.this,new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                dismissPDialog();
//                try {
//                    JSONObject jsonObject=new JSONObject(json);
//                    json=jsonObject.getJSONArray("Items").toString();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                ArrayList<ProfileListSelectEntity> recordListData = (ArrayList<ProfileListSelectEntity>) JSON.parseArray(json, ProfileListSelectEntity.class);
                if(recordListData!=null&&recordListData.size()>0) {

                        mProfileListAdapter.setData(recordListData);
                        mProfileListAdapter.notifyDataSetChanged();

                }else{
                    lvProfle.setVisibility(View.GONE);
                }


            }

            @Override
            public void requestFail(String msg) {
                Toast.makeText(BespockDoctorActivity.this,msg,Toast.LENGTH_SHORT).show();
                dismissPDialog();
                Log.e("data", msg);
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(BespockDoctorActivity.this, getString(R.string.net_error)+code,Toast.LENGTH_SHORT).show();
                dismissPDialog();
            }


        }).doRequest(url);

    }
}
