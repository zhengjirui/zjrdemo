package com.metshow.bz.module.commons.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.SPUtil;
import com.kwan.base.util.ViewUtil;
import com.kwan.base.widget.SpacesItemDecoration;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.FindTopicListAdapter;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.BaseBZRecycleFragment;
import com.metshow.bz.module.commons.activity.CommentActivity;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.module.shai.TopicActivity;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.metshow.bz.presenter.FavTopicPresenter;
import com.metshow.bz.presenter.TopicListPresenter;
import com.metshow.bz.util.TopicMenuUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mr.Kwan on 2017-2-24.
 */

public class TopicListFragment extends BaseBZRecycleFragment implements BZContract.IFavTopicView, BaseQuickAdapter.OnRecyclerViewItemChildClickListener, BZContract.ITopicListView, BZContract.ITopicMenuView {

	private ArrayList<Topic> mTopics = new ArrayList<>();
	private String MaxDate = "0";
	private FavTopicPresenter mPresenter;
	private FindTopicListAdapter mTopicAdapter;
	private TopicListPresenter mTopicListPresenter;

	public static TopicListFragment newInstance() {

		TopicListFragment fragment = new TopicListFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPresenter = new FavTopicPresenter(this);
		mTopicListPresenter = new TopicListPresenter(this);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		int dp = ViewUtil.dip2px(mBaseActivity, 2.5f);
		mRecyclerView.addItemDecoration(new SpacesItemDecoration(dp));
		mRecyclerView.setPadding(dp, dp, dp, dp);
	}

	@Override
	protected BaseQuickAdapter getAdapter() {
		mTopicAdapter = new FindTopicListAdapter(mBaseActivity, mTopics);
		mTopicAdapter.setOnRecyclerViewItemChildClickListener(this);
		return mTopicAdapter;
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {

		StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		layoutManager.setAutoMeasureEnabled(true);
		return layoutManager;
	}


	@Override
	public void onRefresh() {
		super.onRefresh();
		MaxDate = "0";
		mPresenter.getFavTopic(10, MaxDate);
	}

	@Override
	public void onLoadMore() {
		super.onLoadMore();
		mPresenter.getFavTopic(10, BasePresenter.getData(mTopics.get(mTopics.size() - 1).getPublishDate()));
	}

	@Override
	public void getFavTopicSuccess(List<Topic> topics) {

		if (topics.size() > 0) {

			if (isLoadMore) {
				mTopics.addAll(topics);
				mTopicAdapter.addData(topics);
			} else {
				mTopics.clear();
				mTopics.addAll(topics);
				mTopicAdapter.setNewData(topics);
			}

			swipeRefreshLayout.setRefreshing(false);
			swipeRefreshLayout.setLoadingMore(false);
		}
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
					mBaseActivity.showProgress("");
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
					mBaseActivity.showProgress("");
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
				new TopicMenuUtil(mBaseActivity, topic, TopicListFragment.this, position).showShareDialog();
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

	private void go2UserDetail(int position) {

		Bundle bundle = new Bundle();
		bundle.putLong("userid", mTopics.get(position).getUserId());
		bundle.putInt("isFollow", mTopics.get(position).getIsFollow());
		go2ActivityWithLeft(FriendDetailActivity.class, bundle, false);

	}

	//取消关注
	@Override
	public void deleteFollowSuccess(Boolean result, int position) {

		if (result) {
			mTopics.get(position).setIsFollow(0);

			mTopicAdapter.notifyDataSetChanged();
			toastMsg("已取消关注");
		} else {
			toastMsg("取消失败");
		}
		mBaseActivity.dismissProgress();
	}

	//屏蔽
	@Override
	public void disViewSuccess(int result, int position) {

		if (result == 1) {
			mTopicAdapter.remove(position);
			mTopicAdapter.notifyItemRemoved(position);
			toastMsg("已屏蔽");
		} else {
			toastMsg("屏蔽失败");
		}
		mBaseActivity.dismissProgress();
	}

	//举报
	@Override
	public void reportSuccess(Boolean result, int position) {
		if (result) {
			toastMsg("举报成功");
		} else {
			toastMsg("举报失败");
		}
		mBaseActivity.dismissProgress();

	}

	//删除
	@Override
	public void deleteTopicSuccess(Boolean result, int position) {
		if (result) {
			mTopicAdapter.remove(position);
			mTopicAdapter.notifyItemRemoved(position);
			toastMsg("已删除");
		} else {
			toastMsg("删除失败");
		}
		mBaseActivity.dismissProgress();
	}

	@Override
	public void getUserTopicsSuccess(List<Topic> items) {

	}

	@Override
	public void favSuccess(String result, int position) {

		mTopicAdapter.getItem(position).setIsFav(1);
		mTopicAdapter.notifyItemChanged(position);
		mBaseActivity.dismissProgress();
	}

	@Override
	public void deleteFavSuccess(String result, int position) {
		mTopicAdapter.getItem(position).setIsFav(0);
		mTopicAdapter.notifyItemChanged(position);
		mBaseActivity.dismissProgress();
	}

	@Override
	public void addFollowSuccess(String result, int position) {

		mTopics.get(position).setIsFollow(1);
		mTopicAdapter.notifyDataSetChanged();
		toastMsg("已关注");
		mBaseActivity.dismissProgress();
	}
}
