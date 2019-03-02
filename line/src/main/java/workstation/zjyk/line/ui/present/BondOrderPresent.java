package workstation.zjyk.line.ui.present;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import workstation.zjyk.line.modle.bean.OrderMaterielVo;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.request.BondRequest;
import workstation.zjyk.line.ui.views.BondOrderView;
import workstation.zjyk.line.util.Constants;

public class BondOrderPresent extends MvpBasePresenter<BondOrderView> {

    private final BondRequest mBondRequest;

    public BondOrderPresent(BondOrderView view) {
        attachView(view);
        mBondRequest = new BondRequest();
    }

    public void requestBondOrderList(int pageNo, int pageSize, int bondType, String searchDate, String searchText, boolean isShowLoad) {
        Map<String, String> params = new HashMap<>();
        params.put("pageNumber", pageNo + "");
        params.put("pageSize", pageSize + "");
        params.put("bondType", bondType + "");
        params.put("searchData", searchDate);
        params.put("search", searchText);
        if (Constants.isReciver) {
            params.put("query", "all");
        }
        mBondRequest.requestBondOrderList(getView(), params, new RxDataCallBack<List<OrderMaterielVo>>() {
            @Override
            public void onSucess(List<OrderMaterielVo> orderMaterielVos) {
                if (getView() != null) {
                    if (pageNo == 1) {
                        getView().showFirstData(orderMaterielVos);
                    } else {
                        getView().loadMoreData(orderMaterielVos);
                    }
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().loadError();
                }
            }
        }, isShowLoad);
    }
}
