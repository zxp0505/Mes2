package workstation.zjyk.workstation.ui.present;

import android.content.Context;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.SDCardHelper;
import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import workstation.zjyk.workstation.modle.bean.WSAppUpdate;
import workstation.zjyk.workstation.modle.bean.WSPerson;
import workstation.zjyk.workstation.modle.bean.WSWorkReportRecordVO;
import workstation.zjyk.workstation.modle.bean.WSWorkStation;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTrayTaskMainInfoVo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationVo;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.download.DownloadProgressListener;
import workstation.zjyk.workstation.modle.net.WSBaseObsever;
import workstation.zjyk.workstation.modle.request.WSMainRequest;
import workstation.zjyk.workstation.ui.views.WSBaseView;
import workstation.zjyk.workstation.ui.views.WSMainView;
import workstation.zjyk.workstation.util.dialog.WSDownApkProgressDialog;
import workstation.zjyk.workstation.util.dialog.WSProgressDialog;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * Created by zjgz on 2017/11/3.
 */

public class WSMainPresent extends WSRxPresent<WSMainView> {

    private final WSMainRequest mMainRequest;

    public WSMainPresent() {
        mMainRequest = new WSMainRequest();
    }

    public void testServer(HashMap<String, String> params) {
        mMainRequest.testServer(params, getView(), new WSRxDataCallBack<WSWorkStationVo>() {
            @Override
            public void onSucess(WSWorkStationVo workStationVo) {
                if (getView() != null) {
                    getView().showTestServerResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showTestServerResult(false);
                }
            }
        });
    }

    /**
     * 获取工位信息
     */
    public void getStationName(HashMap<String, String> params) {

        mMainRequest.getStationName(params, getView(), new WSRxDataCallBack<WSWorkStationVo>() {
            @Override
            public void onSucess(WSWorkStationVo workStationVo) {
                if (getView() != null) {
                    getView().showStation(workStationVo);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showStation(null);
                }
            }
        });
    }

    /**
     * 查询已接收托盘的信息
     *
     * @param params
     */
    public void getReciveredTrayInfo(Map<String, String> params, String code) {
        mMainRequest.getReciveredTrayInfo(params, getView(), new WSRxDataCallBack<WSWorkStationTrayTaskMainInfoVo>() {
            @Override
            public void onSucess(WSWorkStationTrayTaskMainInfoVo wsWorkStationTrayTaskMainInfoVo) {
                if (getView() != null) {
                    getView().showReciveredTrayInfo(wsWorkStationTrayTaskMainInfoVo, code);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showReciveredTrayInfo(null, code);
                }
            }
        });
    }

    /**
     * 获取工位上所有人员信息
     */
    public void getStationAllPerson(boolean loading) {
        mMainRequest.getStationAllPerson(new HashMap<String, String>(), getView(), new WSRxDataCallBack<List<WSPerson>>() {
            @Override
            public void onSucess(List<WSPerson> personList) {
                if (getView() != null) {
                    getView().showAllPersons(personList);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
//                    ((MainView) getView()).showAllPersons(null);
                }
            }
        }, loading);
    }

    public void loginOut(Map<String, String> map) {
        mMainRequest.loginOut(map, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().loginOutResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().loginOutResult(false);
                }
            }
        });
    }


    /**
     * 发送心跳
     *
     * @param params
     */
    public void requestHeart(Map<String, String> params) {
        mMainRequest.requestHeart(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {

            }

            @Override
            public void onFail() {

            }
        }, false);
    }

    public void checkApkVersion(Map<String, String> params) {
        mMainRequest.checkApkVersion(params, getView(), new WSRxDataCallBack<WSAppUpdate>() {
            @Override
            public void onSucess(WSAppUpdate wsAppUpdate) {
                if (getView() != null) {
                    getView().showCheckApkInfo(wsAppUpdate);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showCheckApkInfo(null);
                }
            }
        });
    }

    public void getStationList(Map<String, String> params) {
        mMainRequest.getStationList(params, getView(), new WSRxDataCallBack<List<WSWorkStation>>() {
            @Override
            public void onSucess(List<WSWorkStation> wsAppUpdate) {
                if (getView() != null) {
                    getView().showStationList(wsAppUpdate);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showStationList(null);
                }
            }
        });
    }

    public void updateStation(Map<String, String> params) {
        mMainRequest.updateStation(params, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String wsAppUpdate) {
                if (getView() != null) {
                    getView().showUpdateStationResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showUpdateStationResult(false);
                }
            }
        });
    }

    public void requestUserReportDetail(Map<String, String> params) {
        mMainRequest.requestUserReportDetail(params, getView(), new WSRxDataCallBack<List<WSWorkReportRecordVO>>() {
            @Override
            public void onSucess(List<WSWorkReportRecordVO> wsAppUpdate) {
                if (getView() != null) {
                    getView().showUserReportDetail(wsAppUpdate);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showUserReportDetail(null);
                }
            }
        });
    }

    /**
     * 下载apk
     *
     * @param params
     */
    WSBaseObsever downObsever = null;

    public void downLoadApk(Context context, String pdfUrl, String saveUrl, WSDownApkProgressDialog mProgressDialog) {
        downObsever = null;
        mMainRequest.downLoadApk(pdfUrl, saveUrl, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().downApkResult(true, saveUrl);
                }
//                if (mProgressDialog != null && mProgressDialog.isShowing()) {
//                    mProgressDialog.dismiss();
//                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().downApkResult(false, saveUrl);
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
//                            if (mProgressDialog != null && mProgressDialog.isShowing()) {
//                                mProgressDialog.dismiss();
//                            }
                            return;
                        }
                    }
                }

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


}
