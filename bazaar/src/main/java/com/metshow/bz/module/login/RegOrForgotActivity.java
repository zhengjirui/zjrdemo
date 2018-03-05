package com.metshow.bz.module.login;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.metshow.bz.util.DialogFactory;
import com.zhy.autolayout.AutoLinearLayout;

public class RegOrForgotActivity extends BaseActivity implements BZContract.IRegOrForgotView {

    private RegOrForgotPresenter mPresenter;
    private int type;
    private TextView tv_get, tv_86;
    private ImageView iv_title_back;
    private View v_divide;
    private EditText et_user_name;
    private AutoLinearLayout ll_account;
    private String phonecode;
    private String phone;


    public static final int TYPE_REG = 1;
    public static final int TYPE_FORGET = 2;
    public static final int TYPE_REG_SOCIAL = 3;

    @Override
    protected void beForeSetContentView() {
        mPresenter = new RegOrForgotPresenter(this);
        type = (Integer) getIntentData("type");
    }

    @Override
    protected int getTitleBarViewId() {
        return 0;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_reg_or_forgot;
    }

    @Override
    protected int getBottomViewId() {
        return 0;
    }

    @Override
    protected void initViews() {

        tv_get = (TextView) findViewById(R.id.tv_get);
        tv_86 = (TextView) findViewById(R.id.tv_86);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        v_divide = findViewById(R.id.v_divide);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        ll_account = (AutoLinearLayout) findViewById(R.id.ll_account);


    }

    @Override
    protected void initViewSetting() {

        if (type == TYPE_REG || type == TYPE_REG_SOCIAL)
            ll_account.setVisibility(View.GONE);
        else
            ll_account.setVisibility(View.VISIBLE);

        et_user_name.setHint("请输入您的手机号码");
        tv_get.setOnClickListener(this);
        iv_title_back.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }


    @Override
    protected BasePresenter getPresent() {
        return mPresenter;
    }

    @Override
    protected Bitmap getBackGroundBitmap() {
        return mImageUtil.compressBitmapFromResPathBySize(getResources(), R.mipmap.login_bg, App.SCREEN_WIDTH, App.STATUS_BAR_HEIGHT);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v == iv_title_back)
            onBackPressed();
        else if (v == tv_get) {
            phone = et_user_name.getText().toString();
            if (phone.isEmpty())
                toastMsg("请输入手机号");
            else if (phone.length() != 11)
                toastMsg("手机号错误");
            else {
                showProgress("正在获取");
				mPresenter.getValidatecode(phone);
            }
        }
    }

    @Override
    public void getPhoneCodeSuccess(String result) {

        phonecode = result;
        dismissProgress();

        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("phone", phone);
        bundle.putString("phonecode", phonecode);

        go2ActivityWithLeft(RegFindActivity.class, bundle, true);

    }

    @Override
    public void regSuccess(User result) {

    }

    @Override
    public void findSuccess(User result) {

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

//	@Override
//	public void getValidatecodeSuccess(String result) {
//
//		Log.e("kwan","mmmm:"+result);
//		mPresenter.getPhoneCode(result,phone);
//	}
}
