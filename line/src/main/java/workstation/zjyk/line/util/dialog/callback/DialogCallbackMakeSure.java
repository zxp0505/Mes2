package workstation.zjyk.line.util.dialog.callback;

import workstation.zjyk.line.modle.bean.GoodsBean;

import java.util.List;

/**
 * 收料信息确认回掉
 * Created by zjgz on 2017/11/1.
 */

public interface DialogCallbackMakeSure {
    void makeSureReciver(List<GoodsBean> datas);
}
