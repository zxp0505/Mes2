package workstation.zjyk.com.scanapp.util.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.net.ScanErrorResultClickListner;
import workstation.zjyk.com.scanapp.ui.application.ScanBaseApplication;


public class ScanResultErrorDialog extends ScanResultBaseDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    String title;
    int code;
    boolean isTwoTips;
    ScanErrorResultClickListner errorResultClickListner;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.btn_sure1)
    Button btnSure1;
    @BindView(R.id.btn_cancle1)
    Button btnCancle1;
    @BindView(R.id.rl_two_tips)
    RelativeLayout rlTwoTips;

    public ScanResultErrorDialog(Context context, String title) {
        this(context, -1, title, false, null);

    }

    public ScanResultErrorDialog(Context context, int code, String title, boolean isTwoTips, ScanErrorResultClickListner errorResultClickListner) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.code = code;
        this.errorResultClickListner = errorResultClickListner;
        this.isTwoTips = isTwoTips;
        initDialogView(context);

    }

    public ScanResultErrorDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected ScanResultErrorDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_result_error);
        ButterKnife.bind(this);
        checkTitleWidth();
        tvTitle.setText(title);
        btnSure.setText("确定");
        if (isTwoTips) {
            rlTwoTips.setVisibility(View.VISIBLE);
            btnSure.setVisibility(View.GONE);
        } else {
            rlTwoTips.setVisibility(View.GONE);
            btnSure.setVisibility(View.VISIBLE);
        }

    }

    private void checkTitleWidth() {
        ViewGroup.LayoutParams layoutParams = llRoot.getLayoutParams();
        if (!TextUtils.isEmpty(title) && title.length() > 16) {
            if (title.length() < 40) {
                layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            } else {
                int dimensionPixelOffset = getContext().getResources().getDimensionPixelOffset(R.dimen.x_design_500px);
                layoutParams.width = dimensionPixelOffset;
            }
            llRoot.setLayoutParams(layoutParams);
        } else {
            int dimensionPixelOffset = getContext().getResources().getDimensionPixelOffset(R.dimen.x_design_500px);
            if ((layoutParams.width - dimensionPixelOffset) > 3) {
                layoutParams.width = dimensionPixelOffset;
                llRoot.setLayoutParams(layoutParams);
            }
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getCode() {
        return code;
    }


    @OnClick({R.id.btn_sure, R.id.btn_sure1, R.id.btn_cancle1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sure1:
            case R.id.btn_sure:
                if (errorResultClickListner != null) {
                    errorResultClickListner.confirm(code);
                }
                if (!TextUtils.isEmpty(title) && title.equals("该Pad未注测")) {
                    ScanBaseApplication.getInstance().exit();
                }
                dismiss();
                break;

            case R.id.btn_cancle1:
                dismiss();
                break;
        }

    }
}
