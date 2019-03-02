package cn.com.ethank.mylibrary.resourcelibrary.glide.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.util.UUID;

import cn.com.ethank.mylibrary.resourcelibrary.glide.ImageLoader;


public class GlideCircleTransform extends BitmapTransformation {
	private int viewWidth = 0;
	private int viewHeight = 0;
	private View view;
	private String uri;

	public GlideCircleTransform(Context context, View view, String uri) {
		super(context);
		this.view = view;
		this.uri = uri;
	}

	public GlideCircleTransform(Context context) {
		super(context);
	}

	@Override
	protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
		return circleCrop(pool, toTransform);
	}

	private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
		if (source == null)
			return null;
		if (view != null) {
			viewWidth = view.getMeasuredWidth();
			viewHeight = view.getMeasuredHeight();
		}
		int mBitmapWidth = source.getWidth();
		int mBitmapHeight = source.getHeight();
		int resultWidth = 0;
		int resultHeight = 0;
		int x = 0;
		int y = 0;
		if (viewWidth == 0 || viewHeight == 0) {
			resultWidth = source.getWidth();
			resultHeight = source.getHeight();
			x = 0;
			y = 0;
		} else {
			float scale = Math.max(viewWidth * 1.0f / mBitmapWidth, viewHeight * 1.0f / mBitmapHeight);
			resultWidth = (int) (viewWidth / scale);
			resultHeight = (int) (viewHeight / scale);
			x = Math.abs((mBitmapWidth - resultWidth) / 2);
			y = Math.abs((mBitmapHeight - resultHeight) / 2);
		}

		Bitmap squared = Bitmap.createBitmap(source, x, y, resultWidth, resultHeight);

		Bitmap result = pool.get(resultWidth, resultHeight, Bitmap.Config.ARGB_8888);
		if (result == null) {
			result = Bitmap.createBitmap(resultWidth, resultHeight, Bitmap.Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(result);
		Paint paint = new Paint();
		paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
		paint.setAntiAlias(true);
		float r = Math.min(resultWidth, resultHeight) * 1f / 2f;
		canvas.drawCircle(resultWidth / 2, resultHeight / 2, r, paint);

		// 这里不能直接回收，直接回收会出现在列表中加载图片时导致有些图片加载不出来，直接将图片放入lrc缓存中自动回收
		// RecycleUtil.recycleBitmap(source);
		// RecycleUtil.recycleBitmap(squared);

		ImageLoader.addBitmapToMemoryCache(UUID.randomUUID().toString(), source);
		ImageLoader.addBitmapToMemoryCache(UUID.randomUUID().toString(), squared);
		return result;
	}

	@Override
	public String getId() {
		return getClass().getName();
	}
}