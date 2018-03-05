package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.PointRecord;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Kwan on 2018/2/6/006.
 */

public class PointRecordPresenter extends BzPresenter {

	BZContract.IPointRecordlView mIViews;

	public PointRecordPresenter(BZContract.IPointRecordlView iView) {
		super(iView);
		mIViews = iView;
	}

	public void getPointRecordList(int pageIndex,int pageSize){
		mModel.getPointRecord(pageIndex,pageSize);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id){
			case ServerAPI.POINTSHOP_EXCHANGELIST_VOCATIONAL_ID:
				mIViews.getPointRecordSuccess(((ListData<PointRecord>)s.getResult()).getItems());
				break;
		}
	}
}
