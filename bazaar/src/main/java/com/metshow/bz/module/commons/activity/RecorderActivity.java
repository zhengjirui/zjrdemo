package com.metshow.bz.module.commons.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.daimajia.numberprogressbar.OnProgressBarListener;
import com.kwan.base.BaseApplication;
import com.kwan.base.activity.BaseActivity;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.PathUtil;
import com.kwan.base.widget.indicator.BezierPagerIndicator;
import com.kwan.base.widget.indicator.CommonNavigator;
import com.kwan.base.widget.indicator.MagicIndicator;
import com.kwan.base.widget.indicator.ScaleTransitionPagerTitleView;
import com.kwan.base.widget.indicator.SimplePagerTitleView;
import com.kwan.base.widget.indicator.abs.CommonNavigatorAdapter;
import com.kwan.base.widget.indicator.abs.IPagerIndicator;
import com.kwan.base.widget.indicator.abs.IPagerTitleView;
import com.metshow.bz.R;

import org.wysaid.camera.CameraInstance;
import org.wysaid.common.Common;
import org.wysaid.nativePort.CGENativeLibrary;
import org.wysaid.view.CameraRecordGLSurfaceView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;


public class RecorderActivity extends BaseActivity {
	/**
	 * 刷新进度条
	 */
	private static final int HANDLE_INVALIDATE_PROGRESS = 0;
	/**
	 * 是否是点击状态
	 */
	private volatile boolean mPressedStatus;
	private volatile boolean mAtRemove;
	volatile boolean currntState;
	private ImageView iv_back;
	private CameraRecordGLSurfaceView mRecorderView;
	// private TextView mRecordTipView;
	private ImageView mShootBtn;
	private NumberProgressBar mProgressBar;
	private boolean isFinish = true;

	private ImageView iv_ok, iv_cancel;

	/**
	 * 录制最长时间
	 */
	public final static int RECORD_TIME_MAX = 10 * 1000;
	/**
	 * 录制最小时间
	 */
	public final static int RECORD_TIME_MIN = 3 * 1000;
	private int mTimeCount;
	private Timer mTimer;

	public static String[] text = {
			"默认", "美颜",
            "Lomofi","Amaro",
            "LordKelvin","Hudson","MissEtikate",
            "Vaiencia","Brannan", "Earlybird",
            "Sierra", "Sutro","Walden",
            "Amatorka","Salad","Archae",
            "Hefe","Rise","SoftElegance",
            "Toaster","Xproll", "Inkwell"

	};

	public static int[] filterIcon = {

			 R.mipmap.moren,R.mipmap.meiyan,
             R.mipmap.lomofi,R.mipmap.amaro
			,R.mipmap.lordkelvin,R.mipmap.hudson,R.mipmap.missetikate
			,R.mipmap.vaiencia,R.mipmap.brannan,R.mipmap.earlybird
			,R.mipmap.sierra,R.mipmap.sutro,R.mipmap.walden
			,R.mipmap.amatorka,R.mipmap.salad,R.mipmap.archae
			,R.mipmap.hefe,R.mipmap.rise,R.mipmap.softelegance
			,R.mipmap.toaster,R.mipmap.xproll,R.mipmap.inkwell


	};


	private MagicIndicator mFilter_indicator;
    private ImageView iv_cam_exchange,iv_cam_light;
    private boolean isFlashOff;

    @Override
	protected void beForeSetContentView() {

	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_recorder;
	}

	@Override
	protected void initViews() {

		iv_ok = (ImageView) findViewById(R.id.iv_ok);
		iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        iv_cam_exchange= (ImageView) findViewById(R.id.iv_cam_exchange);
        iv_cam_light= (ImageView) findViewById(R.id.iv_cam_light);
		mProgressBar = (NumberProgressBar) findViewById(R.id.progressBar);
		mRecorderView = (CameraRecordGLSurfaceView) findViewById(R.id.movieRecorderView);
		mShootBtn = (ImageView) findViewById(R.id.shoot_button);
		// mRecordTipView = (TextView) findViewById(R.id.record_tip);
		// mRecordTipView.setVisibility(View.GONE);
		mShootBtn.setOnTouchListener(mOnVideoControllerTouchListener);
		mFilter_indicator = (MagicIndicator) findViewById(R.id.filter_indicator);


        mProgressBar.setOnProgressBarListener(new OnProgressBarListener() {
            @Override
            public void onProgressChange(int current, int max) {

                Log.e("kwan","xx"+current);
                if(current==3000)
                    findViewById(R.id.iv_time_indicate).setVisibility(View.INVISIBLE);
            }
        });

		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
        iv_cam_exchange.setOnClickListener(this);
        iv_cam_light.setOnClickListener(this);
		iv_ok.setOnClickListener(this);
		iv_cancel.setOnClickListener(this);

		final CommonNavigator commonNavigator = new CommonNavigator(this);
		commonNavigator.setSkimOver(true);
		int fpadding = BaseApplication.SCREEN_WIDTH / 2;
		commonNavigator.setRightPadding(fpadding);
		commonNavigator.setLeftPadding(fpadding);
		commonNavigator.setAdapter(new CommonNavigatorAdapter() {

			@Override
			public int getCount() {
				return text.length;
			}

			@Override
			public IPagerTitleView getTitleView(Context context, final int index) {

				SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
				simplePagerTitleView.setText(text[index]);
				simplePagerTitleView.setTextSize(15);

				simplePagerTitleView.setNormalColor(Color.parseColor("#ffffff"));
				simplePagerTitleView.setSelectedColor(Color.RED);
				simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mFilter_indicator.onPageSelected(index);
						mFilter_indicator.onPageScrolled(index, 0.0f, 0);
						mRecorderView.setFilterWithConfig(mFilterString[index]);
					}
				});

				return simplePagerTitleView;
			}

