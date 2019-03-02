package cn.com.ethank.ui.common.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.SharePreferenceKeyUtil;
import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.ui.common.dialog.callback.ResourceDialogCallBackTwo;
import workstation.zjyk.resource.R;

/**
 * 设置域名
 * Created by zjgz on 2017/10/31.
 */

public class SetDomainDialog extends ResourceCommonDialog implements View.OnClickListener {
    EditText editNumber;
    Button btnCancel;
    Button btnSure;
    TextView tvTitle;
    String title;
    ResourceDialogCallBackTwo dialogCallBackTwo;
    private TextView tvCurrentDomain;

    public SetDomainDialog(Context context, String title, ResourceDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView();
    }

    public SetDomainDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected SetDomainDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }


    private void initDialogView() {
        setContentView(R.layout.dialog_set_domain);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSure = findViewById(R.id.btn_sure);
        editNumber = findViewById(R.id.edit_number);
        tvCurrentDomain = findViewById(R.id.tv_current_domain);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title);
        editNumber.setTag(MUST_HANDLE_TAG);
        KeyboardUtils.show(editNumber);
        btnCancel.setOnClickListener(this);
        btnSure.setOnClickListener(this);

    }

    public void setCurrentHost(String host) {
        tvCurrentDomain.setText("当前地址为: " + host);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_cancel) {
            KeyboardUtils.hideKeyboardOnly(editNumber);
            dialogCallBackTwo.OnNegativeClick();
            dismiss();
        } else if (id == R.id.btn_sure) {
            String str = editNumber.getText().toString();
            if (!TextUtils.isEmpty(str)) {
                KeyboardUtils.hideKeyboardOnly(editNumber);
                dialogCallBackTwo.OnPositiveClick(str);
                dismiss();
            } else {
                ToastUtil.showCenterShort("不能为空", true);
            }
        }
    }
}
