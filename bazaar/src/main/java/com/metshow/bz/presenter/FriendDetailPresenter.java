package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.kwan.base.rxbus.RxBusSubscriberListener;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;
import java.util.List;

import io.LruCacheUtil;

/**
 *
 * Created by Mr.Kwan on 2016-8-4.
 */
public class FriendDetailPresenter extends BzPresenter implements RxBusSubscriberListener<List> {

    private BZContract.IFriendDetailView mIFriendDetailView;

	private LruCacheUtil<List<Topic>> lruCacheUtil;
	private String tagname;
	private String token = "";

    public FriendDetailPresenter(BZContract.IFriendDetailView iFriendDetailView) {
        super(iFriendDetailView);
        mIFriendDetailView = iFriendDetailView;
		lruCacheUtil = new LruCacheUtil<>();
		initRxBus(List.class, this);

    }

    public void getUserInfo(String userid) {
        mModel.getUserInfo(userid);
    }

    public void getUserTopics(int pagesize, String maxdate, long userid) {
		mModel.getUserTopics(pagesize, maxdate, userid);
    }

    public void getUserTags(int pageindex ,int pagesize ,long userid){
		mModel.getUserTags(pageindex, pagesize, userid);
    }

    public void deleteFollow(long FollowUserId){
		mModel.deleteFollow(FollowUserId,0);
    }

    public void addFollow(long FollowUserId){
		mModel.addFollow(FollowUserId,0);
    }

    //收藏 点赞
    public void addAcition(int acttype, long refid, int position) {
		mModel.addAcition(acttype, refid, position);
    }

    public void deleteFav(int type, long refid, int position, int fragmentId) {
		mModel.deleteFav(type, refid, position, fragmentId);
    }

	public void report(long refid, int position){
		mModel.report(refid, position);
	}


	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			case ServerAPI.USER_USERINFO_VOCATIONAL_ID:
				mIFriendDetailView.getUserInfoSuccess((User) s.getResult());
				break;
			case ServerAPI.ARTICLE_TOPICBYUSERID_VOCATIONAL_ID:
				mIFriendDetailView.getUserTopicsSuccess(((ListData<Topic>) s.getResult()).getItems());
				break;
			case ServerAPI.TAG_TAGLISTBYUSERID_VOCATIONAL_ID:
				mIFriendDetailView.getUserTagsSuccess(((ListData<Tag>) s.getResult()).getItems());
				break;
			case ServerAPI.FOLLOW_ADD_VOCATIONAL_ID:
				mIFriendDetailView.addFollowSuccess();
				break;
			case ServerAPI.FOLLOW_DELETE_VOCATIONAL_ID:
				mIFriendDetailView.deleteFollowSuccess();
				break;

			//点赞  收藏成功
			case ServerAPI.ACTION_ADD_VOCATIONAL_ID:
				if ((int) exData.get("acttype") == 1) {//收藏
					mIFriendDetailView.favSuccess((String) s.getResult(), (int) exData.get("position"));
				}else if ((int) exData.get("acttype") == 3) {//举报
					mIFriendDetailView.reportSuccess((Boolean) s.getResult(), (int) exData.get("position"));
				}
				break;
			//取消收藏 . .
			case ServerAPI.ACTION_DELETEFAV_VOCATIONAL_ID:
				mIFriendDetailView.deleteFavSuccess((String) s.getResult(), (int) exData.get("type"), (int) exData.get("position"), (int) exData.get("fragmentId"));
				break;
		}
	}

	@Override
	public void onRxBusCompleted() {

	}

	@Override
	public void onRxBusError(Throwable e) {

	}

	@Override
	public void onRxBusNext(List list) {

	}
}
