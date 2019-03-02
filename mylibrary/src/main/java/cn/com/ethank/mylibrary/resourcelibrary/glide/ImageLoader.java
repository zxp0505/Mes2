package cn.com.ethank.mylibrary.resourcelibrary.glide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.util.LruCache;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.ImageViewTarget;

import cn.com.ethank.mylibrary.resourcelibrary.glide.utils.GlideCircleTransform;
import cn.com.ethank.mylibrary.resourcelibrary.glide.utils.GlideRoundShadowTransform;
import cn.com.ethank.mylibrary.resourcelibrary.glide.utils.GlideRoundTransform;
import cn.com.ethank.mylibrary.resourcelibrary.glide.view.MyImageViewTarget;


@SuppressLint("NewApi")
public class ImageLoader {

    private static LruCache<String, Bitmap> lruCache;
    /**
     * @params DEFAULT默认格式
     * @params SINGEL带圆角
     * @params ROUND设置成圆圈
     * https://github.com/wasabeef/glide-transformations   crop  blur mask 四角倒圆角 圆形  模糊 五角星
     * <p>
     * 在targetImageview中 可以监听加载图片的start  error complite
     */
    public static final int DEFAULT = 0;
    public static final int SINGEL = 1;
    public static final int ROUND = 2;
    public static final int OTHER = 3;

    public static void loadGifImage(Context context, int id, ImageView view) {
        Glide.with(context).load(id).into(view);
    }

    public static void loadImage(Context context, String uri, ImageView view) {
        loadImage(context, uri, view, 0);
    }

    public static void loadImage(Context context, String uri, ImageView view, int type) {
        loadImage(context, uri, 0, view, type, 0);
    }

    public static void loadImage(Context context, String uri, ImageView view, int type, int Radius) {
        loadImage(context, uri, 0, view, type, Radius);
    }

    public static void loadImage(Context context, String uri, int defaultSrc, ImageView view) {
        loadImage(context, uri, defaultSrc, view, 0, 0);
    }

    public static void loadImage(Context context, String uri, int defaultSrc, ImageViewTarget view) {
        loadImage(context, uri, defaultSrc, view, 0, 0);
    }

    @SuppressWarnings("unchecked")
    public static void loadImage(Context context, final String uri, final int defaultSrcImageId, ImageView view, int type, int Radius) {
        // 加载图片
        try {
            if (context == null || view == null) {
                return;
            }

            RequestManager requestManager = null;
            try {
                requestManager = Glide.with(context);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (requestManager == null) {
                return;
            }
            // 0是默认,1是圆角,2是圆圈
            if (type == 1) {
                requestManager.load(uri).placeholder(defaultSrcImageId).dontAnimate().crossFade(0)
                        .transform(new GlideRoundTransform(context, view, uri, Radius)).into(new MyImageViewTarget(view));
            } else if (type == 2) {
                requestManager.load(uri).placeholder(defaultSrcImageId).crossFade().transform(new GlideCircleTransform(context, view, uri))
                        .into(new MyImageViewTarget(view));
            } else if (type == 3) {
                requestManager.load(uri).placeholder(defaultSrcImageId).crossFade().transform(new GlideRoundShadowTransform(context, view, uri))
                        .into(new MyImageViewTarget(view));
            } else {
                requestManager.load(uri).centerCrop().placeholder(defaultSrcImageId).dontAnimate().crossFade(0).into(new MyImageViewTarget(view));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadImage(Context context, final String uri, final int defaultSrcImageId, ImageViewTarget view, int type, int Radius) {
        // 加载图片
        try {
            if (context == null || view == null) {
                return;
            }

            RequestManager requestManager = null;
            try {
                requestManager = Glide.with(context);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (requestManager == null) {
                return;
            }
            // 0是默认,1是圆角,2是圆圈
            if (type == 1) {
                requestManager.load(uri).placeholder(defaultSrcImageId).dontAnimate().crossFade(0)
                        .transform(new GlideRoundTransform(context, view.getView(), uri, Radius)).into(view);
            } else if (type == 2) {
                requestManager.load(uri).placeholder(defaultSrcImageId).crossFade().transform(new GlideCircleTransform(context, view.getView(), uri))
                        .into(view);
            } else if (type == 3) {
                requestManager.load(uri).placeholder(defaultSrcImageId).crossFade().transform(new GlideRoundShadowTransform(context, view.getView(), uri))
                        .into(view);
            } else {
                requestManager.load(uri).centerCrop().placeholder(defaultSrcImageId).dontAnimate().crossFade(0).into(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 往内存缓存中添加Bitmap
     *
     * @param key
     * @param bitmap
     */
    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null && bitmap != null) {
            getBitmapCache().put(key, bitmap);
        }
    }

    private static synchronized LruCache<String, Bitmap> getBitmapCache() {
        if (lruCache == null)
            buildCache();
        return lruCache;
    }

    private static void buildCache() {
        // 给LruCache分配1/8 4M,此处只用于表情缓存,不需要太大
        if (lruCache == null) {
            // 获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            int mCacheSize = maxMemory / 20;
            lruCache = new LruCache<String, Bitmap>(mCacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }
    }

    /**
     * 根据key来获取内存中的图片
     *
     * @param key
     * @return
     */
    public static Bitmap getBitmapFromMemCache(String key) {
        return getBitmapCache().get(key);
    }

    /**
     * 注意清内存只能在主线程，清硬盘只能在子线程
     */
    public static void clearCache(final Context c) {
        Glide.get(c).clearMemory();
        if (Looper.myLooper() == Looper.getMainLooper()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Glide.get(c).clearDiskCache();
                }
            }).start();
        } else {
            Glide.get(c).clearDiskCache();
        }
    }
}
