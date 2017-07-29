package com.hengrunjiankang.health.activity;

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

public class ResetpwdActivity extends BaseActivity {
    private EditText etNowpwd;
    private EditText etNewPwd;
    private EditText etNewPwd2;
    private String strNowpwd = null;
    private String strNewpwd;
    private String strNewpwd2;
    private TextView tvSubmit;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.title_back:
                this.finish();
                overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
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
        params.put("OldPassword", strNowpwd);
        params.put("Password", strNewpwd);
        params.put("IdentityUserIdAssign","Id");

        new CommonHttp(ResetpwdActivity.this,new CommonHttpCallback() {


            @Override
            public void requestSeccess(String json) {
                Toast.makeText(ResetpwdActivity.this,"修改密码成功",Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
            }

            @Override
            public void requestFail(String msg) {
                Toast.makeText(ResetpwdActivity.this,"修改密码失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(ResetpwdActivity.this,getString(R.string.net_error)+code,Toast.LENGTH_SHORT).show();
            }
        }).doRequest(UrlObject.SAVAUSERINFO,params);
    }

    private boolean checked() {
        strNowpwd = etNowpwd.getText().toString();
        if (strNowpwd == null || strNowpwd.equals("")) {
            Toast.makeText(this, "请输入原密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        strNewpwd = etNewPwd.getText().toString();
        if (strNewpwd == null || strNewpwd.equals("")) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        strNewpwd2= etNewPwd2.getText().toString();
        if (strNewpwd2 == null || strNewpwd2.equals("")) {
            Toast.makeText(this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!strNewpwd.equals(strNewpwd2)){
            Toast.makeText(this, "两次新密码输入不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected int setLayout() {
        // TODO Auto-generated method stub
        return R.layout.activity_resetpwd;
    }

    @Override
    protected void findView() {
        // TODO Auto-generated method stub
        etNowpwd = (EditText) findViewById(R.id.et_findpwd_nowpwd);
        etNewPwd = (EditText) findViewById(R.id.et_findpwd_newpwd);
        etNewPwd2 = (EditText) findViewById(R.id.et_findpwd_newpwd2);
        tvSubmit = (TextView) findViewById(R.id.tv_findpwd_submit);
    }

    @Override
    protected void createObject() {
    }

    @Override
    protected void setListener() {
        // TODO Auto-generated method stub
        tvSubmit.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        // TODO Auto-generated method stub
        initTitle("修改密码");
    }
}
