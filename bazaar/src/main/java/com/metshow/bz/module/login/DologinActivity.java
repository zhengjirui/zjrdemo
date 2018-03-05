package com.metshow.bz.module.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kwan.base.activity.BaseActivity;
import com.kwan.base.presenter.BasePresenter;
import com.kwan.base.util.SPUtil;
import com.metshow.bz.IUmengEvent;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.DoLoginPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class DologinActivity extends BaseActivity implements BZContract.IDoLoginView {

    private TextView tv_login, tv_social_login, tv_forgot, tv_reg;
    private ImageView iv_title_close, iv_wb, iv_wx, iv_qq;
    private EditText et_user_name, et_pwd;
    private DoLoginPresenter mDolDoLoginPresenter;
    private UMShareAPI mShareAPI;

    @Override
    protected void beForeSetContentView() {
        mDolDoLoginPresenter = new DoLoginPresenter(this);
    }

    @Override
    protected int getTitleBarViewId() {
        return 0;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_dologin;
    }

    @Override
    protected int getBottomViewId() {
        return 0;
    }

    @Override
    protected void initViews() {

        iv_title_close = (ImageView) findViewById(R.id.iv_title_close);
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_social_login = (TextView) findViewById(R.id.tv_social_login);
        tv_forgot = (TextView) findViewById(R.id.tv_forgot);
        tv_reg = (TextView) findViewById(R.id.tv_reg);

        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);

        iv_wb = (ImageView) findViewById(R.id.iv_wb);
        iv_wx = (ImageView) findViewById(R.id.iv_wx);
        iv_qq = (ImageView) findViewById(R.id.iv_qq);

    }

    @Override
    protected void initViewSetting() {


        tv_login.setOnClickListener(this);
        tv_social_login.setOnClickListener(this);
        tv_forgot.setOnClickListener(this);
        tv_reg.setOnClickListener(this);
        iv_title_close.setOnClickListener(this);

        iv_wb.setOnClickListener(this);
        iv_wx.setOnClickListener(this);
        iv_qq.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= 23) {
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
					!= PackageManager.PERMISSION_GRANTED) {
				//申请WRITE_EXTERNAL_STORAGE权限
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
			}
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
					!= PackageManager.PERMISSION_GRANTED) {
				//申请WRITE_EXTERNAL_STORAGE权限
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 123);
			}
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS)
					!= PackageManager.PERMISSION_GRANTED) {
				//申请WRITE_EXTERNAL_STORAGE权限
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, 123);
			}
		}

        mShareAPI = App.ShareAPI;

    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter getPresent() {
        return null;
    }

    @Override
    protected Bitmap getBackGroundBitmap() {
		return null;
       // return ImageUtil.compressBitmapFromResPathBySize(getResources(), R.mipmap.login_bg, App.SCREEN_WIDTH, App.STATUS_BAR_HEIGHT);
    }


	@Override
	protected int getBackGroundColor() {
		return getResources().getColor(R.color.redWin);
	}

	@Override
    public void onClick(View v) {
        super.onClick(v);

        if (v == tv_forgot) {

            Bundle bundle = new Bundle();
            bundle.putInt("type", RegOrForgotActivity.TYPE_FORGET);
            go2Activity(RegOrForgotActivity.class, bundle, true, R.anim.push_left_in, R.anim.push_left_out);

        } else if (v == tv_reg) {

            Bundle bundle = new Bundle();
            bundle.putInt("type", RegOrForgotActivity.TYPE_REG);
            go2Activity(RegOrForgotActivity.class, bundle, true, R.anim.push_left_in, R.anim.push_left_out);

        } else if (v == tv_login) {
            showProgress("正在登录..");
            mDolDoLoginPresenter.login(et_user_name.getText().toString(), et_pwd.getText().toString());
        } else if (v == iv_title_close)
            onBackPressed();

        else {

            if (v == iv_wx)
                mShareAPI.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);
            else if (v == iv_qq)
                mShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);
            else if (v == iv_wb)
                mShareAPI.getPlatformInfo(this, SHARE_MEDIA.SINA, umAuthListener);
        }
    }


    private int currentPlatform;

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Log.d("kwan", data.toString());

            String Platform = "";
            String OpenId = "";
            String Token = "";
            String Avatar = "";
            String AppId = "";
            String NickName = "";

           /* sinaweibo 1
              tencentWeibo 2
              QZone 3
              wechat 4
              wechatmoments 5
              wechatFavorite 6
              QQ 7*/

            if (platform == SHARE_MEDIA.WEIXIN) {
                Platform = "4";

                OpenId = data.get("openid");
                Token = data.get("access_token");
                Avatar = data.get("profile_image_url");
                AppId = App.APP_ID_WX;
                NickName = data.get("screen_name");

            } else if (platform == SHARE_MEDIA.QQ) {
                Platform = "7";

                OpenId = data.get("openid");
                Token = data.get("access_token");
                Avatar = data.get("profile_image_url");
                AppId = App.APP_ID_QQ;
                NickName = data.get("screen_name");
            } else if (platform == SHARE_MEDIA.SINA) {
                Platform = "1";

                OpenId = data.get("uid");
                Token = data.get("access_token");
                Avatar = data.get("profile_image_url");
                AppId = App.APP_ID_WB;
                NickName = data.get("screen_name");
            }

            currentPlatform = Integer.valueOf(Platform);
            mDolDoLoginPresenter.sLogin(Platform, OpenId, Token, Avatar, AppId, NickName);
            showProgress("登录中...");


        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Log.d("kwan", "Authorize fail");
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Log.d("kwan", "Authorize cancel");
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void loginSuccess(User user) {

        BZSPUtil.setUser(user);
        SPUtil.setIsLogin(true);
        toastMsg("登陆成功");
        dismissProgress();
        setResult(RESULT_OK);
        onBackPressed();

    }

    @Override
    public void sloginSuccess(User result) {

        //  cancelLoadingDialog();
        String phone = result.getPhoneNum();
        result.setPlatform(currentPlatform);
        result.setIsSlogin(1);

		BZSPUtil.setUser(result);
        SPUtil.setIsLogin(true);

        Log.d("kwan","SPUtil.getUser().getIsSlogin()::"+BZSPUtil.getUser().getIsSlogin());

        switch (currentPlatform){

            case 4://WEIXIN
                App.umengEvent(this, IUmengEvent.PlatformLogin,"WEIXIN");

                break;

            case 7://QQ
                App.umengEvent(this, IUmengEvent.PlatformLogin,"QQ");
                break;

            case 1://wB
                App.umengEvent(this, IUmengEvent.PlatformLogin,"WB");
                break;

        }


        //第一次登陆 还没有 电话号码
        if (phone == null || phone.isEmpty() || phone.equals("null")) {

            Bundle bundle = new Bundle();
            bundle.putInt("type", RegOrForgotActivity.TYPE_REG_SOCIAL);
            go2Activity(RegOrForgotActivity.class, bundle, true, R.anim.push_left_in, R.anim.push_left_out);

        } else {

            toastMsg("登陆成功");
            dismissProgress();
            setResult(RESULT_OK);
            onBackPressed();
        }

        Log.d("kwan", result.getPhoneNum());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

}
