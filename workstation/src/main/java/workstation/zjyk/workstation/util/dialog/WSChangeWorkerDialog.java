package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * Created by zjgz on 2017/11/1.
 */

public class WSChangeWorkerDialog extends WSCommonDialog {
    @BindView(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    WSDialogCallBackTwo callBack;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    public WSChangeWorkerDialog(Context context, WSDialogCallBackTwo callBack) {
        super(context, R.style.CommonDialog);
        this.callBack = callBack;
        initDialogView(context);
    }

    public WSChangeWorkerDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected WSChangeWorkerDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.ws_dialog_change_worker);
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
