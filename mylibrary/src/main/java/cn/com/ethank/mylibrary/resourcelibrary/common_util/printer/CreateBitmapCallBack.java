package cn.com.ethank.mylibrary.resourcelibrary.common_util.printer;

import android.graphics.Bitmap;

/**
 * Created by zjgz on 2017/11/9.
 */

public interface CreateBitmapCallBack {
    void onScuess(Bitmap bitmap);

    void onFail();
}
