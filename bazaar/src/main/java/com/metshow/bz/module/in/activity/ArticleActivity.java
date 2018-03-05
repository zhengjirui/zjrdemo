package com.metshow.bz.module.in.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.github.library.bubbleview.BubbleImageView;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.kwan.base.activity.BaseActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.SPUtil;
import com.kwan.base.widget.SlLinearLayoutManager;
import com.kwan.base.widget.observablescrollview.ObservableRecyclerView;
import com.metshow.bz.IUmengEvent;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.adapter.ChatAdapter;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.commons.bean.article.Article;
import com.metshow.bz.commons.bean.topic.Comment;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.activity.ChatActivity;
import com.metshow.bz.module.commons.activity.CommentActivity;
import com.metshow.bz.module.login.DologinActivity;
import com.metshow.bz.presenter.ArticlePresenter;
import com.metshow.bz.presenter.CommentPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.metshow.bz.util.EditDialog;
import com.metshow.bz.util.ShareUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.yongchun.library.view.ImageSelectorActivity;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pl.droidsonroids.gif.GifDrawable;


public class ArticleActivity extends BaseActivity implements BZContract.IArticleView, BZContract.ICommentView {

	private boolean isFav;
	private ObservableRecyclerView mRVRelatedArticles;
	private ObservableRecyclerView mRVComment;
	private NestedScrollView ns_content;
	private AutoLinearLayout toolbar, ll_commentandrelate;
	private AutoFrameLayout fl_load;
	private long id;
	/**
	 * 顶部图片地址
	 */

	private String icon;
	/**
	 * 顶部图片控件
	 */
	private ImageView iv_icon;

	private ArticlePresenter mPresenter;
	private Article mArticle;
	private TextView tv_newcomment, tv_hotcomment, tv_title, tv_comment_tag, tv_send;

	private BaseQuickAdapter<Article> mRelateAdapter;
	private BaseQuickAdapter<Comment> mCommtentAdapter;

	private List<Article> mRelativeArticles;
	private List<Comment> newComment, hotComment;

	private int newCommentIndex = 0;
	private int hotCommentIndex = 1;
	private int CurrentCommnet = newCommentIndex;

	private TextView tv_name, tv_rank, tv_chat, tv_comment, tv_fav, tv_time;
	private ImageView iv_avatar;
	private ImageView iv_isVip, iv_back, iv_fav, iv_comment, iv_resend;

	private AutoLinearLayout ll_comment, ll_fav;

	private long ParentId;
	private String toNikename;
	private long toUserId;
	protected AutoFrameLayout fl_pop;
	protected ImageView iv_delete;
	protected ArrayList<String> mFaceMapKeys;
	protected boolean mIsFaceShow = false;
	protected ViewPager mFaceViewPager;
	public static final int NUM_PAGE = 6;
	public static final int NUM = 20;
	protected final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
	private boolean isShowKeyBoard = false;
	private User user;
	private Comment sendComment;
	private String imgContent;
	private CommentPresenter mCommentPresenter;
	private boolean isbackimag = false;
	private ExitActivityTransition exitTransition;

	private android.os.Handler handler = new android.os.Handler();
	private EditDialog mEditDialog;

	public static void go2Article(int ArticleType, BaseActivity activity, Bundle bundle, boolean isAnim, boolean isFinish) {

		Intent intent = new Intent();

		if (bundle != null)
			intent.putExtras(bundle);

		//图组
		if (ArticleType == 7) {
			intent.setClass(activity, ArticlePicGroupActivity.class);
			activity.startActivity(intent);
			activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

		} else {
			intent.setClass(activity, ArticleActivity.class);
			activity.startActivity(intent);
			if (isAnim)
				activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}

		if (isFinish)
			activity.onBackPressed();

	}

	/**
	 * 进入界面后执行动画
	 *
	 * @param savedInstanceState
	 */
	@Override
	public void into(Bundle savedInstanceState) {
		super.into(savedInstanceState);

		Intent intent = getIntent();
		View view = findViewById(R.id.main_content);

		exitTransition = ActivityTransition.with(intent).
				to(view).
				duration(500).
				start(savedInstanceState);

//      transition.to(findViewById(R.id.fl_load));
//      transition.start(savedInstanceState);

	}

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();

