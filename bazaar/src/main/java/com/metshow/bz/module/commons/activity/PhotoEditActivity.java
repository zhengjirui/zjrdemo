package com.metshow.bz.module.commons.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kwan.base.activity.BaseCommonActivity;
import com.kwan.base.adatper.CommonViewPageAdapter;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.util.PathUtil;
import com.metshow.bz.PhotoEdit.AppConstants;
import com.metshow.bz.PhotoEdit.EffectUtil;
import com.metshow.bz.PhotoEdit.ImageHelper;
import com.metshow.bz.PhotoEdit.ImageUtils;
import com.metshow.bz.PhotoEdit.LabelView;
import com.metshow.bz.PhotoEdit.MyHighlightView;
import com.metshow.bz.PhotoEdit.StringUtils;
import com.metshow.bz.PhotoEdit.TagItem;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.PhotoEditAdapter;
import com.metshow.bz.commons.adapter.StickerTypeAdapter;
import com.metshow.bz.commons.bean.FilterBean;
import com.metshow.bz.commons.bean.StickerType;
import com.metshow.bz.commons.bean.topic.StickerBean;
import com.metshow.bz.commons.widget.CustomViewPager;
import com.metshow.bz.commons.widget.PhotoEditView;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.PhotoEditPresenter;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.model.AspectRatio;
import com.yalantis.ucrop.view.CropImageView;
import com.yalantis.ucrop.view.widget.AspectRatioTextView;
import com.zhy.autolayout.AutoLinearLayout;

import org.wysaid.common.Common;
import org.wysaid.nativePort.CGENativeLibrary;
import org.wysaid.view.ImageGLSurfaceView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.yalantis.ucrop.UCrop.EXTRA_INPUT_URI;
import static com.yalantis.ucrop.UCrop.EXTRA_OUTPUT_URI;


public class PhotoEditActivity extends BaseCommonActivity implements SeekBar.OnSeekBarChangeListener, BZContract.IPhotoEditView {

	//	private ViewGroup drawArea;
//	private MyImageViewDrawableOverlay mImageView;
	//private ImageGLSurfaceView mGLImageView;
	//private Bitmap mBitmap;
	private String mCurrentConfig;
	//	private LabelSelector labelSelector;
	private CustomViewPager vp_image;
	private ArrayList<String> mImagePaths = new ArrayList<>();
	private ArrayList<Bitmap> mBitmaps = new ArrayList<>();
	private ArrayList<PhotoEditView> mPhotoEditViews = new ArrayList<>();
	private ArrayList<PhotoEditView> mRealPhotoEditViews = new ArrayList<>();
	private PhotoEditView mCurrentPhotoEditView;
	private TextView tv_tu, tv_lj, tv_tz, tv_bq, lastTextView;
	private RecyclerView recyclerView, rv_sticker_type;
	private PhotoEditAdapter mLjAdapter, mTuAdapter;
	private TextView tv_bqtip;
	private AutoLinearLayout ll_tz;
	private ArrayList<UCrop> mUCrops = new ArrayList<>();
	private View ll_caijian, ll_liangdu, ll_duibidu, ll_baohedu, ll_seweng, ll_corp, ll_tz2,
			ll_confirm, ll_edit, rl_edit, ll_recyclerView;

	private AspectRatioTextView tv_corp_free, tv_corp_11, tv_corp_23, tv_corp_32, tv_corp_34;
    private AutoLinearLayout ll_corp_free, ll_corp_11, ll_corp_23, ll_corp_32, ll_corp_34;

	private View sk_edit;
	private SeekBar sk_edit_liangdu, sk_edit_duibidu, sk_edit_baohedu, sk_edit_sewen;
	private ImageView iv_cancel, iv_ok;
	private TextView tv_optitle;
	private boolean isCorpMode = false;
	private static int MAX_VALUE = 255;
	private static int MID_VALUE = 127;

	private float mVelueSeDiao, mVelueBaoHeDu, mVelueLiangDu;

	private final int TAG_LIANGDU = 1;
	private final int TAG_DUIBIDU = 2;
	private final int TAG_BAOHEDU = 3;
	private final int TAG_SEWEN = 4;

	private int CurrentVelueTag = TAG_LIANGDU;
	private ArrayList<String> mFilterConfiger;
	private PhotoEditPresenter mPresenter;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();

		mPresenter = new PhotoEditPresenter(this);
		mImagePaths = (ArrayList<String>) getIntentData("data");
		mFilterConfiger = new ArrayList<>();

