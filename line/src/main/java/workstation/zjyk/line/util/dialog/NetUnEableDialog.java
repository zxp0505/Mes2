package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.net.ErrorResultClickListner;
import workstation.zjyk.line.util.DialogCallBackTwo;

/**
 * 分料成功
 * Created by zjgz on 2017/11/1.
 */

public class NetUnEableDialog extends CommonDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    int code;
    DialogCallBackTwo dialogCallBackTwo;

    public NetUnEableDialog(Context context, String title) {
        this(context, title, null);

    }

    public NetUnEableDialog(Context context, String title, DialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public NetUnEableDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected NetUnEableDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_net_uneable);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        btnSure.setText("设置");

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
