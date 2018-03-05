package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.TopicSubject;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2016-9-1.
 */

public class TopicSubjectPresenter extends BzPresenter {

    BZContract.ITopicSubjectView mITopicSubjectView;

    public TopicSubjectPresenter(BZContract.ITopicSubjectView iTopicSubjectView) {
        super(iTopicSubjectView);
        mITopicSubjectView = iTopicSubjectView;
    }

    public void searchSubject(String key){
        mModel.searchSubject(key);
    }

    public void getHotSubject(){
		mModel.getHotSubject();
    }


	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);
		switch (vocational_id) {
			case ServerAPI.TOPIC_HOTLIST_VOCATIONAL_ID:
				mITopicSubjectView.getHotSubjectSuccess(((ListData<TopicSubject>) s.getResult()).getItems());
				break;

			case ServerAPI.TOPIC_SEARCH_VOCATIONAL_ID:
				mITopicSubjectView.searchSubject(((ListData<TopicSubject>) s.getResult()).getItems());
				break;
		}
	}

}
