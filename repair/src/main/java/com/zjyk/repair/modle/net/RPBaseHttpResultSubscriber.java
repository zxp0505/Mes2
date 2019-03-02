package com.zjyk.repair.modle.net;


import com.alibaba.fastjson.JSON;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppUtils;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import com.zjyk.repair.modle.bean.ResultEnum;
import com.zjyk.repair.modle.bean.RPBaseResultCommon;
import com.zjyk.repair.ui.application.RPBaseApplication;
import com.zjyk.repair.util.dialog.RPProgressHUD;

/**
 * 异常处理
 * 进度框处理类
 * Created by hanxue on 17/7/11.
 */

public abstract class RPBaseHttpResultSubscriber<T> extends Subscriber<RPBaseResultCommon<T>> {
    /**
     * json解析类
     */
    protected Type cls;


    private RPProgressHUD progressHUD;
    /**
     * 是否显示提示框
     */
    private boolean isShowPressHUD;
    /**
     * 是否返回BaseResult类
     */
    private boolean isBaseResult;
    private Type type;

    public RPBaseHttpResultSubscriber() {
        initType();
    }

    public RPBaseHttpResultSubscriber(Type cls) {
        this.cls = cls;
        initType();
    }

    public RPBaseHttpResultSubscriber(Type cls, boolean isBaseResult) {
        this.cls = cls;
        this.isBaseResult = isBaseResult;
        initType();
    }

    public RPBaseHttpResultSubscriber(Type cls, boolean isBaseResult, boolean isShowPressHUD) {
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
        if (!AppUtils.isNetwork(RPBaseApplication.getInstance())) {
            if (!isUnsubscribed()) {
                unsubscribe();
            }
            _onError(RPErrorCode.CODE_NET_EXCEPTION, RPErrorCode.STRING_NET_EXCEPTION, null);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //在这里做全局的错误处理
        if (!AppUtils.isNetwork(RPBaseApplication.getInstance())) {
            _onError(RPErrorCode.CODE_NET_EXCEPTION, RPErrorCode.STRING_NET_EXCEPTION, e);
        } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            _onError(RPErrorCode.CODE_NET_SOCKET_TIME_OUT, RPErrorCode.STRING_NET_SOCKET_TIME_OUT, e);
        } else if (e instanceof HttpException) {
            _onError(RPErrorCode.CODE_HTTP_EXCEPTION, RPErrorCode.STRING_HTTP_EXCEPTION, e);
        } else {
            _onError(RPErrorCode.CODE_UNKNOWN_EXCEPTION, RPErrorCode.STRING_UNKNOWN_EXCEPTION, e);
        }
    }

    @Override
    public void onNext(RPBaseResultCommon<T> t) {
        LoggerUtil.json(JSON.toJSONString(t));
        if (t == null) {
            _onError(RPErrorCode.CODE_DATA_NULL, RPErrorCode.STRING_DATA_NULL, null);
            return;
        }

        if (t.getResult() == ResultEnum.ERROR) {
            _onError(RPErrorCode.CODE_RESULT_ERROR, t.getMessage(), null);
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
