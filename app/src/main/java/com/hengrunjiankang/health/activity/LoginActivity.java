package com.hengrunjiankang.health.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.util.CommonUtils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/7/12.
 */

public class LoginActivity extends BaseActivity {
    private AutoCompleteTextView etUsername;
    private EditText etPwd;
    private TextView tvSubmit;// 登录
    private TextView tvRegister;// 跳到注册
    private ImageButton ibUsernameClear;
    private TextView tvFindPwd;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void findView() {
        etPwd=(EditText)findViewById(R.id.et_login_pwd);
        etUsername=(AutoCompleteTextView) findViewById(R.id.act_login_account);
        tvSubmit=(TextView)findViewById(R.id.tv_login_submit);
        tvRegister=(TextView)findViewById(R.id.tv_login_register);
        ibUsernameClear=(ImageButton)findViewById(R.id.ib_login_username_clear);
        tvFindPwd=(TextView)findViewById(R.id.tv_login_findpwd);
    }


    @Override
    protected void createObject() {

    }

    @Override
    protected void setListener() {
        tvSubmit.setOnClickListener(this);
        ibUsernameClear.setOnClickListener(this);
        tvFindPwd.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        initAutoComplete("history",etUsername);
        initTitle("");
    }
    private void initAutoComplete(String field, AutoCompleteTextView auto) {
        SharedPreferences sp = getSharedPreferences("login_records", 0);// 存储登录历史
        String loginhistory = sp.getString("history", "");// 登录记录
        final AutoCompleteTextView tempAuto = auto;
        final String tempHistory = loginhistory;
        auto.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (tempAuto.getText().toString().length() > 0) {
                    ibUsernameClear.setVisibility(View.VISIBLE);
                    if (tempHistory.length() > 0)
                        tempAuto.showDropDown();
                } else {
                    ibUsernameClear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        auto.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus && tempAuto.getText().length() > 0)
                    ibUsernameClear.setVisibility(View.VISIBLE);
                else
                    ibUsernameClear.setVisibility(View.GONE);

            }
        });
        if (!loginhistory.equals("")) {

            String[] hisArrays = loginhistory.split(",");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.item_list_login_records, hisArrays);
            // 只保留最近的50条的记录
            if (hisArrays.length > 50) {
                String[] newArrays = new String[50];
                System.arraycopy(hisArrays, 0, newArrays, 0, 50);
                adapter = new ArrayAdapter<String>(this,
                        R.layout.item_list_login_records, newArrays);
            }
            auto.setAdapter(adapter);
            auto.setDropDownHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            auto.setThreshold(1);
//            auto.setCompletionHint("*最近登陆过的账号");
            // auto.setOnFocusChangeListener(new OnFocusChangeListener() {
            // @Override
            // public void onFocusChange(View v, boolean hasFocus) {
            // AutoCompleteTextView view = (AutoCompleteTextView) v;
            // if (hasFocus) {
            //
            // }
            // }
            //
            // });

        }
    }

    /**
     * 把指定AutoCompleteTextView中内容保存到sharedPreference中指定的字符段
     *
     * @param field
     *            保存在sharedPreference中的字段名
     * @param auto
     *            要操作的AutoCompleteTextView
     */
    private void saveHistory(String field, AutoCompleteTextView auto) {
        String text = auto.getText().toString();
        SharedPreferences sp = getSharedPreferences("login_records", 0);
        String longhistory = sp.getString(field, "");
        if (!longhistory.contains(text + ",")) {
            StringBuilder sb = new StringBuilder(longhistory);
            sb.insert(0, text + ",");
            sp.edit().putString("history", sb.toString()).commit();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login_submit:
                if(check()){
                    login();
                }
                break;
            case R.id.ib_login_username_clear:
                etUsername.setText("");
                break;

            case R.id.title_back:
                finish();
                break;
            case R.id.tv_login_findpwd:
                startActivity(new Intent(LoginActivity.this,FindpwdActivity.class));
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                break;
            case R.id.tv_login_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                this.finish();
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                break;
        }
    }
    // 用于检测是否包含特殊字符的正则表达式
    String regx = "!|！|@|◎|#|＃|(\\$)|￥|%|％|(\\^)|……|(\\&)|※|(\\*)|×|(\\()|（|(\\))|）|_|——|(\\+)|＋|(\\|)|§";

    /**
     * 判断字符串是否符合正则表达式的要求。用于判断用户名是否包含特殊字符
     *
     * @param qString
     *            字符串
     * @param regx
     *            正则表达式
     * @return
     */
    private boolean hasCrossScriptRisk(String qString, String regx) {
        if (qString != null) {
            qString = qString.trim();
            Pattern p = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(qString);
            return m.find();
        }
        return false;
    }

    /**
     * 用于输入的合法性校验和空值检测
     *
     * @return boolean true为合理
     */

    private boolean check() {
        Matcher matcher1 = Pattern.compile(" ").matcher(
                etUsername.getText().toString());
        if (matcher1.find()) {
            Toast.makeText(this, "账号包含非法字符", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (hasCrossScriptRisk(etUsername.getText().toString(), regx)) {
            Toast.makeText(this, "账号包含非法字符", Toast.LENGTH_SHORT).show();
            return false;
        }
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
        initTitle("登录");
        showPDialog();
        HashMap<String,String > params=new HashMap<>();
        params.put("MobileNumber",etUsername.getText().toString());
        params.put("Password",etPwd.getText().toString());
//                params.put("MobileNumber","13936316095");
//        params.put("Password","123456");

        new CommonHttp(LoginActivity.this,new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                dismissPDialog();
                saveHistory("history",etUsername);
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
            }

            @Override
            public void requestFail(String msg) {
                dismissPDialog();
                Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(LoginActivity.this,getString(R.string.net_error),Toast.LENGTH_SHORT).show();
                dismissPDialog();
            }

        }).doRequest(UrlObject.LOGINURL,params);
    }

}
