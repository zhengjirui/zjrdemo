package com.metshow.bz.module.commons.activity;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwan.base.activity.BaseActivity;
import com.kwan.base.util.PathUtil;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.ImageSelectAdapter;

import org.wysaid.camera.CameraInstance;
import org.wysaid.myUtils.ImageUtil;
import org.wysaid.view.CameraRecordGLSurfaceView;

import java.util.ArrayList;

public class CameraActivity extends BaseActivity {

	private CameraRecordGLSurfaceView mCameraView;
	private ImageSelectAdapter mAdapter;
	private ImageView iv_cam, iv_light, iv_exchange;
	private RecyclerView rv_image;
	private ArrayList<String> mListData = new ArrayList<>();
	private boolean isFlashOff = true;

	private TextView tv_next;
    private ImageView iv_close,tv_ratio;

    int height = 1000;

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

	@Override
	protected int getContentViewId() {
		return R.layout.activity_camera;
	}


	@Override
	protected void initViews() {

		mCameraView = (CameraRecordGLSurfaceView) findViewById(R.id.myGLSurfaceView);
		mCameraView.presetCameraForward(true);
		mCameraView.presetRecordingSize(750, height);
		mCameraView.setPictureSize(750, height, true); // > 4MP
		mCameraView.setZOrderOnTop(false);
		mCameraView.setZOrderMediaOverlay(true);
		mCameraView.setOnCreateCallback(new CameraRecordGLSurfaceView.OnCreateCallback() {
			@Override
			public void createOver(boolean success) {
				if (success) {
					Log.i("kwan", "view create OK");
				} else {
					Log.e("kwan", "view create failed!");
				}
			}
		});
		mCameraView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, final MotionEvent event) {

				switch (event.getActionMasked()) {
					case MotionEvent.ACTION_DOWN: {
						Log.i("kwan", String.format("Tap to focus: %g, %g", event.getX(), event.getY()));
						final float focusX = event.getX() / mCameraView.getWidth();
						final float focusY = event.getY() / mCameraView.getHeight();

						mCameraView.focusAtPoint(focusX, focusY, new Camera.AutoFocusCallback() {
							@Override
							public void onAutoFocus(boolean success, Camera camera) {
								if (success) {
									Log.e("kwan", String.format("Focus OK, pos: %g, %g", focusX, focusY));
								} else {
									Log.e("kwan", String.format("Focus failed, pos: %g, %g", focusX, focusY));
									mCameraView.cameraInstance().setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
								}
							}
						});
					}
					break;
					default:
						break;
				}

				return true;
			}
		});

		mCameraView.setPictureSize(750, height, true);

		iv_cam = (ImageView) findViewById(R.id.iv_cam);
		iv_light = (ImageView) findViewById(R.id.iv_light);
		iv_exchange = (ImageView) findViewById(R.id.iv_exchange);
		rv_image = (RecyclerView) findViewById(R.id.rv_image);
        iv_close= (ImageView) findViewById(R.id.iv_close);
		tv_next = (TextView) findViewById(R.id.tv_next);
        tv_ratio = (ImageView) findViewById(R.id.tv_ratio);

		iv_cam.setOnClickListener(this);
		iv_light.setOnClickListener(this);
		iv_exchange.setOnClickListener(this);
		tv_next.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        tv_ratio.setOnClickListener(this);
		rv_image.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
		rv_image.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		rv_image.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		rv_image.setHasFixedSize(true);
		//mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//解决更新 闪烁
		rv_image.setItemAnimator(null);
		rv_image.setNestedScrollingEnabled(false);
		mAdapter = new ImageSelectAdapter(this, mListData);
		rv_image.setAdapter(mAdapter);
	}

	@Override
	public void onPause() {
		super.onPause();

		CameraInstance.getInstance().stopCamera();
		Log.i("kwan", "activity onPause...");
		mCameraView.release(null);
		mCameraView.onPause();

	}

	@Override
	public void onResume() {
		super.onResume();
		mCameraView.onResume();
	}

	@Override
	protected void initViewSetting() {

	}

	@Override
	protected void initData() {

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v == iv_cam) {

			mCameraView.takePicture(new CameraRecordGLSurfaceView.TakePictureCallback() {
				@Override
				public void takePictureOK(Bitmap bmp) {

					if (bmp != null) {

						String s = ImageUtil.saveBitmap(bmp, PathUtil.getApplicationRootPath() + "take_" + System.currentTimeMillis() + ".jpg");
						bmp.recycle();
						mAdapter.addData(s);

						Log.e("kwan", "Take picture success!---" + s);
					} else
						Log.e("kwan", "Take picture failed!");
				}
			}, null, null, 1.0f, true);

		} else if (v == iv_light) {
            if (isFlashOff) {
                mCameraView.setFlashLightMode(Camera.Parameters.FLASH_MODE_TORCH);
                iv_light.setImageResource(R.mipmap.cam_light_on);
            }
            else {
                mCameraView.setFlashLightMode(Camera.Parameters.FLASH_MODE_OFF);
                iv_light.setImageResource(R.mipmap.cam_light_off);
            }

		} else if (v == iv_exchange) {
			mCameraView.switchCamera();
		} else if (v == tv_next) {

			if (mAdapter.getSelectData().size() > 0) {

				Bundle bundle = new Bundle();
				bundle.putSerializable("data", mAdapter.getSelectData());
				go2Activity(PhotoEditActivity.class, bundle, true);

			} else {
				toastMsg("至少选择一张图片");
			}

		}else if(v==iv_close) {
            super.onBackPressed();
        }else if(v==tv_ratio){

            CameraInstance.getInstance().stopCamera();
            Log.i("kwan", "activity onPause...");
            mCameraView.release(null);
            mCameraView.onPause();
            mCameraView.onResume();

            if(height==1000) {
                height = 750;
                tv_ratio.setImageResource(R.mipmap.ic_4_3);
            }
            else {
                height = 1000;
                tv_ratio.setImageResource(R.mipmap.ic_1_1);
            }
		    mCameraView.presetRecordingSize(750,height);
        }
	}
}
