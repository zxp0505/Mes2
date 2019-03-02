package workstation.zjyk.com.scanapp.util.dialog;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by zjgz on 2018/1/29.
 */

public abstract class ScanResultBaseDialog extends ScanCommonDialog implements DialogInterface.OnDismissListener {
    public ScanResultBaseDialog(Context context) {
        super(context, 0);
    }

    public ScanResultBaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        setOnDismissListener(this);
    }

    protected ScanResultBaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public abstract String getTitle();

    public abstract int getCode();

    @Override
    public void onDismiss(DialogInterface dialog) {
//        WSUserManager.getInstance().removeDialog(this);
    }
}
