package com.metshow.bz.presenter;

import android.util.Log;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.User;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2016-9-2.
 */
public class RegOrForgotPresenter extends BzPresenter {


    private BZContract.IRegOrForgotView mIRegOrForgotView;

    public RegOrForgotPresenter(BZContract.IRegOrForgotView  iRegOrForgotView) {
        super(iRegOrForgotView);
        mIRegOrForgotView = iRegOrForgotView;

    }

    public void getPhoneCode(String validatecode ,String phonenum){
        mModel.getPhoneCode(validatecode,phonenum);
    }

	public void getValidatecode(String phonenum){
		mModel.getValidatecode(phonenum);
	}

    public void doReg(String phonenum,String password){
		mModel.doReg(phonenum,password);
    }

    public void doFind(String phonenum, String password) {
		mModel.doFind(phonenum,password);
    }


	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);
		switch (vocational_id) {

			case ServerAPI.USER_PHONECODE_VOCATIONAL_ID:
				mIRegOrForgotView.getPhoneCodeSuccess((String) s.getResult());
				break;
			case ServerAPI.USER_REG_VOCATIONAL_ID:
				if (s.getCode() == 1000)
					onServerFailed(s.getMessage());
				else
					mIRegOrForgotView.regSuccess((User) s.getResult());
				break;

			case ServerAPI.USER_FINDPASS_VOCATIONAL_ID:

				if (s.getCode() == 1000)
					onServerFailed(s.getMessage());
				else
					mIRegOrForgotView.findSuccess((User) s.getResult());
				break;

		}
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, byte[] s) {
		super.onServerSuccess(vocational_id, exData, s);
		switch (vocational_id) {
			case ServerAPI.USER_validatecode_VOCATIONAL_ID:

				Log.e("kwan","sss::"+s.length);

				mIRegOrForgotView.getValidatecodeSuccess(s);
				break;
		}


	}
}
