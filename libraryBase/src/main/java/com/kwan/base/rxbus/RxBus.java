package com.kwan.base.rxbus;


import rx.android.schedulers.AndroidSchedulers;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 *
 * Created by Mr.Kwan on 2016-4-5.
 */
public class RxBus {

    private static volatile RxBus defaultInstance;
    // 主题
    private final Subject<Object,Object> bus;
    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    public RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }
    // 单例RxBus
    public static RxBus getDefault() {
        RxBus rxBus = defaultInstance;
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                rxBus = defaultInstance;
                if (defaultInstance == null) {
                    rxBus = new RxBus();
                    defaultInstance = rxBus;
                }
            }
        }
        return rxBus;
    }

    /**
     * 提供了一个新的事件
     */

    public void post (Object o) {
        bus.onNext(o);
    }

    public void erro(Exception e){
        bus.onError(e);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObservable (Class<T> eventType) {

        //主线程返回  我艹艹 AndroidSchedulers.mainThread()
        return bus.ofType(eventType).observeOn(AndroidSchedulers.mainThread());
//        这里感谢小鄧子的提醒: ofType = filter + cast
//        return bus.filter(new Func1<Object, Boolean>() {
//            @Override
//            public Boolean call(Object o) {
//                return eventType.isInstance(o);
//            }
//        }) .cast(eventType);
    }

}
