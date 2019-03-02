package com.zjyk.repair.modle.callback;

/**
 * Created by zjgz on 2017/11/5.
 */

public interface RPRxDataCallBack<T> {
    void onSucess(T t);

    void onFail();

}
