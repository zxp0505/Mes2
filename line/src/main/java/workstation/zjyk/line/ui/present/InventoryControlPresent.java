package workstation.zjyk.line.ui.present;

import workstation.zjyk.line.modle.bean.LineMaterielStorageManagerVo;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.net.InventoryControlRequest;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.ui.views.InventoryView;

import java.util.List;
import java.util.Map;

/**
 * Created by zjgz on 2017/11/14.
 */

public class InventoryControlPresent extends RxPresent<InventoryView> {

    private final InventoryControlRequest mInventoryControlRequest;

    public InventoryControlPresent(InventoryView inventoryView) {
        attachView(inventoryView);
        mInventoryControlRequest = new InventoryControlRequest();
    }

    public void requestInventoryDatas(Map<String, String> params) {
        mInventoryControlRequest.requestInventoryDatas(params, getView(), new RxDataCallBack<List<LineMaterielStorageManagerVo>>() {
            @Override
            public void onSucess(List<LineMaterielStorageManagerVo> lineMaterielStorageManagerVos) {
                if (getView() != null) {
                    ((InventoryView) getView()).showInventoryData(lineMaterielStorageManagerVos);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((InventoryView) getView()).showInventoryData(null);
                }
            }
        });
    }

    /**
     * 更新data
     * @param params
     */
    public void updataInventoryData(Map<String, String> params, final LineMaterielStorageManagerVo lineMaterielStorageManagerVo) {
        mInventoryControlRequest.updataInventoryData(params, getView(), new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    ((InventoryView) getView()).showUpdataResult(true,lineMaterielStorageManagerVo);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((InventoryView) getView()).showUpdataResult(false,lineMaterielStorageManagerVo);
                }
            }
        });
    }

}
