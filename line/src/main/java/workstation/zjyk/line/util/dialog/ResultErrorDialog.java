package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import workstation.zjyk.line.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.line.modle.net.ErrorResultClickListner;
import workstation.zjyk.line.ui.BaseApplication;

/**
 * 分料成功
 * Created by zjgz on 2017/11/1.
 */

public class ResultErrorDialog extends CommonDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    int code;
    ErrorResultClickListner errorResultClickListner;

    public ResultErrorDialog(Context context, String title) {
        this(context, -1, title, null);

    }

    public ResultErrorDialog(Context context, int code, String title, ErrorResultClickListner errorResultClickListner) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.code = code;
        this.errorResultClickListner = errorResultClickListner;
        initDialogView(context);

    }

    public ResultErrorDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected ResultErrorDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_result_error);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        btnSure.setText("确定");

    }


    @OnClick(R.id.btn_sure)
    public void onViewClicked() {
        dismiss();
        if (errorResultClickListner != null) {
            errorResultClickListner.confirm(code);
        }
        if (!TextUtils.isEmpty(title) && title.equals("该Pad未注测")) {
            BaseApplication.getInstance().exit();
        }
    }
}
