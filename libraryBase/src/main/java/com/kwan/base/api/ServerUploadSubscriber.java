package com.kwan.base.api;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Mr.Kwan on 2016-4-7.
 */
public class ServerUploadSubscriber<T> extends Subscriber<T> {

    private ServerUploadSubscriberListener<T> mListener;
    public int vocational_id = -1;
    public HashMap<String,Object> exdata;


    public ServerUploadSubscriber(ServerUploadSubscriberListener<T> listener) {
        mListener = listener;
    }

    @Override
    public void onCompleted() {
        mListener.onServerUploadCompleted(vocational_id, exdata);
    }

    @Override
    public void onError(Throwable e) {
        mListener.onServerUploadError(vocational_id, exdata, e);
    }

    @Override
    public void onNext(T result) {
        mListener.onServerUploadNext(vocational_id, exdata, result);
    }

    public interface ServerUploadSubscriberListener<T> {

        void onServerUploadCompleted(int vocational_id, HashMap<String, Object> exdata);

        void onServerUploadError(int vocational_id, HashMap<String, Object> exdata, Throwable e);

        void onServerUploadNext(int vocational_id, HashMap<String, Object> exdata, T s);

    }


}
