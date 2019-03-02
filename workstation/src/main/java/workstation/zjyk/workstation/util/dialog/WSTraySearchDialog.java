package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
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
 * 手动搜索
 * Created by zjgz on 2017/10/31.
 */

public class WSTraySearchDialog extends WSCommonDialog implements DialogInterface.OnDismissListener {
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

    public WSTraySearchDialog(Context context, String title, WSDialogCallBackTwo dialogCallBack) {
        super(context, R.style.CommonDialog);
        this.dialogCallBack = dialogCallBack;
        this.title = title;
        initDialogView();
    }

    public WSTraySearchDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected WSTraySearchDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }


    private void initDialogView() {
        setContentView(R.layout.dialog_tray_search);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        editNumber.setTag(MUST_HANDLE_TAG);
        KeyboardUtils.show(editNumber);
        KeyboardUtils.setEdittextOnlyHandInput(editNumber);
        setOnDismissListener(this);
    }


    @OnClick({R.id.btn_cancel, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dialogCallBack.OnNegativeClick();
                KeyboardUtils.hideKeyboardOnly(editNumber);
                dismiss();
                break;
            case R.id.btn_sure:
                String str = editNumber.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    dialogCallBack.OnPositiveClick(str);
                    KeyboardUtils.hideKeyboardOnly(editNumber);
                    dismiss();
                } else {
                    ToastUtil.showCenterShort("不能为空", true);
                }
                break;
        }
    }


    @Override
    public void onDismiss(DialogInterface dialog) {

    }
}
