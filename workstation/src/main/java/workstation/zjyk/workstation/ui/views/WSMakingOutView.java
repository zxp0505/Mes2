package workstation.zjyk.workstation.ui.views;

import java.util.List;

import workstation.zjyk.workstation.modle.bean.WSTaskProductCheckTray;
import workstation.zjyk.workstation.modle.bean.WSWipHistory;
import workstation.zjyk.workstation.modle.bean.WSWorkStationCheckVO;

/**
 * Created by zjgz on 2017/12/21.
 */

public interface WSMakingOutView extends WSBaseView {
    void showDeliveryRecord(WSWipHistory wsWipHistory);

    void showInspebctionList(List<WSTaskProductCheckTray> datas);

    void checkInspectionResult(boolean result,String code);

    void toInspectionResult(boolean result);

    void showBindResult(String result);

    void showDeliveryResult(boolean result,String recordId);

    void showDeliveryPrint(boolean result,boolean isClose);

    void closeCurrent();
}
