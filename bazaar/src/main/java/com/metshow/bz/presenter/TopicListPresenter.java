package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2016-8-9.
 */
public class TopicListPresenter extends BzPresenter {


    private BZContract.ITopicListView mITopicListView;

    public TopicListPresenter(BZContract.ITopicListView iTopicListView) {
        super(iTopicListView);
        mITopicListView = iTopicListView;

    }

    public void addFollow(long FollowUserId,int position){
        mModel.addFollow(FollowUserId, position);
    }

    public void addAcition(int acttype,long refid,int position){
		mModel.addAcition(acttype,refid,position);
    }

    public void deleteFav(long refid,int position){
		mModel.deleteFav(refid,position);
    }

    public void getUserTopics(int pagesize, String maxdate, long userid) {
		mModel.getUserTopics(pagesize, maxdate, userid);
    }

    public void getTagTopics(int pagesize, int pageindex, String tagname) {
		mModel.getTagTopics(pagesize, pageindex, tagname);
    }

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {

			case ServerAPI.ARTICLE_TOPICBYUSERID_VOCATIONAL_ID:
				mITopicListView.getUserTopicsSuccess(((ListData<Topic>) s.getResult()).getItems());
				break;

			case ServerAPI.COMMUNITY_LISTBYTAG_VOCATIONAL_ID:
				mITopicListView.getUserTopicsSuccess(((ListData<Topic>) s.getResult()).getItems());
				break;

			//点赞  收藏成功
			case ServerAPI.ACTION_ADD_VOCATIONAL_ID:
				if((int)exData.get("acttype") == 1){//收藏
					mITopicListView.favSuccess((String)s.getResult(),(int)exData.get("position"));
				}
				break;
			//取消收藏
			case ServerAPI.ACTION_DELETEFAV_VOCATIONAL_ID:
				mITopicListView.deleteFavSuccess((String)s.getResult(),(int)exData.get("position"));
				break;

			case ServerAPI.FOLLOW_ADD_VOCATIONAL_ID:
				mITopicListView.addFollowSuccess((String)s.getResult(),(int)exData.get("position"));
				break;

		}
	}
}
