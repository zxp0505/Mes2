package workstation.zjyk.com.scanapp.modle.request;

import java.util.List;
import java.util.Map;

import workstation.zjyk.com.scanapp.modle.bean.QualityHandleDetailVO;
import workstation.zjyk.com.scanapp.modle.bean.QualityHandledRecordVO;
import workstation.zjyk.com.scanapp.modle.bean.ScanPersonInfo;
import workstation.zjyk.com.scanapp.modle.bean.ScanTrayInfoVo;
import workstation.zjyk.com.scanapp.modle.callback.ScanRxDataCallBack;
import workstation.zjyk.com.scanapp.modle.net.ScanApiManager;
import workstation.zjyk.com.scanapp.modle.net.ScanChildHttpResultObsever;
import workstation.zjyk.com.scanapp.modle.net.ScanErrorCode;
import workstation.zjyk.com.scanapp.ui.views.ScanBaseView;
import workstation.zjyk.com.scanapp.util.ScanURLBuilder;

/**
 * Created by zjgz on 2018/1/20.
 */

public class MainRequest {

    public void testServer(Map<String, String> parms, final ScanBaseView baseView, final ScanRxDataCallBack<ScanTrayInfoVo> callBack, boolean isShowLoading) {
        ScanApiManager.getInstance().post(ScanURLBuilder.GET_TRAY_INFO, parms, baseView.<ScanTrayInfoVo>bindToLife(), new ScanChildHttpResultObsever<ScanTrayInfoVo>(null, ScanTrayInfoVo.class) {
            @Override
            public void _onSuccess(ScanTrayInfoVo workStationVo) {
                callBack.onSucess(workStationVo);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoading) {
//                    baseView.showLoadingDialog(message);
                    baseView.showLoading("正在加载中...");
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
                if (code == ScanErrorCode.CODE_DATA_NULL || code == ScanErrorCode.CODE_RESULT_ERROR ) {
                    callBack.onSucess(null);
                    return;
                }
                callBack.onFail(error, e);

            }
        });
    }

    public void identityVerifi(Map<String, String> parms, final ScanBaseView baseView, final ScanRxDataCallBack<String> callBack, boolean isShowLoading) {
        ScanApiManager.getInstance().post(ScanURLBuilder.IDENTY_VERIFI, parms, baseView.<String>bindToLife(), new ScanChildHttpResultObsever<String>(null, String.class) {
            @Override
            public void _onSuccess(String workStationVo) {
                callBack.onSucess(workStationVo);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoading) {
//                    baseView.showLoadingDialog(message);
                    baseView.showLoading("正在加载中...");
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
                if (code == ScanErrorCode.CODE_DATA_NULL || code == ScanErrorCode.CODE_RESULT_ERROR ) {
                    callBack.onSucess(null);
                    return;
                }
                callBack.onFail(error, e);

            }
        });
    }
    public void requestByScancode(Map<String, String> parms, final ScanBaseView baseView, final ScanRxDataCallBack<ScanTrayInfoVo> callBack, boolean isShowLoading) {
        ScanApiManager.getInstance().post(ScanURLBuilder.GET_TRAY_INFO, parms, baseView.<ScanTrayInfoVo>bindToLife(), new ScanChildHttpResultObsever<ScanTrayInfoVo>(baseView, ScanTrayInfoVo.class) {
            @Override
            public void _onSuccess(ScanTrayInfoVo workStationVo) {
                callBack.onSucess(workStationVo);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoading) {
//                    baseView.showLoadingDialog(message);
                    baseView.showLoading("正在加载中...");
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail(error, e);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }
        });
    }

    public void requestDetail(Map<String, String> parms, final ScanBaseView baseView, final ScanRxDataCallBack<QualityHandleDetailVO> callBack, boolean isShowLoading) {
        ScanApiManager.getInstance().post(ScanURLBuilder.REQUEST_HISTORY_DETAIL, parms, baseView.<QualityHandleDetailVO>bindToLife(), new ScanChildHttpResultObsever<QualityHandleDetailVO>(baseView, QualityHandleDetailVO.class) {
            @Override
            public void _onSuccess(QualityHandleDetailVO workStationVo) {
                callBack.onSucess(workStationVo);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoading) {
//                    baseView.showLoadingDialog(message);
                    baseView.showLoading("正在加载中...");
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail(error, e);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }
        });
    }

    /**
     * 登录
     *
     * @param parms
     * @param baseView
     * @param callBack
     * @param isShowLoading
     */
    public void requestLogin(Map<String, String> parms, final ScanBaseView baseView, final ScanRxDataCallBack<ScanPersonInfo> callBack, boolean isShowLoading) {
        ScanApiManager.getInstance().post(ScanURLBuilder.REQUEST_LOGIN, parms, baseView.<ScanPersonInfo>bindToLife(), new ScanChildHttpResultObsever<ScanPersonInfo>(baseView, ScanPersonInfo.class) {
            @Override
            public void _onSuccess(ScanPersonInfo workStationVo) {
                callBack.onSucess(workStationVo);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoading) {
//                    baseView.showLoadingDialog(message);
                    baseView.showLoading("正在加载中...");
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail(error, e);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }
        });
    }

