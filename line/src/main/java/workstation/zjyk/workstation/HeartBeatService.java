package workstation.zjyk.workstation;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import workstation.zjyk.line.ui.present.MainPresent;
//import workstation.zjyk.workstation.ui.present.WSMainPresent;

/**
 * Created by zjgz on 2018/1/30.
 */

public class HeartBeatService extends Service {
    HeartBean heartBean;
    private static final int SEND_HEART = 1;//发送心跳
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SEND_HEART:
                    startHeart();
                    break;
            }
        }
    };
    private MainPresent mainPresent;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e(TAG, "onRebind");
        super.onRebind(intent);
    }

    private Binder binder = new IHeart.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void updateHeartBean(HeartBean bean) throws RemoteException {
            heartBean = bean;
        }

    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
        mainPresent = new MainPresent(null);
        startHeart();
    }

    private static final String TAG = "HeartBeatService";

    /**
     * 发送心跳
     */
    private void startHeart() {
        if (heartBean != null) {
            Map<String, String> params = new HashMap<>();
            params.put("personsId", heartBean.getPersonsId());
            params.put("clientIP", heartBean.getClientIP());
            params.put("personId", heartBean.getPersonId());
            params.put("wId", heartBean.getWorkStationId());
            LoggerUtil.d(params);
            mainPresent.requestHeart(params);
        }
        handler.sendEmptyMessageDelayed(SEND_HEART, 30 * 1000);
    }
}
