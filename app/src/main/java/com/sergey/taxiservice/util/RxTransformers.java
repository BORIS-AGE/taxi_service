package com.sergey.taxiservice.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public final class RxTransformers {

    public static <T> Observable.Transformer<T, T> applyApiRequestSchedulers() {
        return tObservable -> tObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<T, T> applyOnBeforeAndAfter(Action0 before, Action0 after) {
        return tObservable -> tObservable
                .doAfterTerminate(after)
                .doOnUnsubscribe(after)
                .doOnSubscribe(before);
    }
}