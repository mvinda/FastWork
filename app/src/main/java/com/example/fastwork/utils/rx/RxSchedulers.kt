package com.maka.app.util.rx

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxSchedulers {

    companion object {

        @JvmStatic
        fun <T> applyObservableDoSubscribe(): ObservableTransformer<T, T> {
            return ObservableTransformer {
                it.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }

        @JvmStatic
        fun <T> applyObservableAsync(): ObservableTransformer<T, T> {
            return ObservableTransformer {
                it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            }
        }

        @JvmStatic
        fun <T> applyObservableCompute(): ObservableTransformer<T, T> {
            return ObservableTransformer {
                it.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
            }
        }

        @JvmStatic
        fun <T> applyObservableMainThread(): ObservableTransformer<T, T> {
            return ObservableTransformer { it.observeOn(AndroidSchedulers.mainThread()) }
        }

        @JvmStatic
        fun <T> applyFlowableMainThread(): FlowableTransformer<T, T> {
            return FlowableTransformer { it.observeOn(AndroidSchedulers.mainThread()) }
        }

        @JvmStatic
        fun <T> applyFlowableAsysnc(): FlowableTransformer<T, T> {
            return FlowableTransformer { it.observeOn(AndroidSchedulers.mainThread()) }
        }

        @JvmStatic
        fun <T> applyFlowableCompute(): FlowableTransformer<T, T> {
            return FlowableTransformer { it.observeOn(AndroidSchedulers.mainThread()) }
        }
    }
}