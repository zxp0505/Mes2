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

/**
 * Created by zjgz on 2017/10/31.
 */

public class WSRemindTwoDialog extends WSCommonDialog {
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    String title;
    double maxNumber;
    WSDialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.tv_count)
    TextView tvCount;

    public WSRemindTwoDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView();
    }

    public WSRemindTwoDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected WSRemindTwoDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }

    public void setCount(int count) {
        tvCount.setText(count + "");
    }

    private void initDialogView() {
        setContentView(R.layout.dialog_remind_two);
        ButterKnife.bind(this);
        tvTitle.setText(title);
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
