package com.example.fastwork.utils.rx;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.Subject;


public class RxBus {

    private static final String TAG = "RxBus";

    private static volatile RxBus mInstance;

    private Subject<Object> mSubject;

    private RxBus() {
        mSubject = io.reactivex.subjects.PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                if (mInstance == null) {
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }

    public void post(Object object) {
        mSubject.onNext(object);
    }

    private <T> Observable<T> toObservable(final Class<T> type) {
        return mSubject.ofType(type);
    }

    public boolean hasObservers() {
        return mSubject.hasObservers();
    }

    public <T> Disposable toSubscription(Class<T> type, Consumer<T> consumer, Scheduler scheduler) {
        return RxBus.getInstance()
                .toObservable(type)
                .observeOn(scheduler)
                .subscribe(consumer);
    }

    public <T> Disposable doOnMainThread(Class<T> type, Consumer<T> consumer) {
        return toSubscription(type, consumer, AndroidSchedulers.mainThread());
    }
}
