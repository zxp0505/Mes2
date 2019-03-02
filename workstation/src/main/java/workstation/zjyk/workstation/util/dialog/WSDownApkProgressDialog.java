package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSAppUpdate;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSYesOrNoEnum;
import workstation.zjyk.workstation.ui.customview.DonutProgress;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;


public class WSDownApkProgressDialog extends WSCommonDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    String buttonText;
    WSDialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.progress)
    DonutProgress progress;
    @BindView(R.id.btn_cancle)
    Button btnCancle;
    WSAppUpdate wsAppUpdate;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;

    public WSDownApkProgressDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        this(context, title, "取消", dialogCallBackTwo);

    }

    public WSDownApkProgressDialog(Context context, String title, String buttonText, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public WSDownApkProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected WSDownApkProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    public void setData(WSAppUpdate wsAppUpdate) {
        this.wsAppUpdate = wsAppUpdate;
        progress.setVisibility(View.INVISIBLE);
        WSYesOrNoEnum forceUpdate = wsAppUpdate.getForceUpdate();//是否是强制升级
        if (forceUpdate == WSYesOrNoEnum.YES) {
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
                if(tag){
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
