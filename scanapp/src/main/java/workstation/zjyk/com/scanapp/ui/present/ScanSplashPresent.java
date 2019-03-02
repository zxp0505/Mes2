package workstation.zjyk.com.scanapp.ui.present;

import java.util.Map;

import workstation.zjyk.com.scanapp.modle.bean.ScanPersonInfo;
import workstation.zjyk.com.scanapp.modle.bean.ScanTrayInfoVo;
import workstation.zjyk.com.scanapp.modle.callback.ScanRxDataCallBack;
import workstation.zjyk.com.scanapp.modle.request.MainRequest;
import workstation.zjyk.com.scanapp.ui.views.ScanLoginView;
import workstation.zjyk.com.scanapp.ui.views.ScanSplashView;

/**
 * Created by zjgz on 2018/1/20.
 */

public class ScanSplashPresent extends ScanRxPresent<ScanSplashView> {

    private final MainRequest mainRequest;

    public ScanSplashPresent() {
        mainRequest = new MainRequest();
    }

    public void testServer(Map<String, String> params) {
        mainRequest.testServer(params, getView(), new ScanRxDataCallBack<ScanTrayInfoVo>() {
            @Override
            public void onSucess(ScanTrayInfoVo s) {
                if (getView() != null) {
                    getView().showTestServerResult(true);
                }

            }

            @Override
            public void onFail(String message, Throwable throwable) {
                if (getView() != null) {
                    getView().showTestServerResult(false);
                }
            }
        }, false);
    }

}
