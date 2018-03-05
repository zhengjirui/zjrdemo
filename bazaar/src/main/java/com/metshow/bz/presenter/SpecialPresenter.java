package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.Special;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2017-7-8.
 */

public class SpecialPresenter extends BzPresenter {
	BZContract.ISpecialView iSpecialView;

	public SpecialPresenter(BZContract.ISpecialView iView) {
		super(iView);
		iSpecialView = iView;
	}

	public void getSpecial(long id) {
		mModel.getSpecial(id);
	}

	public void getComment(long id) {
		mModel.getCommentList(6, id, "0");
	}

	public void addAction(int acttype, long refid, int position) {
		mModel.addAction(acttype, refid, position);
	}

	public void deleteFav(long refid, int position) {
		mModel.deleteFav(refid, position);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			case ServerAPI.PRODUCTSPECIAL_DETAIL_VOCATIONAL_ID:
				iSpecialView.getSpecialSuccess((Special) s.getResult());
				break;
			case ServerAPI.COMMENT_LIST_VOCATIONAL_ID:
				iSpecialView.getCommentsSuccess(((ListData<Comment>) s.getResult()).getItems());
				break;

			//点赞  收藏成功
			case ServerAPI.ACTION_ADD_VOCATIONAL_ID:
				if ((int) exData.get("acttype") == 1) {//收藏
					iSpecialView.favSuccess((Integer) s.getResult(), (int) exData.get("position"));
				}
				break;
			//取消收藏
			case ServerAPI.ACTION_DELETEFAV_VOCATIONAL_ID:
				iSpecialView.deleteFavSuccess(Integer.valueOf((String)s.getResult()), (int) exData.get("position"));
				break;
		}

	}
}
