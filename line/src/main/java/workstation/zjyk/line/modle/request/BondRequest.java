package workstation.zjyk.line.modle.request;

import java.net.URL;
import java.util.List;
import java.util.Map;

import rx.Subscription;
import workstation.zjyk.line.modle.bean.AccessoryAddress;
import workstation.zjyk.line.modle.bean.LineDistributeHistoryVO;
import workstation.zjyk.line.modle.bean.OrderDistributeNeedMaterielVo;
import workstation.zjyk.line.modle.bean.OrderDistributeWrapMaterielVo;
import workstation.zjyk.line.modle.bean.OrderMaterielVo;
import workstation.zjyk.line.modle.bean.ValidateProcessInstructionVo;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.net.ApiManager;
import workstation.zjyk.line.modle.net.ChildHttpResultSubscriber;
import workstation.zjyk.line.modle.net.ErrorResultClickListner;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.util.URLBuilder;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.download.DownloadProgressListener;
import workstation.zjyk.workstation.modle.net.WSApiManager;
import workstation.zjyk.workstation.modle.net.WSChildHttpResultObsever;

public class BondRequest {
    /**
     * 查询订单列表
     *
     * @param baseView
     * @param params
     * @param callBack
     * @param isShowDialog
     * @return
     */
    public Subscription requestBondOrderList(BaseView baseView, Map<String, String> params, final RxDataCallBack<List<OrderMaterielVo>> callBack, final boolean isShowDialog) {
        return ApiManager.getInstance().post(URLBuilder.GET_ORDER_BOND_LIST, params, new ChildHttpResultSubscriber<List<OrderMaterielVo>>(baseView, OrderMaterielVo.class) {
            @Override
            public void _onSuccess(List<OrderMaterielVo> wraps) {
                callBack.onSucess(wraps);
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowDialog) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail();
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }
        });

    }

    public Subscription requestBondList(BaseView baseView, Map<String, String> params, final RxDataCallBack<OrderDistributeWrapMaterielVo> callBack, final boolean isShowDialog) {
        return ApiManager.getInstance().post(URLBuilder.CHECK_BOND_LIST, params, new ChildHttpResultSubscriber<OrderDistributeWrapMaterielVo>(baseView, OrderDistributeWrapMaterielVo.class) {
            @Override
            public void _onSuccess(OrderDistributeWrapMaterielVo wraps) {
                callBack.onSucess(wraps);
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowDialog) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail();
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }
        });

    }

    public Subscription checkBindTrayCode(BaseView baseView, Map<String, String> params, final RxDataCallBack<String> callBack, final boolean isShowDialog) {
        return ApiManager.getInstance().post(URLBuilder.MAKING_OUT_BIND_TRAY, params, new ChildHttpResultSubscriber<String>(baseView, String.class) {
            @Override
            public void _onSuccess(String wraps) {
                callBack.onSucess(wraps);
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowDialog) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail();
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }
        });

    }

    /**
     * 下发详情历史记录
     *
     * @param baseView
     * @param params
     * @param callBack
     * @param isShowDialog
     * @return
     */
    public Subscription requestHistoryRecord(BaseView baseView, Map<String, String> params, final RxDataCallBack<List<LineDistributeHistoryVO>> callBack, final boolean isShowDialog) {
        return ApiManager.getInstance().post(URLBuilder.CHECK_BOND_HISTORY, params, new ChildHttpResultSubscriber<List<LineDistributeHistoryVO>>(baseView, LineDistributeHistoryVO.class) {
            @Override
            public void _onSuccess(List<LineDistributeHistoryVO> wraps) {
                callBack.onSucess(wraps);
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowDialog) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail();
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }
        });

    }

    /**
     * 请求附件列表
     *
     * @param baseView
     * @param params
     * @param callBack
     * @param isShowDialog
     * @return
     */
    public Subscription requestAccessoryList(BaseView baseView, Map<String, String> params, final RxDataCallBack<List<AccessoryAddress>> callBack, final boolean isShowDialog) {
        return ApiManager.getInstance().post(URLBuilder.REQUEST_ACCESSORY_LIST, params, new ChildHttpResultSubscriber<List<AccessoryAddress>>(baseView, AccessoryAddress.class) {
            @Override
            public void _onSuccess(List<AccessoryAddress> wraps) {
                callBack.onSucess(wraps);
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowDialog) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail();
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }
        });

    }

    public Subscription checkFileMd5(BaseView baseView, Map<String, String> params, final RxDataCallBack<ValidateProcessInstructionVo> callBack, final boolean isShowDialog) {
        return ApiManager.getInstance().post(URLBuilder.CHECK_ACCESSORY_MD5, params, new ChildHttpResultSubscriber<ValidateProcessInstructionVo>(baseView, ValidateProcessInstructionVo.class) {
            @Override
            public void _onSuccess(ValidateProcessInstructionVo wraps) {
                callBack.onSucess(wraps);
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowDialog) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail();
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }
        });

    }

    public void downLoadPdf(String papkUrl, String saveUrl, final BaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, DownloadProgressListener downloadProgressListener) {
        if (baseView == null) {
            return;
        }
        WSApiManager.getInstance().downFile(papkUrl, saveUrl, null, new WSChildHttpResultObsever<String>(baseView, null) {
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

    public Subscription requestOneBond(BaseView baseView, Map<String, String> params, final RxDataCallBack<String> callBack, final boolean isShowDialog, ErrorResultClickListner errorResultClickListner) {
        return ApiManager.getInstance().post(URLBuilder.REQUEST_ONE_BOND, params, new ChildHttpResultSubscriber<String>(baseView, String.class, errorResultClickListner) {
            @Override
            public void _onSuccess(String wraps) {
                callBack.onSucess(wraps);
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowDialog) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail();
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }
        });

    }

    public Subscription bondPrint(BaseView baseView, Map<String, String> params, final RxDataCallBack<String> callBack, final boolean isShowDialog, ErrorResultClickListner errorResultClickListner) {
        return ApiManager.getInstance().post(URLBuilder.REQUEST_BOND_PRINT, params, new ChildHttpResultSubscriber<String>(baseView, String.class, errorResultClickListner) {
            @Override
            public void _onSuccess(String wraps) {
                callBack.onSucess(wraps);
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowDialog) {
                    baseView.showLoadingDialog(message);
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                callBack.onFail();
                if (isShowDialog) {
                    baseView.hideLoadingDialog();
                }
            }
        });

    }
}
