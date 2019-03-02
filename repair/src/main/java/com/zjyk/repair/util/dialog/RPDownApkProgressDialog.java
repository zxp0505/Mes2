package com.zjyk.repair.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zjyk.repair.R;
import com.zjyk.repair.modle.bean.RPAppUpdate;
import com.zjyk.repair.modle.bean.RPYesOrNoEnum;
import com.zjyk.repair.ui.customview.DonutProgress;
import com.zjyk.repair.util.dialog.callback.RPDialogCallBackTwo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;


public class RPDownApkProgressDialog extends RPCommonDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    String buttonText;
    RPDialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.progress)
    DonutProgress progress;
    @BindView(R.id.btn_cancle)
    Button btnCancle;
    RPAppUpdate wsAppUpdate;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;

    public RPDownApkProgressDialog(Context context, String title, RPDialogCallBackTwo dialogCallBackTwo) {
        this(context, title, "取消", dialogCallBackTwo);

    }

    public RPDownApkProgressDialog(Context context, String title, String buttonText, RPDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public RPDownApkProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected RPDownApkProgressDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    public void setData(RPAppUpdate wsAppUpdate) {
        this.wsAppUpdate = wsAppUpdate;
        progress.setVisibility(View.INVISIBLE);
        RPYesOrNoEnum forceUpdate = wsAppUpdate.getForceUpdate();//是否是强制升级
        if (forceUpdate == RPYesOrNoEnum.YES) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btnSure.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            btnSure.requestLayout();
            btnCancle.setVisibility(View.GONE);
        }
        btnSure.setText("确定");
        btnCancle.setText("取消");
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_apk_down_progress);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        btnSure.setTag(true);
        tvPrompt.setVisibility(View.GONE);


    }

    public void setProgress(float processB) {
        progress.setVisibility(View.VISIBLE);
        float process = NumberUtils.toFloatSaveOne(processB * 100);
        this.progress.setProgress(process);
    }

    public void setInstall() {
        progress.setVisibility(View.INVISIBLE);
        tvPrompt.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.btn_sure, R.id.btn_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                Boolean tag = (Boolean) btnSure.getTag();
                if (tag) {
                    btnSure.setTag(false);
                    btnSure.setBackgroundResource(R.drawable.shape_gray_bg);
                    if (dialogCallBackTwo != null) {
                        dialogCallBackTwo.OnPositiveClick("");
                    }
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
