package workstation.zjyk.workstation.ui.views;

import java.util.List;

import workstation.zjyk.workstation.modle.bean.WSInputWipInfo;
import workstation.zjyk.workstation.modle.bean.WSReturnOrRepair;
import workstation.zjyk.workstation.modle.bean.WSReviewWorkStationVo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationOutVO;

/**
 * Created by zjgz on 2018/1/26.
 */

public interface WSReviewWorkView extends WSBaseView {
    void showRewrokResult(boolean result);

    void closeCurrent();

    void showRewrokStations(List<WSReviewWorkStationVo> wsReviewWorkStationVos, WSInputWipInfo currentBean);

    void showReviewBindTrayResult(boolean result, WSWorkStationOutVO wsWorkStationOutVO);

    void showReviewWorkHistory(WSReturnOrRepair wsReturnOrRepair);
}
