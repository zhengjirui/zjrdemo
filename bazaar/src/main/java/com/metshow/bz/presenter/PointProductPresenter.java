package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.PointResult;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Kwan on 2018/2/6/006.
 */

public class PointProductPresenter extends BzPresenter {

	BZContract.IPointResultView mIViews;

	public PointProductPresenter(BZContract.IPointResultView iView) {
		super(iView);
		mIViews = iView;
	}

	public void getPointResult(long PointShopId, String TrueName,String PhoneNum,String Address){
		mModel.getPointResult(PointShopId,TrueName,PhoneNum,Address);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id){
			case ServerAPI.POINTSHOP_ADD_VOCATIONAL_ID:
				mIViews.getPointResultSuccess((PointResult) s.getResult());
				break;
		}
	}
}
