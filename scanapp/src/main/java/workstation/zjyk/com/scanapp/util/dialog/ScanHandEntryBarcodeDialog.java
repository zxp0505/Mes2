package workstation.zjyk.com.scanapp.util.dialog;

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
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.util.dialog.callback.ScanDialogCallBackTwo;

/**
 * 手动输入barcode
 * Created by zjgz on 2017/10/31.
 */

public class ScanHandEntryBarcodeDialog extends ScanCommonDialog implements DialogInterface.OnDismissListener {
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    ScanDialogCallBackTwo dialogCallBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    String title;

    public ScanHandEntryBarcodeDialog(Context context, String title, ScanDialogCallBackTwo dialogCallBack) {
        super(context, R.style.CommonDialog);
        this.dialogCallBack = dialogCallBack;
        this.title = title;
        initDialogView();
    }

    public ScanHandEntryBarcodeDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected ScanHandEntryBarcodeDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }


    private void initDialogView() {
        setContentView(R.layout.dialog_entry_barcoder);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        KeyboardUtils.show(editNumber);
//        editNumber.setTag(MUST_HANDLE_TAG);
//        KeyboardUtils.setEdittextOnlyHandInput(editNumber);
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
