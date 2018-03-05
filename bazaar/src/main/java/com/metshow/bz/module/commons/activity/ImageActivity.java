package com.metshow.bz.module.commons.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwan.base.activity.BaseActivity;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.BZViewPageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends BaseActivity implements ViewPager.OnPageChangeListener, BZViewPageAdapter.ClickItemListener {

	private ViewPager viewPager;
	private TextView tv_index;
	private List<ImageView> imageViews;
	private ArrayList<String> urls;
	private ArrayList<String> titles;
	private int size;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		urls = (ArrayList<String>) getIntentData("urls");
		//titles= (ArrayList<String>) getIntentData("titles");
	}

	@Override
	protected int getTitleBarViewId() {
		return 0;
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_image;
	}

	@Override
	protected int getBottomViewId() {
		return 0;
	}

	@Override
	protected void initViews() {
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		tv_index = (TextView) findViewById(R.id.tv_index);
	}

	@Override
	protected void initViewSetting() {

		imageViews = new ArrayList<>();
		titles = new ArrayList<>();

		size = urls.size();
		tv_index.setText("1/" + size);
		for (String url : urls) {
			ImageView view = (ImageView) getLayoutInflater().inflate(R.layout.full_image_view, null);
			mImageUtil.loadFromUrlCenterInside(url, view);
			view.setOnClickListener(this);
			imageViews.add(view);
		}

		BZViewPageAdapter<ImageView> adapter = new BZViewPageAdapter<>(imageViews, titles);
		viewPager.setAdapter(adapter);
		viewPager.addOnPageChangeListener(this);

		adapter.setOnClickItemListener(this);

	}

	@Override
	protected int getBackGroundColor() {
		return Color.BLACK;
	}

	@Override
	protected void initData() {

	}


	@Override
	protected BasePresenter getPresent() {
		return null;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		tv_index.setText(position + 1 + "/" + size);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.iv_full)
			onBackPressed();
	}

	@Override
	protected Bitmap getBackGroundBitmap() {
		return null;
	}

	@Override
	public void onClickItem(ViewGroup container, int position, View view) {
		onBackPressed();
	}
}
