package workstation.zjyk.workstation.ui.present;

import java.util.Map;

import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.modle.request.WSTaskRequest;
import workstation.zjyk.workstation.ui.views.WSRecvierMaterailView;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSReciverMaterailPresent extends WSReciverBasePresent<WSRecvierMaterailView> {

    private final WSTaskRequest mTaskRequest;

    public WSReciverMaterailPresent() {
        mTaskRequest = new WSTaskRequest();
    }


    public void reciverMaterail(Map<String, String> params) {
        //都用接收物料和在制品的这个接口
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
    public WSTaskRequest getRequest() {
        return mTaskRequest;
    }

}
