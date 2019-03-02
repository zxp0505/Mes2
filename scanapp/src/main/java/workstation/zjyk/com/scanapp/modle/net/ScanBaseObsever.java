package workstation.zjyk.com.scanapp.modle.net;

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
import retrofit2.adapter.rxjava2.HttpException;
import workstation.zjyk.com.scanapp.modle.bean.ScanBaseResultCommon;
import workstation.zjyk.com.scanapp.modle.bean.ScanErrorBean;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ResultEnum;
import workstation.zjyk.com.scanapp.modle.net.ScanErrorCode;
import workstation.zjyk.com.scanapp.ui.application.ScanBaseApplication;

/**
 * Created by zjgz on 2017/12/1.
 */

public abstract class ScanBaseObsever<T> implements Observer<ScanBaseResultCommon<T>> {
    private static final String TAG = "CommonBaseObsever";
    protected Type cls;


    /**
     * 是否显示提示框
     */
    private boolean isShowPressHUD;
    /**
     * 是否返回BaseResult类
     */
    private boolean isBaseResult;
    private Type type;

    public ScanBaseObsever() {
        initType();
    }

    public ScanBaseObsever(Type cls) {
        this.cls = cls;
        initType();
    }

    public ScanBaseObsever(Type cls, boolean isBaseResult) {
        this.cls = cls;
        this.isBaseResult = isBaseResult;
        initType();
    }

    public ScanBaseObsever(Type cls, boolean isBaseResult, boolean isShowPressHUD) {
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
        if (!AppUtils.isNetwork(ScanBaseApplication.getInstance())) {
            _onError(ScanErrorCode.CODE_NET_EXCEPTION, ScanErrorCode.STRING_NET_EXCEPTION, null, "");
        }
    }

    @Override
    public void onNext(ScanBaseResultCommon<T> t) {
        Log.e(TAG, "onNext" + "--currentThread:" + Thread.currentThread().getName());
        LoggerUtil.json(JSON.toJSONString(t));
        if (t == null) {
            _onError(ScanErrorCode.CODE_DATA_NULL, ScanErrorCode.STRING_DATA_NULL, null, "");
            return;
        }

        if (t.getResult() == null || t.getResult() == ResultEnum.ERROR) {
            _onError(ScanErrorCode.CODE_RESULT_ERROR, t.getMessage()!=null?t.getMessage():"未知异常", null, (String) t.getData());
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
                _onError(ScanErrorCode.CODE_DATA_NULL, ScanErrorCode.STRING_DATA_NULL, null, "");
//                _onSuccess((T) "");
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError" + "--currentThread:" + Thread.currentThread().getName());
        e.printStackTrace();
        //在这里做全局的错误处理 http://blog.csdn.net/jdsjlzx/article/details/51882661
        if (!AppUtils.isNetwork(ScanBaseApplication.getInstance())) {
            _onError(ScanErrorCode.CODE_NET_EXCEPTION, ScanErrorCode.STRING_NET_EXCEPTION, e, "");
        } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            _onError(ScanErrorCode.CODE_NET_SOCKET_TIME_OUT, ScanErrorCode.STRING_NET_SOCKET_TIME_OUT, e, "");
        } else if (e instanceof HttpException) {
            _onError(ScanErrorCode.CODE_HTTP_EXCEPTION, ScanErrorCode.STRING_HTTP_EXCEPTION, e, "");
        } else if (e instanceof UnknownHostException) {
            _onError(ScanErrorCode.CODE_HTTP_NOTFOUND, ScanErrorCode.STRING_UN_CONNET_NET, e, "");
        } else {
            _onError(ScanErrorCode.CODE_UNKNOWN_EXCEPTION, ScanErrorCode.STRING_UNKNOWN_EXCEPTION, e, "");
        }
    }

    @Override
    public void onComplete() {
        Log.e(TAG, "onComplete" + "--currentThread:" + Thread.currentThread().getName());
    }

//    public abstract void _onError(int code, String error, Throwable e);

    public abstract void _onError(ScanErrorBean errorBean);

    public abstract void _showLoadingDialog(String message);

    public abstract void _onSuccess(T t);

    public void _onSuccess(T t, int total) {

    }

    public void _onSuccess(ScanBaseResultCommon<T> baseResultCommon) {

    }

    private void _onError(int code, String errorMessage, Throwable e, String dataMessage) {
        ScanErrorBean bean = new ScanErrorBean();
        bean.setErrorCode(code);
        bean.setDataMessage(dataMessage);
        bean.setErrorMessage(errorMessage);
        bean.setThrowable(e);
        _onError(bean);
    }


    private T createData(String data, ScanBaseResultCommon<T> t) {
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
