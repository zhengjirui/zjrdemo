package com.kwan.base.api;


import com.kwan.base.bean.ServerMsg;

import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by Mr.Kwan on 2016-4-7.
 */
public class ServerSubscriber<T> extends Subscriber<ServerMsg<T>> {

    private ServerSubscriberListener mListener;
    public int vocational_id = -1;
    public HashMap<String,Object> exData;


    public ServerSubscriber(ServerSubscriberListener listener) {
        mListener = listener;
    }

	/**
	 * 事件队列完结
	 * <br>
	 * 在一个正确运行的事件序列中, onCompleted() 和 onError() 有且只有一个，
	 * 并且是事件序列中的最后一个。
	 * 需要注意的是，onCompleted() 和 onError() 二者也是互斥的，
	 * 即在队列中调用了其中一个，就不应该再调用另一个。
	 */
    @Override
    public void onCompleted() {
        mListener.onServerCompleted(vocational_id, exData);
    }

    @Override
    public void onError(Throwable e) {
        mListener.onServerError(vocational_id, exData, e);
    }

    @Override
    public void onNext(ServerMsg<T> tServerJsonMsg) {
        mListener.onServerNext(vocational_id, exData, tServerJsonMsg);
    }


}
