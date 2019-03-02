package workstation.zjyk.com.scanapp.util;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

/**
 * 震动
 * Created by zhangxiaoping on 2019/3/2 15:31
 */
public class VirateUtil {
    //震动milliseconds毫秒
    public static void vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        if (vib.hasVibrator()) {
            vib.vibrate(milliseconds);
        }
    }

    //以pattern[]方式震动
    public static void vibrate(final Activity activity, long[] pattern, int repeat) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        if (vib.hasVibrator()) {
            vib.vibrate(pattern, repeat);
        }
    }

    //取消震动
    public static void virateCancle(final Activity activity) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
    }

}
