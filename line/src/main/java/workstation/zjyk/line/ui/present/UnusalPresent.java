package workstation.zjyk.line.ui.present;

import java.util.Map;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.line.modle.bean.ExceptionOutBean;
import workstation.zjyk.line.modle.bean.ExceptionPrintBean;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.request.ExceptionRequest;
import workstation.zjyk.line.ui.views.UnusalView;

/**
 * Created by zjgz on 2017/11/14.
 */

public class UnusalPresent extends RxPresent<UnusalView> {

    private final ExceptionRequest exceptionRequest;

    public UnusalPresent(UnusalView unusalView) {
        attachView(unusalView);
        exceptionRequest = new ExceptionRequest();
    }


    public void getExceptionPrintData(Map<String, String> map) {
        exceptionRequest.getExceptionPrintData(map, getView(), new RxDataCallBack<ExceptionPrintBean>() {
            @Override
            public void onSucess(ExceptionPrintBean exceptionPrintBean) {
                if (getView() != null) {
                    ((UnusalView) getView()).showExceptionPrintData(exceptionPrintBean,map.get("recordId"),Integer.parseInt(map.get("status")));
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((UnusalView) getView()).showExceptionPrintData(null,map.get("recordId"),Integer.parseInt(map.get("status")));
                }
            }
        });
    }

    public void getExceptionOutData(Map<String, String> map) {
        exceptionRequest.getExceptionOutData(map, getView(), new RxDataCallBack<ExceptionOutBean>() {
            @Override
            public void onSucess(ExceptionOutBean exceptionPrintBean) {
                if (getView() != null) {
                    ((UnusalView) getView()).showExceptionOut(exceptionPrintBean);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((UnusalView) getView()).showExceptionOut(null);
                }
            }
        });
    }



}
