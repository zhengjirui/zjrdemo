package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.BzActivity;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2017-7-13.
 */

public class MyActivityPresenter extends BzPresenter {

	BZContract.IMyActivityView iMyActivityView;

	public MyActivityPresenter(BZContract.IMyActivityView iView) {
		super(iView);
		iMyActivityView =iView;
	}

	public void getMyActivity(int pageindex,int pagesize){
		mModel.getTopicFollowList(pageindex,pagesize);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id){
			case ServerAPI.TOPICFOLLOW_LIST_VOCATIONAL_ID:
				iMyActivityView.getMyActivitySuccess(((ListData<BzActivity>) s.getResult()).getItems());
				break;
		}

	}
}
