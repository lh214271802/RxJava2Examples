package com.nanchen.rxjava2examples.module.rxjava2.operators.item;

import android.util.Log;

import com.nanchen.rxjava2examples.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * PublishSubject
 * <p>
 * onNext() 会通知每个观察者，仅此而已
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-06-22  14:37
 */

public class RxPublishSubjectActivity extends RxOperatorBaseActivity {
    private static final String TAG = "RxPublishSubjectActivit";
    private PublishSubject<Integer> publishSubject;

    @Override
    protected String getSubTitle() {
        return getString(R.string.rx_PublishSubject);
    }

    @Override
    protected void doSomething2() {
        super.doSomething2();


        publishSubject.onNext(1);
        publishSubject.onNext(2);
        publishSubject.onNext(3);

        publishSubject.onNext(4);
        publishSubject.onNext(5);
        publishSubject.onComplete();
    }

    @Override
    protected void doSomething() {
        mRxOperatorsText.append("PublishSubject\n");
        Log.e(TAG, "PublishSubject\n");

        publishSubject = PublishSubject.create();

        publishSubject.subscribeOn(Schedulers.newThread())
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 500; i++) {
                            list.add((integer + i * 100 - 199) + "");
                        }
                        return Observable.fromIterable(list);
                    }
                }).flatMap(new Function<String, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(String s) throws Exception {
                return Observable.just(Integer.parseInt(s));
            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer > 0;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mRxOperatorsText.append("First onSubscribe :" + d.isDisposed() + "\n");
                        Log.e(TAG, "First onSubscribe :" + d.isDisposed() + "\n");
                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        mRxOperatorsText.append("First onNext value :" + integer + "\n");
                        Log.e(TAG, "First onNext value :" + integer + "\n");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mRxOperatorsText.append("First onError:" + e.getMessage() + "\n");
                        Log.e(TAG, "First onError:" + e.getMessage() + "\n");
                    }

                    @Override
                    public void onComplete() {
                        mRxOperatorsText.append("First onComplete!\n");
                        Log.e(TAG, "First onComplete!\n");
                    }
                });

        publishSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxOperatorsText.append("Second onSubscribe :" + d.isDisposed() + "\n");
                Log.e(TAG, "Second onSubscribe :" + d.isDisposed() + "\n");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                mRxOperatorsText.append("Second onNext value :" + integer + "\n");
                Log.e(TAG, "Second onNext value :" + integer + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mRxOperatorsText.append("Second onError:" + e.getMessage() + "\n");
                Log.e(TAG, "Second onError:" + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                mRxOperatorsText.append("Second onComplete!\n");
                Log.e(TAG, "Second onComplete!\n");
            }
        });
    }
}
