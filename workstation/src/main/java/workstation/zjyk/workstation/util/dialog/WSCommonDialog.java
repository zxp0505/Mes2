package workstation.zjyk.workstation.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.scan.ScanGunKeyEventHelper;
import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.R;

/**
 * Created by terry on 8/2/16.
 */

public abstract class WSCommonDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = "WSCommonDialog";
    protected static final int MUST_HANDLE_TAG = 5;//必须手动输入得tag
    protected static final int HANDLE_AND_SCAN_TAG = 10;//手动输入和扫码兼并得tag
    private ScanGunKeyEventHelper mScanGunKeyEventHelper;

    public WSCommonDialog(Context context) {
        super(context);
        initDialogAttrs(context);
    }

    public WSCommonDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogAttrs(context);
    }

    protected WSCommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
                if (!TextUtils.isEmpty(barcode)) {
                    onScanResult(barcode);
                }
            }
        });
    }

    public void onScanResult(String scanResult) {

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        Log.e(TAG, "dispatchKeyEvent");
        Log.e(TAG, "event.getDeviceId()---:" + event.getDeviceId());
        View currentFocus = getCurrentFocus();
        int deviceId = event.getDeviceId();
        if (currentFocus != null && currentFocus instanceof EditText) {
            EditText currentFocusEditext = (EditText) currentFocus;
            Integer tag = (Integer) currentFocusEditext.getTag();
            Log.e(TAG, "dispatchKeyEvent---tag ------：" + tag);
            if (tag != null && tag == MUST_HANDLE_TAG && mScanGunKeyEventHelper.isScanGunEvent(event)) {
                Log.e(TAG, "dispatchKeyEvent---tag =5");
                if (deviceId > 0) {
                    //edittext 得input方式会影响addtextchanglistner
                    ToastUtil.showCenterShort(currentFocus.getContext().getString(R.string.text_handle_input), true);
                    currentFocusEditext.setTag(R.id.tag_first, 1);//此处事处理手动是guru条码得判断 必须手动输入
                    return true;
                } else {
                    currentFocusEditext.setTag(R.id.tag_first, 0);
                    return super.dispatchKeyEvent(event);
                }

//                currentFocusEditext.setFocusable(false);
//                currentFocusEditext.setFocusableInTouchMode(false);
//                currentFocusEditext.clearFocus();
//                KeyboardUtils.hideKeyboardOnly(currentFocusEditext);

            }
            if (tag != null && tag == HANDLE_AND_SCAN_TAG && deviceId > 0 && mScanGunKeyEventHelper.isScanGunEvent(event)) {
                //扫码枪输入
                Log.e(TAG, "dispatchKeyEvent---tag =10");
//                handleTagIs10HideSofe(currentFocusEditext, tagIs10);
//                tagIs10 = true;
//                mScanGunKeyEventHelper.analysisKeyEvent(event);
//                return true;
                return super.dispatchKeyEvent(event);
            }
        }
        if (!(getCurrentFocus() instanceof EditText) && mScanGunKeyEventHelper.isScanGunEvent(event) && deviceId > 0) {
            //dialog输入键盘的时候也会出发
            Log.e(TAG, "dispatchKeyEvent---tag =....");
            mScanGunKeyEventHelper.analysisKeyEvent(event);
            return true;
        }

        return super.dispatchKeyEvent(event);
    }


    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        Log.e(TAG, "onKeyDown");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onPause(){
        if(getCurrentFocus()!= null && getCurrentFocus() instanceof  EditText){
            EditText currentFocus = (EditText) getCurrentFocus();
            KeyboardUtils.hideKeyboardOnly(currentFocus);
        }
    }
}
