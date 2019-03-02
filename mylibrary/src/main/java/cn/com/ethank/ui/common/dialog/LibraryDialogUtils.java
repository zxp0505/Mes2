package cn.com.ethank.ui.common.dialog;

import android.content.Context;

import cn.com.ethank.ui.common.dialog.callback.ResourceDialogCallBackTwo;

public class LibraryDialogUtils {
    public static void showSetDomainDialog(Context context, String currentHost, String title, ResourceDialogCallBackTwo callBack) {
        SetDomainDialog setDomainDialog = new SetDomainDialog(context, title, callBack);
        setDomainDialog.setCanceledOnTouchOutside(false);
        setDomainDialog.setCurrentHost(currentHost);
        setDomainDialog.show();
    }
}
