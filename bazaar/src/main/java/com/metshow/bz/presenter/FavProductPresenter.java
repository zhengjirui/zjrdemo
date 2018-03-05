package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.Product;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2017-7-11.
 */

public class FavProductPresenter extends BzPresenter {

	BZContract.IFavProductView iFavProductView;

	public FavProductPresenter(BZContract.IFavProductView iView) {
		super(iView);
		iFavProductView = iView;
	}

	public void getFavProduct(int pagesize, String createdate) {
		mModel.getFavProduct(pagesize, createdate);
	}

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id){
			case ServerAPI.ACTION_MYPRODUCTFAV_VOCATIONAL_ID:
				iFavProductView.getFavProductSuccess(((ListData<Product>) s.getResult()).getItems());
				break;
		}

	}
}
