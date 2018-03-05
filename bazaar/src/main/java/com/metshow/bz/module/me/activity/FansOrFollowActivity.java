package com.metshow.bz.module.me.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kwan.base.activity.CommonRecycleActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.FollowItem;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.metshow.bz.presenter.FollowPresenter;
import com.metshow.bz.util.BZSPUtil;

import java.util.ArrayList;
import java.util.List;


public class FansOrFollowActivity extends CommonRecycleActivity implements BaseQuickAdapter.OnRecyclerViewItemClickListener, BaseQuickAdapter.OnRecyclerViewItemChildClickListener, BZContract.IFollowView {

	public static String MODE = "mode";
	public static final int MODE_FANS = 1;
	public static final int MODE_FOLLOW = 2;
	private int mode = MODE_FANS;
	private ArrayList<FollowItem> mFollowItems = new ArrayList<>();
	private BaseQuickAdapter<FollowItem> mFollowAdapter;
	private FollowPresenter mPresenter;
	private boolean isMaxData = false;
	private boolean isLoadMore = false;

	@Override
	protected BaseQuickAdapter getAdapter() {

		if (mode == MODE_FOLLOW) {
			mFollowAdapter = new BaseQuickAdapter<FollowItem>(this, R.layout.list_item_follow, mFollowItems) {
				@Override
				protected void convert(BaseViewHolder helper, FollowItem item, int position) {
					helper.setText(R.id.tv_name, item.getNickName());
					helper.setText(R.id.tv_info,item.getDescription());
					helper.setImageUrl(R.id.iv_avatar, item.getAvatar(), R.mipmap.title_user_icon);
					if (item.getIsVip() == 1)
						helper.getView(R.id.iv_isVip).setVisibility(View.VISIBLE);
					else
						helper.getView(R.id.iv_isVip).setVisibility(View.GONE);
					helper.setOnClickListener(R.id.tv_delete, new OnItemChildClickListener());
				}
			};
		} else if (mode == MODE_FANS) {
			mFollowAdapter = new BaseQuickAdapter<FollowItem>(this, R.layout.list_item_follow, mFollowItems) {
				@Override
				protected void convert(BaseViewHolder helper, FollowItem item, int position) {

					helper.setText(R.id.tv_name, item.getNickName());
					helper.setImageUrl(R.id.iv_avatar, item.getAvatar(), R.mipmap.title_user_icon);
					if (item.getIsVip() == 1)
						helper.getView(R.id.iv_isVip).setVisibility(View.VISIBLE);
					else
						helper.getView(R.id.iv_isVip).setVisibility(View.GONE);

					if (item.getIsFollow() > 0) {
						helper.setBackgroundRes(R.id.tv_delete, R.drawable.shape_corner_gray_bg);
						helper.setTextColor(R.id.tv_delete, Color.WHITE);
						helper.setText(R.id.tv_delete, "已关注");
					} else {
						helper.setBackgroundRes(R.id.tv_delete, R.drawable.shape_red_bg);
						helper.setTextColor(R.id.tv_delete, getResources().getColor(R.color.redWin));
						helper.setText(R.id.tv_delete, "关 注");
					}
					//helper.setOnClickListener(R.id.tv_delete, new OnItemChildClickListener());
					helper.setOnClickListener(R.id.tv_delete, new OnItemChildClickListener());
				}
			};
		}

		mFollowAdapter.setOnRecyclerViewItemClickListener(this);
		mFollowAdapter.setOnRecyclerViewItemChildClickListener(this);
		return mFollowAdapter;
	}

