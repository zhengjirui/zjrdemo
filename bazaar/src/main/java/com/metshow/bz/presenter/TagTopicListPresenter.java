package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2017-8-6.
 */

public class TagTopicListPresenter extends BzPresenter {

	private BZContract.ITagTopicListView iTagTopicListView;

	public TagTopicListPresenter(BZContract.ITagTopicListView iView) {
		super(iView);
		iTagTopicListView = iView;
	}

	public void getTopics(int pageindex, int pagesize, String tagname ,long userid){
		mModel.getCommunityListByTagAndUserid(pageindex, pagesize, tagname, userid);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);
		switch (vocational_id) {

			case ServerAPI.COMMUNITY_LISTBYTAGBYUSERID_VOCATIONAL_ID:
				iTagTopicListView.getTopicSuccess(((ListData<Topic>) s.getResult()).getItems());
				break;
		}

	}
}
