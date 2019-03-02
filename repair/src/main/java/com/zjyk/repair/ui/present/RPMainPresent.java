package com.zjyk.repair.ui.present;

import android.content.Context;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zjyk.repair.modle.bean.RPAppUpdate;
import com.zjyk.repair.modle.bean.RPPerson;
import com.zjyk.repair.modle.bean.RPWorkStationVo;
import com.zjyk.repair.modle.callback.RPRxDataCallBack;
import com.zjyk.repair.modle.download.DownloadProgressListener;
import com.zjyk.repair.modle.net.RPBaseObsever;
import com.zjyk.repair.modle.request.RPMainRequest;
import com.zjyk.repair.ui.views.RPMainView;
import com.zjyk.repair.util.dialog.RPDownApkProgressDialog;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.SDCardHelper;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by zjgz on 2017/11/3.
 */

public class RPMainPresent extends RPRxPresent<RPMainView> {
    private final RPMainRequest mMainRequest;

    public RPMainPresent() {
        mMainRequest = new RPMainRequest();
    }

    /**
     * 获取工位信息  ---线边库
     */
    public void getStationName() {
        mMainRequest.getStationName(new HashMap<String, String>(), getView(), new RPRxDataCallBack<RPWorkStationVo>() {
            @Override
            public void onSucess(RPWorkStationVo workStationVo) {
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
//    public void getReciveredTrayInfo(Map<String, String> params) {
//        mMainRequest.getReciveredTrayInfo(params, getView(), new RPRxDataCallBack<RPWorkStationTrayTaskMainInfoVo>() {
//            @Override
//            public void onSucess(RPWorkStationTrayTaskMainInfoVo wsWorkStationTrayTaskMainInfoVo) {
//                if (getView() != null) {
//                    getView().showReciveredTrayInfo(wsWorkStationTrayTaskMainInfoVo);
//                }
//            }
//
//            @Override
//            public void onFail() {
//                if (getView() != null) {
//                    getView().showReciveredTrayInfo(null);
//                }
//            }
//        });
//    }

    /**
     * 获取工位上所有人员信息
     */
    public void getStationAllPerson(boolean loading) {
        mMainRequest.getStationAllPerson(new HashMap<String, String>(), getView(), new RPRxDataCallBack<List<RPPerson>>() {
            @Override
            public void onSucess(List<RPPerson> personList) {
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

//    public void loginOut(Map<String, String> map) {
//        mMainRequest.loginOut(map, getView(), new RPRxDataCallBack<String>() {
//            @Override
//            public void onSucess(String s) {
//                if (getView() != null) {
//                    getView().loginOutResult(true);
//                }
//            }
//
//            @Override
//            public void onFail() {
//                if (getView() != null) {
//                    getView().loginOutResult(false);
//                }
//            }
//        });
//    }


    /**
     * 发送心跳
     *
     * @param params
     */
    public void requestHeart(Map<String, String> params) {
        mMainRequest.requestHeart(params, getView(), new RPRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {

            }

            @Override
            public void onFail() {

            }
        }, false);
    }

    public void checkApkVersion(Map<String, String> params) {
        mMainRequest.checkApkVersion(params, getView(), new RPRxDataCallBack<RPAppUpdate>() {
            @Override
            public void onSucess(RPAppUpdate rpAppUpdate) {
                if (getView() != null) {
                    getView().showCheckApkInfo(rpAppUpdate);
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

//    public void requestUserReportDetail(Map<String, String> params) {
//        mMainRequest.requestUserReportDetail(params, getView(), new RPRxDataCallBack<List<RPWorkReportRecordVO>>() {
//            @Override
//            public void onSucess(List<RPWorkReportRecordVO> wsAppUpdate) {
//                if (getView() != null) {
//                    getView().showUserReportDetail(wsAppUpdate);
//                }
//            }
//
//            @Override
//            public void onFail() {
//                if (getView() != null) {
//                    getView().showUserReportDetail(null);
//                }
//            }
//        });
//    }

    /**
     * 下载apk
     *
     * @param params
     */
    RPBaseObsever downObsever = null;

    public void downLoadApk(Context context, String pdfUrl, String saveUrl, RPDownApkProgressDialog mProgressDialog) {
        downObsever = null;
        mMainRequest.downLoadApk(pdfUrl, saveUrl, getView(), new RPRxDataCallBack<String>() {
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
            public void getObsever(RPBaseObsever baseObsever) {
                downObsever = baseObsever;
            }

        });
    }
}
