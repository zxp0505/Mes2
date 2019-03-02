package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.ui.customview.DonutProgress;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;


public class WSProgressDialog extends WSCommonDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    String buttonText;
    WSDialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.progress)
    DonutProgress progress;

    public WSProgressDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        this(context, title, "取消", dialogCallBackTwo);

    }

    public WSProgressDialog(Context context, String title, String buttonText, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.buttonText = buttonText;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public WSProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected WSProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_progress);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        btnSure.setText(buttonText);

    }

    public void setProgress(float processB){
        float  process=  NumberUtils.toFloatSaveOne(processB*100);
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
