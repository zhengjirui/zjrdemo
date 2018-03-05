package com.metshow.bz.presenter;

import android.util.Log;

import com.kwan.base.bean.ServerMsg;
import com.kwan.base.rxbus.RxBusSubscriberListener;
import com.metshow.bz.commons.bean.Product;
import com.metshow.bz.contract.BZContract;
import com.metshow.bz.net.api.ServerAPI;
import com.metshow.bz.util.BZSPUtil;

import java.util.HashMap;

import io.LruCacheUtil;


/**
 * Created by Mr.Kwan on 2016-8-18.
 */
public class ProductPresenter extends BzPresenter implements RxBusSubscriberListener<Product> {

    private BZContract.IProductView mIProductView;

	private LruCacheUtil<Product> lruCacheUtil;
	private long id;
	private String token="";
	private boolean isLogin;

    public ProductPresenter(BZContract.IProductView iProductView) {
        super(iProductView);
        mIProductView = iProductView;
		lruCacheUtil = new LruCacheUtil<>();
		initRxBus(Product.class, this);
    }

    public void getProduct(long productId,boolean isLogin){


		this.id = productId;
		this.isLogin =isLogin;
		token = BZSPUtil.getUser().getToken();

		//判断是否过期
		if (lruCacheUtil.isObjectCacheOutOfDate("product" + this.id + "" + token, LruCacheUtil.MAX_CACHEDATA_1HOUR)) {
			//网络请求
			mModel.getProduct(productId,isLogin);
		} else {
			//缓存请求 请求完成后会通过RxBus通知回调
			Log.d("ProductPresenter", "begin lruCacheUtil.read");
			lruCacheUtil.read("product" + this.id + "" + token);
		}
    }

    public void deteleFav(long refid,int position){
		mModel.deleteFav(refid, position);
    }

    public void addFav(long refid,int position){
		mModel.addFav(refid, position);
    }


	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		super.onServerSuccess(vocational_id, exData, s);

		switch (vocational_id) {
			case ServerAPI.PRODUCT_DETAIL_VOCATIONAL_ID:
				lruCacheUtil.write((Product) s.getResult(), "product" + this.id + "" + token);
				mIProductView.getProductSuccess((Product) s.getResult());
				break;

			//点赞  收藏成功
			case ServerAPI.ACTION_ADD_VOCATIONAL_ID:
				if ((int) exData.get("acttype") == 1) {//收藏
					mIProductView.favSuccess((String) s.getResult(), (int) exData.get("position"));
				}
				break;
			//取消收藏
			case ServerAPI.ACTION_DELETEFAV_VOCATIONAL_ID:
				mIProductView.deleteFavSuccess((String) s.getResult(), (int) exData.get("position"));
				break;
		}
	}

	@Override
	public void onRxBusCompleted() {

	}

	@Override
	public void onRxBusError(Throwable e) {
		mModel.getProduct(id,isLogin);
	}

	@Override
	public void onRxBusNext(Product product) {
		Log.d("Article presenter", "onRxBusNext");
		mIProductView.getProductSuccess(product);
	}
}
