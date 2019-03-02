package workstation.zjyk.workstation.util.dialog;

import android.content.Context;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import workstation.zjyk.workstation.modle.bean.WSAppUpdate;
import workstation.zjyk.workstation.modle.bean.WSExceptionOutRecordVo;
import workstation.zjyk.workstation.modle.bean.WSInputWipInfo;
import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSMaterialTray;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;
import workstation.zjyk.workstation.modle.bean.WSWip;
import workstation.zjyk.workstation.modle.bean.WSWipTray;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTrayTaskMainInfoVo;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.ui.application.WSBaseApplication;
import workstation.zjyk.workstation.ui.pop.WSEnclosureCallBack;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackThree;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;


/**
 * This class can show custom dialog for app
 * Support title content button with custom text
 * Created by terry on 8/2/16.
 */

public class WSDialogUtils {
    public static void showOneTipDialog(Context context, String title, String buttontext, WSDialogCallBackTwo dialogCallBackTwo) {
        WSOneTipDialog wsOneTipDialog = new WSOneTipDialog(context, title, buttontext, dialogCallBackTwo);
        wsOneTipDialog.setCanceledOnTouchOutside(false);
        wsOneTipDialog.show();
        setCurrentDialog(wsOneTipDialog);
    }

    public static void showTwoTipDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        WSTwoTipDialog wsTwoTipDialog = new WSTwoTipDialog(context, title, dialogCallBackTwo);
        wsTwoTipDialog.setCanceledOnTouchOutside(false);
        wsTwoTipDialog.show();
        setCurrentDialog(wsTwoTipDialog);
    }

    public static WSResultErrorDialog showResultErrorDialog(Context context, int code, String title, boolean isTwoTips, WSErrorResultClickListner errorResultClickListner) {
        WSResultErrorDialog resultErrorDialog = new WSResultErrorDialog(context, code, title, isTwoTips, errorResultClickListner);
        resultErrorDialog.setCanceledOnTouchOutside(false);
        resultErrorDialog.show();
        setCurrentDialog(resultErrorDialog);
        return resultErrorDialog;
    }

    public static void showReciveredTrayDialog(Context context, String title, WSWorkStationTrayTaskMainInfoVo wsWorkStationTrayTaskMainInfoVo, WSDialogCallBackTwo callBack) {
        WSReciveredTrayDialog wsReciveredTrayDialog = new WSReciveredTrayDialog(context, title, callBack);
        wsReciveredTrayDialog.setCanceledOnTouchOutside(false);
        wsReciveredTrayDialog.setData(wsWorkStationTrayTaskMainInfoVo);
        wsReciveredTrayDialog.show();
        setCurrentDialog(wsReciveredTrayDialog);
    }

    public static WSSelectDateDialog showSelectDateDialog(Context context, String title, WSDialogCallBackTwo callBack) {
        WSSelectDateDialog wsSelectDateDialog = new WSSelectDateDialog(context, title, callBack);
        wsSelectDateDialog.setCanceledOnTouchOutside(false);
        setCurrentDialog(wsSelectDateDialog);
        return wsSelectDateDialog;
    }

    public static WSUserRecordDetailDialog showUserRecordDetailDialog(Context context, String title, WSDialogCallBackTwo callBack) {
        WSUserRecordDetailDialog wsUserRecordDetailDialog = new WSUserRecordDetailDialog(context, title, callBack);
        wsUserRecordDetailDialog.setCanceledOnTouchOutside(false);
        setCurrentDialog(wsUserRecordDetailDialog);
        return wsUserRecordDetailDialog;
    }


    /**
     * 改变员工dialgo
     *
     * @param context
     * @param title
     * @param callBack
     */
    public static void showChangeWorkerDialog(Context context, String title, WSDialogCallBackTwo callBack) {
        WSChangeWorkerDialog changeWorkerDialog = new WSChangeWorkerDialog(context, callBack);
        changeWorkerDialog.setData(title);
        changeWorkerDialog.setCanceledOnTouchOutside(false);
        changeWorkerDialog.show();
        setCurrentDialog(changeWorkerDialog);
    }


    public static WSLookTrayListDialog showLookTrayListDialog(Context context, String title, List<MultiItemEntity> datas) {
        WSLookTrayListDialog wsLookTrayListDialog = new WSLookTrayListDialog(context, title, datas);
        wsLookTrayListDialog.setCanceledOnTouchOutside(false);
        wsLookTrayListDialog.show();
        setCurrentDialog(wsLookTrayListDialog);
        return wsLookTrayListDialog;
    }

    public static WSLookTrayListMaterailDialog showLookTrayListMaterailDialog(Context context, String title, List<MultiItemEntity> datas) {
        WSLookTrayListMaterailDialog wsLookTrayListDialog = new WSLookTrayListMaterailDialog(context, title, datas);
        wsLookTrayListDialog.setCanceledOnTouchOutside(false);
        wsLookTrayListDialog.show();
        setCurrentDialog(wsLookTrayListDialog);
        return wsLookTrayListDialog;
    }

    public static WSUpdateStationDialog showUpdateStationDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        WSUpdateStationDialog wsUpdateStationDialog = new WSUpdateStationDialog(context, title, dialogCallBackTwo);
        wsUpdateStationDialog.setCanceledOnTouchOutside(false);
        setCurrentDialog(wsUpdateStationDialog);
        return wsUpdateStationDialog;
    }

    public static WSOutPutInsoectionDialog showOutPutInsoectionDialog(Context context, String title, WSDialogCallBackThree dialogCallBack) {
        WSOutPutInsoectionDialog wsOutPutInsoectionDialog = new WSOutPutInsoectionDialog(context, title, dialogCallBack);
        wsOutPutInsoectionDialog.setCanceledOnTouchOutside(false);
        setCurrentDialog(wsOutPutInsoectionDialog);
        return wsOutPutInsoectionDialog;
    }

    /**
     * 工作提报 数量输入
     *
     * @param context
     * @param title
     * @param bean
     * @param dialogCallBack
     */
    public static WSReportWorkEntryNumber showReportWorkEntryNumberDialog(Context context, String title, WSProcedureStep bean, WSDialogCallBackTwo dialogCallBack) {
        WSReportWorkEntryNumber wsReportWorkEntryNumber = new WSReportWorkEntryNumber(context);
        wsReportWorkEntryNumber.setBeanAndDialogCallBack(bean, title, dialogCallBack);
        wsReportWorkEntryNumber.setCanceledOnTouchOutside(false);
        wsReportWorkEntryNumber.show();
        setCurrentDialog(wsReportWorkEntryNumber);
        return wsReportWorkEntryNumber;
    }

    /**
     * 异常物料输入dialog
     *
     * @param context
     * @param title
     * @param bean
     * @param dialogCallBack
     */
    public static void showExceptMaterailEntryNumberDialog(Context context, String title, WSMaterial bean, WSDialogCallBackTwo dialogCallBack) {
        WSReportWorkEntryNumber wsReportWorkEntryNumber = new WSReportWorkEntryNumber(context);
        wsReportWorkEntryNumber.setBeanAndDialogCallBack(bean, title, dialogCallBack);
        wsReportWorkEntryNumber.setCanceledOnTouchOutside(false);
        wsReportWorkEntryNumber.show();
        setCurrentDialog(wsReportWorkEntryNumber);
    }

    /**
     * 返工物料输入
     *
     * @param context
     * @param title
     * @param bean
     * @param dialogCallBack
     */
    public static void showReviewWorkEntryNumberDialog(Context context, String title, WSInputWipInfo bean, WSDialogCallBackTwo dialogCallBack) {
        WSReportWorkEntryNumber wsReportWorkEntryNumber = new WSReportWorkEntryNumber(context);
        wsReportWorkEntryNumber.setBeanAndDialogCallBack(bean, title, dialogCallBack);
        wsReportWorkEntryNumber.setCanceledOnTouchOutside(false);
        wsReportWorkEntryNumber.show();
        setCurrentDialog(wsReportWorkEntryNumber);
    }

    /**
     * 异常在制品 dialog
     *
     * @param context
     * @param title
     * @param bean
     * @param dialogCallBack
     */
    public static void showExceptMakingEntryNumberDialog(Context context, String title, WSWip bean, WSDialogCallBackTwo dialogCallBack) {
        WSReportWorkEntryNumber wsReportWorkEntryNumber = new WSReportWorkEntryNumber(context);
        wsReportWorkEntryNumber.setBeanAndDialogCallBack(bean, title, dialogCallBack);
        wsReportWorkEntryNumber.setCanceledOnTouchOutside(false);
        wsReportWorkEntryNumber.show();
        setCurrentDialog(wsReportWorkEntryNumber);
    }

    public static WSLookMaterailTrayDialog showLookMaterailTrayDialog(Context context, String title, WSMaterialTray wsMaterialTray) {
        WSLookMaterailTrayDialog wsLookMaterailTrayDialog = new WSLookMaterailTrayDialog(context, title, wsMaterialTray, null, WSLookMaterailTrayDialog.STATUS0);
        wsLookMaterailTrayDialog.setCanceledOnTouchOutside(false);
        wsLookMaterailTrayDialog.show();
        setCurrentDialog(wsLookMaterailTrayDialog);
        return wsLookMaterailTrayDialog;
    }

    /**
     * 输入物料在制品 查看托盘情况
     *
     * @param context
     * @param title
     * @return
     */
    public static WSLookTrayListVpDialog showLookMaterailTrayListDialog(Context context, String title) {
        WSLookTrayListVpDialog wsLookTrayListVpDialog = new WSLookTrayListVpDialog(context, title);
        wsLookTrayListVpDialog.setCanceledOnTouchOutside(false);
        setCurrentDialog(wsLookTrayListVpDialog);
        return wsLookTrayListVpDialog;
    }

    public static WSLookEnclosureDialog showLookEnclosureDialog(Context context, String title, WSEnclosureCallBack enclosureCallBack) {
        WSLookEnclosureDialog wsLookEnclosureDialog = new WSLookEnclosureDialog(context, title, enclosureCallBack);
        wsLookEnclosureDialog.setCanceledOnTouchOutside(false);
        setCurrentDialog(wsLookEnclosureDialog);
        return wsLookEnclosureDialog;
    }

    public static WSLabelTypesDialog showLabelTypesDialog(Context context, String title, WSDialogCallBackTwo enclosureCallBack) {
        WSLabelTypesDialog wsLabelTypesDialog = new WSLabelTypesDialog(context, title, enclosureCallBack);
        wsLabelTypesDialog.setCanceledOnTouchOutside(false);
        setCurrentDialog(wsLabelTypesDialog);
        return wsLabelTypesDialog;
    }

    public static WSLabelTempletDialog showLabelTempletDialog(Context context, String title, WSDialogCallBackTwo enclosureCallBack) {
        WSLabelTempletDialog wsLabelTypesDialog = new WSLabelTempletDialog(context, title, enclosureCallBack);
        wsLabelTypesDialog.setCanceledOnTouchOutside(false);
        setCurrentDialog(wsLabelTypesDialog);
        return wsLabelTypesDialog;
    }

    public static WSLookMaterailTrayDialog showLookMaterailTrayDialog(Context context, String title, WSWipTray wsWipTray) {
        WSLookMaterailTrayDialog wsLookMaterailTrayDialog = new WSLookMaterailTrayDialog(context, title, null, wsWipTray, WSLookMaterailTrayDialog.STATUS1);
        wsLookMaterailTrayDialog.setCanceledOnTouchOutside(false);
        wsLookMaterailTrayDialog.show();
        setCurrentDialog(wsLookMaterailTrayDialog);
        return wsLookMaterailTrayDialog;
    }

    public static void showEntryNumberDialog(Context context, String title, double maxNumber, WSDialogCallBackTwo dialogCallBackTwo) {
        WSEntryNumberDialog wsEntryNumberDialog = new WSEntryNumberDialog(context, title, maxNumber, dialogCallBackTwo);
        wsEntryNumberDialog.setCanceledOnTouchOutside(false);
        wsEntryNumberDialog.show();
        setCurrentDialog(wsEntryNumberDialog);
    }

    public static void showEntryScretKeyDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        WSEntryScretKeyDialog wsEntryScretKeyDialog = new WSEntryScretKeyDialog(context, title, dialogCallBackTwo);
        wsEntryScretKeyDialog.setCanceledOnTouchOutside(false);
        wsEntryScretKeyDialog.show();
        setCurrentDialog(wsEntryScretKeyDialog);
    }

    /**
     * 展示输入barocode的dialog
     */
    public static void showEntryBarcodeDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        WSHandEntryBarcodeDialog handEntryBarcodeDialog = new WSHandEntryBarcodeDialog(context, title, dialogCallBackTwo);
        handEntryBarcodeDialog.setCanceledOnTouchOutside(false);
        handEntryBarcodeDialog.show();
        setCurrentDialog(handEntryBarcodeDialog);

    }

    /**
     * 手动搜索
     *
     * @param context
     * @param title
     * @param dialogCallBackTwo
     */
    public static void showHandSearchDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        WSHandSearchDialog handSearchDialog = new WSHandSearchDialog(context, title, dialogCallBackTwo);
        handSearchDialog.setCanceledOnTouchOutside(false);
        handSearchDialog.show();
        setCurrentDialog(handSearchDialog);

    }

    public static void showExceptOutHistoryDialog(Context context, String title, List<WSExceptionOutRecordVo> wsExceptionOutRecordVoList, WSDialogCallBackThree dialogCallBackTwo) {
        WSExceptOutHistoryDialog wsExceptOutHistoryDialog = new WSExceptOutHistoryDialog(context, title, dialogCallBackTwo);
        wsExceptOutHistoryDialog.setCanceledOnTouchOutside(false);
        wsExceptOutHistoryDialog.setData(wsExceptionOutRecordVoList);
        wsExceptOutHistoryDialog.show();
        setCurrentDialog(wsExceptOutHistoryDialog);

    }


    public static WSReworkDialog showReworkDialog(Context context, String title, int maxnumber, WSDialogCallBackTwo dialogCallBackTwo) {
        WSReworkDialog wsReworkDialog = new WSReworkDialog(context, title, maxnumber, dialogCallBackTwo);
        wsReworkDialog.setCanceledOnTouchOutside(false);
        wsReworkDialog.show();
        setCurrentDialog(wsReworkDialog);
        return wsReworkDialog;
    }

    public static void showTraySearchDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        WSTraySearchDialog traySearchDialog = new WSTraySearchDialog(context, title, dialogCallBackTwo);
        traySearchDialog.setCanceledOnTouchOutside(false);
        traySearchDialog.show();
        setCurrentDialog(traySearchDialog);

    }

    /**
     * 展示维修dailog
     *
     * @param context
     * @param title
     * @param dialogCallBackTwo
     */
    public static WSRepairDialog showRepairDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        WSRepairDialog wsRepairDialog = new WSRepairDialog(context, title, dialogCallBackTwo);
        wsRepairDialog.setCanceledOnTouchOutside(false);
        setCurrentDialog(wsRepairDialog);
