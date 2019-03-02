package com.zjyk.repair.modle.net;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppUtils;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.adapter.rxjava.HttpException;

import com.zjyk.repair.modle.bean.RPErrorBean;
import com.zjyk.repair.modle.bean.ResultEnum;
import com.zjyk.repair.modle.bean.RPBaseResultCommon;
import com.zjyk.repair.ui.application.RPBaseApplication;
import com.zjyk.repair.ui.application.RPBaseApplication;
import com.zjyk.repair.util.dialog.RPProgressHUD;
import com.zjyk.repair.util.dialog.RPProgressHUD;

/**
 * Created by zjgz on 2017/12/1.
 */

public abstract class RPBaseObsever<T> implements Observer<RPBaseResultCommon<T>> {
    private static final String TAG = "CommonBaseObsever";
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

    public RPBaseObsever() {
        initType();
    }

    public RPBaseObsever(Type cls) {
        this.cls = cls;
        initType();
    }

    public RPBaseObsever(Type cls, boolean isBaseResult) {
        this.cls = cls;
        this.isBaseResult = isBaseResult;
        initType();
    }

    public RPBaseObsever(Type cls, boolean isBaseResult, boolean isShowPressHUD) {
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

    public Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        Log.e(TAG, "onSubscribe" + "--currentThread:" + Thread.currentThread().getName());
        _showLoadingDialog("");
        this.disposable = d;
        if (!AppUtils.isNetwork(RPBaseApplication.getInstance())) {
            _onError(RPErrorCode.CODE_NET_EXCEPTION, RPErrorCode.STRING_NET_EXCEPTION, null, "");
        }
    }

    @Override
    public void onNext(RPBaseResultCommon<T> t) {
        Log.e(TAG, "onNext" + "--currentThread:" + Thread.currentThread().getName());
        LoggerUtil.json(JSON.toJSONString(t));
        if (t == null) {
            _onError(RPErrorCode.CODE_DATA_NULL, RPErrorCode.STRING_DATA_NULL, null, "");
            return;
        }

        if (t.getResult() == null || t.getResult() == ResultEnum.ERROR) {
            _onError(RPErrorCode.CODE_RESULT_ERROR, t.getMessage()!=null?t.getMessage():"未知异常", null, (String) t.getData());
            return;
        }

        if (t.getResult() == ResultEnum.OK) {
            if (t.getData() != null) {
                T data = createData((String) t.getData(), t);
                t.setData(data);
                if (data != null) {
                    _onSuccess(data);
                    _onSuccess(data, t.getTotalCount());
                    _onSuccess(t);
                }
            } else {
                //data没有数据
                _onError(RPErrorCode.CODE_DATA_NULL, RPErrorCode.STRING_DATA_NULL, null, "");
//                _onSuccess((T) "");
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError" + "--currentThread:" + Thread.currentThread().getName());
        e.printStackTrace();
        //在这里做全局的错误处理 http://blog.csdn.net/jdsjlzx/article/details/51882661
        if (!AppUtils.isNetwork(RPBaseApplication.getInstance())) {
            _onError(RPErrorCode.CODE_NET_EXCEPTION, RPErrorCode.STRING_NET_EXCEPTION, e, "");
        } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            _onError(RPErrorCode.CODE_NET_SOCKET_TIME_OUT, RPErrorCode.STRING_NET_SOCKET_TIME_OUT, e, "");
        } else if (e instanceof retrofit2.adapter.rxjava2.HttpException) {
            _onError(RPErrorCode.CODE_HTTP_EXCEPTION, RPErrorCode.STRING_HTTP_EXCEPTION, e, "");
        } else if (e instanceof UnknownHostException) {
            _onError(RPErrorCode.CODE_HTTP_NOTFOUND, RPErrorCode.STRING_UN_CONNET_NET, e, "");
        } else {
            _onError(RPErrorCode.CODE_UNKNOWN_EXCEPTION, RPErrorCode.STRING_UNKNOWN_EXCEPTION, e, "");
        }
    }

    @Override
    public void onComplete() {
        Log.e(TAG, "onComplete" + "--currentThread:" + Thread.currentThread().getName());
    }

//    public abstract void _onError(int code, String error, Throwable e);

    public abstract void _onError(RPErrorBean errorBean);

    public abstract void _showLoadingDialog(String message);

    public abstract void _onSuccess(T t);

    public void _onSuccess(T t, int total) {

    }

    public void _onSuccess(RPBaseResultCommon<T> baseResultCommon) {

    }

    private void _onError(int code, String errorMessage, Throwable e, String dataMessage) {
        RPErrorBean bean = new RPErrorBean();
        bean.setErrorCode(code);
        bean.setDataMessage(dataMessage);
        bean.setErrorMessage(errorMessage);
        bean.setThrowable(e);
        _onError(bean);
    }


    private T createData(String data, RPBaseResultCommon<T> t) {
        if (data.startsWith("{") && cls != null) {
            T data_object = (T) JSON.parseObject(data, (Class<Object>) cls);
            t.setDataType(1);
            return data_object;

        } else if (data.startsWith("[") && cls != null) {
            T data_array = null;
            data_array = (T) JSON.parseArray(data, (Class<Object>) cls);
            t.setDataType(2);
            return data_array;

        } else {
            t.setDataType(3);
            return (T) data;
        }


    }

}
