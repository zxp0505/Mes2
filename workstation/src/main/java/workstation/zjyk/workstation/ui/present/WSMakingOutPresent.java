package workstation.zjyk.workstation.ui.present;

import java.util.List;
import java.util.Map;

import workstation.zjyk.workstation.modle.bean.WSInputInfo;
import workstation.zjyk.workstation.modle.bean.WSMaterialTray;
import workstation.zjyk.workstation.modle.bean.WSTaskMainInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskOtherInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskProductCheckTray;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSWipHistory;
import workstation.zjyk.workstation.modle.bean.WSWipTray;
import workstation.zjyk.workstation.modle.bean.WSWorkStationCheckVO;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.modle.request.WSTaskRequest;
import workstation.zjyk.workstation.ui.views.WSMakingOutView;
import workstation.zjyk.workstation.ui.views.WSTaskView;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSMakingOutPresent extends WSRxPresent<WSMakingOutView> {

    private final WSTaskRequest mTaskRequest;

    public WSMakingOutPresent() {
        mTaskRequest = new WSTaskRequest();
    }

    public void requestDeliveryRecord(Map<String, String> params, boolean isShowLoading) {
        mTaskRequest.requestDeliveryRecord(params, getView(), new WSRxDataCallBack<WSWipHistory>() {
            @Override
            public void onSucess(WSWipHistory wsWipHistory) {
                if (getView() != null) {
                    getView().showDeliveryRecord(wsWipHistory);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showDeliveryRecord(null);
                }
            }
        }, isShowLoading);
    }

    public void requestInspectionData(Map<String, String> params, boolean isShowLoading) {
        mTaskRequest.requestInspectionData(params, getView(), new WSRxDataCallBack<List<WSTaskProductCheckTray>>() {
            @Override
            public void onSucess(List<WSTaskProductCheckTray> wsWorkStationCheckVOS) {
                if (getView() != null) {
                    getView().showInspebctionList(wsWorkStationCheckVOS);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showInspebctionList(null);
                }
            }
        }, isShowLoading);
    }

    public void checkInspectionCode(Map<String, String> params, boolean isShowLoading) {
        mTaskRequest.checkInspectionCode(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().checkInspectionResult(true, params.get("code"));
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().checkInspectionResult(false, "");
                }
            }
        }, isShowLoading);
    }

    public void toInspection(Map<String, String> params, boolean isShowLoading) {
        mTaskRequest.toInspection(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().toInspectionResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().toInspectionResult(false);
                }
            }
        }, isShowLoading);
    }

    public void requesBindTray(Map<String, String> params) {
        mTaskRequest.requesBindTray(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().showBindResult(s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showBindResult("");
                }
            }
        }, true);
    }

    public void requesMakingDelivery(Map<String, String> params) {
        mTaskRequest.requesMakingDelivery(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().showDeliveryResult(true, s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showDeliveryResult(false, "");
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

    public void requestPrintDelivery(Map<String, String> params,boolean isClose) {
        mTaskRequest.requestPrintDelivery(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().showDeliveryPrint(true,isClose);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showDeliveryPrint(false,isClose);
                }
            }
        }, true, new WSErrorResultClickListner() {
            @Override
            public void confirm(int code) {
                if (getView() != null) {
                    if(isClose){
                        getView().closeCurrent();
                    }
                }
            }

            @Override
            public void cancle() {

            }
        });
    }

}
