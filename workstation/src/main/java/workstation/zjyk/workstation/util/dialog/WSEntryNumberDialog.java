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
import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * Created by zjgz on 2017/10/31.
 */

public class WSEntryNumberDialog extends WSCommonDialog {
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    String title;
    double maxNumber;
    WSDialogCallBackTwo dialogCallBackTwo;

    public WSEntryNumberDialog(Context context, String title, double maxNumber, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.maxNumber = maxNumber;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView();
    }

    public WSEntryNumberDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected WSEntryNumberDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }


    private void initDialogView() {
        setContentView(R.layout.dialog_entry_number);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        editNumber.setTag(MUST_HANDLE_TAG);
        KeyboardUtils.show(editNumber);
    }


    @OnClick({R.id.btn_cancel, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dialogCallBackTwo.OnNegativeClick();
                KeyboardUtils.hideKeyboardOnly(editNumber);
                dismiss();
                break;
            case R.id.btn_sure:
                String str = editNumber.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    if (!isNumeric(str)) {
                        ToastUtil.showCenterShort("请输入数字", true);
                        return;
                    }
                    double entryNumber = Double.parseDouble(str);
                    if (!check(entryNumber)) {
                        return;
                    }
                    dialogCallBackTwo.OnPositiveClick(entryNumber);
                    KeyboardUtils.hideKeyboardOnly(editNumber);
                    dismiss();
                } else {
                    ToastUtil.showCenterShort("不能为空", true);
                }
                break;
        }
    }

    public boolean isNumeric(String str) {
        return NumberUtils.isNumeric(str);
    }

    private boolean check(double entryNumber) {
        if (entryNumber <= maxNumber) {
            return true;
        } else {
            ToastUtil.showCenterShort("输出数量不能超出剩余总数", true);
        }
        return false;
    }

}
