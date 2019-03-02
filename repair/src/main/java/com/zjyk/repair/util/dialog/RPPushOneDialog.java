package com.zjyk.repair.util.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zjyk.repair.R;
import com.zjyk.repair.modle.bean.RPAppUpdate;
import com.zjyk.repair.util.dialog.callback.RPDialogCallBackTwo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RPPushOneDialog extends RPCommonDialog {


    private String title;
    RPDialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.btn_cancle)
    Button btnCancle;

    public RPPushOneDialog(Context context, String title, int code, RPDialogCallBackTwo dialogCallBackTwo) {
        this(context, title, "取消", dialogCallBackTwo);

    }

    public RPPushOneDialog(Context context, String title, String buttonText, RPDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public RPPushOneDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected RPPushOneDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    public void setData(RPAppUpdate wsAppUpdate) {
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_push_one);
        ButterKnife.bind(this);
        tvTitle.setText(title);


    }

    public String getTitle() {
        if (!TextUtils.isEmpty(title)) {
            return title;
        }
        return "";
    }


    @OnClick({R.id.btn_sure, R.id.btn_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                if (dialogCallBackTwo != null) {
                    dialogCallBackTwo.OnPositiveClick("");
                }
                dismiss();
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
