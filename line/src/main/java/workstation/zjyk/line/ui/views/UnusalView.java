package workstation.zjyk.line.ui.views;

import workstation.zjyk.line.modle.bean.ExceptionOutBean;
import workstation.zjyk.line.modle.bean.ExceptionPrintBean;

/**
 * Created by zjgz on 2017/11/14.
 */

public interface UnusalView extends BaseView {
    void showExceptionOut(ExceptionOutBean exceptionOutBean);

    void showExceptionPrintData(ExceptionPrintBean exceptionPrintBean,String recordId,int status);
}
