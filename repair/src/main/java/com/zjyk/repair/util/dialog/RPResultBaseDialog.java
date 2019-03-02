package com.zjyk.repair.util.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.zjyk.repair.ui.manager.RPUserManager;

/**
 * Created by zjgz on 2018/1/29.
 */

public abstract class RPResultBaseDialog extends RPCommonDialog implements DialogInterface.OnDismissListener {
    public RPResultBaseDialog(Context context) {
        super(context, 0);
    }

    public RPResultBaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        setOnDismissListener(this);
    }

    protected RPResultBaseDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public abstract String getTitle();

    public abstract int getCode();

    @Override
    public void onDismiss(DialogInterface dialog) {
        RPUserManager.getInstance().removeDialog(this);
    }
}
