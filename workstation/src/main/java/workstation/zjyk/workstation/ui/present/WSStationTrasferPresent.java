package workstation.zjyk.workstation.ui.present;

import java.util.List;
import java.util.Map;

import workstation.zjyk.workstation.modle.bean.WSProcedureStep;
import workstation.zjyk.workstation.modle.bean.WSWorkStationInfo;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.modle.request.WSTaskRequest;
import workstation.zjyk.workstation.ui.views.WSReportWorkView;
import workstation.zjyk.workstation.ui.views.WSStationTrasferView;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSStationTrasferPresent extends WSRxPresent<WSStationTrasferView> {

    private final WSTaskRequest mTaskRequest;

    public WSStationTrasferPresent() {
        mTaskRequest = new WSTaskRequest();
    }

    public void requesStationTrasferList(Map<String, String> params) {
        mTaskRequest.requesStationTrasferList(params, getView(), new WSRxDataCallBack<List<WSWorkStationInfo>>() {
            @Override
            public void onSucess(List<WSWorkStationInfo> wsWorkStationInfos) {
                if (getView() != null) {
                    getView().showStationList(wsWorkStationInfos);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showStationList(null);
                }
            }
        }, true);
    }

    public void confirmStationTrasfer(Map<String, String> params) {
        mTaskRequest.confirmStationTrasfer(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().showStationTrasferResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showStationTrasferResult(false);
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
