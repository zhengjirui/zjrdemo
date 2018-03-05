package com.kwan.base.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kwan.base.R;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.zhy.autolayout.AutoLinearLayout;

import static com.kwan.base.R.id.swipeToLoadLayout;


/**
 * Created by Mr.Kwan on 2017-2-24.
 */

public abstract class CommonRecycleActivity extends BaseCommonActivity implements OnRefreshListener, OnLoadMoreListener {

	protected ObservableRecyclerView mRecyclerView;
	protected SwipeToLoadLayout swipeRefreshLayout;
	protected BaseQuickAdapter mAdapter;
	protected int pageIndex;
	protected AutoLinearLayout ll_no_item;


	@Override
	protected int getContentViewId() {
		return R.layout.layout_base_recycle;
	}

	@Override
	protected void initViews() {
		super.initViews();
		ll_no_item = (AutoLinearLayout) findViewById(R.id.ll_no_item);
		mRecyclerView = (ObservableRecyclerView) findViewById(R.id.swipe_target);
		mRecyclerView.setLayoutManager(getLayoutManager());
		mRecyclerView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		mRecyclerView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		mRecyclerView.setHasFixedSize(true);
		//mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//解决更新 闪烁
		mRecyclerView.setItemAnimator(null);
		mRecyclerView.setNestedScrollingEnabled(false);

//		int dp = ViewUtil.dip2px(this, 5);
//		mRecyclerView.addItemDecoration(new SpacesItemDecoration(dp));
//		mRecyclerView.setPadding(dp, dp, dp, dp);
// recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, ScreenUtils.dip2px(this, 2), false));
		mAdapter = getAdapter();
		mRecyclerView.setAdapter(mAdapter);

		swipeRefreshLayout = (SwipeToLoadLayout) findViewById(swipeToLoadLayout);

		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setOnLoadMoreListener(this);

	}

	@Override
	protected String getTitleTxt() {
		return null;
	}

	@Override
	protected void initData() {
		swipeRefreshLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
			}
		}, 1);
	}

	protected abstract BaseQuickAdapter getAdapter();

	protected abstract RecyclerView.LayoutManager getLayoutManager();


	@Override
	public void onRefresh() {
		pageIndex = 1;
	}

	@Override
	public void onLoadMore() {
		pageIndex++;
	}

	@Override
	public void onServerError(int vocational_id, Throwable e) {
		super.onServerError(vocational_id, e);
		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}

	@Override
	public void onServerFailed(String s) {

		super.onServerFailed(s);
		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}

	public void setNoItem(boolean isEmpty){

		if(isEmpty){
			ll_no_item.setVisibility(View.VISIBLE);
			swipeRefreshLayout.setVisibility(View.GONE);
		}else{
			ll_no_item.setVisibility(View.GONE);
			swipeRefreshLayout.setVisibility(View.VISIBLE);
		}

	}
}
