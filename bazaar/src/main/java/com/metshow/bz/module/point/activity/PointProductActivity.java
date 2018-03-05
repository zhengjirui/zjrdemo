package com.metshow.bz.module.point.activity;

import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwan.base.activity.BaseCommonActivity;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.PointProduct;
import com.metshow.bz.commons.bean.PointResult;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.PointProductPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

public class PointProductActivity extends BaseCommonActivity implements BZContract.IPointResultView {


	PointProduct mPointProduct;
	ImageView iv_icon;
	TextView tv_name, tv_point, tv_price, tv_submit;
	private WebView webview;
	PointProductPresenter mPresenter;
	EditText et_name, et_address, et_phone;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mPointProduct = (PointProduct) getIntentData("data");
		mPresenter = new PointProductPresenter(this);
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_point_product;
	}

	@Override
	protected void initViews() {
		super.initViews();

		tv_name = (TextView) findViewById(R.id.tv_name);
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_point = (TextView) findViewById(R.id.tv_point);
		tv_submit = (TextView) findViewById(R.id.tv_submit);

		et_name = (EditText) findViewById(R.id.et_name);
		et_address = (EditText) findViewById(R.id.et_address);
		et_phone = (EditText) findViewById(R.id.et_phone);



		if (BZSPUtil.getUser().getPoints() < mPointProduct.getPoints()) {
			tv_submit.setText("积分不足");
			tv_submit.setBackgroundColor(getResources().getColor(R.color.divider_color));
		} else {
			tv_submit.setText("立即兑换");

			tv_submit.setBackgroundColor(getResources().getColor(R.color.redWin));
			tv_submit.setTextColor(getResources().getColor(R.color.white));
			tv_submit.setOnClickListener(this);
		}

		et_name.setText(BZSPUtil.getUser().getTrueName());
		et_address.setText(BZSPUtil.getUser().getAddress());
		et_phone.setText(BZSPUtil.getUser().getPhoneNum());

		mImageUtil.loadFromUrlCenterInside(mPointProduct.getPics(), iv_icon);
		tv_point.setText(mPointProduct.getPoints() + "");
		tv_name.setText(mPointProduct.getName());
		tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		tv_price.setText("¥ " + mPointProduct.getPrice());

		final String url = mPointProduct.getDescription();
		webview = (com.tencent.smtt.sdk.WebView) findViewById(R.id.webView);
		WebSettings mSetting = webview.getSettings();
		mSetting.setJavaScriptEnabled(true);
		mSetting.setDefaultTextEncodingName("UTF-8");
		mSetting.setCacheMode(WebSettings.LOAD_DEFAULT);

		//支持javascript
		mSetting.setJavaScriptEnabled(true);
// 设置可以支持缩放
		mSetting.setSupportZoom(true);
// 设置出现缩放工具
		mSetting.setBuiltInZoomControls(true);
//扩大比例的缩放
		mSetting.setUseWideViewPort(true);
//自适应屏幕
		mSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		mSetting.setLoadWithOverviewMode(true);

		if (Build.VERSION.SDK_INT >= 19) {
			mSetting.setLoadsImagesAutomatically(true);
		} else {
			mSetting.setLoadsImagesAutomatically(false);
		}

//        webview.addJavascriptInterface(new ProductsInfoActivityJSService(
//                this),"Javahelper");// 这在前

		webview.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String s) {
				if (url.startsWith("http:") || url.startsWith("https:")) {
					webView.loadUrl(url);
					return false;
				} else {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
					return true;
				}
			}

			@Override
			public void onPageFinished(com.tencent.smtt.sdk.WebView view, String url) {
				super.onPageFinished(view, url);
				if (!view.getSettings().getLoadsImagesAutomatically()) {
					view.getSettings().setLoadsImagesAutomatically(true);
				}
			}
		});
		webview.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (webview != null) {
					webview.loadDataWithBaseURL("", url, "text/html", "UTF-8", "");
				}
			}
		}, 100);
	}

	@Override
	protected void initData() {

	}

	@Override
	protected String getTitleTxt() {
		return "商品兑换";
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v == tv_submit)
			doSubmit();
	}

	private void doSubmit() {

		if (mPointProduct.getType() == 1) {//实物
			mPresenter.getPointResult(mPointProduct.getPointShopId(),
					et_name.getText().toString(),et_phone.getText().toString(),et_address.getText().toString());
		}else
			mPresenter.getPointResult(mPointProduct.getPointShopId(),null,null,null);
	}

	@Override
	public void getPointResultSuccess(PointResult result) {

	}
}
