package workstation.zjyk.workstation.util;

import android.util.Log;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 上报网络日志工具类
 */
public class WSUploadNetWorkUtil {
    private static final String TAG = "WSUploadNetWorkUtil";

    private WSUploadNetWorkUtil() {
    }

    public static WSUploadNetWorkUtil getInstance() {
        return Holder.wsUploadNetWorkUtil;
    }

    private static class Holder {
        private static WSUploadNetWorkUtil wsUploadNetWorkUtil = new WSUploadNetWorkUtil();
    }

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 30, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

    public void uploadOverTimeRunnable(UpLoadNetBean upLoadNetBean) {
        threadPoolExecutor.execute(new UploadNetRunnable(upLoadNetBean));
    }

    public class UploadNetRunnable implements Runnable {
        UpLoadNetBean bean;

        public UploadNetRunnable(UpLoadNetBean bean) {
            this.bean = bean;
        }

        @Override
        public void run() {
            Log.e(TAG, bean.toString());
        }
    }

    public static class UpLoadNetBean {
        private String url;//请求地址
        private String overTime;//超时时间

        public String getUrl() {
            return url == null ? "" : url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getOverTime() {
            return overTime == null ? "" : overTime;
        }

        public void setOverTime(String overTime) {
            this.overTime = overTime;
        }

        @Override
        public String toString() {
            return "当前的请求地址是:" + this.url + "---所花费的时间是：" + overTime;
        }
    }

}
