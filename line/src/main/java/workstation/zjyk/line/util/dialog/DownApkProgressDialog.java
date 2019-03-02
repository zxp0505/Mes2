package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.AppUpdate;
import workstation.zjyk.line.modle.bean.YesOrNoEnum;
import workstation.zjyk.line.ui.customview.DonutProgress;
import workstation.zjyk.line.util.DialogCallBackTwo;


public class DownApkProgressDialog extends CommonDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    String buttonText;
    DialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.progress)
    DonutProgress progress;
    @BindView(R.id.btn_cancle)
    Button btnCancle;
    AppUpdate wsAppUpdate;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;

    public DownApkProgressDialog(Context context, String title, DialogCallBackTwo dialogCallBackTwo) {
        this(context, title, "取消", dialogCallBackTwo);

    }

    public DownApkProgressDialog(Context context, String title, String buttonText, DialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public DownApkProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected DownApkProgressDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    public void setData(AppUpdate wsAppUpdate) {
        this.wsAppUpdate = wsAppUpdate;
        progress.setVisibility(View.INVISIBLE);
        YesOrNoEnum forceUpdate = wsAppUpdate.getForceUpdate();//是否是强制升级
        if (forceUpdate == YesOrNoEnum.YES) {
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
