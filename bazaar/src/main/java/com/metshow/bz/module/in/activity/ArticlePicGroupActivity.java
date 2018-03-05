package com.metshow.bz.module.in.activity;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.ViewUtil;
import com.metshow.bz.IUmengEvent;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.BZViewPageAdapter;
import com.metshow.bz.commons.bean.article.Article;
import com.metshow.bz.commons.bean.article.ArticlePicture;
import com.metshow.bz.util.ShareUtil;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;


public class ArticlePicGroupActivity extends BaseArticleActivity implements ViewPager.OnPageChangeListener, BZViewPageAdapter.ClickItemListener {


	private ViewPager mViewPager;
	private TextView tv_index;
	private List<ArticlePicture> pictures;
	private List<View> views;
	private Article mArticle;
	private boolean isFullMode = false;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_article_pic_group;
	}

	@Override
	protected void initViews() {
		super.initViews();
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		tv_index = (TextView) findViewById(R.id.tv_index);
	}

	@Override
	public boolean isFullScreen() {
		return true;
	}

	@Override
	protected BasePresenter getPresent() {
		return null;
	}

	@Override
	public void getArticleSuccess(Article result) {
		super.getArticleSuccess(result);

		mArticle = result;
		pictures = result.getArticlePictures();
		tv_index.setText("1/" + pictures.size());
		views = new ArrayList<>();

		View viewx = getLayoutInflater().inflate(R.layout.item_article_picgroup, mViewPager, false);
		TextView tv = (TextView) viewx.findViewById(R.id.tv_remark);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
		tv.setPadding(60, 40, 60, 40);
		tv.setText("啊");
		ViewUtil.measureView(tv);
		int oneLineHeight = tv.getMeasuredHeight();

		for (ArticlePicture picture : pictures) {
			View view = getLayoutInflater().inflate(R.layout.item_article_picgroup, mViewPager, false);

			mImageUtil.loadFromUrl(picture.getPicPath(), (ImageView) view.findViewById(R.id.iv_icon));

			TextView tv_remark = (TextView) view.findViewById(R.id.tv_remark);
			NestedScrollView scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);

			tv_remark.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
			tv_remark.setPadding(60, 40, 60, 40);
			tv_remark.setText(picture.getRemark());

			ViewUtil.measureView(tv_remark);
			int h = tv_remark.getMeasuredHeight();
			AutoFrameLayout.LayoutParams lp = new AutoFrameLayout.LayoutParams(AutoFrameLayout.LayoutParams.MATCH_PARENT, h);
			lp.gravity = Gravity.BOTTOM;
			scrollView.setLayoutParams(lp);

			View divider = view.findViewById(R.id.divider);
			divider.setLayoutParams(new AutoLinearLayout.LayoutParams(AutoLinearLayout.LayoutParams.MATCH_PARENT, h - oneLineHeight));
			ViewUtil.measureView(divider);

			if (h < divider.getMeasuredHeight())
				divider.setVisibility(View.GONE);

			views.add(view);

		}

		BZViewPageAdapter<View> pageAdapter = new BZViewPageAdapter<>(views);
		pageAdapter.setOnClickItemListener(this);
		mViewPager.setAdapter(pageAdapter);
		mViewPager.setOnPageChangeListener(this);

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		tv_index.setText((position + 1) + "/6");
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onClickItem(ViewGroup container, int position, View view) {

		isFullMode = !isFullMode;

		if (isFullMode) {
			view.findViewById(R.id.scrollView).setVisibility(View.GONE);
			mBottomView.setVisibility(View.GONE);
		} else {
			view.findViewById(R.id.scrollView).setVisibility(View.VISIBLE);
			mBottomView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v == iv_resend) {
			ShareUtil util = new ShareUtil(this);
			util.setShare_Type(IUmengEvent.ArticleShare);
			util.setStr_url("http://bz.metshow.com/api/share.html?articleid=" + mArticle.getArticleId());
			util.setStr_title(mArticle.getTitle());
			util.setmText(mArticle.getSummary());
			util.setStr_img(mArticle.getIco());
			util.showShareDialog();
		}
	}

	@Override
	protected Bitmap getBackGroundBitmap() {
		return null;
	}

	@Override
	protected int getTitleBarViewId() {
		return 0;
	}
}
