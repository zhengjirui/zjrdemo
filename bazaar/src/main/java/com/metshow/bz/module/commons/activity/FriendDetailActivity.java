package com.metshow.bz.module.commons.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kwan.base.activity.BaseCommonActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.FindTopicListAdapter;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.shai.TopicActivity;
import com.metshow.bz.presenter.FriendDetailPresenter;
import com.metshow.bz.util.DialogFactory;
import com.metshow.bz.util.ShareUtil;

import java.util.ArrayList;
import java.util.List;


public class FriendDetailActivity extends BaseCommonActivity implements BZContract.IFriendDetailView, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnRecyclerViewItemChildClickListener {

	private long mUserId;
	private String name;
	private int isFollow;
	private FriendDetailPresenter mPresenter;
	private User mUser;
	private TextView tv_name, tv_topic_num, tv_follow_num, tv_fans_num, tv_favourite_num, tv_description;
	private ImageView iv_avatar, iv_isVip;
	private FindTopicListAdapter mAdapter;
	private SwipeToLoadLayout swipeRefreshLayout;
	private ObservableRecyclerView mRecyclerView;
	private ArrayList<Topic> mTopics = new ArrayList<>();


	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();

		mUserId = (long) getIntentData("userid");


		name = (String) getIntentData("name");
		isFollow = (int) getIntentData("isFollow");
		if (name == null)
			name = "";