		for (String str : mImagePaths) {

			mFilterConfiger.add(null);

			//mImageUris.add(Uri.fromFile(new File(str)));
			String destinationFileName = "crop_" + str.substring(str.lastIndexOf("/") + 1, str.length());
			Log.e("kwan", "save path:" + destinationFileName);

			UCrop uCrop = UCrop.of(Uri.fromFile(new File(str)), Uri.fromFile(new File(PathUtil.getApplicationRootPath(), destinationFileName)));
			//uCrop = basisConfig(uCrop);
			uCrop = advancedConfig(uCrop);
			mUCrops.add(uCrop);

		}
	}

	ArrayList<HashMap<String, Object>> mStickerData = new ArrayList<>();

	@Override
	protected void initViews() {
		super.initViews();

		EffectUtil.clear();

		vp_image = (CustomViewPager) findViewById(R.id.vp_images);

		ll_tz = (AutoLinearLayout) findViewById(R.id.ll_tz);
		tv_tu = (TextView) findViewById(R.id.tv_tu);
		tv_lj = (TextView) findViewById(R.id.tv_lj);
		tv_tz = (TextView) findViewById(R.id.tv_tz);
		tv_bq = (TextView) findViewById(R.id.tv_bq);
		tv_bqtip = (TextView) findViewById(R.id.tv_bqtip);

		ll_tz2 = findViewById(R.id.ll_tz2);

		ll_caijian = findViewById(R.id.ll_caijian);
		ll_liangdu = findViewById(R.id.ll_liangdu);
		ll_duibidu = findViewById(R.id.ll_duibidu);
		ll_baohedu = findViewById(R.id.ll_baohedu);
		ll_seweng = findViewById(R.id.ll_seweng);
		ll_corp = findViewById(R.id.ll_corp);
		ll_confirm = findViewById(R.id.ll_confirm);
		ll_recyclerView = findViewById(R.id.ll_recyclerView);
		ll_edit = findViewById(R.id.ll_edit);
		rl_edit = findViewById(R.id.rl_edit);

		iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
		iv_ok = (ImageView) findViewById(R.id.iv_ok);
		tv_optitle = (TextView) findViewById(R.id.tv_optitle);

		tv_corp_free = (AspectRatioTextView) findViewById(R.id.tv_corp_free);
		tv_corp_11 = (AspectRatioTextView) findViewById(R.id.tv_corp_11);
		tv_corp_23 = (AspectRatioTextView) findViewById(R.id.tv_corp_23);
		tv_corp_32 = (AspectRatioTextView) findViewById(R.id.tv_corp_32);
		tv_corp_34 = (AspectRatioTextView) findViewById(R.id.tv_corp_34);

        ll_corp_free = (AutoLinearLayout) findViewById(R.id.ll_corp_free);
        ll_corp_11 = (AutoLinearLayout) findViewById(R.id.ll_corp_11);
        ll_corp_23 = (AutoLinearLayout) findViewById(R.id.ll_corp_23);
        ll_corp_32 = (AutoLinearLayout) findViewById(R.id.ll_corp_32);
        ll_corp_34 = (AutoLinearLayout) findViewById(R.id.ll_corp_34);

		sk_edit = findViewById(R.id.sk_edit);
//		sk_edit.setOnSeekBarChangeListener(this);

		sk_edit_liangdu = (SeekBar) findViewById(R.id.sk_edit_liangdu);
		sk_edit_duibidu = (SeekBar) findViewById(R.id.sk_edit_duibidu);
		sk_edit_baohedu = (SeekBar) findViewById(R.id.sk_edit_baohedu);
		sk_edit_sewen = (SeekBar) findViewById(R.id.sk_edit_sewen);

		sk_edit_liangdu.setOnSeekBarChangeListener(this);
		sk_edit_duibidu.setOnSeekBarChangeListener(this);
		sk_edit_baohedu.setOnSeekBarChangeListener(this);
		sk_edit_sewen.setOnSeekBarChangeListener(this);

		lastTextView = tv_lj;

		tv_tu.setOnClickListener(this);
		tv_lj.setOnClickListener(this);
		tv_tz.setOnClickListener(this);
		tv_bq.setOnClickListener(this);
		tv_bqtip.setOnClickListener(this);
		ll_caijian.setOnClickListener(this);
		ll_liangdu.setOnClickListener(this);
		ll_duibidu.setOnClickListener(this);
		ll_baohedu.setOnClickListener(this);
		ll_seweng.setOnClickListener(this);

//		tv_corp_free.setOnClickListener(this);
//		tv_corp_11.setOnClickListener(this);
//		tv_corp_23.setOnClickListener(this);
//		tv_corp_32.setOnClickListener(this);
//		tv_corp_34.setOnClickListener(this);

		iv_cancel.setOnClickListener(this);
		iv_ok.setOnClickListener(this);

		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setHasFixedSize(true);

		rv_sticker_type = (RecyclerView) findViewById(R.id.rv_sticker_type);
		rv_sticker_type.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
		rv_sticker_type.setItemAnimator(new DefaultItemAnimator());
		rv_sticker_type.setHasFixedSize(true);

		ArrayList<HashMap<String, Object>> ljdata = new ArrayList<>();

		int v = 0;

		for (String str : RecorderActivity.text) {
			HashMap<String, Object> data = new HashMap<>();
			data.put("name", str);
			data.put("icon", RecorderActivity.filterIcon[v]);
			ljdata.add(data);
			v++;
		}

		mLjAdapter = new PhotoEditAdapter(this, ljdata);
		mTuAdapter = new PhotoEditAdapter(this, mStickerData);

		mLjAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {

				mFilterConfiger.set(mCurrentIndex, RecorderActivity.mFilterString[position]);
				mCurrentPhotoEditView.mImageGLSurfaceView.setFilterWithConfig(RecorderActivity.mFilterString[position]);
				mCurrentPhotoEditView.mImageGLSurfaceView.getResultBitmap(new ImageGLSurfaceView.QueryResultBitmapCallback() {
					@Override
					public void get(final Bitmap bmp) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mBitmaps.set(mCurrentIndex, bmp);
								mCurrentPhotoEditView.mEditImageView.setImageBitmap(bmp);
							}
						});
					}
				});
			}
		});

		mTuAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, final int position) {

				mCurrentPhotoEditView.stickerView.setLocked(false);
				mCurrentPhotoEditView.stickerView.bringToFront();
				mCurrentPhotoEditView.stickerView.removeAllStickers();

				new Thread() {
					@Override
					public void run() {
						super.run();

						final Drawable drawable = decodeUriAsBitmapFromNet((String) mStickerData.get(position).get("icon"));
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mCurrentPhotoEditView.stickerView.addSticker(new DrawableSticker(drawable));
							}
						});
					}
				}.start();

				Log.e("kwan", "xx");
				Glide.with(PhotoEditActivity.this)
						.load((String) mStickerData.get(position).get("icon"))
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.listener(new RequestListener<String, GlideDrawable>() {

							@Override
							public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
								return false;
							}

							@Override
							public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
								Log.e("kwan", "xx");
								return false;
							}
						})
						.centerCrop()
						.placeholder(com.kwan.base.R.mipmap.item_default);

