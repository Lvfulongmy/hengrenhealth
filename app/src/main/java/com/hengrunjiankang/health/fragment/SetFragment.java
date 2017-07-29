package com.hengrunjiankang.health.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.activity.FeedBackActivity;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2017/7/27.
 */

public class SetFragment extends BaseFragment {
    private LinearLayout llShare;
    private LinearLayout llFeedBack;
    /**
     * 分享到微信好友
     */
    private void shareToWx() {
        IWXAPI api = WXAPIFactory.createWXAPI(mActivity, getString(R.string.wxAppId), true);
        api.registerApp(getString(R.string.wxAppId));
        if (api.isWXAppInstalled()) {
            // WXTextObject textObject=new WXTextObject();
            // textObject.text="www.haguan.com";
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = UrlObject.SHAREURL;
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = webpage;
            msg.description = "为用户提供健康服务，创建用户个人健康档案，允许用户线下测量【体脂】、【血糖】、【血氧】、【体重】、【血压】、【心电】、【尿常规】、【血脂】数据，并同步到应用。";
            Bitmap shareBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.applogo,null);
            if (shareBitmap != null)
                msg.setThumbImage(shareBitmap);

            msg.title = "恒瑞健康";
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = 0;

            api.sendReq(req);
        } else {
            Toast.makeText(mActivity, "手机未安装微信",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected int setLayout() {
        return R.layout.fragment_set;
    }

    @Override
    protected void findView(View view) {
        llShare=(LinearLayout)view.findViewById(R.id.ll_set_share);
        llFeedBack=(LinearLayout)view.findViewById(R.id.ll_set_feedback);
    }

    @Override
    protected void createObject() {

    }

    @Override
    protected void setListener() {
        llFeedBack.setOnClickListener(this);
        llShare.setOnClickListener(this);
    }

    @Override
    protected void getData() {

    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.ll_set_feedback:
                    startActivity(new Intent(mActivity, FeedBackActivity.class));
                    mActivity.overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                    break;
                case R.id.ll_set_share:
                    shareToWx();
                    break;
            }
    }
}
