package workstation.zjyk.workstation.ui.present;

import java.util.HashMap;
import java.util.Map;

import workstation.zjyk.workstation.modle.bean.ScanPersonInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationVo;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.request.WSLoginRequest;
import workstation.zjyk.workstation.ui.views.WSInspectLoginView;

/**
 * Created by zjgz on 2018/1/20.
 */

public class WSInspectLoginPresent extends WSRxPresent<WSInspectLoginView> {

    private final WSLoginRequest mainRequest;

    public WSInspectLoginPresent() {
        mainRequest = new WSLoginRequest();
    }
    public void testServer() {
        mainRequest.testServer(new HashMap<String, String>(), getView(), new WSRxDataCallBack<WSWorkStationVo>() {
            @Override
            public void onSucess(WSWorkStationVo workStationVo) {
                if (getView() != null) {
                    getView().showTestServerResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showTestServerResult(false);
                }
            }
        });
    }

    public void requestLogin(Map<String, String> params) {
        mainRequest.requestInspectLogin(params, getView(), new WSRxDataCallBack<ScanPersonInfo>() {
            @Override
            public void onSucess(ScanPersonInfo s) {
                if (getView() != null) {
                    getView().loginResult(s);
                }

            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().loginResult(null);
                }
            }

        }, true);
    }

}
