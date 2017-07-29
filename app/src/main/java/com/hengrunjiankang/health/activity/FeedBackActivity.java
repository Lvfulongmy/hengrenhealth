package com.hengrunjiankang.health.activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/28.
 */

public class FeedBackActivity extends BaseActivity {
    private EditText etContent;
    private TextView tvSubmit;
    @Override
    protected int setLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void findView() {
        etContent=(EditText)findViewById(R.id.et_feedback_content);
        tvSubmit=(TextView)findViewById(R.id.tv_feedback_submit);
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
        initTitle("意见反馈");
    }
    private void submit(){

        HashMap<String, String> params = new HashMap<>();
        params.put("IdentityUserIdAssign","Id");
        if (!TextUtils.isEmpty(etContent.getText())) {
            params.put("OpinionContent", etContent.getText().toString());
        }else{
            Toast.makeText(FeedBackActivity.this,"请输入您的意见建议",Toast.LENGTH_SHORT).show();
            return;
        }
        showPDialog();
        new CommonHttp(this, new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                dismissPDialog();
                Toast.makeText(FeedBackActivity.this, "您的意见已成功提交", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestFail(String msg) {
                Toast.makeText(FeedBackActivity.this, msg, Toast.LENGTH_SHORT).show();
                dismissPDialog();
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(FeedBackActivity.this, getString(R.string.net_error) + code, Toast.LENGTH_SHORT).show();
                dismissPDialog();
            }
        }).doRequest(UrlObject.FEEDBACKURL, params);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                break;
            case R.id.tv_feedback_submit:
                submit();
                break;
        }
    }
}