	@Override
	protected RecyclerView.LayoutManager getLayoutManager() {
		return new LinearLayoutManager(this);
	}

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mode = (int) getIntentData(MODE);
		mPresenter = new FollowPresenter(this);
	}

	@Override
	protected String getTitleTxt() {
		switch (mode) {
			case MODE_FANS:
				return "粉丝";
			case MODE_FOLLOW:
				return "关注";
			default:
				return null;
		}
	}

	@Override
	public void onRefresh() {
		super.onRefresh();
		isLoadMore = false;
		isMaxData = false;

		if (mode == MODE_FOLLOW)
			mPresenter.getFollowList(pageIndex, 20);
		else if (mode == MODE_FANS)
			mPresenter.getBeFollowList(pageIndex, 20);
	}

	@Override
	public void onLoadMore() {
		super.onLoadMore();

		if (!isMaxData) {
			isLoadMore = true;
			mPresenter.getFollowList(pageIndex, 20);
		} else {
			swipeRefreshLayout.setLoadingMore(false);
		}
	}


	@Override
	public void getFollowListSuccess(List<FollowItem> followItems) {

		if (followItems.size() == 0 && isLoadMore == false)
			setNoItem(true);
		else
			setNoItem(false);

		if (followItems.size() == 0)
			isMaxData = true;
		else {
			if (isLoadMore) {
				mFollowItems.addAll(followItems);
				mFollowAdapter.addData(followItems);
			} else {
				mFollowItems.clear();
				mFollowItems.addAll(followItems);
				mFollowAdapter.setNewData(followItems);
			}
		}

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}

	@Override
	public void getBeFollowListSuccess(List<FollowItem> followItems, String serverTime) {
		BZSPUtil.setFollowedDate(BasePresenter.getData(serverTime).split("\\+")[0]);
		getFollowListSuccess(followItems);
	}

	@Override
	public void getDeleteFollowSuccess(Boolean result, int position) {
		mFollowAdapter.remove(position);
		mFollowItems.remove(position);
		dismissProgress();
	}

	@Override
	public void addFollowSuccess(int position) {
		mFollowItems.get(position).setIsFollow(1);
		mFollowAdapter.getItem(position).setIsFollow(1);
		mFollowAdapter.notifyItemChanged(position);
		dismissProgress();
		toastMsg("关注成功");
	}

	@Override
	public void onItemClick(View view, int position) {
		Bundle bundle = new Bundle();
		long userid = 0;
		if (mode == MODE_FOLLOW) {
			userid = mFollowItems.get(position).getFollowUserId();
		} else if (mode == MODE_FANS) {
			userid = mFollowItems.get(position).getUserId();
		}
		bundle.putLong("userid", userid);
		bundle.putInt("isFollow", mFollowItems.get(position).getIsFollow());
		go2ActivityWithLeft(FriendDetailActivity.class, bundle, false);
	}

	@Override
	public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
		if (mode == MODE_FOLLOW) {
			//点击取消关注
			showDeleteFollowDialog(position);
		} else if (mode == MODE_FANS) {
			//粉丝 点击关注
			if (mFollowItems.get(position).getIsFollow() == 0) {
				dismissProgress();
				mPresenter.addFollow(mFollowItems.get(position).getUserId(), position);
			}else{
				showDeleteFollowDialog(position);
			}
		}
	}

	private void showDeleteFollowDialog(final int position) {

		final Dialog dialog = new Dialog(this, R.style.BaseDialog);
		View view = getLayoutInflater().inflate(R.layout.txt_dialog_layout, null);

		TextView sure_btn = (TextView) view.findViewById(R.id.tv_ok);
		TextView cancel_btn = (TextView) view.findViewById(R.id.tv_cancel);
		TextView tv_txt = (TextView) view.findViewById(R.id.tv_txt);

		tv_txt.setText("是否取消关注");

		sure_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (dialog.isShowing())
					dialog.dismiss();

				showProgress("正在取消..");
				mPresenter.deleteFollow(mFollowItems.get(position).getFollowUserId(), position);


			}
		});
		cancel_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (dialog.isShowing())
					dialog.dismiss();
			}
		});

		int dialog_width = (int) (App.SCREEN_WIDTH * 9 / 10.0);
		dialog.setContentView(view, new LinearLayout.LayoutParams(dialog_width, -2));
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
}
