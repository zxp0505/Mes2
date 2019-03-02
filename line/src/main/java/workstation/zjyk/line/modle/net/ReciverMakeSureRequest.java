package workstation.zjyk.line.modle.net;

import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.util.URLBuilder;

import java.util.Map;

import rx.Subscription;

/**
 * Created by zjgz on 2017/11/7.
 */

public class ReciverMakeSureRequest {
    //确认收料
    public Subscription makeSureReciver(Map<String, String> params, BaseView baseView, final RxDataCallBack<String> callBack) {
        return ApiManager.getInstance().get(URLBuilder.GET_RECIVER_MAKESURE, params, new ChildHttpResultSubscriber<String>(baseView, null) {
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
                callBack.onFail();
                baseView.hideLoadingDialog();
            }
        });
    }
}
