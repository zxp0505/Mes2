package workstation.zjyk.line.modle.net;


import com.alibaba.fastjson.JSON;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import rx.exceptions.OnErrorFailedException;
import workstation.zjyk.line.modle.bean.BaseResult;
import workstation.zjyk.line.modle.bean.BaseResultCommon;
import workstation.zjyk.line.ui.BaseApplication;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppUtils;
import workstation.zjyk.line.util.dialog.ProgressHUD;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 异常处理
 * 进度框处理类
 * Created by hanxue on 17/7/11.
 */

public abstract class BaseHttpResultSubscriber<T> extends Subscriber<BaseResultCommon<T>> {
    /**
     * json解析类
     */
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

    public BaseHttpResultSubscriber() {
        initType();
    }

    public BaseHttpResultSubscriber(Type cls) {
        this.cls = cls;
        initType();
    }

    public BaseHttpResultSubscriber(Type cls, boolean isBaseResult) {
        this.cls = cls;
        this.isBaseResult = isBaseResult;
        initType();
    }

    public BaseHttpResultSubscriber(Type cls, boolean isBaseResult, boolean isShowPressHUD) {
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
        if (!AppUtils.isNetwork(BaseApplication.getInstance())) {
            if (!isUnsubscribed()) {
                unsubscribe();
            }
            _onError(ErrorCode.CODE_NET_EXCEPTION, ErrorCode.STRING_NET_EXCEPTION, null);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof OnErrorFailedException) {
//            ToastUtil.showCenterShort("这是个坑", false);
//https://github.com/ReactiveX/RxJava/issues/5508
        } else {
            e.printStackTrace();
            //在这里做全局的错误处理
            if (!AppUtils.isNetwork(BaseApplication.getInstance())) {
                _onError(ErrorCode.CODE_NET_EXCEPTION, ErrorCode.STRING_NET_EXCEPTION, e);
            } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
                _onError(ErrorCode.CODE_NET_SOCKET_TIME_OUT, ErrorCode.STRING_NET_SOCKET_TIME_OUT, e);
            } else if (e instanceof HttpException) {
                _onError(ErrorCode.CODE_HTTP_EXCEPTION, ErrorCode.STRING_HTTP_EXCEPTION, e);
            } else {
                _onError(ErrorCode.CODE_UNKNOWN_EXCEPTION, ErrorCode.STRING_UNKNOWN_EXCEPTION, e);
            }
        }

    }

    @Override
    public void onNext(BaseResultCommon<T> t) {
        LoggerUtil.json(JSON.toJSONString(t));
        if (t == null) {
            _onError(ErrorCode.CODE_DATA_NULL, ErrorCode.STRING_DATA_NULL, null);
            return;
        }

        if (t.getResult() == ResultEnum.ERROR) {
            _onError(ErrorCode.CODE_RESULT_ERROR, t.getMessage(), null);
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
                _onSuccess((T) "");
            }
        }


    }

    public abstract void _onSuccess(T t);

    public void _onSuccess(BaseResult baseResult) {

    }

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
