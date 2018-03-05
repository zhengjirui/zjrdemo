package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2017-7-17.
 */

public class MyTopicPresenter extends BzPresenter {

	BZContract.IMyTopicView iMyTopicView;

	public MyTopicPresenter(BZContract.IMyTopicView iView) {
		super(iView);
		iMyTopicView =iView;
	}

	public void getMyTopic(int pagesize, String maxdate){
		mModel.getMyTopic(pagesize,maxdate);
	}

	public	void getTag(int pageindex, int pagesize, long userid){
		mModel.getUserTags(pageindex,pagesize,userid);
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
			case ServerAPI.TAG_TAGLISTBYUSERID_VOCATIONAL_ID:
				iMyTopicView.getUserTagsSuccess(((ListData<Tag>) s.getResult()).getItems());
				break;
			//我的帖子
			case ServerAPI.ARTICLE_MYTOPIC_VOCATIONAL_ID:
				iMyTopicView.getTopicsSuccess(((ListData<Topic>) s.getResult()).getItems());
				break;

			//取消收藏 . .
			case ServerAPI.ACTION_DELETEFAV_VOCATIONAL_ID:
				iMyTopicView.deleteFavSuccess((String) s.getResult(), (int) exData.get("type"), (int) exData.get("position"), (int) exData.get("fragmentId"));
				break;

			//点赞  收藏成功
			case ServerAPI.ACTION_ADD_VOCATIONAL_ID:
				if ((int) exData.get("acttype") == 1) {//收藏
					iMyTopicView.favSuccess((String) s.getResult(), (int) exData.get("position"));
				}
				break;
		}

	}
}
