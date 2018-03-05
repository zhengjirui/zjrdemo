package com.metshow.bz.module.buy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.fragment.BaseRecycleFragment;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.SPUtil;
import com.metshow.bz.IUmengEvent;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.adapter.ProductAdapter;
import com.metshow.bz.commons.adapter.SpecialAdapter;
import com.metshow.bz.commons.bean.Brand;
import com.metshow.bz.commons.bean.Category;
import com.metshow.bz.commons.bean.Product;
import com.metshow.bz.commons.bean.Special;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.module.shai.ProductActivity;
import com.metshow.bz.presenter.BuyItemPresenter;

import java.util.ArrayList;
import java.util.List;


public class BuyItemFragment extends BaseRecycleFragment implements BZContract.IBuyItemView, BaseQuickAdapter.OnRecyclerViewItemChildClickListener, BaseQuickAdapter.OnRecyclerViewItemClickListener {

	private int mFragmentId;
	private Category mCategory;
	private boolean isRec;

	private ProductAdapter mProductAdapter;
	private SpecialAdapter mSpecialAdapter;

	private ArrayList<Product> mProducts = new ArrayList<>();
	private ArrayList<Special> mSpecials = new ArrayList<>();

	private BuyItemPresenter mBuyItemPresenter;
	private boolean isLoadMore;

	public BuyItemFragment() {
	}

	public static BuyItemFragment newInstance(int recycleInFragmentId, Category category) {

		BuyItemFragment fragment = new BuyItemFragment();
		Bundle args = new Bundle();
		args.putInt("fragmentId", recycleInFragmentId);
		args.putSerializable("category", category);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		if (getArguments() != null) {
			mFragmentId = getArguments().getInt("fragmentId");
			mCategory = (Category) getArguments().getSerializable("category");
			isRec = mCategory.getCategoryName().equals("推荐");
			mBuyItemPresenter = new BuyItemPresenter(this);
			App.umengEvent(mContext, IUmengEvent.CategoryClick, mCategory.getCategoryName());
			//	initRecycleView();
		}
		super.onCreate(savedInstanceState);
	}


	@Override
	protected BaseQuickAdapter getAdapter() {

		if (isRec) {
			mSpecialAdapter = new SpecialAdapter(mBaseActivity, mSpecials);
			mSpecialAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
				@Override
				public void onItemClick(View view, int position) {
					Bundle bundle = new Bundle();
					bundle.putSerializable("data", mSpecials.get(position));
					go2ActivityWithLeft(SpecialActivity.class,bundle,false);
				}
			});
			return mSpecialAdapter;
		} else {
			mProductAdapter = new ProductAdapter(mBaseActivity, mProducts);
			mProductAdapter.setOnRecyclerViewItemChildClickListener(this);
			mProductAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
			mProductAdapter.setOnRecyclerViewItemClickListener(this);
			return mProductAdapter;
		}
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {
		if (isRec)
			return new LinearLayoutManager(mBaseActivity);
		return new GridLayoutManager(mContext, 2);
	}

	@Override
	public void getSpecialsSuccess(List<Special> result) {

		if (isLoadMore) {
			mSpecialAdapter.addData(result);
			mSpecials.addAll(result);
		} else {
			mSpecials.clear();
			mSpecials.addAll(result);
			mSpecialAdapter.setNewData(result);
		}

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}

	@Override
	public void getProductsSuccess(List<Product> result) {
		if (isLoadMore) {
			mProductAdapter.addData(result);
			mProducts.addAll(result);
		} else {
			mProducts.clear();
			mProducts.addAll(result);
			mProductAdapter.setNewData(result);
		}

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}



	@Override
	public void getBrandListSuccess(List<Brand> items) {

	}

	@Override
	public void onRefresh() {
		super.onRefresh();
		isLoadMore = false;
		if (isRec)
			mBuyItemPresenter.getSpecials(20, "0");
		else
			mBuyItemPresenter.getProducts(mCategory.getCategoryId(), 20, "0", SPUtil.getIsLogin());
	}

	@Override
	public void onLoadMore() {
		super.onLoadMore();
		isLoadMore = true;

		if (isRec) {
			String maxdata = BasePresenter.getData(mSpecials.get(mSpecials.size() - 1).getCreateDate());
			mBuyItemPresenter.getSpecials(20, maxdata);
		} else if (mProducts.size() > 0) {

			String maxdata = BasePresenter.getData(mProducts.get(mProducts.size() - 1).getPublishDate());
			mBuyItemPresenter.getProducts(mCategory.getCategoryId(), 10, maxdata, SPUtil.getIsLogin());
		}
	}


	@Override
	public void onItemClick(View view, int position) {
		Product product = mProducts.get(position);
		Bundle bundle = new Bundle();
		bundle.putString("name", product.getProductName());
		bundle.putLong("id", product.getProductId());
		go2ActivityWithLeft(ProductActivity.class, bundle, false);


	}

	@Override
	public void favSuccess(int result, int position) {

		mProducts.get(position).setIsFav(1);
		mProducts.get(position).setFavCount(result);
		mProductAdapter.getData().get(position).setIsFav(1);
		mProductAdapter.getData().get(position).setFavCount(result);
		mProductAdapter.notifyItemChanged(position);
		mBaseActivity.dismissProgress();
	}

	@Override
	public void deleteFavSuccess(int result, int position) {

		mProducts.get(position).setIsFav(0);
		mProducts.get(position).setFavCount(result);
		mProductAdapter.getData().get(position).setIsFav(0);
		mProductAdapter.getData().get(position).setFavCount(result);
		mProductAdapter.notifyItemChanged(position);
		mBaseActivity.dismissProgress();
	}


	@Override
	public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

		if(SPUtil.getIsLogin()) {

			mBaseActivity.showProgress("");
			Product product = mProducts.get(position);

			if (product.getIsFav() > 0)
				mBuyItemPresenter.deleteFav(product.getProductId(), position);
			else
				mBuyItemPresenter.addAction(1, product.getProductId(), position);
		}else{

			Intent intent = new Intent(mContext, DologinActivity.class);
			startActivityForResult(intent,101);

		}

	}
}
