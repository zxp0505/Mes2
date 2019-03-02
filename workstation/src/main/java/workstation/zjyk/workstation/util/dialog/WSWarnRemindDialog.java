package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSWranInfo;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 警告提醒
 * Created by zjgz on 2017/10/31.
 */

public class WSWarnRemindDialog extends WSCommonDialog {
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    String title;
    double maxNumber;
    WSDialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_wran_person)
    TextView tvWranPerson;
    WSWranInfo wsWranInfo;

    public WSWarnRemindDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.maxNumber = maxNumber;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView();
    }

    public WSWarnRemindDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected WSWarnRemindDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }


    private void initDialogView() {
        setContentView(R.layout.dialog_warn_remind);
        ButterKnife.bind(this);
        tvTitle.setText(title);
    }

    public void showInfo(WSWranInfo wsWranInfo) {
        if (wsWranInfo != null) {
            this.wsWranInfo = wsWranInfo;
            tvOrderNumber.setText("订单号： " + wsWranInfo.getOrderNumber());
            tvWranPerson.setText("报警人： " + wsWranInfo.getPersonName());
        }

    }


    @OnClick({R.id.btn_cancel, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dialogCallBackTwo.OnNegativeClick();
                dismiss();
                break;
            case R.id.btn_sure:
                dialogCallBackTwo.OnPositiveClick(wsWranInfo);
                dismiss();
                break;
        }
    }


}
