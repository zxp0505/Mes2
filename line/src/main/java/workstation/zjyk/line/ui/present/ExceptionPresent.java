package workstation.zjyk.line.ui.present;

import workstation.zjyk.line.modle.bean.ExceptionDetailBean;
import workstation.zjyk.line.modle.bean.ExceptionRecordBean;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.request.ExceptionRequest;
import workstation.zjyk.line.ui.views.ExceptionView;

import java.util.List;
import java.util.Map;

/**
 * Created by zjgz on 2017/11/10.
 */

public class ExceptionPresent extends RxPresent<ExceptionView> {

    private final ExceptionRequest mExceptionRequest;

    public ExceptionPresent(ExceptionView baseView) {
        attachView(baseView);
        mExceptionRequest = new ExceptionRequest();
    }

    public void getExceptionData(Map<String, String> map) {
        mExceptionRequest.getExceptionData(map, getView(), new RxDataCallBack<List<ExceptionRecordBean>>() {
            @Override
            public void onSucess(List<ExceptionRecordBean> exceptionRecordBeans) {
                if (getView() != null) {
                    ((ExceptionView) getView()).showExceptionRecord(exceptionRecordBeans);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((ExceptionView) getView()).showExceptionRecord(null);
                }
            }
        });
    }


    public void getExceptionDataDetails(Map<String, String> map, final ExceptionRecordBean bean ) {
        mExceptionRequest.getExceptionDetailData(map, getView(), new RxDataCallBack<List<ExceptionDetailBean>>() {
            @Override
            public void onSucess(List<ExceptionDetailBean> exceptionRecordBeans) {
                if (getView() != null) {
                    ((ExceptionView) getView()).showExceptionDetails(exceptionRecordBeans,bean);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((ExceptionView) getView()).showExceptionDetails(null,bean);
                }
            }
        });
    }
}