			@Override
			public IPagerIndicator getIndicator(Context context) {
				BezierPagerIndicator indicator = new BezierPagerIndicator(context);
				indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4"), Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"));
				return indicator;
			}
		});

		mFilter_indicator.setNavigator(commonNavigator);
		//mFilter_indicator.setVisibility(View.GONE);
		//mRecorderView.presetCameraForward(false);
		mRecorderView.presetRecordingSize(480, 640);
		mRecorderView.setPictureSize(600, 800, true); // > 4MP
		mRecorderView.setZOrderOnTop(false);
		mRecorderView.setZOrderMediaOverlay(true);
		mRecorderView.setFitFullView(true);

		CGENativeLibrary.setLoadImageCallback(loadImageCallback, null);

		mRecorderView.setOnCreateCallback(new CameraRecordGLSurfaceView.OnCreateCallback() {
			@Override
			public void createOver(boolean success) {
				if (success) {
					Log.i("kwan", "view create OK");
				} else {
					Log.e("kwan", "view create failed!");
				}
			}
		});
	}

	public CGENativeLibrary.LoadImageCallback loadImageCallback = new CGENativeLibrary.LoadImageCallback() {
		//Notice: the 'name' passed in is just what you write in the rule, e.g: 1.jpg
		//注意， 这里回传的name不包含任何路径名， 仅为具体的图片文件名如 1.jpg
		@Override
		public Bitmap loadImage(String name, Object arg) {
			Log.i(Common.LOG_TAG, "Loading file: " + name);
			AssetManager am = getAssets();
			InputStream is;
			try {
				is = am.open(name);
			} catch (IOException e) {
				Log.e(Common.LOG_TAG, "Can not open file " + name);
				return null;
			}
			return BitmapFactory.decodeStream(is);
		}

		@Override
		public void loadImageOK(Bitmap bmp, Object arg) {
			Log.i(Common.LOG_TAG, "Loading bitmap over, you can choose to recycle or cache");
			//The bitmap is which you returned at 'loadImage'.
			//You can call recycle when this function is called, or just keep it for further usage.
			//唯一不需要马上recycle的应用场景为 多个不同的滤镜都使用到相同的bitmap
			//那么可以选择缓存起来。
			bmp.recycle();
		}
	};


	private View.OnTouchListener mOnVideoControllerTouchListener = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			int action = event.getAction();

			if (action == MotionEvent.ACTION_DOWN) {

				//   mRecordTipView.setVisibility(View.VISIBLE);
				//   mRecordTipView.setText("上滑取消");
				//mProgressBar.setReachedBarColor(Colo);
				mProgressBar.invalidate();
				mProgressBar.postInvalidate();

				mShootBtn.setBackgroundResource(R.drawable.cam_select);
				startRecord();
			}

			if (action == MotionEvent.ACTION_MOVE) {
				currntState = event.getY() < 0;
				if (currntState != mAtRemove) {
					mAtRemove = currntState;
					changeTip();
				}
			}

			if (action == MotionEvent.ACTION_UP) {
				//停止录制
				if (mPressedStatus) {
                    mShootBtn.setBackgroundResource(R.drawable.cam_normal);
					//mRecordTipView.setVisibility(View.GONE);
					stopRecord();
				}
			}
			return true;
		}

	};

	long mStarTime;
	long mEndTime;
	String mPath;


	private void startRecord() {

		mStarTime = System.currentTimeMillis();
		mPath = PathUtil.getApplicationTempPath() + mStarTime + ".mp4";
		mPressedStatus = true;
		mRecorderView.startRecording(mPath, new CameraRecordGLSurfaceView.StartRecordingCallback() {
			@Override
			public void startRecordingOver(boolean success) {

			}
		});



		mTimeCount = 0;// 时间计数器重新赋值
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {

				mTimeCount++;
				mHandler.sendEmptyMessage(HANDLE_INVALIDATE_PROGRESS);

				if (mTimeCount >= RECORD_TIME_MAX / 100) {// 达到指定时间
					this.cancel();
					mHandler.removeMessages(HANDLE_INVALIDATE_PROGRESS);
				}
			}
		}, 0, 100);
	}


	private void stopRecord() {

		mEndTime = System.currentTimeMillis();
		mPressedStatus = false;
		resetTimer();

		if (mEndTime - mStarTime > RECORD_TIME_MIN && !mAtRemove) {

			mRecorderView.endRecording(new CameraRecordGLSurfaceView.EndRecordingCallback() {
				@Override
				public void endRecordingOK() {
					onRecordFinish();
				}
			}, true);


		} else {

			mRecorderView.endRecording(new CameraRecordGLSurfaceView.EndRecordingCallback() {
				@Override
				public void endRecordingOK() {
					Log.e("kwan", "in stop 2.2");
				}
			}, false);
			if (mEndTime - mStarTime < RECORD_TIME_MIN && !mAtRemove)
				toastMsg("视频录制时间太短");

		}
	}

	/**
	 * 重置计时器
	 */
	private void resetTimer() {

		if (mTimer != null) {
			mTimer.cancel();
			mTimer.purge();
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case HANDLE_INVALIDATE_PROGRESS:
					if (!isFinishing()) {
						if (mPressedStatus) {
							// 设置进度条
							mProgressBar.setProgress(mTimeCount * 100);
							sendEmptyMessageDelayed(0, 30);
						} else {
							mProgressBar.setProgress(0);
							mProgressBar.setReachedBarColor(Color.RED);
							mProgressBar.invalidate();
						}
					}
					break;
			}
		}
	};


	private void changeTip() {

		if (mAtRemove) {
			mProgressBar.setReachedBarColor(Color.RED);
			// mRecordTipView.setText("松开取消");

		} else {
			mProgressBar.setReachedBarColor(Color.GREEN);
			//  mRecordTipView.setText("上滑取消");
		}

	}

	@Override
	protected void initViewSetting() {

	}

	@Override
	protected void initData() {

	}

	@Override
	public void onResume() {
		super.onResume();
		isFinish = true;
		mRecorderView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();

		CameraInstance.getInstance().stopCamera();
		Log.i("kwan", "activity onPause...");
		mRecorderView.release(null);
		mRecorderView.onPause();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		isFinish = false;
		// mRecorderView.stop();
	}


	@Override
	protected BasePresenter getPresent() {
		return null;
	}

	public void onRecordFinish() {
		// mRecorderView.stop();
		// 返回到播放页面

//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				mShootBtn.setVisibility(View.INVISIBLE);
//				iv_cancel.setVisibility(View.VISIBLE);
//				iv_ok.setVisibility(View.VISIBLE);
//			}
//		});

		Bundle bundle = new Bundle();
		bundle.putString("output", mPath);
		//bundle.putBoolean("Rebuild", mRebuild);
		go2Activity(SendTopicActivity.class, bundle, false);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// mRecorderView.stop();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v == iv_back) {
			onBackPressed();
		} else if (v == iv_ok) {

			mShootBtn.setVisibility(View.VISIBLE);
			iv_ok.setVisibility(View.INVISIBLE);
			iv_cancel.setVisibility(View.INVISIBLE);

			Bundle bundle = new Bundle();
			bundle.putString("output", mPath);
			//bundle.putBoolean("Rebuild", mRebuild);
			go2Activity(SendTopicActivity.class, bundle, false);

		} else if (v == iv_cancel) {

			mShootBtn.setVisibility(View.VISIBLE);
			iv_ok.setVisibility(View.INVISIBLE);
			iv_cancel.setVisibility(View.INVISIBLE);
            findViewById(R.id.iv_time_indicate).setVisibility(View.VISIBLE);

		}else if(v==iv_cam_exchange){
            mRecorderView.switchCamera();
        }else if(v==iv_cam_light){
            if (isFlashOff) {
                mRecorderView.setFlashLightMode(Camera.Parameters.FLASH_MODE_TORCH);
                iv_cam_light.setImageResource(R.mipmap.cam_light_on);
            }
            else {
                mRecorderView.setFlashLightMode(Camera.Parameters.FLASH_MODE_OFF);
                iv_cam_light.setImageResource(R.mipmap.cam_light_off);
            }
            isFlashOff = !isFlashOff;
        }
	}

	@Override
	protected Bitmap getBackGroundBitmap() {
		return null;
	}

	@Override
	protected int getBottomViewId() {
		return 0;
	}

	@Override
	protected int getTitleBarViewId() {
		return 0;
	}

	public static String[] mFilterString = {
			"#unpack @blend add huoguo.png 0",
			"meiyan",
			"@adjust lut jiachang.png",
			"@adjust lut huoguo.png",
			"@adjust lut shaokao.png",
			"@adjust lut haixian.png",
			"@adjust lut yinping.png",
			"@adjust lut tiandian.png",
			"@adjust lut liaoli.png",
			"@adjust lut hongpei.png",
			"@adjust lut xican.png",
			"@adjust lut liangtang.png",
			"@adjust lut mianshi.png",
			"@adjust lut xiaoshi.png",
			"@adjust lut mala.png",
			"@adjust lut shala.png",
			"@adjust lut kuaican.png",
			"@adjust lut kouweixia.png",
			"@adjust lut tiebansao.png",
			"@adjust lut baoqianglv.png",
			"@adjust lut yimahong.png",
			"@adjust lut lanjingling.png",
			"@adjust lut heibaidiao.png"
	};
}
