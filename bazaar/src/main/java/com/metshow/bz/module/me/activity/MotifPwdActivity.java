package com.metshow.bz.module.me.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kwan.base.activity.BaseCommonActivity;
import com.kwan.base.presenter.BasePresenter;
import com.metshow.bz.R;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.presenter.UserInfoPresenter;
import com.metshow.bz.util.BZSPUtil;


public class MotifPwdActivity extends BaseCommonActivity implements BZContract.IUserInfoView {

    private UserInfoPresenter mPresenter;
    private EditText et_current_pwd, et_new_pwd, et_renew_pwd;
    private TextView tv_save;
    private String strCurrent, strNew, strRenew;

    @Override
    protected void beForeSetContentView() {
		super.beForeSetContentView();
        mPresenter = new UserInfoPresenter(this);
    }

    @Override
    protected void initViews() {
        super.initViews();

        et_current_pwd = (EditText) findViewById(R.id.et_current_pwd);
        et_new_pwd = (EditText) findViewById(R.id.et_new_pwd);
        et_renew_pwd = (EditText) findViewById(R.id.et_renew_pwd);

        tv_save = (TextView) findViewById(R.id.tv_save);

    }

    @Override
    protected void initViewSetting() {
        super.initViewSetting();
        tv_save.setOnClickListener(this);
    }

	@Override
	protected void initData() {

	}

	@Override
    protected int getContentViewId() {
        return R.layout.activity_motif_pwd;
    }

    @Override
    protected BasePresenter getPresent() {
        return mPresenter;
    }

    @Override
    protected String getTitleTxt() {
        return "修改密码";
    }

    @Override
    public void upDataUserSuccess() {
        User user = BZSPUtil.getUser();
        user.setPassword(strCurrent);
        BZSPUtil.setUser(user);
        toastMsg("修改成功");
        dismissProgress();
        onBackPressed();
    }

    @Override
    public void upLoadAvatarSuccess(String url) {

    }

    @Override
    public void upLoadAvatarFailed() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == tv_save && checkPwd()) {
            mPresenter.updataUser(null, 0, null, null, null, null, strNew, null, strCurrent);
            showProgress("正在提交..");
        }
    }

    private boolean checkPwd() {

        strCurrent = et_current_pwd.getText().toString();
        strNew = et_new_pwd.getText().toString();
        strRenew = et_renew_pwd.getText().toString();

        if (strCurrent == null || strCurrent.isEmpty() ||
                strNew == null || strNew.isEmpty() ||
                strRenew == null || strRenew.isEmpty()) {

            toastMsg("请填写完整");
            return false;
        } else if (!strNew.equals(strRenew)) {
            toastMsg("两次密码不一致");
            return false;
        }

        return true;

    }
}
