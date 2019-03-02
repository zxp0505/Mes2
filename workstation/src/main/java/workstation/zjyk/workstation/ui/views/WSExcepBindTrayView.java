package workstation.zjyk.workstation.ui.views;

/**
 * Created by zjgz on 2017/12/22.
 */

public interface WSExcepBindTrayView extends WSBaseView {
    void showScanResult(String result);

    void showBindTrayResult(boolean result,String exceptPrintId);

    void showExceptPrint(boolean result);

    void closeCurrent();
}
