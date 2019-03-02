package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSAppUpdate;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;


public class WSPushTwoDialog extends WSCommonDialog {


    private String title;
    WSDialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    int code;

    public WSPushTwoDialog(Context context, String title, int code, WSDialogCallBackTwo dialogCallBackTwo) {
        this(context, title, "取消", dialogCallBackTwo);
        this.code = code;
    }

    public WSPushTwoDialog(Context context, String title, String buttonText, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public WSPushTwoDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected WSPushTwoDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    public void setData(WSAppUpdate wsAppUpdate) {
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_push_two);
        ButterKnife.bind(this);
        tvTitle.setText(title);


    }

    public String getTitle() {
        return title;
    }

    @OnClick({R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                if (dialogCallBackTwo != null) {
                    dialogCallBackTwo.OnPositiveClick(code);
                }
                dismiss();
                break;
        }

    }

}
