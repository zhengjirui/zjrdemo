package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2016-7-18.
 */


public class DoLoginPresenter extends BzPresenter {

	private BZContract.IDoLoginView mIDoLoginView;


	public DoLoginPresenter(BZContract.IDoLoginView iDoLoginView) {
		super(iDoLoginView);
		mIDoLoginView = iDoLoginView;


	}

	public void login(String phone, String pwd) {
		mModel.doLogin(phone, pwd);
	}

	public void sLogin(String platform, String openId, String token, String avatar, String appId, String nickName) {

		mModel.sLogin(platform, openId, token, avatar, appId, nickName);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		if (vocational_id == ServerAPI.USER_LOGIN_VOCATIONAL_ID)
			mIDoLoginView.loginSuccess((User) s.getResult());

		else if (vocational_id == ServerAPI.USER_SOCIAL_LOGIN_VOCATIONAL_ID)
			mIDoLoginView.sloginSuccess((User) s.getResult());
	}


}
