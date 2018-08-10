package com.nanchen.rxjava2examples.module.rxjava2.operators.item;

import com.nanchen.rxjava2examples.module.rxjava2.operators.item.RxOperatorBaseActivity;
import com.nanchen.rxjava2examples.net.err.HttpResultFunction;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author LiaoHui
 * @date 2018/8/10
 * @desc
 */
public class RxOnErrorResumeNextActivity extends RxOperatorBaseActivity {
    @Override
    protected String getSubTitle() {
        return "OnErrorResumeNext";
    }

    @Override
    protected void doSomething() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                for (int i = 0; i < 6; i++) {
                    final int finalI = i;
                    if (i == 3) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRxOperatorsText.append(finalI + "ERROR<----------ObservableEmitter\n");
                            }
                        });
                        e.onError(new Throwable("ERROR"));
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRxOperatorsText.append(finalI + "<---ObservableEmitter\n");
                            }
                        });
                        e.onNext(i + "");
                    }
                    Thread.sleep(1000);
                }
                e.onComplete();
            }
        });

        observable
                .onErrorResumeNext(new HttpResultFunction<String>()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mRxOperatorsText.append(d.isDisposed() + "<---onSubscribe\n");
                    }

                    @Override
                    public void onNext(String s) {
                        mRxOperatorsText.append(s + "<---onNext\n");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRxOperatorsText.append(e.toString() + "<---onError\n");

                    }

                    @Override
                    public void onComplete() {
                        mRxOperatorsText.append("<---onComplete\n");
                    }
                });
    }
}
