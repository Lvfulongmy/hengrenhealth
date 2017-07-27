package com.hengrunjiankang.health.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.method.GetVCode;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.util.CommonUtils;
import com.hengrunjiankang.health.widget.TimeButton;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/24.
 */

public class FindpwdActivity extends BaseActivity {
    private EditText etMobile;
    private EditText etVCode;
    private EditText etPwd;
    private EditText etPwd2;
    private TimeButton tbGetVCode;
    private int sex = -1;
    private String strMobile = null;
    private String strVcode;
    private String strPwd;
    private String strPwd2;
    private String strRealName;
    private TextView tvSubmit;
    private GetVCode getVCode;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.title_back:
                this.finish();
                overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
                break;
            case R.id.btn_findpwd_begvcode:
                strMobile = etMobile.getText().toString();
                if (strMobile != null && !strMobile.equals("")&&CommonUtils.checkPhoneNums(strMobile)) {
                    tbGetVCode.onClick();
                    getVCode.getVCode(strMobile);
                    if (strVcode != null)
                        Toast.makeText(FindpwdActivity.this, strVcode,
                                Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FindpwdActivity.this, "请输入手机号",
                            Toast.LENGTH_SHORT).show();
                    tbGetVCode.clearTimer();
                }
                break;
            case R.id.tv_findpwd_submit:
                if (checked()) {
                    submit();
                }
                break;
            default:
                break;
        }
    }

    private void submit() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("MobileNumber", strMobile);
        params.put("MobileVerifyCode", strVcode);
        params.put("Password", strPwd);

        new CommonHttp(FindpwdActivity.this,new CommonHttpCallback() {


            @Override
            public void requestSeccess(String json) {
                Toast.makeText(FindpwdActivity.this,"修改密码成功请重新登录",Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
            }

            @Override
            public void requestFail(String msg) {
                Toast.makeText(FindpwdActivity.this,"修改密码失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(FindpwdActivity.this,getString(R.string.net_error),Toast.LENGTH_SHORT).show();
            }
        }).doRequest(UrlObject.FINDPWD,params);
    }

    private boolean checked() {
        strMobile = etMobile.getText().toString();
        if (strMobile == null || strMobile.equals("")) {
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!CommonUtils.checkPhoneNums(strMobile)) {
            Toast.makeText(this, "请输入正确手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        strVcode = etVCode.getText().toString();
        if (strVcode == null || strVcode.equals("")) {
            Toast.makeText(this, "请输入收到的验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        strPwd = etPwd.getText().toString();
        if (strPwd == null || strPwd.equals("")) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        strPwd2= etPwd.getText().toString();
        if (strPwd2 == null || strPwd.equals("")) {
            Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!strPwd.equals(strPwd2)){
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected int setLayout() {
        // TODO Auto-generated method stub
        return R.layout.activity_findpwd;
    }

    @Override
    protected void findView() {
        // TODO Auto-generated method stub
        etMobile = (EditText) findViewById(R.id.et_findpwd_account);
        etVCode = (EditText) findViewById(R.id.et_findpwd_vcode);
        etPwd = (EditText) findViewById(R.id.et_findpwd_pwd);
        etPwd2 = (EditText) findViewById(R.id.et_findpwd_pwd2);
        tbGetVCode = (TimeButton) findViewById(R.id.btn_findpwd_begvcode);
        tvSubmit = (TextView) findViewById(R.id.tv_findpwd_submit);
    }

    @Override
    protected void createObject() {
        // TODO Auto-generated method stub
        getVCode = new GetVCode(FindpwdActivity.this);
    }

    @Override
    protected void setListener() {
        // TODO Auto-generated method stub
        tbGetVCode.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        // TODO Auto-generated method stub
        initTitle("找回密码");
    }
}
