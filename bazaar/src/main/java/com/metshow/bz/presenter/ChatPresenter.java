package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.AdminUser;
import com.metshow.bz.commons.bean.ImgUploadResult;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.message.MsgChatDetail;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;
import com.metshow.bz.util.BZSPUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mr.Kwan on 2016-7-26.
 */
public class ChatPresenter extends BzPresenter {

    private BZContract.IChatView mIChatView;


    public ChatPresenter( BZContract.IChatView iChatView) {
        super(iChatView);
        mIChatView = iChatView;

    }

    public void getAdminInfo(long id){
		mModel.getAdminInfo(id);
    }

    public void uploadImg(String path) {

        mModel.uploadImag(path);
    }

    public void addMsg(long ToUserId, String Content, int Type, String Url) {
		mModel.addMsg(ToUserId, Content, Type, Url);
    }

    public void getMsgDiolgDetails(int pagesize, String maxdate, long touserid) {
		mModel.getMsgChatDialog(pagesize, maxdate, touserid);
    }

	@Override
	public void onServerUploadNext(int vocational_id, HashMap exdata, Object s) {
		super.onServerUploadNext(vocational_id, exdata, s);

		switch (vocational_id) {

			case ServerAPI.PIC_UPLOADIMAGE_VOCATIONAL_ID:
				//{error:0,url:imageurl}
				int error = ((ImgUploadResult)s).getError();
				if (error == 0)
					mIChatView.uploadImgSuccess(((ImgUploadResult)s).getUrl());
				else {
					onServerFailed("发送失败：" + ((ImgUploadResult)s).getMessage());
					mIChatView.uploadImgFailed();
				}
				break;
		}

	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);
		long myid = BZSPUtil.getUser().getUserId();
		switch (vocational_id) {

			case ServerAPI.MESSAGE_DIALOG_VOCATIONAL_ID:

				if (s.getResult() != null) {

					List<MsgChatDetail> details = ((ListData<MsgChatDetail>) s.getResult()).getItems();

					for (MsgChatDetail detail : details) {
						//设置 itemtype
						if (detail.getUserId() == myid)
							detail.setItemType(0);
						else
							detail.setItemType(1);
					}
					mIChatView.showMsgChatDiaog(details);
				} else
					onServerFailed(s.getMessage());
				break;

			case ServerAPI.MESSAGE_ADD_VOCATIONAL_ID:
				MsgChatDetail detail = (MsgChatDetail) s.getResult();
				//设置 itemtype
				if (detail.getUserId() == myid)
					detail.setItemType(0);
				else
					detail.setItemType(1);

				mIChatView.addMsgSuccess(detail);
				break;

			case ServerAPI.USER_ADMININFO_VOCATIONAL_ID:
				AdminUser user = (AdminUser) s.getResult();
				mIChatView.getAdminInfoSuccess(user);
				break;

		}
	}
}
