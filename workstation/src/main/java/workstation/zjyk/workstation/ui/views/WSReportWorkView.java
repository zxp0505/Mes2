package workstation.zjyk.workstation.ui.views;

import java.util.List;

import workstation.zjyk.workstation.modle.bean.WSDailyWorkHistory;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;

/**
 * Created by zjgz on 2017/12/22.
 */

public interface WSReportWorkView extends WSBaseView {
    void showStepList(List<WSProcedureStep> datas);

    void showStepHistoryList(List<WSDailyWorkHistory> datas);

    void showReportWorkResult(boolean result);

    void closeCurrent();
}
