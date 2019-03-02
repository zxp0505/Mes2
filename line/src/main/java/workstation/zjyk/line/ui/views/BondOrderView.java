package workstation.zjyk.line.ui.views;

import java.util.List;

import workstation.zjyk.line.modle.bean.OrderMaterielVo;

public interface BondOrderView extends BaseView {
    void showFirstData(List<OrderMaterielVo> datas);

    void loadMoreData(List<OrderMaterielVo> datas);

    void loadError();
}
