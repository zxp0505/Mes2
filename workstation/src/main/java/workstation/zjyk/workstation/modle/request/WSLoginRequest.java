package workstation.zjyk.workstation.modle.request;

import java.util.Map;

import workstation.zjyk.workstation.modle.bean.ScanPersonInfo;
import workstation.zjyk.workstation.modle.bean.WSPerson;
import workstation.zjyk.workstation.modle.bean.WSWorkStationVo;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.net.WSApiManager;
import workstation.zjyk.workstation.modle.net.WSChildHttpResultObsever;
import workstation.zjyk.workstation.modle.net.WSErrorCode;
import workstation.zjyk.workstation.ui.views.WSBaseView;
import workstation.zjyk.workstation.util.WSURLBuilder;

/**
 * Created by zjgz on 2017/11/7.
 */

public class WSLoginRequest {

    public void testServer(Map<String, String> parms, final WSBaseView baseView, final WSRxDataCallBack<WSWorkStationVo> callBack) {
        WSApiManager.getInstance().post(WSURLBuilder.GET_STATION_NAME, parms, baseView.<WSWorkStationVo>bindToLife(), new WSChildHttpResultObsever<WSWorkStationVo>(null, WSWorkStationVo.class) {
            @Override
            public void _onSuccess(WSWorkStationVo workStationVo) {
                callBack.onSucess(workStationVo);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                if (code == WSErrorCode.CODE_DATA_NULL || code == WSErrorCode.CODE_RESULT_ERROR || code == WSErrorCode.CODE_DATA_NULL) {
                    callBack.onSucess(null);
                    return;
                }
                callBack.onFail();
            }
        });
    }
    public void requestInspectLogin(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<ScanPersonInfo> callBack, boolean isShowLoading) {
        WSApiManager.getInstance().post(WSURLBuilder.REQUEST_INSPECT_LOGIN, params, baseView.<ScanPersonInfo>bindToLife(), new WSChildHttpResultObsever<ScanPersonInfo>(baseView, ScanPersonInfo.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoading) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoading) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(ScanPersonInfo wsPerson) {
                callBack.onSucess(wsPerson);
                if (isShowLoading) {
                    baseView.hideLoadingDialog();
                }
            }


        });
    }

    public void login(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSPerson> callBack) {
        WSApiManager.getInstance().post(WSURLBuilder.GET_PERSON_SCAN, params, baseView.<WSPerson>bindToLife(), new WSChildHttpResultObsever<WSPerson>(baseView, WSPerson.class) {
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
            public void _onSuccess(WSPerson wsPerson) {
                callBack.onSucess(wsPerson);
                baseView.hideLoadingDialog();
            }


        });
    }

    public void quit(Map<String, String> params, final WSBaseView baseView, final WSRxDataCallBack<WSPerson> callBack) {
        WSApiManager.getInstance().post(WSURLBuilder.LOGIN_OUT, params, baseView.<WSPerson>bindToLife(), new WSChildHttpResultObsever<WSPerson>(baseView, WSPerson.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }

            @Override
            public void _onSuccess(WSPerson o) {
                callBack.onSucess(o);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

        });
    }

    public void upLoadImage(final WSBaseView baseView, Map<String, String> params, String filePath, String photoFileName, final WSRxDataCallBack<WSPerson> callBack) {
//        File file = new File(filePath);
//
//        CommonApiManager.getInstance().upLoadImage(WSURLBuilder.UPLOAD_PHOTO, params, file, photoFileName, new CommonChildHttpResultSubscriber<CommonPerson>(baseView, CommonPerson.class) {
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
