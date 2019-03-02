package workstation.zjyk.workstation.ui.present;

import java.util.Map;

import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.modle.request.WSTaskRequest;
import workstation.zjyk.workstation.ui.views.WSRecvierMakingView;
import workstation.zjyk.workstation.ui.views.WSRecvierMaterailView;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSReciverMakingPresent extends WSReciverBasePresent<WSRecvierMakingView> {

    private final WSTaskRequest mTaskRequest;

    public WSReciverMakingPresent() {
        mTaskRequest = new WSTaskRequest();
    }

    public void reciverMaking(Map<String, String> params) {
        mTaskRequest.reciverMaterailAndMaking(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().reciverResult(true,s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().reciverResult(false,"");
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


    @Override
    WSTaskRequest getRequest() {
        return mTaskRequest;
    }
}
