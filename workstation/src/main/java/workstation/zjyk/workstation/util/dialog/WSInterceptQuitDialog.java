package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 分料成功
 * Created by zjgz on 2017/11/1.
 */

public class WSInterceptQuitDialog extends WSCommonDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    String message;
    WSDialogCallBackTwo dialogCallBackTwo;

    public WSInterceptQuitDialog(Context context, String title) {
        this(context, title, "", null);

    }

    public WSInterceptQuitDialog(Context context, String title, String message, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.message = message;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public WSInterceptQuitDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected WSInterceptQuitDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
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
