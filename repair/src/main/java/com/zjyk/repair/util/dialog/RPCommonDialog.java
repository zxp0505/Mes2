package com.zjyk.repair.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.scan.ScanGunKeyEventHelper;

/**
 * Created by terry on 8/2/16.
 */

public abstract class RPCommonDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = "RPCommonDialog";
    private ScanGunKeyEventHelper mScanGunKeyEventHelper;

    public RPCommonDialog(Context context) {
        super(context);
        initDialogAttrs(context);
    }

    public RPCommonDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogAttrs(context);
    }

    protected RPCommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        if (!(getCurrentFocus() instanceof EditText )&& mScanGunKeyEventHelper.isScanGunEvent(event)) {
            //dialog输入键盘的时候也会出发
            mScanGunKeyEventHelper.analysisKeyEvent(event);
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
}
