package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import workstation.zjyk.line.R;
import workstation.zjyk.line.util.DialogCallBackTwo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zjgz on 2017/11/1.
 */

public class StationSuccessDialog extends CommonDialog {
    @BindView(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    DialogCallBackTwo callBack;

    public StationSuccessDialog(Context context,  DialogCallBackTwo callBack) {
        super(context, R.style.CommonDialog);
        this.callBack =callBack;
        initDialogView(context);
    }

    public StationSuccessDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected StationSuccessDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.pop_feed_one);
        ButterKnife.bind(this);
    }

    public void setData(String data){
        tvDialogTitle.setText(data);
    }

    @OnClick(R.id.tv_cancle)
    public void onViewClicked() {
        callBack.OnPositiveClick("");
        dismiss();
    }
}
