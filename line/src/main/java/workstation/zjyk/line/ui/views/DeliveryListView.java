package workstation.zjyk.line.ui.views;

import java.util.List;

import workstation.zjyk.line.modle.bean.TrayVo;
import workstation.zjyk.line.modle.bean.Wrap;

/**
 * Created by zjgz on 2017/12/4.
 */

public interface DeliveryListView extends BaseView {
    void showFirstData(List<TrayVo> datas);

    void loadMoreData(List<TrayVo> datas);

    void loadError();
    void showTrayInfo(Wrap wrap);
}
