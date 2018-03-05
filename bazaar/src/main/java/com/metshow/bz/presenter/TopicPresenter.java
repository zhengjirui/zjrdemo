package com.metshow.bz.presenter;

import android.util.Log;

import com.kwan.base.bean.ServerMsg;
import com.kwan.base.rxbus.RxBusSubscriberListener;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;
import com.metshow.bz.util.BZSPUtil;

import java.util.HashMap;

import io.LruCacheUtil;

/**
 * Created by Mr.Kwan on 2017-7-12.
 */

public class TopicPresenter extends BzPresenter implements RxBusSubscriberListener<Topic> {

	BZContract.ITopicView iTopicView;
	private LruCacheUtil<Topic> lruCacheUtil;
	private long id;
	private String token="";
	private boolean isLogin;

	public TopicPresenter(BZContract.ITopicView iView) {
		super(iView);
		iTopicView = iView;
		lruCacheUtil = new LruCacheUtil<>();
		initRxBus(Topic.class, this);
	}

	public void deteleFav(long refid,int position){
		mModel.deleteFav(refid, position);
	}

	public void addFav(long refid,int position){
		mModel.addFav(refid, position);
	}

	public void getTopicDetail(long articleid,boolean isLogin) {

		this.id = articleid;
		this.isLogin =isLogin;
		token = BZSPUtil.getUser().getToken();

		//判断是否过期
		if (lruCacheUtil.isObjectCacheOutOfDate("topic" + this.id + "" + token, LruCacheUtil.MAX_CACHEDATA_1HOUR)) {
			//网络请求
			mModel.getTopicDetail(id,isLogin);
		} else {
			//缓存请求 请求完成后会通过RxBus通知回调
			Log.d("TopicPresenter", "begin lruCacheUtil.read");
			lruCacheUtil.read("topic" + this.id + "" + token);
		}

	}

	public void addFollow(long FollowUserId,int position){
		mModel.addFollow(FollowUserId, position);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			case ServerAPI.COMMUNITY_DETAIL_VOCATIONAL_ID:
				lruCacheUtil.write((Topic) s.getResult(), "topic" + this.id + "" + token);
				iTopicView.getTopicDetailSuccess((Topic) s.getResult());
				break;

			//关注
			case ServerAPI.FOLLOW_ADD_VOCATIONAL_ID:
				iTopicView.addFollowSuccess((String) s.getResult(), (int) exData.get("position"));
				break;

			//点赞  收藏成功
			case ServerAPI.ACTION_ADD_VOCATIONAL_ID:
				if ((int) exData.get("acttype") == 1) {//收藏
					iTopicView.favSuccess((String) s.getResult(), (int) exData.get("position"));
				}
				break;
			//取消收藏
			case ServerAPI.ACTION_DELETEFAV_VOCATIONAL_ID:
				iTopicView.deleteFavSuccess((String) s.getResult(), (int) exData.get("position"));
				break;
		}
	}

	@Override
	public void onRxBusCompleted() {

	}

	@Override
	public void onRxBusError(Throwable e) {
		mModel.getTopicDetail(id,isLogin);
	}

	@Override
	public void onRxBusNext(Topic topic) {
		Log.d("Article presenter", "onRxBusNext");
		iTopicView.getTopicDetailSuccess(topic);
	}
}
