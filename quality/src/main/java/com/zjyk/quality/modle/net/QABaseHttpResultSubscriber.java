package com.zjyk.quality.modle.net;


import com.alibaba.fastjson.JSON;
import com.zjyk.quality.modle.bean.QABaseResultCommon;
import com.zjyk.quality.modle.bean.ResultEnum;
import com.zjyk.quality.ui.application.QABaseApplication;
import com.zjyk.quality.util.dialog.QAProgressHUD;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppUtils;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 异常处理
 * 进度框处理类
 * Created by hanxue on 17/7/11.
 */

public abstract class QABaseHttpResultSubscriber<T> extends Subscriber<QABaseResultCommon<T>> {
    /**
     * json解析类
     */
    protected Type cls;


    private QAProgressHUD progressHUD;
    /**
     * 是否显示提示框
     */
    private boolean isShowPressHUD;
    /**
     * 是否返回BaseResult类
     */
    private boolean isBaseResult;
    private Type type;

    public QABaseHttpResultSubscriber() {
        initType();
    }

    public QABaseHttpResultSubscriber(Type cls) {
        this.cls = cls;
        initType();
    }

    public QABaseHttpResultSubscriber(Type cls, boolean isBaseResult) {
        this.cls = cls;
        this.isBaseResult = isBaseResult;
        initType();
    }

    public QABaseHttpResultSubscriber(Type cls, boolean isBaseResult, boolean isShowPressHUD) {
        this.cls = cls;
        this.isBaseResult = isBaseResult;
        this.isShowPressHUD = isShowPressHUD;
        initType();
    }

    private void initType() {
        type = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }


    @Override
    public void onCompleted() {
        if (!isUnsubscribed()) {
            unsubscribe();
        }
//        if (isShowPressHUD) {
//            if (progressHUD != null) {
//                progressHUD.dismiss();
//            }
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!AppUtils.isNetwork(QABaseApplication.getInstance())) {
            if (!isUnsubscribed()) {
                unsubscribe();
            }
            _onError(QAErrorCode.CODE_NET_EXCEPTION, QAErrorCode.STRING_NET_EXCEPTION, null);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //在这里做全局的错误处理
        if (!AppUtils.isNetwork(QABaseApplication.getInstance())) {
            _onError(QAErrorCode.CODE_NET_EXCEPTION, QAErrorCode.STRING_NET_EXCEPTION, e);
        } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            _onError(QAErrorCode.CODE_NET_SOCKET_TIME_OUT, QAErrorCode.STRING_NET_SOCKET_TIME_OUT, e);
        } else if (e instanceof HttpException) {
            _onError(QAErrorCode.CODE_HTTP_EXCEPTION, QAErrorCode.STRING_HTTP_EXCEPTION, e);
        } else {
            _onError(QAErrorCode.CODE_UNKNOWN_EXCEPTION, QAErrorCode.STRING_UNKNOWN_EXCEPTION, e);
        }
    }

    @Override
    public void onNext(QABaseResultCommon<T> t) {
        LoggerUtil.json(JSON.toJSONString(t));
        if (t == null) {
            _onError(QAErrorCode.CODE_DATA_NULL, QAErrorCode.STRING_DATA_NULL, null);
            return;
        }

        if (t.getResult() == ResultEnum.ERROR) {
            _onError(QAErrorCode.CODE_RESULT_ERROR, t.getMessage(), null);
            return;
        }

        if (t.getResult() == ResultEnum.OK) {
            if (t.getData() != null) {
                T data = createData((String) t.getData());
                if (data != null) {
                    _onSuccess(data);
                }
            } else {
                //data没有数据
                _onSuccess((T)"");
            }
        }


    }

    public abstract void _onSuccess(T t);

//    public void _onSuccess(BaseResult baseResult) {
//
//    }

    public void _onSuccess(String messageCode, String message, T o) {

    }

    public abstract void _onError(int code, String error, Throwable e);

    public abstract void _showLoadingDialog(String message);

    private T createData(String data) {
        if (data.startsWith("{") && cls != null) {
            T data_object = (T) JSON.parseObject(data, (Class<Object>) cls);
            return data_object;

        } else if (data.startsWith("[") && cls != null) {
            T data_array = (T) JSON.parseArray(data, (Class<Object>) cls);
            return data_array;

        } else {
            return (T) data;
        }
    }

}
