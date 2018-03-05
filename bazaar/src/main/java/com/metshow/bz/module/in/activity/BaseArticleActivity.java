package com.metshow.bz.module.in.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwan.base.activity.BaseActivity;
import com.kwan.base.util.SPUtil;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.article.Article;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.activity.CommentActivity;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.presenter.ArticlePresenter;
import com.zhy.autolayout.AutoLinearLayout;


/**
 * Created by Mr.Kwan on 2016-10-13.
 */

public abstract class BaseArticleActivity extends BaseActivity implements BZContract.IArticleView {

	protected ImageView iv_back, iv_fav, iv_comment, iv_resend;
	private AutoLinearLayout ll_comment, ll_fav;
	private TextView tv_comment, tv_fav;
	private long id;
	private boolean isFav;
	private ArticlePresenter mPresenter;
	private Article mArticle;

	@Override
	protected int getBottomViewId() {
		return R.layout.articel_bottom;
	}

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mPresenter = new ArticlePresenter(this);
		id = (long) getIntentData("id");
		// icon = (String) getIntentData("icon");
		isFav = (boolean) getIntentData("isFav");

		// 将表情map的key保存在数组中
//        Set<String> keySet = App.getInstance().getFaceMap().keySet();
//        mFaceMapKeys = new ArrayList<>();
//        mFaceMapKeys.addAll(keySet);
	}

	@Override
	protected void initViews() {

		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_fav = (ImageView) findViewById(R.id.iv_fav);
		iv_comment = (ImageView) findViewById(R.id.iv_comment);
		iv_resend = (ImageView) findViewById(R.id.iv_resend);

		ll_comment = (AutoLinearLayout) findViewById(R.id.ll_comment);
		ll_fav = (AutoLinearLayout) findViewById(R.id.ll_fav);

		tv_comment = (TextView) findViewById(R.id.tv_comment);
		tv_fav = (TextView) findViewById(R.id.tv_fav);

	}

	@Override
	protected void initViewSetting() {
		iv_back.setOnClickListener(this);
		iv_resend.setOnClickListener(this);
		ll_comment.setOnClickListener(this);
		ll_fav.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		showProgress("");
		mPresenter.getArticleDetail(id);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v == iv_back) {
			onBackPressed();
		} else if (v == ll_fav) {

			if (SPUtil.getIsLogin()) {
				showProgress("");
				if (isFav)
					mPresenter.deleteFav(id, 0);
				else
					mPresenter.addAcition(1, id, 0);

			} else {
				go2Activity(DologinActivity.class, null, false);
			}

		} else if (v == ll_comment && mArticle != null) {
			if (SPUtil.getIsLogin()) {
				Bundle bundle = new Bundle();
				bundle.putLong("refid", mArticle.getArticleId());
				bundle.putInt("type", CommentActivity.TYPE_ARTICAL);
				go2ActivityForResult(CommentActivity.class, 100, bundle, R.anim.push_left_in, R.anim.push_left_out);
			} else {
				go2Activity(DologinActivity.class, null, false);
			}
		}

	}

	@Override
	public void getArticleSuccess(Article result) {
		mArticle = result;
		if (mArticle.getIsFav() > 0 || isFav) {
			iv_fav.setImageResource(R.mipmap.article_fav_icon);
			isFav = true;
		} else {
			isFav = false;
		}
		tv_fav.setText(result.getFavCount() + "");
		tv_comment.setText(result.getCommentCount() + "");
		dismissProgress();
	}

	@Override
	public void favSuccess(String result, int position) {
		iv_fav.setImageResource(R.mipmap.article_fav_icon);
		isFav = !isFav;
		dismissProgress();
	}

	@Override
	public void deleteFavSuccess(String result, int position) {
		iv_fav.setImageResource(R.mipmap.article_fav_normal_icon);
		isFav = !isFav;
		dismissProgress();
	}
}
