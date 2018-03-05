package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.FollowItem;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.article.FavArticle;
import com.metshow.bz.commons.bean.message.MsgChatItem;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2017-7-17.
 */

public class MessageListPresenter extends BzPresenter {

	private BZContract.IMessageListView iMessageListView;

	public MessageListPresenter(BZContract.IMessageListView iView) {
		super(iView);
		iMessageListView = iView;
	}

	public void getBeFollowList(int pageindex, int pagesize){
		mModel.getBeFollowList(pageindex, pagesize);
	}

	public void getCommentToMe(int pagesize, String maxdate){
		mModel.getCommentsToMe(pagesize,maxdate);
	}

	public void getMsgChatList(String PAGESIZE, String MAXDATE){
		mModel.getMsgChatList(PAGESIZE,MAXDATE);
	}

	public void getBeFaved(int pagesize, String createdate){
		mModel.getBeFaved(pagesize, createdate);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);
		if (s.isSuc()) {
			switch (vocational_id) {

				case ServerAPI.FOLLOW_FOLLOWEDLIST_VOCATIONAL_ID:
					iMessageListView.getBeFollowListSuccess(((ListData<FollowItem>)s.getResult()).getItems(),s.getServertime());
					break;
				case ServerAPI.COMMENT_REPLYLIST_VOCATIONAL_ID:
					iMessageListView.getMeCommentSuccess(((ListData<Comment>) s.getResult()).getItems(),s.getServertime());
					break;
				//我的消息 聊天
				case ServerAPI.MESSAGE_LIST_VOCATIONAL_ID:
					iMessageListView.getMsgChatItemSuccess(((ListData<MsgChatItem>) s.getResult()).getItems());
					break;
				case ServerAPI.MYCOMMUNITY_FAVED_VOCATIONAL_ID:
					iMessageListView.getFavArticleSuccess(((ListData<FavArticle>) s.getResult()).getItems());
					break;
			}
		}
	}
}
