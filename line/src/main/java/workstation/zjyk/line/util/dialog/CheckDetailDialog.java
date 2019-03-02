package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.line.R;

/**
 * 查看物料详情的diaLog
 * Created by zjgz on 2017/11/1.
 */

public class CheckDetailDialog extends CommonDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    String detail;
    @BindView(R.id.tv_materail_detail)
    TextView tvMaterailDetail;


    public CheckDetailDialog(Context context, String title, String detail) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.detail = detail;
        initDialogView(context);

    }

    public CheckDetailDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected CheckDetailDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_check_detail);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        tvMaterailDetail.setText(detail);
        btnSure.setText("确定");

    }


    @OnClick(R.id.btn_sure)
    public void onViewClicked() {
        dismiss();
    }
}
