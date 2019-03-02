package com.zjyk.repair.modle.request;

import com.zjyk.repair.modle.bean.RPAppUpdate;
import com.zjyk.repair.modle.bean.RPPerson;
import com.zjyk.repair.modle.bean.RPWorkReportRecordVO;
import com.zjyk.repair.modle.bean.RPWorkStationVo;
import com.zjyk.repair.modle.callback.RPRxDataCallBack;
import com.zjyk.repair.modle.download.DownloadProgressListener;
import com.zjyk.repair.modle.net.RPApiManager;
import com.zjyk.repair.modle.net.RPChildHttpResultObsever;
import com.zjyk.repair.modle.net.RPHeartApiManager;
import com.zjyk.repair.ui.views.RPBaseView;
import com.zjyk.repair.util.RPURLBuilder;

import java.util.List;
import java.util.Map;


/**
 * Created by zjgz on 2017/11/3.
 */

public class RPMainRequest {

    public void getStationName(Map<String, String> parms, final RPBaseView baseView, final RPRxDataCallBack<RPWorkStationVo> callBack) {
        RPApiManager.getInstance().post(RPURLBuilder.GET_STATION_NAME, parms, baseView.<RPWorkStationVo>bindToLife(), new RPChildHttpResultObsever<RPWorkStationVo>(baseView, RPWorkStationVo.class) {
            @Override
            public void _onSuccess(RPWorkStationVo workStationVo) {
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

//    public void getReciveredTrayInfo(Map<String, String> parms, final RPBaseView baseView, final RPRxDataCallBack<RPWorkStationTrayTaskMainInfoVo> callBack) {
//        RPApiManager.getInstance().post(RPURLBuilder.GET_RECIVERED_TRAY, parms, baseView.<RPWorkStationTrayTaskMainInfoVo>bindToLife(), new RPChildHttpResultObsever<RPWorkStationTrayTaskMainInfoVo>(baseView, RPWorkStationTrayTaskMainInfoVo.class) {
//            @Override
//            public void _onSuccess(RPWorkStationTrayTaskMainInfoVo workStationVo) {
//                callBack.onSucess(workStationVo);
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
//                callBack.onFail();
//                baseView.hideLoadingDialog();
//            }
//        });
//    }

    public void getStationAllPerson(Map<String, String> parms, final RPBaseView baseView, final RPRxDataCallBack<List<RPPerson>> callBack, boolean loading) {
        RPApiManager.getInstance().post(RPURLBuilder.GET_ALL_PERSON, parms, baseView.<List<RPPerson>>bindToLife(), new RPChildHttpResultObsever<List<RPPerson>>(baseView, RPPerson.class) {
            @Override
            public void _onSuccess(List<RPPerson> personList) {

                callBack.onSucess(personList);
                if (loading) {
                    baseView.hideLoadingDialog();
                }
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
                if (loading) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }


    public void requestHeart(Map<String, String> parms, final RPBaseView baseView, final RPRxDataCallBack<String> callBack, boolean loading) {
        RPHeartApiManager.getInstance().post(RPURLBuilder.SEND_HEART, parms, null, new RPChildHttpResultObsever<String>(baseView, null) {
            @Override
            public void _onSuccess(String personList) {
                callBack.onSucess(personList);
                if (loading) {
                    baseView.hideLoadingDialog();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (loading) {
                    baseView.showLoadingDialog(message);
                }
            }

//            @Override
//            public void _onError(int code, String error, Throwable e) {

//                super._onError(code, error, e);
//            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail();
                if (loading) {
                    baseView.hideLoadingDialog();
                }
            }
        });
    }

    public void loginOut(Map<String, String> parms, final RPBaseView baseView, final RPRxDataCallBack<String> callBack) {
        RPApiManager.getInstance().post(RPURLBuilder.LOGIN_OUT, parms, baseView.<String>bindToLife(), new RPChildHttpResultObsever<String>(baseView, null) {
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

    public void downLoadApk(String papkUrl, String saveUrl, final RPBaseView baseView, final RPRxDataCallBack<String> callBack, boolean isShowLoad, DownloadProgressListener downloadProgressListener) {
        if (baseView == null) {
            return;
        }
        RPApiManager.getInstance().downFile(papkUrl, saveUrl, baseView.<String>bindToLife(), new RPChildHttpResultObsever<String>(baseView, null) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoad) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            public void _onSuccess(String info) {
                callBack.onSucess(info);
                if (isShowLoad) {
                    baseView.hideLoadingDialog();
                }
            }
        }, downloadProgressListener);
    }

    public void checkApkVersion(Map<String, String> parms, final RPBaseView baseView, final RPRxDataCallBack<RPAppUpdate> callBack) {
        RPApiManager.getInstance().post(RPURLBuilder.CHECK_APK_VERSION, parms, baseView.<RPAppUpdate>bindToLife(), new RPChildHttpResultObsever<RPAppUpdate>(baseView, RPAppUpdate.class) {
            @Override
            public void _onSuccess(RPAppUpdate workStationVo) {
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

    public void requestUserReportDetail(Map<String, String> parms, final RPBaseView baseView, final RPRxDataCallBack<List<RPWorkReportRecordVO>> callBack) {
        RPApiManager.getInstance().post(RPURLBuilder.GET_USER_REPORT_DETAIL, parms, baseView.<List<RPWorkReportRecordVO>>bindToLife(), new RPChildHttpResultObsever<List<RPWorkReportRecordVO>>(baseView, RPWorkReportRecordVO.class) {
            @Override
            public void _onSuccess(List<RPWorkReportRecordVO> workStationVo) {
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
}
