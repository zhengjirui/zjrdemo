package com.metshow.bz.presenter;

import android.util.Log;

import com.kwan.base.bean.ServerMsg;
import com.kwan.base.rxbus.RxBusSubscriberListener;
import com.metshow.bz.commons.bean.Adforrec;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.article.Article;
import com.metshow.bz.commons.bean.article.Banner;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.LruCacheUtil;

/**
 * Created by Mr.Kwan on 2017-6-18.
 */

public class InItemFragmentPresenter extends BzPresenter implements RxBusSubscriberListener<List> {

	private BZContract.IInItemFragmentView mIView;
	private LruCacheUtil<List<Article>> lruCacheUtil;
	private boolean isRec = false;

	public InItemFragmentPresenter(BZContract.IInItemFragmentView iView) {
		super(iView);
		mIView = iView;
		lruCacheUtil = new LruCacheUtil<>();
		initRxBus(List.class, this);
	}

	public void getRecList(int pageindex, int pagesize) {
		isRec = true;
		if (pageindex == 1)
			mModel.getRecList(pageindex, pagesize);
		else {
			if (lruCacheUtil.isObjectCacheOutOfDate("articlesrec", LruCacheUtil.MAX_CACHEDATA_5MIN)) {
				//网络请求
				mModel.getRecList(pageindex, pagesize);
			} else {
				//缓存请求 请求完成后会通过RxBus通知回调
				Log.d("getRecList", "begin lruCacheUtil.read");
				lruCacheUtil.read("articlesrec");
			}
		}
	}

	public void getInRecommend() {
		mModel.getInRecommend();
	}

	long mChannelId;

	public void getArticles(long channelId, int pagesize, String maxdate,boolean isLoadMore) {

		mChannelId = channelId;

		if (maxdate.equals("0"))
			mModel.getArticleList(channelId, pagesize, maxdate);
		else {
			if (isLoadMore||lruCacheUtil.isObjectCacheOutOfDate("articlesin", LruCacheUtil.MAX_CACHEDATA_5MIN)) {
				//网络请求
				mModel.getArticleList(channelId, pagesize, maxdate);
			} else {
				//缓存请求 请求完成后会通过RxBus通知回调
				Log.d("getArticles", "begin lruCacheUtil.read");
				lruCacheUtil.read("articlesin");
			}
		}
	}

	public void getArticleBanner(long channelid) {
		mModel.getArticleBanner(channelid);
	}


	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			case ServerAPI.ARTICLE_RECLIST_VOCATIONAL_ID:
				List<Article> data = ((ListData<Article>) s.getResult()).getItems();
				for (Article article : data)
					article.setItemType(article.getArticleType() - 1);
				lruCacheUtil.write(data, "articlesrec");

				Collections.shuffle(data);
				mIView.showRecListData(data);
				break;
			case ServerAPI.RECOMMEND_LIST_VOCATIONAL_ID:
				mIView.getBannerSuccess((List<Banner>) s.getResult());
				break;
			case ServerAPI.ARTICLE_LIST_VOCATIONAL_ID:
				List<Article> result = ((ListData<Article>) s.getResult()).getItems();
				lruCacheUtil.write(result, "articlesin");

				Collections.shuffle(result);
				mIView.getArticlesSuccess(result);
				break;
			case ServerAPI.ADFORREC_LIST_VOCATIONAL_ID:
				mIView.getInRecommendSuccess((Adforrec) s.getResult());
				break;
		}
	}


	@Override
	public void onRxBusCompleted() {

	}

	@Override
	public void onRxBusError(Throwable e) {

		if(isRec)
			mModel.getRecList(1, 20);
		else
			mModel.getArticleList(mChannelId, 20, "0");
	}

	@Override
	public void onRxBusNext(List list) {
		if (list.get(0) instanceof Article) {
			Collections.shuffle(list);
			if (isRec) {
				mIView.showRecListData(list);
			}else{
				mIView.getArticlesSuccess(list);
			}
		}
	}
}
