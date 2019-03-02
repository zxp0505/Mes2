package workstation.zjyk.com.scanapp.util.dialog;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.util.dialog.callback.ScanDialogCallBackTwo;


public class ScanOneTipDialog extends ScanCommonDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    String buttonText;
    ScanDialogCallBackTwo dialogCallBackTwo;

    public ScanOneTipDialog(Context context, String title, ScanDialogCallBackTwo dialogCallBackTwo) {
        this(context, title, "确定", dialogCallBackTwo);

    }

    public ScanOneTipDialog(Context context, String title, String buttonText, ScanDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.buttonText = buttonText;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public ScanOneTipDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected ScanOneTipDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_one_tip);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        btnSure.setText(buttonText);

    }


    @OnClick(R.id.btn_sure)
    public void onViewClicked() {
        dismiss();
        if (dialogCallBackTwo != null) {
            dialogCallBackTwo.OnPositiveClick("");
        }
    }
}
