package com.hengrunjiankang.health.okhttp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/13.
 */

public class CommonHttp {
    private CommonHttpCallback callback;
    public static String cookie;
    private boolean isfile=false;
    public CommonHttp(CommonHttpCallback callback,boolean isfile){
        this.callback=callback;
        this.isfile=isfile;
    }
    public void doRequest(Object... objects) {
        String url;
        HashMap<String, String> params;
        HashMap<String, File> files;
        Request request=null;
        Request.Builder requestBuiler = new Request.Builder();
        if(cookie!=null&&!cookie.equals("")){
            requestBuiler.header("Cookie",cookie);
        }

        switch (objects.length) {
            case 1:
                url = (String) objects[0];
                        request=requestBuiler.url(url)
                        .build();
                break;
            case 2:
                url = (String) objects[0];
                FormBody.Builder builder = new FormBody.Builder();
                params = (HashMap<String, String>) objects[1];
                for (HashMap.Entry<String, String> entry :
                        params.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }

                request = requestBuiler.url(url).post(builder.build()).build();
                break;
            case 3:
                url = (String) objects[0];
                MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                MediaType type = MediaType.parse("application/octet-stream");
                params = (HashMap<String, String>) objects[1];
                for (HashMap.Entry<String, String> entry : params.entrySet()
                        ) {
                    multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }

                files = (HashMap<String, File>) objects[2];
                for (HashMap.Entry<String, File> entry :
                        files.entrySet()) {
                    RequestBody fileBody = RequestBody.create(type, entry.getValue());
                    multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue().getName(), fileBody);
                }
                ;
                request = requestBuiler.url(url).post(multipartBuilder.build()).build();
                break;
        }
        final OkHttpClient httpClient = new OkHttpClient();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.requestAbnormal(-1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                cookie=response.header("Set-Cookie","").toString();
                if(response.code()==200){
                    if (isfile) {
                        callback.requestFile(response.body().byteStream());
                    }else {
                        callback.requestSeccess(response.body().string());
                    }
                }else if(response.code()==500){
                    callback.requestFail(response.body().string());
                }else{
                    callback.requestAbnormal(response.code());
                }
            }
        });
    }
}
