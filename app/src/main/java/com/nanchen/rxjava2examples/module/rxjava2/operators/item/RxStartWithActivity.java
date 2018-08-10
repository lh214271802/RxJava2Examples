package com.nanchen.rxjava2examples.module.rxjava2.operators.item;

import com.nanchen.rxjava2examples.module.rxjava2.operators.item.RxOperatorBaseActivity;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * 被观察者的数据流前再增加一点同类型的数据
 *
 * @author LiaoHui
 * @date 2018/8/6
 * @desc
 */
public class RxStartWithActivity extends RxOperatorBaseActivity {
    private static final String TAG = "RxStartWithActivity";

    @Override
    protected String getSubTitle() {
        return "startWith";
    }

    @Override
    protected void doSomething() {
        Observable.just(3, 4, 5).startWith(1)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        mRxOperatorsText.append("--->" + integer);
                    }
                });
    }
}
