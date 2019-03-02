package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.line.R;
import workstation.zjyk.line.util.DialogCallBackTwo;


public class PushOneDialog extends CommonDialog {


    private String title;
    DialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.btn_cancle)
    Button btnCancle;

    public PushOneDialog(Context context, String title, int code, DialogCallBackTwo dialogCallBackTwo) {
        this(context, title, "取消", dialogCallBackTwo);

    }

    public PushOneDialog(Context context, String title, String buttonText, DialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public PushOneDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected PushOneDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_push_one);
        ButterKnife.bind(this);
        tvTitle.setText(title);


    }


    @OnClick({R.id.btn_sure, R.id.btn_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                if (dialogCallBackTwo != null) {
                    dialogCallBackTwo.OnPositiveClick("");
                }
                dismiss();
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
