package com.hengrunjiankang.health.activity;

import android.graphics.Color;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.entity.AccounEntity;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.widget.CustomDatePicker;
import com.lljjcoder.citypickerview.widget.CityPickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/27.
 */

public class MyAccountActivity extends BaseActivity {

    private TextView submit, selectArea, selectBirthday;
    private RadioGroup rgSex;
    private String sex = null, Province, City, District;
    private String name = null, mobile = null, idcard = null, tele = null, edu = null, vocation = null, job = null, work = null, address = null, area = null, birthday = null;
    private EditText etName, etMobile, etIdCard, etTele, etEdu, etVocation, etJob, etWork, etAddress;
    private CityPickerView cityPicker;
    private CustomDatePicker timePicker;
    private SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected int setLayout() {
        return R.layout.activity_myaccount;
    }

    @Override
    protected void findView() {
        etName = (EditText) findViewById(R.id.et_account_name);
        etMobile = (EditText) findViewById(R.id.et_account_mobile);
        etIdCard = (EditText) findViewById(R.id.et_account_idno);
        etTele = (EditText) findViewById(R.id.et_account_tell);
        etEdu = (EditText) findViewById(R.id.et_account_edu);
        etVocation = (EditText) findViewById(R.id.et_account_vocation);
        etJob = (EditText) findViewById(R.id.et_account_job);
        etWork = (EditText) findViewById(R.id.et_account_wrok);
        etAddress = (EditText) findViewById(R.id.et_account_address);
        submit = (TextView) findViewById(R.id.tv_account_submit);
        selectArea = (TextView) findViewById(R.id.tv_account_area);
        selectBirthday = (TextView) findViewById(R.id.tv_account_birthday);
        rgSex=(RadioGroup)findViewById(R.id.rg_account_sex);
    }

    @Override
    protected void createObject() {

    }

    @Override
    protected void setListener() {
        submit.setOnClickListener(this);
        selectArea.setOnClickListener(this);
        selectBirthday.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        initTitle("个人资料");
       initData();
       cityPicker = new CityPickerView(MyAccountActivity.this);

        cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                Province=citySelected[0];
                City=citySelected[1];
                District=citySelected[2];
                cityPicker.hide();
                selectArea.setText(Province + " " + City + " " + District);
            }
        });
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900, 1, 1);
        timePicker = new CustomDatePicker(MyAccountActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(long time) {
                    selectBirthday.setText(formatTime.format(time));
            }
        }, calendar.getTimeInMillis(), System.currentTimeMillis());

    }

    private void initData() {
        showPDialog();
        String urlParams="&IsQueryResidenceAddress=true&SpecifyProperty=Id;FullName;Sex;Birthday;IdcardNumber;MobileNumber;PhoneNumber;UserJob;AcademicQualification;WorkUnit;UserDuties;Addresses.UserId;Addresses.Province;Addresses.City;Addresses.District;Addresses.Detail;";
        new CommonHttp(this, new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                dismissPDialog();
                AccounEntity entity = JSON.parseObject(json, AccounEntity.class);
                selectArea.setText(entity.getAddresses().get(0).getProvince() + " " + entity.getAddresses().get(0).getCity() + " " + entity.getAddresses().get(0).getDistrict());
                selectBirthday.setText(entity.getBirthday().substring(0,10));
                etName.setText(entity.getFullName());
                etMobile.setText(entity.getMobileNumber());
                etIdCard.setText(entity.getIdcardNumber());
                etTele.setText(entity.getPhoneNumber());
                etEdu.setText(entity.getAcademicQualification());
                etVocation.setText(entity.getUserDuties());
                etJob.setText(entity.getUserJob());
                etWork.setText(entity.getWorkUnit());
                etAddress.setText(entity.getAddresses().get(0).getDetail());
                if(entity.getSex().equals("男")){
                    rgSex.check(R.id.rb_account_male);
                }else{
                    rgSex.check(R.id.rb_account_female);
                }
            }

            @Override
            public void requestFail(String msg) {
                Toast.makeText(MyAccountActivity.this, msg, Toast.LENGTH_SHORT).show();
                dismissPDialog();
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(MyAccountActivity.this, getString(R.string.net_error)+code, Toast.LENGTH_SHORT).show();
                dismissPDialog();
            }
        }).doRequest(UrlObject.ACCOUNTINFO+urlParams);


    }
    private void saveUser(){
//        OkHttpUtils.post().url("http://192.168.1.112:9000")
//                .addHeader("Cookie", success)
//                .addParams("FullName", name)
//                .addParams("IdentityUserIdAssign", "Id")
//                .addParams("PhoneNumber", telephone)
//                .addParams("Sex", sex)
//                .addParams("AcademicQualification", acord)
//                .addParams("IdcardNumber", idcard)
//                .addParams("UserJob", professional)
//                .addParams("UserDuties", position)
//                .addParams("WorkUnit", workunits)
//                .addParams("ResidenceAddressProvince", Province)
//                .addParams("ResidenceAddressCity", City)
//                .addParams("ResidenceAddressDistrict", District)
//                .addParams("ResidenceAddressDetail", truehome)
//                .addParams("Birthday", age)
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                break;
            case R.id.tv_account_area:
                cityPicker.show();
                break;
            case R.id.tv_account_birthday:
                timePicker.show(formatTime.format(System.currentTimeMillis()));
                break;
        }
    }
}
