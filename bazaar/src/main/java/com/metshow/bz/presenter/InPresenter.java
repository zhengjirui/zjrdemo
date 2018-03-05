package com.metshow.bz.presenter;

import android.util.Log;

import com.kwan.base.bean.ServerMsg;
import com.kwan.base.rxbus.RxBusSubscriberListener;
import com.metshow.bz.commons.bean.Channel;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;
import java.util.List;

import io.LruCacheUtil;

/**
 * Created by Mr.Kwan on 2017-6-12.
 */

public class InPresenter extends BzPresenter implements RxBusSubscriberListener<List> {

	BZContract.IInFragmentView mIViews;
	private LruCacheUtil<List<Channel>> lruCacheUtil;


	public InPresenter(BZContract.IInFragmentView iView) {
		super(iView);
		mIViews = iView;
		lruCacheUtil = new LruCacheUtil<>();
		initRxBus(List.class, this);
	}


	public void getChannelList() {
		//判断是否过期
		if (lruCacheUtil.isObjectCacheOutOfDate("channels",LruCacheUtil.MAX_CACHEDATA_7DAY)) {
			//网络请求
			mModel.getInChannelList();
		} else {
			//缓存请求 请求完成后会通过RxBus通知回调
			Log.d("InFragmentPres", "begin lruCacheUtil.read");
			lruCacheUtil.read("channels");
		}
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id){
			case ServerAPI.CHANNEL_LIST_VOCATIONAL_ID:
				lruCacheUtil.write((List<Channel>) s.getResult(), "channels");
				mIViews.showChannelList((List<Channel>) s.getResult());
				break;
		}

	}

	@Override
	public void onRxBusCompleted() {

	}

	@Override
	public void onRxBusError(Throwable e) {
		Log.d("onRxBusError", "onRxBusError::"+e.getMessage());
		mModel.getInChannelList();
	}

	@Override
	public void onRxBusNext(List list) {
		Log.d("onRxBusNext", "onRxBusNext");
		if (list.get(0) instanceof Channel) {
			mIViews.showChannelList(list);
		}
	}
}
