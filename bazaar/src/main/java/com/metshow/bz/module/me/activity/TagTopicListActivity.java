package com.metshow.bz.module.me.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kwan.base.activity.CommonRecycleActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.util.SPUtil;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.TopicListAdapter;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.activity.CommentActivity;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.module.shai.TopicActivity;
import com.metshow.bz.module.shai.TopicListActivity;
import com.metshow.bz.presenter.TagTopicListPresenter;
import com.metshow.bz.presenter.TopicListPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.metshow.bz.util.TopicMenuUtil;

import java.util.ArrayList;
import java.util.List;

public class TagTopicListActivity extends CommonRecycleActivity implements BaseQuickAdapter.OnRecyclerViewItemChildClickListener, BZContract.ITopicListView, BZContract.ITopicMenuView, BZContract.ITagTopicListView {


	private Tag mTag;
	private TopicListPresenter mTopicListPresenter;
	private TagTopicListPresenter mTagTopicListPresenter;
	private ArrayList<Topic> mTopics = new ArrayList<>();
	private TopicListAdapter mAdapter;
	private boolean isLoadMore;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mTag = (Tag) getIntentData("tag");
		mTopicListPresenter = new TopicListPresenter(this);
		mTagTopicListPresenter = new TagTopicListPresenter(this);
	}

	@Override
	protected String getTitleTxt() {
		return mTag.getName();
	}

	@Override
	protected BaseQuickAdapter getAdapter() {

		mAdapter = new TopicListAdapter(this,mTopics);
		mAdapter.setOnRecyclerViewItemChildClickListener(this);
		mAdapter.setTagClickListener(new TopicListAdapter.TagClickListener() {
			@Override
			public void onTagClick(String tag, int indextag, int position) {

				Bundle bundle = new Bundle();
				bundle.putInt("type", TopicListActivity.MODE_TAG);
				bundle.putString("tag", tag);
				go2ActivityWithLeft(TopicListActivity.class, bundle, false);

			}
		});
		return mAdapter;
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {
		return new LinearLayoutManager(this);
	}

	@Override
	public void onRefresh() {
		super.onRefresh();
		isLoadMore = false;
		mTagTopicListPresenter.getTopics(1,20,mTag.getTag(), BZSPUtil.getUser().getUserId());
	}

	@Override
	public void onLoadMore() {
		super.onLoadMore();
		isLoadMore = true;
	}

	@Override
	public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
		Topic topic = mTopics.get(position);
		switch (view.getId()) {
			case R.id.iv_avatar:
				go2UserDetail(position);
				break;
			case R.id.tv_name:
				go2UserDetail(position);
				break;
			case R.id.tv_follow:
				if (SPUtil.getIsLogin()) {
					mTopicListPresenter.addFollow(mTopics.get(position).getUserId(), position);
					showProgress("");
				} else {
					go2Activity(DologinActivity.class, null, false);
				}
				break;
			case R.id.iv_icon:
				// toTopicDetail(topic);
				Bundle bundle = new Bundle();
				bundle.putLong("topicid", topic.getArticleId());
				go2Activity(TopicActivity.class, bundle, false);
				break;
			case R.id.iv_fav:

				if (SPUtil.getIsLogin()) {

					if (topic.getIsFav() == 1)
						mTopicListPresenter.deleteFav(topic.getArticleId(), position);
					else
						mTopicListPresenter.addAcition(1, topic.getArticleId(), position);
					showProgress("");
				} else {
					go2Activity(DologinActivity.class, null, false);
				}
				break;
			case R.id.iv_pl:

				if (SPUtil.getIsLogin()) {

					Bundle bundlep = new Bundle();
					bundlep.putInt("type", CommentActivity.TYPE_TOPIC);
					bundlep.putLong("refid", topic.getArticleId());
					go2ActivityWithLeft(CommentActivity.class, bundlep, false);

				} else {
					go2Activity(DologinActivity.class, null, false);
				}
				break;
			case R.id.iv_more:
				new TopicMenuUtil(this, topic, this, position).showShareDialog();
				break;
			case R.id.ll_comment:
				Bundle bundle1 = new Bundle();
				bundle1.putInt("type", CommentActivity.TYPE_TOPIC);
				bundle1.putLong("refid", topic.getArticleId());
				go2ActivityWithLeft(CommentActivity.class, bundle1, false);
				break;
			default:
				Bundle bundle2 = new Bundle();
				bundle2.putLong("topicid", topic.getArticleId());
				go2Activity(TopicActivity.class, bundle2, false);
				break;
		}
	}

	@Override
	public void getUserTopicsSuccess(List<Topic> items) {

	}

	@Override
	protected int getTitleBarRightLayoutId() {
		return R.layout.title_right_follow;
	}

	@Override
	protected void setUpTitleRightView(View v) {
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(60, 60);
		lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		lp.addRule(RelativeLayout.RIGHT_OF, R.id.tv_title);
		lp.leftMargin = 10;
		lp.bottomMargin = 20;
		v.setLayoutParams(lp);
		((ImageView)v).setImageResource(R.mipmap.topic_tag);
	}

	@Override
	public void favSuccess(String result, int position) {

		mAdapter.getItem(position).setIsFav(1);
		mAdapter.notifyItemChanged(position);
		dismissProgress();
	}

	@Override
	public void deleteFavSuccess(String result, int position) {
		mAdapter.getItem(position).setIsFav(0);
		mAdapter.notifyItemChanged(position);
		dismissProgress();
	}

	@Override
	public void addFollowSuccess(String result, int position) {

		mTopics.get(position).setIsFollow(1);
		mAdapter.notifyDataSetChanged();
		toastMsg("已关注");
		dismissProgress();
	}

	//取消关注
	@Override
	public void deleteFollowSuccess(Boolean result, int position) {

		if (result) {
			mTopics.get(position).setIsFollow(0);
			mAdapter.notifyDataSetChanged();
			toastMsg("已取消关注");
		} else {
			toastMsg("取消失败");
		}
		dismissProgress();
	}

	//屏蔽
	@Override
	public void disViewSuccess(int result, int position) {

		if (result == 1) {
			mAdapter.remove(position);
			mAdapter.notifyItemRemoved(position);
			toastMsg("已屏蔽");
		} else {
			toastMsg("屏蔽失败");
		}
		dismissProgress();
	}

	//举报
	@Override
	public void reportSuccess(Boolean result, int position) {
		if (result) {
			toastMsg("举报成功");
		} else {
			toastMsg("举报失败");
		}
		dismissProgress();

	}

	//删除
	@Override
	public void deleteTopicSuccess(Boolean result, int position) {
		if (result) {
			mAdapter.remove(position);
			mAdapter.notifyItemRemoved(position);
			toastMsg("已删除");
		} else {
			toastMsg("删除失败");
		}
		dismissProgress();
	}

	private void go2UserDetail(int position) {

		Bundle bundle = new Bundle();
		bundle.putLong("userid", mTopics.get(position).getUserId());
		bundle.putInt("isFollow", mTopics.get(position).getIsFollow());
		go2ActivityWithLeft(FriendDetailActivity.class, bundle, false);

	}

	@Override
	public void getTopicSuccess(List<Topic> items) {

		if (isLoadMore) {
			mAdapter.addData(items);
			mTopics.addAll(items);

		} else {
			mAdapter.setNewData(items);
			mTopics.clear();
			mTopics.addAll(items);
		}

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}
}
