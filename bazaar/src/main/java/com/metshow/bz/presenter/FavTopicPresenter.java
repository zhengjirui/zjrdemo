package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2017-7-11.
 */

public class FavTopicPresenter extends BzPresenter {

	BZContract.IFavTopicView iFavTopicView;

	public FavTopicPresenter(BZContract.IFavTopicView iView) {
		super(iView);
		iFavTopicView = iView;
	}

	public void getFavTopic(int pagesize, String createdate) {
		mModel.getFavTopic(pagesize, createdate);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);
		switch (vocational_id){
			case ServerAPI.ACTION_MYCOMMUNITYFAV_VOCATIONAL_ID:
				iFavTopicView.getFavTopicSuccess(((ListData<Topic>) s.getResult()).getItems());
				break;
		}
	}
}
