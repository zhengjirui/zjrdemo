package com.kwan.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kwan.base.R;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.kwan.base.widget.observablescrollview.ObservableScrollViewCallbacks;


/**
 * Created by Mr.Kwan on 2017-2-23.
 */

public abstract class BaseRecycleFragment extends BasePageItemFragment implements OnRefreshListener, OnLoadMoreListener {

	protected ObservableRecyclerView mRecyclerView;
	protected ObservableScrollViewCallbacks observableScrollViewCallbacks;
	protected BaseQuickAdapter mBaseAdapter;
	protected SwipeToLoadLayout swipeRefreshLayout;
	protected int pageIndex = 0;
	protected boolean isLoadMore = false;

	@Override
	public void fetchData() {
		Log.d("BaseRecycleFragment", "fetchData::begin autoRefresh");
		swipeRefreshLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
			}
		}, 1);
	}

	@Override
	protected int getContentViewId() {
		return R.layout.layout_base_recycle;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRecyclerView = (ObservableRecyclerView) mContentView.findViewById(R.id.swipe_target);
		swipeRefreshLayout = (SwipeToLoadLayout) mContentView.findViewById(R.id.swipeToLoadLayout);

		mRecyclerView.setLayoutManager(getLayoutManager());
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setScrollViewCallbacks(observableScrollViewCallbacks);
		mRecyclerView.setHasFixedSize(true);

		//麻痹的 关闭硬件加速 否则 cardView 报错

		mRecyclerView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		mRecyclerView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		mBaseAdapter = getAdapter();
		mRecyclerView.setAdapter(mBaseAdapter);

		swipeRefreshLayout.setRefreshEnabled(true);
		swipeRefreshLayout.setLoadMoreEnabled(true);

		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setOnLoadMoreListener(this);


	}

	@Override
	public void onRefresh() {
		isLoadMore = false;
		pageIndex = 1;
	}

	@Override
	public void onLoadMore() {
		isLoadMore = true;
		pageIndex++;
	}

	protected abstract BaseQuickAdapter getAdapter();

	protected abstract RecyclerView.LayoutManager getLayoutManager();

	@Override
	public void onServerFailed(String s) {
		super.onServerFailed(s);
		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}

	@Override
	public void onServerError(int vocational_id, Throwable e) {
		super.onServerError(vocational_id, e);
		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}
}
