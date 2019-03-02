package workstation.zjyk.workstation.modle.net;

import android.support.annotation.NonNull;

import java.lang.reflect.Type;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import workstation.zjyk.workstation.ui.views.WSBaseView;

/**
 * Created by zjgz on 2017/11/5.
 */

public abstract class WSChildHttpResultSubscriber<T> extends WSBaseHttpResultSubscriber<T> {
    WSBaseView baseView;
    WSErrorResultClickListner errorResultClickListner;

    public WSChildHttpResultSubscriber(@NonNull WSBaseView baseView, Type type) {
        this(baseView, type, null);
    }

    public WSChildHttpResultSubscriber(@NonNull WSBaseView baseView, Type type, WSErrorResultClickListner errorResultClickListner) {
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
            case WSErrorCode.CODE_NET_EXCEPTION:
                break;
            case WSErrorCode.CODE_NET_SOCKET_TIME_OUT:
                break;
            case WSErrorCode.CODE_HTTP_EXCEPTION:
                break;
            case WSErrorCode.CODE_UNKNOWN_EXCEPTION:
                break;
            case WSErrorCode.CODE_DATA_NULL:
                break;
            case WSErrorCode.CODE_RESULT_ERROR:
//                baseView.showResultError(error);
                break;
        }
//        ToastUtil.showCenterShort(error,true);
        _onErrorChild(code, error, e);
    }

    protected abstract void _onErrorChild(int code, String error, Throwable e);
}
