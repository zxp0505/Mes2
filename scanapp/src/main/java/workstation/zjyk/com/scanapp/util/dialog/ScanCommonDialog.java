package workstation.zjyk.com.scanapp.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;

/**
 * Created by terry on 8/2/16.
 */

public abstract class ScanCommonDialog extends Dialog {


    public ScanCommonDialog(Context context) {
        super(context);
        initDialogAttrs(context);
    }

    public ScanCommonDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogAttrs(context);
    }

    protected ScanCommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogAttrs(context);
    }

    protected void initDialogAttrs(Context paramContext) {
        setCanceledOnTouchOutside(true);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getAttributes().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getAttributes().y = 0;
        getWindow().setGravity(Gravity.CENTER_VERTICAL);
        getWindow().setAttributes(getWindow().getAttributes());
        if ((paramContext instanceof Activity)) {
            setOwnerActivity((Activity) paramContext);
        }

    }

}
