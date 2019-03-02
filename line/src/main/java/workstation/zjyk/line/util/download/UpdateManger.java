package workstation.zjyk.line.util.download;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.VersionBean;
import workstation.zjyk.line.ui.BaseApplication;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppUtils;
import workstation.zjyk.line.util.Constants;
import workstation.zjyk.line.util.dialog.DialogUtils;
import workstation.zjyk.line.util.dialog.TipDialog;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * apk升级类
 */
public class UpdateManger {

    private static final String TAG = UpdateManger.class.getSimpleName();
    public Activity mContext;
    Timer timer;
    /**
     * 是否需要更新
     */
    private boolean needNotice;

    private String apkName = "";

    private UpdateCallBack updateCallBack;


    public UpdateManger(Activity context) {
        this.mContext = context;
    }

    public void startCheckUpdate(boolean needNotice) {
        this.needNotice = needNotice;
        if (AppUtils.isNetwork(mContext)) {
            versionUpdateAsync();
        }
    }


    /**
     * 检测是否是最新版本，如果是则更新
     * 逻辑为，检测当前版本是否小于版本最低要求，如果最低要求没有设置，则跳过，负责提示更新最新版本
     * 检测当前版本是否小于最新版本，如果是，提示更新最新版本
     *
     * @param version
     * @return
     */
    public boolean checkVersion(final VersionBean version) {
        if (!TextUtils.isEmpty(version.getMinimumVersion())) {
            // 小于最低版本要求更新app
            if (AppUtils.isNewer(version.getMinimumVersion(), AppUtils.getVersionName(BaseApplication.getInstance()))) {
                update(version.getIsForcedUpdate(), version.getLatestInfo(), version.getDownLoadUrl());
                return true;
            } else {// 等于或者大于最低版本app
                return false;
            }
        }
        // 最低版本没配置，小于最最新版本app
        if (AppUtils.isNewer(version.getLatestVersion(), AppUtils.getVersionName(BaseApplication.getInstance()))){
            update(version.getIsForcedUpdate(), version.getLatestInfo(), version.getDownLoadUrl());
            return true;
        }
        return false;
    }

    private void update(final String isForcedUpdate, final String latestInfo, final String downLoadUrl) {
        String cancelMsg = mContext.getString(R.string.st_cancel);
        if (!TextUtils.isEmpty(isForcedUpdate) && "1".equals(isForcedUpdate)) {
            cancelMsg = mContext.getString(R.string.st_exit);
        }
        if (mContext != null && !mContext.isFinishing()) {
            String hint = latestInfo;
            if (TextUtils.isEmpty(hint)) {
                hint = mContext.getString(R.string.st_update_v);
            }
//            DialogUtils.showUpdateDialog(mContext, mContext.getString(R.string.update_notice_title), hint, cancelMsg, mContext.getString(R.string.confirm), new TipDialog.OnDialogBtnClickListener() {
//                @Override
//                public void onLeftBtnClicked(TipDialog paramTipDialog) {
//                    //CANCEL
//                    if (!TextUtils.isEmpty(isForcedUpdate) && "1".equals(isForcedUpdate)) {
//                        System.exit(0);
//                    } else {
//                        if (updateCallBack != null) {
//                            updateCallBack.cancel();
//                        }
//                    }
//                }
//
//                @Override
//                public void onRightBtnClicked(TipDialog paramTipDialog) {
//                    //CONFIRM
//                    AppUpdate appUpdate = new AppUpdate(mContext);
//                    try {
//                        apkName = Constants.APK_NAME
//                                + AppUtils.getVersionName(BaseApplication.getInstance())
//                                + ".apk";
//                        String apkPath = Environment
//                                .getExternalStorageDirectory()
//                                .toString()
//                                + "/Download" + "/" + apkName;
//                        if (new File(apkPath).exists())
//                            new File(apkPath).delete();
//                        appUpdate.downApk(downLoadUrl,
//                                apkName, mContext.getString(R.string.st_hmm_text36_3));
//                        // mContext.registerReceiver(receiver, new
//                        // IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//                        showDownloadProgress();
//                        if (timer != null) {
//                            timer.cancel();
//                            timer = null;
//                        }
//                        timer = new Timer();
//                        timer.schedule(new TimerTask() {
//
//                            @Override
//                            public void run() {
//                                queryDownloadStatus();
//                            }
//                        }, 1, 500);
//
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

        }
    }

    protected void showDownloadProgress() {
        _pd = new ProgressDialog(mContext);
        _pd.setMessage(mContext.getString(R.string.st_hmm_text36_2));
        _pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        _pd.setProgressNumberFormat(null);
        _pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
            }
        });
        _pd.setCanceledOnTouchOutside(false);
        _pd.setCancelable(false);
        _pd.show();
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            queryDownloadStatus();
        }
    };
    private ProgressDialog _pd;

    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(AppUpdate.downId);
        DownloadManager downloadManager = (DownloadManager) mContext
                .getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c
                    .getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.v("down", "STATUS_PAUSED");
                case DownloadManager.STATUS_PENDING:
                    Log.v("down", "STATUS_PENDING");
                case DownloadManager.STATUS_RUNNING:
                    Log.v("down", "STATUS_RUNNING");
                    long toalSize = c
                            .getLong(c
                                    .getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    long currentSize = c
                            .getLong(c
                                    .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int tp = (int) (currentSize * 100 / toalSize);
                    Log.d(TAG, "tp:" + tp);
                    if (_pd != null) {
                        _pd.setProgress(tp);
                    }
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    if (_pd != null) {
                        _pd.setProgress(100);
                    }
                    String apkPath = Environment.getExternalStorageDirectory()
                            .toString() + "/Download" + "/" + apkName;
                    AppUpdate.setupApk(mContext, new File(apkPath));
                    // if(receiver!=null)
                    // mContext.unregisterReceiver(receiver);
                    cancelTimeTask();
//                    AppActivityManager.getInstance().popAllActivity();
                    break;
                case DownloadManager.STATUS_FAILED:
                    Log.v("down", "STATUS_FAILED");
                    cancelTimeTask();
                    downloadManager.remove(AppUpdate.downId);
                    break;
            }
        }
    }

    public void cancelTimeTask() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void versionUpdateAsync() {
//        Jarvis.getInstance().createRequestEngine().getUpgradeInfo(AppApplication.getInstance(), new IVolleyResListener<VersionBean>() {
//            @Override
//            public void onSuccess(VersionBean o) {
//                if (o != null && o.data != null) {
//                    checkVersion(o.data);
//                }
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
    }

    public interface UpdateCallBack {
        // 取消，暂不更新
        void cancel();

    }

    public void setUpdateCallBack(UpdateCallBack updateCallBack) {
        this.updateCallBack = updateCallBack;
    }
}
