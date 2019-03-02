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
import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 输入秘钥
 * Created by zjgz on 2017/10/31.
 */

public class WSEntryScretKeyDialog extends WSCommonDialog {
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    String title;
    WSDialogCallBackTwo dialogCallBackTwo;

    public WSEntryScretKeyDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView();
    }

    public WSEntryScretKeyDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected WSEntryScretKeyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }


    private void initDialogView() {
        setContentView(R.layout.dialog_entry_secret_key);
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
                    dialogCallBackTwo.OnPositiveClick(str);
                    KeyboardUtils.hideKeyboardOnly(editNumber);
                    dismiss();
                } else {
                    ToastUtil.showCenterShort("不能为空", true);
                }
                break;
        }
    }


}
