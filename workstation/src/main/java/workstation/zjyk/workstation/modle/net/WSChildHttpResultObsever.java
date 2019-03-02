package workstation.zjyk.workstation.modle.net;

import android.support.annotation.NonNull;

import java.lang.reflect.Type;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import workstation.zjyk.workstation.modle.bean.WSErrorBean;
import workstation.zjyk.workstation.ui.views.WSBaseView;

/**
 * Created by zjgz on 2017/11/5.
 */

public abstract class WSChildHttpResultObsever<T> extends WSBaseObsever<T> {
    WSBaseView baseView;
    WSErrorResultClickListner errorResultClickListner;

    public WSChildHttpResultObsever(@NonNull WSBaseView baseView, Type type) {
        this(baseView, type, null);
    }

    public WSChildHttpResultObsever(@NonNull WSBaseView baseView, Type type, WSErrorResultClickListner errorResultClickListner) {
        this.baseView = baseView;
        this.cls = type;
        this.errorResultClickListner = errorResultClickListner;
    }


//    @Override
//    public void _onError(int code, String error, Throwable e) {
//        if (e != null) {
//            LoggerUtil.e("code:" + code + "----error:" + error + "---except:" + e.getMessage());
//        } else {
//            LoggerUtil.e("code:" + code + "----error:" + error);
//        }
//        if (baseView != null) {
//            baseView.showResultError(code, error, errorResultClickListner);
//        }
////        ToastUtil.showCenterShort(error,true);
//        _onErrorChild(code, error, e);
//    }

    @Override
    public void _onError(WSErrorBean errorBean) {
        if (errorBean != null) {
            int errorCode = errorBean.getErrorCode();
            String dataMessage = errorBean.getDataMessage();
            Throwable throwable = errorBean.getThrowable();
            String errorMessage = errorBean.getErrorMessage();
            if (baseView != null) {
                baseView.showResultError(errorBean, errorResultClickListner);
            }
            _onErrorChild(errorCode, errorMessage, throwable);
        }
    }

    protected abstract void _onErrorChild(int code, String error, Throwable e);
}
