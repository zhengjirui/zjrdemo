package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.PointDetail;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Kwan on 2018/2/6/006.
 */

public class PointDetailPresenter extends BzPresenter {

	BZContract.IPointDetailView mIViews;

	public PointDetailPresenter(BZContract.IPointDetailView iView) {
		super(iView);
		mIViews = iView;
	}

	public void getPointProductList(int pageIndex,int pageSize){
		mModel.getPointDetail(pageIndex,pageSize);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id){
			case ServerAPI.USERPOINTSLOG_LIST_VOCATIONAL_ID:
				mIViews.getPointDetailSuccess(((ListData<PointDetail>)s.getResult()).getItems());
				break;
		}
	}
}
