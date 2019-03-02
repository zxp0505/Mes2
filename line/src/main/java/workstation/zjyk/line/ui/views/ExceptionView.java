package workstation.zjyk.line.ui.views;

import workstation.zjyk.line.modle.bean.ExceptionDetailBean;
import workstation.zjyk.line.modle.bean.ExceptionRecordBean;

import java.util.List;

/**
 * Created by zjgz on 2017/11/14.
 */

public interface ExceptionView extends BaseView {
    void showExceptionRecord(List<ExceptionRecordBean> datas);
    void showExceptionDetails(List<ExceptionDetailBean> datas, ExceptionRecordBean bean);
}
