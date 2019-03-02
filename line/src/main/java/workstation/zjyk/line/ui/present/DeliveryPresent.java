package workstation.zjyk.line.ui.present;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.request.DeliveryRequest;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.ui.views.DeliveryView;

import java.util.Map;

/**
 * Created by zjgz on 2017/11/14.
 */

public class DeliveryPresent extends RxPresent<BaseView> {

    private final DeliveryRequest mDeliveryRequest;

    public DeliveryPresent(BaseView baseView) {
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
     * 打印投递
     *
     * @param params
     */
    public void printDeliveryInfo(final Map<String, String> params) {
        mDeliveryRequest.printDeliveryInfo(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                ToastUtil.showInfoCenterShort("打印成功");
                if (getView() != null) {
                    ((DeliveryView) getView()).showPrintResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((DeliveryView) getView()).showPrintResult(false);
                }
            }
        });
    }

    /**
     * 扫描控制清单 进行投递
     */
    public void scanDelivery(Map<String, String> params) {
        mDeliveryRequest.scanDelivery(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                ToastUtil.showInfoCenterShort("投递成功");
                if (getView() != null) {
                    ((DeliveryView) getView()).showDeliveryResult(true);
                }
            }

            @Override
            public void onFail() {
//ToastUtil.showCenterShort("投递失败",true);
                if (getView() != null) {
                    ((DeliveryView) getView()).showDeliveryResult(false);
                }
            }
        });
    }

}
