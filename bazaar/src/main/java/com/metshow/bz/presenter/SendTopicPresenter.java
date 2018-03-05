package com.metshow.bz.presenter;

import android.util.Log;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ImgUploadResult;
import com.metshow.bz.commons.bean.VideoUploadResult;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.datamodule.BZModule;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2016-9-2.
 */
public class SendTopicPresenter extends BzPresenter {

	BZContract.ISendTopicView mISendTopicView;

	public SendTopicPresenter(BZContract.ISendTopicView iSendTopicView) {
		super(iSendTopicView);
		mISendTopicView = iSendTopicView;
	}

	public void addTopic(String title, String ico, String content, String tagname, String images, long refId, int refType, long topicId) {
		mModel.addTopic(title, ico, content, tagname, images, refId, refType, topicId);
	}

	public void uploadimages(String path) {
		mModel.upLoadImages(path);
	}

	public void upLoadVideo(String path,
							String tags,
							String content,
							String refid,
							String reftype,
							String topicId) {
		mModel.upLoadVideo(path, tags, content, refid, reftype, topicId);
	}


	@Override
	public void onServerUploadNext(int vocational_id, HashMap exdata, Object result) {
		super.onServerUploadNext(vocational_id, exdata, result);

		switch (vocational_id) {

			case ServerAPI.PIC_UPLOADIMAGE_VOCATIONAL_ID:
				ImgUploadResult s = (ImgUploadResult) result;

				if (!BZModule.isFaild) {

					int error = s.getError();
					if (error == 0) {

						BZModule.backImageUrl.add(s.getUrl());
						Log.d("kwan", "上传图片成功：backImageUrl::" + BZModule.imageSize + ":::" + BZModule.backImageUrl.size());
						if (BZModule.backImageUrl.size() == BZModule.imageSize)
							mISendTopicView.uploadImgSuccess(BZModule.backImageUrl);

					} else {

						BZModule.isFaild = true;
						onServerFailed("发送失败：" + s.getMessage());
						mISendTopicView.uploadImgFailed();
					}
				}

				break;
			case ServerAPI.PIC_UPLOADMOV_VOCATIONAL_ID:
				VideoUploadResult vs = (VideoUploadResult) result;

				Log.e("kwan","kwan"+result.toString());

				int error = vs.getError();
				if (error == 0) {
					mISendTopicView.addTopicSuccess(vs);
				} else {
					onServerFailed("发送失败");
					mISendTopicView.uploadImgFailed();
				}

				break;

		}
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			case ServerAPI.ARTICLE_TOPICNEW_ADD_VOCATIONAL_ID:
				mISendTopicView.addTopicSuccess((String) s.getResult());
				break;
		}
	}
}
