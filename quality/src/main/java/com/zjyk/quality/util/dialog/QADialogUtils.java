package com.zjyk.quality.util.dialog;

import android.content.Context;

import com.zjyk.quality.modle.net.QAErrorResultClickListner;
import com.zjyk.quality.util.dialog.callback.QADialogCallBackTwo;


/**
 * This class can show custom dialog for app
 * Support title content button with custom text
 * Created by terry on 8/2/16.
 */

public class QADialogUtils {
    public static void showResultErrorDialog(Context context, int code, String title, QAErrorResultClickListner errorResultClickListner) {
        QAResultErrorDialog resultErrorDialog = new QAResultErrorDialog(context, code, title, errorResultClickListner);
        resultErrorDialog.setCanceledOnTouchOutside(false);
        resultErrorDialog.show();

    }

    /**
     * 改变员工dialgo
     *
     * @param context
     * @param title
     * @param callBack
     */
    public static void showChangeWorkerDialog(Context context, String title, QADialogCallBackTwo callBack) {
        QAChangeWorkerDialog changeWorkerDialog = new QAChangeWorkerDialog(context, callBack);
        changeWorkerDialog.setData(title);
        changeWorkerDialog.setCanceledOnTouchOutside(false);
        changeWorkerDialog.show();
    }
}
