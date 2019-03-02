package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSWorkStationOutVO;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 维修
 * Created by zjgz on 2017/10/31.
 */

public class WSRepairDialog extends WSCommonDialog {
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
    @BindView(R.id.edit_repair_reason)
    EditText editRepairReason;

    public WSRepairDialog(Context context, String title, WSDialogCallBackTwo dialogCallBack) {
        super(context, R.style.CommonDialog);
        this.dialogCallBack = dialogCallBack;
        this.title = title;
        initDialogView();
    }

    public WSRepairDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected WSRepairDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }


    private void initDialogView() {
        setContentView(R.layout.dialog_repair);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        editNumber.setTag(MUST_HANDLE_TAG);
        editBindTray.setTag(HANDLE_AND_SCAN_TAG);
        editRepairReason.setTag(HANDLE_AND_SCAN_TAG);
        KeyboardUtils.show(editNumber);
        KeyboardUtils.setEdittextOnlyHandInput(editNumber);
        KeyboardUtils.setEdittextInputNoSpace(editBindTray);
    }


    @OnClick({R.id.btn_cancel, R.id.btn_sure, R.id.edit_number, R.id.edit_bind_tray})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dialogCallBack.OnNegativeClick();
                dismissThis();
//                KeyboardUtils.hideKeyboardOnly(editNumber);
//                dismiss();
                break;
            case R.id.btn_sure:
                String str = editNumber.getText().toString();
                WSWorkStationOutVO wsWorkStationOutVO = checkInput();
                if (wsWorkStationOutVO != null) {
                    dialogCallBack.OnPositiveClick(wsWorkStationOutVO);

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
        }
    }

    public void dismissThis() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null && currentFocus instanceof EditText) {
            KeyboardUtils.hideKeyboardOnly((EditText) currentFocus);
        }
        super.dismiss();
    }

//    @Override
//    public void dismiss() {
//        View view = getCurrentFocus();
//        if (view instanceof TextView) {
//            InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
//        }
//        super.dismiss();
//    }
//
//    public void dismissThis(boolean isShow) {
//        View currentFocus = getCurrentFocus();
//        if (currentFocus != null && currentFocus instanceof EditText) {
//            KeyboardUtils.hideKeyboardOnly((EditText) currentFocus);
//        }
//        dismiss();
//    }

    private WSWorkStationOutVO checkInput() {
        String strNumber = editNumber.getText().toString().trim();
        String strTray = editBindTray.getText().toString().trim();
        String strReason = editRepairReason.getText().toString().trim();
        if (TextUtils.isEmpty(strNumber)) {
            ToastUtil.showInfoCenterShort("请输入维修数量!");
            return null;
        }
        if (TextUtils.isEmpty(strTray)) {
            ToastUtil.showInfoCenterShort("请输入托盘码!");
            return null;
        }
//        if (TextUtils.isEmpty(strReason)) {
//            ToastUtil.showInfoCenterShort("请输入维修原因!");
//            return null;
//        }
        WSWorkStationOutVO wsWorkStationOutVO = new WSWorkStationOutVO();
        wsWorkStationOutVO.setReason(strReason);
        wsWorkStationOutVO.setTrayCode(strTray);
        wsWorkStationOutVO.setRepairReturnCount(Double.parseDouble(strNumber));
        return wsWorkStationOutVO;
    }

    @Override
    public void onScanResult(String scanResult) {
        super.onScanResult(scanResult);
//        editBindTray.setText(scanResult);
//        tagIs10 =false;
    }


    public void showThis() {
        if (editNumber != null) {
            editNumber.setText("");
            editBindTray.setText("");
            editRepairReason.setText("");
        }
        KeyboardUtils.show(editNumber);
        show();
    }
}
