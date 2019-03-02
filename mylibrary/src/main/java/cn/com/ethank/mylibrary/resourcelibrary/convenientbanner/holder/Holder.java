package cn.com.ethank.mylibrary.resourcelibrary.convenientbanner.holder;

/**
* Created by Sai on 15/12/14.
* @param <T>
*/
import android.content.Context;
import android.view.View;

public interface Holder<T> {
	View createView(Context context);

	void UpdateUI(Context context, int position, T data);
}