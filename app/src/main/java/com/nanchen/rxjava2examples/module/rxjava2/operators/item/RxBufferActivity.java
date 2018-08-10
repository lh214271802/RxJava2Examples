package com.nanchen.rxjava2examples.module.rxjava2.operators.item;

import android.util.Log;

import com.nanchen.rxjava2examples.R;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * buffer
 * <p>
 * buffer(count, skip)` 从定义就差不多能看出作用了，
 * 将 observable 中的数据按 skip（步长）分成最长不超过 count 的 buffer，然后生成一个 observable
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-06-22  10:20
 */

public class RxBufferActivity extends RxOperatorBaseActivity {

    private static final String TAG = "RxBufferActivity";

    @Override
    protected String getSubTitle() {
        return getString(R.string.rx_buffer);
    }

    @Override
    protected void doSomething() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).compose(new ObservableTransformer<Integer, Integer>() {
            @Override
            public ObservableSource<Integer> apply(Observable<Integer> upstream) {
                return upstream.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
            }
        })
                .buffer(3, 3)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(@NonNull List<Integer> integers) throws Exception {
                        mRxOperatorsText.append("buffer size : " + integers.size() + "\n");
                        Log.e(TAG, "buffer size : " + integers.size() + "\n");
                        mRxOperatorsText.append("buffer value : ");
                        Log.e(TAG, "buffer value : ");
                        for (Integer i : integers) {
                            mRxOperatorsText.append(i + "");
                            Log.e(TAG, i + "");
                        }
                        mRxOperatorsText.append("\n");
                        Log.e(TAG, "\n");
                    }
                });
    }
}
