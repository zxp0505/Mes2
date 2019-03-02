package workstation.zjyk.line.modle.net;

import workstation.zjyk.line.modle.bean.Wrap;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.util.URLBuilder;

import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by zjgz on 2017/11/7.
 */

public class FeedRequest {
    public Subscription getBarCodeResult(BaseView baseView, Map<String, String> params, final RxDataCallBack<Wrap> callBack) {
        return ApiManager.getInstance().get(URLBuilder.GET_WRAP_INFO, params, new ChildHttpResultSubscriber<Wrap>(baseView, Wrap.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail();
                baseView.hideLoadingDialog();
            }

            @Override
            public void _onSuccess(Wrap wrap) {
                callBack.onSucess(wrap);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

        });
    }

    public Subscription requestReciverRecord(BaseView baseView, Map<String, String> params, final RxDataCallBack<List<Wrap>> callBack, final boolean isShowDialog) {
        return ApiManager.getInstance().get(URLBuilder.GET_RECIVER_RECORD, params, new ChildHttpResultSubscriber<List<Wrap>>(baseView, Wrap.class) {
            @Override
            public void _onSuccess(List<Wrap> wraps) {
                callBack.onSucess(wraps);
                if(isShowDialog){
                    baseView.hideLoadingDialog();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if(isShowDialog){
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail();
                if(isShowDialog){
                    baseView.hideLoadingDialog();
                }
            }
        });

    }
}
