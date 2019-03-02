package workstation.zjyk.workstation.ui.present;

import java.util.List;
import java.util.Map;

import workstation.zjyk.workstation.modle.bean.WSInputWipInfo;
import workstation.zjyk.workstation.modle.bean.WSMaintainReason;
import workstation.zjyk.workstation.modle.bean.WSReturnOrRepair;
import workstation.zjyk.workstation.modle.bean.WSReviewWorkStationVo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationOutVO;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.modle.request.WSTaskRequest;
import workstation.zjyk.workstation.ui.views.WSRepairVew;
import workstation.zjyk.workstation.ui.views.WSReviewWorkView;

/**
 * Created by zjgz on 2018/1/26.
 */

public class WSRepairPresent extends WSRxPresent<WSRepairVew> {

    private WSTaskRequest mTaskRequest;

    public WSRepairPresent() {
        mTaskRequest = new WSTaskRequest();
    }

    public void requestRepairHistory(Map<String, String> params, boolean isShowLoading) {
        mTaskRequest.requestRepairHistory(params, getView(), new WSRxDataCallBack<WSReturnOrRepair>() {
            @Override
            public void onSucess(WSReturnOrRepair wsReturnOrRepair) {
                if (getView() != null) {
                    getView().showRepairHistory(wsReturnOrRepair);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showRepairHistory(null);
                }
            }
        }, isShowLoading);
    }

//    public void requestRework(Map<String, String> params) {
//        mTaskRequest.requestRework(params, getView(), new WSRxDataCallBack<String>() {
//            @Override
//            public void onSucess(String s) {
//                if (getView() != null) {
//                    getView().showRewrokResult(true);
//                }
//            }
//
//            @Override
//            public void onFail() {
//                if (getView() != null) {
//                    getView().showRewrokResult(false);
//                }
//            }
//        }, true, new WSErrorResultClickListner() {
//            @Override
//            public void confirm(int code) {
//                if (getView() != null) {
//                    getView().closeCurrent();
//                }
//            }
//
//            @Override
//            public void cancle() {
//
//            }
//        });
//    }


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

    public void requestReairReasons(Map<String, String> params) {
        mTaskRequest.requestReairReasons(params, getView(), new WSRxDataCallBack<List<WSMaintainReason>>() {
            @Override
            public void onSucess(List<WSMaintainReason> s) {
                if (getView() != null) {
                    getView().showReairReasons(s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showReairReasons(null);
                }
            }
        }, true);
    }

    public void requestRepair(Map<String, String> params) {
        mTaskRequest.requestRepair(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().showRepairResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showRepairResult(false);
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
