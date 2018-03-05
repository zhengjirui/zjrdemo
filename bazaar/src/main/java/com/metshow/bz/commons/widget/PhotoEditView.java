package com.metshow.bz.commons.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.metshow.bz.PhotoEdit.EffectUtil;
import com.metshow.bz.PhotoEdit.LabelSelector;
import com.metshow.bz.PhotoEdit.LabelView;
import com.metshow.bz.PhotoEdit.MyImageViewDrawableOverlay;
import com.metshow.bz.R;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;
import com.xiaopo.flying.sticker.ZoomIconEvent;
import com.yalantis.ucrop.view.GestureCropImageView;
import com.yalantis.ucrop.view.OverlayView;
import com.yalantis.ucrop.view.TransformImageView;
import com.yalantis.ucrop.view.UCropView;
import com.zhy.autolayout.AutoRelativeLayout;

import org.wysaid.view.ImageGLSurfaceView;

import java.util.Arrays;

/**
 * Created by Mr.Kwan on 2017-11-30.
 *
 *                    _ooOoo_
 *                   o8888888o
 *                   88" . "88
 *                   (| -_- |)
 *                    O\ = /O
 *                ____/`---'\____
 *              .   ' \\| |// `.
 *               / \\||| : |||// \
 *             / _||||| -:- |||||- \
 *               | | \\\ - /// | |
 *             | \_| ''\---/'' | |
 *              \ .-\__ `-` ___/-. /
 *           ___`. .' /--.--\ `. . __
 *        ."" '< `.___\_<|>_/___.' >'"".
 *       | | : `- \`.;`\ _ /`;.`/ - ` : | |
 *         \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 *                    `=---='
 *                                                    /\_/\
 *   佛祖保佑                                        =( °w°)=
 *    一次过                                          )   ( //
 *   永无BUG                                         (____)//
 *
 */


public class PhotoEditView extends AutoRelativeLayout {

	public Context mContext;
	public View mRootView;
	public ViewGroup drawArea;
	public View overlay;
	public MyImageViewDrawableOverlay mImageView;
	public ImageGLSurfaceView mImageGLSurfaceView;
	public LabelSelector labelSelector;
	public LabelView emptyLabelView;
	public RelativeLayout rl_edit;
	public UCropView mUCropView;
	public GestureCropImageView mGestureCropImageView;
	public OverlayView mOverlayView;
	public FrameLayout ucrop_frame;

	public ImageView mEditImageView,iv_sticker_bmp;
	public StickerView stickerView;

	public PhotoEditView(Context context) {
		this(context, null);
	}

	public PhotoEditView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PhotoEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		init();
	}

	private void init() {

		mRootView = inflate(getContext(), R.layout.photo_edit_view, this);

		//裁剪 相关

		mUCropView = (UCropView) mRootView.findViewById(com.yalantis.ucrop.R.id.ucrop);
		mGestureCropImageView = mUCropView.getCropImageView();
		mOverlayView = mUCropView.getOverlayView();
		mGestureCropImageView.setTransformImageListener(mImageListener);

		ucrop_frame = (FrameLayout) mRootView.findViewById(R.id.ucrop_frame);
		rl_edit= (RelativeLayout) mRootView.findViewById(R.id.rl_edit);
		mEditImageView = ((ImageView) findViewById(com.yalantis.ucrop.R.id.image_view_logo));
		mImageGLSurfaceView = (ImageGLSurfaceView) mRootView.findViewById(R.id.image);

		drawArea = (ViewGroup) mRootView.findViewById(R.id.drawArea);
		overlay = LayoutInflater.from(mContext).inflate(
				R.layout.view_drawable_overlay, null);

		mImageView = (MyImageViewDrawableOverlay) overlay.findViewById(R.id.drawable_overlay);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		mImageView.setLayoutParams(params);
		overlay.setLayoutParams(params);
		drawArea.addView(overlay);

		//添加标签选择器
		RelativeLayout.LayoutParams rparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		labelSelector = new LabelSelector(mContext);
		labelSelector.setLayoutParams(rparams);
		drawArea.addView(labelSelector);
		labelSelector.hide();

		//初始化空白标签
		emptyLabelView = new LabelView(mContext);
		emptyLabelView.setEmpty();
		EffectUtil.addLabelEditable(mImageView, drawArea, emptyLabelView,
				mImageView.getWidth() / 2, mImageView.getWidth() / 2);
		emptyLabelView.setVisibility(View.GONE);

		stickerView = (StickerView) findViewById(R.id.sticker_view);
		iv_sticker_bmp = (ImageView) findViewById(R.id.iv_sticker_bmp);

		//currently you can config your own icons and icon event
		//the event you can custom
		BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(mContext,
				com.xiaopo.flying.sticker.R.drawable.sticker_ic_close_white_18dp),
				BitmapStickerIcon.LEFT_TOP);
		deleteIcon.setIconEvent(new DeleteIconEvent());

		BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(mContext,
				com.xiaopo.flying.sticker.R.drawable.sticker_ic_scale_white_18dp),
				BitmapStickerIcon.RIGHT_BOTOM);
		zoomIcon.setIconEvent(new ZoomIconEvent());

		BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(mContext,
				com.xiaopo.flying.sticker.R.drawable.sticker_ic_flip_white_18dp),
				BitmapStickerIcon.RIGHT_TOP);
		//flipIcon.setIconEvent(new FlipHorizontallyEvent());

