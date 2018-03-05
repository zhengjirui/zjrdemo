package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.PointProduct;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Kwan on 2018/2/6/006.
 */

public class PointProductListPresenter extends BzPresenter {

	BZContract.IPointProductListView mIViews;

	public PointProductListPresenter(BZContract.IPointProductListView iView) {
		super(iView);
		mIViews = iView;
	}

	public void getPointProductList(String maxDate,int pageSize){
		mModel.getPointProductList(maxDate,pageSize);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id){
			case ServerAPI.POINTSHOP_LIST_VOCATIONAL_ID:
				mIViews.getPointProductListSuccess(((ListData<PointProduct>)s.getResult()).getItems());
				break;
		}
	}
}
