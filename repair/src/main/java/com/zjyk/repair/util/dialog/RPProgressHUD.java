package com.zjyk.repair.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zjyk.repair.R;


public class RPProgressHUD extends Dialog {

    public RPProgressHUD(Context context) {
        super(context);
    }

    public RPProgressHUD(Context context, int theme) {
        super(context, theme);
    }

    public static RPProgressHUD show(Context context) {
        return show(context,null,false,null);
    }
    public static RPProgressHUD show(Context context, CharSequence message) {
        return show(context,message,false,null);
    }
    /**
     * 显示加载中提示框
     * @param context
     * @param message 提示消息内容
     * @param cancelable 点击其他地方关闭
     * @param cancelListener 关闭后事件
     * @return
     */
    public static RPProgressHUD show(Context context, CharSequence message, boolean cancelable,
                                     OnCancelListener cancelListener) {
        RPProgressHUD dialog = new RPProgressHUD(context, R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.progress_hud);
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = dialog.findViewById(R.id.message);
            txt.setVisibility(View.VISIBLE);
            txt.setText(message);
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
//      dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.show();
        return dialog;
    }
}
