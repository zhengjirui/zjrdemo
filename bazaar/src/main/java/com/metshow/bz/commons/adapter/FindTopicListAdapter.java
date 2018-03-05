package com.metshow.bz.commons.adapter;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kwan.base.activity.BaseActivity;
import com.kwan.base.adatper.SizeModel;
import com.kwan.base.adatper.StaggeredGridAdapter;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.util.ImageUtil;
import com.kwan.base.util.SPUtil;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.metshow.bz.IUmengEvent;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.RecommendUser;
import com.metshow.bz.commons.bean.Tag;
import com.metshow.bz.commons.bean.article.Banner;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.module.commons.activity.BannerTopicActivity;
import com.metshow.bz.module.commons.activity.HotUserActivity;
import com.metshow.bz.module.commons.activity.WebActivity;
import com.metshow.bz.module.in.activity.ArticleActivity;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.metshow.bz.module.me.activity.TagTopicListActivity;
import com.metshow.bz.presenter.FollowPresenter;
import com.tencent.smtt.sdk.VideoActivity;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mr.Kwan on 2017-2-24.
 */

public class FindTopicListAdapter extends StaggeredGridAdapter<Topic> {

	//保存长宽
	private List<SizeModel> sizeModels = new ArrayList<>();
	private final ImageUtil imageUtil;
	//private final int width;
	private BaseActivity mBaseActivity;
	private FollowPresenter followPresenter;

	public void setFollowPresenter(FollowPresenter followPresenter) {
		this.followPresenter = followPresenter;
	}

	public FindTopicListAdapter(BaseActivity baseActivity, List<Topic> data) {
		super(baseActivity, data);

		mBaseActivity = baseActivity;
		//1为话题 2为投票 3为活动 4为连接 5主题 6视频 7为图组
		addItmeType(0, R.layout.shai_find_topic_item);
		addItmeType(1, R.layout.layout_recommend_recycle_find);
		addItmeType(2, R.layout.layout_banner_find);
		addItmeType(3, R.layout.layout_tag);

		imageUtil = new ImageUtil();
		//int dp = ViewUtil.dip2px(mBaseActivity, 5);
		//width = (App.SCREEN_WIDTH / 2 - dp - (dp / 2));
		//width = App.SCREEN_WIDTH / 2;
		for (Topic topic : data) {
			SizeModel sizeModel = new SizeModel();
			sizeModel.setUrl(topic.getIco());
			sizeModels.add(sizeModel);
		}

	}


	@Override
	protected void convert(BaseViewHolder helper, Topic item, int position) {

		int itemType = helper.getItemViewType();
		Log.d("findTopicAdapter:conve", ":itemType:" + item);
		switch (itemType) {
			case 0:
				setUpItem(helper, item, position);
				break;
			case 1:
				setUpRecommend(helper, item, position);
				break;
			case 2:
				setUpBanner(helper, item, position);
				break;
			case 3:
				setUpTag(helper, item, position);
				break;
			default:
				break;
		}
	}

