package com.zjyk.repair.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.zjyk.repair.R;
import com.zjyk.repair.util.dialog.callback.RPDialogCallBackTwo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zjgz on 2017/11/1.
 */

public class RPChangeWorkerDialog extends RPCommonDialog {
    @BindView(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    RPDialogCallBackTwo callBack;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    public RPChangeWorkerDialog(Context context, RPDialogCallBackTwo callBack) {
        super(context, R.style.CommonDialog);
        this.callBack = callBack;
        initDialogView(context);
    }

    public RPChangeWorkerDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected RPChangeWorkerDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
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
