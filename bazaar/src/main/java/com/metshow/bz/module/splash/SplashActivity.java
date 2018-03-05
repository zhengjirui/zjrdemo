package com.metshow.bz.module.splash;


import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kwan.base.AppManager;
import com.kwan.base.activity.BaseActivity;
import com.kwan.base.config.APPCacheConfig;
import com.kwan.base.widget.FullScreenVideoView;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.IUmengEvent;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.Splash;
import com.metshow.bz.module.commons.activity.FriendDetailActivity;
import com.metshow.bz.module.commons.activity.WebActivity;
import com.metshow.bz.module.main.GuideActivity;
import com.metshow.bz.module.main.MainActivity;
import com.metshow.bz.presenter.SplashPresenter;
import com.metshow.bz.presenter.iviews.ISplashView;
import com.zhy.autolayout.AutoFrameLayout;

import java.io.IOException;

import pl.droidsonroids.gif.AnimationListener;



public class SplashActivity extends BaseActivity implements ISplashView, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {

	private TextView tv_skip;
	private AutoFrameLayout ll_root;
	private SplashPresenter mSplashPresenter;
	private ImageView imageView,imageView2;
	private FullScreenVideoView videoView;
	private WebView webView;
	private Handler handler = new Handler();
	private String url, img_url;
	private volatile boolean isgo = false;
	private Splash mSplash;
	private long refid = 0;
	private String type = "null";

	@Override
	protected void beForeSetContentView() {
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mSplashPresenter = new SplashPresenter(this);

	}

	@Override
	protected int getTitleBarViewId() {
		return 0;
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_splash;
	}

	@Override
	protected int getBottomViewId() {
		return 0;
	}

	@Override
	protected void initViews() {
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView = (ImageView) findViewById(R.id.imageView);
		videoView = (FullScreenVideoView) findViewById(R.id.videoView);
		webView = (WebView) findViewById(R.id.webView);

		tv_skip = (TextView) findViewById(R.id.tv_skip);
		ll_root = (AutoFrameLayout) findViewById(R.id.ll_root);
	}

	@Override
	protected void initViewSetting() {
		tv_skip.setOnClickListener(this);
		imageView.setOnClickListener(this);
	}

	@Override
	protected void initData() {


		imageView2.setVisibility(View.GONE);
		mSplashPresenter.getConfig();
		//imageView2.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				mSplashPresenter.getConfig();
//			}
//		},3000);


	}

	@Override
	protected BasePresenter getPresent() {
		return mSplashPresenter;
	}

	@Override
	protected Bitmap getBackGroundBitmap() {
		return null;
	}

	@Override
	public void showImage(String url, final boolean isFrontPage, String url2, Splash s) {
		mSplash = s;
		this.url = url2;
		img_url = url;
		imageView.setVisibility(View.VISIBLE);
		stayTime = 3000;
		type = "Image:" + url;
		Log.d(getTag(), "showImage: " + url);
		SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
			@Override
			public void onResourceReady(final Bitmap bitmap, GlideAnimation glideAnimation) {

				imageView.setImageBitmap(bitmap);
				if (!isFrontPage)
					goNext();

			}
		};

