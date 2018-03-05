package com.metshow.bz.module.login;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kwan.base.activity.BaseActivity;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.R;
import com.metshow.bz.commons.App;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.RegOrForgotPresenter;
import com.metshow.bz.presenter.UserInfoPresenter;
import com.metshow.bz.util.BZSPUtil;
import com.metshow.bz.util.DialogFactory;
import com.zhy.autolayout.AutoLinearLayout;

public class RegFindActivity extends BaseActivity implements BZContract.IRegOrForgotView, BZContract.IUserInfoView {

    private RegOrForgotPresenter mPresenter;
    private TextView tv_tips, tv_resend, tv_reg;
    private EditText et_code, et_pwd;
    private ImageView iv_title_back;
    private String phone;
    private String phonecode;

    private AutoLinearLayout ll_pwd;

    private TimeCount time;

    private int type;


    @Override
    protected void beForeSetContentView() {
        mPresenter = new RegOrForgotPresenter(this);

        type = (int) getIntentData("type");
        phone = (String) getIntentData("phone");
        phonecode = (String) getIntentData("phonecode");
        time = new TimeCount(60000, 1000);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_reg;
    }

    @Override
    protected void initViews() {

        tv_tips = (TextView) findViewById(R.id.tv_tips);
        tv_resend = (TextView) findViewById(R.id.tv_resend);

        tv_reg = (TextView) findViewById(R.id.tv_reg);

        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);

        et_code = (EditText) findViewById(R.id.et_code);
        et_pwd = (EditText) findViewById(R.id.et_pwd);

        ll_pwd = (AutoLinearLayout) findViewById(R.id.ll_pwd);
    }

    @Override
    protected void initViewSetting() {

        tv_resend.setOnClickListener(this);
        tv_reg.setOnClickListener(this);
        iv_title_back.setOnClickListener(this);

        tv_tips.setText("验证码已发送至" + phone);

        if (type == RegOrForgotActivity.TYPE_REG)
            tv_reg.setText("注册");
        else if (type == RegOrForgotActivity.TYPE_FORGET)
            tv_reg.setText("保存");
        else if (type == RegOrForgotActivity.TYPE_REG_SOCIAL) {
            tv_reg.setText("绑定");
            ll_pwd.setVisibility(View.GONE);
            findViewById(R.id.line_pwd).setVisibility(View.GONE);
        }

    }

    @Override
    protected void initData() {

    }


    @Override
    protected Bitmap getBackGroundBitmap() {
        return mImageUtil.compressBitmapFromResPathBySize(getResources(), R.mipmap.login_bg, App.SCREEN_WIDTH, App.STATUS_BAR_HEIGHT);
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
    protected BasePresenter getPresent() {
        return null;
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v == tv_resend) {

            showProgress("");
			mPresenter.getValidatecode(phone);
            time.start();// 开始计时
            tv_resend.setClickable(false);

        } else if (v == tv_reg) {

            doRegOrFind();

        } else if (v == iv_title_back)
            onBackPressed();


    }

    private void doRegOrFind() {

        String pwd = et_pwd.getText().toString();

        if (!et_code.getText().toString().equals(phonecode)) {
            toastMsg("验证码不正确");
            return;
        }
        if (pwd.isEmpty()&&(type == RegOrForgotActivity.TYPE_REG||type == RegOrForgotActivity.TYPE_FORGET)) {
            toastMsg("密码不能为空");
            return;
        }

        showProgress("");

        if (type == RegOrForgotActivity.TYPE_REG)
            mPresenter.doReg(phone, pwd);
        else if (type == RegOrForgotActivity.TYPE_FORGET)
            mPresenter.doFind(phone, pwd);
        else if (type == RegOrForgotActivity.TYPE_REG_SOCIAL) {

            UserInfoPresenter infoPresenter = new UserInfoPresenter(this);
            infoPresenter.updataUser(phone, 0, null, null, null, null, null, null, null);

        }


    }

    @Override
    public void getPhoneCodeSuccess(String result) {

        phonecode = result;
        toastMsg("已发送");
        dismissProgress();
    }

    @Override
    public void regSuccess(User result) {
        toastMsg("注册成功");
        onBackPressed();
    }

    @Override
    public void findSuccess(User result) {
        toastMsg("找回成功");
        onBackPressed();
    }

	@Override
	public void getValidatecodeSuccess(byte[] result) {


		final View view  = getLayoutInflater().inflate(R.layout.layout_phone_code,null);
		Glide.with(this).load(result).into((ImageView) view.findViewById(R.id.iv_code));

		final Dialog dialog = DialogFactory.showCustomDialog(this,view);

		view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view1) {
                mPresenter.getPhoneCode(((EditText)view.findViewById(R.id.et_code)).getText().toString(),phone);
				dialog.cancel();
			}
		});

		view.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.cancel();
			}
		});








	}

	@Override
    public void upDataUserSuccess() {


        User user = BZSPUtil.getUser();
        user.setPhoneNum(phone);
		BZSPUtil.setUser(user);

        toastMsg("绑定成功");
        onBackPressed();
    }

    @Override
    public void upLoadAvatarSuccess(String url) {

    }

    @Override
    public void upLoadAvatarFailed() {

    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_resend.setText(millisUntilFinished / 1000 + "秒后重发");
        }

        @Override
        public void onFinish() {
            tv_resend.setText("重发验证码");
            tv_resend.setClickable(true);

        }
    }
}
