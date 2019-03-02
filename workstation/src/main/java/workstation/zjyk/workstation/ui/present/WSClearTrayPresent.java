package workstation.zjyk.workstation.ui.present;

import java.util.List;
import java.util.Map;

import workstation.zjyk.workstation.modle.bean.WSProcedureStep;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.request.WSTaskRequest;
import workstation.zjyk.workstation.ui.views.WSClearTrayView;
import workstation.zjyk.workstation.ui.views.WSReportWorkView;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSClearTrayPresent extends WSRxPresent<WSClearTrayView> {

    private final WSTaskRequest mTaskRequest;

    public WSClearTrayPresent() {
        mTaskRequest = new WSTaskRequest();
    }

    public void requesBindTray(Map<String, String> params) {
        mTaskRequest.requesBindTray(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().bindTrayResult(s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().bindTrayResult("");
                }
            }
        }, true);
    }

    public void requesClearTray(Map<String, String> params) {
        mTaskRequest.requesClearTray(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().clearTrayResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().clearTrayResult(false);
                }
            }
        }, true);
    }


}
