package workstation.zjyk.line.modle.request;

import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.net.ApiManager;
import workstation.zjyk.line.modle.net.ChildHttpResultSubscriber;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.util.URLBuilder;

import java.util.Map;

import rx.Subscription;

/**
 * Created by zjgz on 2017/11/14.
 */

public class DeliveryRequest {
    /**
     * 投递
     *
     * @param baseView
     * @param params
     * @param callBack
     * @return
     */
    public Subscription requestDelivery(final BaseView baseView, Map<String, String> params, final RxDataCallBack<String> callBack) {
        return ApiManager.getInstance().get(URLBuilder.GET_DELIVERY_TRAY, params, new ChildHttpResultSubscriber<String>(baseView, null) {
            @Override
            public void _onSuccess(String workStationRequests) {
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

    public Subscription scanDelivery(final BaseView baseView, Map<String, String> params, final RxDataCallBack<String> callBack) {
        return ApiManager.getInstance().get(URLBuilder.SCAN_DELIVERY, params, new ChildHttpResultSubscriber<String>(baseView, null) {
            @Override
            public void _onSuccess(String workStationRequests) {
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
     * 扫码投递异常
     * @param baseView
     * @param params
     * @param callBack
     * @return
     */
    public Subscription scanDeliveryException(final BaseView baseView, Map<String, String> params, final RxDataCallBack<String> callBack) {
        return ApiManager.getInstance().get(URLBuilder.SCAN_DELIVERY_EXCEPTION, params, new ChildHttpResultSubscriber<String>(baseView, null) {
            @Override
            public void _onSuccess(String workStationRequests) {
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
     * 打印投递托盘信息
     *
     * @param baseView
     * @param params
     * @param callBack
     * @return
     */
    public Subscription printDeliveryInfo(final BaseView baseView, Map<String, String> params, final RxDataCallBack<String> callBack) {
        return ApiManager.getInstance().get(URLBuilder.PRINTER_DELIVERY_TRAY_INFO, params, new ChildHttpResultSubscriber<String>(baseView, null) {
            @Override
            public void _onSuccess(String workStationRequests) {
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
     * 打印异常信息
     * @param baseView
     * @param params
     * @param callBack
     * @return
     */
    public Subscription printExceptionInfo(final BaseView baseView, Map<String, String> params, final RxDataCallBack<String> callBack) {
        return ApiManager.getInstance().get(URLBuilder.PRINTRR_EXCEPTION, params, new ChildHttpResultSubscriber<String>(baseView, null) {
            @Override
            public void _onSuccess(String workStationRequests) {
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
     * 清空托盘
     *
     * @param baseView
     * @param params
     * @param callBack
     * @return
     */
    public Subscription clearTray(final BaseView baseView, Map<String, String> params, final RxDataCallBack<String> callBack) {
        return ApiManager.getInstance().get(URLBuilder.CLEAR_TRAY, params, new ChildHttpResultSubscriber<String>(baseView, null) {
            @Override
            public void _onSuccess(String workStationRequests) {
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
}
