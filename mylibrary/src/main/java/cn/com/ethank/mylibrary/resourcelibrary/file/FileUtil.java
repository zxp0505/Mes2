package cn.com.ethank.mylibrary.resourcelibrary.file;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public final class FileUtil {

    public static byte[] readBytes(InputStream stream) {
        return inputStreamToBytes(stream);
    }

    public static String readUTF8String(InputStream stream) {
        byte[] buff = readBytes(stream);
        if (buff == null)
            return null;
        try {
            return new String(buff, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static byte[] readBytes(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return null;

        BufferedInputStream bis = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(filePath));
            bis = new BufferedInputStream(fis);
            return readBytes(bis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } finally {
            close(bis);
            close(fis);
        }
        return null;
    }

    public static String readString(String filePath, String encode) {
        byte[] bytes = readBytes(filePath);
        if (bytes == null)
            return null;
        try {
            return new String(bytes, encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String readUTF8String(String filePath) {
        return readString(filePath, "utf-8");
    }

    public static boolean createDirs(String dirPath) {
        File folder = new File(dirPath);
        if (!(folder.exists()) && !(folder.isDirectory())) {
            return folder.mkdirs();
        }
        return false;
    }

    public static boolean deleteFile(String filePath) {
        File localfile = new File(filePath);
        if (localfile.exists() && localfile.isFile()) {
            return localfile.delete();
        }
        return false;
    }

    public static void deleteFolder(String folderPath) {
        File localFolder = new File(folderPath);
        if (localFolder.isDirectory()) {
            String[] childrenStrings = localFolder.list();
            int length = childrenStrings.length;
            for (int i = 0; i < length; i++) {
                File temp = new File(localFolder, childrenStrings[i]);
                if (temp.isDirectory()) {
                    deleteFolder(temp.getName());
                } else {
                    temp.delete();
                }
            }
            localFolder.delete();
        }
    }

    public static boolean renameFolder(String oldFolderPath, String newFolderPath) {
        return renamePath(oldFolderPath, newFolderPath, true);
    }

    public static boolean renameFile(String oldFilePath, String newFilePath) {
        return renamePath(oldFilePath, newFilePath, false);
    }

    private static boolean renamePath(String oldPath, String newPath, boolean isDir) {
        if (oldPath == null || newPath == null)
            return false;
        File localFile = new File(oldPath);
        File newFile = new File(newPath);
        if (localFile.exists() && (isDir ? localFile.isDirectory() : localFile.isFile())) {
            if (newFile.exists() && (isDir ? newFile.isDirectory() : newFile.isFile()))

                if (isDir) {
                    deleteFolder(newPath);
                } else {
                    newFile.delete();
                }
            return localFile.renameTo(newFile);
        }
        return false;
    }

    private static boolean checkPath(String path, boolean checkDir) {
        if (path == null)
            return false;
        File localFile = new File(path);
        if (!localFile.exists()) {
            return false;
        }
        return checkDir ? localFile.isDirectory() : localFile.isFile();
    }

    public static boolean isFileExist(String filePath) {
        return checkPath(filePath, false);
    }

    public static boolean isFolderExist(String filePath) {
        return checkPath(filePath, true);
    }

    public static boolean writeUTF8String(String filePath, String str) {
        if (TextUtils.isEmpty(str))
            return true;
        try {
            return writeBytes(filePath, str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeBytes(String filePath, byte[] bytes) {
        if (bytes == null || bytes.length == 0 || TextUtils.isEmpty(filePath)) {
            return false;
        }
        FileOutputStream fos = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                createDirs(file.getParent());
            }
            fos = new FileOutputStream(file);
            fos.write(bytes);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fos);
        }
        return false;
    }

    public static boolean appendBytes(String filePath, byte[] bytes, int length) throws IOException {
        if (bytes == null || bytes.length == 0 || TextUtils.isEmpty(filePath)) {
            return false;
        }
        FileOutputStream fos = null;
        Exception ee = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return false;
            }
            fos = new FileOutputStream(file, true);
            fos.write(bytes, 0, length);
            return true;
        } catch (FileNotFoundException e) {
            ee = e;
            e.printStackTrace();
        } catch (IOException e) {
            ee = e;
            e.printStackTrace();
        } finally {
            close(fos);
            if (ee != null) {
                throw new IOException(ee.getMessage());
            }
        }
        return false;
    }

    public static boolean createFile(String fileName) {
        File file = new File(fileName);
        if (!(file.exists())) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean isFileExist(Context context, String fileName) {
        String[] fileNameArray = context.fileList();
        for (String f : fileNameArray) {
            if (f.equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNewThan(String firstFileName, String secondFileName) {
        File file1 = new File(firstFileName);
        File file2 = new File(secondFileName);
        if (file1.exists() && file2.exists()) {
            return file1.lastModified() > file2.lastModified();
        }
        return false;
    }

    private static final int BUFFER_SIZE = 4 * 1024;

    public static InputStream rawInputStreamForFile(String filePath) {
        try {
            return new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void close(Closeable stream) {
        if (stream == null)
            return;
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] inputStreamToBytes(InputStream stream) {
        if (stream == null)
            return null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            int count = 0;
            byte tmpData[] = new byte[BUFFER_SIZE];
            while ((count = stream.read(tmpData, 0, BUFFER_SIZE)) > 0) {
                bos.write(tmpData, 0, count);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(stream);
            close(bos);
        }
    }

    public static InputStream bytesToInputStream(byte[] bytes) {
        if (bytes == null)
            return null;
        return new ByteArrayInputStream(bytes);
    }

    // 创建目录
    public File createDir(String romPath, String path) throws IOException {
        try {
            File dir = new File(romPath + path);
            dir.mkdirs();
            return dir;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param romPath     U盘或者本地的主地址 /mnt/sda/sda1 或者 /stroage/emulated/0
     * @param path        主地址下的地址 romPath+/downAPK
     * @param fileName    下载文件的名称
     * @param inputstream
     * @return
     */
    public File writeToSDPATHFromInput(String romPath, String path, String apkName, InputStream inputstream) {
        File file = null;
        OutputStream outputstream = null;
        try {

            createDir(romPath, path);
            file = new File(romPath + path + apkName);
            outputstream = new FileOutputStream(file);
            byte buffer[] = new byte[1024 * 1024 * 5];
            int len = -1;
            // 将输入流中的内容先输入到buffer中缓存，然后用输出流写到文件中
            while ((len = inputstream.read(buffer)) != -1) {
                outputstream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputstream.flush();
                outputstream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
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


}