		Glide.with(this)
				.load(url)
				.asBitmap()
				.centerCrop()
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.into(target);
	}

	@Override
	public void showGif(String url, String url2, Splash s) {
		mSplash = s;
		this.url = url2;
		img_url = url;
		Log.d(getTag(), "showGif");
		type = "Gif:" + url;
		stayTime = 2500;

		imageView.setVisibility(View.VISIBLE);

		Glide.with(this)
				.load(url)
				.asGif()
				.toBytes()
				.error(R.mipmap.ic_launcher)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.into(new SimpleTarget<byte[]>() {
					@Override
					public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {

						try {
							pl.droidsonroids.gif.GifDrawable gifFromBytes = new pl.droidsonroids.gif.GifDrawable(resource);

							gifFromBytes.addAnimationListener(new AnimationListener() {
								@Override
								public void onAnimationCompleted(int loopNumber) {
									goNextNow();
								}
							});
							imageView.setImageDrawable(gifFromBytes);

						} catch (IOException e) {
							e.printStackTrace();
							goNextNow();
						}
					}
				});
	}

	@Override
	public void showVideo(String url, String imgUrl, Splash s) {
		mSplash = s;
		Log.d(getTag(), "showVideo");
		type = "Video:" + url;
		stayTime = 2000;
		img_url = imgUrl;
		videoView.setVisibility(View.VISIBLE);

		//videoView.setVisibility(View.VISIBLE);
		//showImage(imgUrl,true);
		//MediaController mediaco=new MediaController(this);
		//设置MediaController
		//videoView.setMediaController(mediaco);

		videoView.setVideoURI(Uri.parse(url));
		videoView.setOnCompletionListener(this);
		videoView.setOnPreparedListener(this);
		videoView.setOnErrorListener(this);
		videoView.start();
		Log.d(getTag(), "videoView.start()");
	}

	@Override
	public void showHtml(String url, Splash s) {
		mSplash = s;
		webView.setVisibility(View.VISIBLE);

		type = "Html:" + url;
		//WebSettings settings = webView.getSettings();
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				// goNext();

			}
		});
		webView.loadUrl(url);
	}

	@Override
	public void showPerson(long refId) {
		type = "Person:" + refId;
		refid = refId;
		ll_root.setOnClickListener(this);
	}

	@Override
	public void getConfigSuccess() {
		imageView2.setVisibility(View.GONE);
		mSplashPresenter.getSplashList("0");
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		goNext();
	}

	@Override
	public void onNoNetWork() {
		super.onNoNetWork();
		goNextNow();
	}

	@Override
	public void onServerError(int vocational_id, Throwable e) {
		super.onServerError(vocational_id,e);
		Log.d("kwan",getTag()+"::onServerError::"+e.getMessage());
		goNext();
	}

	@Override
	public void onServerFailed(String s) {
		super.onServerFailed(s);
		Log.d("kwan",getTag()+"::onServerError::"+s);
		goNext();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.d("kwan",getTag()+"::MediaPlayerError::");
		goNextNow();
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		Log.d(getTag(), "onPrepared");
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v == tv_skip) {
			isgo = true;

			boolean isFirst = (Boolean) App.getInstance().getConfiguration().getBasicData(APPCacheConfig.KEY_IS_FIRST_CACHE);

			if (isFirst) {
				Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
				startActivity(intent);
				AppManager.getAppManager().finishActivity(this);
			} else {
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
				AppManager.getAppManager().finishActivity(this);
			}

		} else if (v == imageView) {

			if (url != null && !url.trim().isEmpty()) {

				isgo = true;


				App.umengEvent(this, IUmengEvent.StartUpAdClick, "html:" + url);

				Intent intent = new Intent(SplashActivity.this, WebActivity.class);

				intent.putExtra("url", mSplash.getClickUrl());
				intent.putExtra("img_url", mSplash.getImage4());
				intent.putExtra("title", mSplash.getName());
				intent.putExtra("txt", mSplash.getName());

				intent.putExtra("is2main", true);
				startActivity(intent);
				AppManager.getAppManager().finishActivity(this);
			}

		} else if (v == ll_root) {
			App.umengEvent(this, IUmengEvent.StartUpAdClick, "person:" + refid);
			Intent intent = new Intent(SplashActivity.this, FriendDetailActivity.class);

			intent.putExtra("is2main", true);
			intent.putExtra("userid", refid);
			intent.putExtra("isFollow", false);
			startActivity(intent);
			AppManager.getAppManager().finishActivity(this);

		}

	}

	long stayTime = 3000;

	private void goNext() {

		new Thread() {
			@Override
			public void run() {
				super.run();

				int i = 0;

				while (!isgo) {

					if (i == stayTime) {

						handler.post(new Runnable() {
							@Override
							public void run() {
								App.umengEvent(SplashActivity.this, IUmengEvent.StartUpAdBrowse, type);
								boolean isFirst = (Boolean) App.getInstance().getConfiguration().getBasicData(APPCacheConfig.KEY_IS_FIRST_CACHE);
								if (isFirst) {
									Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
									startActivity(intent);
									AppManager.getAppManager().finishActivity(SplashActivity.this);
								} else {
									Intent intent = new Intent(SplashActivity.this, MainActivity.class);
									startActivity(intent);
									AppManager.getAppManager().finishActivity(SplashActivity.this);
								}
							}
						});
					}

					try {
						Thread.sleep(10);
						i = i + 10;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();


	}

	void goNextNow() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				boolean isFirst = (Boolean) App.getInstance().getConfiguration().getBasicData(APPCacheConfig.KEY_IS_FIRST_CACHE);
				if (isFirst) {
					Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
					startActivity(intent);
					AppManager.getAppManager().finishActivity(SplashActivity.this);
				} else {
					Intent intent = new Intent(SplashActivity.this, MainActivity.class);
					startActivity(intent);
					AppManager.getAppManager().finishActivity(SplashActivity.this);
				}
			}
		});
	}


}
