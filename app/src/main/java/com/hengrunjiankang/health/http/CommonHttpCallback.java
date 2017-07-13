package com.hengrunjiankang.health.http;

import android.os.Handler;

/**
 * 网络访问回调接口
 * 
 * @author 1989.12.16
 * 
 */
public interface CommonHttpCallback {
	void requestSeccess(String json);

	void requestFail(String msg);

	void requestAbnormal(int code);

	public class Handler extends android.os.Handler {
	}
}
