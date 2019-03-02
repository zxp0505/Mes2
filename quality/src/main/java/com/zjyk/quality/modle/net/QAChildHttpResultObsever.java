package com.zjyk.quality.modle.net;

import android.support.annotation.NonNull;

import com.zjyk.quality.ui.views.QABaseView;

import java.lang.reflect.Type;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;

/**
 * Created by zjgz on 2017/11/5.
 */

public abstract class QAChildHttpResultObsever<T> extends QABaseObsever<T> {
    QABaseView baseView;
    QAErrorResultClickListner errorResultClickListner;

    public QAChildHttpResultObsever(@NonNull QABaseView baseView, Type type) {
        this(baseView, type, null);
    }

    public QAChildHttpResultObsever(@NonNull QABaseView baseView, Type type, QAErrorResultClickListner errorResultClickListner) {
        this.baseView = baseView;
        this.cls = type;
        this.errorResultClickListner = errorResultClickListner;
    }


    @Override
    public void _onError(int code, String error, Throwable e) {
        if (e != null) {
            LoggerUtil.e("code:" + code + "----error:" + error + "---except:" + e.getMessage());
        } else {
            LoggerUtil.e("code:" + code + "----error:" + error);
        }
        baseView.showResultError(code, error, errorResultClickListner);
        switch (code) {
            case QAErrorCode.CODE_NET_EXCEPTION:
                break;
            case QAErrorCode.CODE_NET_SOCKET_TIME_OUT:
                break;
            case QAErrorCode.CODE_HTTP_EXCEPTION:
                break;
            case QAErrorCode.CODE_UNKNOWN_EXCEPTION:
                break;
            case QAErrorCode.CODE_DATA_NULL:
                break;
            case QAErrorCode.CODE_RESULT_ERROR:
//                baseView.showResultError(error);
                break;
        }
//        ToastUtil.showCenterShort(error,true);
        _onErrorChild(code, error, e);
    }

    protected abstract void _onErrorChild(int code, String error, Throwable e);
}
