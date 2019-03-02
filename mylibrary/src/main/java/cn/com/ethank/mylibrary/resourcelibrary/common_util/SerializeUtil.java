package cn.com.ethank.mylibrary.resourcelibrary.common_util;

import android.content.Context;

import cn.com.ethank.mylibrary.resourcelibrary.application.DefaultApplication;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by ping on 2017/3/7.
 */

public class SerializeUtil {
    public static final String PATH_PERSON = "/person.txt";//序列化联系人保存的路径

    public static void save(Object t, String path) {  // 序列化给定的类
        try {
            FileOutputStream fs = new FileOutputStream(path);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(t);
            os.flush();
            os.close();
        } catch (Exception ex) {
            LoggerUtil.e("save出现异常--path：" + path);
//            ToastUtil.showShort("save出现异常--path："+path);
            ex.printStackTrace();
        }
    }

    public static Object read(String path) { //反序列化给定的类
        try {
            FileInputStream fs = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fs);
            Object object = ois.readObject();
            ois.close();
            return object;
        } catch (Exception ex) {
            LoggerUtil.e("read出现异常--path：" + path);
//            ToastUtil.showShort("read出现异常--path："+path);
            ex.printStackTrace();
            return null;
        }
    }

    public static boolean saveObject(Serializable ser, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = DefaultApplication.getInstance().openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
            }
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    public static Serializable readObject(String file) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = DefaultApplication.getInstance().openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
            }
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return null;
    }


    public static void deleteFile(String path) {
        //退出登录删除序列化bean
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
}
