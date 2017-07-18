package com.hengrunjiankang.health.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/12.
 */

public class LoginActivity extends BaseActivity {
    private EditText etUsername;
    private EditText etPwd;
    private TextView tvSubmit;// 登录
    private TextView tvRegister;// 跳到注册

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void findView() {
        etPwd=(EditText)findViewById(R.id.et_login_password);
        etUsername=(EditText)findViewById(R.id.et_login_account);
        tvSubmit=(TextView)findViewById(R.id.btn_login_submit);
    }

    @Override
    protected void createObject() {

    }

    @Override
    protected void setListener() {
        tvSubmit.setOnClickListener(this);
    }

    @Override
    protected void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_submit:
//                if(check()){
                    login();
//                }
                break;
        }
    }
    private boolean check() {
        if (etUsername.getText().toString() == null
                || etUsername.getText().toString().length() == 0) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etPwd.getText().toString() == null
                || etPwd.getText().toString().length() == 0) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void login(){
        HashMap<String,String > params=new HashMap<>();
//        params.put("MobileNumber",etUsername.getText().toString());
//        params.put("Password",etPwd.getText().toString());
                params.put("MobileNumber","13936316095");
        params.put("Password","123456");

        new CommonHttp(new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                startActivity(new Intent(LoginActivity.this,RecordActivity.class));
            }

            @Override
            public void requestFail(String msg) {
                Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestAbnormal(int code) {

            }

            @Override
            public void requestFile(InputStream stream) {

            }
        },false).doRequest(UrlObject.LOGINURL,params);
    }

}
