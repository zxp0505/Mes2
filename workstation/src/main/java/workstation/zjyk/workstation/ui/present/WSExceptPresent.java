package workstation.zjyk.workstation.ui.present;

import java.util.List;
import java.util.Map;

import workstation.zjyk.workstation.modle.bean.WSExceptionOutRecordVo;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.modle.request.WSTaskRequest;
import workstation.zjyk.workstation.ui.views.WSClearTrayView;
import workstation.zjyk.workstation.ui.views.WSExcepView;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSExceptPresent extends WSRxPresent<WSExcepView> {

    private final WSTaskRequest mTaskRequest;

    public WSExceptPresent() {
        mTaskRequest = new WSTaskRequest();
    }

    public void requesExceptList(Map<String, String> params) {
        mTaskRequest.requesExceptList(params, getView(), new WSRxDataCallBack<WSTrayLoadInfo>() {
            @Override
            public void onSucess(WSTrayLoadInfo trayLoadInfo) {
                if (getView() != null) {
                    getView().showExceptionInfo(trayLoadInfo);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showExceptionInfo(null);
                }
            }
        }, true);
    }

    public void requesExceptHistoryList(Map<String, String> params) {
        mTaskRequest.requesExceptHistoryList(params, getView(), new WSRxDataCallBack<List<WSExceptionOutRecordVo>>() {
            @Override
            public void onSucess(List<WSExceptionOutRecordVo> trayLoadInfo) {
                if (getView() != null) {
                    getView().showExceptHistoryList(trayLoadInfo);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showExceptHistoryList(null);
                }
            }
        }, true);
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
//                    getView().closeCurrent();
                }
            }

            @Override
            public void cancle() {

            }
        });
    }



}