		mPresenter = new ArticlePresenter(this);
		mCommentPresenter = new CommentPresenter(this);

		id = (long) getIntentData("id");
		icon = (String) getIntentData("icon");
		isFav = (boolean) getIntentData("isFav");

		// 将表情map的key保存在数组中
		Set<String> keySet = App.getInstance().getFaceMap().keySet();
		mFaceMapKeys = new ArrayList<>();
		mFaceMapKeys.addAll(keySet);

		App.umengEvent(this, IUmengEvent.ArticleDetailView, String.valueOf(id));
	}

	@Override
	public boolean isFullScreen() {
		return true;
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_articel;
	}

	@Override
	protected int getBottomViewId() {
		return R.layout.articel_bottom;
	}

	@Override
	protected int getTitleBarViewId() {
		return 0;
	}

	@Override
	protected void initViews() {
		Log.d("initViews", "initViews");

		ll_commentandrelate = (AutoLinearLayout) findViewById(R.id.ll_commentandrelate);

		mRVRelatedArticles = (ObservableRecyclerView) findViewById(R.id.rv_relate);
		mRVComment = (ObservableRecyclerView) findViewById(R.id.rv_comment);

		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		toolbar = (AutoLinearLayout) findViewById(R.id.toolbar);
		fl_load = (AutoFrameLayout) findViewById(R.id.fl_load);
		//ll_content = (AutoLinearLayout) findViewById(R.id.ll_content);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_newcomment = (TextView) findViewById(R.id.tv_newcomment);
		tv_hotcomment = (TextView) findViewById(R.id.tv_hotcomment);
		tv_comment_tag = (TextView) findViewById(R.id.tv_comment_tag);
		ns_content = (NestedScrollView) findViewById(R.id.ns_content);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_rank = (TextView) findViewById(R.id.tv_rank);
		tv_chat = (TextView) findViewById(R.id.tv_chat);
		iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
		iv_isVip = (ImageView) findViewById(R.id.iv_isVip);

		tv_send = (TextView) findViewById(R.id.tv_send);

		tv_name.setTypeface(Typeface.DEFAULT_BOLD);
		//tv_chat.setTypeface(Typeface.DEFAULT_BOLD);

		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_fav = (ImageView) findViewById(R.id.iv_fav);
		iv_comment = (ImageView) findViewById(R.id.iv_comment);
		iv_resend = (ImageView) findViewById(R.id.iv_resend);

		ll_comment = (AutoLinearLayout) findViewById(R.id.ll_comment);
		ll_fav = (AutoLinearLayout) findViewById(R.id.ll_fav);

		tv_comment = (TextView) findViewById(R.id.tv_comment);
		tv_fav = (TextView) findViewById(R.id.tv_fav);

		fl_pop = (AutoFrameLayout) findViewById(R.id.fl_pop);
//		ll_edit = (AutoLinearLayout) findViewById(R.id.ll_edit);
//		et_content = (EditText) findViewById(R.id.et_content);
//		iv_emo = (ImageView) findViewById(R.id.iv_emo);
//		iv_camera = (ImageView) findViewById(R.id.iv_camera);
//		mFaceRoot = (AutoLinearLayout) findViewById(R.id.ll_face);
		mFaceViewPager = (ViewPager) findViewById(R.id.face_pager);
		iv_delete = (ImageView) findViewById(R.id.iv_delete);

		//进入界面后加载顶部图片
		if (icon != null && !icon.isEmpty())

			if (icon.endsWith(".gif")){

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
					if (isDestroyed())
						return;
				}
				Glide.with(this)
						.load(icon)
						.asGif()
						.toBytes()
						.error(R.mipmap.item_default)
						.diskCacheStrategy(DiskCacheStrategy.SOURCE)
						.into(new SimpleTarget<byte[]>() {

							@Override
							public void onLoadStarted(Drawable placeholder) {
								super.onLoadStarted(placeholder);
								iv_icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
								iv_icon.setImageDrawable(placeholder);
							}

							@Override
							public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {

								try {
									GifDrawable gifFromBytes = new GifDrawable(resource);
									float scale = gifFromBytes.getMinimumHeight() / (gifFromBytes.getMinimumWidth() * 1.0f);
									int viewHeight = (int) (App.SCREEN_WIDTH * scale);
									iv_icon.setLayoutParams(new AutoFrameLayout.LayoutParams(App.SCREEN_WIDTH, viewHeight));
									iv_icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
									iv_icon.setImageDrawable(gifFromBytes);

									AutoFrameLayout.LayoutParams lp = new AutoFrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
									lp.topMargin = viewHeight;
									tv_title.setLayoutParams(lp);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						});
			} else {
				Glide.with(this)
						.load(icon)
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

								float scale = resource.getHeight() / (resource.getHeight() * 1.0f);
								int viewHeight = (int) (App.SCREEN_WIDTH * scale);
								iv_icon.setLayoutParams(new AutoFrameLayout.LayoutParams(App.SCREEN_WIDTH, viewHeight));

								iv_icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
								iv_icon.setImageBitmap(resource);

								AutoFrameLayout.LayoutParams lp = new AutoFrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
								lp.topMargin = viewHeight;
								tv_title.setLayoutParams(lp);
							}
						});

			}


		//initFacePage();
	}

	@Override
	protected void initViewSetting() {


		ll_base_main.setFitsSystemWindows(false);
		tv_newcomment.setOnClickListener(this);
		tv_hotcomment.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		iv_resend.setOnClickListener(this);
		ll_comment.setOnClickListener(this);
		ll_fav.setOnClickListener(this);
		tv_send.setOnClickListener(this);
		//iv_icon.setVisibility(View.GONE);
		mRVRelatedArticles.setLayoutManager(new SlLinearLayoutManager(this, SlLinearLayoutManager.VERTICAL, false));
		mRVRelatedArticles.setNestedScrollingEnabled(false);
		mRVComment.setLayoutManager(new SlLinearLayoutManager(this, SlLinearLayoutManager.VERTICAL, false));
		mRVComment.setNestedScrollingEnabled(false);

//		iv_emo.setOnClickListener(this);
//		iv_camera.setOnClickListener(this);


//		et_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//			@Override
//			public boolean onEditorAction(TextView v, int actionId,
//										  KeyEvent event) {
//				if (actionId == EditorInfo.IME_ACTION_SEND) {
//
//					onSend(et_content.getText().toString());
//					et_content.setText("");
//					recycleMoveEnd();
//				}
//				return false;
//			}
//		});
//
//		et_content.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View view, MotionEvent motionEvent) {
//				iv_emo.setImageResource(R.mipmap.zan_normal_icon);
//				mIsFaceShow = false;
//				mFaceRoot.setVisibility(View.GONE);
//				return false;
//			}
//		});

		mEditDialog = new EditDialog(this) {
			@Override
			protected void onSend(String s) {
				onSendActivtyComment(s);
			}

			@Override
			protected void onSelectImageBack(String s) {
			}
		};
		Log.e("initViewSetting", "initViewSetting");
	}

	private void onSendActivtyComment(String content) {

		if (user == null)
			user = BZSPUtil.getUser();

		sendComment = new Comment();
		sendComment.setNickName(user.getNickName());
		sendComment.setAvatar(user.getAvatar());
		sendComment.setParentId(ParentId);
		sendComment.setContent(content);
		sendComment.setRefId(id);
		sendComment.setType(2);
		sendComment.setUserId(user.getUserId());
		sendComment.setToNickName(toNikename);
		sendComment.setToUserId(toUserId);
		sendComment.setCreateDate("/Date(" + System.currentTimeMillis() + ")/");

		if (imgContent != null && !imgContent.isEmpty()) {
			//先上传图片 获取图片的url
			showProgress("发送中..");
			mCommentPresenter.upLoadImag(imgContent, content, id, 1, ParentId);
			fl_pop.setVisibility(View.GONE);
			sendComment.setImage(imgContent);
			imgContent = null;
			isbackimag = false;

		} else {
			//直接发送文字评论
			showProgress("发送中..");
			mCommentPresenter.addComment(content, null, id, 1, ParentId);
		}
	}

	private void onSend(String content) {

		if (user == null)
			user = BZSPUtil.getUser();

		sendComment = new Comment();
		sendComment.setNickName(user.getNickName());
		sendComment.setAvatar(user.getAvatar());
		sendComment.setParentId(ParentId);
		sendComment.setContent(content);
		sendComment.setRefId(id);
		sendComment.setType(2);
		sendComment.setUserId(user.getUserId());
		sendComment.setToNickName(toNikename);
		sendComment.setToUserId(toUserId);
		sendComment.setCreateDate("/Date(" + System.currentTimeMillis() + ")/");

		if (imgContent != null && !imgContent.isEmpty()) {
			//先上传图片 获取图片的url
			showProgress("发送中..");
			mCommentPresenter.upLoadImag(imgContent, content, id, 1, ParentId);
			fl_pop.setVisibility(View.GONE);
			sendComment.setImage(imgContent);
			imgContent = null;
			isbackimag = false;

		} else {
			//直接发送文字评论
			showProgress("发送中..");
			mCommentPresenter.addComment(content, null, id, 1, ParentId);
		}

	}

	private void onSelectImageBack(String s) {
		imgContent = s;
		isbackimag = true;
	}

	public void recycleMoveEnd() {
		mRVComment.postDelayed(new Runnable() {
			@Override
			public void run() {
				mRVComment.scrollVerticallyTo(0);
			}
		}, 100);
	}

	@Override
	protected void initData() {
		//  ViewCompat.setTransitionName(findViewById(R.id.iv_icon), "iv_icon");
		//  supportPostponeEnterTransition();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPresenter.getArticleDetail(id);
			}
		}, 500);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		webview = null;
	}

	private com.tencent.smtt.sdk.WebView webview;

	//获取到 服务器返回的 详情的HTml代码 后使用 腾讯的webview框架加载 详情html内容
	protected void onXWalkReady(final String url) {

		webview = (com.tencent.smtt.sdk.WebView) findViewById(R.id.webView);
		WebSettings mSetting = webview.getSettings();
		mSetting.setJavaScriptEnabled(true);
		mSetting.setDefaultTextEncodingName("UTF-8");
		mSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
		if (Build.VERSION.SDK_INT >= 19) {
			mSetting.setLoadsImagesAutomatically(true);
		} else {
			mSetting.setLoadsImagesAutomatically(false);
		}

//        webview.addJavascriptInterface(new ProductsInfoActivityJSService(
//                this),"Javahelper");   // 这在前

		webview.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String s) {
				if (url.startsWith("http:") || url.startsWith("https:")) {
					webView.loadUrl(url);
					return false;
				} else {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
					return true;
				}
			}

			@Override
			public void onPageFinished(com.tencent.smtt.sdk.WebView view, String url) {
				super.onPageFinished(view, url);
				if (!view.getSettings().getLoadsImagesAutomatically()) {
					view.getSettings().setLoadsImagesAutomatically(true);
				}

				ll_commentandrelate.setVisibility(View.VISIBLE);
			}


		});
		webview.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (webview != null) {
					fl_load.setVisibility(View.GONE);
					toolbar.setVisibility(View.VISIBLE);
					ns_content.setVisibility(View.VISIBLE);
					webview.loadDataWithBaseURL("", url, "text/html", "UTF-8", "");
				}
			}
		}, 100);

	}

	@Override
	protected void onResume() {
		super.onResume();

		Log.e("onResume", "onResume");

		if (isbackimag) {
			showImgPopup(imgContent);
		}
	}

	private void showImgPopup(String imgPath) {

		fl_pop.setVisibility(View.VISIBLE);
		BubbleImageView imageView = (BubbleImageView) fl_pop.findViewById(R.id.iv_content);
		mImageUtil.loadFromFile(imgPath, imageView);
	}


	@Override
	protected BasePresenter getPresent() {
		return mPresenter;
	}


	//成功获取到服务器返回的 文字详情 javebean
	@Override
	public void getArticleSuccess(final Article result) {

		mArticle = result;

		tv_name.setText(mArticle.getAuthor());
		if (mArticle.getRank() == null || mArticle.getRank().isEmpty())
			tv_rank.setVisibility(View.GONE);
		else
			tv_rank.setText(mArticle.getRank());
		tv_title.setText(mArticle.getTitle());

		tv_time.setText(BasePresenter.getStrData(mArticle.getCreateDate()));

		tv_chat.setOnClickListener(this);
		mImageUtil.loadFromUrlWithCircle(mArticle.getAuthorIco(), iv_avatar);

		if (mArticle.getIsVip() == 0)
			iv_isVip.setVisibility(View.GONE);

		if (mArticle.getIsFav() > 0 || isFav) {
			iv_fav.setImageResource(R.mipmap.article_fav_icon);
			isFav = true;
		} else {
			isFav = false;
		}

		tv_fav.setText(result.getFavCount() + "");
		tv_comment.setText(result.getCommentCount() + "");

		//推荐文章
		mRelativeArticles = mArticle.getRelatedArticles();

		mRelateAdapter = new BaseQuickAdapter<Article>(this, R.layout.list_item_relate_articel, mRelativeArticles) {
			@Override
			protected void convert(BaseViewHolder helper, Article item, int position) {
				helper.setImageUrl(R.id.iv_icon, item.getIco());
				helper.setText(R.id.tv_content, item.getTitle());
				helper.setText(R.id.tv_fav, item.getFavCount() + "个喜欢");

			}
		};

		//点击推荐文章
		mRelateAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {

				Article article = mRelativeArticles.get(position);
				Bundle bundle = new Bundle();
				bundle.putLong("id", article.getArticleId());
				bundle.putString("icon", article.getIco());
				bundle.putBoolean("isFav", article.getIsFav() > 0);
				go2Activity(ArticleActivity.class, bundle, false);
			}
		});


		mRVRelatedArticles.setAdapter(mRelateAdapter);
		//mRVRelatedArticles.setVisibility(View.GONE);
		newComment = mArticle.getNewCommentList();
		hotComment = mArticle.getHotCommentList();

