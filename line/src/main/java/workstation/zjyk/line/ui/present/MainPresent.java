package workstation.zjyk.line.ui.present;

import android.app.Activity;
import android.content.Context;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.SDCardHelper;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import workstation.zjyk.line.modle.bean.AppUpdate;
import workstation.zjyk.line.modle.bean.InitWorkStationPad;
import workstation.zjyk.line.modle.bean.Person;
import workstation.zjyk.line.modle.bean.VersionBean;
import workstation.zjyk.line.modle.bean.WorkStationVo;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.request.DeliveryRequest;
import workstation.zjyk.line.modle.request.MainRequest;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.ui.views.MainView;
import workstation.zjyk.line.util.dialog.DownApkProgressDialog;
import workstation.zjyk.line.util.download.UpdateManger;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.download.DownloadProgressListener;
import workstation.zjyk.workstation.modle.net.WSBaseObsever;

/**
 * Created by zjgz on 2017/11/3.
 */

public class MainPresent extends RxPresent<MainView> {

    private final MainRequest mMainRequest;
    private final DeliveryRequest mDeliveryRequest;

    public MainPresent(MainView baseView) {
        attachView(baseView);
        mMainRequest = new MainRequest();
        mDeliveryRequest = new DeliveryRequest();
    }

    private UpdateManger.UpdateCallBack updateCallBack = new UpdateManger.UpdateCallBack() {
        @Override
        public void cancel() {
            checkPadVerify();
        }
    };

    /**
     * 第一步：
     * 检测程序版本
     * 检测是否是最新版本，如果是则更新
     * 逻辑为，检测当前版本是否小于版本最低要求，如果最低要求没有设置，则跳过，负责提示更新最新版本
     * 检测当前版本是否小于最新版本，如果是，提示更新最新版本
     */
    public void checkVersion(final Activity activity) {
        Map<String, String> map = new HashMap<>();
        Subscription padVersion = mMainRequest.getPadVersion(map, getView(), new RxDataCallBack<VersionBean>() {

            @Override
            public void onSucess(VersionBean versionBean) {
// 更新
                UpdateManger updateManger = new UpdateManger(activity);
                updateManger.setUpdateCallBack(updateCallBack);
                boolean isUpdate = updateManger.checkVersion(versionBean);
                if (!isUpdate) {
//                    checkPadVerify();
                } else {

                }
            }

            @Override
            public void onFail() {
                checkPadVerify();
            }
        });
        addSubscription(padVersion);

    }

    private void checkPadVerify() {
        // 验证pad是否合法，合法后才能进行udp连接和任务信息
        Subscription subscription = mMainRequest.checkPadVerify(new HashMap<String, String>(), getView(), new RxDataCallBack<InitWorkStationPad>() {
            @Override
            public void onSucess(InitWorkStationPad initWorkStationPad) {
                LoggerUtil.e("onsucess:" + initWorkStationPad.toString());
            }

            @Override
            public void onFail() {

            }
        });
        addSubscription(subscription);

    }


    public void testServer(HashMap<String, String> params) {
        mMainRequest.testServer(params, getView(), new RxDataCallBack<WorkStationVo>() {
            @Override
            public void onSucess(WorkStationVo workStationVo) {
                if (getView() != null) {
                    ((MainView) getView()).showTestServerResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((MainView) getView()).showTestServerResult(false);
                }
            }
        });
    }

    /**
     * 获取工位信息  ---线边库
     */
    public void getStationName() {
        mMainRequest.getStationName(new HashMap<String, String>(), getView(), new RxDataCallBack<WorkStationVo>() {
            @Override
            public void onSucess(WorkStationVo workStationVo) {
                if (getView() != null) {
                    ((MainView) getView()).showStation(workStationVo);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((MainView) getView()).showStation(null);
                }
            }
        });
    }

    /**
     * 获取工位上所有人员信息
     */
    public void getStationAllPerson(boolean loading) {
        mMainRequest.getStationAllPerson(new HashMap<String, String>(), getView(), new RxDataCallBack<List<Person>>() {
            @Override
            public void onSucess(List<Person> personList) {
                if (getView() != null) {
                    ((MainView) getView()).showAllPersons(personList);
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
        Subscription subscription = mMainRequest.loginOut(map, getView(), new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    ((MainView) getView()).loginOutResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((MainView) getView()).loginOutResult(false);
                }
            }
        });
        addSubscription(subscription);
    }

    public void printDeliveryInfo(final Map<String, String> params) {
        mDeliveryRequest.printDeliveryInfo(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                ToastUtil.showInfoCenterShort("打印成功");
//                clearTray(params);
            }

            @Override
            public void onFail() {

            }
        });
    }

    public void printExceptionInfo(final Map<String, String> params) {
        mDeliveryRequest.printExceptionInfo(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                ToastUtil.showInfoCenterShort("打印成功");
            }

            @Override
            public void onFail() {

            }
        });
    }

    //清空托盘
    public void clearTray(Map<String, String> params) {
        mDeliveryRequest.clearTray(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    ((MainView) getView()).clearTrayResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((MainView) getView()).clearTrayResult(false);
                }
            }
        });
    }

    public void requestHeart(Map<String, String> params) {
        mMainRequest.requestHeart(params, getView(), new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {

            }

            @Override
            public void onFail() {

            }
        }, false);
    }

    public void checkApkVersion(Map<String, String> params) {
        mMainRequest.checkApkVersion(params, getView(), new RxDataCallBack<AppUpdate>() {
            @Override
            public void onSucess(AppUpdate wsAppUpdate) {
                if (getView() != null) {
                    ((MainView) getView()).showCheckApkInfo(wsAppUpdate);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((MainView) getView()).showCheckApkInfo(null);
                }
            }
        }, true);
    }

    /**
     * 下载apk
     *
     * @param params
     * //
     */
    WSBaseObsever downObsever = null;

    public void downLoadApk(Context context, String pdfUrl, String saveUrl, DownApkProgressDialog mProgressDialog) {
        downObsever = null;
        mMainRequest.downLoadApk(pdfUrl, saveUrl, getView(), new WSRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    ((MainView) getView()).downApkResult(true, saveUrl);
                }
//                if (mProgressDialog != null && mProgressDialog.isShowing()) {
//                    mProgressDialog.dismiss();
//                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((MainView) getView()).downApkResult(false, saveUrl);
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