//		BitmapStickerIcon heartIcon =
//				new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp),
//						BitmapStickerIcon.LEFT_BOTTOM);
		//heartIcon.setIconEvent(new HelloIconEvent());

		stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon, flipIcon));
		//stickerView.setBackgroundColor(Color.WHITE);
		stickerView.setLocked(false);
		stickerView.setConstrained(true);

		final String TAG = "kwan";

		stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
			@Override
			public void onStickerAdded(@NonNull Sticker sticker) {
				Log.d(TAG, "onStickerAdded");
			}

			@Override
			public void onStickerClicked(@NonNull Sticker sticker) {
				//stickerView.removeAllSticker();
				if (sticker instanceof TextSticker) {
					((TextSticker) sticker).setTextColor(Color.RED);
					stickerView.replace(sticker);
					stickerView.invalidate();
				}
				Log.d(TAG, "onStickerClicked");
			}

			@Override
			public void onStickerDeleted(@NonNull Sticker sticker) {
				Log.d(TAG, "onStickerDeleted");
			}

			@Override
			public void onStickerDragFinished(@NonNull Sticker sticker) {
				Log.d(TAG, "onStickerDragFinished");
			}

			@Override
			public void onStickerTouchedDown(@NonNull Sticker sticker) {
				Log.d(TAG, "onStickerTouchedDown");
			}

			@Override
			public void onStickerZoomFinished(@NonNull Sticker sticker) {
				Log.d(TAG, "onStickerZoomFinished");
			}

			@Override
			public void onStickerFlipped(@NonNull Sticker sticker) {
				Log.d(TAG, "onStickerFlipped");
			}

			@Override
			public void onStickerDoubleTapped(@NonNull Sticker sticker) {
				Log.d(TAG, "onDoubleTapped: double tap will be with two click");
			}
		});
	}


	public void setFilter(String config) {
		mImageGLSurfaceView.setFilterWithConfig(config);
	}

	private TransformImageView.TransformImageListener mImageListener = new TransformImageView.TransformImageListener() {
		@Override
		public void onRotate(float currentAngle) {
			//setAngleText(currentAngle);
		}

		@Override
		public void onScale(float currentScale) {
			//setScaleText(currentScale);
		}

		@Override
		public void onLoadComplete() {
			mUCropView.animate().alpha(1).setDuration(300).setInterpolator(new AccelerateInterpolator());
//			mBlockingView.setClickable(false);
//			mShowLoader = false;
//			supportInvalidateOptionsMenu();
		}

		@Override
		public void onLoadFailure(@NonNull Exception e) {
//			setResultError(e);
//			finish();
		}

	};


}
