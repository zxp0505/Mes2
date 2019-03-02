package workstation.zjyk.line.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.scan.ScanGunKeyEventHelper;
import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.PhoneUtils;
import workstation.zjyk.line.R;

/**
 * Created by terry on 8/2/16.
 */

public abstract class CommonDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = "CommonDialog";
    private ScanGunKeyEventHelper mScanGunKeyEventHelper;

    public CommonDialog(Context context) {
        super(context);
        initDialogAttrs(context);
    }

    public CommonDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogAttrs(context);
    }

    protected CommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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

        initScan();
    }

    protected void initScan() {
        //检查登陆信息
        mScanGunKeyEventHelper = new ScanGunKeyEventHelper(new ScanGunKeyEventHelper.OnScanSuccessListener() {
            @Override
            public void onScanSuccess(String barcode) {
                Log.e(TAG, "barcode:" + barcode);
                onScanResult(barcode);
            }
        });
    }

    public void onScanResult(String scanResult) {

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        //!(getCurrentFocus() instanceof EditText )&&

        if (!(getCurrentFocus() instanceof EditText) && mScanGunKeyEventHelper.isScanGunEvent(event)) {
            //dialog输入键盘的时候也会出发
            mScanGunKeyEventHelper.analysisKeyEvent(event);
            return true;
        }
        Log.e(TAG, "event.getDeviceId()---:" + event.getDeviceId());
        if (getCurrentFocus() instanceof EditText && event.getDeviceId() > 0) {
            ToastUtil.showCenterShort(getCurrentFocus().getContext().getString(R.string.text_handle_input), true);
            KeyboardUtils.hideKeyboardOnly((EditText) getCurrentFocus());
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}
