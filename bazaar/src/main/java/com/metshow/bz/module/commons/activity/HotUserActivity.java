package com.metshow.bz.module.commons.activity;

import com.kwan.base.activity.CommonPageActivity;
import com.kwan.base.adatper.CommonFragmentPageAdapter;
import com.metshow.bz.commons.bean.RecommendUser;
import com.metshow.bz.commons.bean.UserType;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.fragment.HotUserItemFragment;
import com.metshow.bz.presenter.RecommendUserListPresenter;

import java.util.List;


public class HotUserActivity extends CommonPageActivity implements BZContract.IRecommendUserListView {

	private RecommendUserListPresenter mPresenter;
	private CommonFragmentPageAdapter mPageAdapter;

	@Override
	protected String getTitleTxt() {
		return "热门用户";
	}

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mPresenter = new RecommendUserListPresenter(this);
	}

	@Override
	protected void initViews() {
		super.initViews();
		mPageAdapter = new CommonFragmentPageAdapter(getSupportFragmentManager());
	}

	@Override
	protected void initData() {
		super.initData();
		showProgress("");
		mPresenter.getUserType();
	}

    @Override
    public void getUserListSuccess(List<RecommendUser> items) {}

	@Override
	public void getUserTypeSuccess(List<UserType> items) {
		for (UserType item : items) {

			HotUserItemFragment fragment = HotUserItemFragment.newInstance(item.getUserTypeId());
			//fragment.setObservableScrollViewCallbacks(this);
			mPageAdapter.addFragment(fragment, item.getName());

		}
		setUpViewPage(mPageAdapter);
		dismissProgress();
	}
}
