package com.metshow.bz.module.commons.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.fragment.BaseRecycleFragment;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.FollowItem;
import com.metshow.bz.commons.bean.RecommendUser;
import com.metshow.bz.commons.bean.UserType;
import com.metshow.bz.commons.bean.article.Article;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.metshow.bz.presenter.FollowPresenter;
import com.metshow.bz.presenter.RecommendUserListPresenter;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-18.
 */

public class HotUserItemFragment extends BaseRecycleFragment implements BZContract.IRecommendUserListView, BZContract.IFollowView, BaseQuickAdapter.OnRecyclerViewItemChildClickListener, BaseQuickAdapter.OnRecyclerViewItemClickListener {

	private List<RecommendUser> mRecommendUsers = new ArrayList<>();
	private BaseQuickAdapter<RecommendUser> mAdapter;
	private RecommendUserListPresenter mPresenter;
	private FollowPresenter mFollowPresenter;
	private long mType;


	public static HotUserItemFragment newInstance(long type) {
		HotUserItemFragment fragment = new HotUserItemFragment();
		Bundle args = new Bundle();
		args.putLong("type", type);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {

			mType = getArguments().getLong("type");
			mPresenter = new RecommendUserListPresenter(this);
			mFollowPresenter = new FollowPresenter(this);


		}
	}

	@Override
	protected BaseQuickAdapter getAdapter() {

		mAdapter = new BaseQuickAdapter<RecommendUser>(mBaseActivity, R.layout.list_item_recommend_user_list, mRecommendUsers) {
			@Override
			protected void convert(BaseViewHolder helper, RecommendUser item, int position) {

				mImageUtil.loadFromUrlWithCircle(item.getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));

				helper.setText(R.id.tv_name, item.getNickName());
				((TextView) helper.getView(R.id.tv_name)).setTypeface(Typeface.DEFAULT_BOLD);

				helper.setVisible(R.id.iv_isVip, item.getIsVip() == 1);
				helper.setText(R.id.tv_rank, item.getRank());

				helper.setText(R.id.tv_follow, "关注：" + item.getFollowCount());
				helper.setText(R.id.tv_befollow, "粉丝：" + item.getBeFollowedCount());
				helper.setOnClickListener(R.id.tv_add_follow, new OnItemChildClickListener());
				if (item.getIsFollow() > 0)
					helper.setText(R.id.tv_add_follow, "已关注");
				else
					helper.setText(R.id.tv_add_follow, "关注");

				AutoLinearLayout ll_pic = helper.getView(R.id.ll_pic);
				List<Article> articles = item.getArticleList();

				if (articles != null && articles.size() > 0) {
					AutoLinearLayout.LayoutParams lp = new AutoLinearLayout.LayoutParams(App.SCREEN_WIDTH/3,App.SCREEN_WIDTH/3);
					for (int i = 0; i < articles.size(); i++) {
						if (i == 3)
							break;
						else {

							ImageView imageView = new ImageView(mBaseActivity);
							imageView.setLayoutParams(lp);
							mImageUtil.loadFromUrl(articles.get(i).getIco(),imageView);
							ll_pic.addView(imageView);
						}
					}
				}
			}
		};
		mAdapter.setOnRecyclerViewItemChildClickListener(this);
		mAdapter.setOnRecyclerViewItemClickListener(this);
		return mAdapter;
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {
		return new LinearLayoutManager(mBaseActivity);
	}

	@Override
	public void onRefresh() {
		super.onRefresh();

		isLoadMore = false;
		pageIndex = 1;
		mPresenter.getUserList(20, mType, pageIndex);
	}


	@Override
	public void onLoadMore() {
		super.onLoadMore();
		mPresenter.getUserList(20, mType, pageIndex);
	}


	@Override
	public void getUserListSuccess(List<RecommendUser> items) {
		if (isLoadMore) {
			mRecommendUsers.addAll(items);
			mAdapter.addData(items);
		} else {

			mRecommendUsers.clear();
			mRecommendUsers.addAll(items);
			mAdapter.setNewData(items);

		}

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}

	@Override
	public void getUserTypeSuccess(List<UserType> items) {

	}

	@Override
	public void onServerFailed(String s) {
		super.onServerFailed(s);

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}

	@Override
	public void onServerError(int vocational_id, Throwable e) {
		super.onServerError(vocational_id, e);

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}

	@Override
	public void onItemClick(View view, int position) {

		Bundle bundle = new Bundle();
		bundle.putLong("userid", mRecommendUsers.get(position).getUserId());
		bundle.putInt("isFollow", mRecommendUsers.get(position).getIsFollow());
		go2Activity(FriendDetailActivity.class, bundle, false);

	}


	@Override
	public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
		showProgress("");
		if (mRecommendUsers.get(position).getIsFollow() > 0) {
			mFollowPresenter.deleteFollow(mRecommendUsers.get(position).getUserId(), position);
		} else {
			mFollowPresenter.addFollow(mRecommendUsers.get(position).getUserId(), position);
		}
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
		mAdapter.notifyItemChanged(position);
		dismissProgress();

	}

	@Override
	public void addFollowSuccess(int position) {

		mRecommendUsers.get(position).setIsFollow(1);
		mAdapter.notifyItemChanged(position);
		dismissProgress();

	}
}
