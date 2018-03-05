package com.metshow.bz.commons.adapter;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kwan.base.activity.BaseActivity;
import com.kwan.base.adatper.base.BaseMultiItemQuickAdapter;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.util.SPUtil;
import com.metshow.bz.IUmengEvent;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.Aduser;
import com.metshow.bz.commons.bean.article.Article;
import com.metshow.bz.commons.bean.article.Banner;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.metshow.bz.module.commons.activity.WebActivity;
import com.metshow.bz.module.in.activity.ArticleActivity;
import com.metshow.bz.module.in.activity.ArticlePicGroupActivity;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.module.main.MainActivity;
import com.metshow.bz.module.shai.TopicActivity;
import com.metshow.bz.presenter.FollowPresenter;
import com.tencent.smtt.sdk.VideoActivity;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


/**
 * Created by Mr.Kwan on 2017-2-24.
 */

public class ArticleListAdapter extends BaseMultiItemQuickAdapter<Article> {

	private BaseActivity mBaseActivity;
	private FollowPresenter followPresenter;

	public void setFollowPresenter(FollowPresenter followPresenter) {
		this.followPresenter = followPresenter;
	}

	public ArticleListAdapter(BaseActivity baseActivity, List<Article> data) {
		super(baseActivity, data);

		mBaseActivity = baseActivity;
		//1为话题 2为投票 3为活动 4为连接 5主题 6视频 7为图组
		addItmeType(0, R.layout.list_item_fav_article);
		addItmeType(1, R.layout.list_item_fav_article);
		addItmeType(2, R.layout.list_item_fav_article);
		addItmeType(3, R.layout.list_item_fav_article);
		addItmeType(4, R.layout.list_item_fav_article);
		addItmeType(5, R.layout.list_item_fav_article);
		addItmeType(6, R.layout.list_item_fav_article);
		addItmeType(7, R.layout.layout_recommend_recycle_in); //推荐用户
		addItmeType(8, R.layout.layout_banner); //banner
	}

	@Override
	protected void convert(BaseViewHolder helper, final Article item, int position) {
		int itemType = helper.getItemViewType();
		switch (itemType) {

			case 0:
				helper.setVisible(R.id.tv_tag, true);
				if (item.getArticleType() == 7)
					helper.setText(R.id.tv_tag, "大片");
				else
					helper.setText(R.id.tv_tag, item.getChannelName());

				helper.setImageUrl(R.id.iv_icon, item.getIco());
				helper.setText(R.id.tv_title, item.getTitle());
				helper.setText(R.id.tv_content, item.getSummary());
				helper.setVisible(R.id.iv_delete, false);
				helper.setOnClickListener(R.id.content, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						onArticalItemClick(v, item);
					}
				});
				Typeface typeFace = Typeface.createFromAsset(mBaseActivity.getAssets(), "fonts/xhjt.ttf");
				//使用字体
				((TextView) helper.getView(R.id.tv_title)).setTypeface(typeFace);
				((TextView) helper.getView(R.id.tv_content)).setTypeface(typeFace);

				break;
			case 7:
				setUpRecmmend(helper, item.getAdforrecs().getAduser());
				break;
			case 8:
				setUpBanner(helper, item.getBanners());
				break;
			default:
				helper.setVisible(R.id.tv_tag, true);
				if (item.getArticleType() == 7)
					helper.setText(R.id.tv_tag, "大片");
				else
					helper.setText(R.id.tv_tag, item.getChannelName());

