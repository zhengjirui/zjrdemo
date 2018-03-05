package com.metshow.bz.module.in.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.fragment.BaseRecycleFragment;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.IUmengEvent;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.adapter.ArticleListAdapter;
import com.metshow.bz.commons.bean.Adforrec;
import com.metshow.bz.commons.bean.Channel;
import com.metshow.bz.commons.bean.FollowItem;
import com.metshow.bz.commons.bean.article.Article;
import com.metshow.bz.commons.bean.article.Banner;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.FollowPresenter;
import com.metshow.bz.presenter.InItemFragmentPresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.chanleId
 */
public class 	InItemFragment extends BaseRecycleFragment implements BZContract.IInItemFragmentView, BZContract.IFollowView {

	private static final String CHANNEL = "channel";
	private InItemFragmentPresenter mPresenter;
	private Channel mChannel;
	private long mChanelId;
	private boolean isRec;
	private List<Article> mDatas = new ArrayList<>();
	private ArticleListAdapter mAdapter;
	private FollowPresenter mFollowPresenter;

	public InItemFragment() {
	}

	public static InItemFragment newInstance(Channel channel) {
		InItemFragment fragment = new InItemFragment();
		Bundle args = new Bundle();
		args.putSerializable(CHANNEL, channel);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mChannel = (Channel) getArguments().getSerializable(CHANNEL);
			mChanelId = mChannel.getChannelId();
			isRec = mChanelId == 20;
			mPresenter = new InItemFragmentPresenter(this);
			App.umengEvent(mContext, IUmengEvent.ArticleChannelBrowse, mChannel.getChannelName());
			//mBannerView = mBaseActivity.getLayoutInflater().inflate(, mRecyclerView, false);
		}

		Log.d(getTag(), "InItemFragment:onCreate -- chanel id::" + mChannel.getChannelId());
	}

	@Override
	protected BaseQuickAdapter getAdapter() {

		mAdapter = new ArticleListAdapter(mBaseActivity, mDatas);
		mFollowPresenter = new FollowPresenter(this);
		mAdapter.setFollowPresenter(mFollowPresenter);
		return mAdapter;
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {
		return new LinearLayoutManager(mContext);
	}

	@Override
	public void onRefresh() {
		super.onRefresh();
		mPresenter.getArticleBanner(mChanelId);
	}

	@Override
	public void onLoadMore() {
		super.onLoadMore();
		if (isRec)
			mPresenter.getRecList(pageIndex, 20);
		else
			mPresenter.getArticles(mChannel.getChannelId(), 20,
					BasePresenter.getData(mDatas.get(mDatas.size() - 1).getPublishDate()),true);
	}

	@Override
	public void getBannerSuccess(List<Banner> result) {

		mDatas = new ArrayList<>();

		if (result != null && result.size() > 0) {

			Article banner = new Article();
			banner.setBanners(result);
			banner.setItemType(8);
			mDatas.add(banner);

		}
		if (isRec)
			mPresenter.getInRecommend();//获取推荐用户
		else
			mPresenter.getArticles(mChanelId, 20, "0",false);//获取文章

	}

	@Override
	public void getArticlesSuccess(List<Article> items) {

		mDatas.addAll(items);

		if (pageIndex == 1)
			mAdapter.setNewData(mDatas);
		else
			mAdapter.addData(items);
		//mAdapter.setNewData(mDatas);
		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);

	}

	Adforrec mAdforrec;

	@Override
	public void getInRecommendSuccess(Adforrec result) {
		mAdforrec = result;
		mPresenter.getRecList(pageIndex, 20);
	}

	@Override
	public void showRecListData(List<Article> articles) {

		if(pageIndex == 1) {

			mDatas.addAll(articles);

			if ( mAdforrec != null && mAdforrec.getAduser() != null && mAdforrec.getAduser().size() > 0) {
				Article ad = new Article();
				ad.setAdforrecs(mAdforrec);
				ad.setItemType(7);
				mDatas.add(2, ad);
			}
			mAdapter.setNewData(mDatas);
		}
		else
			mAdapter.addData(articles);

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);

	}

	@Override
	public void getFollowListSuccess(List<FollowItem> followItems) {

	}

	@Override
	public void getBeFollowListSuccess(List<FollowItem> followItems, String serverTime) {

	}

	@Override
	public void getDeleteFollowSuccess(Boolean result,int position) {

		mAdforrec.getAduser().get(position).setIsFollow(0);
		mAdapter.getRecmmendAdapter().getItem(position).setIsFollow(0);

		mAdapter.getRecmmendAdapter().notifyItemChanged(position);
		toastMsg("取消成功");
		mBaseActivity.dismissProgress();

	}

	@Override
	public void addFollowSuccess(int position) {

		mAdforrec.getAduser().get(position).setIsFollow(1);
		mAdapter.getRecmmendAdapter().getItem(position).setIsFollow(1);
		mAdapter.getRecmmendAdapter().notifyItemChanged(position);
		toastMsg("关注成功");
		mBaseActivity.dismissProgress();

	}

}
