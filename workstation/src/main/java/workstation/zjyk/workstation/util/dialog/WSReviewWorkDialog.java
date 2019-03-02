package workstation.zjyk.workstation.util.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSInputWipInfo;
import workstation.zjyk.workstation.modle.bean.WSReviewWorkStationVo;
import workstation.zjyk.workstation.modle.bean.WSReworkTrayInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationOutVO;
import workstation.zjyk.workstation.ui.pop.WSReviewStationsPopu;
import workstation.zjyk.workstation.ui.pop.basePop.WSBasePopupWindow;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 维修
 * Created by zjgz on 2017/10/31.
 */

public class WSReviewWorkDialog extends WSCommonDialog implements DialogInterface.OnDismissListener {
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    WSDialogCallBackTwo dialogCallBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    String title;
    @BindView(R.id.edit_bind_tray)
    EditText editBindTray;
    @BindView(R.id.tv_select_work)
    TextView tvSelectWork;
    @BindView(R.id.tv_workstation_name)
    TextView tvWorkstationName;
    @BindView(R.id.edit_reviewwork_reason)
    EditText editReviewworkReason;
    @BindView(R.id.tv_produce_name)
    TextView tvProduceName;
    private WSReviewStationsPopu wsReviewStationsPopu;

    public WSReviewWorkDialog(Context context, String title, WSDialogCallBackTwo dialogCallBack) {
        super(context, R.style.CommonDialog);
        this.dialogCallBack = dialogCallBack;
        this.title = title;
        initDialogView();
    }

    public WSReviewWorkDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected WSReviewWorkDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }


    private void initDialogView() {
        setContentView(R.layout.dialog_reviewwork);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        editNumber.setTag(MUST_HANDLE_TAG);
        editBindTray.setTag(HANDLE_AND_SCAN_TAG);
        editReviewworkReason.setTag(HANDLE_AND_SCAN_TAG);
        tvWorkstationName.setText("选择的工位: ");
        KeyboardUtils.show(editBindTray);
        KeyboardUtils.setEdittextOnlyHandInput(editNumber);
        KeyboardUtils.setEdittextInputNoSpace(editBindTray);
        setOnDismissListener(this);
    }


    @OnClick({R.id.btn_cancel, R.id.btn_sure, R.id.edit_number, R.id.edit_bind_tray, R.id.tv_select_work})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dialogCallBack.OnNegativeClick();
                KeyboardUtils.hideKeyboardOnly(editNumber);
                dismiss();
                break;
            case R.id.btn_sure:
                WSWorkStationOutVO wsWorkStationOutVO = checkInput();
                if (wsWorkStationOutVO != null) {
                    dialogCallBack.OnPositiveClick(wsWorkStationOutVO);
                    KeyboardUtils.hideKeyboardOnly(editNumber);
//                    dismiss();
                } else {
//                    ToastUtil.showCenterShort("不能为空", true);
                }
                break;
            case R.id.edit_number:
                KeyboardUtils.show(editNumber);
                break;
            case R.id.edit_bind_tray:
                KeyboardUtils.show(editBindTray);
                break;
            case R.id.tv_select_work:
                int[] locationOnScreen = UICommonUtil.getLocationOnScreen(tvSelectWork);
                int width = view.getWidth();
                int height = view.getHeight();
                float x = tvSelectWork.getX();
                float y = tvSelectWork.getY();
                getPopu().showPopuWindowAtlocation(tvSelectWork, (int) (x + width), (int) (y - height / 3));
                break;
        }
    }

    private WSWorkStationOutVO checkInput() {
//        String[] split = tvWorkstationName.getText().toString().split(":");
//        if (TextUtils.isEmpty(split[1].trim())) {
//            ToastUtil.showInfoCenterShort("请先选择工位!");
//            return null;
//        }
//        String str_number = editNumber.getText().toString().trim();
        String str_tray = editBindTray.getText().toString();
//        String str_reason = editReviewworkReason.getText().toString();
//        if (TextUtils.isEmpty(str_number)) {
//            ToastUtil.showInfoCenterShort("请输入维修数量!");
//            return false;
//        }
        if (TextUtils.isEmpty(str_tray)) {
            ToastUtil.showInfoCenterShort("请输入托盘码!");
            return null;
        }
//        if (TextUtils.isEmpty(str_reason)) {
//            ToastUtil.showInfoCenterShort("请输入返工原因!");
//            return null;
//        }

        WSWorkStationOutVO wsWorkStationOutVO = new WSWorkStationOutVO();
//        wsWorkStationOutVO.setReason(str_reason);
        wsWorkStationOutVO.setTrayCode(str_tray);
        WSReviewWorkStationVo wsReviewWorkStationVo = wsReviewWorkStationVos.get(0);
        wsWorkStationOutVO.setReceiveWorkStationId(wsReviewWorkStationVo.getId());//接收工位id
        wsWorkStationOutVO.setProcedureId(currentBean.getId());//前置工序id
        wsWorkStationOutVO.setRepairReturnCount(currentBean.getTrueCount());//返工数量
        return wsWorkStationOutVO;
    }

    private WSReviewStationsPopu getPopu() {
        if (wsReviewStationsPopu == null) {
            wsReviewStationsPopu = new WSReviewStationsPopu(activity);
            wsReviewStationsPopu.setData(wsReworkTrayInfo);
            wsReviewStationsPopu.setOnDismissListener(new WSBasePopupWindow.OnDismissListener() {
                @Override
                public void onDismiss(String name) {
                    WSWorkStationInfo currentSelect = wsReviewStationsPopu.getCurrentSelect();
                    if (currentSelect != null && currentSelect.isSelect()) {
                        tvWorkstationName.setText("选择的工位: " + currentSelect.getName());
                    } else {
                        tvWorkstationName.setText("选择的工位: ");
                    }
                }
            });
        }
        return wsReviewStationsPopu;
    }

    @Override
    public void onScanResult(String scanResult) {
        super.onScanResult(scanResult);
        editBindTray.setText(scanResult);
//        tagIs10 =false;
    }

    WSReworkTrayInfo wsReworkTrayInfo;
    Activity activity;

    List<WSReviewWorkStationVo> wsReviewWorkStationVos;
    WSInputWipInfo currentBean;

    public WSReviewWorkDialog setData(Activity activity, WSReworkTrayInfo wsReworkTrayInfo) {
        this.activity = activity;
        this.wsReworkTrayInfo = wsReworkTrayInfo;
        return this;
    }

    public WSReviewWorkDialog setData(Activity activity, List<WSReviewWorkStationVo> wsReviewWorkStationVos, WSInputWipInfo currentBean) {
        this.activity = activity;
        this.wsReviewWorkStationVos = wsReviewWorkStationVos;
        this.currentBean = currentBean;
        refresh();
        return this;
    }

    private void refresh() {
        if (wsReviewWorkStationVos != null && wsReviewWorkStationVos.size() > 0) {
            WSReviewWorkStationVo wsReviewWorkStationVo = wsReviewWorkStationVos.get(0);
            tvProduceName.setText("工位名称: " + wsReviewWorkStationVo.getName());
        }
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
//        tvWorkstationName.setText("选择的工位: ");
//        editNumber.setText("");
        editBindTray.setText("");
//        editReviewworkReason.setText("");
    }

    public void showThis() {
        KeyboardUtils.show(editBindTray);
        show();
    }

    public void dismissThis() {
        KeyboardUtils.hideKeyboardOnly(editBindTray);
        dismiss();
    }
}
