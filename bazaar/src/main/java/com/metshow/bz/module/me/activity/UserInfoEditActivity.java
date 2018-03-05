package com.metshow.bz.module.me.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kwan.base.activity.BaseCommonActivity;
import com.kwan.base.widget.CircularImage;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.UserInfoPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.yongchun.library.view.ImageSelectorActivity;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

public class UserInfoEditActivity extends BaseCommonActivity implements BZContract.IUserInfoView {

	private CircularImage iv_avatar;
	private User mUser;
	private TextView tv_real_name,tv_mobilephone,tv_address,tv_name, tv_birth, tv_area, tv_description, tv_phone,tv_base,tv_shouhuo;
    private AutoLinearLayout ll_base,ll_shouhuo;
	private UserInfoPresenter mPresenter;
	private final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
	private String selectImgPath;

	@Override
	protected String getTitleTxt() {
		return "编辑资料";
	}

	@Override
	protected void beForeSetContentView() {
		super.beForeSetContentView();
		mUser = BZSPUtil.getUser();
		mPresenter = new UserInfoPresenter(this);
	}

	@Override
	protected Bitmap getBackGroundBitmap() {
		return null;
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_user_info_edit;
	}

	@Override
	protected void initViews() {
		super.initViews();

		iv_avatar = (CircularImage) findViewById(R.id.iv_avatar);
		mImageUtil.loadFromUrl(mUser.getAvatar(), iv_avatar);


		ll_base = (AutoLinearLayout) findViewById(R.id.ll_base);
		ll_shouhuo= (AutoLinearLayout) findViewById(R.id.ll_shouhuo);

		tv_base= (TextView) findViewById(R.id.tv_base);
		tv_shouhuo= (TextView) findViewById(R.id.tv_shouhuo);

		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_birth = (TextView) findViewById(R.id.tv_birth);
		tv_area = (TextView) findViewById(R.id.tv_area);
		tv_description = (TextView) findViewById(R.id.tv_description);
		tv_phone = (TextView) findViewById(R.id.tv_phone);

		tv_real_name = (TextView) findViewById(R.id.tv_real_name);
		tv_mobilephone = (TextView) findViewById(R.id.tv_mobilephone);
		tv_address = (TextView) findViewById(R.id.tv_address);

		tv_name.setText(mUser.getNickName());
		tv_birth.setText(mUser.getBirthday());
		tv_area.setText(mUser.getAddress());
		tv_description.setText(mUser.getDescription());
		tv_phone.setText(mUser.getPhoneNum());

		tv_real_name.setText(mUser.getTrueName());
		tv_mobilephone.setText(mUser.getPhoneNum());
		tv_address.setText(mUser.getAddress());

		tv_shouhuo.setOnClickListener(this);
		tv_base.setOnClickListener(this);

		tv_real_name.setOnClickListener(this);
		tv_mobilephone.setOnClickListener(this);
		tv_address.setOnClickListener(this);

	}

	@Override
	protected void initViewSetting() {
		super.initViewSetting();
		iv_avatar.setOnClickListener(this);

		tv_name.setOnClickListener(this);
		tv_birth.setOnClickListener(this);
		tv_area.setOnClickListener(this);
		tv_description.setOnClickListener(this);
		tv_phone.setOnClickListener(this);


	}

	@Override
	protected void initData() {

	}

	public static final int NICKENAME_REQUEST_CODE = 2;
	public static final int BIRTH_REQUEST_CODE = 3;
	public static final int AREA_REQUEST_CODE = 4;
	public static final int DESCRIBE_REQUEST_CODE = 5;

	public static final int ADDRESS_REQUEST_CODE = 6;
	public static final int REALNAME_REQUEST_CODE = 7;
	public static final int PHONE_REQUEST_CODE = 8;


