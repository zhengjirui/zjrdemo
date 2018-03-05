package com.metshow.bz.presenter;

import com.kwan.base.bean.ServerMsg;
import com.metshow.bz.commons.bean.Brand;
import com.metshow.bz.commons.bean.ListData;
import com.metshow.bz.commons.bean.Product;
import com.metshow.bz.commons.bean.Special;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;

import java.util.HashMap;

/**
 * Created by Mr.Kwan on 2016-8-21.
 */

public class BuyItemPresenter extends BzPresenter {

	private BZContract.IBuyItemView mIBuyItemView;

	public BuyItemPresenter(BZContract.IBuyItemView iBuyItemView) {
		super(iBuyItemView);
		mIBuyItemView = iBuyItemView;

	}

	public void getSpecials(int pageSize,String maxdate){
		mModel.getSpecials(pageSize,maxdate);
	}

	public void getProducts(long categoryId, int pagesize, String maxdate, boolean isLogin) {
		mModel.getProducts(categoryId, pagesize, maxdate, isLogin);
	}

	public void getProducts(String productname, int pagesize, String maxdate, boolean isLogin) {
		mModel.getProducts(productname, pagesize, maxdate, isLogin);
	}

	public void getBrand() {
		mModel.getBrand();
	}


	public void addAction(int acttype, long refid, int position) {
		mModel.addAction(acttype, refid, position);
	}

	public void deleteFav(long refid, int position) {
		mModel.deleteFav(refid, position);
	}


	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {

			case ServerAPI.PRODUCTSPECIAL_LIST_VOCATIONAL_ID:
				mIBuyItemView.getSpecialsSuccess(((ListData<Special>) s.getResult()).getItems());
				break;
			case ServerAPI.PRODUCT_LISTBYCATEGORY_VOCATIONAL_ID:
				mIBuyItemView.getProductsSuccess(((ListData<Product>) s.getResult()).getItems());
				break;
			case ServerAPI.PRODUCT_SEARCH_VOCATIONAL_ID:
				mIBuyItemView.getProductsSuccess(((ListData<Product>) s.getResult()).getItems());
				break;
			//点赞  收藏成功
			case ServerAPI.ACTION_ADD_VOCATIONAL_ID:
				if ((int) exData.get("acttype") == 1) {//收藏
					mIBuyItemView.favSuccess((Integer) s.getResult(), (int) exData.get("position"));
				}
				break;
			//取消收藏
			case ServerAPI.ACTION_DELETEFAV_VOCATIONAL_ID:
				mIBuyItemView.deleteFavSuccess(Integer.valueOf((String)s.getResult()), (int) exData.get("position"));
				break;
			case ServerAPI.BRAND_LIST_VOCATIONAL_ID:
				mIBuyItemView.getBrandListSuccess(((ListData<Brand>) s.getResult()).getItems());
				break;
		}

	}

}
