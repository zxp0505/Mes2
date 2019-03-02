package workstation.zjyk.line.modle.request;

import workstation.zjyk.line.modle.bean.ExceptionDetailBean;
import workstation.zjyk.line.modle.bean.ExceptionOutBean;
import workstation.zjyk.line.modle.bean.ExceptionPrintBean;
import workstation.zjyk.line.modle.bean.ExceptionRecordBean;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.net.ApiManager;
import workstation.zjyk.line.modle.net.ChildHttpResultSubscriber;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.util.URLBuilder;

import java.util.List;
import java.util.Map;

/**
 * Created by zjgz on 2017/11/10.
 */

public class ExceptionRequest {
    /**
     * 异常报告列表
     *
     * @param map
     * @param baseView
     * @param callBack
     */
    public void getExceptionData(Map<String, String> map, final BaseView baseView, final RxDataCallBack<List<ExceptionRecordBean>> callBack) {
        ApiManager.getInstance().post(URLBuilder.GET_EXCEPTION_RECORD, map, new ChildHttpResultSubscriber<List<ExceptionRecordBean>>(baseView, ExceptionRecordBean.class) {
            @Override
            public void _onSuccess(List<ExceptionRecordBean> exceptionRecordBeans) {
                callBack.onSucess(exceptionRecordBeans);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }
        });
    }

    /**
     *根据异常输出记录id查询异常输出记录详情
     * @param map
     * @param baseView
     * @param callBack
     */
    public void getExceptionDetailData(Map<String, String> map, final BaseView baseView, final RxDataCallBack<List<ExceptionDetailBean>> callBack) {
        ApiManager.getInstance().post(URLBuilder.GET_EXCEPTION_RECORD_DETAIL, map, new ChildHttpResultSubscriber<List<ExceptionDetailBean>>(baseView, ExceptionDetailBean.class) {
            @Override
            public void _onSuccess(List<ExceptionDetailBean> exceptionRecordBeans) {
                callBack.onSucess(exceptionRecordBeans);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }
        });
    }

    /**
     *根据异常输出记录id查询异常输出打印信息
     * @param map
     * @param baseView
     * @param callBack
     */

    public void getExceptionPrintData(Map<String, String> map, final BaseView baseView, final RxDataCallBack<ExceptionPrintBean> callBack) {
        ApiManager.getInstance().post(URLBuilder.GET_EXCEPTION_RECORD_INFO, map, new ChildHttpResultSubscriber<ExceptionPrintBean>(baseView, ExceptionPrintBean.class) {
            @Override
            public void _onSuccess(ExceptionPrintBean exceptionRecordBeans) {
                callBack.onSucess(exceptionRecordBeans);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }
        });
    }

    /**
     *保存异常输出信息
     * @param map
     * @param baseView
     * @param callBack
     */

    public void getExceptionOutData(Map<String, String> map, final BaseView baseView, final RxDataCallBack<ExceptionOutBean> callBack) {
        ApiManager.getInstance().post(URLBuilder.SAVE_EXCEPTION_RECORD_OUT, map, new ChildHttpResultSubscriber<ExceptionOutBean>(baseView, ExceptionOutBean.class) {
            @Override
            public void _onSuccess(ExceptionOutBean exceptionRecordBeans) {
                callBack.onSucess(exceptionRecordBeans);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }
        });
    }
}
