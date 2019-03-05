package workstation.zjyk.com.scanapp.ui.present;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import workstation.zjyk.com.scanapp.modle.bean.QualityHandledRecordVO;
import workstation.zjyk.com.scanapp.modle.callback.ScanRxDataCallBack;
import workstation.zjyk.com.scanapp.modle.request.MainRequest;
import workstation.zjyk.com.scanapp.ui.views.ScanCodeView;
import workstation.zjyk.com.scanapp.ui.views.ScanWaitWarnView;

/**
 * Created by zjgz on 2018/1/20.
 */

public class ScanWaitWarnPresent extends ScanRxPresent<ScanWaitWarnView> {

    private final MainRequest mainRequest;

    public ScanWaitWarnPresent() {
        mainRequest = new MainRequest();
    }

    public void pullWarnInfo(Map<String,String> params, boolean isShowLoad) {
        mainRequest.pullWarnInfoRequest(params, getView(), new ScanRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().showWarnInfo(true);
                }

            }

            @Override
            public void onFail(String message, Throwable throwable) {
                if (getView() != null) {
                    getView().showWarnInfo(false);
                }
            }
        }, true);
    }

}
