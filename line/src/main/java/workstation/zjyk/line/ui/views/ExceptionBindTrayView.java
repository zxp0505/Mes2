package workstation.zjyk.line.ui.views;

import workstation.zjyk.line.modle.bean.ExceptionOutBean;

/**
 * Created by zjgz on 2017/11/10.
 */

public interface ExceptionBindTrayView extends BaseView {
    void showTrayStatus(boolean isSucess);
    void showFeedResult(ExceptionOutBean result);
}
