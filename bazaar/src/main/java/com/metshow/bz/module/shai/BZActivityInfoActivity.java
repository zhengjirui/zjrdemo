package com.metshow.bz.module.shai;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.kwan.base.activity.BaseCommonActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.config.Config;
import com.kwan.base.util.SPUtil;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.CommentAdapter;
import com.metshow.bz.commons.adapter.FindTopicListAdapter;
import com.metshow.bz.commons.adapter.GrandPicAdapter;
import com.metshow.bz.commons.bean.BzActivity;
import com.metshow.bz.commons.bean.FollowItem;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.activity.CommentActivity;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.metshow.bz.module.commons.activity.RecorderActivity;
import com.metshow.bz.module.commons.activity.SendTopicActivity;
import com.metshow.bz.module.commons.activity.WebActivity;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.module.me.activity.GrandPicActivity;
import com.metshow.bz.presenter.BZActivityInfoPresenter;
import com.metshow.bz.presenter.CommentPresenter;
import com.metshow.bz.presenter.FollowPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.metshow.bz.util.DialogFactory;
import com.metshow.bz.util.EditDialog;
import com.metshow.bz.util.ShareUtil;
import com.yongchun.library.utils.FileUtils;
import com.yongchun.library.view.ImageSelectorActivity;
import com.zhy.autolayout.AutoFrameLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.kwan.base.activity.BaseWebActivity.MODE_CONTENT;

public class BZActivityInfoActivity extends BaseCommonActivity implements OnRefreshListener, OnLoadMoreListener, BZContract.IBZActivityInfoView, BZContract.IFollowView, BZContract.ICommentView {

	private ImageView iv_icon, iv_camera, iv_share;
	private TextView tv_description, tv_content, tv_more, tv_morepic, tv_send;
	private AutoFrameLayout fl_content;
	private TabLayout tabLayout;
	private ArrayList<String> titles;
	private TabLayout.Tab[] tabs;
	private int current;
	private BzActivity mBzActivity;
	private SwipeToLoadLayout swipeRefreshLayout;
	private BZActivityInfoPresenter mBzActivityInfoPresenter;

	private CommentPresenter mCommentPresenter;
	private ObservableRecyclerView rl_topic, rl_comment, rl_pic;
	private ArrayList<Topic> mTopics = new ArrayList<>();

	private ArrayList<String> mPics = new ArrayList<>();
	private ArrayList<Comment> mComments = new ArrayList<>();

	private FollowPresenter mFollowPresenter;

	private FindTopicListAdapter topicAdapter;
	private CommentAdapter mCommentAdapter;

	private GrandPicAdapter picAdapter;

