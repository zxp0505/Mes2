package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.line.R;
import workstation.zjyk.line.util.DialogCallBackTwo;

/**
 * Created by zjgz on 2017/11/1.
 */

public class ChangeWorkerDialog extends CommonDialog {
    @BindView(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    DialogCallBackTwo callBack;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    public ChangeWorkerDialog(Context context, DialogCallBackTwo callBack) {
        super(context, R.style.CommonDialog);
        this.callBack = callBack;
        initDialogView(context);
    }

    public ChangeWorkerDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected ChangeWorkerDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_change_worker);
        ButterKnife.bind(this);
    }

    public void setData(String data) {
        tvDialogTitle.setText(data);
    }

    @OnClick({R.id.tv_cancle, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                callBack.OnPositiveClick(null);
                break;
            case R.id.tv_cancle:
                callBack.OnNegativeClick();
                break;
        }
        dismiss();
    }

}
