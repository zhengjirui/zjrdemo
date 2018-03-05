package com.metshow.bz.presenter;

import android.util.Log;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.FilterBean;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.StickerType;
import com.metshow.bz.commons.bean.topic.StickerBean;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;


/**
 * Created by Mr.Kwan on 2016-8-16.
 */
public class PhotoEditPresenter extends BzPresenter  {

	private BZContract.IPhotoEditView mIPhotoEditView;

	public PhotoEditPresenter(BZContract.IPhotoEditView iPhotoEditView) {
		super(iPhotoEditView);
		mIPhotoEditView = iPhotoEditView;
	}

	public void getStickerType() {
		mModel.getStickerType();
	}

	public void getSticker(long id){
		mModel.getSticker(id);
	}

	public void getFilter(){
		Log.e("kwan","xx");
		mModel.getFilter();
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			case ServerAPI.STICKERTYPE_LIST_VOCATIONAL_ID:
				mIPhotoEditView.getStickerTypeSuccess(((ListData<StickerType>) s.getResult()).getItems());
				break;
			//点赞  收藏成功
			case ServerAPI.STICKER_LIST_VOCATIONAL_ID:
                mIPhotoEditView.getStickerSuccess(((ListData<StickerBean>) s.getResult()).getItems());
				break;
			//取消收藏
			case ServerAPI.FILTER_LIST_VOCATIONAL_ID:
				mIPhotoEditView.getFilterSuccess(((ListData<FilterBean>) s.getResult()).getItems());
				break;
		}
	}

}
