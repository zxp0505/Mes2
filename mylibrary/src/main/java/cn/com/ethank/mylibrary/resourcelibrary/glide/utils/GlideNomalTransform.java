package cn.com.ethank.mylibrary.resourcelibrary.glide.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.util.UUID;

import cn.com.ethank.mylibrary.resourcelibrary.glide.ImageLoader;
import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;


public class GlideNomalTransform extends BitmapTransformation {
	private int viewWidth = 0;
	private int viewHeight = 0;
	private View view;
	private float radius = 10;

	public GlideNomalTransform(Context context) {
		super(context);
	}

	public GlideNomalTransform(Context context, View view) {
		this(context);
		this.view = view;
	}

	public GlideNomalTransform(Context context, View view, int designRound) {
		this(context);
		this.view = view;
		// 设计图按750算的圆角
		if (context != null) {
			int screenWidth = UICommonUtil.getScreenWidthPixels(context);
			radius = designRound * screenWidth / 750;
		} else {
			radius = designRound;
		}

	}

	@Override
	protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
		return toTransform;
	}

	public Bitmap circleCrop(BitmapPool pool, Bitmap source) {
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
		// float r = Math.min(resultWidth, resultHeight) * 1f / 2f;
		// canvas.drawCircle(resultWidth / 2, resultHeight / 2, r, paint);
		RectF rectF = new RectF(0f, 0f, resultWidth, resultHeight);
		if (view != null) {
			canvas.drawRoundRect(rectF, radius * resultWidth / viewWidth, radius * resultWidth / viewWidth, paint);
		} else {
			canvas.drawRoundRect(rectF, radius, radius, paint);
		}
		// 这里不能直接回收，直接回收会出现在列表中加载图片时导致有些图片加载不出来，直接将图片放入lrc缓存中自动回收
		// RecycleUtil.recycleBitmap(source);
		// RecycleUtil.recycleBitmap(squared);

		ImageLoader.addBitmapToMemoryCache(UUID.randomUUID().toString(), source);
		ImageLoader.addBitmapToMemoryCache(UUID.randomUUID().toString(), squared);
		return result;
	}

	public Bitmap circleCrop(Bitmap source) {
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

		Bitmap result = Bitmap.createBitmap(resultWidth, resultHeight, Bitmap.Config.ARGB_8888);
		if (result == null) {
			result = Bitmap.createBitmap(resultWidth, resultHeight, Bitmap.Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(result);
		Paint paint = new Paint();
		paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
		paint.setAntiAlias(true);
		// float r = Math.min(resultWidth, resultHeight) * 1f / 2f;
		// canvas.drawCircle(resultWidth / 2, resultHeight / 2, r, paint);
		RectF rectF = new RectF(0f, 0f, resultWidth, resultHeight);
		if (view != null) {
			canvas.drawRoundRect(rectF, radius * resultWidth / viewWidth, radius * resultWidth / viewWidth, paint);
		} else {
			canvas.drawRoundRect(rectF, radius, radius, paint);
		}
		RecycleUtil.recycleBitmap(source);
		RecycleUtil.recycleBitmap(squared);
		return result;
	}

	@Override
	public String getId() {
		return getClass().getName();
	}
}