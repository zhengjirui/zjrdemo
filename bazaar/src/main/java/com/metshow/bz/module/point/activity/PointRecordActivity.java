package com.metshow.bz.module.point.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kwan.base.activity.BaseCommonActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.config.Config;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.PointRecordAdapter;
import com.metshow.bz.commons.bean.PointRecord;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.activity.WebActivity;
import com.metshow.bz.presenter.PointRecordPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import static com.kwan.base.activity.BaseWebActivity.MODE_WEB;

public class PointRecordActivity extends BaseCommonActivity implements BZContract.IPointRecordlView, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnRecyclerViewItemClickListener {

	private PointRecordPresenter mPresenter;
	private RecyclerView mRecyclerView;
	private SwipeToLoadLayout swipeRefreshLayout;
	private PointRecordAdapter mAdapter;
	private ArrayList<PointRecord> mPointRecords = new ArrayList<>();
	private AutoLinearLayout ll_record;
	private TextView tv_point;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mPresenter = new PointRecordPresenter(this);
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_point_record;
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
		mAdapter = new PointRecordAdapter(this,mPointRecords);
		mAdapter.setOnRecyclerViewItemClickListener(this);
		mRecyclerView.setAdapter(mAdapter);
		//麻痹的 关闭硬件加速 否则 cardView 报错

		mRecyclerView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		mRecyclerView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

		swipeRefreshLayout.setRefreshEnabled(true);
		swipeRefreshLayout.setLoadMoreEnabled(true);

		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setOnLoadMoreListener(this);

		ll_record = (AutoLinearLayout) findViewById(R.id.ll_record);
		ll_record.setOnClickListener(this);

		tv_point = (TextView) findViewById(R.id.tv_point);
		tv_point.setText(BZSPUtil.getUser().getPoints()+"");
	}

	@Override
	protected void initData() {
		swipeRefreshLayout.setRefreshing(true);
	}

	@Override
	protected String getTitleTxt() {
		return "兑换记录";
	}

	@Override
	public void getPointRecordSuccess(List<PointRecord> items) {
		if (isLoadMore) {
			mAdapter.addData(items);
			mPointRecords.addAll(items);
		} else {
			mPointRecords.clear();
			mPointRecords.addAll(items);
			mAdapter.setNewData(items);
		}

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}

	boolean isLoadMore = false;
	private int pageIndex;
	@Override
	public void onRefresh() {
		isLoadMore = false;
		pageIndex = 1;
		mPresenter.getPointRecordList(pageIndex,20);
	}

	@Override
	public void onLoadMore() {
		isLoadMore = true;
		pageIndex++;
		mPresenter.getPointRecordList(pageIndex,20);
	}

	@Override
	public void onItemClick(View view, int position) {

		Bundle bundle = new Bundle();
		bundle.putSerializable("data",mPointRecords.get(position));
		go2ActivityWithLeft(RecordDetailActivity.class,bundle,false);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if(v==ll_record) {
			Bundle bundle = new Bundle();
			bundle.putInt(Config.INTENT_KEY_MODE,MODE_WEB);
			bundle.putString(Config.INTENT_KEY_TITLE,"个人积分");
			bundle.putString(Config.INTENT_KEY_DATA,"http://bz.metshow.com/api/2017/score.html");

			go2ActivityWithLeft(WebActivity.class,bundle , false);
		}
	}
}
