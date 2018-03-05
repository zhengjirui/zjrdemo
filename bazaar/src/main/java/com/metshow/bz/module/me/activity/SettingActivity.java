package com.metshow.bz.module.me.activity;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kwan.base.activity.BaseCommonActivity;
import com.kwan.base.util.SPUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.module.commons.activity.BannerTopicActivity;
import com.metshow.bz.module.commons.activity.UserFeedBackActivity;
import com.metshow.bz.presenter.SettingPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

import io.LruCacheUtil;

public class SettingActivity extends BaseCommonActivity implements BZContract.ISettingView {

	private TextView tv_clear_cache, tv_pass, tv_logout, tv_cache_size, tv_user_feedback, tv_add_contact, tv_about;
	private File cacheDir;
	private User mUser;
	private SwitchButton sb_state;
	private AutoLinearLayout ll_get_state;
	private boolean isRefuseMessage;
	private SettingPresenter mPresenter;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_setting;
	}

	@Override
	protected void initViews() {
		super.initViews();

		mPresenter = new SettingPresenter(this);
		mUser = BZSPUtil.getUser();
		tv_clear_cache = (TextView) findViewById(R.id.tv_clear_cache);
		tv_cache_size = (TextView) findViewById(R.id.tv_cache_size);

		tv_user_feedback = (TextView) findViewById(R.id.tv_user_feedback);
		tv_about = (TextView) findViewById(R.id.tv_about);
		tv_pass = (TextView) findViewById(R.id.tv_pass);
		tv_logout = (TextView) findViewById(R.id.tv_logout);
		tv_add_contact = (TextView) findViewById(R.id.tv_add_contact);
		sb_state = (SwitchButton) findViewById(R.id.sb_state);
		ll_get_state = (AutoLinearLayout) findViewById(R.id.ll_get_state);
		// sb_state.setOnCheckedChangeListener(this);
		sb_state.setOnClickListener(this);
		tv_pass.setOnClickListener(this);
		tv_logout.setOnClickListener(this);
	}

	@Override
	protected void initViewSetting() {
		super.initViewSetting();

		tv_clear_cache.setOnClickListener(this);
		tv_cache_size.setOnClickListener(this);

		tv_user_feedback.setOnClickListener(this);

		tv_about.setOnClickListener(this);

		if (mUser == null) {

			ll_get_state.setVisibility(View.GONE);
			tv_add_contact.setVisibility(View.GONE);
			findViewById(R.id.line_add_contact).setVisibility(View.GONE);


		} else {

			if (mUser.getIsRefuseMessage() == 0) {
				sb_state.setChecked(true);
				isRefuseMessage = false;
			} else {
				isRefuseMessage = true;
				sb_state.setChecked(false);
			}
		}

		cacheDir = LruCacheUtil.getDiskCacheDir(this, "object");
		File imageCache = new File(App.getInstance().getCacheDir() + "/image_cache");
		tv_cache_size.setText(getFormatSize(getFolderSize(cacheDir) + getFolderSize(imageCache)));
	}

	@Override
	protected void initData() {

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v == tv_clear_cache) {
			showCacheDialog();
		} else if (v == tv_user_feedback) {
			go2Activity(UserFeedBackActivity.class, null, false, R.anim.push_left_in, R.anim.push_left_out);
		} else if (v == tv_about) {
			go2Activity(BannerTopicActivity.AboutActivity.class, null, false, R.anim.push_left_in, R.anim.push_left_out);
		} else if (v == sb_state) {
			showProgress("");
			if (isRefuseMessage) {
				mPresenter.setState(0);
			} else {
				mPresenter.setState(1);
			}
		} else if (v == tv_pass) {
			go2Activity(MotifPwdActivity.class, null, false, R.anim.push_left_in, R.anim.push_left_out);
		} else if (v == tv_logout) {

			if (mUser.getIsSlogin() == 1) {

				SHARE_MEDIA platform;

				if (mUser.getPlatform() == 1)
					platform = SHARE_MEDIA.SINA;
				else if (mUser.getPlatform() == 4)
					platform = SHARE_MEDIA.WEIXIN;
				else
					platform = SHARE_MEDIA.QQ;


				App.ShareAPI.deleteOauth(this, platform, new UMAuthListener() {
					@Override
					public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

						Log.d("kwan", "do deleteOauth");
						SPUtil.setIsLogin(false);
						BZSPUtil.setUser(new User());

						mPresenter.postRxBus(1);
					}

					@Override
					public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

					}

					@Override
					public void onCancel(SHARE_MEDIA share_media, int i) {

					}
				});
			} else {
				SPUtil.setIsLogin(false);
				BZSPUtil.setUser(new User());
				onBackPressed();
			}
		}
	}

	private void showCacheDialog() {

		final Dialog dialog = new Dialog(this, R.style.BaseDialog);
		View view = getLayoutInflater().inflate(R.layout.clear_cache_dialog_layout, null);

		TextView sure_btn = (TextView) view.findViewById(R.id.tv_ok);
		TextView cancel_btn = (TextView) view.findViewById(R.id.tv_cancel);

		int dialog_width = (int) (App.SCREEN_WIDTH * 9 / 10.0);
		dialog.setContentView(view, new LinearLayout.LayoutParams(dialog_width, -2));
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();

		sure_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (dialog.isShowing())
					dialog.dismiss();

				new Thread() {
					@Override
					public void run() {
						super.run();
						Glide.get(App.getInstance()).clearDiskCache();
						new LruCacheUtil<>().clear();
					}
				}.start();
				Glide.get(App.getInstance()).clearMemory();
				toastMsg("缓存已清除");
				tv_cache_size.setText("0.0KB");

			}
		});
		cancel_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (dialog.isShowing())
					dialog.dismiss();
			}
		});
	}

	@Override
	protected String getTitleTxt() {
		return "设置";
	}

	/**
	 * 获取文件夹大
	 *
	 * @param file File实例
	 * @return long
	 */
	public static long getFolderSize(java.io.File file) {

		long size = 0;
		try {

			if (file != null) {
				java.io.File[] fileList = file.listFiles();

				if (fileList != null)

					for (File f : fileList) {
						if (f.isDirectory())
							size = size + getFolderSize(f);
						else
							size = size + f.length();
					}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		//return size/1048576;
		return size;
	}

	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "KB";
		}
		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}

	@Override
	public void onServerFailed(String s) {
		super.onServerFailed(s);
		toastMsg(s);

	}

	@Override
	public void setStateSuccess(String result) {

		isRefuseMessage = !isRefuseMessage;
		if (isRefuseMessage)
			mUser.setIsRefuseMessage(1);
		else
			mUser.setIsRefuseMessage(0);
		BZSPUtil.setUser(mUser);
		dismissProgress();
	}
}
