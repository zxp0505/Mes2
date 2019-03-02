package workstation.zjyk.com.scanapp.ui.views;

import java.util.List;

import workstation.zjyk.com.scanapp.modle.bean.QualityHandledRecordVO;

/**
 * Created by zjgz on 2018/1/20.
 */

public interface ScanCodeView extends ScanBaseView {

    public  void showHistoryList(List<QualityHandledRecordVO> historyList);
}