//				Drawable drawable =
//						ContextCompat.getDrawable(PhotoEditActivity.this, Sticksers[position]);


			}
		});

		recyclerView.setAdapter(mLjAdapter);


		//设置View pager
		int i = 0;

		for (final String str : mImagePaths) {

			Log.e("kwan", "surfaceCreated--" + mImagePaths.size());
			final PhotoEditView photoEditView = new PhotoEditView(this);
			final Bitmap bitmap = com.kwan.base.util.ImageUtil.compressBitmapFromPathBySize(str, 500, 500);

			photoEditView.mImageGLSurfaceView.setSurfaceCreatedCallback(new ImageGLSurfaceView.OnSurfaceCreatedCallback() {
				@Override
				public void surfaceCreated() {

					Log.e("kwan", "surfaceCreated--" + str);

					photoEditView.mImageGLSurfaceView.setImageBitmap(bitmap);
					photoEditView.mImageGLSurfaceView.setFilterWithConfig(mCurrentConfig);
					photoEditView.mImageGLSurfaceView.setDisplayMode(ImageGLSurfaceView.DisplayMode.DISPLAY_ASPECT_FIT);

				}
			});

			photoEditView.mEditImageView.setImageBitmap(bitmap);

			try {
				photoEditView.mGestureCropImageView.setImageUri((Uri) mUCrops.get(i).getCropOptionsBundle().getParcelable(EXTRA_INPUT_URI),
						(Uri) mUCrops.get(i).getCropOptionsBundle().getParcelable(EXTRA_OUTPUT_URI));
			} catch (Exception e) {
				e.printStackTrace();
			}

			mPhotoEditViews.add(photoEditView);
			mBitmaps.add(bitmap);
			i++;
		}

		mCurrentPhotoEditView = mPhotoEditViews.get(0);
		CommonViewPageAdapter adapter = new CommonViewPageAdapter<>(mPhotoEditViews);
		vp_image.setAdapter(adapter);
		vp_image.setOffscreenPageLimit(10);

		vp_image.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {

				mCurrentIndex = position;
				mCurrentPhotoEditView = mPhotoEditViews.get(position);
				tv_title.setText("图片编辑（" + (position + 1) + "/" + mImagePaths.size() + "）");

				//mCurrentPhotoEditView.mImageGLSurfaceView.setFilterWithConfig(mFilterConfiger.get(position));

				if (isCorpMode)
					mCurrentPhotoEditView.ucrop_frame.bringToFront();


			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});

		CGENativeLibrary.setLoadImageCallback(loadImageCallback, null);

		sk_edit_liangdu.setMax(MAX_VALUE);
		sk_edit_duibidu.setMax(MAX_VALUE);
		sk_edit_baohedu.setMax(MAX_VALUE);
		sk_edit_sewen.setMax(MAX_VALUE);

		sk_edit_liangdu.setProgress(MID_VALUE);
		sk_edit_duibidu.setProgress(MID_VALUE);
		sk_edit_baohedu.setProgress(MID_VALUE);
		sk_edit_sewen.setProgress(MID_VALUE);
