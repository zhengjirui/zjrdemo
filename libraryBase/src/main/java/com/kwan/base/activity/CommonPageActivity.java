package com.kwan.base.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.kwan.base.R;
import com.kwan.base.widget.SlidingTabLayout;

/**
 * Created by Mr.Kwan on 2017-2-23.
 */

public abstract class CommonPageActivity extends BaseCommonActivity {

	protected SlidingTabLayout mTabLayout;
	protected ViewPager mViewPager;

	@Override
	protected int getContentViewId() {
		return R.layout.layout_base_page;
	}

	@Override
	protected void initViews() {
		super.initViews();

		mTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
	}

	@Override
	protected void initData() {

	}

	protected void setUpViewPage(final PagerAdapter adapter) {
		setUpViewPage(adapter, false, 12, getResources().getColor(R.color.redWin), getResources().getColor(R.color.txt_cobalt_blue));
	}

	protected void setUpViewPage(final PagerAdapter adapter, boolean isDivider, int selectTextColor, int unSelectTextColor) {
		setUpViewPage(adapter, isDivider, 12, selectTextColor, unSelectTextColor);
	}

	protected void setUpViewPage(final PagerAdapter adapter, int titleSize, int selectTextColor, int unSelectTextColor) {
		setUpViewPage(adapter, false, titleSize, selectTextColor, unSelectTextColor);
	}

	protected void setUpViewPage(final PagerAdapter adapter, boolean isDivider, int titleSize, int selectTextColor, int unSelectTextColor) {

		mViewPager.setAdapter(adapter);
		mTabLayout.setTitleTextColor(selectTextColor, unSelectTextColor);//标题字体颜色
		// mTabLayout.setTabStripWidth(110);//滑动条宽度
		mTabLayout.setSelectedIndicatorColors(selectTextColor);//滑动条颜色
		mTabLayout.setTabTitleTextSize(titleSize);//标题字体大小
		mTabLayout.setDistributeEvenly(true); //均匀平铺选项卡
		if (isDivider)
			mTabLayout.setDividerColors(getResources().getColor(R.color.divider_color));

		mTabLayout.setViewPager(mViewPager);

	}
}
