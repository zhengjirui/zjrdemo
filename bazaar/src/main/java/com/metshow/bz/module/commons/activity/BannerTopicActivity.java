package com.metshow.bz.module.commons.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kwan.base.activity.BaseCommonActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.SPUtil;
import com.kwan.base.util.ViewUtil;
import com.kwan.base.widget.SlidingTabLayout;
import com.kwan.base.widget.SpacesItemDecoration;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.adapter.FindTopicListAdapter;
import com.metshow.bz.commons.bean.TopicSubject;
import com.metshow.bz.commons.bean.article.Banner;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.in.activity.ArticleActivity;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.module.shai.TopicActivity;
import com.metshow.bz.presenter.BannerTopicPresenter;
import com.metshow.bz.util.DialogFactory;
import com.metshow.bz.util.ShareUtil;
import com.yongchun.library.utils.FileUtils;
import com.yongchun.library.view.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BannerTopicActivity extends BaseCommonActivity implements SlidingTabLayout.OnTabClickLister, OnRefreshListener, OnLoadMoreListener,  BZContract.IBannerTopicView,  BaseQuickAdapter.OnRecyclerViewItemChildClickListener {

	private Banner mBanner;
	private ImageView iv_icon, iv_share, iv_send;
	private TextView tv_num;
	private SlidingTabLayout mTabLayout;
	private SwipeToLoadLayout swipeRefreshLayout;

	private int currentIndex = 0;

	private final ArrayList<Topic> newsTopics = new ArrayList<>();
	private final ArrayList<Topic> hotsTopics = new ArrayList<>();

	private FindTopicListAdapter newsAdapter, hotsAdapter;
	private ObservableRecyclerView recyclerView;
	private BannerTopicPresenter mPresnter;
	private Dialog mSendTopicDialog;
	private final int CAMERA_REQUEST_CODE = 1;
	private String cameraPath;
	private TopicSubject mBannerTopic;
	private boolean isLoadMore = false;
	private int pageindex = 1;
	private String maxdata = "0";
	private ImageView iv_add;

	@Override
	protected String getTitleTxt() {
		return mBanner.getName();
	}

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mBanner = (Banner) getIntentData("banner");
		mPresnter = new BannerTopicPresenter(this);
	}

	@Override
	protected void initViews() {
		super.initViews();

		tv_num = (TextView) findViewById(R.id.tv_num);
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		iv_share = (ImageView) findViewById(R.id.iv_share);
		iv_send = (ImageView) findViewById(R.id.iv_send);

		mTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);

		// ll_content = (AutoLinearLayout) findViewById(R.id.ll_content);

		swipeRefreshLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
		swipeRefreshLayout.setEnabled(true);
		swipeRefreshLayout.setRefreshEnabled(true);
		swipeRefreshLayout.setLoadMoreEnabled(true);

		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setOnLoadMoreListener(this);
