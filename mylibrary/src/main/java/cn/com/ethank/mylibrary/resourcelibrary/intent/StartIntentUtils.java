package cn.com.ethank.mylibrary.resourcelibrary.intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Map;

public class StartIntentUtils {

	public static void startIntentUtils(Context context, Class<?> c) {
		Intent intent = new Intent(context, c);
		context.startActivity(intent);
	}

	public static void startIntentUtils(Context context, Class<?> c, String key, String value) {
		Intent intent = new Intent(context, c);
		intent.putExtra(key, value);
		context.startActivity(intent);
	}

	public static void startIntentUtils(Context context, Class<?> c, Map<String, String> map) {
		try {
			Intent intent = new Intent(context, c);
			for (Map.Entry<String, String> kv : map.entrySet()) {
				intent.putExtra(kv.getKey(), kv.getValue());
			}
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void startIntentUtils(Context context, Class<?> c, Bundle bundle) {
		Intent intent = new Intent(context, c);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}

	public static void startIntentUtilsFromResult(Activity context, Class<?> c, int requestCode) {
		Intent intent = new Intent(context, c);
		context.startActivityForResult(intent, requestCode);
	}

	public static void startIntentUtilsFromResult(Activity context, Class<?> c, String key, String value, int requestCode) {
		Intent intent = new Intent(context, c);
		intent.putExtra(key, value);
		context.startActivityForResult(intent, requestCode);
	}

	public static void startIntentUtilsFromResult(Activity context, Class<?> c, int requestCode,Bundle bundle) {
		Intent intent = new Intent(context, c);
		intent.putExtras(bundle);
		context.startActivityForResult(intent, requestCode);
	}

	public static void startIntentUtilsFromResult(Activity context, Class<?> c, Map<String, String> map, int requestCode) {
		try {
			Intent intent = new Intent(context, c);
			for (Map.Entry<String, String> kv : map.entrySet()) {
				intent.putExtra(kv.getKey(), kv.getValue());
			}
			context.startActivityForResult(intent, requestCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void startServiceUtils(Context context, Class<?> c) {
		Intent intent = new Intent(context, c);
		context.startService(intent);
	}

	public static void startServiceUtils(Context context, Class<?> c, String key, String value) {
		Intent intent = new Intent(context, c);
		intent.putExtra(key, value);
		context.startService(intent);
	}

	public static void startServiceUtils(Context context, Class<?> c, Bundle bundle) {
		try {
			Intent intent = new Intent(context, c);
			intent.putExtras(bundle);
			context.startService(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
