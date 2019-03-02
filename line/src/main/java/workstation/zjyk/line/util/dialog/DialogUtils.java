package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import workstation.zjyk.line.modle.bean.AppUpdate;
import workstation.zjyk.line.modle.bean.ExceptionDetailBean;
import workstation.zjyk.line.modle.bean.GoodsBean;
import workstation.zjyk.line.modle.bean.LineDistributeHistoryVO;
import workstation.zjyk.line.modle.bean.LineMaterielStorageManagerVo;
import workstation.zjyk.line.modle.bean.MaterielVo;
import workstation.zjyk.line.modle.bean.WorkStationDistributeMateriel;
import workstation.zjyk.line.modle.bean.YesOrNoEnum;
import workstation.zjyk.line.modle.net.ErrorResultClickListner;
import workstation.zjyk.line.util.DialogCallBackTwo;
import workstation.zjyk.line.util.dialog.callback.DialogCallbackMakeSure;
import workstation.zjyk.line.util.dialog.callback.EnclosureCallBack;

import java.util.List;


/**
 * This class can show custom dialog for app
 * Support title content button with custom text
 * Created by terry on 8/2/16.
 */

public class DialogUtils {

    /**
     * This dialog has title content and two button
     *
     * @param context
     * @param title
     * @param content
     * @param leftBtnText
     * @param rightBtnText
     * @param onDialogBtnClickListener
     */
    public static void showTipDialog(Context context, String title, String content, String leftBtnText, String rightBtnText, TipDialog.OnDialogBtnClickListener onDialogBtnClickListener) {
        if (content == null) {
            return;
        }
        TipDialog tipDialog = new TipDialog(context);
        tipDialog.setTitle(title);
        tipDialog.setContent(content);
        tipDialog.setLeftBtnText(leftBtnText);
        tipDialog.setRightBtnText(rightBtnText);
        tipDialog.setOnBtnClickListener(onDialogBtnClickListener);
        try {
            tipDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This dialog has content and two button but no title
     *
     * @param context
     * @param content
     * @param leftBtnText
     * @param rightBtnText
     * @param onDialogBtnClickListener
     */
    public static void showTipDialogNoTitle(Context context, String content, String leftBtnText, String rightBtnText, TipDialog.OnDialogBtnClickListener onDialogBtnClickListener) {
        if (content == null) {
            return;
        }
        TipDialog tipDialog = new TipDialog(context);
        tipDialog.hideTitle();
        tipDialog.setContent(content);
        tipDialog.setLeftBtnText(leftBtnText);
        tipDialog.setRightBtnText(rightBtnText);
        tipDialog.setOnBtnClickListener(onDialogBtnClickListener);
        try {
            tipDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This dialog is support for update
     *
     * @param context
     * @param title
     * @param content
     * @param leftBtnText
     * @param rightBtnText
     * @param onDialogBtnClickListener
     */
//    public static void showUpdateDialog(Context context, String title, String content, String leftBtnText, String rightBtnText, TipDialog.OnDialogBtnClickListener onDialogBtnClickListener) {
//        if (content == null) {
//            return;
//        }
//        TipDialog tipDialog = new TipDialog(context);
//        tipDialog.setTitle(title);
//        tipDialog.setContent(content);
//        tipDialog.setLeftBtnText(leftBtnText);
//        tipDialog.setRightBtnText(rightBtnText);
//        tipDialog.setOnBtnClickListener(onDialogBtnClickListener);
//        tipDialog.setCancelable(false);
//        try {
//            tipDialog.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    /**
     * This dialog has one button title and content
     *
     * @param context
     * @param title
     * @param content
     * @param leftBtnText
     * @param onConfirmListener
     */
    public static void showTipDialogOneButton(Context context, String title, String content, String leftBtnText, TipDialogOneButton.OnConfirmListener onConfirmListener) {
        if (content == null) {
            return;
        }
        TipDialogOneButton tipDialogOneButton = new TipDialogOneButton(context);
        tipDialogOneButton.setTitle(title);
        tipDialogOneButton.setContent(content);
        tipDialogOneButton.setBtnText(leftBtnText);
        tipDialogOneButton.setOnConfirmListener(onConfirmListener);
        try {
            tipDialogOneButton.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This dialog has one button content but no title
     *
     * @param context
     * @param content
     * @param leftBtnText
     * @param onConfirmListener
     */
    public static void showTipDialogOneButtonNoTitle(Context context, String content, String leftBtnText, TipDialogOneButton.OnConfirmListener onConfirmListener) {
        if (content == null) {
            return;
        }
        TipDialogOneButton tipDialogOneButton = new TipDialogOneButton(context);
        tipDialogOneButton.hideTitle();
        tipDialogOneButton.setContent(content);
        tipDialogOneButton.setBtnText(leftBtnText);
        tipDialogOneButton.setOnConfirmListener(onConfirmListener);
        try {
            tipDialogOneButton.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This dialog has one button content but no title
     * 点击其他地方不能关闭
     *
     * @param context
     * @param content
     * @param leftBtnText
     * @param onConfirmListener
     */
    public static void showTipDialogOneButtonNoTitleNoCancelable(Context context, String content, String leftBtnText, TipDialogOneButton.OnConfirmListener onConfirmListener) {
        if (content == null) {
            return;
        }
        TipDialogOneButton tipDialogOneButton = new TipDialogOneButton(context);
        tipDialogOneButton.hideTitle();
        tipDialogOneButton.setContent(content);
        tipDialogOneButton.setBtnText(leftBtnText);
        tipDialogOneButton.setCancelable(false);// 点击其他地方不能关闭
        tipDialogOneButton.setOnConfirmListener(onConfirmListener);
        try {
            tipDialogOneButton.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This dialog has one button title and content
     *
     * @param context
     * @param title
     * @param content
     * @param leftBtnText
     * @param onConfirmListener
     */
    public static void showTipDialogOneButtonNoCancelable(Context context, String title, String content, String leftBtnText, TipDialogOneButton.OnConfirmListener onConfirmListener) {
        if (content == null) {
            return;
        }
        TipDialogOneButton tipDialogOneButton = new TipDialogOneButton(context);
        tipDialogOneButton.setTitle(title);
        tipDialogOneButton.setContent(content);
        tipDialogOneButton.setBtnText(leftBtnText);
        tipDialogOneButton.setCancelable(false);// 点击其他地方不能关闭
        tipDialogOneButton.setOnConfirmListener(onConfirmListener);
        try {
            tipDialogOneButton.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showDistribuNumberDialog(@NonNull Context context, WorkStationDistributeMateriel bean, DialogCallBackTwo callBack) {
        TipDialogEntryNumber tipDialogEntryNumber = new TipDialogEntryNumber(context);
        tipDialogEntryNumber.setBeanAndDialogCallBack(bean, callBack);
        tipDialogEntryNumber.setCanceledOnTouchOutside(false);
        tipDialogEntryNumber.show();
    }

    public static void showExceptionNumberDialog(@NonNull Context context, ExceptionDetailBean exceptionDetailBean, DialogCallBackTwo callBack) {
        ExceptionEntryNumber tipDialogEntryNumberTwo = new ExceptionEntryNumber(context);
        tipDialogEntryNumberTwo.setBeanAndDialogCallBack(exceptionDetailBean, callBack);
        tipDialogEntryNumberTwo.setCanceledOnTouchOutside(false);
        tipDialogEntryNumberTwo.show();
//        tipDialogEntryNumberTwo.showInput();
    }

    /**
     * 入仓dialog
     *
     * @param context
     * @param bean
     * @param callBack
     */
    public static void showInWareNumberDialog(@NonNull Context context, MaterielVo bean, DialogCallBackTwo callBack) {
        TipDialogEntryNumberTwo tipDialogEntryNumberTwo = new TipDialogEntryNumberTwo(context);
        tipDialogEntryNumberTwo.setBeanAndDialogCallBack(bean, callBack);
        tipDialogEntryNumberTwo.setCanceledOnTouchOutside(false);
        tipDialogEntryNumberTwo.show();
    }

    public static void showReciverMakeSureDialog(Context context, List<GoodsBean> datas, DialogCallbackMakeSure callbackMakeSure) {
        ReciverMakeSureDialog reciverMakeSureDialog = new ReciverMakeSureDialog(context, callbackMakeSure);
        reciverMakeSureDialog.setData(datas);
        reciverMakeSureDialog.setCanceledOnTouchOutside(false);
        reciverMakeSureDialog.show();
    }

    /**
     * 展示工位分配成功
     *
     * @param context
     * @param view
     */
    public static void showStationSuccessDialog(Context context, View view, String station, DialogCallBackTwo callBack) {
        final StationSuccessDialog stationSuccessDialog = new StationSuccessDialog(context, callBack);
        stationSuccessDialog.setData(station);
//        if(view != null){
//            view.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (stationSuccessDialog.isShowing()) {
//                        stationSuccessDialog.dismiss();
//                    }
//                }
//            }, 1000);
//        }
        stationSuccessDialog.show();
    }

    /**
     * 改变员工dialgo
     *
     * @param context
     * @param title
     * @param callBack
     */
    public static void showChangeWorkerDialog(Context context, String title, DialogCallBackTwo callBack) {
        ChangeWorkerDialog changeWorkerDialog = new ChangeWorkerDialog(context, callBack);
        changeWorkerDialog.setData(title);
        changeWorkerDialog.setCanceledOnTouchOutside(false);
        changeWorkerDialog.show();
    }

    public static RemindDialog showRemindDialog(Context context, String title, DialogCallBackTwo callBack) {
        RemindDialog remindDialog = new RemindDialog(context, callBack);
//        remindDialog.setData(title, yesOrNoEnum);
        remindDialog.setCanceledOnTouchOutside(false);
//        remindDialog.show();
        return remindDialog;
    }

    public static RemindTwoDialog showRemindTwoDialog(Context context, String title, DialogCallBackTwo callBack) {
        RemindTwoDialog remindDialog = new RemindTwoDialog(context, title, callBack);
        remindDialog.setCanceledOnTouchOutside(false);
        return remindDialog;
    }

    public static void showBondHsitoryRecordDialog(Context context, String title, List<LineDistributeHistoryVO> datas, DialogCallBackTwo callBack) {
        BondHistoryRecordDialog remindDialog = new BondHistoryRecordDialog(context, callBack);
        remindDialog.setData(datas);
        remindDialog.setCanceledOnTouchOutside(false);
        remindDialog.show();
    }


    /**
     * 投递成功 并打印dialog
     *
     * @param context
     * @param dialogCallBackTwo
     * @return
     */
    public static DeliverySuccessDialog showDelivvarySucessDialog(Context context, String title, DialogCallBackTwo dialogCallBackTwo) {
        DeliverySuccessDialog deliverySuccessDialog = new DeliverySuccessDialog(context, dialogCallBackTwo);
        deliverySuccessDialog.setCanceledOnTouchOutside(false);
        deliverySuccessDialog.setTitle(title);
        deliverySuccessDialog.show();
        return deliverySuccessDialog;
    }

    /**
     * 退出登陆dialog
     *
     * @param context
     * @param dialogCallBackTwo
     */
    public static void showLoginOutDialog(Context context, DialogCallBackTwo dialogCallBackTwo) {
        LoginOutDialog deliverySuccessDialog = new LoginOutDialog(context, dialogCallBackTwo);
        deliverySuccessDialog.setCanceledOnTouchOutside(false);
        deliverySuccessDialog.show();
    }

    public static void showResultErrorDialog(Context context, int code, String title, ErrorResultClickListner errorResultClickListner) {
        ResultErrorDialog resultErrorDialog = new ResultErrorDialog(context, code, title, errorResultClickListner);
        resultErrorDialog.setCanceledOnTouchOutside(false);
        resultErrorDialog.show();

    }

    public static void showCheckDialog(Context context, String title, String detail) {
        CheckDetailDialog checkDetailDialog = new CheckDetailDialog(context, title, detail);
        checkDetailDialog.setCanceledOnTouchOutside(false);
        checkDetailDialog.show();

    }

    /**
     * 展示库存管理的修改数量的dialog
     */
    public static void showInventoryDialog(Context context, LineMaterielStorageManagerVo lineMaterielStorageManagerVo, InventoryDialog.InventoryDialogCallback inventoryDialogCallback) {
        InventoryDialog inventoryDialog = new InventoryDialog(context, lineMaterielStorageManagerVo, inventoryDialogCallback);
        inventoryDialog.setCanceledOnTouchOutside(false);
        inventoryDialog.show();
    }

    /**
     * 展示输入barocode的dialog
     */
    public static void showEntryBarcodeDialog(Context context, String title, DialogCallBackTwo dialogCallBackTwo) {
        HandEntryBarcodeDialog handEntryBarcodeDialog = new HandEntryBarcodeDialog(context, title, dialogCallBackTwo);
        handEntryBarcodeDialog.setCanceledOnTouchOutside(false);
        handEntryBarcodeDialog.show();
    }

    /**
     * 接收后台推送 提示强制退出的dialog
     */
    public static void showInterceptQuitDialog(Context context, String title, String message, DialogCallBackTwo dialogCallBackTwo) {
        InterceptQuitDialog interceptQuitDialog = new InterceptQuitDialog(context, title, message, dialogCallBackTwo);
        interceptQuitDialog.setCanceledOnTouchOutside(false);
        interceptQuitDialog.show();
    }

    /**
     * 网络不可用的dialog
     */
    public static void showNetUneableDialog(Context context, String title, DialogCallBackTwo dialogCallBackTwo) {
        NetUnEableDialog netUnEableDialog = new NetUnEableDialog(context, title, dialogCallBackTwo);
        netUnEableDialog.setCanceledOnTouchOutside(false);
        netUnEableDialog.show();
    }

    public static DownApkProgressDialog showUpdateDialog(Context context, String title, AppUpdate wsAppUpdate, DialogCallBackTwo dialogCallBackTwo) {
        DownApkProgressDialog wsDownApkProgressDialog = new DownApkProgressDialog(context, title, dialogCallBackTwo);
        wsDownApkProgressDialog.setCanceledOnTouchOutside(false);
        wsDownApkProgressDialog.setData(wsAppUpdate);
        wsDownApkProgressDialog.show();
        return wsDownApkProgressDialog;
    }

    public static void showPushOneDialog(Context context, String title, int code, DialogCallBackTwo dialogCallBackTwo) {
        PushOneDialog wsPushOneDialog = new PushOneDialog(context, title, code, dialogCallBackTwo);
        wsPushOneDialog.setCanceledOnTouchOutside(false);
        wsPushOneDialog.show();
    }

    public static SelectDateDialog showSelectDateDialog(Context context, String title, DialogCallBackTwo callBack) {
        SelectDateDialog wsSelectDateDialog = new SelectDateDialog(context, title, callBack);
        wsSelectDateDialog.setCanceledOnTouchOutside(false);
        return wsSelectDateDialog;
    }

    public static LookEnclosureDialog showLookEnclosureDialog(Context context, String title, EnclosureCallBack enclosureCallBack) {
        LookEnclosureDialog wsLookEnclosureDialog = new LookEnclosureDialog(context, title, enclosureCallBack);
        wsLookEnclosureDialog.setCanceledOnTouchOutside(false);
        return wsLookEnclosureDialog;
    }

}
