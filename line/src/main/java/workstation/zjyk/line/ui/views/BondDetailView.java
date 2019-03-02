package workstation.zjyk.line.ui.views;

import java.util.List;

import workstation.zjyk.line.modle.bean.AccessoryAddress;
import workstation.zjyk.line.modle.bean.LineDistributeHistoryVO;
import workstation.zjyk.line.modle.bean.OrderDistributeNeedMaterielVo;
import workstation.zjyk.line.modle.bean.OrderDistributeWrapMaterielVo;

public interface BondDetailView extends BaseView {
    void showData(OrderDistributeWrapMaterielVo datas);

    void showBindResult(boolean result, String trayCode);

    void showBondHistoryData(List<LineDistributeHistoryVO> datas);

    void showAccessoryList(List<AccessoryAddress> datas);

    void showOneBondResult(boolean result, boolean errorClick, String trayCode, boolean isCheck, boolean isPrint);

    void showPrintResult(boolean result);

    void showFileMd5CheckResult(boolean b, String fileName, String newDownUrl);

    void downPdfResult(boolean result, String saveUrl);
}
