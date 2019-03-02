package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSAppUpdate;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;


public class WSPushOneDialog extends WSCommonDialog {


    private String title;
    WSDialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.btn_cancle)
    Button btnCancle;

    public WSPushOneDialog(Context context, String title, int code, WSDialogCallBackTwo dialogCallBackTwo) {
        this(context, title, "取消", dialogCallBackTwo);

    }

    public WSPushOneDialog(Context context, String title, String buttonText, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public WSPushOneDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected WSPushOneDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    public void setData(WSAppUpdate wsAppUpdate) {
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
