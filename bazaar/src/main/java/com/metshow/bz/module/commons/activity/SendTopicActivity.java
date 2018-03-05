package com.metshow.bz.module.commons.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwan.base.activity.BaseActivity;
import com.kwan.base.adatper.base.BaseQuickAdapter;
import com.kwan.base.adatper.base.BaseViewHolder;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.widget.FullScreenVideoView;
import com.metshow.bz.R;
import com.metshow.bz.commons.adapter.BZViewPageAdapter;
import com.metshow.bz.commons.bean.TopicSubject;
import com.metshow.bz.commons.bean.VideoUploadResult;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.datamodule.BZModule;
import com.metshow.bz.presenter.SendTopicPresenter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yongchun.library.view.ImageSelectorActivity;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SendTopicActivity extends BaseActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, BZViewPageAdapter.ClickItemListener, BZContract.ISendTopicView, UMShareListener {

	private SendTopicPresenter mPresenter;
	private AutoLinearLayout ll_tag, ll_topic;
	private TextView tv_send, tv_tag, tv_topic;
	private String mPath;
	private FullScreenVideoView mSufaceView;
	private ImageView iv_back, iv_with_wb, iv_with_wm, iv_with_wc;
	private boolean isWithWB, isWithWM, isWithWC;
	//private ViewPager viewPager;
	private final int MODE_VIDEO = 1;
	private final int MODE_PICTURE = 2;
	private int mode = MODE_VIDEO;
	private ArrayList<String> imagePaths;
	private ArrayList<ImageView> imageViews = new ArrayList<>();
	private String tag = "";
	private TopicSubject mTopicSubject;
	private EditText et_content;
	private RecyclerView rv_pic;
	private BaseQuickAdapter<String> mPicAdapter;

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 防止锁屏

		mPresenter = new SendTopicPresenter(this);
		mode = getIntent().getIntExtra("mode", MODE_VIDEO);

		if (getIntentData("subject") != null) {
			mTopicSubject = (TopicSubject) getIntentData("subject");
		}
		if (mode == MODE_VIDEO)
			mPath = getIntent().getStringExtra("output");
		else if (mode == MODE_PICTURE) {
			imagePaths = getIntent().getStringArrayListExtra("output");
			imagePaths.add("null");
		}
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_send_topic;
	}

	@Override
	protected void initViews() {

		ll_tag = (AutoLinearLayout) findViewById(R.id.ll_tag);
		ll_topic = (AutoLinearLayout) findViewById(R.id.ll_topic);
		mSufaceView = (FullScreenVideoView) findViewById(R.id.preview_theme);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		iv_with_wb = (ImageView) findViewById(R.id.iv_with_wb);
		//iv_with_wm = (ImageView) findViewById(R.id.iv_with_wm);
		iv_with_wc = (ImageView) findViewById(R.id.iv_with_wc);


		rv_pic = (RecyclerView) findViewById(R.id.rv_pic);

		//  viewPager = (ViewPager) findViewById(R.id.viewPager);
		tv_send = (TextView) findViewById(R.id.tv_send);
		tv_tag = (TextView) findViewById(R.id.tv_tag);
		tv_topic = (TextView) findViewById(R.id.tv_topic);

		et_content = (EditText) findViewById(R.id.et_content);
	}

	@Override
	protected void initViewSetting() {

		rv_pic.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
		rv_pic.setItemAnimator(new DefaultItemAnimator());
		rv_pic.setHasFixedSize(true);
		rv_pic.setNestedScrollingEnabled(false);

		//麻痹的 关闭硬件加速 否则 cardView 报错

		rv_pic.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		rv_pic.setLayerType(View.LAYER_TYPE_HARDWARE, null);

		if (mImageUtil == null)
			Log.e("kwan", "null");

		ll_tag.setOnClickListener(this);
		ll_topic.setOnClickListener(this);
		tv_send.setOnClickListener(this);
		iv_back.setOnClickListener(this);

		iv_with_wb.setOnClickListener(this);
		// iv_with_wm.setOnClickListener(this);
		iv_with_wc.setOnClickListener(this);

		if (mTopicSubject != null)
			tv_topic.setText(mTopicSubject.getName());

		switch (mode) {
			case MODE_VIDEO:

				//  viewPager.setVisibility(View.GONE);
				mSufaceView.setVideoPath(mPath);
				mSufaceView.setOnCompletionListener(this);
				mSufaceView.setOnPreparedListener(this);
				mSufaceView.setOnErrorListener(this);
				mSufaceView.start();
				break;

			case MODE_PICTURE:

				mSufaceView.setVisibility(View.GONE);
				mPicAdapter = new BaseQuickAdapter<String>(this, R.layout.list_item_pic, imagePaths) {
					@Override
					protected void convert(BaseViewHolder helper, String item, int position) {

						if (item.equals("null")) {

						    AutoLinearLayout.LayoutParams params = new AutoLinearLayout.LayoutParams(150,150);
                            params.gravity= Gravity.CENTER;
                            ((ImageView) helper.getView(R.id.iv_icon)).setLayoutParams(params);
                            mImageUtil.loadFromRes(R.mipmap.pic_add, (ImageView) helper.getView(R.id.iv_icon));
                        }
						else {

                            mImageUtil.loadFromFile(item, (ImageView) helper.getView(R.id.iv_icon));
                        }
					}
				};

				mPicAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
					@Override
					public void onItemClick(View view, int position) {

						if (position == imagePaths.size() - 1) {

							imagePaths.remove(position);
							Bundle bundle = new Bundle();
							bundle.putInt(ImageSelectorActivity.EXTRA_SELECT_MODE, ImageSelectorActivity.MODE_MULTIPLE);
							bundle.putBoolean(ImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
							bundle.putBoolean(ImageSelectorActivity.EXTRA_IS_FINISHRESULT, true);
							bundle.putStringArrayList(ImageSelectorActivity.EXTRA_INIT_SELECT, imagePaths);
							go2ActivityForResult(ImageSelectorActivity.class, 1, bundle, R.anim.push_left_in, R.anim.push_left_out);
						}

					}
				});


				rv_pic.setAdapter(mPicAdapter);

//				for (String str : imagePaths) {
//					ImageView imageView = new ImageView(this);
//					imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//					mImageUtil.loadFromFile(str, imageView);
//					imageViews.add(imageView);
//				}
//				BZViewPageAdapter<ImageView> adapter = new BZViewPageAdapter<>(imageViews);
//				adapter.setOnClickItemListener(this);
//				viewPager.setAdapter(adapter);

				break;
		}
	}

	@Override
	protected void initData() {

	}

	@Override
	public boolean isFullScreen() {
		return true;
	}

	@Override
	protected BasePresenter getPresent() {
		return mPresenter;
	}

	@Override
	public void onCompletion(MediaPlayer mediaPlayer) {

	}

	@Override
	public void onPrepared(MediaPlayer mediaPlayer) {
		// mediaPlayer.start();
		mediaPlayer.setLooping(true);
	}

	String TAG = "kwan";

	@Override
	public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
		toastMsg("视频播放发生错误");
		Log.d(TAG, "OnError - Error code: " + what + " Extra code: " + extra);
		switch (what) {
			case -1004:
				Log.d(TAG, "MEDIA_ERROR_IO");
				break;
			case -1007:
				Log.d(TAG, "MEDIA_ERROR_MALFORMED");
				break;
			case 200:
				Log.d(TAG, "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
				break;
			case 100:
				Log.d(TAG, "MEDIA_ERROR_SERVER_DIED");
				break;
			case -110:
				Log.d(TAG, "MEDIA_ERROR_TIMED_OUT");
				break;
			case 1:
				Log.d(TAG, "MEDIA_ERROR_UNKNOWN");
				break;
			case -1010:
				Log.d(TAG, "MEDIA_ERROR_UNSUPPORTED");
				break;
		}
		switch (extra) {
			case 800:
				Log.d(TAG, "MEDIA_INFO_BAD_INTERLEAVING");
				break;
			case 702:
				Log.d(TAG, "MEDIA_INFO_BUFFERING_END");
				break;
			case 701:
				Log.d(TAG, "MEDIA_INFO_METADATA_UPDATE");
				break;
			case 802:
				Log.d(TAG, "MEDIA_INFO_METADATA_UPDATE");
				break;
			case 801:
				Log.d(TAG, "MEDIA_INFO_NOT_SEEKABLE");
				break;
			case 1:
				Log.d(TAG, "MEDIA_INFO_UNKNOWN");
				break;
			case 3:
				Log.d(TAG, "MEDIA_INFO_VIDEO_RENDERING_START");
				break;
			case 700:
				Log.d(TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING");
				break;
		}
		return false;

	}

	@Override
	public void onClickItem(ViewGroup container, int position, View view) {

		if (mode == MODE_PICTURE) {

			Bundle bundle = new Bundle();
			bundle.putInt(ImageSelectorActivity.EXTRA_SELECT_MODE, ImageSelectorActivity.MODE_MULTIPLE);
			bundle.putBoolean(ImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
			bundle.putBoolean(ImageSelectorActivity.EXTRA_IS_FINISHRESULT, true);
			bundle.putStringArrayList(ImageSelectorActivity.EXTRA_INIT_SELECT, imagePaths);
			go2ActivityForResult(ImageSelectorActivity.class, 1, bundle, R.anim.push_left_in, R.anim.push_left_out);

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			if (requestCode == 1) { //点击图片返回

				imageViews.clear();
				imagePaths.clear();
				imagePaths.addAll(data.getStringArrayListExtra(ImageSelectorActivity.REQUEST_OUTPUT));
				imagePaths.add("null");

				mPicAdapter = new BaseQuickAdapter<String>(this, R.layout.list_item_pic, imagePaths) {
					@Override
					protected void convert(BaseViewHolder helper, String item, int position) {
						if (item.equals("null")) {

                            (helper.getView(R.id.iv_icon)).setPadding(50,50,50,50);
                            mImageUtil.loadFromRes(R.mipmap.icon, (ImageView) helper.getView(R.id.iv_icon));
                        }
						else{
                            ( helper.getView(R.id.iv_icon)).setPadding(0,0,0,0);
							mImageUtil.loadFromFile(item, (ImageView) helper.getView(R.id.iv_icon));
						}
					}
				};

				mPicAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
					@Override
					public void onItemClick(View view, int position) {
						if (position == imagePaths.size() - 1) {
							Bundle bundle = new Bundle();
							bundle.putInt(ImageSelectorActivity.EXTRA_SELECT_MODE, ImageSelectorActivity.MODE_MULTIPLE);
							bundle.putBoolean(ImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
							bundle.putBoolean(ImageSelectorActivity.EXTRA_IS_FINISHRESULT, true);
							bundle.putStringArrayList(ImageSelectorActivity.EXTRA_INIT_SELECT, imagePaths);
							go2ActivityForResult(ImageSelectorActivity.class, 1, bundle, R.anim.push_left_in, R.anim.push_left_out);
						}
					}
				});

				rv_pic.setAdapter(mPicAdapter);

			} else if (requestCode == 2) {//点击tag返回

				tag = data.getStringExtra("tag").replace("，", ",");
				tv_tag.setText(tag);

			} else if (requestCode == 3) {
				mTopicSubject = (TopicSubject) data.getSerializableExtra("subject");
				tv_topic.setText(mTopicSubject.getName());
			}
		}

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v == iv_back) {
			onBackPressed();
		} else if (v == ll_tag) {
			go2ActivityForResult(TagActivity.class, 2, null, R.anim.push_left_in, R.anim.push_left_out);
		} else if (v == ll_topic) {
			go2ActivityForResult(TopicSubjectActivity.class, 3, null, R.anim.push_left_in, R.anim.push_left_out);
		} else if (v == tv_send) {
			doSend();
		} else if (v == iv_with_wb) {
			isWithWB = !isWithWB;
			if (isWithWB)
				iv_with_wb.setImageResource(R.mipmap.share_weibo);
			else
				iv_with_wb.setImageResource(R.mipmap.share_weibo_normal);

		} else if (v == iv_with_wc) {
			isWithWC = !isWithWC;
			if (isWithWC)
				iv_with_wc.setImageResource(R.mipmap.share_wechat);
			else
				iv_with_wc.setImageResource(R.mipmap.share_wechat_normal);
		} else if (v == iv_with_wm) {
			isWithWM = !isWithWM;
			if (isWithWM)
				iv_with_wm.setImageResource(R.mipmap.share_wechatmoment);
			else
				iv_with_wm.setImageResource(R.mipmap.share_wechatmoment_normal);
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

	private void doSend() {
		showProgress("");
		if (mode == MODE_PICTURE) {

			imagePaths.remove(imagePaths.size() - 1);

			BZModule.isFaild = false;
			BZModule.imageSize = imagePaths.size();
			BZModule.backImageUrl = new ArrayList<>();

			for (String path : imagePaths)
				mPresenter.uploadimages(path);

		} else if (mode == MODE_VIDEO) {

			String topicId = "0";
			if (mTopicSubject != null)
				topicId = String.valueOf(mTopicSubject.getTopicId());

			mPresenter.upLoadVideo(mPath, tag, et_content.getText().toString(), "0", "0", topicId);
		}
	}

	String backImageUrl;

	@Override
	public void uploadImgSuccess(List<String> backImageUrl) {
		//            {\"title\":\"投稿\",\"ico\":\"ico1\",\"content\":\"content\",\"tagname\":\"美女\",\"images\":\"http://www.sd.xinhuanet.com/news/yule/2015-10/08/1116742491_14440348978261n.jpg,http://www.sd.xinhuanet.com/news/yule/2015-10/08/1116742491_14440348978261n.jpg\",\"refId\":1, \"refType\":2,”topicId”:1}
//                tile:标题
//                ico：预览图
//                content：内容
//                tagname：标签名称
//                images：多图以逗号分隔
//                refId:默认传0，如果是RefType为2的话，RefId为商品编号
//                refType:默认为0，商品为2
//                topicId:话题编号

		Log.e("kwan", "uploadImgSuccess");

		this.backImageUrl = backImageUrl.get(0);

		StringBuilder img = new StringBuilder();
		for (String url : backImageUrl)
			img.append(url + ",");

		String images = img.toString().substring(0, img.lastIndexOf(","));

		long topicId = 0;

		if (mTopicSubject != null)
			topicId = mTopicSubject.getTopicId();

		mPresenter.addTopic("发帖",
				backImageUrl.get(0),
				et_content.getText().toString(),
				tag,
				images,
				0,
				0,
				topicId
		);
	}

	@Override
	public void uploadImgFailed() {
		toastMsg("上传多图失败");
		// showLoadingDialog("");
	}

	@Override
	public void addTopicSuccess(String result) {
		dismissProgress();
		toastMsg("发帖成功");
		share(result);
	}


	@Override
	public void addTopicSuccess(VideoUploadResult result) {
		dismissProgress();
		toastMsg("发帖成功");
		share(result);
	}

	ShareAction action;

	//分享图片
	private void share(String id) {
		if (!isWithWM && !isWithWC && !isWithWB)
			onBackPressed();
		else {

			String txt = "我在 芭莎in 发现了一张不错的图片，分享给大家。";
			UMImage image = new UMImage(this, new File(imagePaths.get(0)));
			String url = "http://bz.metshow.com/api/share.html?articleid=" + id;
			action = new ShareAction(this);
			action.withText(txt)
					.withTargetUrl(url)
					.withMedia(image)
					.withTitle(txt)
					.setCallback(this);
			if (isWithWB) {
				action.setPlatform(SHARE_MEDIA.SINA);
			} else if (isWithWM) {
				action.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
			} else if (isWithWC) {
				action.setPlatform(SHARE_MEDIA.WEIXIN);
			}
			action.share();
		}
	}

	//分享视频
	private void share(VideoUploadResult video) {

		if (!isWithWM && !isWithWC && !isWithWB)
			onBackPressed();
		else {

			String txt = "我在 芭莎in 发现了一段不错的视频，分享给大家。";
			UMImage image = new UMImage(this, video.getPreviewpic());
			String url = "http://bz.metshow.com/api/share.html?articleid=" + video.getArticleId();

			action = new ShareAction(this);
			action.withText(txt)
                    .withTargetUrl(url)
					.withMedia(image)
					.withTitle(txt)
					.setCallback(this);

			if (isWithWB) {
				action.setPlatform(SHARE_MEDIA.SINA);
			} else if (isWithWM) {
				action.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
			} else if (isWithWC) {
				action.setPlatform(SHARE_MEDIA.WEIXIN);
			}
			action.share();

		}
	}

	@Override
	public void onResult(SHARE_MEDIA share_media) {
		doNextShare(share_media);
	}

	@Override
	public void onError(SHARE_MEDIA share_media, Throwable throwable) {
		doNextShare(share_media);
	}

	@Override
	public void onCancel(SHARE_MEDIA share_media) {
		doNextShare(share_media);
	}

	private void doNextShare(SHARE_MEDIA share_media) {

		if (share_media == SHARE_MEDIA.SINA) {
			action.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
			action.share();
		} else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
			action.setPlatform(SHARE_MEDIA.WEIXIN);
			action.share();
		} else {
			onBackPressed();
		}

	}
}
