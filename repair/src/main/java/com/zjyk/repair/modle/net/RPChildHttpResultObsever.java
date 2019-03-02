package com.zjyk.repair.modle.net;

import android.support.annotation.NonNull;

import java.lang.reflect.Type;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;

import com.zjyk.repair.ui.views.RPBaseView;
import com.zjyk.repair.ui.views.RPBaseView;

/**
 * Created by zjgz on 2017/11/5.
 */

public abstract class RPChildHttpResultObsever<T> extends RPBaseObsever<T> {
    RPBaseView baseView;
    RPErrorResultClickListner errorResultClickListner;

    public RPChildHttpResultObsever(@NonNull RPBaseView baseView, Type type) {
        this(baseView, type, null);
    }

    public RPChildHttpResultObsever(@NonNull RPBaseView baseView, Type type, RPErrorResultClickListner errorResultClickListner) {
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
            case RPErrorCode.CODE_NET_EXCEPTION:
                break;
            case RPErrorCode.CODE_NET_SOCKET_TIME_OUT:
                break;
            case RPErrorCode.CODE_HTTP_EXCEPTION:
                break;
            case RPErrorCode.CODE_UNKNOWN_EXCEPTION:
                break;
            case RPErrorCode.CODE_DATA_NULL:
                break;
            case RPErrorCode.CODE_RESULT_ERROR:
//                baseView.showResultError(error);
                break;
        }
//        ToastUtil.showCenterShort(error,true);
        _onErrorChild(code, error, e);
    }

    protected abstract void _onErrorChild(int code, String error, Throwable e);
}
