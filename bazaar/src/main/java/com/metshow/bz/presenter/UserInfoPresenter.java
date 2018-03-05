package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ImgUploadResult;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2016-7-29.
 */
public class UserInfoPresenter extends BzPresenter {

    private BZContract.IUserInfoView mIUserInfoView;


    public UserInfoPresenter(BZContract.IUserInfoView iUserInfoView) {
        super(iUserInfoView);
        mIUserInfoView = iUserInfoView;
    }

    public void updataUser(String PhoneNum, int Sex, String NickName, String TrueName,
                           String Description, String Address, String Password,
                           String Birthday, String OldPassword) {
        mModel.updataUser(PhoneNum, Sex, NickName, TrueName,
                Description, Address, Password, Birthday, OldPassword);
    }

    public void uploadAvatar(String path) {
		mModel.uploadAvatar(path);
    }


	@Override
	public void onServerUploadNext(int vocational_id, HashMap exdata, Object s) {
		super.onServerUploadNext(vocational_id, exdata, s);

		switch (vocational_id) {

			case ServerAPI.PIC_UPLOADAVATAR_VOCATIONAL_ID:
				//{error:0,url:imageurl}
				int error = ((ImgUploadResult)s).getError();
				if (error == 0)
					mIUserInfoView.upLoadAvatarSuccess(((ImgUploadResult)s).getUrl());
				else
					mIUserInfoView.upLoadAvatarFailed();
				break;
		}
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);
		switch (vocational_id) {
			case ServerAPI.USER_UPDATE_VOCATIONAL_ID:
				mIUserInfoView.upDataUserSuccess();
				break;
		}
	}
}