//		if(newComment.size()>0)
		tv_comment_tag.setVisibility(View.VISIBLE);

		mCommtentAdapter = new BaseQuickAdapter<Comment>(this, R.layout.list_item_comment, newComment) {
			@Override
			protected void convert(BaseViewHolder helper, Comment item, int position) {

				mImageUtil.loadFromUrlWithCircle(item.getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));
				helper.setText(R.id.tv_name, item.getNickName());
				helper.setTextTypeFace(R.id.tv_name, Typeface.DEFAULT_BOLD);
				helper.setText(R.id.tv_time, BasePresenter.getStrData(item.getCreateDate()));

				if (item.getParentId() > 0) {
					helper.setText(R.id.tv_content, ChatAdapter.getTxtMsg("@" + item.getToNickName() + " " + item.getContent()));
				} else {
					helper.setText(R.id.tv_content, ChatAdapter.getTxtMsg(item.getContent()));
				}

				if (item.getImage() == null || item.getImage().isEmpty()) {
					helper.setVisible(R.id.iv_content, false);
				} else {
					helper.setVisible(R.id.iv_content, true);
					helper.setImageUrl(R.id.iv_content, item.getImage());
				}
			}
		};

		//点击评论
		mCommtentAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {

				if (!isShowKeyBoard) {

					//	ll_edit.setVisibility(View.VISIBLE);

					Comment comment = mCommtentAdapter.getData().get(position);
					//	et_content.requestFocus();
					mInputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
					//	et_content.setHint("回复：" + comment.getNickName());
					ParentId = comment.getCommentId();
					toNikename = comment.getNickName();
					toUserId = comment.getUserId();
				}
			}
		});
		mRVComment.setAdapter(mCommtentAdapter);
		//   mRVComment.setVisibility(View.GONE);

		// fl_load.setVisibility(View.GONE);

		Log.d("kwan", "get article success");
		onXWalkReady(result.getContent());
	}

	@Override
	public void getMyCommentSuccess(List<Comment> comments) {

	}

	@Override
	public void getCommentListSuccess(List<Comment> comments) {

	}

	@Override
	public void addCommnetSuccess(int result) {
		sendComment.setCommentId(result);

		Comment comment = sendComment;

		if (comment != null && comment.getCommentId() != 0) {
			newComment.add(0, comment);
			hotComment.add(comment);

			if (CurrentCommnet == newCommentIndex)
				mCommtentAdapter.add(0, comment);
			else if (CurrentCommnet == hotCommentIndex)
				mCommtentAdapter.add(mCommtentAdapter.getData().size() - 1, comment);
		}
		mRVComment.scrollToPosition(1);
		dismissProgress();
	}

	@Override
	public void reportSuccess(Integer integer) {

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v == tv_hotcomment && CurrentCommnet == newCommentIndex) {

			CurrentCommnet = hotCommentIndex;
			tv_hotcomment.setBackgroundColor(getResources().getColor(R.color.white));
			tv_newcomment.setBackgroundColor(getResources().getColor(R.color.default_circle_indicator_stroke_color));

			mCommtentAdapter.setNewData(hotComment);
		} else if (v == tv_newcomment && CurrentCommnet == hotCommentIndex) {

			CurrentCommnet = newCommentIndex;
			tv_newcomment.setBackgroundColor(getResources().getColor(R.color.white));
			tv_hotcomment.setBackgroundColor(getResources().getColor(R.color.default_circle_indicator_stroke_color));
			mCommtentAdapter.setNewData(newComment);

		} else if (v == tv_chat && mArticle != null) {
			if (SPUtil.getIsLogin()) {
				App.umengEvent(this, IUmengEvent.ChatWithArticle, String.valueOf(mArticle.getAdminId()));
				Bundle bundle = new Bundle();
				bundle.putLong("toUserId", mArticle.getAdminId());
				bundle.putLong("adminId", mArticle.getAdminId());
				bundle.putString("name", mArticle.getAuthor());
				go2ActivityWithLeft(ChatActivity.class, bundle, false);

			} else {
				go2Activity(DologinActivity.class, null, false);
			}

		} else if (v == iv_back) {
			onBackPressed();
		} else if (v == ll_fav) {

			if (SPUtil.getIsLogin()) {
				showProgress("");
				if (isFav)
					mPresenter.deleteFav(id, 0);
				else
					mPresenter.addAcition(1, id, 0);

			} else {
				go2Activity(DologinActivity.class, null, false);
			}

		} else if (v == ll_comment && mArticle != null) {

			if (SPUtil.getIsLogin()) {

				Bundle bundle = new Bundle();
				bundle.putLong("refid", mArticle.getArticleId());
				bundle.putInt("type", CommentActivity.TYPE_ARTICAL);
				go2ActivityForResult(CommentActivity.class, 100, bundle, R.anim.push_left_in, R.anim.push_left_out);
			} else {
				go2Activity(DologinActivity.class, null, false);
			}
		} else if (v == iv_resend) {

			ShareUtil util = new ShareUtil(this);

			if (mArticle != null) {
				util.setShare_Type(IUmengEvent.ArticleShare);

				util.setStr_url("http://bz.metshow.com/api/share.html?articleid=" + mArticle.getArticleId());
				util.setStr_title(mArticle.getTitle());
				util.setmText(mArticle.getSummary());
				util.setStr_img(mArticle.getIco());
				util.showShareDialog();
			}
		}
