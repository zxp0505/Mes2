package workstation.zjyk.line.modle.request;

import java.util.List;
import java.util.Map;

import rx.Subscription;
import workstation.zjyk.line.modle.bean.TrayVo;
import workstation.zjyk.line.modle.bean.Wrap;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.net.ApiManager;
import workstation.zjyk.line.modle.net.ChildHttpResultSubscriber;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.util.URLBuilder;

/**
 * Created by zjgz on 2017/11/14.
 */

public class DeliveryListRequest {
    /**
     * 投递
     *
     * @param baseView
     * @param params
     * @param callBack
     * @return
     */
    public Subscription requestTrayInfo(final BaseView baseView, Map<String, String> params, final RxDataCallBack<Wrap> callBack) {
        return ApiManager.getInstance().get(URLBuilder.GET_TRAY_INFO, params, new ChildHttpResultSubscriber<Wrap>(baseView, Wrap.class) {
            @Override
            public void _onSuccess(Wrap workStationRequests) {
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

    public Subscription requestDeliveryList(final BaseView baseView, Map<String, String> params, final RxDataCallBack<List<TrayVo>> callBack, boolean isShowDialog) {
        return ApiManager.getInstance().get(URLBuilder.GET_DELIVERY_LIST, params, new ChildHttpResultSubscriber<List<TrayVo>>(baseView, TrayVo.class) {
            @Override
            public void _onSuccess(List<TrayVo> datas) {
                callBack.onSucess(datas);
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowDialog) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }
        });
    }

}
