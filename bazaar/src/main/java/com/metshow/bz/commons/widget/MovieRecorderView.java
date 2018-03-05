package com.metshow.bz.commons.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OutputFormat;
import android.media.MediaRecorder.VideoEncoder;
import android.media.MediaRecorder.VideoSource;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import com.metshow.bz.R;
import com.metshow.bz.commons.App;

import java.io.File;
import java.io.IOException;


/**
 * 视频播放控件
 */
@SuppressLint("NewApi")
public class MovieRecorderView extends LinearLayout implements OnErrorListener {

	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	// private NumberProgressBar mProgressBar;

	private MediaRecorder mMediaRecorder;
	private Camera mCamera;

	private boolean isCameraLock;
	private int Orientation;

	private int mWidth;// 视频分辨率宽度
	private int mHeight;// 视频分辨率高度
	private boolean isOpenCamera;// 是否一开始就打开摄像头


	private File mRecordFile = null;// 文件

	public MovieRecorderView(Context context) {
		this(context, null);
	}

	public MovieRecorderView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public MovieRecorderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// 初始化各项组件
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MovieRecorderView, defStyle, 0);
		mWidth = a.getInteger(R.styleable.MovieRecorderView_video_width, 320);// 默认320
		mHeight = a.getInteger(R.styleable.MovieRecorderView_video_height, 240);// 默认240
		isOpenCamera = a.getBoolean(R.styleable.MovieRecorderView_is_open_camera, true);// 默认打开
		LayoutInflater.from(context).inflate(R.layout.movie_recorder_view, this);
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		//  mProgressBar = (NumberProgressBar) findViewById(R.id.progressBar);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(new CustomCallBack());
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		a.recycle();
		Orientation = 90;

	}

	private class CustomCallBack implements Callback {

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			if (!isOpenCamera)
				return;
			initCamera();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			if (!isOpenCamera)
				return;
			freeCameraResource();
		}

	}

	/**
	 * 初始化摄像头
	 *
	 * @date 2015-2-5
	 */
	@SuppressLint("NewApi")
	public void initCamera() {


		Log.d("kwan", "initCamera()");

		try {

			if (mCamera != null) {
				freeCameraResource();
			}

			mCamera = Camera.open();


			// freeCameraResource();

			if (mCamera == null)
				return;

			// setCameraParams();
			mCamera.setDisplayOrientation(Orientation);
			mCamera.setPreviewDisplay(mSurfaceHolder);
			mCamera.startPreview();
			mCamera.unlock();
			isCameraLock = false;
		} catch (Exception e) {
			Log.d("recoder error", "initCamera()");
			e.printStackTrace();
		}
	}


	/**
	 * 释放摄像头资源
	 *
	 * @date 2015-2-5
	 */
	private void freeCameraResource() {
		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.lock();
			isCameraLock = true;
			mCamera.release();
			mCamera = null;
		}
	}

	private void createRecordDir() {
		try {

			File sampleDir = new File(Environment.getExternalStorageDirectory() + File.separator + "im/video/");
			if (!sampleDir.exists()) {
				sampleDir.mkdirs();
			}
			File vecordDir = sampleDir;
			// 创建文件

			mRecordFile = File.createTempFile("recording", ".mp4", vecordDir); //mp4格式
			Log.i("TAG", mRecordFile.getAbsolutePath());
		} catch (IOException e) {
			Log.d("recoder error", "createRecordDir():::" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 初始化
	 *
	 * @throws IOException
	 * @date 2015-2-5
	 */
	private void initRecord() {

		createRecordDir();

		try {

			Log.d("kwan", "islock::" + isCameraLock);

			mMediaRecorder = new MediaRecorder();
			mMediaRecorder.reset();
			Log.d("kwan", "is camera null:::" + (mCamera == null));
			if (mCamera != null) {
				mMediaRecorder.setCamera(mCamera);
			}
			mMediaRecorder.setOnErrorListener(this);
			mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
			mMediaRecorder.setVideoSource(VideoSource.CAMERA);// 视频源
			mMediaRecorder.setAudioSource(AudioSource.MIC);// 音频源
			mMediaRecorder.setOutputFormat(OutputFormat.MPEG_4);// 视频输出格式
			mMediaRecorder.setAudioEncoder(AudioEncoder.AMR_NB);// 音频格式
			//  mMediaRecorder.setVideoSize(mWidth, mHeight);// 设置分辨率：
			// mMediaRecorder.setVideoFrameRate(16);// 这个我把它去掉了，感觉没什么用
			mMediaRecorder.setVideoEncodingBitRate(5 * App.SCREEN_HEIGHT * App.SCREEN_WIDTH);// 设置帧频率，然后就清晰了
			mMediaRecorder.setOrientationHint(Orientation);// 输出旋转90度，保持竖屏录制
			mMediaRecorder.setVideoEncoder(VideoEncoder.MPEG_4_SP);// 视频录制格式
			// mediaRecorder.setMaxDuration(Constant.MAXVEDIOTIME * 1000);
			mMediaRecorder.setOutputFile(mRecordFile.getAbsolutePath());
			mMediaRecorder.prepare();
			mMediaRecorder.start();

		} catch (IOException e) {
			Log.d("recoder error", "createRecordDir()");
		}

	}


	/**
	 * 开始录制视频
	 */
	public void record() {


		//  initCamera();
		initRecord();

	}

	/**
	 * 停止拍摄
	 *
	 * @date 2015-2-5
	 */
	public void stop() {
		stopRecord();
		releaseRecord();
		freeCameraResource();
	}

	/**
	 * 停止录制
	 *
	 * @date 2015-2-5
	 */
	public void stopRecord() {

		try {

			if (mMediaRecorder != null) {
				// 设置后不会崩
				mMediaRecorder.setOnErrorListener(null);
				mMediaRecorder.setPreviewDisplay(null);
				mMediaRecorder.stop();
			}
		} catch (Exception e) {
			Log.d("recorder error", "stopRecord()");
			e.printStackTrace();
		}
	}

	/**
	 * 释放资源
	 *
	 * @date 2015-2-5
	 */
	private void releaseRecord() {
		if (mMediaRecorder != null) {
			mMediaRecorder.setOnErrorListener(null);
			mMediaRecorder.release();
		}
		mMediaRecorder = null;
	}


	/**
	 * @return the mVecordFile
	 */
	public File getmRecordFile() {
		return mRecordFile;
	}


	@Override
	public void onError(MediaRecorder mr, int what, int extra) {

		Log.d("onError::", "what:" + what + " extra:" + extra);

		if (mr != null)
			mr.reset();

	}


}