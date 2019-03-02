package workstation.zjyk.workstation.modle.request;

import java.util.List;
import java.util.Map;

import workstation.zjyk.workstation.modle.bean.WSAppUpdate;
import workstation.zjyk.workstation.modle.bean.WSPerson;
import workstation.zjyk.workstation.modle.bean.WSWorkReportRecordVO;
import workstation.zjyk.workstation.modle.bean.WSWorkStation;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTrayTaskMainInfoVo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationVo;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.download.DownloadProgressListener;
import workstation.zjyk.workstation.modle.net.WSApiManager;
import workstation.zjyk.workstation.modle.net.WSChildHttpResultObsever;
import workstation.zjyk.workstation.modle.net.WSErrorCode;
import workstation.zjyk.workstation.modle.net.WSHeartApiManager;
import workstation.zjyk.workstation.ui.views.WSBaseView;
import workstation.zjyk.workstation.util.WSURLBuilder;

/**
 * Created by zjgz on 2017/11/3.
 */

public class WSMainRequest {
    public void testServer(Map<String, String> parms, final WSBaseView baseView, final WSRxDataCallBack<WSWorkStationVo> callBack) {
        WSApiManager.getInstance().post(WSURLBuilder.GET_STATION_NAME, parms, baseView.<WSWorkStationVo>bindToLife(), new WSChildHttpResultObsever<WSWorkStationVo>(baseView, WSWorkStationVo.class) {
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

    public void getStationName(Map<String, String> parms, final WSBaseView baseView, final WSRxDataCallBack<WSWorkStationVo> callBack) {
        WSApiManager.getInstance().post(WSURLBuilder.GET_STATION_NAME, parms, baseView.<WSWorkStationVo>bindToLife(), new WSChildHttpResultObsever<WSWorkStationVo>(baseView, WSWorkStationVo.class) {
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
                callBack.onFail();
                baseView.hideLoadingDialog();
            }
        });
    }

    public void getReciveredTrayInfo(Map<String, String> parms, final WSBaseView baseView, final WSRxDataCallBack<WSWorkStationTrayTaskMainInfoVo> callBack) {
        WSApiManager.getInstance().post(WSURLBuilder.GET_RECIVERED_TRAY, parms, baseView.<WSWorkStationTrayTaskMainInfoVo>bindToLife(), new WSChildHttpResultObsever<WSWorkStationTrayTaskMainInfoVo>(baseView, WSWorkStationTrayTaskMainInfoVo.class) {
            @Override
            public void _onSuccess(WSWorkStationTrayTaskMainInfoVo workStationVo) {
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

    public void getStationAllPerson(Map<String, String> parms, final WSBaseView baseView, final WSRxDataCallBack<List<WSPerson>> callBack, boolean loading) {
        WSApiManager.getInstance().post(WSURLBuilder.GET_ALL_PERSON, parms, baseView.<List<WSPerson>>bindToLife(), new WSChildHttpResultObsever<List<WSPerson>>(baseView, WSPerson.class) {
            @Override
            public void _onSuccess(List<WSPerson> personList) {

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


    public void requestHeart(Map<String, String> parms, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean loading) {
        WSHeartApiManager.getInstance().post(WSURLBuilder.SEND_HEART, parms, null, new WSChildHttpResultObsever<String>(baseView, null) {
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

    public void loginOut(Map<String, String> parms, final WSBaseView baseView, final WSRxDataCallBack<String> callBack) {
        WSApiManager.getInstance().post(WSURLBuilder.LOGIN_OUT, parms, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null) {
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

    public void downLoadApk(String papkUrl, String saveUrl, final WSBaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, DownloadProgressListener downloadProgressListener) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().downFile(papkUrl, saveUrl, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, null) {
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

    public void checkApkVersion(Map<String, String> parms, final WSBaseView baseView, final WSRxDataCallBack<WSAppUpdate> callBack) {
        WSApiManager.getInstance().post(WSURLBuilder.CHECK_APK_VERSION, parms, baseView.<WSAppUpdate>bindToLife(), new WSChildHttpResultObsever<WSAppUpdate>(baseView, WSAppUpdate.class) {
            @Override
            public void _onSuccess(WSAppUpdate workStationVo) {
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

    public void getStationList(Map<String, String> parms, final WSBaseView baseView, final WSRxDataCallBack<List<WSWorkStation>> callBack) {
        WSApiManager.getInstance().post(WSURLBuilder.GET_STATION_LIST, parms, baseView.<List<WSWorkStation>>bindToLife(), new WSChildHttpResultObsever<List<WSWorkStation>>(baseView, WSWorkStation.class) {
            @Override
            public void _onSuccess(List<WSWorkStation> workStationVo) {
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

    public void updateStation(Map<String, String> parms, final WSBaseView baseView, final WSRxDataCallBack<String> callBack) {
        WSApiManager.getInstance().post(WSURLBuilder.UPDATE_STATION, parms, baseView.<String>bindToLife(), new WSChildHttpResultObsever<String>(baseView, WSWorkStation.class) {
            @Override
            public void _onSuccess(String workStationVo) {
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

    public void requestUserReportDetail(Map<String, String> parms, final WSBaseView baseView, final WSRxDataCallBack<List<WSWorkReportRecordVO>> callBack) {
        WSApiManager.getInstance().post(WSURLBuilder.GET_USER_REPORT_DETAIL, parms, baseView.<List<WSWorkReportRecordVO>>bindToLife(), new WSChildHttpResultObsever<List<WSWorkReportRecordVO>>(baseView, WSWorkReportRecordVO.class) {
            @Override
            public void _onSuccess(List<WSWorkReportRecordVO> workStationVo) {
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
