package cn.com.ethank.mylibrary.resourcelibrary.glide.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by dddd on 2015/12/19.
 */
public class GlideTransform extends BitmapTransformation {
    public GlideTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool bitmapPool, Bitmap bitmap, int i, int i1) {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }
}
