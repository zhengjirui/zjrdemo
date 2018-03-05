package com.metshow.bz.module.shai.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.fragment.BaseRecycleFragment;
import com.metshow.bz.IUmengEvent;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.adapter.FindTopicListAdapter;
import com.metshow.bz.commons.bean.FollowItem;
import com.metshow.bz.commons.bean.RecommendUser;
import com.metshow.bz.commons.bean.article.ArticlePicture;
import com.metshow.bz.commons.bean.article.Banner;
import com.metshow.bz.commons.bean.topic.FindTag;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.activity.CommentActivity;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.metshow.bz.module.commons.activity.ImageActivity;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.module.shai.TopicActivity;
import com.metshow.bz.presenter.FindItemPresenter;
import com.metshow.bz.presenter.FollowPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.tencent.smtt.sdk.VideoActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindItemFragment extends BaseRecycleFragment implements BaseQuickAdapter.OnRecyclerViewItemClickListener, BaseQuickAdapter.OnRecyclerViewItemChildClickListener, BZContract.IFindItemFragmentView, BZContract.IFollowView {

	private int mFragmentId;
	private FindTag mTag;
	private FindItemPresenter mPresenter;
	private FollowPresenter mFollowPresenter;
	private ArrayList<Topic> mTopics = new ArrayList<>();
	private boolean isLoadMore;
	private boolean isRec = false;

	public static FindItemFragment newInstance(int id, FindTag tag) {
		FindItemFragment fragment = new FindItemFragment();
		Bundle args = new Bundle();
		args.putInt("id", id);
		args.putSerializable("tag", tag);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {

			mFragmentId = getArguments().getInt("id");
			mTag = (FindTag) getArguments().getSerializable("tag");
			isRec = mFragmentId == 0;
			mPresenter = new FindItemPresenter(this);
			mFollowPresenter = new FollowPresenter(this);
			App.umengEvent(mContext, IUmengEvent.CommunityInsertDiscoverClick, mTag.getName());
			initRecycleView();
		}
	}

	private void initRecycleView() {

		mRecyclerView.setBackgroundColor(Color.WHITE);
//		int dp = ViewUtil.dip2px(mContext, 5);
//		mRecyclerView.addItemDecoration(new SpacesItemDecoration(dp));
//		mRecyclerView.setPadding(dp, dp, dp, dp);

		if (isRec) {
			FindTopicListAdapter adapter = (FindTopicListAdapter) getAdapter();
			adapter.setFullSpanIndex(new int[]{0, 1});
			mRecyclerView.setAdapter(adapter);
		}

	}

	@Override
	public void onRefresh() {
		super.onRefresh();
		isLoadMore = false;
		mTopics.clear();
		if (isRec)
			//获取banner
			mPresenter.getBanner();
		else
			mPresenter.getTopics(pageIndex, 20, mTag.getName());
	}

	@Override
	public void onLoadMore() {
		super.onLoadMore();
		isLoadMore = true;
		mPresenter.getTopics(pageIndex, 20, mTag.getName());
	}

	FindTopicListAdapter adapter;

	@Override
	protected BaseQuickAdapter getAdapter() {
		adapter = new FindTopicListAdapter(mBaseActivity, mTopics);
		adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
		adapter.setOnRecyclerViewItemClickListener(this);
		adapter.setOnRecyclerViewItemChildClickListener(this);
		adapter.setFollowPresenter(mFollowPresenter);
		return adapter;
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {
		return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
	}

	@Override
	public void onItemClick(View view, int position) {

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
						mPresenter.addAcition(1, topic.getArticleId(), position);
					} else {
						//取消收藏
						showProgress("");
						mPresenter.deleteFav(3, topic.getArticleId(), position, mFragmentId);
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

	@Override
	public void getBannerSuccess(List<Banner> result) {

		Topic topic = new Topic();
		topic.setBanners(result);
		topic.setItemType(2);
		mTopics.add(topic);

		Log.e("getBannerSuccess", "getBannerSuccess");
		mPresenter.getRecommend();

	}

	private List<RecommendUser> mRecommendUsers;

	@Override
	public void getRecommendSuccess(List<RecommendUser> result) {
		mRecommendUsers = result;
		Topic topic = new Topic();
		topic.setRecommendUsers(result);
		topic.setItemType(1);
		mTopics.add(topic);

		Log.d("kwan", "getRecommendSuccess");
		mPresenter.getTopics(1, 20, mTag.getName());
	}

	@Override
	public void getTopicsSuccess(List<Topic> items) {


		if (isLoadMore) {


			((FindTopicListAdapter) mRecyclerView.getAdapter()).addData(items);
			mTopics.addAll(items);


		} else {

			mTopics.addAll(items);
			items.clear();
			items.addAll(mTopics);

			((FindTopicListAdapter) mRecyclerView.getAdapter()).setNewData(items);


		}


		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);

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
	public void getFollowListSuccess(List<FollowItem> followItems) {

	}

	@Override
	public void getBeFollowListSuccess(List<FollowItem> followItems, String serverTime) {

	}

	@Override
	public void getDeleteFollowSuccess(Boolean result, int position) {
		mRecommendUsers.get(position).setIsFollow(0);
		adapter.getUserAdapter().getItem(position).setIsFollow(0);
		adapter.getUserAdapter().notifyItemChanged(position);
		toastMsg("取消成功");
		mBaseActivity.dismissProgress();
	}

	@Override
	public void addFollowSuccess(int position) {
		mRecommendUsers.get(position).setIsFollow(1);
		adapter.getUserAdapter().getItem(position).setIsFollow(1);
		adapter.getUserAdapter().notifyItemChanged(position);
		toastMsg("关注成功");
		mBaseActivity.dismissProgress();
	}

	private void toTopicDetail(Topic topic) {

		int type = topic.getArticleType();
		Bundle bundle = new Bundle();

		switch (type) {
			//视频
			case 6:
				bundle.putString("url", topic.getVideoUrl());
				mBaseActivity.go2Activity(VideoActivity.class, bundle, false);
				break;
			//图组
			case 7:
				List<ArticlePicture> pictures = topic.getArticlePictures();
				ArrayList<String> urls = new ArrayList<>();
				ArrayList<String> titles = new ArrayList<>();
				for (ArticlePicture picture : pictures) {
					urls.add(picture.getPicPath());
					titles.add(picture.getRemark());
				}
				bundle.putStringArrayList("urls", urls);
				bundle.putStringArrayList("titles", titles);
				mBaseActivity.go2Activity(ImageActivity.class, bundle, false);
				break;
		}
	}
}
