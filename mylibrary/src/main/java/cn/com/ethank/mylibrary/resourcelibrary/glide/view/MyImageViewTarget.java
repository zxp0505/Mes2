package cn.com.ethank.mylibrary.resourcelibrary.glide.view;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;

/**
 * 为了解决Glide图片centerCrop()计算错误,导致图片显示变形,在不同的情况下把imageview设置不同的ScaleType
 *
 * @author Gao Xuefeng
 * @version 创建时间：2015-11-4 下午1:56:59
 */
public class MyImageViewTarget extends ImageViewTarget {

    private ImageView view;
    int width;
    int height;

    public MyImageViewTarget(ImageView view) {
        super(view);
        this.view = view;
    }

    public MyImageViewTarget(ImageView view, int width, int height) {
        super(view);
        this.view = view;
        this.width = width;
        this.height = height;
    }

    @Override
    protected void setResource(Object resource) {
        if (resource != null && resource instanceof Drawable && view != null) {
            view.setScaleType(ScaleType.FIT_XY);
            view.setImageDrawable((Drawable) resource);
        }
    }

    @Override
    public void onResourceReady(Object resource, GlideAnimation glideAnimation) {
        if (view != null) {
            view.setScaleType(ScaleType.FIT_XY);
        }
        super.onResourceReady(resource, glideAnimation);
    }

    @Override
    public void onLoadCleared(Drawable placeholder) {
        setDrfaultStyle();
        super.onLoadCleared(placeholder);
    }

    private void setDrfaultStyle() {
        if (view != null) {
            view.setScaleType(ScaleType.CENTER_CROP);
        }
    }

    @Override
    public void onLoadFailed(Exception e, Drawable errorDrawable) {
        setDrfaultStyle();
        super.onLoadFailed(e, errorDrawable);
    }

    @Override
    public void onLoadStarted(Drawable placeholder) {
        setDrfaultStyle();
        super.onLoadStarted(placeholder);
    }

    @Override
    public void getSize(SizeReadyCallback cb) {
        if (width > 0 && height > 0) {
            cb.onSizeReady(width, height);
            return;
        }
        super.getSize(cb);
    }
}
