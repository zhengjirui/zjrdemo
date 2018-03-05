package com.metshow.bz.module.me.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.kwan.base.activity.CommonRecycleActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.FindTopicListAdapter;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.activity.CommentActivity;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.module.shai.TopicActivity;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.metshow.bz.presenter.MyTopicPresenter;
import com.metshow.bz.util.BZSPUtil;

import java.util.ArrayList;
import java.util.List;

public class MyTopicActivity extends CommonRecycleActivity implements BZContract.IMyTopicView, BaseQuickAdapter.OnRecyclerViewItemChildClickListener {

	private List<Topic> mTopics = new ArrayList<>();
	private FindTopicListAdapter adapter;
	MyTopicPresenter mMyTopicPresenter;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mMyTopicPresenter = new MyTopicPresenter(this);
	}

	@Override
	protected void initData() {
		super.initData();
		showProgress("");
		mMyTopicPresenter.getTag(1,20, BZSPUtil.getUser().getUserId());
	}

	@Override
	protected void initViewSetting() {
		super.initViewSetting();
//		int dp = ViewUtil.dip2px(this, 5);
//		mRecyclerView.addItemDecoration(new SpacesItemDecoration(dp));
//		mRecyclerView.setPadding(dp, dp, dp, dp);
		//mRecyclerView.setPadding(35,20,35,20);
	}

	@Override
	protected String getTitleTxt() {
		return "帖子";
	}

	@Override
	protected BaseQuickAdapter getAdapter() {
		adapter = new FindTopicListAdapter(this,mTopics);
		adapter.setOnRecyclerViewItemChildClickListener(this);
		adapter.setFullSpanIndex(new int[]{0});
		return adapter;
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {
		return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
	}

	@Override
	public void getUserTagsSuccess(List<Tag> tags) {
		Topic topic = new Topic();
		topic.setItemType(3);
		topic.setTopictags(tags);

		mTopics.add(topic);
		mMyTopicPresenter.getMyTopic(20,"0");
	}

	@Override
	public void getTopicsSuccess(List<Topic> topics) {


		mTopics.addAll(topics);
		adapter.setNewData(mTopics);

		dismissProgress();
		swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void deleteFavSuccess(String result, int type, int position, int fragmentId) {
		mTopics.get(position).setIsFav(0);
		mTopics.get(position).setFavCount(Integer.valueOf(result));

		((FindTopicListAdapter) mRecyclerView.getAdapter()).getData().get(position).setIsFav(0);
		((FindTopicListAdapter) mRecyclerView.getAdapter()).getData().get(position).setFavCount(Integer.valueOf(result));

		(mRecyclerView.getAdapter()).notifyItemChanged(position + 1);
		dismissProgress();
	}

	@Override
	public void favSuccess(String result, int position) {
		mTopics.get(position).setIsFav(1);
		mTopics.get(position).setFavCount(Integer.valueOf(result));

		((FindTopicListAdapter) mRecyclerView.getAdapter()).getData().get(position).setIsFav(1);
		((FindTopicListAdapter) mRecyclerView.getAdapter()).getData().get(position).setFavCount(Integer.valueOf(result));

		(mRecyclerView.getAdapter()).notifyItemChanged(position + 1);
		dismissProgress();

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
				break;
			//我的贴子 评论
			case R.id.ll_pl:

				if (BZSPUtil.getIsLogin()) {
					Bundle bundle = new Bundle();
					bundle.putInt("type", CommentActivity.TYPE_TOPIC);
					bundle.putLong("refid", topic.getArticleId());
					go2ActivityWithLeft(CommentActivity.class, bundle, false);
				} else {
					go2Activity(DologinActivity.class, null, false);
				}
				break;
			//我的帖子 收藏
			case R.id.ll_fav:
				if (BZSPUtil.getIsLogin()) {
					if (topic.getIsFav() == 0) {
						//收藏
						showProgress("");
						mMyTopicPresenter.addAcition(1, topic.getArticleId(), position);
					} else {
						//取消收藏
						showProgress("");
						mMyTopicPresenter.deleteFav(3, topic.getArticleId(), position, 0);
					}
				} else {
					go2Activity(DologinActivity.class, null, false);
				}
				break;

			case R.id.ll_user:
				Bundle bundle1 = new Bundle();
				bundle1.putLong("userid", topic.getUserId());
				bundle1.putInt("isFollow", topic.getIsFollow());
				go2ActivityWithLeft(FriendDetailActivity.class, bundle1, false);
				break;
			case R.id.tv_content:
				Bundle bundle2 = new Bundle();
				bundle2.putLong("topicid", topic.getArticleId());
				go2ActivityWithLeft(TopicActivity.class, bundle2, false);
				break;
		}
	}
}
