package cn.com.ethank.mylibrary.resourcelibrary.common_util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.support.v4.app.Fragment;
import android.util.Log;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

public class FileUtils {


    public static void deleteFile(String filePath) {
        File f = new File(filePath);
        if (f != null && f.exists()) {
            f.delete();
        }
    }

    public static boolean saveImage(Bitmap photo, String spath) {
        try {
            if (!new File(spath).getParentFile().exists()) {
                new File(spath).getParentFile().mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getCameraTypeName(int index) {
        if (index < 10) {
            return "00" + index;
        } else {
            return "0" + index;
        }
    }

    public static void fileChannelCopy(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();
            out = fo.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }

    public static void deleteAllFilesNoDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesNoDir(files[i]);
        }
    }

    public static File getFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }

    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 将context路径转换为内存卡中的路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static Bitmap decodeUriAsBitmap(Uri uri, Context context) {
        Bitmap bitmap = null;
        try {
            // bitmap =
            // BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),
                    uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


    public static void cropImage(Uri uri, int outputX, int outputY, int resultCode, Context context) {
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);// 输出图片大小
        intent.putExtra("outputY", outputY);
        // intent.putExtra("return-data", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        Activity a = (Activity) context;
        a.startActivityForResult(intent, resultCode);
    }

    public static void cropImage(Uri uri, int outputX, int outputY, int resultCode, Context context
            , String filePath) {
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);// 输出图片大小
        intent.putExtra("outputY", outputY);
        //创建文件
        createPath(filePath);
        Uri uriPath = Uri.parse("file://" + filePath);
        //将裁剪好的图输出到所建文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);

//		intent.putExtra("return-data", false);
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//		intent.putExtra("noFaceDetection", true); // no face detection
        Activity a = (Activity) context;
        a.startActivityForResult(intent, resultCode);
    }

    // fragment使用
    public static void cropImage(Uri uri, int outputX, int outputY, int resultCode, Fragment fragment
            , String filePath) {
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);// 输出图片大小
        intent.putExtra("outputY", outputY);
        //创建文件
        createPath(filePath);
        Uri uriPath = Uri.parse("file://" + filePath);
        //将裁剪好的图输出到所建文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);
        fragment.startActivityForResult(intent, resultCode);
    }

    private static void createPath(String path) {
        File pathFile = new File(path);
        if (pathFile.getParentFile().exists()) {
            if (pathFile.isFile()) {
                pathFile.delete();
                return;
            }
        }
        pathFile.getParentFile().mkdirs();
    }


    public static String getSDPath() {
        String path = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();//获取跟目录
        } else {
            path = "sdcard";
        }
        LoggerUtil.e("path=" + path);
        return path;
    }


    public static String getAppPath(String fileDir) {
        String path = getSDPath() + File.separator
                + fileDir;
        return path;
    }

    public static Bitmap convertBmp(Bitmap bmp) {
        int w = bmp.getWidth();
        int h = bmp.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1); // 镜像水平翻转
        Bitmap convertBmp = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);

        return convertBmp;
    }

    public static String writeFile(InputStream in, File file) {
        FileOutputStream fileOutputStream = null;
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            fileOutputStream = new FileOutputStream(file);
            byte[] bytes = new byte[1024 * 1024];
            int len = -1;
            while ((len = in.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
            fileOutputStream.flush();
            return file.getAbsolutePath();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return "";

    }

}
