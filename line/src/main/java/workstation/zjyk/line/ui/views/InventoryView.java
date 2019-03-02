package workstation.zjyk.line.ui.views;

import workstation.zjyk.line.modle.bean.LineMaterielStorageManagerVo;

import java.util.List;

/**
 * Created by zjgz on 2017/11/14.
 */

public interface InventoryView extends BaseView {
    void showInventoryData(List<LineMaterielStorageManagerVo> datas);
    void showUpdataResult(boolean result, LineMaterielStorageManagerVo lineMaterielStorageManagerVo);
}