				helper.setImageUrl(R.id.iv_icon, item.getIco());
				helper.setText(R.id.tv_title, item.getTitle());
				helper.setText(R.id.tv_content, item.getSummary());
				helper.setVisible(R.id.iv_delete, false);
				helper.setOnClickListener(R.id.content, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						onArticalItemClick(v, item);
					}
				});

				break;
		}

	}

	private void onArticalItemClick(View v, Article article) {

		Intent intent;

		Bundle bundle = new Bundle();
		bundle.putLong("id", article.getArticleId());
		bundle.putString("icon", article.getIco());
		bundle.putBoolean("isFav", article.getIsFav() > 0);

		if (article.getArticleType() == 7) {
			mBaseActivity.go2ActivityWithLeft(ArticlePicGroupActivity.class, bundle, false);
		} else if(article.getArticleType() == 4){


			bundle.putString("DATA", article.getUrl());
			bundle.putString("TITLE", article.getTitle());
			bundle.putString("txt", article.getTitle());
			bundle.putString("img_url", article.getAuthorIco());

			Log.e("WebActivity", "WebActivity");
			mBaseActivity.go2Activity(WebActivity.class, bundle, false);

		}else {
			intent = new Intent(mBaseActivity, ArticleActivity.class);
			intent.putExtras(bundle);
			((MainActivity) mBaseActivity).dogo(v.findViewById(R.id.iv_icon), intent);
		}
	}

	private void setUpBanner(BaseViewHolder helper, final List<Banner> result) {

		final TextView tv_banner = helper.getView(R.id.tv_banner_txt);
		final ViewPager viewPager = helper.getView(R.id.vp);
		final AutoLinearLayout ll_banner_dot = helper.getView(R.id.ll_banner_dot);

		ArrayList<View> imageViews = new ArrayList<>();
		final ArrayList<View> dots = new ArrayList<>();
		ArrayList<String> urls = new ArrayList<>();

		for (int i = 0; i < result.size(); i++) {
			View v = new View(mBaseActivity);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(16, 16);
			lp.setMargins(10, 0, 10, 0);
			v.setLayoutParams(lp);
			if (i == 0) {

				tv_banner.setTypeface(Typeface.DEFAULT_BOLD);
				String str = result.get(0).getName().trim();
				if (!str.isEmpty()) {
					tv_banner.setVisibility(View.VISIBLE);
					tv_banner.setText(result.get(0).getName());

				} else {
					tv_banner.setVisibility(View.INVISIBLE);
				}
				v.setBackgroundResource(R.drawable.dot_focused);
			} else
				v.setBackgroundResource(R.drawable.dot_normal);

			dots.add(v);

		}

		ll_banner_dot.removeAllViews();
		for (View v : dots)
			ll_banner_dot.addView(v);

		for (int i = 0; i < result.size(); i++) {

			ImageView imageView = new ImageView(mContext);
			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			imageView.setLayoutParams(lp);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

			if (result.get(i).getImg().endsWith(".gif")) {
				mBaseActivity.mImageUtil.loadGifFromUrl(result.get(i).getImg(), imageView);
			} else {
				mBaseActivity.mImageUtil.loadFromUrl(result.get(i).getImg(), imageView);
			}

			imageViews.add(imageView);
			dots.get(i).setVisibility(View.VISIBLE);
			urls.add(result.get(i).getImg());
		}

		BZViewPageAdapter mBannerAdapter = new BZViewPageAdapter<>(imageViews);
		mBannerAdapter.setOnClickItemListener(new BZViewPageAdapter.ClickItemListener() {
			@Override
			public void onClickItem(ViewGroup container, int position, View view) {
				onBannerClcik(container, position, view, result);
			}
		});
		// 设置填充ViewPager页面的适配器
		viewPager.setAdapter(mBannerAdapter);
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			int oldPosition = 0;

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
				dots.get(position).setBackgroundResource(R.drawable.dot_focused);
				oldPosition = position;

				if (result.get(position).getName() != null && !result.get(position).getName().isEmpty()) {
					tv_banner.setVisibility(View.VISIBLE);
					tv_banner.setText(result.get(position).getName());
					App.umengEvent(mContext, IUmengEvent.MainBannerAdBrowse,
							result.get(position).getName() + ":" + result.get(position).getId());
				} else {
					App.umengEvent(mContext, IUmengEvent.MainBannerAdBrowse,
							"null" + ":" + result.get(position).getId());
					tv_banner.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		App.umengEvent(mContext, IUmengEvent.MainBannerAdBrowse,
				result.get(0).getName() + ":" + result.get(0).getId());

		if (result.size() == 1)
			ll_banner_dot.setVisibility(View.INVISIBLE);

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
			bundle.putString("DATA", banner.getUrl());
			bundle.putString("TITLE", banner.getName());
			bundle.putString("txt", banner.getName());
			bundle.putString("img_url", banner.getImg());

			Log.e("WebActivity", "WebActivity");
			mBaseActivity.go2Activity(WebActivity.class, bundle, false);

		} else if (type == 4 && banner.getUrl() != null && !banner.getUrl().isEmpty()) {

			Bundle bundle = new Bundle();
			bundle.putString("url", banner.getUrl());
			Log.e("VideoActivity", "VideoActivity");
			mBaseActivity.go2Activity(VideoActivity.class, bundle, false);

		} else if (type == 8) {


			Bundle bundle = new Bundle();
			bundle.putLong("userid", banner.getRefId());
			bundle.putInt("isFollow", 0);
//			bundle.putBoolean("is2main", false);
			bundle.putString("name", banner.getRemark());
			mBaseActivity.go2ActivityWithLeft(FriendDetailActivity.class, bundle, false);


		} else if(type == 11){
			Bundle bundle = new Bundle();
			bundle.putLong("topicid", banner.getRefId());
			mBaseActivity.go2Activity(TopicActivity.class,bundle,false);

		}else {
			//-1为晒
			//Type：10为话题 , 9为用户反馈
			mBaseActivity.toastMsg("ChannelType:" + banner.getChannelType() + " type:" + type);
		}
	}

	private BaseQuickAdapter<Aduser> recmmendAdapter;

	private void setUpRecmmend(BaseViewHolder helper, final List<Aduser> adusers) {

		final List<Aduser> mAdusers = adusers;
		RecyclerView recmmendRecyclerView;
		if (mAdusers == null || mAdusers.size() == 0)
			helper.getConvertView().setVisibility(View.GONE);
		else {

			recmmendRecyclerView = helper.getView(R.id.recycler);
			recmmendRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
			recmmendRecyclerView.setItemAnimator(new DefaultItemAnimator());
			recmmendRecyclerView.setHasFixedSize(true);

			recmmendAdapter = new BaseQuickAdapter<Aduser>(mBaseActivity, R.layout.list_item_in_recemmend, mAdusers) {
				@Override
				protected void convert(BaseViewHolder helper, Aduser item, int position) {

					mBaseActivity.mImageUtil.loadFromUrlWithCircle(item.getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));
					//mBaseActivity.mImageUtil.loadFromResWithCircle(R.mipmap.icon_vip, (ImageView) helper.getView(R.id.iv_isVip));
					GifDrawable mGifDrawable = (GifDrawable) ((GifImageView) helper.getView(R.id.iv_isVip)).getDrawable();
					if (!mGifDrawable.isRunning()) {
						mGifDrawable.start();
					}

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

			recmmendAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
				@Override
				public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

					if (!SPUtil.getIsLogin()) {
						mBaseActivity.go2Activity(DologinActivity.class, null, false);
						return;
					}

					Aduser aduser = mAdusers.get(position);
					if (aduser.getIsFollow() == 0) {
						mBaseActivity.showProgress("");
						followPresenter.addFollow(aduser.getUserId(), position);
					} else {
						mBaseActivity.showProgress("");
						followPresenter.deleteFollow(aduser.getUserId(), position);
					}
				}
			});

			recmmendAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
				@Override
				public void onItemClick(View view, int position) {

					App.umengEvent(mContext, IUmengEvent.MainInsertAdClick,
							mAdusers.get(position).getNickName() + ":" + mAdusers.get(position).getUserId());

					Bundle bundle = new Bundle();

					bundle.putLong("userid", mAdusers.get(position).getUserId());
					bundle.putInt("isFollow", 0);
					bundle.putBoolean("is2main", false);
					bundle.putString("name", mAdusers.get(position).getNickName());

					mBaseActivity.go2ActivityWithLeft(FriendDetailActivity.class, bundle, false);

				}
			});

			recmmendRecyclerView.setAdapter(recmmendAdapter);
		}
	}

	public BaseQuickAdapter<Aduser> getRecmmendAdapter() {
		return recmmendAdapter;
	}
}
