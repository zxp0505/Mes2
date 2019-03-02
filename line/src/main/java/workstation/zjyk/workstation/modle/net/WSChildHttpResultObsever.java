package workstation.zjyk.workstation.modle.net;

import android.support.annotation.NonNull;

import java.lang.reflect.Type;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import workstation.zjyk.line.modle.net.ErrorResultClickListner;
import workstation.zjyk.line.ui.views.BaseView;

/**
 * Created by zjgz on 2017/11/5.
 */

public abstract class WSChildHttpResultObsever<T> extends WSBaseObsever<T> {
    BaseView baseView;
    ErrorResultClickListner errorResultClickListner;

    public WSChildHttpResultObsever(@NonNull BaseView baseView, Type type) {
        this(baseView, type, null);
    }

    public WSChildHttpResultObsever(@NonNull BaseView baseView, Type type, ErrorResultClickListner errorResultClickListner) {
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
        if(baseView != null){
            baseView.showResultError(code, error, errorResultClickListner);
        }
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
