package workstation.zjyk.line.ui.present;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import workstation.zjyk.line.modle.bean.TrayVo;
import workstation.zjyk.line.modle.bean.Wrap;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.request.DeliveryListRequest;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.ui.views.DeliveryListView;
import workstation.zjyk.line.ui.views.DeliveryView;

/**
 * Created by zjgz on 2017/12/4.
 */

public class DeliveryListPresent extends RxPresent<DeliveryView> {

    private final DeliveryListRequest mDeliveryListRequest;

    public DeliveryListPresent(DeliveryListView deliveryView) {
        attachView(deliveryView);
        mDeliveryListRequest = new DeliveryListRequest();
    }

    /**
     * 请求未投递托盘列表
     */
    public void requestDeliveryList(int pageNumber, int pageSize, final boolean isShowDialog) {
        Map<String, String> map = new HashMap<>();
        map.put("pageSize", pageSize + "");
        map.put("pageNumber", pageNumber + "");
        mDeliveryListRequest.requestDeliveryList(getView(), map, new RxDataCallBack<List<TrayVo>>() {
            @Override
            public void onSucess(List<TrayVo> trayVos) {
                if (getView() != null) {
                    if (pageNumber == 1) {
                        ((DeliveryListView) getView()).showFirstData(trayVos);
                    } else {
                        ((DeliveryListView) getView()).loadMoreData(trayVos);
                    }
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((DeliveryListView) getView()).loadError();
                }
            }
        },isShowDialog);
    }

    /**
     * 请求托盘详情
     */
    public void requestTrayDetail(Map<String, String> map) {
        mDeliveryListRequest.requestTrayInfo(getView(), map, new RxDataCallBack<Wrap>() {
            @Override
            public void onSucess(Wrap wrap) {
                if (getView() != null) {
                    ((DeliveryListView) getView()).showTrayInfo(wrap);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((DeliveryListView) getView()).showTrayInfo(null);
                }
            }
        });
    }
}
