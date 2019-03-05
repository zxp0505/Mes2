package workstation.zjyk.workstation.ui.present;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;
import cn.com.ethank.mylibrary.resourcelibrary.utils.SDCardHelper;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import workstation.zjyk.workstation.modle.bean.WSBeginOrEnd;
import workstation.zjyk.workstation.modle.bean.WSConcernMaterielVO;
import workstation.zjyk.workstation.modle.bean.WSFollowedBean;
import workstation.zjyk.workstation.modle.bean.WSInputInfo;
import workstation.zjyk.workstation.modle.bean.WSLabelBean;
import workstation.zjyk.workstation.modle.bean.WSMaterialTray;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;
import workstation.zjyk.workstation.modle.bean.WSReworkTrayInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskMainInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskOtherInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskProductCheckTray;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSTrayVo;
import workstation.zjyk.workstation.modle.bean.WSValidateProcessInstructionVo;
import workstation.zjyk.workstation.modle.bean.WSWipTray;
import workstation.zjyk.workstation.modle.bean.WSWorkStationOutVO;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.download.DownloadProgressListener;
import workstation.zjyk.workstation.modle.net.WSBaseObsever;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.modle.request.WSTaskRequest;
import workstation.zjyk.workstation.ui.views.WSTaskView;
import workstation.zjyk.workstation.util.dialog.WSProgressDialog;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSTaskPresent extends WSRxPresent<WSTaskView> {

    private final WSTaskRequest mTaskRequest;

    public WSTaskPresent() {
        mTaskRequest = new WSTaskRequest();
    }

    public void requestTaskMainInfo(Map<String, String> params, boolean isShowLoading) {
        mTaskRequest.requestTaskMainInfo(params, getView(), new WSRxDataCallBack<WSTaskMainInfo>() {
            @Override
            public void onSucess(WSTaskMainInfo info) {
                if (getView() != null) {
                    getView().showTaskInfo(info);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showTaskInfo(null);
                }
            }
        }, isShowLoading);
    }


    /**
     * 获取关注列表
     *
     * @param params
     * @param isShowLoading
     */
    public void getFollowData(Map<String, String> params, boolean isShowLoading) {
        mTaskRequest.getFollowData(params, getView(), new WSRxDataCallBack<List<WSFollowedBean>>() {
            @Override
            public void onSucess(List<WSFollowedBean> info) {
                if (getView() != null) {
                    getView().showFolloweData(info);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showFolloweData(null);
                }
            }
        }, isShowLoading);
    }

    public void requestTaskOtherInfo(Map<String, String> params, boolean isShowLoading) {
        mTaskRequest.requestTaskOtherInfo(params, getView(), new WSRxDataCallBack<WSTaskOtherInfo>() {
            @Override
            public void onSucess(WSTaskOtherInfo info) {
                if (getView() != null) {
                    getView().showTaskOtherInfo(info);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showTaskOtherInfo(null);
                }
            }
        }, isShowLoading);
    }

    public void requestTaskMaterailAndMakingList(Map<String, String> params, boolean isShowLoading) {
        mTaskRequest.requestTaskMaterailAndMakingList(params, getView(), new WSRxDataCallBack<WSInputInfo>() {
            @Override
            public void onSucess(WSInputInfo wsInputInfo) {
                if (getView() != null) {
                    getView().showMaterailAndMakingList(wsInputInfo);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showMaterailAndMakingList(null);
                }
            }
        }, isShowLoading);
    }

    public void requestTrayMaterailList(Map<String, String> params) {
        mTaskRequest.requestTrayMaterailList(params, getView(), new WSRxDataCallBack<List<WSTrayVo>>() {
            @Override
            public void onSucess(List<WSTrayVo> wsTrayLoadInfos) {
                if (getView() != null) {
//                    getView().showTrayMaterailList(wsTrayLoadInfos);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showTrayMaterailList(null);
                }
            }
        }, true);
    }

    public void requestTrayList(Map<String, String> params) {
        mTaskRequest.requestTrayMaterailList(params, getView(), new WSRxDataCallBack<List<WSTrayVo>>() {
            @Override
            public void onSucess(List<WSTrayVo> wsTrayLoadInfos) {
                if (getView() != null) {
                    getView().showTrayList(wsTrayLoadInfos);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showTrayList(null);
                }
            }
        }, true);
    }

    public void requestTrayMakingList(Map<String, String> params) {
        mTaskRequest.requestTrayMakingList(params, getView(), new WSRxDataCallBack<List<WSTrayLoadInfo>>() {
            @Override
            public void onSucess(List<WSTrayLoadInfo> wsTrayLoadInfos) {
                if (getView() != null) {
                    getView().showTrayMakingList(wsTrayLoadInfos);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showTrayMakingList(null);
                }
            }
        }, true);
    }

    public void requestMaterailTrayList(Map<String, String> params) {
        mTaskRequest.requestMaterailTrayList(params, getView(), new WSRxDataCallBack<WSMaterialTray>() {
            @Override
            public void onSucess(WSMaterialTray wsMaterialTray) {
                if (getView() != null) {
                    getView().showMaterailTrayList(wsMaterialTray);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showMaterailTrayList(null);
                }
            }
        }, true);
    }

    public void requestMakingTrayList(Map<String, String> params) {
        mTaskRequest.requestMakingTrayList(params, getView(), new WSRxDataCallBack<WSWipTray>() {
            @Override
            public void onSucess(WSWipTray wsWipTray) {
                if (getView() != null) {
                    getView().showMakingTrayList(wsWipTray);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showMakingTrayList(null);
                }
            }
        }, true);
    }

    public void requestBomInfo(Map<String, String> params) {
        mTaskRequest.requestBomInfo(params, getView(), new WSRxDataCallBack<WSTrayLoadInfo>() {
            @Override
            public void onSucess(WSTrayLoadInfo trayLoadInfo) {
                if (getView() != null) {
                    getView().showBomInfo(trayLoadInfo);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showBomInfo(null);
                }
            }
        }, true);
    }

    public void requestStartPause(Map<String, String> params) {
        int currentStatus = Integer.parseInt(params.get("status"));
        mTaskRequest.requestStartPause(params, getView(), new WSRxDataCallBack<WSBeginOrEnd>() {
            @Override
            public void onSucess(WSBeginOrEnd s) {
                if (getView() != null) {
                    getView().showBeginOrPauseResult(true, currentStatus, s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showBeginOrPauseResult(false, currentStatus, null);
                }
            }
        }, false, new WSErrorResultClickListner() {
            @Override
            public void confirm(int code) {
                if (getView() != null) {
                    getView().toEndTaskOpition(true, currentStatus);
                }
            }

            @Override
            public void cancle() {
                if (getView() != null) {
                    getView().toEndTaskOpition(false, -1);
                }
            }
        });
    }

    public void requestReworkTrayInfo(Map<String, String> params) {
        mTaskRequest.requestReworkTrayInfo(params, getView(), new WSRxDataCallBack<WSReworkTrayInfo>() {
            @Override
            public void onSucess(WSReworkTrayInfo s) {
                if (getView() != null) {
                    getView().showRewrokTrayinfo(s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showRewrokTrayinfo(null);
                }
            }
        }, true);
    }

    public void requestRework(Map<String, String> params) {
        mTaskRequest.requestRework(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().showRewrokResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showRewrokResult(false);
                }
            }
        }, true, new WSErrorResultClickListner() {
            @Override
            public void confirm(int code) {

            }

            @Override
            public void cancle() {

            }
        });
    }

    public void repairBindTray(Map<String, String> params, WSWorkStationOutVO wsWorkStationOutVO) {
        mTaskRequest.requesBindTray(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String result) {
                if (getView() != null) {
                    getView().showRepairBindTrayResult(true, wsWorkStationOutVO);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showRepairBindTrayResult(false, wsWorkStationOutVO);
                }
            }
        }, true);
    }

    public void requestRepair(Map<String, String> params) {
        mTaskRequest.requestRepair(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().showRepairResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showRepairResult(false);
                }
            }
        }, true, null);
    }

    /**
     * 获取标签类型
     *
     * @param params
     */
    public void requestLabelTypes(Map<String, String> params) {
        mTaskRequest.requestLabelTypes(params, getView(), new WSRxDataCallBack<List<WSLabelBean>>() {
            @Override
            public void onSucess(List<WSLabelBean> s) {
                if (getView() != null) {
                    getView().showLabelTypes(s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showLabelTypes(null);
                }
            }
        }, true, null);
    }

    /**
     * 打印标签
     *
     * @param params
     */
    public void printLabel(Map<String, String> params) {
        mTaskRequest.printLabelTypes(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().showPrintLabelResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showPrintLabelResult(false);
                }
            }
        }, true, null);
    }


    //    public void requestRework(Map<String, String> params) {
