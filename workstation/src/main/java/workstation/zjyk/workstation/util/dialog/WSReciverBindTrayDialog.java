package workstation.zjyk.workstation.util.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSReworkTrayInfo;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 接收绑定托盘
 * Created by zjgz on 2017/10/31.
 */

public class WSReciverBindTrayDialog extends WSCommonDialog implements DialogInterface.OnDismissListener {

    String title;
    WSDialogCallBackTwo dialogCallBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edit_bind_tray)
    EditText editBindTray;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String message;
    @BindView(R.id.tv_reciver_tray)
    TextView tvReciverTray;
    @BindView(R.id.rl_edit)
    RelativeLayout rlEdit;

    public WSReciverBindTrayDialog(Context context, String title, String message, WSDialogCallBackTwo dialogCallBack) {
        super(context, R.style.CommonDialog);
        this.dialogCallBack = dialogCallBack;
        this.title = title;
        this.message = message;
        initDialogView();
    }

    public WSReciverBindTrayDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected WSReciverBindTrayDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }


    private void initDialogView() {
        setContentView(R.layout.dialog_revier_bindtray);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        if (!TextUtils.isEmpty(message)) {
            tvReciverTray.setVisibility(View.VISIBLE);
            rlEdit.setVisibility(View.GONE);
            tvTitle.setText("请查看接收的托盘码");
            tvReciverTray.setText("工位托盘码: " + message);
        } else {
            tvTitle.setText("请扫描或手动输入接收托盘码");
            tvReciverTray.setVisibility(View.GONE);
            rlEdit.setVisibility(View.VISIBLE);
            editBindTray.setTag(HANDLE_AND_SCAN_TAG);
            KeyboardUtils.setEdittextInputNoSpace(editBindTray);
            KeyboardUtils.show(editBindTray);
        }
        setOnDismissListener(this);
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
                if (rlEdit.getVisibility() == View.VISIBLE) {
                    if (checkInput()) {
                        KeyboardUtils.hideKeyboardOnly(editBindTray);
                        String editText = editBindTray.getText().toString();
                        postiveDismiss(editText.trim() + ":0");//0表示edittext需要校验托盘码 1：不需要
                    }
                } else {
                    String[] split = tvReciverTray.getText().toString().trim().split(":");
                    postiveDismiss(split[1] + ":1");
                }
                break;
            case R.id.edit_bind_tray:
                KeyboardUtils.show(editBindTray);
                break;
        }
    }

    private void postiveDismiss(String message) {
        dialogCallBack.OnPositiveClick(message);
        dismiss();
    }

    private boolean checkInput() {
        String str_tray = editBindTray.getText().toString();
        if (TextUtils.isEmpty(str_tray)) {
            ToastUtil.showInfoCenterShort("请输入托盘码!");
            return false;
        }
        return true;
    }


    @Override
    public void onScanResult(String scanResult) {
        super.onScanResult(scanResult);
        editBindTray.setText(scanResult);
    }

    WSReworkTrayInfo wsReworkTrayInfo;
    Activity activity;

    public WSReciverBindTrayDialog setData(Activity activity, WSReworkTrayInfo wsReworkTrayInfo) {
        this.activity = activity;
        this.wsReworkTrayInfo = wsReworkTrayInfo;
        return this;
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        editBindTray.setText("");
    }
}
