package com.metshow.bz.module.shai;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.library.bubbleview.BubbleImageView;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.SPUtil;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.adapter.CommentAdapter;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.article.ArticlePicture;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.commons.bean.topic.Topic;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.activity.BaseSendActivity;
import com.metshow.bz.module.commons.activity.CommentActivity;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.metshow.bz.module.commons.activity.ImageActivity;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.presenter.CommentPresenter;
import com.metshow.bz.presenter.TopicPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.metshow.bz.util.DialogFactory;
import com.metshow.bz.util.ShareUtil;
import com.metshow.bz.util.TopicMenuUtil;
import com.tencent.smtt.sdk.VideoActivity;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class TopicActivity extends BaseSendActivity implements BZContract.ITopicView, BZContract.ITopicMenuView, OnRefreshListener, BZContract.ICommentView {

	private long mTopicId;
	private TopicPresenter mPresenter;
	private ImageView iv_avatar;
	private TextView tv_name, tv_follow, tv_icon_num, tv_content, tv_fav, tv_pl, tv_rank, tv_time;
	private ImageView iv_icon, iv_fav, iv_pl, iv_more, iv_isVip;
	private AutoLinearLayout ll_tag;
	//private ScrollView swipe_target;
	private SwipeToLoadLayout swipeToLoadLayout;
	private Topic mTopic;
	private AutoLinearLayout ll_user;
	private AutoFrameLayout fl_icon;
	private CommentPresenter mCommentPresenter;
	private ObservableRecyclerView rl_comment;
	private ArrayList<Comment> mComments = new ArrayList<>();
	private CommentAdapter mCommentAdapter;
	private ImageView iv_share;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mTopicId = (long) getIntentData("topicid");
		mPresenter = new TopicPresenter(this);
		mCommentPresenter = new CommentPresenter(this);
	}


	@Override
	protected int getContentViewId() {
		return R.layout.activity_topic;
	}

	@Override
	protected void initViews() {
		super.initViews();

		swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
		iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_rank = (TextView) findViewById(R.id.tv_rank);
		iv_isVip = (ImageView) findViewById(R.id.iv_isVip);
		tv_follow = (TextView) findViewById(R.id.tv_follow);
		tv_icon_num = (TextView) findViewById(R.id.tv_icon_num);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_fav = (TextView) findViewById(R.id.tv_fav);
		tv_pl = (TextView) findViewById(R.id.tv_pl);
		iv_share= (ImageView) findViewById(R.id.iv_share);
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		iv_fav = (ImageView) findViewById(R.id.iv_fav);
		iv_pl = (ImageView) findViewById(R.id.iv_pl);
		iv_more = (ImageView) findViewById(R.id.iv_more);
		ll_tag = (AutoLinearLayout) findViewById(R.id.ll_tag);
		//swipe_target = (ScrollView) findViewById(R.id.swipe_target);

		ll_user = (AutoLinearLayout) findViewById(R.id.ll_user);
		fl_icon = (AutoFrameLayout) findViewById(R.id.fl_icon);
		rl_comment = (ObservableRecyclerView) findViewById(R.id.rl_comment);
		tv_time = (TextView) findViewById(R.id.tv_time);


	}

	@Override
	protected void initViewSetting() {
		super.initViewSetting();

		tv_name.setTypeface(Typeface.DEFAULT_BOLD);
		tv_content.setTypeface(Typeface.DEFAULT_BOLD);

		swipeToLoadLayout.setOnRefreshListener(this);

		iv_icon.setOnClickListener(this);
		tv_name.setOnClickListener(this);
		iv_avatar.setOnClickListener(this);
		tv_content.setOnClickListener(this);
		tv_follow.setOnClickListener(this);

		iv_more.setOnClickListener(this);

		tv_pl.setOnClickListener(this);
		iv_pl.setOnClickListener(this);
		iv_fav.setOnClickListener(this);
		tv_fav.setOnClickListener(this);
		iv_share.setOnClickListener(this);

		mCommentAdapter = new CommentAdapter(this, mComments);
		rl_comment.setNestedScrollingEnabled(false);
		rl_comment.setHasFixedSize(true);
		rl_comment.setLayoutManager(new LinearLayoutManager(this));
		mCommentAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
			@Override
			public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
				if (view.getId() == R.id.ll_more) {

					final Comment comment = mComments.get(position);

					View parent = getLayoutInflater().inflate(R.layout.menu_comment, null, false);
					final Dialog mDialog = DialogFactory.showMenuDialog(TopicActivity.this, parent);
					mDialog.setCanceledOnTouchOutside(true);

					TextView tv_cancel = (TextView) parent.findViewById(R.id.tv_cancel);
					TextView tv_report = (TextView) parent.findViewById(R.id.tv_report);

					tv_cancel.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							mDialog.dismiss();
						}
					});

					tv_report.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							mDialog.dismiss();
							mCommentPresenter.reportComment(comment.getCommentId());
							showProgress("");
						}
					});

				}
			}
		});
		rl_comment.setAdapter(mCommentAdapter);
	}


	User user;
	long ParentId, toUserId;
	//int refid;
	String toNikename = "";
	String imgContent;
	Comment sendComment;

	@Override
	protected void onSend(String content) {

		if (user == null)
			user = BZSPUtil.getUser();

		sendComment = new Comment();
		sendComment.setNickName(user.getNickName());
		sendComment.setAvatar(user.getAvatar());
		sendComment.setParentId(ParentId);
		sendComment.setContent(content);
		sendComment.setRefId(mTopicId);
		sendComment.setType(3);
		sendComment.setUserId(user.getUserId());
		sendComment.setToNickName(toNikename);
		sendComment.setToUserId(toUserId);
		sendComment.setCreateDate("/Date(" + (System.currentTimeMillis() + (8 * 60 * 60 * 1000)) + ")/");

		if (imgContent != null && !imgContent.isEmpty()) {
			//先上传图片 获取图片的url
			showProgress("发送中..");
			mCommentPresenter.upLoadImag(imgContent, content, mTopicId, 3, ParentId);
			fl_pop.setVisibility(View.GONE);
			sendComment.setImage(imgContent);
			imgContent = null;

		} else {
			//直接发送文字评论
			showProgress("发送中..");
			mCommentPresenter.addComment(content, null, mTopicId, 3, ParentId);
		}


	}

	@Override
	protected void onSelectImageBack(String s) {
		imgContent = s;
		showImgPopup(imgContent);
	}

	private void showImgPopup(String imgPath) {

		fl_pop.setVisibility(View.VISIBLE);
		BubbleImageView imageView = (BubbleImageView) fl_pop.findViewById(R.id.iv_content);
		mImageUtil.loadFromFile(imgPath, imageView);
	}


	@Override
	protected void initData() {
		swipeToLoadLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeToLoadLayout.setRefreshing(true);
			}
		});
	}

	@Override
	protected String getTitleTxt() {
		return "帖子详情";
	}

	@Override
	protected int getTitleBarRightLayoutId() {
		return 0;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v == iv_icon) {
			toTopicDetail(mTopic);
		} else if (v == iv_avatar || v == tv_name) {

			Bundle bundle = new Bundle();
			bundle.putLong("userid", mTopic.getUserId());
			bundle.putInt("isFollow", mTopic.getIsFollow());
			go2ActivityWithLeft(FriendDetailActivity.class, bundle, false);

		} else if (v == tv_follow) {

			if (SPUtil.getIsLogin()) {
			showProgress("");
			mPresenter.addFollow(mTopic.getUserId(), 0);
			} else {
				go2Activity(DologinActivity.class, null, false);
			}
		} else if (v == iv_fav || v == tv_fav) {
			if (SPUtil.getIsLogin()) {
				showProgress("");
				if (mTopic.getIsFav() > 0)
					mPresenter.deteleFav(mTopic.getArticleId(), 0);
				else
					mPresenter.addFav(mTopic.getArticleId(), 0);
			} else {
				go2Activity(DologinActivity.class, null, false);
			}
		} else if (v == iv_pl || v == tv_pl) {
			if (SPUtil.getIsLogin()) {
				Bundle bundle = new Bundle();
				bundle.putInt("type", CommentActivity.TYPE_TOPIC);
				bundle.putLong("refid", mTopic.getArticleId());
				go2ActivityWithLeft(CommentActivity.class, bundle, false);
			} else {
				go2Activity(DologinActivity.class, null, false);
			}
		} else if (v == tv_content) {

			Bundle bundle = new Bundle();
			bundle.putInt("type", TopicListActivity.MODE_USER);
			bundle.putLong("userid", mTopic.getUserId());
			bundle.putString("name", mTopic.getAuthor());
			go2ActivityWithLeft(TopicListActivity.class, bundle, false);

		} else if (v == iv_more) {
			new TopicMenuUtil(this, mTopic, this, 0).showShareDialog();
		}else if(v==iv_share){
			ShareUtil shareUtil = new ShareUtil(this);
			shareUtil.setStr_title("时尚芭莎的精彩瞬间");
			shareUtil.setStr_img(mTopic.getIco());
			shareUtil.setmText("快来芭莎in晒晒你的时尚朋友圈，越晒越时髦");
			shareUtil.setStr_url("http://bz.metshow.com/api/2017/sticker_details.html?id="+mTopicId);
			shareUtil.showShareDialog();
		}
	}


	@Override
	public void getTopicDetailSuccess(Topic topic) {
		mTopic = topic;
		loadViewData(topic);
		mCommentPresenter.getCommentList(3, mTopic.getArticleId(), "0");

	}

	@Override
	public void addFollowSuccess(String result, int sition) {
		mTopic.setIsFollow(1);
		tv_follow.setBackgroundColor(getResources().getColor(R.color.txt_cobalt_blue));
		tv_follow.setText("已关注");
		tv_follow.setTextColor(Color.WHITE);
		tv_follow.setEnabled(false);
		toastMsg("已关注");
		dismissProgress();
	}

	@Override
	public void favSuccess(String result, int position) {

		mTopic.setIsFav(1);
		iv_fav.setImageResource(R.mipmap.article_fav_icon);
		tv_fav.setTextColor(getResources().getColor(R.color.redWin));


		tv_fav.setText(result+"人喜欢");
		mTopic.setFavCount(Integer.valueOf(result));
		dismissProgress();
	}

	@Override
	public void deleteFavSuccess(String result, int position) {

		mTopic.setIsFav(0);
		iv_fav.setImageResource(R.mipmap.article_fav_normal_icon);
		tv_fav.setTextColor(getResources().getColor(R.color.txt_gray));
		tv_fav.setText(mTopic.getFavCount() - 1 + "人喜欢");
		mTopic.setFavCount(mTopic.getFavCount() - 1);
		dismissProgress();
	}

	@Override
	public void onServerError(int vocational_id, Throwable e) {
		super.onServerError(vocational_id, e);
		swipeToLoadLayout.setRefreshing(false);

	}

	@Override
	public void onServerFailed(String s) {
		super.onServerFailed(s);
		toastMsg(s);
		swipeToLoadLayout.setRefreshing(false);
	}

	private void loadViewData(Topic topic) {

		if (topic != null) {

			mImageUtil.loadFromUrlWithCircle(topic.getAuthorIco(), iv_avatar);
			final int width = App.SCREEN_WIDTH;
			Glide.with(getApplication())
					.load(topic.getIco())
					.asBitmap()
					.placeholder(R.mipmap.item_default)
					.override(width, BitmapImageViewTarget.SIZE_ORIGINAL)
					.into(new SimpleTarget<Bitmap>() {

						public void onLoadStarted(Drawable placeholder) {
							iv_icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
							iv_icon.setImageDrawable(placeholder);
						}

						@Override
						public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
							float scale = resource.getHeight() / (resource.getWidth() * 1.0f);
							int viewHeight = (int) (width * scale);
							iv_icon.setLayoutParams(new FrameLayout.LayoutParams(width, viewHeight));
							iv_icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
							iv_icon.setImageBitmap(resource);
						}
					});

			tv_name.setText(topic.getAuthor());

			if (topic.getRank() == null || topic.getRank().isEmpty())
				tv_rank.setVisibility(View.GONE);
			else
				tv_rank.setText(topic.getRank());

			if (topic.getIsVip() == 0)
				iv_isVip.setVisibility(View.GONE);

			if (topic.getIsFollow() > 0 || topic.getUserId() == BZSPUtil.getUser().getUserId())
				tv_follow.setVisibility(View.GONE);

			if(topic.getArticlePictures().size()>1) {
				tv_icon_num.setText(topic.getArticlePictures().size() + "");
				tv_icon_num.setVisibility(View.VISIBLE);
			}else
				tv_icon_num.setVisibility(View.GONE);

			if (topic.getContent() != null && !topic.getContent().isEmpty())
				tv_content.setText(topic.getContent());
			else
				tv_content.setVisibility(View.GONE);

			tv_time.setText(BasePresenter.getStrData(topic.getPublishDate()));

			String strTags = topic.getTags();

			if (strTags != null && !strTags.isEmpty()) {
				String[] tags = strTags.split(",");
				for (String tag : tags) {
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					lp.setMargins(0, 0, 20, 0);
					TextView textView = new TextView(this);
					textView.setLayoutParams(lp);
					textView.setText(tag);
					textView.setTextSize(10);
					textView.setTextColor(getResources().getColor(R.color.redWin));
					textView.setBackgroundResource(R.drawable.shape_corner_light_gray_bg);
					textView.setPadding(20, 5, 20, 5);
					ll_tag.addView(textView);
				}
			} else {
				ll_tag.setVisibility(View.GONE);
			}

			if (topic.getIsFav() == 0) {
				iv_fav.setImageResource(R.mipmap.article_fav_normal_icon);
				tv_fav.setTextColor(getResources().getColor(R.color.txt_gray));
			} else {
				iv_fav.setImageResource(R.mipmap.article_fav_icon);
				tv_fav.setTextColor(getResources().getColor(R.color.redWin));
			}

			tv_fav.setText(topic.getFavCount() + "人喜欢");
			tv_pl.setText(topic.getCommentCount() + "条评论");

			ll_user.setVisibility(View.VISIBLE);

		} else {
			toastMsg("帖子不存在");
			new Handler(getMainLooper()).postDelayed(new Runnable() {
				@Override
				public void run() {
					onBackPressed();
				}
			}, 1000);

		}
	}

	private void toTopicDetail(Topic topic) {

		int type = topic.getArticleType();
		Bundle bundle = new Bundle();

		switch (type) {
			//视频
			case 6:
				bundle.putString("url", topic.getVideoUrl());
				go2Activity(VideoActivity.class, bundle, false);
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
				go2Activity(ImageActivity.class, bundle, false);
				break;
		}
	}

	@Override
	public void deleteFollowSuccess(Boolean result, int position) {
		if (result) {
			tv_follow.setVisibility(View.VISIBLE);
			mTopic.setIsFollow(0);
			toastMsg("已取消关注");
		} else {
			toastMsg("取消失败");
		}
		dismissProgress();
	}

	@Override
	public void disViewSuccess(int result, int position) {
		if (result == 1) {
			toastMsg("已屏蔽");
			onBackPressed();
		} else {
			toastMsg("屏蔽失败");
		}
		dismissProgress();
	}

	@Override
	public void reportSuccess(Boolean result, int position) {
		if (result) {
			toastMsg("举报成功");
		} else {
			toastMsg("举报失败");
		}
		dismissProgress();
	}

	@Override
	public void deleteTopicSuccess(Boolean result, int position) {
		if (result) {
			toastMsg("已删除");
			onBackPressed();
		} else {
			toastMsg("删除失败");
		}
		dismissProgress();
	}



	@Override
	public void onRefresh() {
		mPresenter.getTopicDetail(mTopicId, SPUtil.getIsLogin());
	}

	@Override
	public void getMyCommentSuccess(List<Comment> comments) {

	}

	@Override
	public void getCommentListSuccess(List<Comment> comments) {
		mComments.addAll(comments);

		mCommentAdapter.setNewData(comments);

		swipeToLoadLayout.setRefreshing(false);
	}

	@Override
	public void addCommnetSuccess(int result) {

		sendComment.setCommentId(result);

		if (mComments.size() == 0) {
			mComments.add(sendComment);
			mCommentAdapter.setNewData(mComments);

		} else {
			mComments.add(sendComment);
			mCommentAdapter.add(0, sendComment);
		}

		rl_comment.scrollVerticallyTo(0);

		//sendedComment.add(sendComment);
		dismissProgress();

	}

	@Override
	public void reportSuccess(Integer integer) {
toastMsg("举报成功");
	}
}
