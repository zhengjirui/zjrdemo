package com.metshow.bz.util;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.metshow.bz.IUmengEvent;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.social.UMPlatformData;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhy.autolayout.AutoLinearLayout;


/**
 * ShareSDK分享工具类
 * Created by Mr.Kwan on 2016-1-23.
 */
public class ShareUtil implements View.OnClickListener {

	private Activity mActivity;
	private View parent;
	private AutoLinearLayout ll_sinaweibo, ll_wechat, ll_wechatmoments, ll_qq;
	private TextView tv_cancel;
	private String mText = null;
	private String str_url = null;
	private String str_title = null;
	private String str_img = null;
//    private String str_lat = "0";
//    private String str_lon = "0";

	private Dialog mDialog;

	private String Share_Type = IUmengEvent.AppShare;


//
//	//1.晒--活动详情
//	public static String sai_activity= "http://bz.metshow.com/api/2017/activity_details.html?id=";
//
////2.晒--圈子
//public static String sai_topic ="http://bz.metshow.com/api/2017/sticker_details.html?id=";


//	//	3.卖--推荐--专辑
//	public static String buy_topic ="http://bz.metshow.com/api/2017/sticker_details.html?id={id
//
//		}
//		4.卖--产品详情
//		http://bz.metshow.com/api/2017/ware_details.html?id={id
//
//		}
//
//		5.晒-用户推荐--用户首页
//		http://bz.metshow.com/api/2017/usersite.html?id={id
//
//		}
//		6.晒--帖子详情
//		http://bz.metshow.com/api/2017/sticker_details.html?id={id
//
//		}




public ShareUtil(Activity activity) {

		mActivity = activity;

		parent = mActivity.getLayoutInflater().inflate(R.layout.dialog_share, null);

		ll_sinaweibo = (AutoLinearLayout) parent.findViewById(R.id.ll_sinaweibo);
		ll_wechat = (AutoLinearLayout) parent.findViewById(R.id.ll_wechat);
		ll_wechatmoments = (AutoLinearLayout) parent.findViewById(R.id.ll_wechatmoments);
		ll_qq = (AutoLinearLayout) parent.findViewById(R.id.ll_qq);
		tv_cancel = (TextView) parent.findViewById(R.id.tv_cancel);

		ll_sinaweibo.setOnClickListener(this);
		ll_wechat.setOnClickListener(this);
		ll_wechatmoments.setOnClickListener(this);
		ll_qq.setOnClickListener(this);

		tv_cancel.setOnClickListener(this);
	}

	public Dialog showShareDialog() {
		mDialog = DialogFactory.showMenuDialog(mActivity, parent);
		return mDialog;
	}


	@Override
	public void onClick(View v) {

		checkPermition();

			if (v == ll_sinaweibo) {
				shareTo(SHARE_MEDIA.SINA);
			} else if (v == ll_qq) {
				shareTo(SHARE_MEDIA.QQ);
			} else if (v == ll_wechat) {
				shareTo(SHARE_MEDIA.WEIXIN);
			} else if (v == ll_wechatmoments) {
				shareTo(SHARE_MEDIA.WEIXIN_CIRCLE);
			}


		mDialog.dismiss();
	}

	private void checkPermition() {

		if (Build.VERSION.SDK_INT >= 23) {

			String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};

			for (String str : mPermissionList) {

				if (ContextCompat.checkSelfPermission(mActivity, str)
						!= PackageManager.PERMISSION_GRANTED) {
					//申请WRITE_EXTERNAL_STORAGE权限
					ActivityCompat.requestPermissions(mActivity, new String[]{str},
							123);
					//Log.d("kwan",)
					//return false;
				}
			}

		}

		//return true;

	}

	public void shareTo(SHARE_MEDIA plant) {
		UMImage image = null;
		if (str_img != null && !str_img.trim().isEmpty())
			image = new UMImage(mActivity, str_img);//网络图片

		ShareAction action = new ShareAction(mActivity).setPlatform(plant);

		action.withText(mText)
				.withTitle(str_title)
				.setCallback(umShareListener);
		if (image != null)
			action.withMedia(image);

		if (str_url != null)
			action.withTargetUrl(str_url);

		action.share();

	}

	private UMShareListener umShareListener = new UMShareListener() {
		@Override
		public void onResult(SHARE_MEDIA platform) {
			Log.d("plat", "platform" + platform);

			Toast.makeText(mActivity, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
			UMPlatformData plat;
			String arg;

			if (platform == SHARE_MEDIA.SINA) {
				plat = new UMPlatformData(UMPlatformData.UMedia.SINA_WEIBO, String.valueOf(BZSPUtil.getUser().getUserId()));
				arg = "SINA_WEIBO";
			} else if (platform == SHARE_MEDIA.QQ) {
				plat = new UMPlatformData(UMPlatformData.UMedia.TENCENT_QQ, String.valueOf(BZSPUtil.getUser().getUserId()));
				arg = "TENCENT_QQ";
			} else if (platform == SHARE_MEDIA.WEIXIN) {
				plat = new UMPlatformData(UMPlatformData.UMedia.WEIXIN_FRIENDS, String.valueOf(BZSPUtil.getUser().getUserId()));
				arg = "WEIXIN_FRIENDS";
			} else {
				plat = new UMPlatformData(UMPlatformData.UMedia.WEIXIN_CIRCLE, String.valueOf(BZSPUtil.getUser().getUserId()));
				arg = "WEIXIN_CIRCLE";
			}
			MobclickAgent.onSocialEvent(mActivity, plat);

			App.umengEvent(mActivity, Share_Type, arg);

		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			Toast.makeText(mActivity, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
			if (t != null) {
				Log.d("throw", "throw:" + t.getMessage());
			}
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			Toast.makeText(mActivity, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
		}
	};

	public void setmText(String mText) {
		this.mText = mText;
	}

	public void setStr_url(String str_url) {
		this.str_url = str_url;
	}

	public void setStr_title(String str_title) {
		this.str_title = str_title;
	}

	public void setStr_img(String str_img) {
		this.str_img = str_img;
	}

	public void setShare_Type(String share_Type) {
		Share_Type = share_Type;
	}

}
