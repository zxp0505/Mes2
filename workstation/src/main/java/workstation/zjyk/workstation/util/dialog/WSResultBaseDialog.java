package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.content.DialogInterface;

import workstation.zjyk.workstation.ui.manager.WSUserManager;

/**
 * Created by zjgz on 2018/1/29.
 */

public abstract class WSResultBaseDialog extends WSCommonDialog implements DialogInterface.OnDismissListener {
    public WSResultBaseDialog(Context context) {
        super(context, 0);
    }

    public WSResultBaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        setOnDismissListener(this);
    }

    protected WSResultBaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public abstract String getTitle();

    public abstract int getCode();

    @Override
    public void onDismiss(DialogInterface dialog) {
        WSUserManager.getInstance().removeDialog(this);
    }
}
