package cn.com.ethank.ui.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;

/**
 * Created by terry on 8/2/16.
 */

public abstract class ResourceCommonDialog extends Dialog {

    private static final String TAG = "WSCommonDialog";
    protected static final int MUST_HANDLE_TAG = 5;//必须手动输入得tag
    protected static final int HANDLE_AND_SCAN_TAG = 10;//手动输入和扫码兼并得tag

    public ResourceCommonDialog(Context context) {
        super(context);
        initDialogAttrs(context);
    }

    public ResourceCommonDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogAttrs(context);
    }

    protected ResourceCommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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


    public void onScanResult(String scanResult) {

    }


    @Override
    protected void onStop() {
        super.onStop();
    }


}
