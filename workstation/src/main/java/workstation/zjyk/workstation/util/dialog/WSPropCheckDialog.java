package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 输入秘钥
 * Created by zjgz on 2017/10/31.
 */

public class WSPropCheckDialog extends WSCommonDialog {
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    String title;
    WSDialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.tv_check_title)
    TextView tvCheckTitle;
    @BindView(R.id.tv_check_result)
    TextView tvCheckResult;
    @BindView(R.id.tv_check_prop_title)
    TextView tvCheckPropTitle;
    @BindView(R.id.tv_check_prop_value)
    TextView tvCheckPropValue;

    @BindView(R.id.tv_check_prop_back)
    LinearLayout tvCheckPropBack;

    @BindView(R.id.tv_check_fanwei_title)
    TextView tvCheckFanweiTitle;
    @BindView(R.id.tv_check_fanwei)
    TextView tvCheckFanwei;
    String message;

    public WSPropCheckDialog(Context context, String title, String message, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.message = message;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView();
    }

    public WSPropCheckDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected WSPropCheckDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }


    private void initDialogView() {
        setContentView(R.layout.dialog_prop_check);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        setMessage(message);
    }

    public void setMessage(String message) {
        String[] split = message.split(";");
        if (split != null && split.length == 5) {
            String checkResult = split[0];
            String checkOrder = split[1];
            String checkPropName = split[2];
            String checkPropValue = split[3];
            String checkRange = split[4];
            tvTitle.setText(checkOrder);
            if ("YES".equals(checkResult.toUpperCase())) {
                tvCheckResult.setText("合格");
                tvCheckPropBack.setBackgroundColor(this.getContext().getResources().getColor(R.color.c_btn_bg));
            } else {
                tvCheckResult.setText("不合格");
                //tvCheckPropBack.setBackgroud(getResources().getColor(R.color.red));
            }
            tvCheckPropTitle.setText(checkPropName);
            tvCheckPropValue.setText(checkPropValue);
            tvCheckFanwei.setText(checkRange);
        }

        if(tvCheckFanwei != null){
            tvCheckFanwei.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            },4000);
        }
    }

    @OnClick({R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_sure:
                dismiss();
                break;
        }
    }


}