	private EditDialog mEditDialog;
	private View infoView;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mBzActivity = (BzActivity) getIntentData("data");
		mBzActivityInfoPresenter = new BZActivityInfoPresenter(this);
		mFollowPresenter = new FollowPresenter(this);
		mCommentPresenter = new CommentPresenter(this);
		Log.e("kwan", mBzActivity.toString());
	}


	@Override
	protected int getContentViewId() {
		return R.layout.activity_bzinfo;
	}

	@Override
	protected void initViews() {
		super.initViews();

		tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		iv_camera = (ImageView) findViewById(R.id.iv_camera);
		iv_share = (ImageView) findViewById(R.id.iv_share);
		tv_description = (TextView) findViewById(R.id.tv_description);
		tv_content = (TextView) findViewById(R.id.tv_content);
		fl_content = (AutoFrameLayout) findViewById(R.id.fl_content);
		swipeRefreshLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
		tv_more = (TextView) findViewById(R.id.tv_more);

		tv_more.setOnClickListener(this);
		iv_camera.setOnClickListener(this);
		iv_share.setVisibility(View.GONE);
		rl_topic = new ObservableRecyclerView(this);

		setUpTopicView();
		setUpInfoView();
	}

	private void setUpInfoView() {

		infoView = getLayoutInflater().inflate(R.layout.layout_bzactivity_info, null);
		tv_morepic = (TextView) infoView.findViewById(R.id.tv_more);
		tv_morepic.setOnClickListener(this);

		tv_send = (TextView) infoView.findViewById(R.id.tv_send);
		tv_send.setOnClickListener(this);

		rl_pic = (ObservableRecyclerView) infoView.findViewById(R.id.rl_pic);
		rl_pic.setLayoutManager(new GridLayoutManager(this, 4));
		rl_pic.setHasFixedSize(true);
		rl_pic.setNestedScrollingEnabled(false);
		picAdapter = new GrandPicAdapter(this, mPics);
		rl_pic.setAdapter(picAdapter);

		rl_comment = (ObservableRecyclerView) infoView.findViewById(R.id.rl_comment);
		rl_comment.setLayoutManager(new LinearLayoutManager(this));
		rl_comment.setHasFixedSize(true);
		rl_comment.setNestedScrollingEnabled(false);

		mCommentAdapter = new CommentAdapter(this, mComments);
		rl_comment.setAdapter(mCommentAdapter);
	}

	private void setUpTopicView() {
		rl_topic.setNestedScrollingEnabled(false);
		rl_topic.setHasFixedSize(true);
		rl_topic.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


		topicAdapter = new FindTopicListAdapter(this, mTopics);
		topicAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
		topicAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
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
								mBzActivityInfoPresenter.addAcition(1, topic.getArticleId(), position);
							} else {
								//取消收藏
								showProgress("");
								mBzActivityInfoPresenter.deleteFav(3, topic.getArticleId(), position, 0);
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
		});
		topicAdapter.setFollowPresenter(mFollowPresenter);

		rl_topic.setAdapter(topicAdapter);
	}

	@Override
	protected void initViewSetting() {
		super.initViewSetting();


		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setOnLoadMoreListener(this);

		titles = new ArrayList<>();

		titles.add("参与活动");
		titles.add("活动详情");

		TabLayout.Tab tab1 = tabLayout.newTab().setCustomView(R.layout.tab_with_num);
		TabLayout.Tab tab2 = tabLayout.newTab().setCustomView(R.layout.tab_with_num);

		tabLayout.addTab(tab1);
		tabLayout.addTab(tab2);

		tabs = new TabLayout.Tab[]{tab1, tab2};
		initTabs();

		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				current = tab.getPosition();
				((TextView) tab.getCustomView().findViewById(R.id.tab_name)).setTextColor(getResources().getColor(R.color.redWin));
				showNavigation(current);
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
				((TextView) tab.getCustomView().findViewById(R.id.tab_name)).setTextColor(getResources().getColor(R.color.txt_cobalt_blue));
			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		mEditDialog = new EditDialog(this) {
			@Override
			protected void onSend(String s) {
				toastMsg(s);
			}

			@Override
			protected void onSelectImageBack(String s) {
			}
		};
	}

	private void showNavigation(int index) {

		if (index == 0) {

			if (mTopics.size() == 0) {
				showProgress("");
				mBzActivityInfoPresenter.getTopic("0", 20, mBzActivity.getTopicId());
			}

			fl_content.removeAllViews();
			fl_content.addView(rl_topic);

		} else {

			fl_content.removeAllViews();
			fl_content.addView(infoView);
		}

	}

	private void initTabs() {

		for (int i = 0; i < tabs.length; i++) {
			View view = tabs[i].getCustomView();
			((TextView) view.findViewById(R.id.tab_name)).setText(titles.get(i));
			if (i == 0)
				((TextView) view.findViewById(R.id.tab_name)).setTextColor(getResources().getColor(R.color.redWin));
		}

	}

	@Override
	protected void initData() {
		swipeRefreshLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
			}
		}, 1);
	}


	@Override
	protected String getTitleTxt() {
		return "活动详情";
	}


	@Override
	protected int getTitleBarRightLayoutId() {
		return R.layout.title_right_follow;
	}

	@Override
	protected void setUpTitleRightView(View v) {
		super.setUpTitleRightView(v);

		((ImageView) v).setImageResource(R.mipmap.icon_me_share);
		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareUtil util = new ShareUtil(BZActivityInfoActivity.this);
				util.setmText(mBzActivity.getDescription());
				util.setStr_title(mBzActivity.getName());
				util.setStr_url("http://bz.metshow.com/api/2017/activity_details.html?id="+mBzActivity.getTopicId());
				util.setStr_img("");

				util.showShareDialog();
			}
		});
	}

	@Override
	public void onRefresh() {
		showProgress("");
		mBzActivityInfoPresenter.getBZActivityInfo(mBzActivity.getTopicId());
	}

	@Override
	public void onLoadMore() {
		swipeRefreshLayout.setLoadingMore(false);
	}

	@Override
	public void getBZActivityInfoSuccess(BzActivity info) {
		dismissProgress();
		mBzActivity = info;
		tv_content.setText(info.getDescription());
		tv_description.setText(info.getName());

		if (info.getPic().endsWith(".gif"))
			mImageUtil.loadGifFromUrl(info.getPic(), iv_icon);
		else

			Glide.with(this)
					.load(info.getPic())
					.asBitmap()
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					.fitCenter()
					.placeholder(R.mipmap.item_default)
					.into(new ViewTarget<ImageView, Bitmap>(iv_icon) {
						@Override
						public void onLoadStarted(Drawable placeholder) {
							iv_icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
							iv_icon.setImageDrawable(placeholder);
						}

						@Override
						public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
							iv_icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
							iv_icon.setImageBitmap(resource);
						}
					});


		mPics.addAll(Arrays.asList(mBzActivity.getAllPics().split(",")));
		ArrayList<String> pic = new ArrayList<>();
		if (mPics.size() > 4) {

			for (int i = 0; i < 4; i++)
				pic.add(mPics.get(i));


		} else {
			pic.addAll(mPics);
		}
		picAdapter.setNewData(pic);
		Log.e("kwan", "getArticleId::" + mBzActivity.getTopicId());
		mCommentPresenter.getCommentList(5, mBzActivity.getTopicId(), "0");

	}

	@Override
	public void getTopicSuccess(List<Topic> items) {

		mTopics.addAll(items);
		topicAdapter.setNewData(items);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v == tv_more) {

			Bundle bundle = new Bundle();
			bundle.putString(Config.INTENT_KEY_TITLE, "活动详情");
			bundle.putString(Config.INTENT_KEY_DATA, mBzActivity.getContent());
			bundle.putInt(Config.INTENT_KEY_MODE, MODE_CONTENT);
			go2ActivityWithLeft(WebActivity.class, bundle, false);

		} else if (v == tv_send) {
			mEditDialog.show();
		} else if (v == tv_morepic) {
			Bundle bundle = new Bundle();
			bundle.putSerializable("data", mPics);
			go2ActivityWithLeft(GrandPicActivity.class, bundle, false);
		} else if (v == iv_camera) {
			showTopicDialog();
		}else{
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

	@Override
	public void getFollowListSuccess(List<FollowItem> followItems) {

	}

	@Override
	public void getBeFollowListSuccess(List<FollowItem> followItems, String serverTime) {

	}

	@Override
	public void getDeleteFollowSuccess(Boolean result, int position) {

	}

	@Override
	public void addFollowSuccess(int position) {

	}

	@Override
	public void getMyCommentSuccess(List<Comment> comments) {

	}

	@Override
	public void getCommentListSuccess(List<Comment> comments) {
		mComments.addAll(comments);
		mCommentAdapter.setNewData(comments);

		showNavigation(0);
		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setLoadingMore(false);
	}

	@Override
	public void addCommnetSuccess(int result) {

	}

	@Override
	public void reportSuccess(Integer integer) {

	}

	@Override
	protected void onSoftKeyBoardVisible(boolean visible) {
		super.onSoftKeyBoardVisible(visible);
		if (!visible)
			mEditDialog.cancel();
	}

	private Dialog mSendTopicDialog;
	private final int CAMERA_REQUEST_CODE = 1;
	private String cameraPath;
	private void showTopicDialog() {

		if(!SPUtil.getIsLogin()){
			go2ActivityWithLeft(DologinActivity.class,null,false);
			return;
		}

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
				toastMsg( "没有相机权限");
			}
		}
	}


	private void starCam() {

		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File cameraFile = FileUtils.createCameraFile(this);
		cameraPath = cameraFile.getAbsolutePath();

		i.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
		i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
		startActivityForResult(i, CAMERA_REQUEST_CODE);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CAMERA_REQUEST_CODE) {
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(cameraPath))));
			ArrayList<String> output = new ArrayList<>();
			output.add(cameraPath);
			Bundle bundle = new Bundle();
			bundle.putInt("mode", 2);
			bundle.putStringArrayList("output", output);

			go2Activity(SendTopicActivity.class, bundle, false);
		}
	}
}
