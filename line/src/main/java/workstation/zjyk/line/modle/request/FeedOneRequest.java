package workstation.zjyk.line.modle.request;

import workstation.zjyk.line.modle.bean.ExceptionOutBean;
import workstation.zjyk.line.modle.bean.WorkStationDistributeMateriel;
import workstation.zjyk.line.modle.bean.WorkStationRequest;
import workstation.zjyk.line.modle.bean.WorkStationRequestDetail;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.net.ApiManager;
import workstation.zjyk.line.modle.net.ChildHttpResultSubscriber;
import workstation.zjyk.line.modle.net.ErrorResultClickListner;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.util.URLBuilder;

import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by zjgz on 2017/11/9.
 */

public class FeedOneRequest {
    /**
     * 获取工位列表
     *
     * @param baseView
     * @param params
     * @param callBack
     */
    public Subscription getStation(final BaseView baseView, Map<String, String> params, final RxDataCallBack<List<WorkStationRequest>> callBack) {
        return ApiManager.getInstance().get(URLBuilder.GET_STATION_LIST, params, new ChildHttpResultSubscriber<List<WorkStationRequest>>(baseView, WorkStationRequest.class) {
            @Override
            public void _onSuccess(List<WorkStationRequest> workStationRequests) {
                callBack.onSucess(workStationRequests);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }
        });
    }

    /**
     * 获取物料详情
     *
     * @param baseView
     * @param params
     * @param callBack
     */
    public Subscription requestMaterailDetail(final BaseView baseView, Map<String, String> params, final RxDataCallBack<List<WorkStationRequestDetail>> callBack) {
        return ApiManager.getInstance().get(URLBuilder.GET_STATION_REQUIR_DETAIL, params, new ChildHttpResultSubscriber<List<WorkStationRequestDetail>>(baseView, WorkStationRequestDetail.class) {
            @Override
            public void _onSuccess(List<WorkStationRequestDetail> workStationRequestDetails) {
                callBack.onSucess(workStationRequestDetails);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }
        });
    }

    /**
     * 获取确认发放物料列表
     */
    public Subscription getInHouseData(final BaseView baseView, Map<String, String> params, final RxDataCallBack<List<WorkStationDistributeMateriel>> callBack) {
        return ApiManager.getInstance().post(URLBuilder.GET_STATION_MATERAIL_LIST, params, new ChildHttpResultSubscriber<List<WorkStationDistributeMateriel>>(baseView, WorkStationDistributeMateriel.class) {
            @Override
            public void _onSuccess(List<WorkStationDistributeMateriel> workStationDistributeMateriels) {
                callBack.onSucess(workStationDistributeMateriels);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }
        });
    }

    /**
     * 绑定托盘请求托盘状态
     *
     * @param baseView
     * @param params
     * @param callBack
     */
    public Subscription requestTrayStatus(final BaseView baseView, Map<String, String> params, final RxDataCallBack<String> callBack) {
        return ApiManager.getInstance().get(URLBuilder.GET_BIND_TRAY_SCAN, params, new ChildHttpResultSubscriber<String>(baseView, null) {
            @Override
            public void _onSuccess(String s) {
                callBack.onSucess(s);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }
        });
    }

    /**
     * queren发料
     *
     * @param params
     */
    public Subscription requestMakeSureFeed(final BaseView baseView, Map<String, String> params, final RxDataCallBack<String> callBack, ErrorResultClickListner errorResultClickListner) {
        return ApiManager.getInstance().post(URLBuilder.MAKE_SURE_ISSUR, params, new ChildHttpResultSubscriber<String>(baseView, null,errorResultClickListner) {
            @Override
            public void _onSuccess(String s) {
                callBack.onSucess(s);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }
        });
    }

    public Subscription inWareRequest(final BaseView baseView, Map<String, String> params, final RxDataCallBack<String> callBack) {
        return ApiManager.getInstance().post(URLBuilder.GET_INWARE, params, new ChildHttpResultSubscriber<String>(baseView, null) {
            @Override
            public void _onSuccess(String s) {
                callBack.onSucess(s);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }
        });
    }

    public Subscription requestTrayExceptionStatus(final BaseView baseView, Map<String, String> params, final RxDataCallBack<String> callBack) {
        return ApiManager.getInstance().get(URLBuilder.GET_EXCEPTION_BIND_TRAY_SCAN, params, new ChildHttpResultSubscriber<String>(baseView, null) {
            @Override
            public void _onSuccess(String s) {
                callBack.onSucess(s);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }
        });
    }

    public Subscription requestMakeSureFeedException(final BaseView baseView, Map<String, String> params, final RxDataCallBack<ExceptionOutBean> callBack, ErrorResultClickListner errorResultClickListner) {
        return ApiManager.getInstance().post(URLBuilder.GET_EXCEPTION_BIND_TRAY, params, new ChildHttpResultSubscriber<ExceptionOutBean>(baseView, ExceptionOutBean.class,errorResultClickListner) {
            @Override
            public void _onSuccess(ExceptionOutBean s) {
                callBack.onSucess(s);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }
        });
    }
}
