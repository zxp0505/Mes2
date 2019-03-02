package workstation.zjyk.workstation.ui.views;

import java.util.List;

import workstation.zjyk.workstation.modle.bean.WSAppUpdate;
import workstation.zjyk.workstation.modle.bean.WSPerson;
import workstation.zjyk.workstation.modle.bean.WSWorkReportRecordVO;
import workstation.zjyk.workstation.modle.bean.WSWorkStation;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTrayTaskMainInfoVo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationVo;

/**
 * Created by zjgz on 2017/11/10.
 */

public interface WSMainView extends WSBaseView {
    void showStation(WSWorkStationVo workStationVo);

    void showReciveredTrayInfo(WSWorkStationTrayTaskMainInfoVo wsWorkStationTrayTaskMainInfoVo, String code);

    void loginOutResult(boolean result);

    void clearTrayResult(boolean result);

    void showAllPersons(List<WSPerson> personList);

    void downApkResult(boolean result, String saveUrl);

    void showCheckApkInfo(WSAppUpdate wsAppUpdate);

    void showStationList(List<WSWorkStation> wsWorkStations);

    void showUpdateStationResult(boolean result);

    void showUserReportDetail(List<WSWorkReportRecordVO> wsWorkReportRecordVOList);

    void showTestServerResult(boolean result);
}
