package workstation.zjyk.com.scanapp.util.dialog;

import android.content.Context;

import workstation.zjyk.com.scanapp.modle.net.ScanErrorResultClickListner;
import workstation.zjyk.com.scanapp.util.dialog.callback.ScanDialogCallBackTwo;


/**
 * This class can show custom dialog for app
 * Support title content button with custom text
 * Created by terry on 8/2/16.
 */

public class ScanDialogUtils {
    public static void showOneTipDialog(Context context, String title, String buttontext, ScanDialogCallBackTwo dialogCallBackTwo) {
        ScanOneTipDialog wsOneTipDialog = new ScanOneTipDialog(context, title, buttontext, dialogCallBackTwo);
        wsOneTipDialog.setCanceledOnTouchOutside(false);
        wsOneTipDialog.show();
    }

    public static ScanResultErrorDialog showResultErrorDialog(Context context, int code, String title, boolean isTwoTips, ScanErrorResultClickListner errorResultClickListner) {
        ScanResultErrorDialog resultErrorDialog = new ScanResultErrorDialog(context, code, title, isTwoTips, errorResultClickListner);
        resultErrorDialog.setCanceledOnTouchOutside(false);
        resultErrorDialog.show();
        return resultErrorDialog;
    }

    public static ScanHandEntryBarcodeDialog showEntryCodeDialog(Context context, String title, ScanDialogCallBackTwo callBackTwo) {
        ScanHandEntryBarcodeDialog resultErrorDialog = new ScanHandEntryBarcodeDialog(context, title, callBackTwo);
        resultErrorDialog.setCanceledOnTouchOutside(false);
        resultErrorDialog.show();
        return resultErrorDialog;
    }


}
