package workstation.zjyk.com.scanapp.ui.views;

import workstation.zjyk.com.scanapp.modle.bean.QualityHandleDetailVO;
import workstation.zjyk.com.scanapp.modle.bean.ScanTrayInfoVo;

/**
 * Created by zjgz on 2018/1/20.
 */

public interface ScanMainView extends ScanBaseView {

    void scanResult(ScanTrayInfoVo trayInfoVo);

    void scanResult(String message, Throwable throwable);

    void upLoadResult(boolean result);

    void commitExceptResult(boolean result);

    void refuseResult(boolean result);

    void showDetail(QualityHandleDetailVO qualityHandleDetailVO);

    void showDownPicResult(String requestUrl,String picPath);
}
