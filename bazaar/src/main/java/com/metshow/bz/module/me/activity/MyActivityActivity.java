package com.metshow.bz.module.me.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kwan.base.activity.CommonRecycleActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.metshow.bz.commons.adapter.ActivityAdapter;
import com.metshow.bz.commons.bean.BzActivity;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.shai.BZActivityInfoActivity;
import com.metshow.bz.presenter.MyActivityPresenter;

import java.util.ArrayList;
import java.util.List;

public class MyActivityActivity extends CommonRecycleActivity implements BZContract.IMyActivityView, BaseQuickAdapter.OnRecyclerViewItemClickListener {

	List<BzActivity> mBzActivities = new ArrayList<>();
	MyActivityPresenter myActivityPresenter;
	private boolean isLoadMore;

	@Override
	protected String getTitleTxt() {
		return "参加的活动";
	}

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		myActivityPresenter = new MyActivityPresenter(this);
	}

	@Override
	protected void initViews() {
		super.initViews();
		mAdapter.setOnRecyclerViewItemClickListener(this);
	}

	@Override
	protected BaseQuickAdapter getAdapter() {

		ActivityAdapter adapter = new ActivityAdapter(this, mBzActivities);
		adapter.setIsShowGo(false);
		return adapter;
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {
		return new LinearLayoutManager(this);
	}

	@Override
	public void onLoadMore() {
		super.onLoadMore();
		isLoadMore = true;
		myActivityPresenter.getMyActivity(pageIndex, 20);
	}

	@Override
	public void onRefresh() {
		super.onRefresh();
		showProgress("");
		isLoadMore = false;
		myActivityPresenter.getMyActivity(1, 20);
	}

	@Override
	public void getMyActivitySuccess(List<BzActivity> activities) {

		if (isLoadMore) {
			mBzActivities.addAll(activities);
			mAdapter.addData(activities);
		} else {
			mBzActivities.clear();
			mBzActivities.addAll(activities);
			mAdapter.setNewData(activities);
		}
		swipeRefreshLayout.setLoadingMore(false);
		swipeRefreshLayout.setRefreshing(false);
		dismissProgress();
	}

	@Override
	public void onItemClick(View view, int position) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("data", mBzActivities.get(position));
		go2ActivityWithLeft(BZActivityInfoActivity.class, bundle, false);
	}
}
