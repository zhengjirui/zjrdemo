package com.metshow.bz.module.buy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.kwan.base.adatper.CommonFragmentPageAdapter;
import com.kwan.base.fragment.BasePageFragment;
import com.metshow.bz.IUmengEvent;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.Category;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.BuyFragmentPresenter;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyFragment extends BasePageFragment implements BZContract.IBuyFragmentView {

	private BuyFragmentPresenter mBuyFragmentPresenter;
	private CommonFragmentPageAdapter mPageAdapter;
	private HashMap<Integer, BuyItemFragment> mBuyItemFragments;

	public BuyFragment() {
	}

	// TODO: Rename and change types and number of parameters
	public static BuyFragment newInstance() {
		BuyFragment fragment = new BuyFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBuyFragmentPresenter = new BuyFragmentPresenter(this);
		App.umengEvent(mContext, IUmengEvent.ModuleBrowse,"买");

	}

	@Override
	public void fetchData() {
		mBuyFragmentPresenter.getCategoryList();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Logger.d("onViewCreated");
		if (mPageAdapter != null) {
			setUpViewPage(mPageAdapter,12);
		}

	}

	@Override
	public void showCategoryList(List<Category> categories) {
		mPageAdapter = new CommonFragmentPageAdapter(getChildFragmentManager());
		mBuyItemFragments = new HashMap<>();
		Category rec = new Category();
		rec.setCategoryName("推荐");
		categories.add(0,rec);
		int id = 0;
		for (Category category : categories) {

			BuyItemFragment fragment = BuyItemFragment.newInstance(id, category);
			//fragment.setObservableScrollViewCallbacks(this);
			// fragment.setRecycleRefrshListener(this);
			mPageAdapter.addFragment(fragment, category.getCategoryName());
			mBuyItemFragments.put(id, fragment);
			id++;
		}

		setUpViewPage(mPageAdapter,12);
	}
}
