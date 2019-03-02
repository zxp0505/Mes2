package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;


public class WSTwoTipDialog extends WSCommonDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    String buttonText;
    WSDialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.btn_cancle)
    Button btnCancle;

    public WSTwoTipDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        this(context, title, "确定", dialogCallBackTwo);

    }

    public WSTwoTipDialog(Context context, String title, String buttonText, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.buttonText = buttonText;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public WSTwoTipDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected WSTwoTipDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_two_tip);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        btnSure.setText(buttonText);

    }


    @OnClick({R.id.btn_sure, R.id.btn_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                dismiss();
                if (dialogCallBackTwo != null) {
                    dialogCallBackTwo.OnPositiveClick("");
                }
                break;
            case R.id.btn_cancle:
                dismiss();
                if (dialogCallBackTwo != null) {
                    dialogCallBackTwo.OnNegativeClick();
                }
                break;
        }

    }
}
