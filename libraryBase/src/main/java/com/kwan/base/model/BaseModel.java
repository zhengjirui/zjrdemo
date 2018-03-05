package com.kwan.base.model;

import android.util.Log;

import com.google.gson.Gson;
import com.kwan.base.api.ServerByteSubscriberListener;
import com.kwan.base.api.ServerSubscriberListener;
import com.kwan.base.api.ServerUploadSubscriber;
import com.kwan.base.bean.ServerMsg;
import com.kwan.base.util.SysUtil;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by Mr.Kwan on 2016-10-8.
 */

public abstract class BaseModel implements ServerSubscriberListener,ServerByteSubscriberListener, ServerUploadSubscriber.ServerUploadSubscriberListener {


	private IBasePresenter mIBasePresenter;
	/**
	 * 存放了所有请求的集合
	 */
	protected HashMap<Integer, Subscription> requests = new HashMap<>();

	public BaseModel(IBasePresenter iBasePresenter) {
		mIBasePresenter = iBasePresenter;
	}


	/**
	 * @param args
	 * @return
	 */
	protected String getJsonStrArg(HashMap<String, Object> args) {

		Gson gson = new Gson();
		String json = gson.toJson(args);
		Logger.t("Args::").json(json);
		return json;
	}

	protected boolean checkNetWorkAvailable() {

		if (SysUtil.getNetworkState() == SysUtil.NETWORK_NONE) {
			mIBasePresenter.onNoNetWork();
		}
		// Log.d("BaseModel", "checkNetWorkAvailable:" + SysUtil.getNetworkState());
		return SysUtil.getNetworkState() == SysUtil.NETWORK_NONE;

	}

	@Override
	public void onServerCompleted(int vocational_id, HashMap<String, Object> exData) {

	}

	@Override
	public void onServerError(int vocational_id, HashMap<String, Object> exData, Throwable throwable) {
		mIBasePresenter.onServerError(vocational_id, throwable);
	}

	@Override
	public void onServerNext(int vocational_id, HashMap<String, Object> exData, ServerMsg serverMsg) {

		if (serverMsg.isSuc()) {
			mIBasePresenter.onServerSuccess(vocational_id, exData, serverMsg);
		} else {
			mIBasePresenter.onServerFailed(serverMsg.getMessage());
		}
	}

	@Override
	public void onServerNext(int vocational_id, HashMap<String, Object> exData, byte[] serverMsg) {


			mIBasePresenter.onServerSuccess(vocational_id, exData, serverMsg);

	}

	@Override
	public void onServerUploadCompleted(int vocational_id, HashMap exdata) {
		mIBasePresenter.onServerUploadCompleted(vocational_id, exdata);
	}

//	@Override
//	public void onServerUploadNext(int vocational_id, HashMap<String, Object> exdata, ServerMsg<String> s) {
//
//		if (s.isSuc()) {
//			mIBasePresenter.onServerUploadNext(vocational_id,exdata,s);
//		} else {
//			mIBasePresenter.onServerFailed(s.getMessage());
//		}
//
//	}


	@Override
	public void onServerUploadNext(int vocational_id, HashMap exdata, Object s) {

		if (s instanceof ServerMsg)
			if (((ServerMsg) s).isSuc()) {
				mIBasePresenter.onServerUploadNext(vocational_id, exdata, s);
			} else {
				mIBasePresenter.onServerFailed(((ServerMsg) s).getMessage());
			}
		else
			mIBasePresenter.onServerUploadNext(vocational_id, exdata, s);
	}

	@Override
	public void onServerUploadError(int vocational_id, HashMap exdata, Throwable e) {
		mIBasePresenter.onServerUploadError(vocational_id, exdata, e);
	}

	public class TransFormJson<T> {

		public Func1<String, Observable<T>> getTransFormer(final java.lang.reflect.Type type) {

			return new Func1<String, Observable<T>>() {
				@Override
				public Observable<T> call(String s) {
					Logger.t("ServerBack::").e(s);
					Gson gson = new Gson();
					final T msg = gson.fromJson(s, type);
					Logger.t("ServerBack::").e("fromJson ok");
                    Log.e("kwan","kkwan--- "+s);

					return Observable.create(new Observable.OnSubscribe<T>() {
						@Override
						public void call(Subscriber<? super T> subscriber) {
							subscriber.onNext(msg);
							subscriber.onCompleted();
						}
					});
				}
			};


		}
	}

	public void unSubscribeNet() {

		Set<Integer> set = requests.keySet();
		for (Integer integer : set) {
			requests.get(integer).unsubscribe();
		}
	}

	public void unSubscribeNet(int id) {
		if (requests.get(id) != null)
			requests.get(id).unsubscribe();
	}


}

