package com.metshow.bz.module.in.fragment;

import android.content.Context;
import android.os.Bundle;

import com.kwan.base.adatper.CommonFragmentPageAdapter;
import com.kwan.base.fragment.BasePageFragment;
import com.metshow.bz.commons.bean.Channel;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.InPresenter;

import java.util.List;


public class InFragment extends BasePageFragment implements BZContract.IInFragmentView{

	private InPresenter mInPresenter;
	private CommonFragmentPageAdapter mPageAdapter;
	public InFragment() {}

	public static InFragment newInstance() {
		InFragment fragment = new InFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mInPresenter = new InPresenter(this);
		mPageAdapter = new CommonFragmentPageAdapter(getChildFragmentManager());

	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void showProgress(String txt) {

	}

	@Override
	public void dismissProgress() {

	}

	@Override
	public void onServerError(int vocational_id, Throwable e) {

	}

	@Override
	public void onServerFailed(String s) {

	}

	@Override
	public void fetchData() {
		mInPresenter.getChannelList();
	}



	@Override
	public void showChannelList(List<Channel> channels) {

		for (Channel channel :channels){
			mPageAdapter.addFragment(InItemFragment.newInstance(channel), channel.getChannelName());
		}
		setUpViewPage(mPageAdapter,12);
	}
}
