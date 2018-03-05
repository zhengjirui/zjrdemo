package com.metshow.bz.module.commons.activity;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.ChatAdapter;
import com.metshow.bz.commons.bean.AdminUser;
import com.metshow.bz.commons.bean.FollowItem;
import com.metshow.bz.commons.bean.message.MsgChatDetail;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.ChatPresenter;
import com.metshow.bz.presenter.FollowPresenter;
import com.metshow.bz.util.DialogFactory;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends BaseChatActivity implements BZContract.IChatView, OnRefreshListener, BZContract.IFollowView {

	private String name;
	private ImageView iv_add;
	private ChatPresenter mChatPresenter;
	private long toUserId, adminId;
	private ArrayList<MsgChatDetail> chatDetails = new ArrayList<>();
	private ChatAdapter adapter;
	private Dialog adminDialog;
	private View adminView;
	private FollowPresenter followPresenter;
	private AdminUser mAdminUser;

	@Override
	protected void beForeSetContentView() {

		super.beForeSetContentView();
		name = (String) getIntentData("name");
		toUserId = (long) getIntentData("toUserId");
		adminId = (long) getIntentData("adminId");
		mChatPresenter = new ChatPresenter(this);
		followPresenter = new FollowPresenter(this);
		Log.d("kwan", "adminId::" + adminId);

	}

	@Override
	protected String getTitleTxt() {
		return name;
	}

	@Override
	protected int getTitleBarRightLayoutId() {
		return R.layout.title_right_follow;
	}

	@Override
	protected void setUpTitleRightView(View v) {
		super.setUpTitleRightView(v);

		iv_add = (ImageView) v;
		iv_add.setOnClickListener(this);
		if (adminId == 0)
			iv_add.setVisibility(View.GONE);
		else {
			adminView = getLayoutInflater().inflate(R.layout.dialog_admin, null);
			adminDialog = DialogFactory.showMenuDialog(this, adminView, false);
			mChatPresenter.getAdminInfo(adminId);
		}

	}

	@Override
	protected void initViewSetting() {
		super.initViewSetting();

		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
		//自动滑到底部
		layoutManager.setStackFromEnd(true);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setHasFixedSize(true);
		swipeToLoadLayout.setOnRefreshListener(this);

		swipeToLoadLayout.setLoadMoreEnabled(false);
		swipeToLoadLayout.setRefreshEnabled(true);

		adapter = new ChatAdapter(this, chatDetails);
		mRecyclerView.setAdapter(adapter);
	}

	@Override
	protected void initData() {
		swipeToLoadLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeToLoadLayout.setRefreshing(true);
			}
		});
	}


	@Override
	protected BasePresenter getPresent() {
		return mChatPresenter;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v == iv_add) {
			adminDialog.show();
			// mRecyclerView.scrollVerticallyTo(0);
		}
	}

	@Override
	protected void onSend(String s) {
		showProgress("");
		mChatPresenter.addMsg(toUserId, s, 0, "");
	}

	@Override
	protected void onSelectImageBack(String s) {
		showProgress("正在发送..");
		mChatPresenter.uploadImg(s);
	}

	@Override
	public void showMsgChatDiaog(List<MsgChatDetail> details) {
		//Collections.reverse(details);

		boolean isFrist = chatDetails.size() == 0;
		chatDetails.addAll(details);
		adapter.addData(details);
		if (isFrist)
			recycleMoveEnd();
		swipeToLoadLayout.setRefreshing(false);
	}

	int addMsgCount = 0;

	@Override
	public void addMsgSuccess(MsgChatDetail detail) {
		addMsgCount++;
		chatDetails.add(detail);
		adapter.add(0, detail);
		recycleMoveEnd();
		dismissProgress();

	}

	@Override
	public void uploadImgSuccess(String url) {
		dismissProgress();
		mChatPresenter.addMsg(toUserId, "", 1, url);
	}

	@Override
	public void uploadImgFailed() {
		dismissProgress();
	}

	@Override
	public void getAdminInfoSuccess(final AdminUser user) {

		mAdminUser = user;
		adminView.findViewById(R.id.content).setVisibility(View.VISIBLE);
		adminView.findViewById(R.id.progressBar).setVisibility(View.GONE);

		((TextView) adminView.findViewById(R.id.tv_tag)).setText(mAdminUser.getTags());
		((TextView) adminView.findViewById(R.id.tv_description)).setText(mAdminUser.getDescription());
		((TextView) adminView.findViewById(R.id.tv_name)).setText(mAdminUser.getNickName());
		mImageUtil.loadFromUrlCenterInside(mAdminUser.getAvatar(), (ImageView) adminView.findViewById(R.id.iv_avatar));

		TextView tv_add = (TextView) adminView.findViewById(R.id.tv_add_follow);

		if (mAdminUser.getIsFollow() > 0)
			tv_add.setText("取消关注");
		else
			tv_add.setText("关注作者");

		tv_add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addOrDelFav(mAdminUser.getIsFollow(), mAdminUser.getUserId());
			}
		});
	}

	private void addOrDelFav(int isFollow, long id) {
		if (isFollow > 0)
			followPresenter.deleteFollow(id, 0);
		else
			followPresenter.addFollow(id, 0);
		showProgress("");
	}



	@Override
	public void onRefresh() {

		if (chatDetails.size() > 0)
			mChatPresenter.getMsgDiolgDetails(30, BasePresenter.getData(chatDetails.get(chatDetails.size() - 1).getCreateDate()), toUserId);
		else
			mChatPresenter.getMsgDiolgDetails(30, "0", toUserId);
	}

	@Override
	public void onServerFailed(String s) {
		super.onServerFailed(s);
		swipeToLoadLayout.setRefreshing(false);
	}

	@Override
	public void onServerError(int vocational_id, Throwable e) {
		super.onServerError(vocational_id, e);
		swipeToLoadLayout.setRefreshing(false);
	}

	@Override
	public void getFollowListSuccess(List<FollowItem> followItems) {

	}

	@Override
	public void getBeFollowListSuccess(List<FollowItem> followItems, String serverTime) {

	}

	@Override
	public void getDeleteFollowSuccess(Boolean result, int position) {
		((TextView) adminView.findViewById(R.id.tv_add_follow)).setText("关注作者");
		mAdminUser.setIsFollow(0);
		toastMsg("取消成功");
		dismissProgress();
	}

	@Override
	public void addFollowSuccess(int position) {

		((TextView) adminView.findViewById(R.id.tv_add_follow)).setText("取消关注");
		mAdminUser.setIsFollow(1);
		toastMsg("关注成功");
		dismissProgress();
	}
}





