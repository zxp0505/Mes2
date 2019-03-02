package workstation.zjyk.line.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import java.lang.ref.WeakReference;
import java.net.Socket;

/**
 * Created by zjgz on 2017/11/8.
 */

public class BackgroundService extends Service {
    private static final String TAG = "danxx";
    /**
     * 心跳频率
     */
    private static final long HEART_BEAT_RATE = 3 * 1000;
    /**
     * 服务器ip地址
     */
    public static final String HOST = "192.168.123.27";// "192.168.1.21";//
    /**
     * 服务器端口号
     */
    public static final int PORT = 9800;
    /**
     * 服务器消息回复广播
     */
    public static final String MESSAGE_ACTION = "message_ACTION";
    /**
     * 服务器心跳回复广播
     */
    public static final String HEART_BEAT_ACTION = "heart_beat_ACTION";

    private LocalBroadcastManager mLocalBroadcastManager;

    // For heart Beat
    private Handler mHandler = new Handler();
    /**
     * 心跳任务，不断重复调用自己
     */
    private Runnable heartBeatRunnable = new Runnable() {

        @Override
        public void run() {
            if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
                //TODO  发送心跳
            }
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    private long sendTime = 0L;

    /**
     * aidl通讯回调
     */
//    private IBackService.Stub iBackService = new IBackService.Stub() {
//
//        /**
//         * 收到内容发送消息
//         * @param message 需要发送到服务器的消息
//         * @return
//         * @throws RemoteException
//         */
//        @Override
//        public boolean sendMessage(String message) throws RemoteException {
//            return sendMsg(message);
//        }
//    };
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new InitSocketThread().start();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

    }

    public void sendMsg(final String msg) {
        sendTime = System.currentTimeMillis();//每次发送成数据，就改一下最后成功发送的时间，节省心跳间隔时间
    }

    private void initSocket() {//初始化Socket
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//初始化成功后，就准备发送心跳包
    }

    /**
     * 心跳机制判断出socket已经断开后，就销毁连接方便重新创建连接
     *
     * @param mSocket
     */
    private void releaseLastSocket(WeakReference<Socket> mSocket) {
    }

    class InitSocketThread extends Thread {
        @Override
        public void run() {
            super.run();
            initSocket();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(heartBeatRunnable);
        mHandler.removeCallbacksAndMessages(null);
    }
}
