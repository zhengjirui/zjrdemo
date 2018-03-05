package com.metshow.bz.module.me.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kwan.base.activity.BaseCommonActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.MyCommentAdapter;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.in.activity.ArticleActivity;
import com.metshow.bz.presenter.BzPresenter;
import com.metshow.bz.presenter.CommentPresenter;

import java.util.ArrayList;
import java.util.List;

public class MyCommentActivity extends BaseCommonActivity implements OnRefreshListener, OnLoadMoreListener, BZContract.ICommentView, BaseQuickAdapter.OnRecyclerViewItemClickListener {

	private RecyclerView mRecyclerView;
	private SwipeToLoadLayout swipeRefreshLayout;
	private CommentPresenter mPresenter;
	private MyCommentAdapter mAdapter;
	private List<Comment> comments = new ArrayList<>();
	private boolean isLoadMore;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mPresenter = new CommentPresenter(this);
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_my_comment;
	}

	@Override
	protected void initViews() {
		super.initViews();

		mRecyclerView = (ObservableRecyclerView) findViewById(com.kwan.base.R.id.swipe_target);
		swipeRefreshLayout = (SwipeToLoadLayout) findViewById(com.kwan.base.R.id.swipeToLoadLayout);

		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//mRecyclerView.setScrollViewCallbacks(observableScrollViewCallbacks);
		mRecyclerView.setHasFixedSize(true);
		mAdapter = new MyCommentAdapter(this,comments);
		mAdapter.setOnRecyclerViewItemClickListener(this);
		mRecyclerView.setAdapter(mAdapter);
		//麻痹的 关闭硬件加速 否则 cardView 报错

		mRecyclerView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		mRecyclerView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

		swipeRefreshLayout.setRefreshEnabled(true);
		swipeRefreshLayout.setLoadMoreEnabled(true);

		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setOnLoadMoreListener(this);
	}

	@Override
	protected void initData() {
		swipeRefreshLayout.setRefreshing(true);

	}

	@Override
	protected String getTitleTxt() {
		return "我的评论";
	}

	@Override
	public void onRefresh() {
		isLoadMore = false;
		mPresenter.getMyComment("0");
	}

	@Override
	public void onLoadMore() {
		isLoadMore = true;
		mPresenter.getMyComment(BzPresenter.getData(comments.get(comments.size()-1).getCreateDate()));
	}

	@Override
	public void getMyCommentSuccess(List<Comment> comments) {
		
		if (isLoadMore) {
			mAdapter.addData(comments);
			this.comments.addAll(comments);
		} else {
			this.comments.clear();
			this.comments.addAll(comments);
			mAdapter.setNewData(comments);
		}

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}

	@Override
	public void getCommentListSuccess(List<Comment> comments) {

	}

	@Override
	public void addCommnetSuccess(int result) {

	}

	@Override
	public void reportSuccess(Integer integer) {

	}

	@Override
	public void onItemClick(View view, int position) {

		Bundle bundle = new Bundle();
		bundle.putLong("id", comments.get(position).getRefId());
		bundle.putString("icon", comments.get(position).getSourceImage());
		bundle.putBoolean("isFav", false);
		go2Activity(ArticleActivity.class, bundle, false);
	}
}
