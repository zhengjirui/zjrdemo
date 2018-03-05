package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.BzActivity;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.TriProduct;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mr.Kwan on 2017-7-5.
 */

public class ShaiActivityPresenter extends BzPresenter {

	BZContract.IShaiActivityView mIView;

	public ShaiActivityPresenter(BZContract.IShaiActivityView iView) {

		super(iView);
		mIView =iView;
	}


	public void getActivitys(int pageindex, int pagesize) {
		mModel.getShaiActivity(pageindex, pagesize);
	}

	public void getTriProducts() {
		mModel.getTriProducts();
	}



	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			case ServerAPI.TOPIC_LIST_VOCATIONAL_ID:
				mIView.getActivitysSuccess(((ListData<BzActivity>) s.getResult()).getItems());
				break;
			case ServerAPI.TRIPRODUCT_LIST_VOCATIONAL_ID:
				mIView.getTriProductSuccess((List<TriProduct>) s.getResult());
				break;
		}


	}
}
