package com.metshow.bz.presenter;

import android.util.Log;

import com.kwan.base.bean.ServerMsg;
import com.kwan.base.rxbus.RxBusSubscriberListener;
import com.metshow.bz.commons.bean.topic.FindTag;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;
import java.util.List;

import io.LruCacheUtil;

/**
 * Created by Mr.Kwan on 2017-6-12.
 */

public class FindPresenter extends BzPresenter implements RxBusSubscriberListener<List> {

	private LruCacheUtil<List<FindTag>> lruCacheUtil;

	BZContract.IFindFragmentView mIViews;
	public FindPresenter(BZContract.IFindFragmentView iView) {
		super(iView);
		mIViews = iView;

		lruCacheUtil = new LruCacheUtil<>();
		initRxBus(List.class, this);
	}

	public void getFindTags() {

		//判断是否过期
		if (lruCacheUtil.isObjectCacheOutOfDate("findtag",LruCacheUtil.MAX_CACHEDATA_7DAY)) {
			//网络请求
			mModel.getFindTags();
		} else {
			//缓存请求 请求完成后会通过RxBus通知回调
			Log.d("FindPresenter", "begin lruCacheUtil.read");
			lruCacheUtil.read("findtag");
		}

	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id){
			case ServerAPI.TAG_FINDLIST_VOCATIONAL_ID:
				lruCacheUtil.write((List<FindTag>) s.getResult(), "findtag");
				mIViews.showFindTags((List<FindTag>) s.getResult());
				break;
		}

	}

	@Override
	public void onRxBusCompleted() {

	}

	@Override
	public void onRxBusError(Throwable e) {
		mModel.getFindTags();
	}

	@Override
	public void onRxBusNext(List list) {
		Log.d("onRxBusNext", "onRxBusNext");
		if (list.get(0) instanceof FindTag) {
			mIViews.showFindTags(list);
		}
	}
}
