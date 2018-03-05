package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.TopicSubject;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2016-11-3.
 */

public class BannerTopicPresenter extends BzPresenter {

	BZContract.IBannerTopicView iBannerTopicView;

	public BannerTopicPresenter(BZContract.IBannerTopicView iView) {
		super(iView);
		iBannerTopicView = iView;
	}

	public void topicFollowAdd(long topicid) {
		mModel.topicFollowAdd(topicid);
	}

	public void topicFollowDelete(long topicid) {
		mModel.topicFollowDelete(topicid);
	}

	public void getNewTopics(String maxdate, int pagesize, long topicid) {
		mModel.getNewTopics(maxdate, pagesize, topicid);
	}

	public void getHotTopics(int pageindex, int pagesize, long topicid) {
		mModel.getHotTopics(pageindex, pagesize, topicid);
	}

	public void getBannerTopicDetail(long topicid) {
		mModel.getBannerTopicDetail(topicid);
	}

	//收藏 点赞
	public void addAcition(int acttype, long refid, int position) {
		mModel.addAcition(acttype, refid, position);
	}

	public void deleteFav(int type, long refid, int position) {
		mModel.deleteFav(type, refid, position);
	}


	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		if (s.isSuc()) {

			switch (vocational_id) {

				case ServerAPI.TOPIC_LIST4BANNER_VOCATIONAL_ID:
					iBannerTopicView.getNewsTopicSuccess(((ListData<Topic>) s.getResult()).getItems());
					break;

				case ServerAPI.COMMUNITY_LIST4TOPICBYUPCOUNT_VOCATIONAL_ID:
					iBannerTopicView.getHotsTopicSuccess(((ListData<Topic>) s.getResult()).getItems());
					break;

				//取消收藏 . .
				case ServerAPI.ACTION_DELETEFAV_VOCATIONAL_ID:
					iBannerTopicView.deleteFavSuccess((String) s.getResult(), (int) exData.get("type"), (int) exData.get("position"));
					break;

				//点赞  收藏成功
				case ServerAPI.ACTION_ADD_VOCATIONAL_ID:

					if ((int) exData.get("acttype") == 1) {//收藏
						iBannerTopicView.favSuccess((String) s.getResult(), (int) exData.get("position"));
					}
					break;

				//点赞  收藏成功
				case ServerAPI.TOPIC_DETAIL_VOCATIONAL_ID:
					iBannerTopicView.getBannerTopicSuccess((TopicSubject) s.getResult());
					break;

				case ServerAPI.TOPICFOLLOW_ADD_VOCATIONAL_ID:
					iBannerTopicView.addFollowSuccess((String) s.getResult());
					break;
				case ServerAPI.TOPICFOLLOW_DELETE_VOCATIONAL_ID:
					iBannerTopicView.deleteFollowSuccess((String) s.getResult());
					break;
			}

		}
	}

}
