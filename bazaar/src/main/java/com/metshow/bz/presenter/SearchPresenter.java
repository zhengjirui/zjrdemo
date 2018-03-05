package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2017-7-14.
 */

public class SearchPresenter extends BzPresenter {

	BZContract.ISearchView iSearchView;

	public SearchPresenter(BZContract.ISearchView iView) {
		super(iView);
		iSearchView = iView;
	}

	public void searchTag(int pageindex, int pagesize, String key) {

		mModel.searchTag(pageindex, pagesize, key);
	}

	public void searchUser(int pageindex, int pagesize, String key) {

		mModel.searchUser(pageindex, pagesize, key);
	}

	public void searchTopic(int pageindex, int pagesize, String key) {

		mModel.searchTopic(pageindex, pagesize, key);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {

			case ServerAPI.COMMUNITY_SEARCH_VOCATIONAL_ID:
				iSearchView.getUserSuccess(((ListData<User>) s.getResult()).getItems());
				break;

			case ServerAPI.COMMUNITY_SEARCHTOPIC_VOCATIONAL_ID:
				iSearchView.getTopicSuccsess(((ListData<Topic>) s.getResult()).getItems());
				break;

			case ServerAPI.COMMUNITY_SEARCHTAG_VOCATIONAL_ID:
				iSearchView.getTagSuccess(((ListData<Tag>) s.getResult()).getItems());
				break;

		}

	}
}
