package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import workstation.zjyk.line.R;


/**
 * Created by terry on 8/2/16.
 */

public class TipDialog extends CommonDialog {

    private TextView txt_title;
    private TextView txt_content;
    private Button btn_cancel;
    private Button btn_confirm;

    protected OnDialogBtnClickListener mOnClickListener;

    public TipDialog(Context context) {
        super(context, R.style.CommonDialog);
        initDialogView();
    }

    public TipDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected TipDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }

    public static TipDialog newInstance(Context context) {
        return new TipDialog(context);
    }

    private void initDialogView() {
        setContentView(R.layout.dialog_tip);
        txt_title = findViewById(R.id.txt_title);
        txt_content = findViewById(R.id.txt_content);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);
    }

    public void hideTitle() {
        txt_title.setVisibility(View.GONE);
    }

    public void setContent(String text) {
        txt_content.setText(text);
    }

    public void setLeftBtnText(String text) {
        btn_cancel.setText(text);
    }

    public void setRightBtnText(String text) {
        btn_confirm.setText(text);
    }

    public void setTitle(String text) {
        txt_title.setText(text);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.btn_cancel) {
            if (mOnClickListener != null) {
                mOnClickListener.onLeftBtnClicked(this);
            }

        } else if (i == R.id.btn_confirm) {
            mOnClickListener.onRightBtnClicked(this);

        }
        dismiss();
    }

    public void setOnBtnClickListener(OnDialogBtnClickListener paramOnDialogBtnClickListener) {
        this.mOnClickListener = paramOnDialogBtnClickListener;
    }

    public interface OnDialogBtnClickListener {
        void onLeftBtnClicked(TipDialog paramTipDialog);

        void onRightBtnClicked(TipDialog paramTipDialog);
    }

}