//		sk_edit.setMax(MAX_VALUE);
//		sk_edit.setProgress(MID_VALUE);

		//vp_image.setOnClickListener(this);

	}


	/**
	 * 根据图片的url路径获得Bitmap对象
	 *
	 * @param url
	 * @return
	 */
	private Drawable decodeUriAsBitmapFromNet(String url) {

		URL fileUrl = null;
		Bitmap bitmap = null;

		try {
			fileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try {
			HttpURLConnection conn = (HttpURLConnection) fileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new BitmapDrawable(bitmap);
	}

	MyHighlightView mCurrentHighlightView;

	@Override
	protected void setUpTitleRightView(View v) {
		super.setUpTitleRightView(v);

		((TextView) v).setText("下一步");
		((TextView) v).setTextColor(getResources().getColor(R.color.redWin));
		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				savePicture();
			}
		});
	}

	ArrayList<String> tags = new ArrayList<>();

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
			addLabel(new TagItem(AppConstants.POST_TYPE_TAG, data.getStringExtra("tag")));
			tags.add(data.getStringExtra("tag"));

	}

	@Override
	protected int getTitleBarRightLayoutId() {
		return R.layout.title_right_txt;
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_photo_edit;
	}

	@Override
	protected void initData() {
		setupAspectRatioWidget();
		mPresenter.getStickerType();
		mPresenter.getFilter();
	}

	private List<AspectRatioTextView> mCropAspectRatioViews = new ArrayList<>();
    private List<AutoLinearLayout> mCropViews = new ArrayList<>();

	private void setupAspectRatioWidget() {

		tv_corp_free.setAspectRatio(new AspectRatio(getString(com.yalantis.ucrop.R.string.ucrop_label_original).toUpperCase(),
				CropImageView.SOURCE_IMAGE_ASPECT_RATIO, CropImageView.SOURCE_IMAGE_ASPECT_RATIO));
		tv_corp_11.setAspectRatio(new AspectRatio(null, 1, 1));
		tv_corp_23.setAspectRatio(new AspectRatio(null, 2, 3));
		tv_corp_32.setAspectRatio(new AspectRatio(null, 3, 2));
		tv_corp_34.setAspectRatio(new AspectRatio(null, 3, 4));

		tv_corp_free.setSelected(true);

		mCropAspectRatioViews.add(tv_corp_free);
        mCropAspectRatioViews.add(tv_corp_11);
        mCropAspectRatioViews.add(tv_corp_23);
        mCropAspectRatioViews.add(tv_corp_32);
        mCropAspectRatioViews.add(tv_corp_34);

        mCropViews.add(ll_corp_free);
        mCropViews.add(ll_corp_11);
        mCropViews.add(ll_corp_23);
        mCropViews.add(ll_corp_32);
        mCropViews.add(ll_corp_34);

        int p = 0;

		for (int i=0;i<mCropAspectRatioViews.size();i++) {

            final AspectRatioTextView cropAspectRatioView = mCropAspectRatioViews.get(i);

            cropAspectRatioView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentPhotoEditView.mGestureCropImageView.setTargetAspectRatio(
                            cropAspectRatioView.getAspectRatio(false));
                    mCurrentPhotoEditView.mGestureCropImageView.setImageToWrapCropBounds();
                    if (!v.isSelected()) {
                        for (AspectRatioTextView cropAspectRatioView : mCropAspectRatioViews) {
                            cropAspectRatioView.setSelected(cropAspectRatioView == v);
                        }
                    }
                }
            });
            final int finalI = i;
            mCropViews.get(i).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					mCurrentPhotoEditView.mGestureCropImageView.setTargetAspectRatio(
							cropAspectRatioView.getAspectRatio(false));
					mCurrentPhotoEditView.mGestureCropImageView.setImageToWrapCropBounds();

					if (!v.isSelected()) {
						for (AspectRatioTextView cropAspectRatioView : mCropAspectRatioViews) {
							cropAspectRatioView.setSelected(cropAspectRatioView == mCropAspectRatioViews.get(finalI));
						}
					}
				}
			});

			p++;
		}
	}


	int mCurrentIndex = 0;

	public static int[] Sticksers = {R.drawable.sticker1, R.drawable.sticker2, R.drawable.sticker3, R.drawable.sticker4,
			R.drawable.sticker5, R.drawable.sticker6, R.drawable.sticker7, R.drawable.sticker8};

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v == tv_tu && lastTextView != tv_tu) { //贴图

			mCurrentPhotoEditView.stickerView.setLocked(false);
			rv_sticker_type.setVisibility(View.VISIBLE);
			ll_recyclerView.bringToFront();
			recyclerView.setAdapter(mTuAdapter);
			tv_tu.setTextColor(getResources().getColor(R.color.redWin));
			lastTextView.setTextColor(getResources().getColor(R.color.txt_cobalt_blue));
			lastTextView = tv_tu;

		} else if (v == tv_bq && lastTextView != tv_bq) { //标签

			mCurrentPhotoEditView.stickerView.setLocked(true);

			tv_bqtip.bringToFront();
			tv_bq.setTextColor(getResources().getColor(R.color.redWin));
			lastTextView.setTextColor(getResources().getColor(R.color.txt_cobalt_blue));
			lastTextView = tv_bq;

		} else if (v == tv_lj && lastTextView != tv_lj) { //滤镜

			mCurrentPhotoEditView.stickerView.setLocked(true);

			ll_recyclerView.bringToFront();
			recyclerView.setAdapter(mLjAdapter);
			rv_sticker_type.setVisibility(View.INVISIBLE);
			tv_lj.setTextColor(getResources().getColor(R.color.redWin));
			lastTextView.setTextColor(getResources().getColor(R.color.txt_cobalt_blue));
			lastTextView = tv_lj;

		} else if (v == tv_tz && lastTextView != tv_tz) {//调整

			mCurrentPhotoEditView.stickerView.setLocked(true);
			ll_tz.bringToFront();
			tv_tz.setTextColor(getResources().getColor(R.color.redWin));
			lastTextView.setTextColor(getResources().getColor(R.color.txt_cobalt_blue));
			lastTextView = tv_tz;

		} else if (v == ll_caijian) {

			tv_optitle.setText("裁剪");
			vp_image.setPagingEnabled(false);
			isCorpMode = true;
			sk_edit.setVisibility(View.GONE);
			ll_corp.setVisibility(View.VISIBLE);
			mCurrentPhotoEditView.ucrop_frame.bringToFront();
			ll_tz2.bringToFront();
			ll_confirm.bringToFront();

			mCurrentPhotoEditView.mEditImageView.setVisibility(View.GONE);
			mCurrentPhotoEditView.mUCropView.setVisibility(View.VISIBLE);

		} else if (v == ll_liangdu) {//亮度

			vp_image.setPagingEnabled(false);
			tv_optitle.setText("亮度");
			CurrentVelueTag = TAG_LIANGDU;

			mCurrentPhotoEditView.ucrop_frame.bringToFront();
			mCurrentPhotoEditView.mEditImageView.setVisibility(View.VISIBLE);
			mCurrentPhotoEditView.mUCropView.setVisibility(View.GONE);
			ll_confirm.bringToFront();
			ll_tz2.bringToFront();
			ll_corp.setVisibility(View.GONE);
			sk_edit.setVisibility(View.VISIBLE);

			sk_edit_liangdu.setVisibility(View.VISIBLE);

			sk_edit_duibidu.setVisibility(View.GONE);
			sk_edit_baohedu.setVisibility(View.GONE);
			sk_edit_sewen.setVisibility(View.GONE);


		} else if (v == ll_duibidu) {

			vp_image.setPagingEnabled(false);
			tv_optitle.setText("对比度");
			CurrentVelueTag = TAG_DUIBIDU;

			mCurrentPhotoEditView.ucrop_frame.bringToFront();
			mCurrentPhotoEditView.mEditImageView.setVisibility(View.VISIBLE);
			mCurrentPhotoEditView.mUCropView.setVisibility(View.GONE);
			ll_confirm.bringToFront();
			ll_tz2.bringToFront();
			ll_corp.setVisibility(View.GONE);
			sk_edit.setVisibility(View.VISIBLE);

			sk_edit_liangdu.setVisibility(View.GONE);
			sk_edit_duibidu.setVisibility(View.VISIBLE);
			sk_edit_baohedu.setVisibility(View.GONE);
			sk_edit_sewen.setVisibility(View.GONE);

			sk_edit_duibidu.bringToFront();


		} else if (v == ll_baohedu) {

			vp_image.setPagingEnabled(false);
			tv_optitle.setText("饱和度");
			CurrentVelueTag = TAG_BAOHEDU;

			mCurrentPhotoEditView.ucrop_frame.bringToFront();
			mCurrentPhotoEditView.mEditImageView.setVisibility(View.VISIBLE);
			mCurrentPhotoEditView.mUCropView.setVisibility(View.GONE);
			ll_confirm.bringToFront();
			ll_tz2.bringToFront();
			ll_corp.setVisibility(View.GONE);
			sk_edit.setVisibility(View.VISIBLE);

			sk_edit_liangdu.setVisibility(View.GONE);
			sk_edit_duibidu.setVisibility(View.GONE);
			sk_edit_baohedu.setVisibility(View.VISIBLE);
			sk_edit_sewen.setVisibility(View.GONE);

		} else if (v == ll_seweng) {

			vp_image.setPagingEnabled(false);
			tv_optitle.setText("色温");
			CurrentVelueTag = TAG_SEWEN;

			mCurrentPhotoEditView.ucrop_frame.bringToFront();
			mCurrentPhotoEditView.mEditImageView.setVisibility(View.VISIBLE);
			mCurrentPhotoEditView.mUCropView.setVisibility(View.GONE);

			ll_confirm.bringToFront();
			ll_tz2.bringToFront();
			ll_corp.setVisibility(View.GONE);
			sk_edit.setVisibility(View.VISIBLE);

			sk_edit_liangdu.setVisibility(View.GONE);
			sk_edit_duibidu.setVisibility(View.GONE);
			sk_edit_baohedu.setVisibility(View.GONE);
			sk_edit_sewen.setVisibility(View.VISIBLE);

			sk_edit_sewen.bringToFront();

		} else if (v == iv_cancel) {// 编辑取消

			vp_image.setPagingEnabled(true);
			isCorpMode = false;
			ll_edit.bringToFront();
			rl_edit.bringToFront();
			mCurrentPhotoEditView.rl_edit.bringToFront();

		} else if (v == iv_ok) { //编辑 确认

			vp_image.setPagingEnabled(true);
			if (isCorpMode)
				cropAndSaveImage();
			else {

				mBitmaps.set(mCurrentIndex, mCurrentEditSaveBitmap);
				ll_edit.bringToFront();
				rl_edit.bringToFront();
				mCurrentPhotoEditView.rl_edit.bringToFront();

				String picName = System.currentTimeMillis() + ".jpg";
				String fileName = null;
				try {
					fileName = ImageUtils.saveToFile(PathUtil.getApplicationRootPath() + picName, false, mCurrentEditSaveBitmap);
				} catch (IOException e) {
					e.printStackTrace();
				}

				mImagePaths.set(mCurrentIndex, fileName);
				mCurrentPhotoEditView.mImageGLSurfaceView.setImageBitmap(mCurrentEditSaveBitmap);
			}
		} else if (tv_bqtip == v) {

			Bundle bundle = new Bundle();
			bundle.putBoolean("data", true);
			go2ActivityForResult(TagActivity.class, 1000, bundle, 0, 0);

		}

	}


	private void cropAndSaveImage() {

		showProgress("");

		mCurrentPhotoEditView.mGestureCropImageView.cropAndSaveImage(Bitmap.CompressFormat.JPEG, 90, new BitmapCropCallback() {

			@Override
			public void onBitmapCropped(@NonNull Uri resultUri, int offsetX, int offsetY, int imageWidth, int imageHeight) {
//				setResultUri(resultUri, mGestureCropImageView.getTargetAspectRatio(), offsetX, offsetY, imageWidth, imageHeight);
//				finish();

				Log.e("kwan", "result path:" + resultUri.getPath());

				mImagePaths.set(mCurrentIndex, resultUri.getPath());

				try {
					mCurrentPhotoEditView.mGestureCropImageView.setImageUri(resultUri, (Uri) mUCrops.get(mCurrentIndex).getCropOptionsBundle().getParcelable(EXTRA_OUTPUT_URI));
				} catch (Exception e) {
					e.printStackTrace();
				}
				isCorpMode = false;
				ll_edit.bringToFront();
				rl_edit.bringToFront();
				mCurrentPhotoEditView.rl_edit.bringToFront();
				final Bitmap bitmap = com.kwan.base.util.ImageUtil.compressBitmapFromPathBySize(resultUri.getPath(), 500, 500);
				Log.e("kwan","resultUri.getPath()::"+resultUri.getPath());
				mBitmaps.set(mCurrentIndex,bitmap);
				mCurrentPhotoEditView.mImageGLSurfaceView.setImageBitmap(bitmap);
                mCurrentPhotoEditView.mEditImageView.setImageBitmap(bitmap);

				dismissProgress();

			}

			@Override
			public void onCropFailure(@NonNull Throwable t) {
//				setResultError(t);
//				finish();
				Log.e("kwan", "result path:" + t.getMessage());
			}
		});

	}

	private List<LabelView> labels = new ArrayList<>();

	//添加标签
	private void addLabel(TagItem tagItem) {

		mCurrentPhotoEditView.labelSelector.hide();
		mCurrentPhotoEditView.emptyLabelView.setVisibility(View.INVISIBLE);

		if (labels.size() >= 5) {
			//	alert("温馨提示", "您只能添加5个标签！", "确定", null, null, null, true);
		} else {

			int left = mCurrentPhotoEditView.emptyLabelView.getLeft();
			int top = mCurrentPhotoEditView.emptyLabelView.getTop();

			if (labels.size() == 0 && left == 0 && top == 0) {
				left = mCurrentPhotoEditView.mImageView.getWidth() / 2 - 10;
				top = mCurrentPhotoEditView.mImageView.getWidth() / 2;
			}

			final LabelView label = new LabelView(this);

			label.tv_delete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					EffectUtil.removeLabelEditable(mCurrentPhotoEditView.mImageView, mCurrentPhotoEditView.drawArea, label);
				}
			});

			label.tv_edit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					EffectUtil.removeLabelEditable(mCurrentPhotoEditView.mImageView, mCurrentPhotoEditView.drawArea, label);
				}
			});

			label.init(tagItem);
			EffectUtil.addLabelEditable(mCurrentPhotoEditView.mImageView, mCurrentPhotoEditView.drawArea, label, left, top);
			labels.add(label);

			Log.e("kwan","add abel");
            mCurrentPhotoEditView.rl_edit.bringToFront();
		}
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


	//保存图片
	private void savePicture() {

		showProgress("");



		for (final PhotoEditView view : mPhotoEditViews) {

			view.mImageGLSurfaceView.getResultBitmap(new ImageGLSurfaceView.QueryResultBitmapCallback() {
				@Override
				public void get(final Bitmap bmp) {

					//final String s = ImageUtil.saveBitmap(bmp);
					//sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + s)));
					//加滤镜

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								String picName = "final_" + System.currentTimeMillis() + ".jpg";
								final String fileName;

								fileName = ImageUtils.saveToFile(PathUtil.getApplicationRootPath() + picName, false, bmp);
								view.iv_sticker_bmp.setImageBitmap(bmp);
								view.stickerView.save(new File(fileName));
								mCompletePaths.add(fileName);
								completeNum++;

								if (completeNum == mImagePaths.size()) {
									dismissProgress();
									toastMsg("完成");
									Bundle bundle = new Bundle();
									bundle.putInt("mode", 2);
									bundle.putStringArrayList("output", mCompletePaths);
									go2Activity(SendTopicActivity.class, bundle, true);
								}

								Log.e("kwan", "mPhotoEditViews::" + fileName);

							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				}
			});


		}
	}

	@Override
	protected String getTitleTxt() {
		return "图片编辑（1/" + mImagePaths.size() + "）";
	}

	int completeNum = 0;
	ArrayList<String> mCompletePaths = new ArrayList<>();
	Bitmap mCurrentEditSaveBitmap;

	// seekBar
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch (seekBar.getId()) {
			case R.id.sk_edit_liangdu:
				mVelueLiangDu = progress * 1.0F / MID_VALUE;
				break;

			case R.id.sk_edit_baohedu:
				mVelueBaoHeDu = progress * 1.0F / MID_VALUE;
				break;

			case R.id.sk_edit_sewen:
				mVelueSeDiao = (progress - MID_VALUE) * 1.0F / MID_VALUE * 180;
				break;
		}
		mCurrentEditSaveBitmap = ImageHelper.handleImageEffect(
				mBitmaps.get(mCurrentIndex), mVelueSeDiao, mVelueBaoHeDu, mVelueLiangDu);

		mCurrentPhotoEditView.mEditImageView.setImageBitmap(mCurrentEditSaveBitmap);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	List<StickerType> mStickerType;
	StickerTypeAdapter mStickerTypeAdapter;

	@Override
	public void getStickerTypeSuccess(List<StickerType> items) {
		mStickerType = items;

		Log.e("kwan", items.size() + "");
		mStickerTypeAdapter = new StickerTypeAdapter(this, items);

		mStickerTypeAdapter.setItemClickListener(new StickerTypeAdapter.ItemClickListener() {
			@Override
			public void onItemClick(View v, StickerType item, int position) {
				mPresenter.getSticker(item.getStickerTypeId());

				if (mAllStickerData.get(item.getStickerTypeId()) == null) {
					mPresenter.getSticker(item.getStickerTypeId());
				} else {
					setSticker(mAllStickerData.get(item.getStickerTypeId()));
				}
			}
		});

		rv_sticker_type.setAdapter(mStickerTypeAdapter);

		mPresenter.getSticker(items.get(0).getStickerTypeId());

	}

	@Override
	public void getFilterSuccess(List<FilterBean> items) {
	}

	HashMap<Long, List<StickerBean>> mAllStickerData = new HashMap<>();

	@Override
	public void getStickerSuccess(List<StickerBean> items) {
		mAllStickerData.put(items.get(0).getStickerTypeId(), items);
		setSticker(items);
	}

	void setSticker(List<StickerBean> data) {

		Log.e("kwan", "in set");

		mStickerData.clear();
		for (StickerBean res : data) {
			HashMap<String, Object> datax = new HashMap<>();
			datax.put("icon", res.getPic());
			mStickerData.add(datax);
		}

		mTuAdapter.setNewData(mStickerData);

	}

	private class SavePicToFileTask extends AsyncTask<Bitmap, Void, String> {

		Bitmap bitmap;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					showProgress("图片处理中...");
				}
			});
		}

		@Override
		protected String doInBackground(Bitmap... params) {
			String fileName = null;

			try {

				bitmap = params[0];
				String picName = "final_" + System.currentTimeMillis() + ".jpg";
				fileName = ImageUtils.saveToFile(PathUtil.getApplicationRootPath() + picName, false, bitmap);

			} catch (Exception e) {

				e.printStackTrace();
				toastMsg("图片处理错误，请退出相机并重试");
			}
			return fileName;
		}

		@Override
		protected void onPostExecute(String fileName) {
			super.onPostExecute(fileName);

			Log.e("kwan", "path::" + fileName);

			if (StringUtils.isEmpty(fileName)) {
				return;
			}

			completeNum++;
			mCompletePaths.add(fileName);

			Log.e("kwan", "completeNum:" + completeNum);
			Log.e("kwan", "mImagePaths.size():" + mImagePaths.size());

			if (completeNum == mImagePaths.size()) {
				dismissProgress();
				toastMsg("完成");
				Bundle bundle = new Bundle();
				bundle.putInt("mode", 2);
				bundle.putStringArrayList("output", mCompletePaths);
				go2Activity(SendTopicActivity.class, bundle, true);
			}

			//将照片信息保存至sharedPreference
			//保存标签信息
//			List<TagItem> tagInfoList = new ArrayList<TagItem>();
//			for (LabelView label : labels) {
//				tagInfoList.add(label.getTagInfo());
//			}

			//将图片信息通过EventBus发送到MainActivity
//			FeedItem feedItem = new FeedItem(tagInfoList,fileName);
//			EventBus.getDefault().post(feedItem);
			//CameraManager.getInst().close();
		}
	}


	private UCrop advancedConfig(@NonNull UCrop uCrop) {
		UCrop.Options options = new UCrop.Options();
		options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
		options.setCompressionQuality(90);
		options.setHideBottomControls(false);
		options.setFreeStyleCropEnabled(true);
		return uCrop.withOptions(options);
	}
}