// else if (v == iv_emo) {
//
//			if (!mIsFaceShow) {
//
//				mInputMethodManager.hideSoftInputFromWindow(et_content.getWindowToken(), 0);
//				try {
//					Thread.sleep(80);// 解决此时会黑一下屏幕的问题
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				iv_emo.setImageResource(R.mipmap.chat_type_icon);
//				mIsFaceShow = true;
//				mFaceRoot.setVisibility(View.VISIBLE);
//
//			} else {
//
//				iv_emo.setImageResource(R.mipmap.zan_normal_icon);
//				mIsFaceShow = false;
//				mFaceRoot.setVisibility(View.GONE);
//				mInputMethodManager.showSoftInput(et_content, 0);
//
//			}
//		} else if (v == iv_camera) {
//			if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//					!= PackageManager.PERMISSION_GRANTED) {
//				//申请WRITE_EXTERNAL_STORAGE权限
//				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//						WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//			} else {
//				ImageSelectorActivity.start(this, 1, ImageSelectorActivity.MODE_MULTIPLE, true, false, false);
//			}
//		}
		else if (v == tv_send) {
			mEditDialog.show();
		}

	}


	@Override
	protected Bitmap getBackGroundBitmap() {
		return null;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				ImageSelectorActivity.start(this, 1, ImageSelectorActivity.MODE_MULTIPLE, true, false, false);
			} else {
				toastMsg("请允许应用访问图片");
			}
		}
	}

	@Override
	public void favSuccess(String result, int position) {
		iv_fav.setImageResource(R.mipmap.article_fav_icon);
		isFav = !isFav;
		if (mArticle != null)
			mArticle.setFavCount(mArticle.getFavCount() + 1);
		tv_fav.setText(mArticle.getFavCount() + "");

		dismissProgress();
	}

	@Override
	public void deleteFavSuccess(String result, int position) {
		toastMsg("取消成功");
		iv_fav.setImageResource(R.mipmap.article_fav_normal_icon);
		isFav = !isFav;
		if (mArticle != null)
			mArticle.setFavCount(mArticle.getFavCount() - 1);
		tv_fav.setText(mArticle.getFavCount() + "");
		dismissProgress();
	}

	@Override
	public void onServerError(int vocational_id, Throwable e) {
		super.onServerError(vocational_id, e);
		fl_load.setVisibility(View.GONE);
	}

	@Override
	public void onServerFailed(String s) {
		super.onServerFailed(s);
		fl_load.setVisibility(View.GONE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);


		if (data != null) {

			if (requestCode == 100) {
				Comment comment = (Comment) data.getSerializableExtra("data");
				ArrayList<Comment> comments = (ArrayList<Comment>) data.getSerializableExtra("array");
				if (comment != null && comment.getCommentId() != 0) {
					newComment.add(0, comment);
					hotComment.add(comment);

					if (CurrentCommnet == newCommentIndex)
						mCommtentAdapter.add(0, comment);
					else
						mCommtentAdapter.add(mCommtentAdapter.getData().size() - 1, comment);
				}

				if (comments != null && comments.size() > 0) {

					mArticle.setCommentCount(mArticle.getCommentCount() + comments.size());
					tv_comment.setText(mArticle.getCommentCount()+"");
					iv_comment.setImageResource(R.mipmap.article_comment_icon);
				}
			}

			if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
				ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
				onSelectImageBack(images.get(0));
			}

		}
	}


