package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;


public class WSOneTipDialog extends WSCommonDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    String buttonText;
    WSDialogCallBackTwo dialogCallBackTwo;

    public WSOneTipDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        this(context, title, "确定", dialogCallBackTwo);

    }

    public WSOneTipDialog(Context context, String title, String buttonText, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.buttonText = buttonText;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public WSOneTipDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected WSOneTipDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
