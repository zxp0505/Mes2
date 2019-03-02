package com.zjyk.repair.util.dialog;

import android.content.Context;

import com.zjyk.repair.modle.net.RPErrorResultClickListner;
import com.zjyk.repair.util.dialog.callback.RPDialogCallBackTwo;


/**
 * This class can show custom dialog for app
 * Support title content button with custom text
 * Created by terry on 8/2/16.
 */

public class RPDialogUtils {
    public static RPResultErrorDialog showResultErrorDialog(Context context, int code, String title, boolean isTwoTips, RPErrorResultClickListner errorResultClickListner) {
        RPResultErrorDialog resultErrorDialog = new RPResultErrorDialog(context, code, title, isTwoTips, errorResultClickListner);
        resultErrorDialog.setCanceledOnTouchOutside(false);
        resultErrorDialog.show();
        return resultErrorDialog;
    }

    /**
     * 改变员工dialgo
     *
     * @param context
     * @param title
     * @param callBack
     */
    public static void showChangeWorkerDialog(Context context, String title, RPDialogCallBackTwo callBack) {
        RPChangeWorkerDialog changeWorkerDialog = new RPChangeWorkerDialog(context, callBack);
        changeWorkerDialog.setData(title);
        changeWorkerDialog.setCanceledOnTouchOutside(false);
        changeWorkerDialog.show();
    }

    public static void showInterceptQuitDialog(Context context, String title, String message, RPDialogCallBackTwo dialogCallBackTwo) {
        RPInterceptQuitDialog wsReviewWorkDialog = new RPInterceptQuitDialog(context, title, message, dialogCallBackTwo);
        wsReviewWorkDialog.setCanceledOnTouchOutside(false);
        wsReviewWorkDialog.show();
    }

    public static RPPushOneDialog showPushOneDialog(Context context, String title, int code, RPDialogCallBackTwo dialogCallBackTwo) {
        RPPushOneDialog wsPushOneDialog = new RPPushOneDialog(context, title, code, dialogCallBackTwo);
        wsPushOneDialog.setCanceledOnTouchOutside(false);
        wsPushOneDialog.show();
        return wsPushOneDialog;
    }
}
