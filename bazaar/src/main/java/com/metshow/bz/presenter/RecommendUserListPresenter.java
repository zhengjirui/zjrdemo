package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.RecommendUser;
import com.metshow.bz.commons.bean.UserType;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-4.
 */

public class RecommendUserListPresenter extends BzPresenter   {
	BZContract.IRecommendUserListView mIView;
	public RecommendUserListPresenter(BZContract.IRecommendUserListView iView) {
		super(iView);
		mIView = iView;
	}

	public  void getUserList(int pageSize, long userTypeId, int pageIndex){
		mModel.getUserTypeUser(pageSize, userTypeId, pageIndex);
	}

//	public void searchUsers(int pageindex,int pagesize,String key) {
//		mModel.searchUsers(pageindex,pagesize,key);
//	}

	public void getUserType(){
		mModel.getUserType();
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			case ServerAPI.USERTYPE_USERLIST_VOCATIONAL_ID:
				mIView.getUserListSuccess(((ListData<RecommendUser>) s.getResult()).getItems());
				break;
			case ServerAPI.USERTYPE_LIST_VOCATIONAL_ID:
				mIView.getUserTypeSuccess((List<UserType>) s.getResult());
				break;
		}


	}
}
