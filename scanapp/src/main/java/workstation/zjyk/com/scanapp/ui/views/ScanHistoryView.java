package workstation.zjyk.com.scanapp.ui.views;

import java.util.List;

import workstation.zjyk.com.scanapp.modle.bean.QualityHandledRecordVO;

public interface ScanHistoryView extends ScanBaseView {
    void showFirstData(List<QualityHandledRecordVO> firstDatas);

    void loadMoreData(List<QualityHandledRecordVO> moreDatas);

    void loadError();
}
