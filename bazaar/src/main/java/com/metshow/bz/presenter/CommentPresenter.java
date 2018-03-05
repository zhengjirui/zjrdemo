package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2017-6-12.
 */

public class CommentPresenter extends BzPresenter {

	BZContract.ICommentView mIViews;

	public CommentPresenter(BZContract.ICommentView iView) {
		super(iView);
		mIViews = iView;
	}

	public void reportComment(long CommentId) {
		mModel.reportComment(CommentId);
	}

	public void upLoadImag(String path, String content, long refId, int type, long parentId) {
		mModel.uploadImag(path, content, refId, type, parentId);
	}

	public void addComment(String Content, String Image, long RefId, int Type, long ParentId) {
		mModel.addComment(Content, Image, RefId, Type, ParentId);
	}

	public void getCommentList(int type, long refid, String maxdate) {
		mModel.getCommentList(type, refid, maxdate);
	}

	public void getMyComment(String maxdate){

		mModel.getMyComment( maxdate);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {

			case ServerAPI.COMMENT_MYLIST_VOCATIONAL_ID:
				mIViews.getMyCommentSuccess(((ListData<Comment>) s.getResult()).getItems());
				break;

			case ServerAPI.COMMENT_LIST_VOCATIONAL_ID:
				mIViews.getCommentListSuccess(((ListData<Comment>) s.getResult()).getItems());
				break;
			case ServerAPI.COMMENT_ADD_VOCATIONAL_ID:
				mIViews.addCommnetSuccess(Integer.valueOf((String) s.getResult()));
				break;
			case ServerAPI.COMMENTACTION_POLICE_VOCATIONAL_ID:
				mIViews.reportSuccess(Integer.valueOf((String) s.getResult()));
				break;
		}

	}

	@Override
	public void onServerUploadNext(int vocational_id, HashMap exdata, Object s) {
		super.onServerUploadNext(vocational_id, exdata, s);

		switch (vocational_id) {
			case ServerAPI.PIC_UPLOADIMAGE_VOCATIONAL_ID:
				mIViews.addCommnetSuccess(Integer.valueOf(((ServerMsg<String>)s).getResult()));
				break;
		}
	}
}
