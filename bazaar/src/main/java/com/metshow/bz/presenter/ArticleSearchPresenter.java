package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.ServerConfig;
import com.metshow.bz.commons.bean.article.Article;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2017-7-12.
 */

public class ArticleSearchPresenter extends BzPresenter {

	BZContract.IArticleSearchView iArticleSearchView;

	public ArticleSearchPresenter(BZContract.IArticleSearchView iView) {
		super(iView);
		iArticleSearchView = iView;
	}

	public void searchArticle(String key, int pagesize, String maxdate) {
		mModel.searchArticle(key, pagesize, maxdate);
	}


	public void getHotTag() {
		mModel.getHotTag();
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			case ServerAPI.ARTICLE_SEARCH_VOCATIONAL_ID:
				iArticleSearchView.getArticleSuccess(((ListData<Article>) s.getResult()).getItems());
				break;
			case ServerAPI.CONFIG_DETAIL_VOCATIONAL_ID:
				iArticleSearchView.showTags(((ServerConfig)s.getResult()).getHotKey().split(","));
				break;
		}
	}
}
