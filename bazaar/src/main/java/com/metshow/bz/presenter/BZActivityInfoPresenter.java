package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.BzActivity;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2017-7-15.
 */

public class BZActivityInfoPresenter extends BzPresenter {

	BZContract.IBZActivityInfoView ibzActivityInfoView;

	public BZActivityInfoPresenter(BZContract.IBZActivityInfoView iView) {
		super(iView);
		ibzActivityInfoView = iView;
	}

	public void getBZActivityInfo(long topicid){
		mModel.getBZActivtyDetail(topicid);
	}

	public void getTopic(String maxdate, int pagesize, long topicid){
		mModel.getNewTopics(maxdate, pagesize, topicid);
	}

	//收藏 点赞
	public void addAcition(int acttype, long refid, int position) {
		mModel.addAcition(acttype, refid, position);
	}

	public void deleteFav(int type, long refid, int position, int fragmentId) {
		mModel.deleteFav(type, refid, position, fragmentId);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id){
			case  ServerAPI.TOPIC_DETAIL_VOCATIONAL_ID:
				ibzActivityInfoView.getBZActivityInfoSuccess((BzActivity) s.getResult());
				break;
			case ServerAPI.TOPIC_LIST4BANNER_VOCATIONAL_ID:
				ibzActivityInfoView.getTopicSuccess(((ListData<Topic>) s.getResult()).getItems());
				break;

		}
	}
}
