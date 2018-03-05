package com.metshow.bz.module.commons.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.kwan.base.activity.BaseActivity;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.R;
import com.metshow.bz.module.main.MainActivity;
import com.metshow.bz.util.ShareUtil;
import com.orhanobut.logger.Logger;


public class WebProductActivity extends BaseActivity {

    private String url, txt, title, img_url;
    private WebView mWebView;
    private ImageView iv_refresh, iv_close, iv_back, iv_fron, iv_share;

    private boolean isBack2Main = false;

    @Override
    public boolean isFullScreen() {
        return true;
    }

    @Override
    protected int getBottomViewId() {
        return R.layout.web_bottom;
    }

	@Override
	protected int getTitleBarViewId() {
		return 0;
	}

	@Override
    protected void beForeSetContentView() {

        url = getIntent().getStringExtra("url");
        txt = getIntent().getStringExtra("txt");
        title = getIntent().getStringExtra("title");
        img_url = getIntent().getStringExtra("img_url");

        isBack2Main = getIntent().getBooleanExtra("is2main", false);


        Logger.d(url);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_productweb;
    }

    @Override
    protected void initViews() {

        mWebView = (WebView) findViewById(R.id.webView);

        iv_refresh = (ImageView) findViewById(R.id.iv_fresh);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_fron = (ImageView) findViewById(R.id.iv_fron);
        iv_share = (ImageView) findViewById(R.id.iv_share);

        WebSettings webseting = mWebView.getSettings();
        webseting.setJavaScriptEnabled(true);

        webseting.setAllowFileAccess(true);// 设置允许访问文件数据
        webseting.setSupportZoom(true);//支持放大网页功能
        webseting.setBuiltInZoomControls(true);//支持缩小网页功能
        webseting.setAppCacheEnabled(false);
        webseting.setDisplayZoomControls(false);
        webseting.setCacheMode(WebSettings.LOAD_DEFAULT);

        //扩大比例的缩放
        webseting.setUseWideViewPort(true);
        //自适应屏幕
        webseting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webseting.setLoadWithOverviewMode(true);

        iv_refresh.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_fron.setOnClickListener(this);
        iv_share.setOnClickListener(this);

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
               dismissProgress();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                mWebView.setVisibility(View.VISIBLE);
                mWebView.bringToFront();
                showProgress("");


            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                //  super.onReceivedError(view, request, error);
                Log.d(getTag(), "erro::" + error.getDescription());
//                tv_retry.setVisibility(View.VISIBLE);
//                tv_retry.bringToFront();
//                mWebView.setVisibility(View.GONE);
//                Toast.makeText(WebActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
            }
        });
        mWebView.loadUrl(url);
    }

    @Override
    protected void initViewSetting() {

    }

    @Override
    protected void initData() {

    }


    @Override
    protected BasePresenter getPresent() {
        return null;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v == iv_back) {
            mWebView.goBack();
        } else if (v == iv_close) {
            onBackPressed();
        } else if (v == iv_refresh) {
            mWebView.reload();
        } else if (v == iv_fron) {
            mWebView.goForward();
        } else if (v == iv_share) {

            ShareUtil util = new ShareUtil(this);
            util.setStr_url(url);
            util.setmText(txt);
            util.setStr_title(title);
            util.setStr_img(img_url);
            util.showShareDialog();
        }

    }

	@Override
	protected Bitmap getBackGroundBitmap() {
		return null;
	}

	@Override
    protected int getBackGroundColor() {
        ll_base_main.setFitsSystemWindows(false);
        return Color.parseColor("#00000000");
    }


    @Override
    public void onBackPressed() {

        if (isBack2Main) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        super.onBackPressed();
    }
}