//        mTaskRequest.requestRework(params, getView(), new WSRxDataCallBack<String>() {
//            @Override
//            public void onSucess(String s) {
//                if (getView() != null) {
//                    getView().showRewrokResult(true);
//                }
//            }
//
//            @Override
//            public void onFail() {
//                if (getView() != null) {
//                    getView().showRewrokResult(false);
//                }
//            }
//        }, true, null);
//    }
    public void checkFileMd5(String accessoryType, String downUrl, String taskId, String md5, String fileName) {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        params.put("fileMd5", md5);
        params.put("fileUrl", downUrl);
        params.put("accessoryType", accessoryType);
        mTaskRequest.checkFileMd5(params, getView(), new WSRxDataCallBack<WSValidateProcessInstructionVo>() {
            @Override
            public void onSucess(WSValidateProcessInstructionVo bean) {
                if (getView() != null) {
                    String status = bean.getStatus();
                    String newDownUrl = bean.getUrl();
                    if (!TextUtils.isEmpty(status)) {
                        int i = Integer.parseInt(status);
                        switch (i) {
                            case 0:
                                if (getView() != null) {
                                    getView().showFileMd5CheckResult(false, fileName, newDownUrl);
                                }
                                break;
                            case 1:
                                if (getView() != null) {
                                    getView().showFileMd5CheckResult(true, fileName, newDownUrl);
                                }
                                break;
                        }
                    }
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
//                    getView().showFileMd5CheckResult(false,fileName);
                }
            }
        }, true);
    }

    WSBaseObsever downObsever = null;

    public void downLoadPdf(Context context, String pdfUrl, String saveUrl) {
        downObsever = null;
        WSProgressDialog progressDialog = getProgressDialog(context);
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        mTaskRequest.downLoadPdf(pdfUrl, saveUrl, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().downPdfResult(true, saveUrl);
                }
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().downPdfResult(false, saveUrl);
                }
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        }, false, new DownloadProgressListener() {


            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                //判断sd卡空间
//                long available = responseBody.contentLength();
                long sdCardAvailableSize = SDCardHelper.getSDCardAvailableSize() * 1024 * 1024;
                File file = new File(saveUrl);
                File parentFile = file.getParentFile();
                if (sdCardAvailableSize < contentLength) {//
                    //sd空间不足
                    SDCardHelper.deleteDir(parentFile);
                    long sdCardAvailableSizeNow = SDCardHelper.getSDCardAvailableSize() * 1024 * 1024;
                    if (sdCardAvailableSizeNow < contentLength) {//sdCardAvailableSize<available
                        ToastUtil.showInfoCenterShort("内存空间不足，请清理sd卡空间");
                        if (downObsever.disposable != null) {
                            downObsever.disposable.dispose();
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            return;
                        }
                    }
                }

//                Log.e("manager","available:"+available+".....sdCardAvailableSize:"+sdCardAvailableSize);
//                Log.e("tag", "bytesRead:" + bytesRead + "---contentLength:" + contentLength + "--done:" + done);
                float process = (float) bytesRead / contentLength;

                Observable.just(process).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Float>() {
                    @Override
                    public void accept(Float aFloat) throws Exception {
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.setProgress(process);
                        }
                    }
                });
            }

            @Override
            public void getObsever(WSBaseObsever baseObsever) {
                downObsever = baseObsever;
            }

        });
    }

    private WSProgressDialog mProgressDialog;

    private WSProgressDialog getProgressDialog(Context context) {
        //取消
        if (mProgressDialog == null) {
            mProgressDialog = new WSProgressDialog(context, "", new WSDialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
                    //取消
                    if (downObsever != null) {
                        downObsever.disposable.dispose();
                    }

                }

                @Override
                public void OnNegativeClick() {

                }
            });
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        return mProgressDialog;
    }

    public void requesStepList(Map<String, String> params) {
        mTaskRequest.requesStepList(params, getView(), new WSRxDataCallBack<List<WSProcedureStep>>() {
            @Override
            public void onSucess(List<WSProcedureStep> wsProcedureSteps) {
                if (getView() != null) {
                    getView().showStepList(wsProcedureSteps);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showStepList(null);
                }
            }
        }, true);
    }

    public void requesExceptList(Map<String, String> params) {
        mTaskRequest.requesExceptList(params, getView(), new WSRxDataCallBack<WSTrayLoadInfo>() {
            @Override
            public void onSucess(WSTrayLoadInfo trayLoadInfo) {
                if (getView() != null) {
                    getView().showExceptionInfo(trayLoadInfo);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showExceptionInfo(null);
                }
            }
        }, true);
    }

    /**
     * 请求报验
     *
     * @param params
     * @param isShowLoading
     */
    public void requestInspectionData(Map<String, String> params, boolean isShowLoading) {
        mTaskRequest.requestInspectionData(params, getView(), new WSRxDataCallBack<List<WSTaskProductCheckTray>>() {
            @Override
            public void onSucess(List<WSTaskProductCheckTray> wsWorkStationCheckVOS) {
                if (getView() != null) {
                    getView().showInspebctionList(wsWorkStationCheckVOS);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showInspebctionList(null);
                }
            }
        }, isShowLoading);
    }

    public void toInspection(Map<String, String> params, boolean isShowLoading) {
        mTaskRequest.toInspection(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().toInspectionResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().toInspectionResult(false);
                }
            }
        }, isShowLoading);
    }

    public void checkInspectionCode(Map<String, String> params, boolean isShowLoading) {
        mTaskRequest.checkInspectionCode(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().checkInspectionResult(true, params.get("code"));
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().checkInspectionResult(false, "");
                }
            }
        }, isShowLoading);
    }

    /**
     * 确认巡检
     * @param params
     * @param isShowLoading
     */
    public void toMakeSureInspect(Map<String, String> params, boolean isShowLoading) {
        mTaskRequest.toMakeSureInspect(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().toMakeSureInspectResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().toMakeSureInspectResult(false);
                }
            }
        }, isShowLoading);
    }

    /**
     * 发送警告
     * @param params
     * @param isShowLoading
     */
    public void toSendWran(Map<String, String> params, boolean isShowLoading) {
        mTaskRequest.toSendWran(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().showWarnResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showWarnResult(false);
                }
            }
        }, isShowLoading);
    }

}
