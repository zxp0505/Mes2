package workstation.zjyk.workstation.ui.present;

import java.util.List;
import java.util.Map;

import workstation.zjyk.workstation.modle.bean.WSInputWipInfo;
import workstation.zjyk.workstation.modle.bean.WSReturnOrRepair;
import workstation.zjyk.workstation.modle.bean.WSReviewWorkStationVo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationOutVO;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.modle.request.WSTaskRequest;
import workstation.zjyk.workstation.ui.views.WSReviewWorkView;

/**
 * Created by zjgz on 2018/1/26.
 */

public class WSReviewWrokPresent extends WSRxPresent<WSReviewWorkView> {

    private WSTaskRequest mTaskRequest;

    public WSReviewWrokPresent() {
        mTaskRequest = new WSTaskRequest();
    }

    public void requestReviewWorkStation(Map<String, String> params, WSInputWipInfo currentBean) {
        mTaskRequest.requestReviewWorkStation(params, getView(), new WSRxDataCallBack<List<WSReviewWorkStationVo>>() {
            @Override
            public void onSucess(List<WSReviewWorkStationVo> wsReviewWorkStationVos) {
                if (getView() != null) {
                    getView().showRewrokStations(wsReviewWorkStationVos, currentBean);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showRewrokStations(null, currentBean);
                }
            }
        }, true);
    }

    public void requestRework(Map<String, String> params) {
        mTaskRequest.requestRework(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().showRewrokResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showRewrokResult(false);
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

    public void reviewBindTray(Map<String, String> params, WSWorkStationOutVO wsWorkStationOutVO) {
        mTaskRequest.requesBindTray(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String result) {
                if (getView() != null) {
                    getView().showReviewBindTrayResult(true, wsWorkStationOutVO);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showReviewBindTrayResult(false, wsWorkStationOutVO);
                }
            }
        }, true);
    }

    public void requestReviewHistory(Map<String, String> params,boolean isShowLoading) {
        mTaskRequest.requestReviewHistory(params, getView(), new WSRxDataCallBack<WSReturnOrRepair>() {
            @Override
            public void onSucess(WSReturnOrRepair wsReturnOrRepair) {
                if (getView() != null) {
                    getView().showReviewWorkHistory(wsReturnOrRepair);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showReviewWorkHistory(null);
                }
            }
        }, isShowLoading);
    }
}
