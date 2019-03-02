package workstation.zjyk.workstation.ui.present;

import java.util.Map;

import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.request.WSTaskRequest;
import workstation.zjyk.workstation.ui.views.WSBomDetailView;
import workstation.zjyk.workstation.ui.views.WSExcepView;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSBomDetailPresent extends WSRxPresent<WSBomDetailView> {

    private final WSTaskRequest mTaskRequest;

    public WSBomDetailPresent() {
        mTaskRequest = new WSTaskRequest();
    }

    public void requestPrintBom(Map<String, String> params) {
        mTaskRequest.requestPrintBom(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String result) {
                if (getView() != null) {
                    getView().showPrintResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showPrintResult(false);
                }
            }
        }, true);
    }

    public void commitFollowData(Map<String, String> params) {
        mTaskRequest.commitFollowData(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String result) {
                if (getView() != null) {
                    getView().showUpdateFollow(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showUpdateFollow(false);
                }
            }
        }, true);
    }


}
