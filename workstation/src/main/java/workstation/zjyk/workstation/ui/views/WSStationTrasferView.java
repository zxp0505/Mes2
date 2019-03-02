package workstation.zjyk.workstation.ui.views;

import java.util.List;

import workstation.zjyk.workstation.modle.bean.WSProcedureStep;
import workstation.zjyk.workstation.modle.bean.WSWorkStationInfo;

/**
 * Created by zjgz on 2017/12/22.
 */

public interface WSStationTrasferView extends WSBaseView {
    void showStationList(List<WSWorkStationInfo> datas);

    void showStationTrasferResult(boolean result);

    void closeCurrent();
}
