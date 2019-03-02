package workstation.zjyk.workstation.ui.views;

import java.util.List;

import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSOrderCheckVo;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTask;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTaskListBean;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTaskTableCount;

/**
 * Created by zjgz on 2017/12/7.
 */

public interface WSInspectListView extends WSBaseView {

    void showFirstData(List<WSOrderCheckVo> datas);


    void loadMoreData(List<WSOrderCheckVo> datas);

    void showScanResult(WSTrayLoadInfo trayLoadInfo);

    void loadError();

    void showMaterailBill(List<WSMaterial> materials);

    void showStationList(List<WSWorkStationInfo> wsWorkStationInfos, WSWorkStationTask wsWorkStationTask);

    void showTaskListCount(WSWorkStationTaskTableCount wsWorkStationTaskTableCount);

    void showDateList(List<String> dateList);
}
