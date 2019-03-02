package workstation.zjyk.workstation.ui.views;

import java.util.List;

import workstation.zjyk.workstation.modle.bean.WSMaintainReason;
import workstation.zjyk.workstation.modle.bean.WSReturnOrRepair;
import workstation.zjyk.workstation.modle.bean.WSWorkStationOutVO;

/**
 * Created by zjgz on 2018/2/1.
 */

public interface WSRepairVew extends WSBaseView {
    void showRepairHistory(WSReturnOrRepair wsReturnOrRepair);

    void closeCurrent();


    void showBindResult(String result);

    void showRepairResult(boolean result);

    void showReairReasons(List<WSMaintainReason> reasons);
}
