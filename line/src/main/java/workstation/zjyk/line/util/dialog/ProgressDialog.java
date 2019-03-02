package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;
import workstation.zjyk.line.R;
import workstation.zjyk.line.ui.customview.DonutProgress;
import workstation.zjyk.line.util.DialogCallBackTwo;


public class ProgressDialog extends CommonDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    String buttonText;
    DialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.progress)
    DonutProgress progress;

    public ProgressDialog(Context context, String title, DialogCallBackTwo dialogCallBackTwo) {
        this(context, title, "取消", dialogCallBackTwo);

    }

    public ProgressDialog(Context context, String title, String buttonText, DialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.buttonText = buttonText;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public ProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected ProgressDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_progress);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        btnSure.setText(buttonText);

    }

    public void setProgress(float processB) {
        float process = NumberUtils.toFloatSaveOne(processB * 100);
        this.progress.setProgress(process);
    }


    @OnClick(R.id.btn_sure)
    public void onViewClicked() {
        dismiss();
        if (dialogCallBackTwo != null) {
            dialogCallBackTwo.OnPositiveClick("");
        }
    }
}
