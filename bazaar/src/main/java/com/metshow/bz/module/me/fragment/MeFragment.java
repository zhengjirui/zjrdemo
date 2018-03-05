package com.metshow.bz.module.me.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kwan.base.fragment.BasePageItemFragment;
import com.kwan.base.widget.CircularImage;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.me.activity.FansOrFollowActivity;
import com.metshow.bz.module.me.activity.FavoriteActivity;
import com.metshow.bz.module.me.activity.MyActivityActivity;
import com.metshow.bz.module.me.activity.MyCommentActivity;
import com.metshow.bz.module.me.activity.MyTopicActivity;
import com.metshow.bz.module.me.activity.SettingActivity;
import com.metshow.bz.module.me.activity.UserInfoEditActivity;
import com.metshow.bz.module.point.activity.PointProductListActivity;
import com.metshow.bz.presenter.FriendDetailPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.metshow.bz.util.ShareUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

import static com.metshow.bz.R.id.ll_activities;

/**
 * Created by Mr.Kwan on 2017-2-21.
 */

public class MeFragment extends BasePageItemFragment implements BZContract.IFriendDetailView {

	private CircularImage iv_avatar, iv_isVip;
	private TextView tv_name, tv_point, tv_topic_num, tv_follow_num, tv_fans_num, tv_favourite_num, tv_des;
	private FriendDetailPresenter mPresenter;

	public static MeFragment newInstance() {
		MeFragment fragment = new MeFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void fetchData() {
		showProgress("");
		//getUserInfoSuccess(BZSPUtil.getUser());
		mPresenter.getUserInfo(BZSPUtil.getUser().getUserId() + "");
	}

	@Override
	public void onResume() {
		super.onResume();
		fetchData();
	}

	@Override
	protected int getContentViewId() {
		return R.layout.fragment_me;
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPresenter = new FriendDetailPresenter(this);

		AutoLinearLayout ll_user_detail_header = (AutoLinearLayout) mContentView.findViewById(R.id.ll_user_detail_header);
		AutoLinearLayout ll_topic_num = (AutoLinearLayout) mContentView.findViewById(R.id.ll_topic_num);
		AutoLinearLayout ll_follow_num = (AutoLinearLayout) mContentView.findViewById(R.id.ll_follow_num);
		AutoLinearLayout ll_fans_num = (AutoLinearLayout) mContentView.findViewById(R.id.ll_fans_num);
		AutoLinearLayout ll_favourite_num = (AutoLinearLayout) mContentView.findViewById(R.id.ll_favourite_num);

		AutoLinearLayout ll_activities = (AutoLinearLayout) mContentView.findViewById(R.id.ll_activities);
		AutoLinearLayout ll_points = (AutoLinearLayout) mContentView.findViewById(R.id.ll_points);
		AutoLinearLayout ll_setting = (AutoLinearLayout) mContentView.findViewById(R.id.ll_setting);
		AutoLinearLayout ll_share = (AutoLinearLayout) mContentView.findViewById(R.id.ll_share);
		AutoLinearLayout ll_my = (AutoLinearLayout) mContentView.findViewById(R.id.ll_my);
		iv_avatar = (CircularImage) mContentView.findViewById(R.id.iv_avatar);
		iv_isVip = (CircularImage) mContentView.findViewById(R.id.iv_isVip);

		tv_name = (TextView) mContentView.findViewById(R.id.tv_name);
		tv_point = (TextView) mContentView.findViewById(R.id.tv_point);
		tv_topic_num = (TextView) mContentView.findViewById(R.id.tv_topic_num);
		tv_follow_num = (TextView) mContentView.findViewById(R.id.tv_follow_num);
		tv_fans_num = (TextView) mContentView.findViewById(R.id.tv_fans_num);
		tv_favourite_num = (TextView) mContentView.findViewById(R.id.tv_favourite_num);
		tv_des = (TextView) mContentView.findViewById(R.id.tv_description);

		ll_user_detail_header.setOnClickListener(this);
		ll_topic_num.setOnClickListener(this);
		ll_follow_num.setOnClickListener(this);
		ll_fans_num.setOnClickListener(this);
		ll_favourite_num.setOnClickListener(this);

		ll_activities.setOnClickListener(this);
		ll_points.setOnClickListener(this);
		ll_setting.setOnClickListener(this);
		ll_share.setOnClickListener(this);
		ll_my.setOnClickListener(this);
		//	mImageUtil.loadFromResWithCircle(R.mipmap.default_user_icon, (ImageView) mContentView.findViewById(R.id.iv_avatar));
		//	mImageUtil.loadFromResWithCircle(R.mipmap.icon_vip, (ImageView) mContentView.findViewById(R.id.iv_isVip));
	}



	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
			case R.id.ll_user_detail_header:
				go2ActivityWithLeft(UserInfoEditActivity.class, null, false);
				break;
			case R.id.ll_topic_num:
				go2ActivityWithLeft(MyTopicActivity.class, null, false);
				break;
			case R.id.ll_follow_num:
				Bundle bundle_follow = new Bundle();
				bundle_follow.putInt(FansOrFollowActivity.MODE, FansOrFollowActivity.MODE_FOLLOW);
				go2ActivityWithLeft(FansOrFollowActivity.class, bundle_follow, false);
				break;
			case R.id.ll_fans_num:
				Bundle bundle_fans = new Bundle();
				bundle_fans.putInt(FansOrFollowActivity.MODE, FansOrFollowActivity.MODE_FANS);
				go2ActivityWithLeft(FansOrFollowActivity.class, bundle_fans, false);
				break;
			case R.id.ll_favourite_num:
				go2ActivityWithLeft(FavoriteActivity.class, null, false);
				break;
			case ll_activities:
				go2ActivityWithLeft(MyActivityActivity.class, null, false);
				break;
			case R.id.ll_points:
				go2ActivityWithLeft(PointProductListActivity.class, null, false);
				break;
			case R.id.ll_share:
				ShareUtil shareUtil = new ShareUtil(mBaseActivity);
				shareUtil.showShareDialog();
				break;
			case R.id.ll_setting:
				go2ActivityWithLeft(SettingActivity.class, null, false);
				break;
			case R.id.ll_my:
				go2ActivityWithLeft(MyCommentActivity.class, null, false);
				break;
		}
	}

	@Override
	public void getUserInfoSuccess(User user) {

		user.setToken(BZSPUtil.getUser().getToken());

		BZSPUtil.setUser(user);

		if (mImageUtil != null)
			mImageUtil.loadFromUrl(user.getAvatar(), iv_avatar);

		if (user.getIsVip() == 1)
			iv_isVip.setVisibility(View.VISIBLE);
		else
			iv_isVip.setVisibility(View.GONE);
		tv_name.setText(user.getNickName());

		tv_des.setText(user.getDescription());
		tv_point.setText("积分：" + user.getPoints());

		tv_topic_num.setText(user.getArticleCount() + "");
		tv_follow_num.setText(user.getFollowCount() + "");
		tv_fans_num.setText(user.getBeFollowedCount() + "");
		tv_favourite_num.setText(user.getFavCount() + "");

		Log.e("kwan","art"+user.getArticleCount());


		dismissProgress();
	}

	@Override
	public void getUserTopicsSuccess(List<Topic> topics) {

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

	}

	@Override
	public void deleteFavSuccess(String result, int type, int position, int fragmentId) {

	}

	@Override
	public void reportSuccess(Boolean result, int position) {

	}
}