//	private void initFacePage() {
//
//		// TODO Auto-generated method stub
//		List<View> lv = new ArrayList<>();
//		for (int i = 0; i < NUM_PAGE; ++i)
//			lv.add(getGridView(i));
//
//		BZViewPageAdapter<View> adapter = new BZViewPageAdapter<>(lv);
//
//		mFaceViewPager.setAdapter(adapter);
//		mFaceViewPager.setCurrentItem(mCurrentPage);
//
//		CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
//		indicator.setViewPager(mFaceViewPager);
//		adapter.notifyDataSetChanged();
//		mFaceRoot.setVisibility(View.GONE);
//
//		indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//			@Override
//			public void onPageSelected(int arg0) {
//				mCurrentPage = arg0;
//			}
//
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//				// do nothing
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//				// do nothing
//			}
//		});
//	}
//
//	private GridView getGridView(int i) {
//
//		// TODO Auto-generated method stub
//		GridView gv = new GridView(this);
//		gv.setNumColumns(7);
//		gv.setSelector(new ColorDrawable(Color.TRANSPARENT));// 屏蔽GridView默认点击效果
//		gv.setBackgroundColor(Color.TRANSPARENT);
//		gv.setCacheColorHint(Color.TRANSPARENT);
//		gv.setHorizontalSpacing(1);
//		gv.setVerticalSpacing(1);
//		gv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//				ViewGroup.LayoutParams.WRAP_CONTENT));
//		gv.setGravity(Gravity.CENTER);
//		gv.setAdapter(new FaceAdapter(this, i));
//
//		//防止乱滚动
//		gv.setOnTouchListener(new View.OnTouchListener() {
//			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getAction() == MotionEvent.ACTION_MOVE) {
//					return true;
//				}
//				return false;
//			}
//		});
//		gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//									long arg3) {
//				// TODO Auto-generated method stub
//				if (arg2 == NUM) {// 删除键的位置
//					int selection = et_content.getSelectionStart();
//					String text = et_content.getText().toString();
//					if (selection > 0) {
//						String text2 = text.substring(selection - 1);
//						if ("]".equals(text2)) {
//							int start = text.lastIndexOf("[");
//							int end = selection;
//							et_content.getText().delete(start, end);
//							return;
//						}
//						et_content.getText()
//								.delete(selection - 1, selection);
//					}
//				} else {
//					int count = mCurrentPage * NUM + arg2;
//
//					// 注释的部分，在EditText中显示字符串
//					// 下面这部分，在EditText中显示表情
//					Bitmap bitmap = BitmapFactory.decodeResource(
//							getResources(), (Integer) App.getInstance()
//									.getFaceMap().values().toArray()[count]);
//
//					if (bitmap != null) {
//
//						ImageSpan imageSpan = new ImageSpan(ArticleActivity.this, ImageUtil.scaleBitmap(bitmap, 60, 60));
//						String emojiStr = mFaceMapKeys.get(count);
//						SpannableString spannableString = new SpannableString(emojiStr);
//						spannableString.setSpan(imageSpan,
//								emojiStr.indexOf('['),
//								emojiStr.indexOf(']') + 1,
//								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//						et_content.append(spannableString);
//
//					} else {
//
//						String ori = et_content.getText().toString();
//						int index = et_content.getSelectionStart();
//						StringBuilder stringBuilder = new StringBuilder(ori);
//						stringBuilder.insert(index, mFaceMapKeys.get(count));
//						et_content.setText(stringBuilder.toString());
//						et_content.setSelection(index
//								+ mFaceMapKeys.get(count).length());
//					}
//				}
//			}
//		});
//		return gv;
//	}


	protected void onSoftKeyBoardVisible(boolean visible) {
		super.onSoftKeyBoardVisible(visible);

		isShowKeyBoard = visible;

		if (!isShowKeyBoard)
			mEditDialog.cancel();

//		if (!isShowKeyBoard && mFaceRoot.getVisibility() == View.GONE && !isbackimag && !mIsFaceShow) {
//
//			fl_pop.setVisibility(View.GONE);
//			ll_edit.setVisibility(View.GONE);
//			//  setSwipeMargins(false);
//		} else {
//			ll_edit.setVisibility(View.VISIBLE);
//			// setSwipeMargins(true);
//		}
	}

	//退出当前界面 执行动画

	@Override
	public void onBackPressed() {


		//      super.onBackPressed();
//        overridePendingTransition(0,
//                R.anim.push_bottom_out);

		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
//		exitTransition.exit(this, new Runnable() {
//			@Override
//			public void run() {
//				//ns_content.scrollTo(0,0);
//				ArticleActivity.super.onBackPressed();
//				overridePendingTransition(0, 0);
//			}
//		});
	}


}

