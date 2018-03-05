package com.metshow.bz.module.point.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kwan.base.activity.BaseCommonActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.PointProductListAdapter;
import com.metshow.bz.commons.bean.PointProduct;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.PointProductListPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class PointProductListActivity extends BaseCommonActivity implements BZContract.IPointProductListView, BaseQuickAdapter.OnRecyclerViewItemClickListener, OnRefreshListener, OnLoadMoreListener {

	private PointProductListPresenter mPresenter;
	private AutoLinearLayout ll_point,ll_record;
	private TextView tv_point;
	private RecyclerView mRecyclerView;

	PointProductListAdapter mAdapter;
	private SwipeToLoadLayout swipeRefreshLayout;

	private ArrayList<PointProduct> mPointProduct = new ArrayList<>();
	private boolean isLoadMore;
	private int pageIndex;
	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mPresenter = new PointProductListPresenter(this);
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_point_product_list;
	}

	@Override
	protected void initViews() {
		super.initViews();

		ll_point = (AutoLinearLayout) findViewById(R.id.ll_point);
		ll_record= (AutoLinearLayout) findViewById(R.id.ll_record);
		tv_point= (TextView) findViewById(R.id.tv_point);

		mRecyclerView = (ObservableRecyclerView) findViewById(com.kwan.base.R.id.swipe_target);
		swipeRefreshLayout = (SwipeToLoadLayout) findViewById(com.kwan.base.R.id.swipeToLoadLayout);

		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//mRecyclerView.setScrollViewCallbacks(observableScrollViewCallbacks);
		mRecyclerView.setHasFixedSize(true);
		mAdapter = new PointProductListAdapter(this,mPointProduct);
		mAdapter.setOnRecyclerViewItemClickListener(this);
		mRecyclerView.setAdapter(mAdapter);
		//麻痹的 关闭硬件加速 否则 cardView 报错

		mRecyclerView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		mRecyclerView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

		swipeRefreshLayout.setRefreshEnabled(true);
		swipeRefreshLayout.setLoadMoreEnabled(true);

		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setOnLoadMoreListener(this);

		ll_point.setOnClickListener(this);
		ll_record.setOnClickListener(this);

		tv_point.setText(BZSPUtil.getUser().getPoints()+"");
	}

	@Override
	protected void initData() {
		mPresenter.getPointProductList("0",20);
	}

	@Override
	protected String getTitleTxt() {
		return "积分换礼";
	}

	@Override
	public void getPointProductListSuccess(List<PointProduct> items) {

		if (isLoadMore) {
			mAdapter.addData(items);
			mPointProduct.addAll(items);
		} else {
			mPointProduct.clear();
			mPointProduct.addAll(items);
			mAdapter.setNewData(items);
		}

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if(v==ll_point){

			go2ActivityWithLeft(PointDetailActivity.class,null,false);

		}else if(v==ll_record){
			go2ActivityWithLeft(PointRecordActivity.class,null,false);
		}

	}

	@Override
	public void onItemClick(View view, int position) {


		Bundle bundle = new Bundle();
		bundle.putSerializable("data",mPointProduct.get(position));
		go2ActivityWithLeft(PointProductActivity.class,bundle,false);
	}

	@Override
	public void onRefresh() {
		isLoadMore = false;
		pageIndex = 1;
		mPresenter.getPointProductList("0",20);
	}

	@Override
	public void onLoadMore() {
		isLoadMore = true;
		pageIndex++;
		mPresenter.getPointProductList(BasePresenter.getData(mPointProduct.get(mPointProduct.size()-1).getCreateDate()),20);
	}
}
