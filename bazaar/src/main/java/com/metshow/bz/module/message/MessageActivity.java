package com.metshow.bz.module.message;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kwan.base.activity.BaseCommonActivity;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.FriendDetailPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

public class MessageActivity extends BaseCommonActivity implements BZContract.IFriendDetailView {

	private AutoLinearLayout ll_follow, ll_comment, ll_fav, ll_chat, ll_notice;
	private TextView tv_follow,tv_comment,tv_fav,tv_chat,tv_notice;

	private FriendDetailPresenter mPresenter;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();

		mPresenter = new FriendDetailPresenter(this);
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_message;
	}

	@Override
	protected void initViews() {
		super.initViews();

		ll_follow = (AutoLinearLayout) findViewById(R.id.ll_follow);
		ll_comment = (AutoLinearLayout) findViewById(R.id.ll_comment);
		ll_fav = (AutoLinearLayout) findViewById(R.id.ll_fav);
		ll_chat = (AutoLinearLayout) findViewById(R.id.ll_chat);
		ll_notice = (AutoLinearLayout) findViewById(R.id.ll_notice);

		tv_follow= (TextView) findViewById(R.id.tv_newfollow);
		tv_comment= (TextView) findViewById(R.id.tv_newcomment);
		tv_fav= (TextView) findViewById(R.id.tv_newfav);
		tv_chat= (TextView) findViewById(R.id.tv_newchat);
		tv_notice= (TextView) findViewById(R.id.tv_newnotice);

	}

	@Override
	protected void initViewSetting() {
		super.initViewSetting();

		ll_follow.setOnClickListener(this);
		ll_comment.setOnClickListener(this);
		ll_fav.setOnClickListener(this);
		ll_chat.setOnClickListener(this);
		ll_notice.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		int type;
		String title;

		if (v == ll_follow) {
			type = MessageListActivity.TYPE_FOLLOW;
			title = "新的关注";
			goNext(type, title);
		} else if (v == ll_comment) {
			type = MessageListActivity.TYPE_COMMENT;
			title = "评论我的";
			goNext(type, title);
		} else if (v == ll_fav) {
			type = MessageListActivity.TYPE_FAV;
			title = "被喜欢的";
			goNext(type, title);
		} else if (v == ll_chat) {
			type = MessageListActivity.TYPE_CHAT;
			title = "聊天";
			goNext(type, title);
		} else if (v == ll_notice) {
			type = MessageListActivity.TYPE_NOTICE;
			title = "通知";
			goNext(type, title);
		}
	}

	void goNext(int type, String title) {
		Bundle bundle = new Bundle();
		bundle.putInt("type", type);
		bundle.putString("title", title);
		go2ActivityWithLeft(MessageListActivity.class, bundle, false);
	}

	@Override
	protected void initData() {
		mPresenter.getUserInfo(BZSPUtil.getUser().getUserId() + "");
	}

	@Override
	protected String getTitleTxt() {
		return "消息";
	}

	@Override
	public void getUserInfoSuccess(User user) {

		int FollowedBadgeCount = user.getFollowedBadgeCount();
		int ReplyBadgeCount =user.getMessageBadgeCount();
		int FavBadgeCount =user.getFavBadgeCount();
		int MessageBadgeCount = user.getMessageBadgeCount();
		int SystemMsgBadgeCount = user.getSystemMsgBadgeCount();

		if(FollowedBadgeCount>0) {
			tv_follow.setText(FollowedBadgeCount + "");
			tv_follow.setVisibility(View.VISIBLE);
		}

		if(ReplyBadgeCount>0) {
			tv_comment.setText(ReplyBadgeCount + "");
			tv_comment.setVisibility(View.VISIBLE);
		}

		if(FavBadgeCount>0) {
			tv_fav.setText(FavBadgeCount + "");
			tv_fav.setVisibility(View.VISIBLE);
		}

		if(MessageBadgeCount>0) {
			tv_chat.setText(MessageBadgeCount + "");
			tv_chat.setVisibility(View.VISIBLE);
		}

		if(SystemMsgBadgeCount>0) {
			tv_notice.setText(SystemMsgBadgeCount + "");
			tv_notice.setVisibility(View.VISIBLE);
		}

		Log.d("kwan","xxx::"+user.getMessageBadgeCount());
	}

	@Override
	public void getUserTopicsSuccess(List<Topic> topics) {

	}

	@Override
	public void getUserTagsSuccess(List<Tag> tags) {

	}

	@Override
	public void addFollowSuccess() {

	}

	@Override
	public void deleteFollowSuccess() {

	}

	@Override
	public void favSuccess(String result, int position) {

	}

	@Override
	public void deleteFavSuccess(String result, int type, int position, int fragmentId) {

	}

	@Override
	public void reportSuccess(Boolean result, int position) {

	}
}
