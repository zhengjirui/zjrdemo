package com.metshow.bz.presenter;

import android.util.Log;

import com.kwan.base.bean.ServerMsg;
import com.kwan.base.rxbus.RxBusSubscriberListener;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.RecommendUser;
import com.metshow.bz.commons.bean.article.Banner;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;
import com.metshow.bz.util.BZSPUtil;

import java.util.HashMap;
import java.util.List;

import io.LruCacheUtil;

/**
 * Created by Mr.Kwan on 2017-6-12.
 */

public class FindItemPresenter extends BzPresenter implements RxBusSubscriberListener<List> {

	BZContract.IFindItemFragmentView mIViews;
	private LruCacheUtil<List<Topic>> lruCacheUtil;
	private String tagname;
	private String token = "";

	public FindItemPresenter(BZContract.IFindItemFragmentView iView) {
		super(iView);
		mIViews = iView;
		lruCacheUtil = new LruCacheUtil<>();
		initRxBus(List.class, this);
	}

	public void getTopics(int pageindex, int pagesize, String tagname) {

		this.tagname = tagname;
		token = BZSPUtil.getUser().getToken();
		Log.e("getTopics1", "1tagname::" + tagname);
		if (pageindex == 1) {

			Log.e("getTopics", "tagname::" + tagname);

			mModel.getFindTopics(pageindex, pagesize, tagname);
		} else {

			//判断是否过期
			//if (lruCacheUtil.isObjectCacheOutOfDate("topic_find_" + this.tagname + "_" + token, LruCacheUtil.MAX_CACHEDATA_5MIN)) {
				//网络请求
				mModel.getFindTopics(pageindex, pagesize, tagname);
			//} else {
				//缓存请求 请求完成后会通过RxBus通知回调
				Log.d("FindItemPresenter", "begin lruCacheUtil.read");
				//lruCacheUtil.read("topic_find_" + this.tagname + "_" + token);
			//}

		}
	}

	//收藏 点赞
	public void addAcition(int acttype, long refid, int position) {
		mModel.addAcition(acttype, refid, position);
	}

	public void deleteFav(int type, long refid, int position, int fragmentId) {
		mModel.deleteFav(type, refid, position, fragmentId);
	}

	public void getBanner() {
		mModel.getFindBanner();
	}

	public void getRecommend() {
		mModel.getFindRecommend();
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			case ServerAPI.COMMUNITY_LISTBYTAG_VOCATIONAL_ID:
				mIViews.getTopicsSuccess(((ListData<Topic>) s.getResult()).getItems());
				//lruCacheUtil.write(((ListData<Topic>) s.getResult()).getItems(), "topic_find_" + this.tagname + "_" + token);
				break;


			//取消收藏 . .
			case ServerAPI.ACTION_DELETEFAV_VOCATIONAL_ID:
				mIViews.deleteFavSuccess((String) s.getResult(), (int) exData.get("type"), (int) exData.get("position"), (int) exData.get("fragmentId"));
				break;

			//点赞  收藏成功
			case ServerAPI.ACTION_ADD_VOCATIONAL_ID:
				if ((int) exData.get("acttype") == 1) {//收藏
					mIViews.favSuccess((String) s.getResult(), (int) exData.get("position"));
				}
				break;

			case ServerAPI.RECOMMEND_LIST_VOCATIONAL_ID:
				mIViews.getBannerSuccess((List<Banner>) s.getResult());
				break;

			case ServerAPI.USER_ADMINLIST_VOCATIONAL_ID:
				mIViews.getRecommendSuccess((List<RecommendUser>) s.getResult());
				break;
		}

	}


	@Override
	public void onRxBusCompleted() {

	}

	@Override
	public void onRxBusError(Throwable e) {
		mModel.getFindTopics(1, 20, tagname);
	}

	@Override
	public void onRxBusNext(List list) {
		if (list.get(0) instanceof Topic) {
			mIViews.getTopicsSuccess(list);
		}
	}

}
