package workstation.zjyk.workstation.ui.views;

import java.util.List;

import workstation.zjyk.workstation.modle.bean.WSExceptionOutRecordVo;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;

/**
 * Created by zjgz on 2017/12/22.
 */

public interface WSExcepView extends WSBaseView {
    void showExceptionInfo(WSTrayLoadInfo wsTrayLoadInfo);

    void showExceptHistoryList(List<WSExceptionOutRecordVo> wsExceptionOutRecordVoList);

    void showExceptPrint(boolean result);
}