	private void setUpTag(BaseViewHolder helper, Topic item, int position) {

		ObservableRecyclerView rl_tag = helper.getView(R.id.rl_tag);
		final List<Tag> tags = item.getTopictags();

		rl_tag.setLayoutManager(new GridLayoutManager(mContext, 6));
		rl_tag.setItemAnimator(new DefaultItemAnimator());
		rl_tag.setHasFixedSize(true);

		BaseQuickAdapter<Tag> adapter = new BaseQuickAdapter<Tag>(mContext, R.layout.list_item_mytopic_tag, tags) {
			@Override
			protected void convert(BaseViewHolder helper, Tag item, int position) {
				helper.setText(R.id.tv_tag, item.getName());
			}
		};

		adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("tag",tags.get(position));
				mBaseActivity.go2Activity(TagTopicListActivity.class,bundle,false);
			}
		});

		rl_tag.setAdapter(adapter);

	}

	private void setUpItem(BaseViewHolder helper, Topic item, int position) {

		if (sizeModels.size() > 0) {

			final SizeModel sizeModel = sizeModels.get(position);
			final ImageView view = helper.getView(R.id.iv_icon);

			imageUtil.loadFromUrlWithCircle(item.getAuthorIco(), (ImageView) helper.getView(R.id.iv_avatar));

			helper.setText(R.id.tv_name, item.getAuthor());

			helper.setVisible(R.id.iv_isVip, item.getIsVip() > 0);
			//helper.setVisible(R.id.iv_avatar_bg, item.getIsVip() > 0);
			helper.setText(R.id.tv_content, item.getContent());
			helper.setText(R.id.tv_pl, item.getCommentCount() + "");
			helper.setText(R.id.tv_fav, item.getFavCount() + "");

			//helper.setBackgroundColor(R.id.cardview, 0xFFFFFFFF);

			if (item.getArticlePictures() != null && item.getArticlePictures().size() > 1) {
				helper.setText(R.id.tv_imgszie, item.getArticlePictures().size() + "");
				helper.setVisible(R.id.al_imgsize, true);
			} else {
				helper.setVisible(R.id.al_imgsize, false);
			}

			if (item.getIsFav() > 0) {
				helper.setImageResource(R.id.iv_fav, R.mipmap.article_fav_icon);
				helper.setTextColor(R.id.tv_fav, mBaseActivity.getResources().getColor(R.color.redWin));
			} else {
				helper.setImageResource(R.id.iv_fav, R.mipmap.article_fav_normal_icon);
				helper.setTextColor(R.id.tv_fav, mBaseActivity.getResources().getColor(R.color.txt_gray));
			}

			OnItemChildClickListener childClickListener = new OnItemChildClickListener();

			helper.setOnClickListener(R.id.ll_user, childClickListener);
			helper.setOnClickListener(R.id.iv_icon, childClickListener);
			helper.setOnClickListener(R.id.ll_pl, childClickListener);
			helper.setOnClickListener(R.id.ll_fav, childClickListener);
			helper.setOnClickListener(R.id.tv_content, childClickListener);

			try {

				Glide.with(mBaseActivity)
						.load(item.getIco())
						.asBitmap()
					//	.transform(new GlideRoundTransform(mContext))
						.into(new SimpleTarget<Bitmap>() {

							@Override
							public void onLoadStarted(Drawable placeholder) {

								view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
								view.setCropToPadding(true);
								view.setPadding(100, 100, 100, 100);
								view.setImageDrawable(placeholder);
							}

							@Override
							public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

								if (sizeModel.isNull()) {

									//Log.d("kwan","view.getMeasuredWidth()"+view.getMeasuredWidth());
									Log.d("kwan", "view.getWidth()" + view.getWidth());

									float scale = resource.getHeight() / (resource.getWidth() * 1.0f);
									int viewHeight = (int) (view.getWidth() * scale);
									sizeModel.setSize(view.getWidth(), viewHeight);
								}

								view.setLayoutParams(new FrameLayout.LayoutParams(sizeModel.getWidth(), sizeModel.getHeight()));
								view.setScaleType(ImageView.ScaleType.CENTER_CROP);
								view.setCropToPadding(true);
								view.setPadding(0, 0, 0, 0);
								view.setImageBitmap(resource);

							}
						});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	BaseQuickAdapter<RecommendUser> userAdapter;

	private void setUpRecommend(BaseViewHolder helper, Topic item, int position) {

		final List<RecommendUser> mUsers = item.getRecommendUsers();

		RecyclerView recyclerView = helper.getView(R.id.recycler);
		AutoLinearLayout ll_title = helper.getView(R.id.ll_title);
		TextView tv_more = helper.getView(R.id.tv_more);

		tv_more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mBaseActivity.go2ActivityWithLeft(HotUserActivity.class, null, false);
			}
		});

		recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setHasFixedSize(true);

		userAdapter = new BaseQuickAdapter<RecommendUser>(mContext, R.layout.list_item_in_recemmend, mUsers) {
			@Override
			protected void convert(BaseViewHolder helper, RecommendUser item, int position) {
				mBaseActivity.mImageUtil.loadFromUrlWithCircle(item.getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));

				helper.setText(R.id.tv_name, item.getNickName());

				if (item.getIsFollow() == 0) {
					helper.setTextColor(R.id.tv_follow, mBaseActivity.getResources().getColor(R.color.redWin));
					helper.setBackgroundRes(R.id.tv_follow, R.drawable.shape_reg_btn_bg_white);
					helper.setText(R.id.tv_follow, "关注");
				} else {
					helper.setTextColor(R.id.tv_follow, mBaseActivity.getResources().getColor(R.color.txt_white));
					helper.setBackgroundRes(R.id.tv_follow, R.drawable.shape_reg_btn_bg);
					helper.setText(R.id.tv_follow, "已关注");
				}
				helper.setOnClickListener(R.id.tv_follow, new OnItemChildClickListener());
			}
		};

		userAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {

				Bundle bundle = new Bundle();
				bundle.putLong("userid", mUsers.get(position).getUserId());
				bundle.putInt("isFollow", mUsers.get(position).getIsFollow());
				mBaseActivity.go2Activity(FriendDetailActivity.class, bundle, false);
			}
		});

		userAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
			@Override
			public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

				if (view.getId() == R.id.tv_follow) {
					if (SPUtil.getIsLogin()) {

						RecommendUser aduser = mUsers.get(position);
						if (aduser.getIsFollow() == 0) {
							mBaseActivity.showProgress("");
							followPresenter.addFollow(aduser.getUserId(), position);
						} else {
							mBaseActivity.showProgress("");
							followPresenter.deleteFollow(aduser.getUserId(), position);
						}
					} else {
						mBaseActivity.go2Activity(DologinActivity.class, null, false);
					}
				}
			}
		});

		if (mUsers.size() > 0)
			ll_title.setVisibility(View.VISIBLE);

		recyclerView.setAdapter(userAdapter);

	}

	private void setUpBanner(BaseViewHolder helper, Topic item, int position) {

		ArrayList<ImageView> imageViews = new ArrayList<>();
		final ArrayList<View> dots = new ArrayList<>();
		ArrayList<String> urls = new ArrayList<>();
		final List<Banner> mBanners = item.getBanners();

		AutoLinearLayout ll_banner_dot = helper.getView(R.id.ll_banner_dot);
		ViewPager viewPager = helper.getView(R.id.vp);
		final TextView tv_banner = helper.getView(R.id.tv_banner_txt);

		ll_banner_dot.removeAllViews();
		tv_banner.setVisibility(View.GONE);

		if(mBanners.size()==0){
			viewPager.setVisibility(View.GONE);
			return;
		}


		for (int i = 0; i < mBanners.size(); i++) {

			View v = new View(mContext);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(16, 16);
			lp.setMargins(10, 0, 10, 0);
			v.setLayoutParams(lp);

			if (i == 0) {

				String txt = mBanners.get(0).getName();
				if (txt != null && !txt.isEmpty()) {
					tv_banner.setTypeface(Typeface.DEFAULT_BOLD);
					tv_banner.setVisibility(View.VISIBLE);
					tv_banner.setText(txt);
				}


				v.setBackgroundResource(R.drawable.dot_focused);
			} else {
				v.setBackgroundResource(R.drawable.dot_normal);
			}
			dots.add(v);
		}

		ll_banner_dot.removeAllViews();
		for (View v : dots)
			ll_banner_dot.addView(v);

		if (mBanners.size() == 1)
			ll_banner_dot.setVisibility(View.GONE);

		for (int i = 0; i < mBanners.size(); i++) {

			ImageView imageView = new ImageView(mContext);
			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			imageView.setLayoutParams(lp);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

			if (mBanners.get(i).getImg().endsWith(".gif")) {
				imageUtil.loadGifFromUrl(mBanners.get(i).getImg(), imageView);
			} else {
				imageUtil.loadFromUrl(mBanners.get(i).getImg(), imageView);
			}
			imageViews.add(imageView);
			dots.get(i).setVisibility(View.VISIBLE);
			urls.add(mBanners.get(i).getImg());
		}

		BZViewPageAdapter mBannerAdapter = new BZViewPageAdapter<>(imageViews);
		mBannerAdapter.setOnClickItemListener(new BZViewPageAdapter.ClickItemListener() {
			@Override
			public void onClickItem(ViewGroup container, int position, View view) {
				Banner banner = mBanners.get(position);

				int type = banner.getType();

				if (type == 1) {

					Bundle bundle = new Bundle();
					bundle.putLong("id", banner.getRefId());
					bundle.putString("icon", banner.getImg());
					bundle.putBoolean("isFav", false);
					mBaseActivity.go2Activity(ArticleActivity.class, bundle, false);

				} else if (type == 2 && banner.getUrl() != null && !banner.getUrl().isEmpty()) {

					Bundle bundle = new Bundle();

					bundle.putString("url", banner.getUrl());
					bundle.putString("title", banner.getName());
					bundle.putString("txt", banner.getName());
					bundle.putString("img_url", banner.getImg());

					mBaseActivity.go2Activity(WebActivity.class, bundle, false);

				} else if (type == 4 && banner.getUrl() != null && !banner.getUrl().isEmpty()) {

					Bundle bundle = new Bundle();
					bundle.putString("url", banner.getUrl());
					mBaseActivity.go2Activity(VideoActivity.class, bundle, false);
				} else if (type == 10) {
					Bundle bundle = new Bundle();
					bundle.putSerializable("banner", banner);
					mBaseActivity.go2Activity(BannerTopicActivity.class, bundle, false);
				}
			}
		});

		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			private int oldPosition = 0;

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {

				dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
				dots.get(position).setBackgroundResource(R.drawable.dot_focused);
				oldPosition = position;

				tv_banner.setText(mBanners.get(position).getName());
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});

		// 设置填充ViewPager页面的适配器
		viewPager.setAdapter(mBannerAdapter);
		mBannerAdapter.notifyDataSetChanged();



	}

	public void addData(List<Topic> data) {

		ArrayList<SizeModel> sizeModels = new ArrayList<>();

		for (Topic topic : data) {
			SizeModel sizeModel = new SizeModel();
			sizeModel.setUrl(topic.getIco());
			sizeModels.add(sizeModel);
		}

		this.sizeModels.addAll(sizeModels);
		System.out.println("addData::" + data.size());
		super.addData(data);

	}

	public void setNewData(List<Topic> data) {


		ArrayList<SizeModel> sizeModels = new ArrayList<>();

		for (Topic topic : data) {
			SizeModel sizeModel = new SizeModel();
			sizeModel.setUrl(topic.getIco());
			sizeModels.add(sizeModel);
		}
		this.sizeModels = sizeModels;

		super.setNewData(data);

	}

	private void onBannerClcik(ViewGroup container, int position, View view, List<Banner> mBanners) {

		// Type为2，为连接广告，Url为空，则不打开。
		// Type为1,为文章推荐，点击调用文章详情接口
		// Type为3，为无连接广告
		// Type为4，为视频，视频地址为Url字段
		Banner banner = mBanners.get(position);
		App.umengEvent(mContext, IUmengEvent.MainBannerAdClick,
				banner.getName() + ":" + banner.getId());

		int type = banner.getType();

		if (type == 1) {

			Bundle bundle = new Bundle();
			bundle.putLong("id", banner.getRefId());
			bundle.putString("icon", banner.getImg());
			bundle.putBoolean("isFav", false);

			mBaseActivity.go2Activity(ArticleActivity.class, bundle, false);

		} else if (type == 2 && banner.getUrl() != null && !banner.getUrl().isEmpty()) {

			Bundle bundle = new Bundle();
			bundle.putString("url", banner.getUrl());
			bundle.putString("title", banner.getName());
			bundle.putString("txt", banner.getName());
			bundle.putString("img_url", banner.getImg());
			mBaseActivity.go2Activity(WebActivity.class, bundle, false);

		} else if (type == 4 && banner.getUrl() != null && !banner.getUrl().isEmpty()) {

			Bundle bundle = new Bundle();
			bundle.putString("url", banner.getUrl());
			mBaseActivity.go2Activity(VideoActivity.class, bundle, false);
		}
	}

	public BaseQuickAdapter<RecommendUser> getUserAdapter() {
		return userAdapter;
	}
}
