package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.FollowItem;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2016-8-8.
 */
public class FollowPresenter extends BzPresenter {

	private BZContract.IFollowView mIView;

	public FollowPresenter(BZContract.IFollowView iFollowView) {
		super(iFollowView);
		mIView = iFollowView;
	}

	public void addFollow(long FollowUserId, int position) {
		mModel.addFollow(FollowUserId, position);
	}

	public void getBeFollowList(int pageindex, int pagesize) {
		mModel.getBeFollowList(pageindex, pagesize);
	}

	public void deleteFollow(long followUserId, int position) {
		mModel.deleteFollow(followUserId, position);
	}

	public void getFollowList(int pageindex, int pagesize) {
		mModel.getFollowList(pageindex, pagesize);
	}

	@Override
	public void onServerFailed(String msg) {
		super.onServerFailed(msg);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);
		switch (vocational_id) {

			case ServerAPI.FOLLOW_LIST_VOCATIONAL_ID:
				mIView.getFollowListSuccess(((ListData<FollowItem>) s.getResult()).getItems());
				break;
			case ServerAPI.FOLLOW_DELETE_VOCATIONAL_ID:
				mIView.getDeleteFollowSuccess(Boolean.getBoolean((String) s.getResult()), (Integer) exData.get("position"));
				break;
			case ServerAPI.FOLLOW_FOLLOWEDLIST_VOCATIONAL_ID:
				mIView.getBeFollowListSuccess(((ListData<FollowItem>) s.getResult()).getItems(), s.getServertime());
				break;
			case ServerAPI.FOLLOW_ADD_VOCATIONAL_ID:
				mIView.addFollowSuccess((Integer) exData.get("position"));
				break;
		}


	}

}