	@Override
	public void onClick(View v) {
		super.onClick(v);
		Bundle bundle = new Bundle();
		if (v == iv_avatar)
			ImagePickActivity.start(this, 1, ImagePickActivity.MODE_MULTIPLE, true, false, false);
		else if (v == tv_name) {

			bundle.putInt("type",NICKENAME_REQUEST_CODE);
			go2ActivityForResult(UserModifyActivity.class, NICKENAME_REQUEST_CODE, bundle, R.anim.push_left_in, R.anim.push_left_out);

		} else if (v == tv_birth) {
			bundle.putInt("type",BIRTH_REQUEST_CODE);
			go2ActivityForResult(UserModifyActivity.class, BIRTH_REQUEST_CODE, bundle, R.anim.push_left_in, R.anim.push_left_out);

		} else if (v == tv_area) {
			bundle.putInt("type",AREA_REQUEST_CODE);
			go2ActivityForResult(UserModifyActivity.class, AREA_REQUEST_CODE, bundle, R.anim.push_left_in, R.anim.push_left_out);
		} else if (v == tv_phone) {

		} else if (v == tv_description) {
			bundle.putInt("type",DESCRIBE_REQUEST_CODE);
			go2ActivityForResult(UserModifyActivity.class, DESCRIBE_REQUEST_CODE, bundle, R.anim.push_left_in, R.anim.push_left_out);
		}else if(v==tv_mobilephone){
			bundle.putInt("type",PHONE_REQUEST_CODE);
			go2ActivityForResult(UserModifyActivity.class, PHONE_REQUEST_CODE, bundle, R.anim.push_left_in, R.anim.push_left_out);
		}else if(v==tv_real_name){
			bundle.putInt("type",REALNAME_REQUEST_CODE);
			go2ActivityForResult(UserModifyActivity.class, REALNAME_REQUEST_CODE, bundle, R.anim.push_left_in, R.anim.push_left_out);
		}else if(v==tv_address){
			bundle.putInt("type",ADDRESS_REQUEST_CODE);
			go2ActivityForResult(UserModifyActivity.class, ADDRESS_REQUEST_CODE, bundle, R.anim.push_left_in, R.anim.push_left_out);
		}else if(v==tv_base){
			tv_base.setTextColor(getResources().getColor(R.color.txt_cobalt_blue));
			tv_shouhuo.setTextColor(getResources().getColor(R.color.gray));
			ll_base.setVisibility(View.VISIBLE);
			ll_shouhuo.setVisibility(View.GONE);

		}else if(v==tv_shouhuo){
			tv_shouhuo.setTextColor(getResources().getColor(R.color.txt_cobalt_blue));
			tv_base.setTextColor(getResources().getColor(R.color.gray));
			ll_base.setVisibility(View.GONE);
			ll_shouhuo.setVisibility(View.VISIBLE);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
			ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
			showProgress("正在上传..");
			selectImgPath = images.get(0);
			mPresenter.uploadAvatar(selectImgPath);

		} else if (resultCode == RESULT_OK && data != null) {

			String content = data.getStringExtra("content");

			switch (requestCode) {
				case NICKENAME_REQUEST_CODE:
					mUser.setNickName(content);
					BZSPUtil.setUser(mUser);
					tv_name.setText(content);
					break ;
				case BIRTH_REQUEST_CODE:
					mUser.setBirthday(content);
					BZSPUtil.setUser(mUser);
					tv_birth.setText(content);
					break;
				case AREA_REQUEST_CODE:
					mUser.setAddress(content);
					BZSPUtil.setUser(mUser);
					tv_area.setText(content);
					break;
				case DESCRIBE_REQUEST_CODE:
					mUser.setDescription(content);
					BZSPUtil.setUser(mUser);
					tv_description.setText(content);
					break;

				case ADDRESS_REQUEST_CODE:
					mUser.setAddress(content);
					BZSPUtil.setUser(mUser);
					tv_address.setText(content);
					break;
				case PHONE_REQUEST_CODE:
					mUser.setPhoneNum(content);
					BZSPUtil.setUser(mUser);
					tv_mobilephone.setText(content);
					break;

				case REALNAME_REQUEST_CODE:
					mUser.setTrueName(content);
					BZSPUtil.setUser(mUser);
					tv_real_name.setText(content);
					break;
			}
		}

	}

	@Override
	public void upDataUserSuccess() {

	}

	@Override
	public void upLoadAvatarSuccess(String url) {
		mImageUtil.loadFromFileWithCircle(selectImgPath, iv_avatar);
		mUser.setAvatar(url);
		BZSPUtil.setUser(mUser);
		toastMsg("更新成功");
		dismissProgress();
	}

	@Override
	public void upLoadAvatarFailed() {
		toastMsg("更新失败");
		dismissProgress();
	}
}
