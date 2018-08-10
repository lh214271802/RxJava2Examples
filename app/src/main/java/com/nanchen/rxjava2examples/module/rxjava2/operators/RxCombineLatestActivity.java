package com.nanchen.rxjava2examples.module.rxjava2.operators;

import android.util.Log;

import com.nanchen.rxjava2examples.module.rxjava2.operators.item.RxOperatorBaseActivity;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * 把第一个被观察者最新的数据，和另外的观察者相连
 * @author LiaoHui
 * @date 2018/8/6
 * @desc
 */
public class RxCombineLatestActivity extends RxOperatorBaseActivity {
    @Override
    protected String getSubTitle() {
        return "CombineLatest";
    }

    @Override
    protected void doSomething() {
        Observable<Integer> obs1 = Observable.just(1,2,3,4,5,6,7,8,9);
        Observable<String> obs2 = Observable.just("a","b","b");
        Observable.combineLatest(obs1, obs2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return "-->"+integer+s+"\n";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mRxOperatorsText.append(s);
            }
        });
    }
}
