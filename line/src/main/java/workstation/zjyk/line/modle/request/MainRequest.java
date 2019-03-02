package workstation.zjyk.line.modle.request;

import workstation.zjyk.line.modle.bean.AppUpdate;
import workstation.zjyk.line.modle.bean.InitWorkStationPad;
import workstation.zjyk.line.modle.bean.Person;
import workstation.zjyk.line.modle.bean.VersionBean;
import workstation.zjyk.line.modle.bean.WorkStationVo;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.net.ApiManager;
import workstation.zjyk.line.modle.net.ChildHttpResultSubscriber;
import workstation.zjyk.line.modle.net.ErrorCode;
import workstation.zjyk.line.modle.net.HeartApiManager;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.util.URLBuilder;

import java.util.List;
import java.util.Map;

import rx.Subscription;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.download.DownloadProgressListener;
import workstation.zjyk.workstation.modle.net.WSApiManager;
import workstation.zjyk.workstation.modle.net.WSChildHttpResultObsever;
import workstation.zjyk.workstation.modle.net.WSErrorCode;

/**
 * Created by zjgz on 2017/11/3.
 */

public class MainRequest {
    public Subscription getPadVersion(Map<String, String> parms, final BaseView baseView, final RxDataCallBack<VersionBean> callBack) {
        return ApiManager.getInstance().post(URLBuilder.PAD_VERSION_URL, parms, new ChildHttpResultSubscriber<VersionBean>(baseView, VersionBean.class) {
            @Override
            public void _onSuccess(VersionBean versionBean) {
                baseView.hideLoadingDialog();
                callBack.onSucess(versionBean);

            }


            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }
        });
    }

    public Subscription checkPadVerify(Map<String, String> parms, final BaseView baseView, final RxDataCallBack<InitWorkStationPad> callBack) {
        return ApiManager.getInstance().get(URLBuilder.PAD_VERIFY_URL, parms, new ChildHttpResultSubscriber<InitWorkStationPad>(baseView, InitWorkStationPad.class) {
            @Override
            public void _onSuccess(InitWorkStationPad initWorkStationPad) {
                baseView.hideLoadingDialog();
                callBack.onSucess(initWorkStationPad);

            }


            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }


        });
    }
    public Subscription testServer(Map<String, String> parms, final BaseView baseView, final RxDataCallBack<WorkStationVo> callBack) {
        return ApiManager.getInstance().get(URLBuilder.GET_STATION_NAME, parms, new ChildHttpResultSubscriber<WorkStationVo>(baseView, WorkStationVo.class) {
            @Override
            public void _onSuccess(WorkStationVo workStationVo) {
                callBack.onSucess(workStationVo);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (code == ErrorCode.CODE_DATA_NULL || code == ErrorCode.CODE_RESULT_ERROR ) {
                    callBack.onSucess(null);
                    return;
                }
                callBack.onFail();
                baseView.hideLoadingDialog();
            }
        });
    }
    public Subscription getStationName(Map<String, String> parms, final BaseView baseView, final RxDataCallBack<WorkStationVo> callBack) {
        return ApiManager.getInstance().get(URLBuilder.GET_STATION_NAME, parms, new ChildHttpResultSubscriber<WorkStationVo>(baseView, WorkStationVo.class) {
            @Override
            public void _onSuccess(WorkStationVo workStationVo) {
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

    public Subscription getStationAllPerson(Map<String, String> parms, final BaseView baseView, final RxDataCallBack<List<Person>> callBack, boolean loading) {
        return ApiManager.getInstance().get(URLBuilder.GET_ALL_PERSON, parms, new ChildHttpResultSubscriber<List<Person>>(baseView, Person.class) {
            @Override
            public void _onSuccess(List<Person> personList) {
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


    public Subscription requestHeart(Map<String, String> parms, final BaseView baseView, final RxDataCallBack<String> callBack, boolean loading) {
        return HeartApiManager.getInstance().post(URLBuilder.SEND_HEART, parms, new ChildHttpResultSubscriber<String>(baseView, null) {
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

            @Override
            public void _onError(int code, String error, Throwable e) {

//                super._onError(code, error, e);
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

    public Subscription loginOut(Map<String, String> parms, final BaseView baseView, final RxDataCallBack<String> callBack) {
        return ApiManager.getInstance().get(URLBuilder.LOGIN_OUT, parms, new ChildHttpResultSubscriber<String>(baseView, null) {
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

    public Subscription checkApkVersion(Map<String, String> parms, final BaseView baseView, final RxDataCallBack<AppUpdate> callBack, boolean isShowLoading) {
        return ApiManager.getInstance().get(URLBuilder.CHECK_APK_VERSION, parms, new ChildHttpResultSubscriber<AppUpdate>(baseView, AppUpdate.class) {
            @Override
            public void _onSuccess(AppUpdate s) {
                callBack.onSucess(s);
                if (isShowLoading) {
                    baseView.hideLoadingDialog();
                }

            }

            @Override
            public void _showLoadingDialog(String message) {
                if (isShowLoading) {
                    baseView.hideLoadingDialog();
                }
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                if (isShowLoading) {
                    baseView.hideLoadingDialog();
                }
                callBack.onFail();
            }
        });
    }

    public void downLoadApk(String papkUrl, String saveUrl, final BaseView baseView, final WSRxDataCallBack<String> callBack, boolean isShowLoad, DownloadProgressListener downloadProgressListener) {
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
}