		mPresenter = new FriendDetailPresenter(this);
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_friend_detail;
	}

	@Override
	protected void initViews() {
		super.initViews();
		iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
		tv_name = (TextView) findViewById(R.id.tv_name);
		iv_isVip = (ImageView) findViewById(R.id.iv_isVip);
		tv_topic_num = (TextView) findViewById(R.id.tv_topic_num);
		tv_follow_num = (TextView) findViewById(R.id.tv_follow_num);
		tv_fans_num = (TextView) findViewById(R.id.tv_fans_num);
		tv_favourite_num = (TextView) findViewById(R.id.tv_favourite_num);
		tv_description = (TextView) findViewById(R.id.tv_description);

		mRecyclerView = (ObservableRecyclerView) findViewById(R.id.rl_topic);
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
		mRecyclerView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		mRecyclerView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		mRecyclerView.setHasFixedSize(true);
		//mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//解决更新 闪烁
		mRecyclerView.setItemAnimator(null);
		mRecyclerView.setNestedScrollingEnabled(false);

//		int dp = ViewUtil.dip2px(this, 5);
//		mRecyclerView.addItemDecoration(new SpacesItemDecoration(dp));
//		mRecyclerView.setPadding(dp, dp, dp, dp);
// recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, ScreenUtils.dip2px(this, 2), false));
		mAdapter = new FindTopicListAdapter(this, mTopics);
		mAdapter.setOnRecyclerViewItemChildClickListener(this);
		mRecyclerView.setAdapter(mAdapter);

		swipeRefreshLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setOnLoadMoreListener(this);

	}

	@Override
	protected void initData() {
		showProgress("");
		mPresenter.getUserInfo(String.valueOf(mUserId));
	}

	@Override
	protected String getTitleTxt() {
		return name;
	}

	@Override
	protected int getTitleBarRightLayoutId() {
		return R.layout.layout_user_menu;
	}

	@Override
	protected void setUpTitleRightView(View v) {
		super.setUpTitleRightView(v);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(50, 50);
		lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		//lp.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		lp.rightMargin = 20;
		v.setLayoutParams(lp);
		v.setOnClickListener(this);
	}

	private Dialog mDialog;

	@Override
	public void onClick(View v) {
		super.onClick(v);

		switch (v.getId()) {
			case R.id.iv_user_menu:

				View parent = getLayoutInflater().inflate(R.layout.send_topic_dialog, null);
				TextView tv_video = (TextView) parent.findViewById(R.id.tv_video);
				TextView tv_cancel = (TextView) parent.findViewById(R.id.tv_cancel);
				TextView tv_photo = (TextView) parent.findViewById(R.id.tv_photo);
				TextView tv_camera = (TextView) parent.findViewById(R.id.tv_camera);

				tv_video.setText("举报");
				tv_camera.setText("分享主页");

				tv_photo.setVisibility(View.GONE);

				tv_video.setOnClickListener(this);
				tv_cancel.setOnClickListener(this);
				tv_photo.setOnClickListener(this);
				tv_camera.setOnClickListener(this);

				mDialog = DialogFactory.showMenuDialog(this, parent);

				break;

			case R.id.tv_video:
				//举报
				mDialog.cancel();
				showProgress("");
				mPresenter.report(mUserId, 0);
				break;

			case R.id.tv_camera:
				mDialog.cancel();

				ShareUtil shareUtil = new ShareUtil(this);
				shareUtil.setStr_title(mUser.getNickName() + "的时尚朋友圈");
				shareUtil.setmText("快来芭莎in晒晒你的时尚朋友圈，越晒越时髦！");
				shareUtil.setStr_img(mUser.getAvatar());
				shareUtil.setStr_url("http://bz.metshow.com/api/2017/usersite.html?id=" + mUserId);
				shareUtil.showShareDialog();

				break;
		}

	}

	@Override
	public void getUserInfoSuccess(User user) {
		if (user != null) {

			mUser = user;

			tv_title.setText(user.getNickName());
			tv_name.setText(user.getNickName());
			mImageUtil.loadFromUrlWithCircle(mUser.getAvatar(), iv_avatar);

			tv_description.setText(user.getDescription());
			tv_topic_num.setText(user.getArticleCount() + "");
			tv_follow_num.setText(user.getFollowCount() + "");
			tv_fans_num.setText(user.getBeFollowedCount() + "");
			tv_favourite_num.setText("0");

			if (user.getIsVip() == 1)
				iv_isVip.setVisibility(View.VISIBLE);
			else
				iv_isVip.setVisibility(View.GONE);

		}
		mPresenter.getUserTopics(20, "0", mUserId);
	}

	@Override
	public void getUserTopicsSuccess(List<Topic> topics) {
		mTopics.addAll(topics);
		mAdapter.setNewData(topics);
		dismissProgress();
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
		mTopics.get(position).setIsFav(1);
		mTopics.get(position).setFavCount(Integer.valueOf(result));
		mAdapter.notifyItemChanged(position);
		dismissProgress();
	}

	@Override
	public void deleteFavSuccess(String result, int type, int position, int fragmentId) {
		mTopics.get(position).setIsFav(0);
		mTopics.get(position).setFavCount(Integer.valueOf(result));
		mAdapter.notifyItemChanged(position);
		dismissProgress();
	}

	@Override
	public void reportSuccess(Boolean result, int position) {
		dismissProgress();
		if (result)
			toastMsg("举报成功");
		else
			toastMsg("举报失败");
	}

	@Override
	public void onRefresh() {

	}

	@Override
	public void onLoadMore() {

	}


	@Override
	public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
		Topic topic = mTopics.get(position);

		switch (view.getId()) {
			//我的帖子 大图
			case R.id.iv_icon:

				Bundle bundlex = new Bundle();
				bundlex.putLong("topicid", topic.getArticleId());

				go2ActivityWithLeft(TopicActivity.class, bundlex, false);

				// toTopicDetail(topic);
				break;
			//我的贴子 评论
			case R.id.ll_pl:
				Bundle bundle = new Bundle();
				bundle.putInt("type", CommentActivity.TYPE_TOPIC);
				bundle.putLong("refid", topic.getArticleId());
				go2ActivityWithLeft(CommentActivity.class, bundle, false);
				break;
			//我的帖子 收藏
			case R.id.ll_fav:
				if (topic.getIsFav() == 0) {
					//收藏
					showProgress("");
					mPresenter.addAcition(1, topic.getArticleId(), position);
				} else {
					//取消收藏
					showProgress("");
					mPresenter.deleteFav(3, topic.getArticleId(), position, 0);
				}
				break;

			case R.id.tv_content:

				Bundle bundle2 = new Bundle();
				bundle2.putLong("topicid", topic.getArticleId());

				go2ActivityWithLeft(TopicActivity.class, bundle2, false);

//				Bundle bundle2 = new Bundle();
//				bundle2.putInt("type", TopicListActivity.MODE_USER);
//				bundle2.putLong("userid", topic.getUserId());
//				bundle2.putString("name", topic.getAuthor());
//				go2ActivityWithLeft(TopicListActivity.class, bundle2, false);

				break;
		}
	}
}
