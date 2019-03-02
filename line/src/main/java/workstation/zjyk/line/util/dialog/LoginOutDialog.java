package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import workstation.zjyk.line.R;
import workstation.zjyk.line.util.DialogCallBackTwo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 分料成功
 * Created by zjgz on 2017/11/1.
 */

public class LoginOutDialog extends CommonDialog {

    @BindView(R.id.btn_print)
    Button btnPrint;
    @BindView(R.id.btn_cancle)
    Button btnCancle;
    DialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    public LoginOutDialog(Context context, DialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);
    }

    public LoginOutDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected LoginOutDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_delivery);
        ButterKnife.bind(this);
        tvTitle.setText("确定要退出吗?");
        btnCancle.setText("取消");
        btnPrint.setText("确定");
    }

    public void setData(String data) {
    }

    @OnClick({R.id.btn_print, R.id.btn_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_print:
                dialogCallBackTwo.OnPositiveClick("");
                dismiss();
                break;
            case R.id.btn_cancle:
                dialogCallBackTwo.OnNegativeClick();
                dismiss();
                break;
        }
    }
}
