package com.hengrunjiankang.health.http;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;

import android.content.SharedPreferences;
import android.os.AsyncTask;

/**
 * 网络访问的类
 *
 * @author 1989.12.16
 */
public class CommonHttpPostRequest extends AsyncTask<Object, Object, String> {
    private String cookie = null; // 持久化session
    private static final String TAG = CommonHttpCallback.class.getSimpleName();
    private CommonHttpCallback callback;
    private int code = -1;

    public CommonHttpPostRequest(CommonHttpCallback callback,String cookie) {
        // TODO Auto-generated constructor stub
        this.callback = callback;
        this.cookie=cookie;
    }

    @Override
    protected String doInBackground(Object... obj) {
        // TODO Auto-generated method stub
        if (obj.length == 0)
            return null;
        String actionUrl = null;
        HashMap<String, String> params = null;
        HashMap<String, File> files = null;

        DefaultHttpClient client = null;
        HttpParams httpParams = new BasicHttpParams();//
        // httpParams.setParameter("X-Requested-With", "XMLHttpRequest");
        HttpConnectionParams.setConnectionTimeout(httpParams, 8000); // 连接超时
        HttpConnectionParams.setSoTimeout(httpParams, 6000); // 响应超时
        HttpPost post = null;
        switch (obj.length) {
            case 1:
                actionUrl = (String) obj[0];
                client = new DefaultHttpClient();
                post = new HttpPost(actionUrl);
                break;
            case 2:
                actionUrl = (String) obj[0];
                params = (HashMap<String, String>) obj[1];
                client = new DefaultHttpClient();
                post = new HttpPost(actionUrl);
                break;
            case 3:
                actionUrl = (String) obj[0];
                params = (HashMap<String, String>) obj[1];
                files = (HashMap<String, File>) obj[2];
                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("http", PlainSocketFactory
                        .getSocketFactory(), 100));
                client = new DefaultHttpClient();
                // client = new DefaultHttpClient(new ThreadSafeClientConnManager(
                // httpParams, registry), httpParams);
                post = new HttpPost(actionUrl);
                break;
            default:
                break;
        }
        post.setParams(httpParams);

//         添加session
         if (null != cookie) {
            post.setHeader("Cookie", cookie);
         }
        Charset charset = Charset.forName(HTTP.UTF_8);
        post.addHeader("charset", HTTP.UTF_8);
        try {

            if (obj.length == 2) {
                HashMap<String, String> map = params;
                ArrayList<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    BasicNameValuePair pair = new BasicNameValuePair(
                            entry.getKey(), entry.getValue());
                    pairList.add(pair);
                }
                HttpEntity entity = new UrlEncodedFormEntity(pairList, "UTF-8");
                post.setEntity(entity);
            }

            if (files != null) {
                // String BOUNDARY = UUID.randomUUID().toString();
                // post.setHeader("Content-Type",
                // "multipart/form-data;boundary="
                // + BOUNDARY);
                MultipartEntityBuilder builder = MultipartEntityBuilder
                        .create();
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                // builder.setCharset(charset);
                // MultipartEntity mulentity = new MultipartEntity(
                // HttpMultipartMode.BROWSER_COMPATIBLE, null, charset);
                // MultipartEntity mulentity=new MultipartEntity();

                if (params != null) {
//					LinkedList<BasicNameValuePair> link=new LinkedList<BasicNameValuePair>();
                    ContentType contentType = ContentType.create(
                            HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        // builder.addPart(entry.getKey(),
                        // new StringBody(entry.getValue(), charset));

                        // builder.addTextBody(entry.getKey(),
                        // entry.getValue());
                        if (entry.getValue() != null)
                            builder.addTextBody(entry.getKey(), entry.getValue(), contentType);
                    }

                }
                for (Map.Entry<String, File> fileEntry : files.entrySet()) {
                    FileBody filebody = new FileBody(fileEntry.getValue());
//					 = new FileBody(fileEntry.getValue(),
//					 "image/jpeg", "UTF-8");

                    // mulentity.addPart(fileEntry.getKey(), filebody);
                    // mulentity.addPart("pic1",filebody);

                    builder.addPart(fileEntry.getKey(), filebody);
                    // builder.addBinaryBody(fileEntry.getKey(),
                    // fileEntry.getValue());

                }
                post.setEntity(builder.build());
            }

            //

            HttpResponse response = client.execute(post);// 包含响应的状态和返回的结果
            code = response.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK || code == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                String jsonStr = EntityUtils.toString(response.getEntity(),
                        "UTF-8");
                if (jsonStr == null)
                    return null;
                return jsonStr;

            }
        } catch (ClientProtocolException e) {

            return null;
        } catch (IOException e) {

            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        if (code == HttpStatus.SC_OK) {

            callback.requestSeccess(result);

        } else {
            // 网络请求失败
            callback.requestFail(getMsg(result));
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
            return json;
    }

//    /**
//     * @param client
//     */
//    private void getSessionId(DefaultHttpClient client) {
//        CookieStore cookieStore = client.getCookieStore();
//        List<Cookie> cookies = cookieStore.getCookies();
//        if (null != cookies && cookies.size() > 0) {
//            for (Cookie c : cookies) {
//                if ("JSESSIONID".equalsIgnoreCase(c.getName())) {
//                    if (null != c.getValue()) {
//                        try {
//                            JSESSIONID = URLDecoder.decode(c.getValue(),
//                                    "UTF-8");
//                        } catch (UnsupportedEncodingException e) {
//
//                        }
//                    }
//                }
//            }
//        }
//    }
}
