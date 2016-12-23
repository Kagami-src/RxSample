package com.kagami.rxsample.rx;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by sinceredeveloper on 16/10/21.
 */


/**
 * 事件总线
 */
public class RxBus {
    private static volatile RxBus defaultInstance;

    private final Subject<Object, Object> bus;
    public RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }
    // 单例RxBus
    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance ;
    }

    /**
     * 发送事件
     * @param o
     */
    public void post (Object o) {
        bus.onNext(o);
    }
    public <T> Observable<T> toObservable (Class<T> eventType) {
        return bus.ofType(eventType);
    }

    /**
     * 监听事件
     * @param eventClass
     * @param onNext
     * @param <T>
     * @return
     */
    public <T> Subscription register(final Class<T> eventClass, Action1<T> onNext) {
        return toObservable(eventClass).subscribe(onNext);
    }



//使用方法

//    messageDeleteSubscription=RxBus.getDefault().register(RxBus.MessageDeleteEvent.class, new Action1<RxBus.MessageDeleteEvent>() {
//        @Override
//        public void call(RxBus.MessageDeleteEvent messageDeleteEvent) {
//
//          //do something
//        }
//    });
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(!messageDeleteSubscription.isUnsubscribed())
//            messageDeleteSubscription.unsubscribe();
//    }
}
