package workstation.zjyk.line.modle.net;

import workstation.zjyk.line.modle.bean.LineMaterielStorageManagerVo;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.util.URLBuilder;

import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by zjgz on 2017/11/14.
 */

public class InventoryControlRequest {
    public Subscription requestInventoryDatas(Map<String, String> params, BaseView baseView, final RxDataCallBack<List<LineMaterielStorageManagerVo>> callBack) {
        return ApiManager.getInstance().post(URLBuilder.GET_INVENTORY_LIST, params, new ChildHttpResultSubscriber<List<LineMaterielStorageManagerVo>>(baseView, LineMaterielStorageManagerVo.class) {
            @Override
            public void _onSuccess(List<LineMaterielStorageManagerVo> lineMaterielStorageManagerVos) {
                callBack.onSucess(lineMaterielStorageManagerVos);
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

    public Subscription updataInventoryData(Map<String, String> params, BaseView baseView, final RxDataCallBack<String> callBack) {
        return ApiManager.getInstance().post(URLBuilder.UPDATA_INVENTORY_DATA, params, new ChildHttpResultSubscriber<String>(baseView, null) {
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
}
