package workstation.zjyk.workstation.ui.views;

import java.util.List;

import workstation.zjyk.workstation.modle.bean.WSBeginOrEnd;
import workstation.zjyk.workstation.modle.bean.WSConcernMaterielVO;
import workstation.zjyk.workstation.modle.bean.WSFollowedBean;
import workstation.zjyk.workstation.modle.bean.WSInputInfo;
import workstation.zjyk.workstation.modle.bean.WSLabelBean;
import workstation.zjyk.workstation.modle.bean.WSMaterialTray;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;
import workstation.zjyk.workstation.modle.bean.WSReworkTrayInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskMainInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskOtherInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskProductCheckTray;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSTrayVo;
import workstation.zjyk.workstation.modle.bean.WSWipTray;
import workstation.zjyk.workstation.modle.bean.WSWorkStationOutVO;

/**
 * Created by zjgz on 2017/12/21.
 */

public interface WSTaskView extends WSBaseView {


    void showTaskInfo(WSTaskMainInfo wsTaskMainInfo);

    void showTaskOtherInfo(WSTaskOtherInfo wsTaskOtherInfo);

    void showMaterailAndMakingList(WSInputInfo inputInfo);

    void showTrayMaterailList(List<WSTrayLoadInfo> datas);

    void showTrayMakingList(List<WSTrayLoadInfo> datas);

    void showTrayList(List<WSTrayVo> trayList);

    void showMaterailTrayList(WSMaterialTray wsMaterialTray);

    void showMakingTrayList(WSWipTray wsMaterialTray);

    void showBomInfo(WSTrayLoadInfo info);

    void downPdfResult(boolean result, String filePath);

    void showBeginOrPauseResult(boolean result, int currentStatus, WSBeginOrEnd wsBeginOrEnd);

    void showFileMd5CheckResult(boolean result, String fileName, String newDownUrl);

    void showRewrokTrayinfo(WSReworkTrayInfo wsReworkTrayInfo);

    void showRewrokResult(boolean result);

    void showRepairResult(boolean result);

    void showLabelTypes(List<WSLabelBean> result);

    void showPrintLabelResult(boolean result);

    void showStepList(List<WSProcedureStep> datas);

    void showRepairBindTrayResult(boolean result, WSWorkStationOutVO wsWorkStationOutVO);

    void showExceptionInfo(WSTrayLoadInfo trayLoadInfo);

    void toEndTaskOpition(boolean confirm, int currentStatus);

    void showFolloweData(List<WSFollowedBean> wsConcernMaterielVOList);

    void showInspebctionList(List<WSTaskProductCheckTray> datas);

    void toInspectionResult(boolean result);

    void toMakeSureInspectResult(boolean result);

    void checkInspectionResult(boolean result, String code);

}