//        wsRepairDialog.show();
        return wsRepairDialog;
    }

    public static WSRepairReasonDialog showRepairReasonDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        WSRepairReasonDialog wsRepairDialog = new WSRepairReasonDialog(context, title, dialogCallBackTwo);
        wsRepairDialog.setCanceledOnTouchOutside(false);
        setCurrentDialog(wsRepairDialog);
        return wsRepairDialog;
    }

    /**
     * 展示返工扫描托盘dialog
     *
     * @param context
     * @param title
     * @param dialogCallBackTwo
     * @return
     */
    public static WSReviewWorkScanTrayDialog showReviewWorkScantrayDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        WSReviewWorkScanTrayDialog wsReviewWorkScanTrayDialog = new WSReviewWorkScanTrayDialog(context, title, dialogCallBackTwo);
        wsReviewWorkScanTrayDialog.setCanceledOnTouchOutside(false);
        setCurrentDialog(wsReviewWorkScanTrayDialog);
//        wsReviewWorkScanTrayDialog.show();
        return wsReviewWorkScanTrayDialog;
    }

    /**
     * 返工dialog
     *
     * @param context
     * @param title
     * @param dialogCallBackTwo
     * @return
     */
    public static WSReviewWorkDialog showReviewDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        WSReviewWorkDialog wsReviewWorkDialog = new WSReviewWorkDialog(context, title, dialogCallBackTwo);
        wsReviewWorkDialog.setCanceledOnTouchOutside(false);
        setCurrentDialog(wsReviewWorkDialog);
        return wsReviewWorkDialog;
    }

    public static void showInterceptQuitDialog(Context context, String title, String message, WSDialogCallBackTwo dialogCallBackTwo) {
        WSInterceptQuitDialog wsReviewWorkDialog = new WSInterceptQuitDialog(context, title, message, dialogCallBackTwo);
        wsReviewWorkDialog.setCanceledOnTouchOutside(false);
        wsReviewWorkDialog.show();
        setCurrentDialog(wsReviewWorkDialog);
    }

    /**
     * 在接收界面 接收弹框
     *
     * @param context
     * @param title
     * @param dialogCallBackTwo
     */
    public static void showReciverBindTrayDialog(Context context, String title, String message, WSDialogCallBackTwo dialogCallBackTwo) {
        WSReciverBindTrayDialog wsReciverBindTrayDialog = new WSReciverBindTrayDialog(context, title, message, dialogCallBackTwo);
        wsReciverBindTrayDialog.setCanceledOnTouchOutside(false);
        wsReciverBindTrayDialog.show();
        setCurrentDialog(wsReciverBindTrayDialog);
    }

    /**
     * 结束前提醒用户
     *
     * @param context
     * @param title
     * @param dialogCallBackTwo
     */
    public static void showRemindDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        WSRemindDialog wsRemindDialog = new WSRemindDialog(context, title, dialogCallBackTwo);
        wsRemindDialog.setCanceledOnTouchOutside(false);
        wsRemindDialog.show();
        setCurrentDialog(wsRemindDialog);
    }

    public static void showPropCheckDialog(Context context, String title, String message, WSDialogCallBackTwo dialogCallBackTwo) {
        WSPropCheckDialog wsRemindDialog = new WSPropCheckDialog(context, title, message,dialogCallBackTwo);
        wsRemindDialog.setCanceledOnTouchOutside(false);
//        wsRemindDialog.setMessage(message);
        wsRemindDialog.show();
        setCurrentDialog(wsRemindDialog);
    }

    public static void showRemindTwoDialog(Context context, String title, int count, WSDialogCallBackTwo dialogCallBackTwo) {
        WSRemindTwoDialog wsRemindDialog = new WSRemindTwoDialog(context, title, dialogCallBackTwo);
        wsRemindDialog.setCanceledOnTouchOutside(false);
        wsRemindDialog.setCount(count);
        wsRemindDialog.show();
        setCurrentDialog(wsRemindDialog);
    }

    public static WSDownApkProgressDialog showUpdateDialog(Context context, String title, WSAppUpdate wsAppUpdate, WSDialogCallBackTwo dialogCallBackTwo) {
        WSDownApkProgressDialog wsDownApkProgressDialog = new WSDownApkProgressDialog(context, title, dialogCallBackTwo);
        wsDownApkProgressDialog.setCanceledOnTouchOutside(false);
        wsDownApkProgressDialog.setData(wsAppUpdate);
        wsDownApkProgressDialog.show();
        setCurrentDialog(wsDownApkProgressDialog);
        return wsDownApkProgressDialog;
    }

    public static WSPushOneDialog showPushOneDialog(Context context, String title, int code, WSDialogCallBackTwo dialogCallBackTwo) {
        WSPushOneDialog wsPushOneDialog = new WSPushOneDialog(context, title, code, dialogCallBackTwo);
        wsPushOneDialog.setCanceledOnTouchOutside(false);
        wsPushOneDialog.show();
        setCurrentDialog(wsPushOneDialog);
        return wsPushOneDialog;
    }

    public static WSPushTwoDialog showPushTwoDialog(Context context, String title, int code, WSDialogCallBackTwo dialogCallBackTwo) {
        WSPushTwoDialog wsPushTwoDialog = new WSPushTwoDialog(context, title, code, dialogCallBackTwo);
        wsPushTwoDialog.setCanceledOnTouchOutside(false);
        wsPushTwoDialog.show();
        setCurrentDialog(wsPushTwoDialog);
        return wsPushTwoDialog;
    }

    public static WSReportHistoryDialog showHistoryReportDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        WSReportHistoryDialog wsReportHistoryDialog = new WSReportHistoryDialog(context, title, dialogCallBackTwo);
        wsReportHistoryDialog.setCanceledOnTouchOutside(false);
//        wsReportHistoryDialog.show();
        setCurrentDialog(wsReportHistoryDialog);
        return wsReportHistoryDialog;
    }

    public static WSWarnRemindDialog showWranRemindDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        WSWarnRemindDialog wsWarnRemindDialog = new WSWarnRemindDialog(context, title, dialogCallBackTwo);
        wsWarnRemindDialog.setCanceledOnTouchOutside(false);
        setCurrentDialog(wsWarnRemindDialog);
        return wsWarnRemindDialog;
    }


    private static void setCurrentDialog(WSCommonDialog dialog) {
        ((WSBaseApplication) WSBaseApplication.getInstance()).setCurrentDialog(dialog);
    }


}
