package workstation.zjyk.com.scanapp.ui.present;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import workstation.zjyk.com.scanapp.modle.bean.QualityHandledRecordVO;
import workstation.zjyk.com.scanapp.modle.bean.ScanPersonInfo;
import workstation.zjyk.com.scanapp.modle.callback.ScanRxDataCallBack;
import workstation.zjyk.com.scanapp.modle.request.MainRequest;
import workstation.zjyk.com.scanapp.ui.views.ScanHistoryView;
import workstation.zjyk.com.scanapp.ui.views.ScanLoginView;

/**
 * Created by zjgz on 2018/1/20.
 */

public class ScanHistoryPresent extends ScanRxPresent<ScanHistoryView> {

    private final MainRequest mainRequest;

    public ScanHistoryPresent() {
        mainRequest = new MainRequest();
    }

    public void requestHistoryList(int pageNo, int pageSize, String searchDate, String searchText, boolean isShowLoad) {
        Map<String, String> params = new HashMap<>();
        params.put("pageNumber", pageNo + "");
        params.put("pageSize", pageSize + "");
//        params.put("searchData", searchDate);
        params.put("search", searchText);
        mainRequest.requestHistoryList(params, getView(), new ScanRxDataCallBack<List<QualityHandledRecordVO>>() {
            @Override
            public void onSucess(List<QualityHandledRecordVO> orderMaterielVos) {
                if (getView() != null) {
                    if (pageNo == 1) {
                        getView().showFirstData(orderMaterielVos);
                    } else {
                        getView().loadMoreData(orderMaterielVos);
                    }
                }
            }


            @Override
            public void onFail(String message, Throwable throwable) {
                if (getView() != null) {
                    getView().loadError();
                }
            }

        }, isShowLoad);
    }

//    public void requestLogin(Map<String, String> params) {
//        mainRequest.requestLogin(params, getView(), new ScanRxDataCallBack<ScanPersonInfo>() {
//            @Override
//            public void onSucess(ScanPersonInfo s) {
//                if (getView() != null) {
//                    getView().loginResult(s);
//                }
//
//            }
//
//            @Override
//            public void onFail(String message, Throwable throwable) {
//                if (getView() != null) {
//                    getView().loginResult(null);
//                }
//            }
//        }, true);
//    }

}
