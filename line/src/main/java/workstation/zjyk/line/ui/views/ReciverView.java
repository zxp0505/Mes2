package workstation.zjyk.line.ui.views;

import workstation.zjyk.line.modle.bean.GoodsBean;

import java.util.List;

/**
 * Created by zjgz on 2017/10/25.
 */

public interface ReciverView extends BaseView {
    void showFirstData(List<GoodsBean> datas);

    void loadMoreData(List<GoodsBean> datas);
}
