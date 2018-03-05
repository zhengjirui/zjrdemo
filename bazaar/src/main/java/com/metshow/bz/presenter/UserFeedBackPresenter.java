package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ImgUploadResult;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2016-8-28.
 */
public class UserFeedBackPresenter extends BzPresenter {

    private BZContract.IUserFeedBackView mIUserFeedBackView;


    public UserFeedBackPresenter(BZContract.IUserFeedBackView iUserFeedBackView) {
        super(iUserFeedBackView);
        mIUserFeedBackView = iUserFeedBackView;
    }


    public void addFeedBack(String Images,String Content){
        mModel.addFeedBack(Images, Content);
    }

    public void upLoadImage(String path){
		mModel.uploadImag(path);
    }

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);
		switch (vocational_id) {
			case ServerAPI.FEEDBACK_ADD_VOCATIONAL_ID:
				mIUserFeedBackView.addFeedBackSuccess((String)s.getResult());
				break;
		}

	}

	@Override
	public void onServerUploadNext(int vocational_id, HashMap exdata, Object s) {
		super.onServerUploadNext(vocational_id, exdata, s);

		switch (vocational_id) {

			case ServerAPI.PIC_UPLOADIMAGE_VOCATIONAL_ID:
				int error = ((ImgUploadResult)s).getError();
				if (error == 0)
					mIUserFeedBackView.uploadImgSuccess(((ImgUploadResult)s).getUrl());
				else {
					mIUserFeedBackView.onServerFailed("发送失败：" + ((ImgUploadResult)s).getMessage());
					mIUserFeedBackView.uploadImgFailed();
				}
				break;
		}
	}
}
