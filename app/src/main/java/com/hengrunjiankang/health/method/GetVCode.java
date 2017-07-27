package com.hengrunjiankang.health.method;

import java.net.URL;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;

public class GetVCode {
	private String result = null;
	private Context mContext = null;

	public GetVCode(Context mContext) {
		this.mContext = mContext;
	}

	public String getResult() {
		return result;
	}

	public void getVCode(String mobile) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("MobileNumber", mobile);

		new CommonHttp(mContext,new CommonHttpCallback() {

			@Override
			public void requestSeccess(String json) {
                Toast.makeText(mContext,"获取验证码成功",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void requestFail(String msg) {
                Toast.makeText(mContext,"获取验证码失败",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void requestAbnormal(int code) {
                Toast.makeText(mContext,mContext.getString(R.string.net_error),Toast.LENGTH_SHORT).show();
			}
		}).doRequest(UrlObject.BEGVCODE,params);
	}
}
