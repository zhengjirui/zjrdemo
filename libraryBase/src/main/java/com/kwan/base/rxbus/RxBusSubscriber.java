package com.kwan.base.rxbus;

import rx.Subscriber;

/**
 *
 * Created by Mr.Kwan on 2016-4-6.
 */
public class RxBusSubscriber<T> extends Subscriber<T> {


    private RxBusSubscriberListener<T> mListener;

    public RxBusSubscriber(RxBusSubscriberListener<T> listener) {
        mListener = listener;
    }

    @Override
    public void onCompleted() {
        mListener.onRxBusCompleted();
    }

    @Override
    public void onError(Throwable e) {
        mListener.onRxBusError(e);
    }

    @Override
    public void onNext(T t) {
        mListener.onRxBusNext(t);
    }

}
