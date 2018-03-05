package com.kwan.base.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.kwan.base.R;
import com.kwan.base.adatper.CommonFragmentPageAdapter;
import com.kwan.base.widget.SlidingTabLayout;

/**
 * 包含一个ViewPage的fragment
 */
public abstract class BasePageFragment extends BasePageItemFragment implements View.OnClickListener {

    protected SlidingTabLayout mTabLayout;
    protected ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabLayout = (SlidingTabLayout) mBaseContentView.findViewById(R.id.tabs);
        mViewPager = (ViewPager) mBaseContentView.findViewById(R.id.viewPager);
    }

	@Override
	protected int getContentViewId() {
		return R.layout.layout_base_page;
	}

    protected String getTitle() {
        return "title";
    }

    protected void setUpViewPage(final CommonFragmentPageAdapter adapter) {
        setUpViewPage(adapter, false, 16);
    }

    protected void setUpViewPage(final CommonFragmentPageAdapter adapter, boolean isDivider) {
        setUpViewPage(adapter, isDivider, 16);
    }

    protected void setUpViewPage(final CommonFragmentPageAdapter adapter, int titleSize) {
        setUpViewPage(adapter, false, titleSize);
    }

    protected void setUpViewPage(final CommonFragmentPageAdapter adapter, boolean isDivider, int titleSize) {

        mViewPager.setAdapter(adapter);
        int mainColor = getResources().getColor(R.color.colorMain);

        mTabLayout.setTitleTextColor(mainColor, getResources().getColor(R.color.txt_cobalt_blue));//标题字体颜色
        // mTabLayout.setTabStripWidth(110);//滑动条宽度
        mTabLayout.setSelectedIndicatorColors(mainColor);//滑动条颜色
        mTabLayout.setTabTitleTextSize(titleSize);//标题字体大小
        mTabLayout.setDistributeEvenly(true); //均匀平铺选项卡
        if (isDivider)
            mTabLayout.setDividerColors(mBaseActivity.getResources().getColor(R.color.divider_color));

        mTabLayout.setViewPager(mViewPager);

    }

    @Override
    public void onClick(View v) {

    }

}
