package com.hengrunjiankang.health.activity;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hengrunjiankang.health.R;

/**
 * 网页访问页面
 * 
 * @author 1989.12.16
 * 
 */
@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends BaseActivity {
	private ProgressBar progerBar;
	private WebView webView;

	// private ImageButton btnBack;
	// private ImageButton btnForward;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_back:
			this.finish();
			overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
			break;
		default:
			break;
		}
	}

	@Override
	protected int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.activity_webview;
	}

	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		webView = (WebView) findViewById(R.id.web_wb_view);

	}

	@Override
	protected void createObject() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE = 1;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub
//		showPDialog();
		initTitle(getIntent().getStringExtra("title"));
		final String url = getIntent().getStringExtra("url");
		// String url="http://www.baidu.com";
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webView.loadUrl(url);

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
//				progerBar.setVisibility(View.GONE);
//				dismissPDialog();
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 自身加载新链接,不做外部跳转
				Log.e("url", url);
//				if (!url.startsWith("http")) {
////					Log.i("shouldOverrideUrlLoading", "处理自定义scheme");
////					Toast.makeText(WebViewActivity.this, "需要下载腾讯客户端",
////							Toast.LENGTH_LONG).show();
//					try {
//						// 以下固定写法
//						final Intent intent = new Intent(Intent.ACTION_VIEW,
//								Uri.parse(url));
//						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//								| Intent.FLAG_ACTIVITY_SINGLE_TOP);
//						startActivity(intent);
//					} catch (Exception e) {
//						// 防止没有安装的情况
//						e.printStackTrace();
//					}
//					return true;
//				}
				view.loadUrl(url);

				return true;
			}

		});
		
		
		
		webView.setWebChromeClient(new WebChromeClient() {});
		webView.setDownloadListener(new MyWebViewDownLoadListener());

	}
	private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }
}
