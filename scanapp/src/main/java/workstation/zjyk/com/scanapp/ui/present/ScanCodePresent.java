package workstation.zjyk.com.scanapp.ui.present;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import workstation.zjyk.com.scanapp.modle.bean.QualityHandledRecordVO;
import workstation.zjyk.com.scanapp.modle.bean.ScanPersonInfo;
import workstation.zjyk.com.scanapp.modle.callback.ScanRxDataCallBack;
import workstation.zjyk.com.scanapp.modle.request.MainRequest;
import workstation.zjyk.com.scanapp.ui.views.ScanCodeView;
import workstation.zjyk.com.scanapp.ui.views.ScanLoginView;

/**
 * Created by zjgz on 2018/1/20.
 */

public class ScanCodePresent extends ScanRxPresent<ScanCodeView> {

    private final MainRequest mainRequest;

    public ScanCodePresent() {
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
            public void onSucess(List<QualityHandledRecordVO> s) {
                if (getView() != null) {
                    getView().showHistoryList(s);
                }

            }

            @Override
            public void onFail(String message, Throwable throwable) {
                if (getView() != null) {
                    getView().showHistoryList(null);
                }
            }
        }, true);
    }

}
