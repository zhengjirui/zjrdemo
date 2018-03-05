package com.metshow.bz.module.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.util.SPUtil;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.ProductSearchAdapter;
import com.metshow.bz.commons.adapter.TagAdapter;
import com.metshow.bz.commons.bean.Brand;
import com.metshow.bz.commons.bean.Product;
import com.metshow.bz.commons.bean.Special;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.shai.ProductActivity;
import com.metshow.bz.presenter.BuyItemPresenter;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class BuySearchActivity extends BaseSearchActivity implements View.OnFocusChangeListener, BZContract.IBuyItemView, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnRecyclerViewItemClickListener {


	BuyItemPresenter buyItemPresenter;

	private AutoLinearLayout ll_brand;
	private ObservableRecyclerView rl_brand, rl_product;
	private TagAdapter mBrandAdapter;
	private ProductSearchAdapter mProductAdaper;
	private ArrayList<String> mBrandStr = new ArrayList<>();
	private ArrayList<Product> mProducts = new ArrayList<>();
	private ArrayList<Brand> mBrands = new ArrayList<>();
	private String mKey;
	private SwipeToLoadLayout swipeRefreshLayout;
	private boolean isLoadMore;
	private boolean isLogin;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		buyItemPresenter = new BuyItemPresenter(this);
		isLogin = SPUtil.getIsLogin();
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_buy_search;
	}

	@Override
	protected void initViews() {
		super.initViews();
		swipeRefreshLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
		ll_brand = (AutoLinearLayout) findViewById(R.id.ll_brand);
		rl_brand = (ObservableRecyclerView) findViewById(R.id.rl_brand);
		rl_product = (ObservableRecyclerView) findViewById(R.id.rl_product);

		initRecycler(rl_brand);
		initRecycler(rl_product);

		mBrandAdapter = new TagAdapter(this, mBrandStr);
		mBrandAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
			@Override
			public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
				onSearch(mBrandStr.get(position));
			}
		});

		mProductAdaper = new ProductSearchAdapter(this, mProducts);
		mProductAdaper.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				Product product = mProducts.get(position);
				Bundle bundle = new Bundle();
				bundle.putString("name", product.getProductName());
				bundle.putLong("id", product.getProductId());

				go2ActivityWithLeft(ProductActivity.class, bundle, false);
			}
		});

		et_search.setHint("搜索商品、品牌");
		et_search.setOnFocusChangeListener(this);

		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setOnLoadMoreListener(this);

		rl_brand.setAdapter(mBrandAdapter);
		rl_product.setAdapter(mProductAdaper);

//		swipeRefreshLayout.setRefreshEnabled(false);
//		swipeRefreshLayout.setLoadMoreEnabled(false);

	}

	@Override
	protected void initData() {
		super.initData();

//		showProgress("");
//		buyItemPresenter.getBrand();
	}

	private void initRecycler(RecyclerView recyclerView) {
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
	public void onSearch(String txt) {
		txt = txt.trim();
		showProgress("");
		mKey = txt;
		buyItemPresenter.getProducts(txt, 20, "0", isLogin);
	}


	@Override
	public void onFocusChange(View v, boolean hasFocus) {

		if (hasFocus) {

			ll_brand.setVisibility(View.VISIBLE);
			rl_product.setVisibility(View.GONE);

			if (mBrandStr.isEmpty()) {
				showProgress("");
				buyItemPresenter.getBrand();
			}
		} else {
			ll_brand.setVisibility(View.GONE);
			rl_product.setVisibility(View.VISIBLE);

			mInputMethodManager.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
		}

	}

	@Override
	public void getSpecialsSuccess(List<Special> result) {

	}

	@Override
	public void getProductsSuccess(List<Product> result) {

		if (isLoadMore) {
			mProductAdaper.addData(result);
			mProducts.addAll(result);
		} else {
			mProducts.clear();
			mProducts.addAll(result);
			mProductAdaper.setNewData(result);
		}

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);

		ll_search.requestFocus();

		dismissProgress();

	}

	@Override
	public void favSuccess(int result, int position) {

	}

	@Override
	public void deleteFavSuccess(int result, int position) {

	}

	@Override
	public void getBrandListSuccess(List<Brand> items) {

		//Log.d("kwan", "item::" + items.size());
		int i = 0;
		for (Brand brand : items) {
			if (i == 20)
				break;
			mBrandStr.add(brand.getBrandName());
			i++;
		}

		mBrandAdapter.setNewData(mBrandStr);
		dismissProgress();

//		ll_brand.setVisibility(View.VISIBLE);
//		rl_product.setVisibility(View.GONE);
	}

	@Override
	public void onRefresh() {
		isLoadMore = false;
		buyItemPresenter.getProducts(mKey, 20, "0", isLogin);
	}

	@Override
	public void onLoadMore() {
		isLoadMore = true;
	}

	@Override
	public void onItemClick(View view, int position) {

	}
}
