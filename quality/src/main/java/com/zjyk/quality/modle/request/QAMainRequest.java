package com.zjyk.quality.modle.request;

import com.zjyk.quality.modle.bean.QAPerson;
import com.zjyk.quality.modle.bean.QAWorkStationVo;
import com.zjyk.quality.modle.callback.QARxDataCallBack;
import com.zjyk.quality.modle.net.QAApiManager;
import com.zjyk.quality.modle.net.QAChildHttpResultObsever;
import com.zjyk.quality.modle.net.QAHeartApiManager;
import com.zjyk.quality.ui.views.QABaseView;
import com.zjyk.quality.util.QAURLBuilder;

import java.util.List;
import java.util.Map;

/**
 * Created by zjgz on 2017/11/3.
 */

public class QAMainRequest {

    public void getStationName(Map<String, String> parms, final QABaseView baseView, final QARxDataCallBack<QAWorkStationVo> callBack) {
        QAApiManager.getInstance().get(QAURLBuilder.GET_STATION_NAME, parms, baseView.<QAWorkStationVo>bindToLife(), new QAChildHttpResultObsever<QAWorkStationVo>(baseView, QAWorkStationVo.class) {
            @Override
            public void _onSuccess(QAWorkStationVo workStationVo) {
                callBack.onSucess(workStationVo);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail();
                baseView.hideLoadingDialog();
            }
        });
    }

    public void getStationAllPerson(Map<String, String> parms, final QABaseView baseView, final QARxDataCallBack<List<QAPerson>> callBack, boolean loading) {
        QAApiManager.getInstance().get(QAURLBuilder.GET_ALL_PERSON, parms, baseView.<List<QAPerson>>bindToLife(), new QAChildHttpResultObsever<List<QAPerson>>(baseView, QAPerson.class) {
            @Override
            public void _onSuccess(List<QAPerson> personList) {
                callBack.onSucess(personList);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (loading) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail();
                baseView.hideLoadingDialog();
            }
        });
    }


    public void requestHeart(Map<String, String> parms, final QABaseView baseView, final QARxDataCallBack<String> callBack, boolean loading) {
        QAHeartApiManager.getInstance().get(QAURLBuilder.SEND_HEART, parms, baseView.<String>bindToLife(), new QAChildHttpResultObsever<String>(baseView, null) {
            @Override
            public void _onSuccess(String personList) {
                callBack.onSucess(personList);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (loading) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onError(int code, String error, Throwable e) {

//                super._onError(code, error, e);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail();
                baseView.hideLoadingDialog();
            }
        });
    }

    public void loginOut(Map<String, String> parms, final QABaseView baseView, final QARxDataCallBack<String> callBack) {
        QAApiManager.getInstance().get(QAURLBuilder.LOGIN_OUT, parms, baseView.<String>bindToLife(), new QAChildHttpResultObsever<String>(baseView, null) {
            @Override
            public void _onSuccess(String s) {
                callBack.onSucess(s);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.hideLoadingDialog();
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }
        });
    }
}
