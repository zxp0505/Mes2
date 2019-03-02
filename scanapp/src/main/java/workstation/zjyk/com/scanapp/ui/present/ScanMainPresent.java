package workstation.zjyk.com.scanapp.ui.present;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import workstation.zjyk.com.scanapp.modle.bean.QualityHandleDetailVO;
import workstation.zjyk.com.scanapp.modle.bean.ScanTrayInfoVo;
import workstation.zjyk.com.scanapp.modle.callback.ScanRxDataCallBack;
import workstation.zjyk.com.scanapp.modle.request.MainRequest;
import workstation.zjyk.com.scanapp.ui.views.ScanMainView;
import workstation.zjyk.com.scanapp.util.ScanConstants;

/**
 * Created by zjgz on 2018/1/20.
 */

public class ScanMainPresent extends ScanRxPresent<ScanMainView> {

    private final MainRequest mainRequest;

    public ScanMainPresent() {
        mainRequest = new MainRequest();
    }

    public void requestByScancode(Map<String, String> params) {
        mainRequest.requestByScancode(params, getView(), new ScanRxDataCallBack<ScanTrayInfoVo>() {
            @Override
            public void onSucess(ScanTrayInfoVo s) {
                if (getView() != null) {
                    getView().scanResult(s);
                }

            }

            @Override
            public void onFail(String message, Throwable throwable) {
                if (getView() != null) {
                    getView().scanResult(message, throwable);
                }
            }
        }, true);
    }

    public void requestDetail(Map<String, String> params) {
        mainRequest.requestDetail(params, getView(), new ScanRxDataCallBack<QualityHandleDetailVO>() {
            @Override
            public void onSucess(QualityHandleDetailVO s) {
                if (getView() != null) {
                    getView().showDetail(s);
                }

            }

            @Override
            public void onFail(String message, Throwable throwable) {
                if (getView() != null) {
                    getView().showDetail(null);
                }
            }
        }, true);
    }

    public void uploadImages(Map<String, String> params, List<String> images) {
        mainRequest.uploadImages(params, images, getView(), new ScanRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().upLoadResult(true);
                }

            }

            @Override
            public void onFail(String message, Throwable throwable) {
                if (getView() != null) {
                    getView().upLoadResult(false);
                }
            }
        }, true);
    }


    /**
     * 提交异常
     *
     * @param params
     */
    public void commitExcept(Map<String, String> params) {
        mainRequest.commitExcept(params, getView(), new ScanRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().commitExceptResult(true);
                }

            }

            @Override
            public void onFail(String message, Throwable throwable) {
                if (getView() != null) {
                    getView().commitExceptResult(false);
                }
            }
        }, true);
    }

    /**
     * 拒绝
     *
     * @param params
     */
    public void refuseRequest(Map<String, String> params) {
        mainRequest.refuseRequest(params, getView(), new ScanRxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().refuseResult(true);
                }

            }

            @Override
            public void onFail(String message, Throwable throwable) {
                if (getView() != null) {
                    getView().refuseResult(false);
                }
            }
        }, true);
    }

    public void downLoadPic(String url, Context context) {
        new AsyncTask<String, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (getView() != null) {
                    getView().showLoading("正在加载中...");
                }

            }

            @Override
            protected String doInBackground(String... strings) {
                String path = "";
                try {
                    File file = Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                    path = file.getAbsolutePath();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return path;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (getView() != null) {
                    getView().hideLoading();
                    getView().showDownPicResult(url,s);
                }
            }
        }.execute(url);

    }
}
