package com.zjyk.quality.modle.callback;

/**
 * Created by zjgz on 2017/11/5.
 */

public interface QARxDataCallBack<T> {
    void onSucess(T t);

    void onFail();

}
