package com.metshow.bz.module.buy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.kwan.base.activity.BaseCommonActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.util.SPUtil;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.adapter.CommentAdapter;
import com.metshow.bz.commons.adapter.ProductAdapter;
import com.metshow.bz.commons.bean.Product;
import com.metshow.bz.commons.bean.Special;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.module.shai.ProductActivity;
import com.metshow.bz.presenter.SpecialPresenter;
import com.metshow.bz.util.EditDialog;
import com.tencent.smtt.sdk.WebSettings;

import java.util.ArrayList;
import java.util.List;

public class SpecialActivity extends BaseCommonActivity implements BZContract.ISpecialView, BaseQuickAdapter.OnRecyclerViewItemChildClickListener, BaseQuickAdapter.OnRecyclerViewItemClickListener {

	private SpecialPresenter mSpecialPresenter;
	long mId;
	private ImageView iv_share;
	private RecyclerView rv_product, rv_comment;
	private List<Product> mProducts = new ArrayList<>();
	private List<Comment> mComments = new ArrayList<>();
	private ProductAdapter mProductAdapter;
	private CommentAdapter mCommentAdapter;

	private ImageView iv_icon;
	private TextView tv_name, tv_send;
	private Special mSpecial;

	private EditDialog mEditDialog;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mSpecial = (Special) getIntentData("data");
		mId = mSpecial.getProductSpecialId();
		mSpecialPresenter = new SpecialPresenter(this);
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_special;
	}

	@Override
	protected void initViews() {
		super.initViews();

		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		tv_name = (TextView) findViewById(R.id.tv_name);
		loadPic();
		tv_send = (TextView) findViewById(R.id.tv_send);
		tv_name.setText(mSpecial.getTitle());

		rv_product = (RecyclerView) findViewById(R.id.rv_product);
		rv_product.setNestedScrollingEnabled(false);
		mProductAdapter = new ProductAdapter(this, mProducts);
		mProductAdapter.setOnRecyclerViewItemChildClickListener(this);
		mProductAdapter.setOnRecyclerViewItemClickListener(this);

		rv_product.setLayoutManager(new GridLayoutManager(this, 2));
		rv_product.setItemAnimator(new DefaultItemAnimator());
		rv_product.setHasFixedSize(true);
		//麻痹的 关闭硬件加速 否则 cardView 报错
		rv_product.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		rv_product.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		rv_product.setAdapter(mProductAdapter);

		rv_comment = (RecyclerView) findViewById(R.id.rv_comment);
		rv_comment.setNestedScrollingEnabled(false);
		mCommentAdapter = new CommentAdapter(this, mComments);
		rv_comment.setLayoutManager(new LinearLayoutManager(this));
		rv_comment.setItemAnimator(new DefaultItemAnimator());
		rv_comment.setHasFixedSize(true);
		//麻痹的 关闭硬件加速 否则 cardView 报错
		rv_comment.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		rv_comment.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		rv_comment.setAdapter(mCommentAdapter);

		tv_send.setOnClickListener(this);

		mEditDialog = new EditDialog(this) {
			@Override
			protected void onSend(String s) {
				toastMsg(s);
			}

			@Override
			protected void onSelectImageBack(String s) {
			}
		};
	}

	void loadPic() {
		String icon = mSpecial.getIco();
		if (icon.endsWith(".gif"))
			mImageUtil.loadGifFromUrl(icon, iv_icon, App.SCREEN_WIDTH);
		else {
			Glide.with(this)
					.load(icon)
					.asBitmap()
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					.fitCenter()
					.placeholder(R.mipmap.item_default)
					.into(new ViewTarget<ImageView, Bitmap>(iv_icon) {
						@Override
						public void onLoadStarted(Drawable placeholder) {
							iv_icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
							iv_icon.setImageDrawable(placeholder);
						}

						@Override
						public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
							iv_icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
							iv_icon.setImageBitmap(resource);
						}
					});
		}
	}

	@Override
	protected void initData() {
		mSpecialPresenter.getSpecial(mId);
	}

	@Override
	protected String getTitleTxt() {
		return "专辑详情";
	}

	@Override
	protected int getTitleBarRightLayoutId() {
		return R.layout.title_right_follow;
	}

	@Override
	protected void setUpTitleRightView(View v) {
		super.setUpTitleRightView(v);
		iv_share = (ImageView) v;
		((ImageView) v).setImageResource(R.mipmap.banner_topic_share);
		iv_share.setOnClickListener(this);
	}

	@Override
	public void getSpecialSuccess(Special result) {

		onXWalkReady(result.getContent());

		mProductAdapter.setNewData(result.getRelativeProduct());
		mProducts.addAll(result.getRelativeProduct());
	}

	@Override
	public void getCommentsSuccess(List<Comment> result) {

		mCommentAdapter.setNewData(result);
		mComments.addAll(result);

	}

	@Override
	public void favSuccess(int result, int position) {
		mProducts.get(position).setIsFav(1);
		mProducts.get(position).setFavCount(result);
		mProductAdapter.getData().get(position).setIsFav(1);
		mProductAdapter.getData().get(position).setFavCount(result);
		mProductAdapter.notifyItemChanged(position);
		dismissProgress();
	}

	@Override
	public void deleteFavSuccess(int result, int position) {
		mProducts.get(position).setIsFav(0);
		mProducts.get(position).setFavCount(result);
		mProductAdapter.getData().get(position).setIsFav(0);
		mProductAdapter.getData().get(position).setFavCount(result);
		mProductAdapter.notifyItemChanged(position);
		dismissProgress();
	}

	private com.tencent.smtt.sdk.WebView webview;

	//获取到 服务器返回的 详情的HTml代码 后使用 腾讯的webview框架加载 详情html内容
	protected void onXWalkReady(final String url) {

		webview = (com.tencent.smtt.sdk.WebView) findViewById(R.id.webView);

		WebSettings mSetting = webview.getSettings();
		mSetting.setJavaScriptEnabled(true);
		mSetting.setDefaultTextEncodingName("UTF-8");
		mSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
		if (Build.VERSION.SDK_INT >= 19) {
			mSetting.setLoadsImagesAutomatically(true);
		} else {
			mSetting.setLoadsImagesAutomatically(false);
		}

//        webview.addJavascriptInterface(new ProductsInfoActivityJSService(
//                this),"Javahelper");   // 这在前

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

				mSpecialPresenter.getComment(mId);
			}
		});
		webview.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (webview != null) {
//					fl_load.setVisibility(View.GONE);
//					toolbar.setVisibility(View.VISIBLE);
//					ns_content.setVisibility(View.VISIBLE);
					webview.loadDataWithBaseURL("", url, "text/html", "UTF-8", "");
				}
			}
		}, 100);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v == tv_send) {
			mEditDialog.show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mEditDialog.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		mEditDialog.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	@Override
	public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
		if (SPUtil.getIsLogin()) {

			showProgress("");
			Product product = mProducts.get(position);

			if (product.getIsFav() > 0)
				mSpecialPresenter.deleteFav(product.getProductId(), position);
			else
				mSpecialPresenter.addAction(1, product.getProductId(), position);
		} else {

			Intent intent = new Intent(this, DologinActivity.class);
			startActivityForResult(intent, 101);

		}
	}

	@Override
	public void onItemClick(View view, int position) {
		Product product = mProducts.get(position);
		Bundle bundle = new Bundle();
		bundle.putString("name", product.getProductName());
		bundle.putLong("id", product.getProductId());
		go2ActivityWithLeft(ProductActivity.class, bundle, false);
	}
}
