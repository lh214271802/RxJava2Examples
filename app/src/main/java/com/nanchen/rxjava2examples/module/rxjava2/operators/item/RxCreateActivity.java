package com.nanchen.rxjava2examples.module.rxjava2.operators.item;

import android.util.Log;

import com.nanchen.rxjava2examples.R;
import com.nanchen.rxjava2examples.net.err.HttpResultFunction;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * create
 * <p>
 * 最常见的操作符，用于生产一个发射对象
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-06-19  17:06
 */

public class RxCreateActivity extends RxOperatorBaseActivity {
    private static final String TAG = "RxCreateActivity";

    @Override
    protected String getSubTitle() {
        return getString(R.string.rx_create);
    }

    @Override
    protected void doSomething() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                mRxOperatorsText.append("Observable emit 1" + "\n");
                Log.e(TAG, "Observable emit 1" + "\n");
                e.onNext(1);
                mRxOperatorsText.append("Observable emit 2" + "\n");
                Log.e(TAG, "Observable emit 2" + "\n");
                e.onNext(2);
                mRxOperatorsText.append("Observable emit 3" + "\n");
                Log.e(TAG, "Observable emit 3" + "\n");
                e.onNext(3);
                e.onComplete();
                mRxOperatorsText.append("Observable emit 4" + "\n");
                Log.e(TAG, "Observable emit 4" + "\n");
                e.onNext(4);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                return Observable.just(integer).map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        mRxOperatorsText.append("~~Observable flatMap~~~~" + integer + "\n");
                        return "----->900" + integer;
                    }
                });
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                String[] strings = s.split("----->");
                int parseInt = Integer.parseInt(strings[1]);
                mRxOperatorsText.append("%%%%%%Observable map%%%%%%" + parseInt + "\n");
                return parseInt;
            }
        }).onErrorResumeNext(new HttpResultFunction<Integer>())
                .subscribe(new Observer<Integer>() {
                    private int i;
                    private Disposable mDisposable;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mRxOperatorsText.append("onSubscribe : " + d.isDisposed() + "\n");
                        Log.e(TAG, "onSubscribe : " + d.isDisposed() + "\n");
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        mRxOperatorsText.append("onNext : value : " + integer + "\n");
                        Log.e(TAG, "onNext : value : " + integer + "\n");
                        i++;
                        if (i == 2) {
                            // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                            mDisposable.dispose();
                            mRxOperatorsText.append("onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
                            Log.e(TAG, "onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mRxOperatorsText.append("onError : value : " + e.getMessage() + "\n");
                        Log.e(TAG, "onError : value : " + e.getMessage() + "\n");
                    }

                    @Override
                    public void onComplete() {
                        mRxOperatorsText.append("onComplete" + "\n");
                        Log.e(TAG, "onComplete" + "\n");
                    }
                });
    }
}
