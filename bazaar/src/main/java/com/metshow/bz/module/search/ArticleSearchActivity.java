package com.metshow.bz.module.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.ArticleSearchAdapter;
import com.metshow.bz.commons.adapter.TagAdapter;
import com.metshow.bz.commons.bean.article.Article;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.in.activity.ArticleActivity;
import com.metshow.bz.presenter.ArticleSearchPresenter;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ArticleSearchActivity extends BaseSearchActivity implements BZContract.IArticleSearchView, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnRecyclerViewItemClickListener {

	private AutoLinearLayout ll_tag;
	private ArticleSearchAdapter mArticleAdapter;
	private ArticleSearchPresenter mPresenter;
	private ObservableRecyclerView rl_hot, rl_history, rl_articl;
	private SwipeToLoadLayout swipeToLoadLayout;
	TagAdapter mTagAdapter;
	private ArrayList<Article> mArticles = new ArrayList<>();

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mPresenter = new ArticleSearchPresenter(this);

	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_article_search;
	}

	@Override
	protected void initViews() {
		super.initViews();
		ll_tag = (AutoLinearLayout) findViewById(R.id.ll_tag);
		rl_hot = (ObservableRecyclerView) findViewById(R.id.rl_hot);
		rl_history = (ObservableRecyclerView) findViewById(R.id.rl_history);
		rl_articl = (ObservableRecyclerView) findViewById(R.id.rl_article);

		initRecycler(rl_hot);
		initRecycler(rl_history);
		initRecycler(rl_articl);

		mArticleAdapter = new ArticleSearchAdapter(this, mArticles);
		mArticleAdapter.setOnRecyclerViewItemClickListener(this);
		rl_articl.setAdapter(mArticleAdapter);
		swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);

		swipeToLoadLayout.setEnabled(true);
//		swipeToLoadLayout.setRefreshEnabled(false);
//		swipeToLoadLayout.setLoadMoreEnabled(false);

		swipeToLoadLayout.setOnRefreshListener(this);
		swipeToLoadLayout.setOnLoadMoreListener(this);

		et_search.setHint("搜索文章");
	}

	@Override
	public void onSearch(String txt) {
		showProgress("");
		mPresenter.searchArticle(txt, 20, "0");
	}

	void initRecycler(RecyclerView recyclerView) {
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		recyclerView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		recyclerView.setHasFixedSize(true);
		//mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//解决更新 闪烁
		recyclerView.setItemAnimator(null);
		recyclerView.setNestedScrollingEnabled(false);

	}

	@Override
	protected void initData() {
		super.initData();
		showProgress("");
		mPresenter.getHotTag();
	}

	@Override
	public void getArticleSuccess(List<Article> items) {

		mArticles.addAll(items);
		mArticleAdapter.setNewData(items);

		ll_tag.setVisibility(View.GONE);
		rl_articl.setVisibility(View.VISIBLE);
		dismissProgress();
	}

	@Override
	public void showTags(final String[] tags) {

		mTagAdapter = new TagAdapter(this, java.util.Arrays.asList(tags));
		mTagAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
			@Override
			public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
				showProgress("");
				et_search.setHint(tags[position]);
				mPresenter.searchArticle(tags[position], 20, "0");

			}
		});
		rl_hot.setAdapter(mTagAdapter);
		dismissProgress();
	}

	@Override
	public void onRefresh() {

	}

	@Override
	public void onLoadMore() {

	}

	@Override
	public void onItemClick(View view, int position) {

		Article article = mArticles.get(position);
		Bundle bundle = new Bundle();
		bundle.putLong("id",article.getArticleId());
		bundle.putString("icon",article.getIco());
		bundle.putBoolean("isFav",article.getIsFav()==1);

		go2Activity(ArticleActivity.class,bundle,false);

	}
}
