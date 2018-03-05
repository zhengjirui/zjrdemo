package com.metshow.bz.module.point.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kwan.base.activity.BaseCommonActivity;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.PointDetailAdapter;
import com.metshow.bz.commons.bean.PointDetail;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.PointDetailPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class PointDetailActivity extends BaseCommonActivity implements BZContract.IPointDetailView, OnRefreshListener, OnLoadMoreListener {

	AutoLinearLayout ll_record;
	private TextView tv_point;
	private RecyclerView mRecyclerView;
	private SwipeToLoadLayout swipeRefreshLayout;
	PointDetailPresenter presenter;

	PointDetailAdapter mAdapter;
	ArrayList<PointDetail> mPointDetails = new ArrayList<>();
	private int pageIndex;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		presenter = new PointDetailPresenter(this);
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_point_detail;
	}

	@Override
	protected void initViews() {
		super.initViews();
		ll_record = (AutoLinearLayout) findViewById(R.id.ll_record);
		mRecyclerView = (ObservableRecyclerView) findViewById(com.kwan.base.R.id.swipe_target);
		swipeRefreshLayout = (SwipeToLoadLayout) findViewById(com.kwan.base.R.id.swipeToLoadLayout);

		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//mRecyclerView.setScrollViewCallbacks(observableScrollViewCallbacks);
		mRecyclerView.setHasFixedSize(true);
		mAdapter = new PointDetailAdapter(this,mPointDetails);

		mRecyclerView.setAdapter(mAdapter);
		//麻痹的 关闭硬件加速 否则 cardView 报错

		mRecyclerView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		mRecyclerView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

		swipeRefreshLayout.setRefreshEnabled(true);
		swipeRefreshLayout.setLoadMoreEnabled(true);

		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setOnLoadMoreListener(this);

		tv_point= (TextView) findViewById(R.id.tv_point);
		tv_point.setText(BZSPUtil.getUser().getPoints()+"");
		ll_record.setOnClickListener(this);

	}

	@Override
	protected void initData() {
		swipeRefreshLayout.setRefreshing(true);
	}

	@Override
	protected String getTitleTxt() {
		return "积分明细";
	}

	@Override
	public void getPointDetailSuccess(List<PointDetail> items) {
		Log.e("kwan","success");
		//mPointDetails.addAll(items);

//		mBaseAdapter = getAdapter();


		if (isLoadMore) {
			mAdapter.addData(items);
			mPointDetails.addAll(items);
		} else {
			mPointDetails.clear();
			mPointDetails.addAll(items);
			mAdapter.setNewData(items);
		}

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);

	}

	boolean isLoadMore = false;

	@Override
	public void onRefresh() {
		isLoadMore = false;
		pageIndex = 1;
		presenter.getPointProductList(pageIndex,20);
	}

	@Override
	public void onLoadMore() {
		isLoadMore = true;
		pageIndex++;
		presenter.getPointProductList(pageIndex,20);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v==ll_record){
			go2ActivityWithLeft(PointRecordActivity.class,null,false);
		}
	}
}
