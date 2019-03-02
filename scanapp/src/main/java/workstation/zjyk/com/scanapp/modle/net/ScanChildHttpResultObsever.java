package workstation.zjyk.com.scanapp.modle.net;

import android.support.annotation.NonNull;

import java.lang.reflect.Type;

import workstation.zjyk.com.scanapp.modle.bean.ScanErrorBean;
import workstation.zjyk.com.scanapp.ui.views.ScanBaseView;

/**
 * Created by zjgz on 2017/11/5.
 */

public abstract class ScanChildHttpResultObsever<T> extends ScanBaseObsever<T> {
    ScanBaseView baseView;
    ScanErrorResultClickListner errorResultClickListner;

    public ScanChildHttpResultObsever(@NonNull ScanBaseView baseView, Type type) {
        this(baseView, type, null);
    }

    public ScanChildHttpResultObsever(@NonNull ScanBaseView baseView, Type type, ScanErrorResultClickListner errorResultClickListner) {
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
    public void _onError(ScanErrorBean errorBean) {
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
