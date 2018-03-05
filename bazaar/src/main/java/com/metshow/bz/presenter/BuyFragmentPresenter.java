package com.metshow.bz.presenter;

import android.util.Log;

import com.kwan.base.bean.ServerMsg;
import com.kwan.base.rxbus.RxBusSubscriberListener;
import com.metshow.bz.commons.bean.Category;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;
import java.util.List;

import io.LruCacheUtil;


/**
 * Created by Mr.Kwan on 2016-8-21.
 */

public class BuyFragmentPresenter extends BzPresenter implements RxBusSubscriberListener<List> {

	private BZContract.IBuyFragmentView mIBuyFragmentView;
	private LruCacheUtil<List<Category>> lruCacheUtil;

	public BuyFragmentPresenter(BZContract.IBuyFragmentView iBuyFragmentView) {
		super(iBuyFragmentView);
		mIBuyFragmentView = iBuyFragmentView;
		lruCacheUtil = new LruCacheUtil<>();
		initRxBus(List.class, this);
	}

	public void getCategoryList() {

		//判断是否过期
		if (lruCacheUtil.isObjectCacheOutOfDate("category", LruCacheUtil.MAX_CACHEDATA_7DAY)) {
			//网络请求
			mModel.getCategoryList();
		} else {
			//缓存请求 请求完成后会通过RxBus通知回调
			Log.d("BuyFragmentPresenter", "begin lruCacheUtil.read");
			lruCacheUtil.read("category");
		}
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);
		switch (vocational_id) {
			case ServerAPI.CATEGORY_LIST_VOCATIONAL_ID:
				lruCacheUtil.write((List<Category>) s.getResult(), "category");
				mIBuyFragmentView.showCategoryList((List<Category>) s.getResult());
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
		if (list.get(0) instanceof Category) {
			mIBuyFragmentView.showCategoryList(list);
		}
	}
}
