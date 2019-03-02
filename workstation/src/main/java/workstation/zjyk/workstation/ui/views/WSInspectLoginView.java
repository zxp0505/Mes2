package workstation.zjyk.workstation.ui.views;

import workstation.zjyk.workstation.modle.bean.ScanPersonInfo;

/**
 * Created by zjgz on 2018/1/20.
 */

public interface WSInspectLoginView extends WSBaseView {

    void loginResult(ScanPersonInfo personInfo);
    void showTestServerResult(boolean result);
}
