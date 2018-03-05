package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 *
 * Created by Mr.Kwan on 2016-8-11.
 */
public class TopicMenuPresenter extends BzPresenter {


    private BZContract.ITopicMenuView mITopicMenuView;

    public TopicMenuPresenter(BZContract.ITopicMenuView iTopicMenuView) {
        super(iTopicMenuView);
        mITopicMenuView = iTopicMenuView;

    }

    public void deleteTopic(long articleid, int position){
        mModel.deleteTopic(articleid, position);
    }

    public void deleteFollow(long followUserId, int position){
		mModel.deleteFollow(followUserId, position);
    }

    public void disview(long refid, int position){
		mModel.disview(refid, position);
    }

    public void report(long refid, int position){
		mModel.report(refid, position);
    }


	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);
		switch (vocational_id) {

			//删除帖子
			case ServerAPI.ARTICLE_DELETEMYTOPIC_VOCATIONAL_ID:
				mITopicMenuView.deleteTopicSuccess((Boolean) s.getResult(), (int) exData.get("position"));
				break;

			//屏蔽 ，举报
			case ServerAPI.ACTION_ADD_VOCATIONAL_ID:
				if ((int) exData.get("acttype") == 3) {//举报
					mITopicMenuView.reportSuccess((Boolean) s.getResult(), (int) exData.get("position"));
				} else if ((int) exData.get("acttype") == 4) {//屏蔽
					mITopicMenuView.disViewSuccess((Integer) s.getResult(), (int) exData.get("position"));
				}
				break;
			//取消收藏
			case ServerAPI.FOLLOW_DELETE_VOCATIONAL_ID:
				mITopicMenuView.deleteFollowSuccess((Boolean) s.getResult(), (int) exData.get("position"));
				break;

		}
	}
}
