package workstation.zjyk.line.util.download;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class AppUpdate 
{

	/** 测试提交 **/
	private Context mContext;
	// 下载文件的id
	public static  long downId;

	// 下载管理
	private DownloadManager downManager;

	// 下载文件
	private File existFile;
	String app_name;

	public AppUpdate(Context ctx) {
		mContext = ctx;
		app_name = (String) mContext.getApplicationInfo().loadLabel(mContext.getPackageManager());
		downManager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
	}

	

//	public File download(UpdateInfo2 updateInfo) throws UnsupportedEncodingException {
//		if (updateInfo != null) {
//			downApk(updateInfo.getUrl(), app_name, "正在下载...");
//		}
//		return null;
//	}

	/**
	 * 
	 * 下载apk
	 * 
	 */
	@SuppressLint("NewApi")
	public void downApk(String apkUrl, String fileName, String description) throws UnsupportedEncodingException {
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
		request.setTitle(fileName);
		request.setDescription(description);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			request.allowScanningByMediaScanner();
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
		}
		request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
		try {
			if (downManager != null) {
				downId = downManager.enqueue(request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Uri uri = Uri.parse(apkUrl);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			mContext.startActivity(intent);
		}

	}

	/**
	 * 
	 * 判断是否下载了
	 * 
	 */

	@SuppressLint("NewApi")
	protected boolean isDownLoad(String title) {
		Query query = new Query();
		query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
		Cursor c = downManager.query(query);
		if (c == null) {
			return false;
		}
		while (c.moveToNext()) {
			if (title.equals(c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE)))) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					String str = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
					if (str == null)
						return false;
					existFile = new File(str);

				} else {
					existFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + title);
				}
				break;
			}
		}
		if (c != null) {
			c.close();
		}

		return existFile != null && existFile.exists();
	}

	/**
	 * 
	 * 获得包名
	 * 
	 */
	public static String getPacakgeName(Context context) {
		return context.getPackageName();
	}

	/**
	 * 
	 * 获得软件版本
	 * 
	 */
	public static String getVersionName(Context context) {
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

			return info.versionName;
		} catch (NameNotFoundException e) {
		}
		return "";
	}

	/**
	 * 
	 * 获得软件code
	 * 
	 */
	public static int getVersionCode(Context context) {
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
		}
		return 0;
	}

	/**
	 * 
	 * 安装软件
	 * 
	 */
	public static void setupApk(Context context, File file) {
		Log.i("", "zzzz--gg==="+file.exists());
		if (file.exists()) {
			Intent intentApk = new Intent(Intent.ACTION_VIEW);
			intentApk.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intentApk.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intentApk.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			context.startActivity(intentApk);
		}
	}

	
	// 判断版本号是否需要升级
	public static boolean isNewer(String serverVersion, String clientVersion) {
		String s1[] = serverVersion.split("\\.");
		String s2[] = clientVersion.split("\\.");

		if (s1.length != s2.length) {
			if (s1.length < s2.length) {
				for (int n = 1; n <= s2.length - s1.length; n++) {
					serverVersion += ".0";
				}
			} else {
				for (int n = 1; n <= s1.length - s2.length; n++) {
					clientVersion += ".0";
				}
			}
		}
		s1 = serverVersion.split("\\.");
		s2 = clientVersion.split("\\.");
		for (int i = 0; i < s1.length; i++) {
			int t1 = Integer.parseInt(s1[i]);
			int t2 = Integer.parseInt(s2[i]);
			if (t1 > t2) {
				return true;
			} else if (t1 < t2)
				return false;
		}
		return false;
	}
}
