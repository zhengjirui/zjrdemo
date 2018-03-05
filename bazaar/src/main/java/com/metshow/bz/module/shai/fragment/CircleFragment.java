package com.metshow.bz.module.shai.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.fragment.BaseRecycleFragment;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.SPUtil;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.TopicListAdapter;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.activity.CommentActivity;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.module.shai.TopicActivity;
import com.metshow.bz.module.shai.TopicListActivity;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.metshow.bz.presenter.CirclePresenter;
import com.metshow.bz.presenter.TopicListPresenter;
import com.metshow.bz.util.TopicMenuUtil;

import java.util.ArrayList;
import java.util.List;

public class CircleFragment extends BaseRecycleFragment implements BZContract.ICircleView, BaseQuickAdapter.OnRecyclerViewItemChildClickListener, BZContract.ITopicMenuView, BZContract.ITopicListView {

	private TopicListPresenter mTopicListPresenter;
	private CirclePresenter mCirclePresenter;
	private ArrayList<Topic> mTopics = new ArrayList<>();
	private boolean isLogin;
	private boolean isLoadMore;
	private TopicListAdapter mAdapter;

	public CircleFragment() {
	}

	public static CircleFragment newInstance() {
		CircleFragment fragment = new CircleFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mCirclePresenter = new CirclePresenter(this);
			mTopicListPresenter = new TopicListPresenter(this);
			fetchData();
		}
	}

	@Override
	protected BaseQuickAdapter getAdapter() {
		mAdapter = new TopicListAdapter(mBaseActivity,mTopics);
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
		return new LinearLayoutManager(mBaseActivity);
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

	@Override
	public void addBannerFollowSuccess(String result, int position) {
	}

	@Override
	public void onRefresh() {
		super.onRefresh();
		isLogin = SPUtil.getIsLogin();
		mCirclePresenter.getCircleTopic(isLogin,"0",20,pageIndex);
	}

	@Override
	public void onLoadMore() {
		super.onLoadMore();
		isLoadMore = true;
		mCirclePresenter.getCircleTopic(isLogin, BasePresenter.getData(mTopics.get(mTopics.size() - 1).getPublishDate()), 20, pageIndex);
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
				new TopicMenuUtil(mBaseActivity, topic, CircleFragment.this, position).showShareDialog();
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

			mAdapter.notifyDataSetChanged();
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
			mAdapter.remove(position);
			mAdapter.notifyItemRemoved(position);
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
			mAdapter.remove(position);
			mAdapter.notifyItemRemoved(position);
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

		mAdapter.getItem(position).setIsFav(1);
		mAdapter.notifyItemChanged(position);
		mBaseActivity.dismissProgress();
	}

	@Override
	public void deleteFavSuccess(String result, int position) {
		mAdapter.getItem(position).setIsFav(0);
		mAdapter.notifyItemChanged(position);
		mBaseActivity.dismissProgress();
	}

	@Override
	public void addFollowSuccess(String result, int position) {

		mTopics.get(position).setIsFollow(1);
		mAdapter.notifyDataSetChanged();
		toastMsg("已关注");
		mBaseActivity.dismissProgress();
	}
}
