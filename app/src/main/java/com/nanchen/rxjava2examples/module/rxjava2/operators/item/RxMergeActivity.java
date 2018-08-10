package com.nanchen.rxjava2examples.module.rxjava2.operators.item;

import android.util.Log;

import com.nanchen.rxjava2examples.R;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * merge
 * <p>
 * 将多个Observable合起来，接受可变参数，也支持使用迭代器集合
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-06-22  11:19
 */

public class RxMergeActivity extends RxOperatorBaseActivity {

    private static final String TAG = "RxMergeActivity";

    @Override
    protected String getSubTitle() {
        return getString(R.string.rx_merge);
    }

    @Override
    protected void doSomething() {
        Observable.merge(Observable.just(1, 2, 5, 7, 3, 8, 9), Observable.just(3, 4, 5))
                .distinct()
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        mRxOperatorsText.append("merge :" + integer + "\n");
                        Log.e(TAG, "accept: merge :" + integer + "\n");
                    }
                });
    }
}
