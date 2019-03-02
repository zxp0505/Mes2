package workstation.zjyk.line.ui.present;

import java.util.HashMap;
import java.util.Map;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.request.DeliveryRequest;
import workstation.zjyk.line.modle.request.MainRequest;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.ui.views.DeliveryExceptionView;
import workstation.zjyk.line.ui.views.DeliveryView;

/**
 * Created by zjgz on 2017/11/14.
 */

public class DeliveryExceptionPresent extends RxPresent<BaseView> {

    private final DeliveryRequest mDeliveryRequest;

    public DeliveryExceptionPresent(BaseView baseView) {
        attachView(baseView);
        mDeliveryRequest = new DeliveryRequest();
    }

    @Deprecated //暂时弃用 由于业务改变为 先打印 后扫清单码 投递
    public void requestDelivery(Map<String, String> params) {
        mDeliveryRequest.requestDelivery(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().showNetData(s);
                }

            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showNetData(null);
                }
            }
        });
    }

    /**
     * 打印异常信息
     *
     * @param params
     */
    public void printDeliveryExceptionInfo(final Map<String, String> params) {
        mDeliveryRequest.printExceptionInfo(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                ToastUtil.showInfoCenterShort("打印成功");
                if (getView() != null) {
                    ((DeliveryExceptionView) getView()).showPrintResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((DeliveryExceptionView) getView()).showPrintResult(false);
                }
            }
        });

    }


    /**
     * 扫描控制清单 进行投递
     */
    public void scanDeliveryException(Map<String, String> params) {
        mDeliveryRequest.scanDeliveryException(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                ToastUtil.showInfoCenterShort("投递成功");
                if (getView() != null) {
                    ((DeliveryExceptionView) getView()).showDeliveryResult(true);
                }
            }

            @Override
            public void onFail() {
//ToastUtil.showCenterShort("投递失败",true);
                if (getView() != null) {
                    ((DeliveryExceptionView) getView()).showDeliveryResult(false);
                }
            }
        });
    }

}
