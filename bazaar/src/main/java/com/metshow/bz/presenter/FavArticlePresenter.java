package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.article.FavArticle;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2017-7-11.
 */

public class FavArticlePresenter extends BzPresenter {

	BZContract.IFavArticleView iFavArticleView;

	public FavArticlePresenter(BZContract.IFavArticleView iView) {
		super(iView);
		iFavArticleView = iView;
	}

	//我收藏的 文章
	public void getFavArticle(int pagesize, String createdate) {
		mModel.getFavArticle(pagesize, createdate);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {

			case ServerAPI.ACTION_MYARTICLEFAV_VOCATIONAL_ID:
				iFavArticleView.getFavArticleSuccess(((ListData<FavArticle>) s.getResult()).getItems());
				break;

		}
	}
}
