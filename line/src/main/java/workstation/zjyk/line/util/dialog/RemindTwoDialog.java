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

/**
 * Created by zjgz on 2017/10/31.
 */

public class RemindTwoDialog extends CommonDialog {
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    String title;
    double maxNumber;
    DialogCallBackTwo dialogCallBackTwo;

    public RemindTwoDialog(Context context, String title, DialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView();
    }

    public RemindTwoDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected RemindTwoDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }


    private void initDialogView() {
        setContentView(R.layout.dialog_remind_two);
        ButterKnife.bind(this);
        tvTitle.setText(title);
    }


    public TextView getTvTitleView() {
        return tvTitle;
    }

    @OnClick({R.id.btn_cancel, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dialogCallBackTwo.OnNegativeClick();
                dismiss();
                break;
            case R.id.btn_sure:
                dialogCallBackTwo.OnPositiveClick("");
                dismiss();
                break;
        }
    }


}