    public void requestWarnLogin(Map<String, String> parms, final ScanBaseView baseView, final ScanRxDataCallBack<ScanPersonInfo> callBack, boolean isShowLoading) {
        ScanApiManager.getInstance().post(ScanURLBuilder.REQUEST_WARN_LOGIN, parms, baseView.<ScanPersonInfo>bindToLife(), new ScanChildHttpResultObsever<ScanPersonInfo>(baseView, ScanPersonInfo.class) {
            @Override
            public void _onSuccess(ScanPersonInfo workStationVo) {
                callBack.onSucess(workStationVo);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoading) {
//                    baseView.showLoadingDialog(message);
                    baseView.showLoading("正在加载中...");
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail(error, e);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }
        });
    }
    /**
     * 请求历史列表
     *
     * @param parms
     * @param baseView
     * @param callBack
     * @param isShowLoading
     */
    public void requestHistoryList(Map<String, String> parms, final ScanBaseView baseView, final ScanRxDataCallBack<List<QualityHandledRecordVO>> callBack, boolean isShowLoading) {
        ScanApiManager.getInstance().post(ScanURLBuilder.REQUEST_HISTORY_LIST, parms, baseView.<List<QualityHandledRecordVO>>bindToLife(), new ScanChildHttpResultObsever<List<QualityHandledRecordVO>>(baseView, QualityHandledRecordVO.class) {
            @Override
            public void _onSuccess(List<QualityHandledRecordVO> workStationVo) {
                callBack.onSucess(workStationVo);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoading) {
//                    baseView.showLoadingDialog(message);
                    baseView.showLoading("正在加载中...");
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail(error, e);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }
        });
    }

    public void uploadImages(Map<String, String> parms, List<String> images, final ScanBaseView baseView, final ScanRxDataCallBack<String> callBack, boolean isShowLoading) {
        ScanApiManager.getInstance().uploadImages(ScanURLBuilder.UPLOAD_IMAGES, parms, images, baseView.<String>bindToLife(), new ScanChildHttpResultObsever<String>(baseView, null) {
            @Override
            public void _onSuccess(String workStationVo) {
                callBack.onSucess(workStationVo);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoading) {
//                    baseView.showLoadingDialog(message);
                    baseView.showLoading("正在加载中...");
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail(error, e);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }
        });
    }

    public void commitExcept(Map<String, String> parms, final ScanBaseView baseView, final ScanRxDataCallBack<String> callBack, boolean isShowLoading) {
        ScanApiManager.getInstance().post("", parms, baseView.<ScanTrayInfoVo>bindToLife(), new ScanChildHttpResultObsever<String>(baseView, String.class) {
            @Override
            public void _onSuccess(String workStationVo) {
                callBack.onSucess(workStationVo);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoading) {
//                    baseView.showLoadingDialog(message);
                    baseView.showLoading("正在加载中...");
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail(error, e);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }
        });
    }

    public void refuseRequest(Map<String, String> parms, final ScanBaseView baseView, final ScanRxDataCallBack<String> callBack, boolean isShowLoading) {
        ScanApiManager.getInstance().post(ScanURLBuilder.REQUEST_REFUSE, parms, baseView.<ScanTrayInfoVo>bindToLife(), new ScanChildHttpResultObsever<String>(baseView, String.class) {
            @Override
            public void _onSuccess(String workStationVo) {
                callBack.onSucess(workStationVo);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoading) {
//                    baseView.showLoadingDialog(message);
                    baseView.showLoading("正在加载中...");
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail(error, e);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }
        });
    }

    /**
     * 拉去警告信息
     * @param parms
     * @param baseView
     * @param callBack
     * @param isShowLoading
     */
    public void pullWarnInfoRequest(Map<String, String> parms, final ScanBaseView baseView, final ScanRxDataCallBack<String> callBack, boolean isShowLoading) {
        ScanApiManager.getInstance().post("", parms, baseView.<ScanTrayInfoVo>bindToLife(), new ScanChildHttpResultObsever<String>(baseView, String.class) {
            @Override
            public void _onSuccess(String workStationVo) {
                callBack.onSucess(workStationVo);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoading) {
//                    baseView.showLoadingDialog(message);
                    baseView.showLoading("正在加载中...");
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail(error, e);
                if (isShowLoading) {
//                    baseView.hideLoadingDialog();
                    baseView.hideLoading();
                }
            }
        });
    }
}
