package com.example.fastwork.utils.rx;

import io.reactivex.disposables.Disposable;
import org.reactivestreams.Subscription;



public class RxUtil {

    public static void unSubscribe(Subscription subscription) {
        if (subscription != null) {
            subscription.cancel();
        }
    }

    public static void unSubscribe(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static void unSubscribe(Subscription... subscriptions) {
        for (Subscription subscription :
                subscriptions) {
            unSubscribe(subscription);
        }
    }

    public static void unSubscribe(Disposable... disposables) {
        for (Disposable disposable :
                disposables) {
            unSubscribe(disposable);
        }
    }
}
