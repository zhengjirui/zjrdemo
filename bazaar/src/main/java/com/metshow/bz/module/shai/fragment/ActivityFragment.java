package com.metshow.bz.module.shai.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.fragment.BaseRecycleFragment;
import com.metshow.bz.commons.adapter.ShaiActivityAdapter;
import com.metshow.bz.commons.bean.BzActivity;
import com.metshow.bz.commons.bean.TriProduct;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.shai.BZActivityInfoActivity;
import com.metshow.bz.presenter.ShaiActivityPresenter;

import java.util.ArrayList;
import java.util.List;


public class ActivityFragment extends BaseRecycleFragment implements BZContract.IShaiActivityView, BaseQuickAdapter.OnRecyclerViewItemClickListener, BaseQuickAdapter.OnRecyclerViewItemChildClickListener {

	private ShaiActivityPresenter mActivityPresenter;
	private List<BzActivity> mBzActivities = new ArrayList<>();
	private ShaiActivityAdapter adapter;

	public static ActivityFragment newInstance() {
        ActivityFragment fragment = new ActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ActivityFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
			mActivityPresenter = new ShaiActivityPresenter(this);
			fetchData();
        }
    }

	@Override
	protected BaseQuickAdapter getAdapter() {
		adapter = new ShaiActivityAdapter(mBaseActivity,mBzActivities);
	//	adapter.setOnRecyclerViewItemClickListener(this);
		adapter.setOnRecyclerViewItemChildClickListener(this);
		return adapter;
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {
		return new LinearLayoutManager(mBaseActivity);
	}

	@Override
	public void getActivitysSuccess(List<BzActivity> items) {

		mBzActivities.addAll(items);
		items.add(0,mBzActivities.get(0));
		mBaseAdapter.setNewData(items);
		swipeRefreshLayout.setRefreshing(false);

	}

	@Override
	public void getTriProductSuccess(List<TriProduct> items) {

		BzActivity bzActivity = new BzActivity();
		bzActivity.setItemType(1);
		bzActivity.setTriProducts(items);
		mBzActivities.add(bzActivity);
		mActivityPresenter.getActivitys(pageIndex,20);
	}

	@Override
	public void onRefresh() {
		super.onRefresh();
		mActivityPresenter.getTriProducts();
	}

	@Override
	public void onItemClick(View view, int position) {

		Bundle bundle = new Bundle();
		bundle.putSerializable("data",mBzActivities.get(position));
		go2ActivityWithLeft(BZActivityInfoActivity.class,bundle,false);
	}

	@Override
	public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("data",mBzActivities.get(position));
		go2ActivityWithLeft(BZActivityInfoActivity.class,bundle,false);
	}
}
