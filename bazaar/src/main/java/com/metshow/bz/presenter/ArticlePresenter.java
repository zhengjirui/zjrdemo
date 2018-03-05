package com.metshow.bz.presenter;

import android.util.Log;

import com.kwan.base.bean.ServerMsg;
import com.kwan.base.rxbus.RxBusSubscriberListener;
import com.metshow.bz.commons.bean.article.Article;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;
import com.metshow.bz.util.BZSPUtil;

import java.util.HashMap;

import io.LruCacheUtil;


/**
 * Created by Mr.Kwan on 2016-8-16.
 */
public class ArticlePresenter extends BzPresenter implements RxBusSubscriberListener<Article> {

	private BZContract.IArticleView mIArticleView;
	private LruCacheUtil<Article> lruCacheUtil;
	private long id = 0;

	public ArticlePresenter(BZContract.IArticleView iArticleView) {
		super(iArticleView);
		mIArticleView = iArticleView;
		lruCacheUtil = new LruCacheUtil<>();
		initRxBus(Article.class, this);

	}

	String token = "";

	public void getArticleDetail(long id) {
		this.id = id;
		token = BZSPUtil.getUser().getToken();

		//判断是否过期
		if (lruCacheUtil.isObjectCacheOutOfDate("article" + this.id + "" + token, LruCacheUtil.MAX_CACHEDATA_1HOUR)) {
			//网络请求
			mModel.getArticleDetail(id);
		} else {
			//缓存请求 请求完成后会通过RxBus通知回调
			Log.d("ArticlePresenter", "begin lruCacheUtil.read");
			lruCacheUtil.read("article" + this.id + "" + token);
		}

	}

	public void addAcition(int acttype, long refid, int position) {
		mModel.addAcition(acttype, refid, position);
	}

	public void deleteFav(long refid, int position) {
		mModel.deleteFav(refid, position);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			case ServerAPI.ARTICLE_DETAIL_VOCATIONAL_ID:
				lruCacheUtil.write((Article) s.getResult(), "article" + this.id + "" + token);
				mIArticleView.getArticleSuccess((Article) s.getResult());
				break;
			//点赞  收藏成功
			case ServerAPI.ACTION_ADD_VOCATIONAL_ID:
				if ((int) exData.get("acttype") == 1) {//收藏
					mIArticleView.favSuccess((String) s.getResult(), (int) exData.get("position"));
				}
				break;
			//取消收藏
			case ServerAPI.ACTION_DELETEFAV_VOCATIONAL_ID:
				mIArticleView.deleteFavSuccess((String) s.getResult(), (int) exData.get("position"));
				break;
		}

	}

	@Override
	public void onRxBusCompleted() {

	}

	@Override
	public void onRxBusError(Throwable e) {
		Log.d("Article presenter", "onRxBusError");
		mModel.getArticleDetail(id);
	}

	@Override
	public void onRxBusNext(Article article) {
		Log.d("Article presenter", "onRxBusNext");
		mIArticleView.getArticleSuccess(article);
	}
}
