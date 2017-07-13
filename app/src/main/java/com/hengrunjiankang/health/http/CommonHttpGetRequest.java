package com.hengrunjiankang.health.http;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 网络访问的类
 * 
 * @author 1989.12.16
 * 
 */
public class CommonHttpGetRequest extends AsyncTask<Object, Object, String> {
	private String JSESSIONID = null; // 持久化session
	private static final String TAG = CommonHttpCallback.class.getSimpleName();
	private CommonHttpCallback callback;
	private int code = -1;

	public CommonHttpGetRequest(CommonHttpCallback callback) {
		// TODO Auto-generated constructor stub
		this.callback = callback;
	}

	@Override
	protected String doInBackground(Object... obj) {
		// TODO Auto-generated method stub
		String actionUrl = (String) obj[0];
		HttpGet get = new HttpGet(actionUrl);

		DefaultHttpClient client = new DefaultHttpClient();

		// 添加session
		// if (null != JSESSIONID) {
		// post.setHeader("Cookie", "JSESSIONID=" + JSESSIONID);
		// } else {
		// // 当用户第一次发送请求时，客户端sessionId为空，则不处理
		// }
		Charset charset = Charset.forName(HTTP.UTF_8);

		//

		HttpResponse response = null;
		try {
			response = client.execute(get);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 包含响应的状态和返回的结果
		code = response.getStatusLine().getStatusCode();
		if (code == HttpStatus.SC_OK) {
			getSessionId(client);
			String jsonStr = null;
			try {
				jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (jsonStr == null)
				return null;
			return jsonStr;

		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub

		if (code == HttpStatus.SC_OK) {

			if (result != null) {

				switch (getStatus(result)) {
				case 0:
					callback.requestSeccess(result);
					break;

				default:
					callback.requestFail(result);
					break;
				}
			} else {
				// 网络请求失败
				callback.requestAbnormal(-1);
			}

		} else {
			try {
				callback.requestAbnormal(code);
			} catch (Exception e) {
				callback.requestAbnormal(-1);
			}
		}

	}

	/**
	 * 获取status
	 */
	private int getStatus(String json) {
		try {
			JSONObject object = new JSONObject(json);
			return object.getInt("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 获取msg
	 */
	private String getMsg(String json) {
		try {
			JSONObject object = new JSONObject(json);
			return object.getString("msg");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param client
	 */
	private void getSessionId(DefaultHttpClient client) {
		CookieStore cookieStore = client.getCookieStore();
		List<Cookie> cookies = cookieStore.getCookies();
		if (null != cookies && cookies.size() > 0) {
			for (Cookie c : cookies) {
				if ("JSESSIONID".equalsIgnoreCase(c.getName())) {
					if (null != c.getValue()) {
						try {
							JSESSIONID = URLDecoder.decode(c.getValue(),
									"UTF-8");
						} catch (UnsupportedEncodingException e) {

						}
					}
				}
			}
		}
	}
}
