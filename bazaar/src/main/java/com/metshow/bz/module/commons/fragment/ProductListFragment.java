package com.metshow.bz.module.commons.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.ViewUtil;
import com.kwan.base.widget.SpacesItemDecoration;
import com.metshow.bz.commons.adapter.ProductAdapter;
import com.metshow.bz.commons.bean.Product;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.BaseBZRecycleFragment;
import com.metshow.bz.module.shai.ProductActivity;
import com.metshow.bz.presenter.FavProductPresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mr.Kwan on 2017-2-24.
 */

public class ProductListFragment extends BaseBZRecycleFragment implements BZContract.IFavProductView, BaseQuickAdapter.OnRecyclerViewItemClickListener {

	private List<Product> mProducts = new ArrayList<>();
	private String MaxDate = "0";
	private FavProductPresenter mPresenter;
	private ProductAdapter mProductAdapter;

	public static ProductListFragment newInstance() {

		ProductListFragment fragment = new ProductListFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPresenter = new FavProductPresenter(this);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		int dp = ViewUtil.dip2px(mBaseActivity, 5);
		mRecyclerView.addItemDecoration(new SpacesItemDecoration(dp));
		mRecyclerView.setPadding(dp, dp, dp, dp);
	}

	@Override
	protected BaseQuickAdapter getAdapter() {
		mProductAdapter = new ProductAdapter(mBaseActivity, mProducts);
		mProductAdapter.setOnRecyclerViewItemClickListener(this);
		return mProductAdapter;
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {

		StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		layoutManager.setAutoMeasureEnabled(true);
		return layoutManager;
	}


	@Override
	public void onRefresh() {
		super.onRefresh();
		MaxDate = "0";
		mPresenter.getFavProduct(10, MaxDate);
	}

	@Override
	public void onLoadMore() {
		super.onLoadMore();
		mPresenter.getFavProduct(10, BasePresenter.getData(mProducts.get(mProducts.size() - 1).getPublishDate()));
	}

	@Override
	public void getFavProductSuccess(List<Product> products) {
		if (products.size() > 0) {

			for (Product p :products)
				p.setIsFav(1);


			if (isLoadMore) {
				mProducts.addAll(products);
				mProductAdapter.addData(products);
			} else {
				mProducts.clear();
				mProducts.addAll(products);
				mProductAdapter.setNewData(products);
			}
		}

		swipeRefreshLayout.setLoadingMore(false);
		swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void onItemClick(View view, int position) {
		Product product = mProducts.get(position);
		Bundle bundle = new Bundle();
		bundle.putString("name", product.getProductName());
		bundle.putLong("id", product.getProductId());
		go2ActivityWithLeft(ProductActivity.class, bundle, false);
	}
}
