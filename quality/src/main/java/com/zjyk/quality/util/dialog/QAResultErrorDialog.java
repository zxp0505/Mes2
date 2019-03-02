package com.zjyk.quality.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.TextView;

import com.zjyk.quality.R;
import com.zjyk.quality.modle.net.QAErrorResultClickListner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class QAResultErrorDialog extends QACommonDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    int code;
    QAErrorResultClickListner errorResultClickListner;

    public QAResultErrorDialog(Context context, String title) {
        this(context, -1, title, null);

    }

    public QAResultErrorDialog(Context context, int code, String title, QAErrorResultClickListner errorResultClickListner) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.code = code;
        this.errorResultClickListner = errorResultClickListner;
        initDialogView(context);

    }

    public QAResultErrorDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected QAResultErrorDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
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
    }
}
