package com.metshow.bz.module.me.activity;

import android.graphics.Bitmap;

import com.kwan.base.activity.CommonPageActivity;
import com.kwan.base.adatper.CommonFragmentPageAdapter;
import com.metshow.bz.R;
import com.metshow.bz.module.commons.fragment.FavArticleListFragment;
import com.metshow.bz.module.commons.fragment.ProductListFragment;
import com.metshow.bz.module.commons.fragment.TopicListFragment;

/**
 *
 * Created by Mr.Kwan on 2017-2-23.
 */

public class FavoriteActivity extends CommonPageActivity {

	private CommonFragmentPageAdapter mPageAdapter;

	@Override
	protected String getTitleTxt() {
		return "喜欢";
	}

	@Override
	protected Bitmap getBackGroundBitmap() {
		return null;
	}

	@Override
	protected void initData() {
		super.initData();
		mPageAdapter = new CommonFragmentPageAdapter(getSupportFragmentManager());

		FavArticleListFragment articleFragment = FavArticleListFragment.newInstance();
		TopicListFragment topicFragment = TopicListFragment.newInstance();
		ProductListFragment productFragment = ProductListFragment.newInstance();

		mPageAdapter.addFragment(articleFragment, "文章");
		mPageAdapter.addFragment(topicFragment, "帖子");
		mPageAdapter.addFragment(productFragment, "商品");

		setUpViewPage(mPageAdapter,12,getResources().getColor(R.color.bazzarRed),getResources().getColor(R.color.txt_cobalt_blue));

	}
}
