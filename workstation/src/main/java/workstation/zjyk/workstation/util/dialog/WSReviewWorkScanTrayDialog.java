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
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 返工
 * Created by zjgz on 2017/10/31.
 */

public class WSReviewWorkScanTrayDialog extends WSCommonDialog {
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

    public WSReviewWorkScanTrayDialog(Context context, String title, WSDialogCallBackTwo dialogCallBack) {
        super(context, R.style.CommonDialog);
        this.dialogCallBack = dialogCallBack;
        this.title = title;
        initDialogView();
    }

    public WSReviewWorkScanTrayDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected WSReviewWorkScanTrayDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }


    private void initDialogView() {
        setContentView(R.layout.dialog_reviewwork_bind_tray);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        editBindTray.setTag(HANDLE_AND_SCAN_TAG);
        KeyboardUtils.setEdittextInputNoSpace(editBindTray);
    }


    @OnClick({R.id.btn_cancel, R.id.btn_sure, R.id.edit_bind_tray})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dialogCallBack.OnNegativeClick();
                KeyboardUtils.hideKeyboardOnly(editBindTray);
                dismiss();
                break;
            case R.id.btn_sure:
                String str = editBindTray.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    dialogCallBack.OnPositiveClick(str.trim());
                    KeyboardUtils.hideKeyboardOnly(editBindTray);
//                    dismiss();
                } else {
                    ToastUtil.showCenterShort("不能为空", true);
                }
                break;
            case R.id.edit_bind_tray:
                KeyboardUtils.show(editBindTray);
                break;
        }
    }

    @Override
    public void onScanResult(String scanResult) {
        super.onScanResult(scanResult);
        editBindTray.setText(scanResult);
//        tagIs10 =false;
    }

    public void showThis(){
        editBindTray.setText("");
        KeyboardUtils.show(editBindTray);
        show();
    }

}
