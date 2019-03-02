package workstation.zjyk.workstation.ui.present;

import java.util.List;
import java.util.Map;

import workstation.zjyk.workstation.modle.bean.WSDailyWorkHistory;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;
import workstation.zjyk.workstation.modle.bean.WSTaskMainInfo;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.modle.request.WSTaskRequest;
import workstation.zjyk.workstation.ui.views.WSReportWorkView;
import workstation.zjyk.workstation.ui.views.WSTaskView;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSReportWorkPresent extends WSRxPresent<WSReportWorkView> {

    private final WSTaskRequest mTaskRequest;

    public WSReportWorkPresent() {
        mTaskRequest = new WSTaskRequest();
    }

    public void requesStepList(Map<String, String> params) {
        mTaskRequest.requesStepList(params, getView(), new WSRxDataCallBack<List<WSProcedureStep>>() {
            @Override
            public void onSucess(List<WSProcedureStep> wsProcedureSteps) {
                if (getView() != null) {
                    getView().showStepList(wsProcedureSteps);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showStepList(null);
                }
            }
        }, true);
    }

    public void requesStepHistoryList(Map<String, String> params,boolean isShowLoading) {
        mTaskRequest.requesStepHistoryList(params, getView(), new WSRxDataCallBack<List<WSDailyWorkHistory>>() {
            @Override
            public void onSucess(List<WSDailyWorkHistory> wsDailyWorkHistories) {
                if (getView() != null) {
                    getView().showStepHistoryList(wsDailyWorkHistories);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showStepHistoryList(null);
                }
            }
        }, isShowLoading);
    }

    public void requesReportComfirm(Map<String, String> params) {
        mTaskRequest.requesStepConfirm(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String result) {
                if (getView() != null) {
                    getView().showReportWorkResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showReportWorkResult(false);
                }
            }
        }, true,new WSErrorResultClickListner() {
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
