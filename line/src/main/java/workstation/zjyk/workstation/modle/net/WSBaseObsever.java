package workstation.zjyk.workstation.modle.net;

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
import workstation.zjyk.line.modle.bean.BaseResultCommon;
import workstation.zjyk.line.modle.net.ResultEnum;
import workstation.zjyk.line.ui.BaseApplication;
import workstation.zjyk.line.util.dialog.ProgressHUD;

/**
 * Created by zjgz on 2017/12/1.
 */

public abstract class WSBaseObsever<T> implements Observer<BaseResultCommon<T>> {
    private static final String TAG = "CommonBaseObsever";
    protected Type cls;


    private ProgressHUD progressHUD;
    /**
     * 是否显示提示框
     */
    private boolean isShowPressHUD;
    /**
     * 是否返回BaseResult类
     */
    private boolean isBaseResult;
    private Type type;

    public WSBaseObsever() {
        initType();
    }

    public WSBaseObsever(Type cls) {
        this.cls = cls;
        initType();
    }

    public WSBaseObsever(Type cls, boolean isBaseResult) {
        this.cls = cls;
        this.isBaseResult = isBaseResult;
        initType();
    }

    public WSBaseObsever(Type cls, boolean isBaseResult, boolean isShowPressHUD) {
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
        if (!AppUtils.isNetwork(BaseApplication.getInstance())) {
            _onError(WSErrorCode.CODE_NET_EXCEPTION, WSErrorCode.STRING_NET_EXCEPTION, null);
        }
    }

    @Override
    public void onNext(BaseResultCommon<T> t) {
        Log.e(TAG, "onNext" + "--currentThread:" + Thread.currentThread().getName());
        LoggerUtil.json(JSON.toJSONString(t));
        if (t == null) {
            _onError(WSErrorCode.CODE_DATA_NULL, WSErrorCode.STRING_DATA_NULL, null);
            return;
        }

        if (t.getResult() == ResultEnum.ERROR) {
            _onError(WSErrorCode.CODE_RESULT_ERROR, t.getMessage(), null);
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
                _onError(WSErrorCode.CODE_DATA_NULL, WSErrorCode.STRING_DATA_NULL, null);
//                _onSuccess((T) "");
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError" + "--currentThread:" + Thread.currentThread().getName());
        e.printStackTrace();
        //在这里做全局的错误处理 http://blog.csdn.net/jdsjlzx/article/details/51882661
        if (!AppUtils.isNetwork(BaseApplication.getInstance())) {
            _onError(WSErrorCode.CODE_NET_EXCEPTION, WSErrorCode.STRING_NET_EXCEPTION, e);
        } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            _onError(WSErrorCode.CODE_NET_SOCKET_TIME_OUT, WSErrorCode.STRING_NET_SOCKET_TIME_OUT, e);
        } else if (e instanceof HttpException) {
            _onError(WSErrorCode.CODE_HTTP_EXCEPTION, WSErrorCode.STRING_HTTP_EXCEPTION, e);
        } else if (e instanceof UnknownHostException) {
            _onError(WSErrorCode.CODE_HTTP_NOTFOUND, WSErrorCode.STRING_UN_CONNET_NET, e);
        } else {
            _onError(WSErrorCode.CODE_UNKNOWN_EXCEPTION, WSErrorCode.STRING_UNKNOWN_EXCEPTION, e);
        }
    }

    @Override
    public void onComplete() {
        Log.e(TAG, "onComplete" + "--currentThread:" + Thread.currentThread().getName());
    }

    public abstract void _onError(int code, String error, Throwable e);

    public abstract void _showLoadingDialog(String message);

    public abstract void _onSuccess(T t);

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
