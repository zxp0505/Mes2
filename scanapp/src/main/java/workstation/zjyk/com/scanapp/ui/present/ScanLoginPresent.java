package workstation.zjyk.com.scanapp.ui.present;

import java.util.List;
import java.util.Map;

import workstation.zjyk.com.scanapp.modle.bean.ScanPersonInfo;
import workstation.zjyk.com.scanapp.modle.bean.ScanTrayInfoVo;
import workstation.zjyk.com.scanapp.modle.callback.ScanRxDataCallBack;
import workstation.zjyk.com.scanapp.modle.request.MainRequest;
import workstation.zjyk.com.scanapp.ui.views.ScanLoginView;
import workstation.zjyk.com.scanapp.ui.views.ScanMainView;

/**
 * Created by zjgz on 2018/1/20.
 */

public class ScanLoginPresent extends ScanRxPresent<ScanLoginView> {

    private final MainRequest mainRequest;

    public ScanLoginPresent() {
        mainRequest = new MainRequest();
    }

    public void requestLogin(Map<String, String> params) {
        mainRequest.requestLogin(params, getView(), new ScanRxDataCallBack<ScanPersonInfo>() {
            @Override
            public void onSucess(ScanPersonInfo s) {
                if (getView() != null) {
                    getView().loginResult(s);
                }

            }

            @Override
            public void onFail(String message, Throwable throwable) {
                if (getView() != null) {
                    getView().loginResult(null);
                }
            }
        }, true);
    }

    public void requestWarnLogin(Map<String, String> params) {
        mainRequest.requestWarnLogin(params, getView(), new ScanRxDataCallBack<ScanPersonInfo>() {
            @Override
            public void onSucess(ScanPersonInfo s) {
                if (getView() != null) {
                    getView().loginResult(s);
                }

            }

            @Override
            public void onFail(String message, Throwable throwable) {
                if (getView() != null) {
                    getView().loginResult(null);
                }
            }
        }, true);
    }

}
