package workstation.zjyk.line.util.dialog;

import android.content.Context;
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

public class InterceptQuitDialog extends CommonDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    String message;
    DialogCallBackTwo dialogCallBackTwo;

    public InterceptQuitDialog(Context context, String title) {
        this(context, title, "", null);

    }

    public InterceptQuitDialog(Context context, String title, String message, DialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.message = message;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public InterceptQuitDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected InterceptQuitDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_intercept_quit);
        ButterKnife.bind(this);
        tvTitle.setText(message);
        btnSure.setText("确定");

    }


    @OnClick(R.id.btn_sure)
    public void onViewClicked() {
        dismiss();
        if (dialogCallBackTwo != null) {
            dialogCallBackTwo.OnPositiveClick("");
        }
    }
}
