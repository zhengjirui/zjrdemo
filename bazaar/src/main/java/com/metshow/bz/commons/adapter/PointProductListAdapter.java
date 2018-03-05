package com.metshow.bz.commons.adapter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kwan.base.BaseApplication;
import com.kwan.base.activity.BaseActivity;
import com.kwan.base.adatper.SizeModel;
import com.kwan.base.adatper.StaggeredGridAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.util.ImageUtil;
import com.metshow.bz.IUmengEvent;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.PointProduct;
import com.metshow.bz.commons.bean.article.Banner;
import com.metshow.bz.module.commons.activity.WebActivity;
import com.metshow.bz.module.in.activity.ArticleActivity;
import com.metshow.bz.presenter.FollowPresenter;
import com.tencent.smtt.sdk.VideoActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mr.Kwan on 2017-2-24.
 */

public class PointProductListAdapter extends StaggeredGridAdapter<PointProduct> {

	//保存长宽
	private List<SizeModel> sizeModels = new ArrayList<>();

	//private final int width;
	private BaseActivity mBaseActivity;
	private FollowPresenter followPresenter;

	ImageUtil imageUtil;

	public void setFollowPresenter(FollowPresenter followPresenter) {
		this.followPresenter = followPresenter;
	}

	public PointProductListAdapter(BaseActivity baseActivity, List<PointProduct> data) {
		super(baseActivity, data);
		imageUtil = BaseApplication.getInstance().getImageUtil();
		mBaseActivity = baseActivity;
		//1为话题 2为投票 3为活动 4为连接 5主题 6视频 7为图组
		addItmeType(0, R.layout.list_item_point_product);
		addItmeType(1, R.layout.layout_banner_find);
		imageUtil = new ImageUtil();

	}


	@Override
	protected void convert(BaseViewHolder helper, PointProduct item, int position) {
		int itemType = helper.getItemViewType();
		switch (itemType) {
			case 0:
				setUpItem(helper, item, position);
				break;
			case 1:
				setUpBanner(helper, item, position);
				break;
			default:
				break;
		}
	}

	private void setUpItem(BaseViewHolder helper, PointProduct item, int position) {

		imageUtil.loadFromUrlCenterInside(item.getIco(), (ImageView) helper.getView(R.id.iv_icon));

		helper.setText(R.id.tv_name,item.getName());
		helper.setText(R.id.tv_point,item.getPoints()+"");


	}

	private void setUpBanner(BaseViewHolder helper, PointProduct item, int position) {

//		ArrayList<ImageView> imageViews = new ArrayList<>();
//		final ArrayList<View> dots = new ArrayList<>();
//		ArrayList<String> urls = new ArrayList<>();
//		final List<Banner> mBanners = item.getBanners();
//
//		AutoLinearLayout ll_banner_dot = helper.getView(R.id.ll_banner_dot);
//		ViewPager viewPager = helper.getView(R.id.vp);
//		final TextView tv_banner = helper.getView(R.id.tv_banner_txt);
//
//		ll_banner_dot.removeAllViews();
//		tv_banner.setVisibility(View.GONE);
//
//		if(mBanners.size()==0){
//			viewPager.setVisibility(View.GONE);
//			return;
//		}
//
//
//		for (int i = 0; i < mBanners.size(); i++) {
//
//			View v = new View(mContext);
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(16, 16);
//			lp.setMargins(10, 0, 10, 0);
//			v.setLayoutParams(lp);
//
//			if (i == 0) {
//
//				String txt = mBanners.get(0).getName();
//				if (txt != null && !txt.isEmpty()) {
//					tv_banner.setTypeface(Typeface.DEFAULT_BOLD);
//					tv_banner.setVisibility(View.VISIBLE);
//					tv_banner.setText(txt);
//				}
//
//
//				v.setBackgroundResource(R.drawable.dot_focused);
//			} else {
//				v.setBackgroundResource(R.drawable.dot_normal);
//			}
//			dots.add(v);
//		}
//
//		ll_banner_dot.removeAllViews();
//		for (View v : dots)
//			ll_banner_dot.addView(v);
//
//		if (mBanners.size() == 1)
//			ll_banner_dot.setVisibility(View.GONE);
//
//		for (int i = 0; i < mBanners.size(); i++) {
//
//			ImageView imageView = new ImageView(mContext);
//			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//			imageView.setLayoutParams(lp);
//			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//			if (mBanners.get(i).getImg().endsWith(".gif")) {
//				imageUtil.loadGifFromUrl(mBanners.get(i).getImg(), imageView);
//			} else {
//				imageUtil.loadFromUrl(mBanners.get(i).getImg(), imageView);
//			}
//			imageViews.add(imageView);
//			dots.get(i).setVisibility(View.VISIBLE);
//			urls.add(mBanners.get(i).getImg());
//		}
//
//		BZViewPageAdapter mBannerAdapter = new BZViewPageAdapter<>(imageViews);
//		mBannerAdapter.setOnClickItemListener(new BZViewPageAdapter.ClickItemListener() {
//			@Override
//			public void onClickItem(ViewGroup container, int position, View view) {
//				Banner banner = mBanners.get(position);
//
//				int type = banner.getType();
//
//				if (type == 1) {
//
//					Bundle bundle = new Bundle();
//					bundle.putLong("id", banner.getRefId());
//					bundle.putString("icon", banner.getImg());
//					bundle.putBoolean("isFav", false);
//					mBaseActivity.go2Activity(ArticleActivity.class, bundle, false);
//
//				} else if (type == 2 && banner.getUrl() != null && !banner.getUrl().isEmpty()) {
//
//					Bundle bundle = new Bundle();
//
//					bundle.putString("url", banner.getUrl());
//					bundle.putString("title", banner.getName());
//					bundle.putString("txt", banner.getName());
//					bundle.putString("img_url", banner.getImg());
//
//					mBaseActivity.go2Activity(WebActivity.class, bundle, false);
//
//				} else if (type == 4 && banner.getUrl() != null && !banner.getUrl().isEmpty()) {
//
//					Bundle bundle = new Bundle();
//					bundle.putString("url", banner.getUrl());
//					mBaseActivity.go2Activity(VideoActivity.class, bundle, false);
//				} else if (type == 10) {
//					Bundle bundle = new Bundle();
//					bundle.putSerializable("banner", banner);
//					mBaseActivity.go2Activity(BannerTopicActivity.class, bundle, false);
//				}
//			}
//		});
//
//		// 设置一个监听器，当ViewPager中的页面改变时调用
//		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//			private int oldPosition = 0;
//
//			@Override
//			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//			}
//
//			@Override
//			public void onPageSelected(int position) {
//
//				dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
//				dots.get(position).setBackgroundResource(R.drawable.dot_focused);
//				oldPosition = position;
//
//				tv_banner.setText(mBanners.get(position).getName());
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int state) {
//			}
//		});
//
//		// 设置填充ViewPager页面的适配器
//		viewPager.setAdapter(mBannerAdapter);
//		mBannerAdapter.notifyDataSetChanged();



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

}
