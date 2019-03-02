package cn.com.ethank.mylibrary.resourcelibrary.common_util.log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.ConfigDefine;


/**
 * Created by ping on 2017/2/15.
 */

public class LoggerUtil {
    private LoggerUtil() {
    }

    public static void init(String tag) {
        if (ConfigDefine.isShowLog) {
//            Logger.init(tag);
            Logger.addLogAdapter(new AndroidLogAdapter());
        }
    }

    public static void i(String message) {
        if (ConfigDefine.isShowLog) {
            Logger.i(message);
        }
    }

    public static void e(String message) {
        if (ConfigDefine.isShowLog) {
            Logger.e(message);
        }
    }

    public static void e_exception(Exception exception, String message) {
        if (ConfigDefine.isShowLog) {
            Logger.e(exception, message);
        }
    }

    public static void json(String json) {
        if (ConfigDefine.isShowLog) {
            Logger.json(json);
        }
    }

    public static void i_tag(String tag, String message) {
        if (ConfigDefine.isShowLog) {
            Logger.t(tag).i(message);
        }
    }

    public static void d(Object object) {
        if (ConfigDefine.isShowLog) {
            Logger.d(object);
        }
    }
}
