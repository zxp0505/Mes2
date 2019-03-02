package com.zjyk.repair.modle.request;

import java.util.Map;

import com.zjyk.repair.modle.bean.RPPerson;
import com.zjyk.repair.modle.callback.RPRxDataCallBack;
import com.zjyk.repair.modle.net.RPApiManager;
import com.zjyk.repair.modle.net.RPChildHttpResultObsever;
import com.zjyk.repair.ui.views.RPBaseView;
import com.zjyk.repair.util.RPURLBuilder;

/**
 * Created by zjgz on 2017/11/7.
 */

public class RPLoginRequest {
    public void login(Map<String, String> params, final RPBaseView baseView, final RPRxDataCallBack<RPPerson> callBack) {
        RPApiManager.getInstance().get(RPURLBuilder.GET_PERSON_SCAN, params, baseView.<RPPerson>bindToLife(), new RPChildHttpResultObsever<RPPerson>(baseView,RPPerson.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            public void _onSuccess(RPPerson RPPerson) {
                callBack.onSucess(RPPerson);
                baseView.hideLoadingDialog();
            }



        });
    }

    public void quit(Map<String, String> params, final RPBaseView baseView, final RPRxDataCallBack<RPPerson> callBack) {
        RPApiManager.getInstance().get(RPURLBuilder.LOGIN_OUT, params, baseView.<RPPerson>bindToLife(), new RPChildHttpResultObsever<RPPerson>(baseView, RPPerson.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }

            @Override
            public void _onSuccess(RPPerson o) {
                callBack.onSucess(o);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

        });
    }

    public void upLoadImage(final RPBaseView baseView, Map<String, String> params, String filePath, String photoFileName, final RPRxDataCallBack<RPPerson> callBack) {
//        File file = new File(filePath);
//
//        CommonApiManager.getInstance().upLoadImage(RPURLBuilder.UPLOAD_PHOTO, params, file, photoFileName, new CommonChildHttpResultSubscriber<CommonPerson>(baseView, CommonPerson.class) {
//            @Override
//            public void _onSuccess(CommonPerson s) {
//                callBack.onSucess(s);
//                baseView.hideLoadingDialog();
//            }
//
//            @Override
//            public void _showLoadingDialog(String message) {
//                baseView.showLoadingDialog(message);
//            }
//
//            @Override
//            protected void _onErrorChild(int code, String error, Throwable e) {
//                baseView.hideLoadingDialog();
//                callBack.onFail();
//            }
//        });

    }
}
