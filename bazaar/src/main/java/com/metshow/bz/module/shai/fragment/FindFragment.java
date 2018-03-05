package com.metshow.bz.module.shai.fragment;

import android.os.Bundle;

import com.kwan.base.adatper.CommonFragmentPageAdapter;
import com.kwan.base.fragment.BasePageFragment;
import com.metshow.bz.commons.bean.topic.FindTag;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.FindPresenter;

import java.util.HashMap;
import java.util.List;


public class FindFragment extends BasePageFragment implements BZContract.IFindFragmentView {

	private FindPresenter mFindPresenter;
	private CommonFragmentPageAdapter mPageAdapter;
	private HashMap<Integer, FindItemFragment> mFindItemFragments = new HashMap<>();

	public FindFragment() {
	}

	public static FindFragment newInstance() {
		FindFragment fragment = new FindFragment();
		return fragment;
	}

	@Override
	protected String getItemTag() {
		return "kwan find";
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFindPresenter = new FindPresenter(this);
		mPageAdapter = new CommonFragmentPageAdapter(getChildFragmentManager());

		fetchData();

	}

	@Override
	public void fetchData() {

		showProgress("");
		mFindPresenter.getFindTags();
	}

	@Override
	public void showFindTags(List<FindTag> result) {
		int id = 0;

		for (FindTag tag : result) {

			FindItemFragment fragment = FindItemFragment.newInstance(id, tag);
			//fragment.setObservableScrollViewCallbacks(this);
			mPageAdapter.addFragment(fragment, tag.getName());
			mFindItemFragments.put(id, fragment);
			id++;
		}
		setUpViewPage(mPageAdapter, 12);
	}
}
