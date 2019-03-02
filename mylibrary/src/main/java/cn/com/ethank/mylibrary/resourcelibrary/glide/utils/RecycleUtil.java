package cn.com.ethank.mylibrary.resourcelibrary.glide.utils;

import android.graphics.Bitmap;

/**
 * Created by dddd on 2015/12/5.
 */
public class RecycleUtil {
    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmap = null;

    }
}
