package com.hengrunjiankang.health.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

public class RegisterActivity extends BaseActivity {
    private EditText etMobile;
    private EditText etVCode;
    private EditText etPwd;
    private TimeButton tbGetVCode;
    private int sex = -1;
    private String strMobile = null;
    private String strVcode;
    private String strPwd;
    private TextView tvSubmit;
    private GetVCode getVCode;
    private ToggleButton tbAgree;
    private TextView tvProtocol;
    private boolean isAgreeProtoco;
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.title_back:
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                this.finish();
                overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
                break;
            case R.id.btn_register_begvcode:
                strMobile = etMobile.getText().toString();
                if (strMobile != null && !strMobile.equals("")&&CommonUtils.checkPhoneNums(strMobile)) {
                    tbGetVCode.onClick();
                    getVCode.getVCode(strMobile);
                    if (strVcode != null)
                        Toast.makeText(RegisterActivity.this, strVcode,
                                Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "请输入正确手机号",
                            Toast.LENGTH_SHORT).show();
                    tbGetVCode.clearTimer();
                }
                break;
            case R.id.tv_register_submit:
                if (checked()) {
                    submit();
                }
                break;
            case R.id.tv_register_protocol:
                Intent intent=new Intent(RegisterActivity.this,WebViewActivity.class);
                intent.putExtra("title","用户协议");
                intent.putExtra("url",UrlObject.PROTOCLURL);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
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

        new CommonHttp(RegisterActivity.this,new CommonHttpCallback() {


            @Override
            public void requestSeccess(String json) {
                Toast.makeText(RegisterActivity.this,"注册成功正在登录",Toast.LENGTH_SHORT).show();
                login();
                overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
            }

            @Override
            public void requestFail(String msg) {
//                Toast.makeText(RegisterActivity.this,"修改密码失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(RegisterActivity.this,getString(R.string.net_error),Toast.LENGTH_SHORT).show();
            }
        }).doRequest(UrlObject.REGISTERURL,params);
    }

    private void login(){
       showPDialog();
        HashMap<String,String > params=new HashMap<>();
        params.put("MobileNumber",strMobile);
        params.put("Password",strPwd);
//                params.put("MobileNumber","13936316095");
//        params.put("Password","123456");

        new CommonHttp(RegisterActivity.this,new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                dismissPDialog();
                CommonUtils.saveUserInfo(RegisterActivity.this,strMobile,strPwd);
                startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
            }

            @Override
            public void requestFail(String msg) {
                dismissPDialog();
                Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(RegisterActivity.this,getString(R.string.net_error),Toast.LENGTH_SHORT).show();
               dismissPDialog();
            }

        }).doRequest(UrlObject.LOGINURL,params);
    }

    private boolean checked() {
        strMobile = etMobile.getText().toString();
        if(!isAgreeProtoco){
            Toast.makeText(this, "请仔细阅读用户协议并同意", Toast.LENGTH_SHORT).show();
            return false;
        }
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
        return true;
    }

    @Override
    protected int setLayout() {
        // TODO Auto-generated method stub
        return R.layout.activity_register;
    }

    @Override
    protected void findView() {
        // TODO Auto-generated method stub
        etMobile = (EditText) findViewById(R.id.et_register_mobile);
        etVCode = (EditText) findViewById(R.id.et_register_vcode);
        etPwd = (EditText) findViewById(R.id.et_register_pwd);
        tbGetVCode = (TimeButton) findViewById(R.id.btn_register_begvcode);
        tvSubmit = (TextView) findViewById(R.id.tv_register_submit);
        tbAgree=(ToggleButton)findViewById(R.id.tb_register_agree);
        tvProtocol=(TextView)findViewById(R.id.tv_register_protocol);
    }

    @Override
    protected void createObject() {
        // TODO Auto-generated method stub
        getVCode = new GetVCode(RegisterActivity.this);
    }

    @Override
    protected void setListener() {
        // TODO Auto-generated method stub
        tbGetVCode.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        tvProtocol.setOnClickListener(this);
        tbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAgreeProtoco=b;
            }
        });
    }

    @Override
    protected void getData() {
        // TODO Auto-generated method stub
        initTitle("");
    }
}
