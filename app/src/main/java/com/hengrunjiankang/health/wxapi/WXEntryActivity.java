package com.hengrunjiankang.health.wxapi;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private static final long DelayedTime=3000;

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        // TODO Auto-generated method stub
//        try {
//            if (resp instanceof SendAuth.Resp) {
//                SendAuth.Resp newResp = (SendAuth.Resp) resp;
//                switch (newResp.errCode) {
//                    case 0:
//                        String code = newResp.code;
//                        Message msg=new Message();
//                        msg.what=0;
//                        Bundle bundle=new Bundle();
//                        bundle.putString("code",code);
//                        msg.setData(bundle);
//                        handler.sendMessage(msg);
//                        break;
//                    case -4:
//                        Toast.makeText(WXEntryActivity.this, "拒绝授权", Toast.LENGTH_SHORT)
//                                .show();
//                        handler.sendEmptyMessage(1);
//                        break;
//                    case -2:
//                        Toast.makeText(WXEntryActivity.this, "取消授权", Toast.LENGTH_SHORT)
//                                .show();
//                        handler.sendEmptyMessage(1);
//                        break;
//                    default:
//                        Toast.makeText(WXEntryActivity.this, "微信登录异常", Toast.LENGTH_SHORT)
//                                .show();
//                        handler.sendEmptyMessage(1);
//                        finish();
//                        break;
//                }
//                // 获取微信传回的code
//
//            }
//        } catch (Exception e) {
//            Toast.makeText(WXEntryActivity.this, "微信登录异常", Toast.LENGTH_SHORT)
//                    .show();
//        }


    }
    public  static final int success=0;
    public static final int fail=1;
    Handler handler=new Handler(new Handler.Callback(){

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case success:

                    break;
                case fail:
                    finish();
                    break;
            }
            return false;
        }
    });

}