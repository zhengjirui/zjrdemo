package com.kwan.base.rxbus;

/**
 *
 * Created by Mr.Kwan on 2016-4-6.
 */
public interface RxBusSubscriberListener<T> {

     void onRxBusCompleted();

     void onRxBusError(Throwable e);

     void onRxBusNext(T t);

}
