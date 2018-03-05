package com.metshow.bz.module.commons.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.commons.adapter.ArticleSearchAdapter;
import com.metshow.bz.commons.bean.article.FavArticle;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.BaseBZRecycleFragment;
import com.metshow.bz.module.in.activity.ArticleActivity;
import com.metshow.bz.presenter.FavArticlePresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * 文章列表fragment
 * <p>
 * Created by Mr.Kwan on 2017-2-23.
 */

public class FavArticleListFragment extends BaseBZRecycleFragment implements BZContract.IFavArticleView, BaseQuickAdapter.OnRecyclerViewItemClickListener {

	private List<FavArticle> mFavArticles = new ArrayList<>();
	private ArticleSearchAdapter<FavArticle> mArticleAdapter;
	protected FavArticlePresenter mPresenter;
	private String MaxDate = "0";

	public static FavArticleListFragment newInstance() {

		FavArticleListFragment fragment = new FavArticleListFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPresenter = new FavArticlePresenter(this);
	}

	@Override
	protected BaseQuickAdapter getAdapter() {
		mArticleAdapter = new ArticleSearchAdapter<>(mBaseActivity, mFavArticles);
		mArticleAdapter.setOnRecyclerViewItemClickListener(this);
		return mArticleAdapter;
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {
		return new LinearLayoutManager(mBaseActivity);
	}

	@Override
	public void onRefresh() {
		super.onRefresh();
		MaxDate = "0";
		mPresenter.getFavArticle(10, MaxDate);
	}

	@Override
	public void onLoadMore() {
		super.onLoadMore();
		mPresenter.getFavArticle(10, BasePresenter.getData(mFavArticles.get(mFavArticles.size() - 1).getCreateDate()));
	}

	@Override
	public void getFavArticleSuccess(List<FavArticle> favArticles) {
		if (favArticles.size() > 0)

			if (isLoadMore) {
				mFavArticles.addAll(favArticles);
				mArticleAdapter.addData(favArticles);
			} else {
				mFavArticles.clear();
				mFavArticles.addAll(favArticles);
				mArticleAdapter.setNewData(favArticles);
			}

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}

	@Override
	public void onItemClick(View view, int position) {
		FavArticle article = mFavArticles.get(position);
		Bundle bundle = new Bundle();
		bundle.putLong("id", article.getRefId());
		bundle.putString("icon", article.getIco());
		bundle.putBoolean("isFav", article.getIsFav() > 0);
		mBaseActivity.go2ActivityWithLeft(ArticleActivity.class,bundle,false);
	}
}
