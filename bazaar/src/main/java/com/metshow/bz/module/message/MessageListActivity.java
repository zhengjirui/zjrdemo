package com.metshow.bz.module.message;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kwan.base.activity.CommonRecycleActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.FollowItem;
import com.metshow.bz.commons.bean.article.FavArticle;
import com.metshow.bz.commons.bean.message.MsgChatItem;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.MessageListPresenter;

import java.util.ArrayList;
import java.util.List;

public class MessageListActivity extends CommonRecycleActivity implements BZContract.IMessageListView {

	int mType = -1;
	String title;
	MessageListPresenter mMessageListPresenter;
	public static final int TYPE_FOLLOW = 1;
	public static final int TYPE_COMMENT = 2;
	public static final int TYPE_FAV = 3;
	public static final int TYPE_CHAT = 4;
	public static final int  TYPE_NOTICE = 5;

	public ArrayList<FollowItem> mFollowItems = new ArrayList<>();
	public ArrayList<Comment> mComments = new ArrayList<>();
	public ArrayList<FavArticle> mFavArticles = new ArrayList<>();
	public ArrayList<MsgChatItem> mMsgChatItems = new ArrayList<>();
	public ArrayList<String> mNotices = new ArrayList<>();


	BaseQuickAdapter adapter;


	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mType = (int) getIntentData("type");
		title = (String) getIntentData("title");
		mMessageListPresenter = new MessageListPresenter(this);
	}

	@Override
	protected String getTitleTxt() {
		return title;
	}



	@Override
	protected BaseQuickAdapter getAdapter() {

		int layout = 0;
		ArrayList<? extends Object> data = new ArrayList<>();

		switch (mType) {
			case TYPE_FOLLOW:
				data = mFollowItems;
				layout = R.layout.list_item_msg_follow;
				break;
			case TYPE_COMMENT:
				data = mComments;
				layout = R.layout.list_item_msg_comment;
				break;
			case TYPE_FAV:
				data = mFavArticles;
				layout = R.layout.list_item_msg_comment;
				break;
			case TYPE_CHAT:
				layout = R.layout.list_item_msg_chat;
				data = mMsgChatItems;
				break;

			case TYPE_NOTICE:
				layout = R.layout.list_item_msg_chat;
				data = mNotices;
				break;
		}
		
		//具体接口：
		//In改版接口文档：17.1 系统通知
		//获取消息数量：In接口文档3.5


		adapter = new BaseQuickAdapter(this, layout, data) {
			@Override
			protected void convert(BaseViewHolder helper, Object item, int position) {

				switch (mType) {
					case TYPE_FOLLOW:
						FollowItem followItem = (FollowItem) item;

						helper.setImageUrl(R.id.iv_avatar, followItem.getFollowAvatar());
						helper.setText(R.id.tv_content, followItem.getFollowNickName() + "关注了你");
						helper.setText(R.id.tv_time, BasePresenter.getStrData(followItem.getFollowDate()));

						break;
					case TYPE_COMMENT:
						Comment comment = (Comment) item;
						helper.setImageUrl(R.id.iv_avatar, comment.getAvatar());
						helper.setImageUrl(R.id.iv_icon, comment.getSourceImage());
						helper.setText(R.id.tv_content, comment.getNickName() + " 评论说: " + comment.getContent());
						helper.setText(R.id.tv_time, BasePresenter.getStrData(comment.getCreateDate()));

						break;
					case TYPE_FAV:
						FavArticle favArticle = (FavArticle) item;
						helper.setImageUrl(R.id.iv_avatar, favArticle.getAuthorIco());
						helper.setImageUrl(R.id.iv_icon, favArticle.getIco());
						((TextView)helper.getView(R.id.tv_content)).setMaxLines(1);
						helper.setText(R.id.tv_content, favArticle.getAuthor() + " 喜欢了你的" + favArticle.getTitle());
						helper.setText(R.id.tv_time, BasePresenter.getStrData(favArticle.getPublishDate()));

						break;
					case TYPE_CHAT:
						MsgChatItem chatItem = (MsgChatItem) item;
						helper.setImageUrl(R.id.iv_avatar, chatItem.getAvatar());
						helper.setText(R.id.tv_name, chatItem.getNickName());
						((TextView)helper.getView(R.id.tv_content)).setMaxLines(1);
						helper.setText(R.id.tv_content, chatItem.getContent());
						helper.setText(R.id.tv_time, BasePresenter.getStrData(chatItem.getFollowDate()));

						break;

					case TYPE_NOTICE:

						break;
				}

			}
		};

		return adapter;
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {
		return new LinearLayoutManager(this);
	}


	@Override
	public void onRefresh() {
		super.onRefresh();

		switch (mType) {
			case TYPE_FOLLOW:
				mMessageListPresenter.getBeFollowList(1, 20);
				break;
			case TYPE_COMMENT:
				mMessageListPresenter.getCommentToMe(20, "0");
				break;
			case TYPE_FAV:
				mMessageListPresenter.getBeFaved(20, "0");
				break;
			case TYPE_CHAT:
				mMessageListPresenter.getMsgChatList("20", "0");
				break;
			case TYPE_NOTICE:
				swipeRefreshLayout.setRefreshing(false);
				break;
		}


	}

	@Override
	public void getBeFollowListSuccess(List<FollowItem> items, String servertime) {

		mFollowItems.addAll(items);
		adapter.setNewData(items);
		swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void getMeCommentSuccess(List<Comment> items, String servertime) {
		mComments.addAll(items);
		adapter.setNewData(items);
		swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void getMsgChatItemSuccess(List<MsgChatItem> items) {
		mMsgChatItems.addAll(items);
		adapter.setNewData(items);
		swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void getFavArticleSuccess(List<FavArticle> items) {
		mFavArticles.addAll(items);
		adapter.setNewData(items);
		swipeRefreshLayout.setRefreshing(false);
	}
}
