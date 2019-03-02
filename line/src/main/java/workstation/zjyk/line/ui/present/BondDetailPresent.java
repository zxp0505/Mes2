package workstation.zjyk.line.ui.present;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.SDCardHelper;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import workstation.zjyk.line.modle.bean.AccessoryAddress;
import workstation.zjyk.line.modle.bean.LineDistributeHistoryVO;
import workstation.zjyk.line.modle.bean.OrderDistributeWrapMaterielVo;
import workstation.zjyk.line.modle.bean.ValidateProcessInstructionVo;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.net.ErrorResultClickListner;
import workstation.zjyk.line.modle.request.BondRequest;
import workstation.zjyk.line.ui.views.BondDetailView;
import workstation.zjyk.line.util.DialogCallBackTwo;
import workstation.zjyk.line.util.dialog.ProgressDialog;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.download.DownloadProgressListener;
import workstation.zjyk.workstation.modle.net.WSBaseObsever;

public class BondDetailPresent extends MvpBasePresenter<BondDetailView> {

    private final BondRequest mBondRequest;

    public BondDetailPresent(BondDetailView view) {
        attachView(view);
        mBondRequest = new BondRequest();
    }

    public void requestBondList(Map<String, String> params, boolean isShowLoad) {
        mBondRequest.requestBondList(getView(), params, new RxDataCallBack<OrderDistributeWrapMaterielVo>() {
            @Override
            public void onSucess(OrderDistributeWrapMaterielVo orderMaterielVos) {
                if (getView() != null) {
                    getView().showData(orderMaterielVos);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showData(null);
                }
            }
        }, isShowLoad);
    }

    public void checkBindTrayCode(Map<String, String> params, boolean isShowLoad, String trayCode) {
        mBondRequest.checkBindTrayCode(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String orderMaterielVos) {
                if (getView() != null) {
                    getView().showBindResult(true, trayCode);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showBindResult(false, trayCode);
                }
            }
        }, isShowLoad);
    }

    public void requestOneBond(Map<String, String> params, boolean isCheck, boolean isShowLoad, boolean isPrint) {

        mBondRequest.requestOneBond(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String trayCode) {
                if (getView() != null) {
                    getView().showOneBondResult(true, false, trayCode, isCheck, isPrint);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showOneBondResult(false, false, "", isCheck, isPrint);
                }
            }
        }, isShowLoad, new ErrorResultClickListner() {
            @Override
            public void confirm(int code) {
                if (getView() != null) {
                    getView().showOneBondResult(false, true, "", isCheck, isPrint);
                }
            }

            @Override
            public void cancle() {

            }
        });
    }

    public void bondPrint(Map<String, String> params, boolean isShowLoad) {
        mBondRequest.bondPrint(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String trayCode) {
                if (getView() != null) {
                    getView().showPrintResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showPrintResult(false);
                }
            }
        }, isShowLoad, new ErrorResultClickListner() {
            @Override
            public void confirm(int code) {
                if (getView() != null) {
                }
            }

            @Override
            public void cancle() {

            }
        });
    }

    public void requestHistoryRecord(Map<String, String> params, boolean isShowLoad) {
        mBondRequest.requestHistoryRecord(getView(), params, new RxDataCallBack<List<LineDistributeHistoryVO>>() {
            @Override
            public void onSucess(List<LineDistributeHistoryVO> orderMaterielVos) {
                if (getView() != null) {
                    getView().showBondHistoryData(orderMaterielVos);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showBondHistoryData(null);
                }
            }
        }, isShowLoad);
    }

    public void requestAccessoryList(Map<String, String> params, boolean isShowLoad) {
        mBondRequest.requestAccessoryList(getView(), params, new RxDataCallBack<List<AccessoryAddress>>() {
            @Override
            public void onSucess(List<AccessoryAddress> orderMaterielVos) {
                if (getView() != null) {
                    getView().showAccessoryList(orderMaterielVos);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showAccessoryList(null);
                }
            }
        }, isShowLoad);
    }


    public void checkFileMd5(String accessoryType, String downUrl, String productId, String md5, String fileName) {
        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        params.put("fileMd5", md5);
        params.put("fileUrl", downUrl);
        params.put("accessoryType", accessoryType);
        mBondRequest.checkFileMd5(getView(), params, new RxDataCallBack<ValidateProcessInstructionVo>() {
            @Override
            public void onSucess(ValidateProcessInstructionVo bean) {
                if (getView() != null) {
                    String status = bean.getStatus();
                    String newDownUrl = bean.getUrl();
                    if (!TextUtils.isEmpty(status)) {
                        int i = Integer.parseInt(status);
                        switch (i) {
                            case 0:
                                getView().showFileMd5CheckResult(false, fileName, newDownUrl);
                                break;
                            case 1:
                                getView().showFileMd5CheckResult(true, fileName, newDownUrl);
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
        ProgressDialog progressDialog = getProgressDialog(context);
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        mBondRequest.downLoadPdf(pdfUrl, saveUrl, getView(), new WSRxDataCallBack<String>() {
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

    private ProgressDialog mProgressDialog;

    private ProgressDialog getProgressDialog(Context context) {
        //取消
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context, "", new DialogCallBackTwo() {
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
}
