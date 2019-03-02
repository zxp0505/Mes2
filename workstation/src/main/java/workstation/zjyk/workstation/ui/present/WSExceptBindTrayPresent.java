package workstation.zjyk.workstation.ui.present;

import java.util.Map;

import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.modle.request.WSTaskRequest;
import workstation.zjyk.workstation.ui.views.WSExcepBindTrayView;
import workstation.zjyk.workstation.ui.views.WSExcepView;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSExceptBindTrayPresent extends WSRxPresent<WSExcepBindTrayView> {

    private final WSTaskRequest mTaskRequest;

    public WSExceptBindTrayPresent() {
        mTaskRequest = new WSTaskRequest();
    }

    public void bindTray(Map<String, String> params) {
        mTaskRequest.requesBindTray(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String result) {
                if (getView() != null) {
                    getView().showScanResult(result);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showScanResult("");
                }
            }
        }, true);
    }

    public void requesExceptDelivery(Map<String, String> params) {
        mTaskRequest.requesExceptDelivery(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String result) {
                if (getView() != null) {
                    getView().showBindTrayResult(true, result);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showBindTrayResult(false, "");
                }
            }
        }, true, new WSErrorResultClickListner() {
            @Override
            public void confirm(int code) {
                if (getView() != null) {
                    getView().closeCurrent();
                }
            }

            @Override
            public void cancle() {

            }
        });
    }

    public void requesExceptPrint(Map<String, String> params) {
        mTaskRequest.requesExceptPrint(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String result) {
                if (getView() != null) {
                    getView().showExceptPrint(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showExceptPrint(false);
                }
            }
        }, true, new WSErrorResultClickListner() {
            @Override
            public void confirm(int code) {
                if (getView() != null) {
                    getView().closeCurrent();
                }
            }

            @Override
            public void cancle() {

            }
        });
    }
}
