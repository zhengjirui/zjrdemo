package com.kwan.base.presenter;

import android.support.annotation.CallSuper;
import android.util.Log;

import com.kwan.base.bean.ServerMsg;
import com.kwan.base.model.BaseModel;
import com.kwan.base.model.IBasePresenter;
import com.kwan.base.rxbus.RxBus;
import com.kwan.base.rxbus.RxBusSubscriber;
import com.kwan.base.rxbus.RxBusSubscriberListener;
import com.kwan.base.util.SysUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static android.content.ContentValues.TAG;

/**
 * Presenter 基类
 * Created by Mr.Kwan on 2016-9-30.
 */

public abstract class BasePresenter implements IBasePresenter {

    protected IBaseView mIBaseView;
	protected List<Subscription> mRXSubscription = new ArrayList<>();
	protected CompositeSubscription mCompositeSubscription
            = new CompositeSubscription();


    public BasePresenter(IBaseView iView) {
        mIBaseView = iView;
    }

    @CallSuper
    public void onActivityCreate() {

    }

    @CallSuper
    public void onActivityDestroy() {

        //取消所有RXBus订阅
        for (Subscription subscription : mRXSubscription) {
            if (subscription.isUnsubscribed())
                subscription.unsubscribe();
        }
        //取消所有网络订阅
        getModel().unSubscribeNet();
        mCompositeSubscription.unsubscribe();

    }

    @Override
    public void onNoNetWork() {
        mIBaseView.toastMsg("没有网络");
        mIBaseView.dismissProgress();
        mIBaseView.onNoNetWork();
    }

    @Override
    public void onServerError(int vocational_id, Throwable throwable) {

		Log.d("BasePresenter","onServerError");
        throwable.printStackTrace();
        if (throwable instanceof java.net.SocketTimeoutException) {
            mIBaseView.toastMsg("连接超时,请稍后重试！");
        } else {
            Logger.d(throwable.getMessage());
        }
        mIBaseView.dismissProgress();
        mIBaseView.onServerError(vocational_id, throwable);
    }

    @Override
    public void onServerFailed(String msg) {
        mIBaseView.toastMsg(msg);
        mIBaseView.dismissProgress();
        mIBaseView.onServerFailed(msg);
    }

    @Override
    public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, ServerMsg s) {
		Log.d("onServerSuccess","vocational_id:"+vocational_id);
        mIBaseView.dismissProgress();
    }

	@Override
	public void onServerSuccess(int vocational_id, HashMap<String, Object> exData, byte[] s) {
		Log.d("onServerSuccessByte","vocational_id:"+vocational_id);
		mIBaseView.dismissProgress();
	}

	@Override
	public void onServerUploadError(int vocational_id, HashMap exdata, Throwable e) {

	}

	@Override
	public void onServerUploadCompleted(int vocational_id, HashMap exdata) {

	}

	@Override
	public void onServerUploadNext(int vocational_id, HashMap exdata, Object s) {

	}



    /**
     * 订阅RxBus要监听的 数据类型
     *
     * @param clz     数据类型
     * @param listener 监听回调
     * @param <T>      类型
     */
    public <T> void initRxBus(Class<T> clz, RxBusSubscriberListener<T> listener) {
        Log.d(TAG, "initRxBus");
        Subscriber<T> subscriber = new RxBusSubscriber<>(listener);
        mRXSubscription.add(
                RxBus.getDefault().toObservable(clz).subscribe(subscriber)
        );

    }

    public void postRxBus(Object o) {
        RxBus.getDefault().post(o);
    }

    public static String getData(String date) {
		return date.replace("/Date(", "").replace(")/", "");
	}

	public static String getStrData(String date) {

		if(date!=null&&!date.isEmpty()) {

			long min = 60 * 1000;
			long hour = 60 * min;
			long day = 24 * hour;
			long mon = 30 * day;

			long dateTime = Long.parseLong(getData(date).trim());
			long dateCurrent = System.currentTimeMillis();

			long showTime = dateCurrent - dateTime + (8 * hour);

			if (showTime <= min)
				return showTime / 1000 + "秒前";

			if (showTime <= hour)
				return showTime / min + "分钟前";

			if (showTime <= day)
				return showTime / hour + "小时前";

			if (showTime <= mon)
				return showTime / day + "天前";

			if (showTime <= 3 * mon)
				return showTime / mon + "个月前";

			return SysUtil.milliSecond2Date("MM-dd", Long.parseLong(getData(date).trim()));
		}

		return "null";
	}

	public static String getfullData(String date){
		return SysUtil.milliSecond2Date("yyyy-MM-dd hh:mm:ss", Long.parseLong(getData(date).trim()));
	}

	public static String getStrEndData(String date) {

		long min = 60 * 1000;
		long hour = 60 * min;
		long day = 24 * hour;
		long mon = 30 * day;

		long dateTime = Long.parseLong(getData(date).trim());
		long dateCurrent = System.currentTimeMillis();

		long showTime = Math.abs(dateCurrent - dateTime + (8 * hour));

		if (showTime <= min)
			return showTime / 1000 + "秒";

		if (showTime <= hour)
			return showTime / min + "分钟";

		if (showTime <= day)
			return showTime / hour + "小时";

		if (showTime <= mon)
			return showTime / day + "天";

		if (showTime <= 3 * mon)
			return showTime / mon + "个月";

		return SysUtil.milliSecond2Date("MM-dd", Long.parseLong(getData(date).trim()));
	}



	protected abstract BaseModel getModel();
}