//
		hotsAdapter = new FindTopicListAdapter(this, hotsTopics);

		hotsAdapter.setOnRecyclerViewItemChildClickListener(this);

		newsAdapter = new FindTopicListAdapter(this, newsTopics);

		newsAdapter.setOnRecyclerViewItemChildClickListener(this);
		newsAdapter.setHasStableIds(true);

		createViews();

	}

	public void createViews() {

		recyclerView = (ObservableRecyclerView) findViewById(R.id.recyclerView);
		StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(manager);

		recyclerView.setNestedScrollingEnabled(false);
		recyclerView.setItemAnimator(null);
		recyclerView.setHasFixedSize(true);

		int dp = ViewUtil.dip2px(this, 5);
		recyclerView.addItemDecoration(new SpacesItemDecoration(dp));
		recyclerView.setPadding(dp, dp, dp, dp);

		//麻痹的 关闭硬件加速 否则 cardView 报错
		recyclerView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		recyclerView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

	}

	@Override
	protected void initViewSetting() {
		super.initViewSetting();
		String img_url = mBanner.getImg();

		if (img_url.endsWith(".gif"))
			mImageUtil.loadGifFromUrl(img_url, iv_icon);
		else
			mImageUtil.loadFromUrl(img_url, iv_icon);

		int red = getResources().getColor(R.color.bazzarRed);
		mTabLayout.setTitleTextColor(red, getResources().getColor(R.color.txt_cobalt_blue));//标题字体颜色
		// mTabLayout.setTabStripWidth(110);//滑动条宽度
		mTabLayout.setSelectedIndicatorColors(red);//滑动条颜色
		mTabLayout.setTabTitleTextSize(16);//标题字体大小
		mTabLayout.setDistributeEvenly(true); //均匀平铺选项卡
		mTabLayout.setDividerColors(getResources().getColor(R.color.divider_color));

		mTabLayout.setUp(new String[]{"最新", "热门"});
		mTabLayout.setOnTabClickLister(this);

		iv_share.setOnClickListener(this);
		iv_send.setOnClickListener(this);

		mPresnter.getBannerTopicDetail(mBanner.getRefId());

	}

	@Override
	protected void initData() {

	}

	@Override
	protected int getTitleBarRightLayoutId() {
		return R.layout.title_right_follow;
	}

	@Override
	protected void setUpTitleRightView(View v) {
		super.setUpTitleRightView(v);
		iv_add = (ImageView) v;
		iv_add.setVisibility(View.GONE);

	}

	@Override
	protected void onResume() {
		super.onResume();
		swipeRefreshLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
			}
		}, 100);
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_banner_topic;
	}

	@Override
	protected BasePresenter getPresent() {
		return mPresnter;
	}

	@Override
	public void onTabClick(int position) {

		isFirst = false;

		if (position == 0) {

			currentIndex = 0;
			recyclerView.setAdapter(newsAdapter);

		} else if (position == 1) {

			currentIndex = 1;
			recyclerView.setAdapter(hotsAdapter);

			if (hotsTopics.size() == 0) {
				swipeRefreshLayout.postDelayed(new Runnable() {
					@Override
					public void run() {
						swipeRefreshLayout.setRefreshing(true);
					}
				}, 100);
			}
		}
	}

	@Override
	public void onRefresh() {
		isLoadMore = false;
		if (currentIndex == 0) {
			maxdata = "0";
			mPresnter.getNewTopics(maxdata, 10, mBanner.getRefId());
		} else {
			pageindex = 1;
			mPresnter.getHotTopics(pageindex, 10, mBanner.getRefId());
		}
	}

	@Override
	public void onLoadMore() {

		isLoadMore = true;

		if (currentIndex == 0) {

			maxdata = BasePresenter.getData(newsTopics.get(newsTopics.size() - 1).getPublishDate());
			mPresnter.getNewTopics(maxdata, 10, mBanner.getRefId());
		} else {
			pageindex += pageindex;
			mPresnter.getHotTopics(pageindex, 10, mBanner.getRefId());
		}
	}



	@Override
	public void getNewsTopicSuccess(List<Topic> items) {

		if (isLoadMore) {

			newsTopics.addAll(items);
			newsAdapter.addData(items);
		} else {
			newsTopics.clear();
			newsTopics.addAll(items);
			newsAdapter.setNewData(items);
		}

		recyclerView.setAdapter(newsAdapter);

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}

	@Override
	public void getHotsTopicSuccess(List<Topic> items) {



		if (isLoadMore) {
			hotsTopics.addAll(items);
			hotsAdapter.addData(items);
		} else {
			hotsTopics.clear();
			hotsTopics.addAll(items);
			hotsAdapter.setNewData(items);
		}

		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);

	}

	boolean isFirst = true;

	void dopre() {
		if (isFirst) {
			onTabClick(0);
		}
	}

	@Override
	public void deleteFavSuccess(String result, int type, int position) {

		dopre();

		if (currentIndex == 0) {
			newsTopics.get(position).setIsFav(0);
			newsTopics.get(position).setFavCount(Integer.valueOf(result));
		} else {
			hotsTopics.get(position).setIsFav(0);
			hotsTopics.get(position).setFavCount(Integer.valueOf(result));
		}
		recyclerView.getAdapter().notifyItemChanged(position);
		dismissProgress();
	}

	@Override
	public void favSuccess(String result, final int position) {
		dopre();
		if (currentIndex == 0) {
			newsTopics.get(position).setIsFav(1);
			newsTopics.get(position).setFavCount(Integer.valueOf(result));
		} else {
			hotsTopics.get(position).setIsFav(1);
			hotsTopics.get(position).setFavCount(Integer.valueOf(result));
		}

		recyclerView.getAdapter().notifyItemChanged(position);
		dismissProgress();
	}

	@Override
	public void getBannerTopicSuccess(TopicSubject result) {
		mBannerTopic = result;
		setTitleTxt(result.getName());
		tv_num.setText(mBannerTopic.getJoinUserCount() + "人参与");
		iv_icon.setOnClickListener(this);

		if (mBannerTopic.getIsFollow() == 0) {
			iv_add.setImageResource(R.mipmap.follow_add_icon);
		} else {
			iv_add.setImageResource(R.mipmap.followed);
		}
		iv_add.setOnClickListener(this);
		iv_add.setVisibility(View.VISIBLE);
	}

	@Override
	public void addFollowSuccess(String result) {

		mBannerTopic.setIsFollow(1);
		iv_add.setImageResource(R.mipmap.followed);
		toastMsg("已关注");
		dismissProgress();
	}

	@Override
	public void deleteFollowSuccess(String result) {

		mBannerTopic.setIsFollow(0);
		iv_add.setImageResource(R.mipmap.follow_add_icon);
		toastMsg("已取消关注");
		dismissProgress();
	}

	@Override
	public void onServerError(int vocational_id, Throwable e) {
		super.onServerError(vocational_id, e);

		dismissProgress();
		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);

	}

	@Override
	public void onServerFailed(String s) {
		super.onServerFailed(s);
		dismissProgress();
		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v == iv_share) {

			ShareUtil shareUtil = new ShareUtil(this);
			shareUtil.setStr_title(mBanner.getName());
			shareUtil.setmText("bz.metshow.com");
			shareUtil.setStr_img(mBanner.getImg());
			shareUtil.setStr_url("http://bz.metshow.com/api/topic/index.html?topicid=" + mBanner.getRefId());
			shareUtil.showShareDialog();

		} else if (v == iv_send) {

			if (mBannerTopic != null)
				showTopicDialog();

		} else if (v == iv_add) {
			if (mBannerTopic.getIsFollow() > 0)
				mPresnter.topicFollowDelete(mBannerTopic.getTopicId());
			else
				mPresnter.topicFollowAdd(mBannerTopic.getTopicId());
			showProgress("");

		} else if (v == iv_icon) {
			long id = mBannerTopic.getArticleId();

			if (id > 0) {

				Bundle bundle = new Bundle();

				bundle.putString("icon", mBannerTopic.getPic());
				bundle.putLong("id", id);
				bundle.putBoolean("isFav", mBannerTopic.getIsFollow() != 0);

				Log.d("kwan", "id::" + mBannerTopic.getArticleId());
				go2Activity(ArticleActivity.class, bundle, false);
			}


		} else {
			switch (v.getId()) {
				case R.id.tv_video:
					go2Activity(RecorderActivity.class, null, false);
					mSendTopicDialog.cancel();
					break;
				case R.id.tv_camera:
					if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
							!= PackageManager.PERMISSION_GRANTED) {
						//申请WRITE_EXTERNAL_STORAGE权限
						ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
								CAMERA_REQUEST_CODE);
					} else {
						starCam();
					}
					mSendTopicDialog.cancel();
					break;
				case R.id.tv_photo:
					ImageSelectorActivity.setOnResultListener(new ImageSelectorActivity.OnResultListener() {
						@Override
						public void onResult(ArrayList<String> images) {
							Bundle bundle = new Bundle();
							bundle.putStringArrayList("output", images);
							bundle.putSerializable("subject", mBannerTopic);
							bundle.putInt("mode", 2);
							go2Activity(SendTopicActivity.class, bundle, false);
						}
					});

					Bundle bundle = new Bundle();
					bundle.putInt(ImageSelectorActivity.EXTRA_SELECT_MODE, ImageSelectorActivity.MODE_MULTIPLE);
					bundle.putBoolean(ImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
					bundle.putBoolean(ImageSelectorActivity.EXTRA_IS_FINISHRESULT, false);
					go2Activity(ImageSelectorActivity.class, bundle, false);

					mSendTopicDialog.cancel();
					break;

				case R.id.tv_cancel:
					mSendTopicDialog.cancel();
					break;

			}

		}

	}

	private void showTopicDialog() {

		View parent = getLayoutInflater().inflate(R.layout.send_topic_dialog, null);
		TextView tv_video = (TextView) parent.findViewById(R.id.tv_video);
		TextView tv_cancel = (TextView) parent.findViewById(R.id.tv_cancel);
		TextView tv_photo = (TextView) parent.findViewById(R.id.tv_photo);
		TextView tv_camera = (TextView) parent.findViewById(R.id.tv_camera);

		tv_video.setOnClickListener(this);
		tv_cancel.setOnClickListener(this);
		tv_photo.setOnClickListener(this);
		tv_camera.setOnClickListener(this);

		mSendTopicDialog = DialogFactory.showMenuDialog(this, parent);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == CAMERA_REQUEST_CODE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				starCam();
			} else {
				Toast.makeText(this, "没有相机权限", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void starCam() {

		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File cameraFile = FileUtils.createCameraFile(this);
		cameraPath = cameraFile.getAbsolutePath();

		i.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
		i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
		startActivityForResult(i, CAMERA_REQUEST_CODE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CAMERA_REQUEST_CODE) {
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(cameraPath))));

			ArrayList<String> output = new ArrayList<>();
			output.add(cameraPath);
			Bundle bundle = new Bundle();
			bundle.putInt("mode", 2);
			bundle.putStringArrayList("output", output);
			bundle.putSerializable("subject", mBannerTopic);
			go2Activity(SendTopicActivity.class, bundle, false);
		}
	}


	@Override
	public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

		Topic topic = (Topic) adapter.getData().get(position);

		switch (view.getId()) {
			//我的帖子 大图
			case R.id.iv_icon:
				// toTopicDetail(topic);

//                Bundle bundlex = new Bundle();
//                bundlex.putInt("type",TopicListActivity.MODE_USER);
//                bundlex.putLong("userid", topic.getUserId());
//                bundlex.putString("name", topic.getAuthor());
//                go2ActivityWithLeft(TopicListActivity.class, bundlex, false);

				Bundle bundlex = new Bundle();
				bundlex.putLong("topicid", topic.getArticleId());
				go2ActivityWithLeft(TopicActivity.class, bundlex, false);

				break;
			//我的贴子 评论
			case R.id.ll_pl:

				if (SPUtil.getIsLogin()) {

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
				if (SPUtil.getIsLogin()) {
					if (topic.getIsFav() == 0) {
						//收藏
						dismissProgress();
						mPresnter.addAcition(1, topic.getArticleId(), position);

					} else {
						//取消收藏
						dismissProgress();
						mPresnter.deleteFav(3, topic.getArticleId(), position);
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

	public static class AboutActivity extends BaseCommonActivity {

		private ImageView iv_bg;

		@Override
		protected void initViews() {
			super.initViews();
			iv_bg = (ImageView) findViewById(R.id.iv_bg);
		}

		@Override
		protected void initViewSetting() {
			super.initViewSetting();
			iv_bg.setImageBitmap(mImageUtil.compressBitmapFromResPathBySize(getResources(),
					R.mipmap.about_us_bg,
					App.SCREEN_WIDTH, App.SCREEN_HEIGHT - App.STATUS_BAR_HEIGHT - App.STATUS_BAR_HEIGHT));
		}

		@Override
		protected void initData() {

		}

		@Override
		protected int getContentViewId() {
			return R.layout.activity_about;
		}

		@Override
		protected BasePresenter getPresent() {
			return null;
		}

		@Override
		protected String getTitleTxt() {
			return "关于我们";
		}

		@Override
		protected int getTitleBarRightLayoutId() {
			return 0;
		}

		@Override
		protected int getBackGroundColor() {
			return Color.parseColor("#f7f5f6");
		}


	}
}
