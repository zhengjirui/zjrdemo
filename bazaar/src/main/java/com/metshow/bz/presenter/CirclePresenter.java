package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2016-8-22.
 */
public class CirclePresenter extends BzPresenter {


    private BZContract.ICircleView mICircleView;

    public CirclePresenter(BZContract.ICircleView iCircleView) {
        super(iCircleView);
        mICircleView = iCircleView;
    }

    public void getCircleTopic(boolean isLogin, String maxdate, int pagesize, int pageindex) {

        if (isLogin)
            mModel.getCircleTopics(maxdate, pagesize);
        else
            mModel.getTopicRec(pageindex, pagesize);
    }

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);
		switch (vocational_id) {
			case ServerAPI.COMMUNITY_CIRCLEARTICLE2_VOCATIONAL_ID:
				mICircleView.getTopicSuccess(((ListData<Topic>) s.getResult()).getItems());
				break;

			case ServerAPI.COMMUNITY_REC_VOCATIONAL_ID:
				mICircleView.getTopicSuccess(((ListData<Topic>) s.getResult()).getItems());
				break;
		}


	}

}
