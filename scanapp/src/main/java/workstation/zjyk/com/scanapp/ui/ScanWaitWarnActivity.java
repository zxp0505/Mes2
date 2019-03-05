package workstation.zjyk.com.scanapp.ui;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.server.MessageEventBean;
import cn.com.ethank.mylibrary.resourcelibrary.server.MyServer;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.ui.present.ScanMainPresent;
import workstation.zjyk.com.scanapp.ui.present.ScanWaitWarnPresent;
import workstation.zjyk.com.scanapp.ui.views.ScanWaitWarnView;
import workstation.zjyk.com.scanapp.util.ScanURLBuilder;
import workstation.zjyk.com.scanapp.util.ScanUserManager;
import workstation.zjyk.com.scanapp.util.SoundPoolHelper;
import workstation.zjyk.com.scanapp.util.VirateUtil;

/**
 * Created by zhangxiaoping on 2019/3/2 15:07
 */
public class ScanWaitWarnActivity extends ScanBaseActivity<ScanWaitWarnPresent> implements ScanWaitWarnView {
    @BindView(R.id.send_wran)
    Button sendWran;
    @BindView(R.id.bt_play)
    Button btPlay;
    private SoundPoolHelper soundPoolHelper;
    private MyServer myServer;

    @Override
    protected void creatPresent() {
        currentPresent = new ScanWaitWarnPresent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wait_warn;
    }

    @Override
    protected View getLoadingTargetView() {
        return mFlContent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        pullWarnInfo();
    }

    @Override
    public void initOnCreate() {
        super.initOnCreate();
        startServer();
    }

    private void startServer() {
        try {
            myServer = new MyServer();
            myServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.send_wran, R.id.bt_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_wran:
                notification(sendWran, "", "", "");
//                startVibrate();
                break;
            case R.id.bt_play:
//                cleanNotification(sendWran);
//                cancleVibrate();
                playMusic();
                break;
        }
    }


    private int id = 1;

    public void notification(View view, String title, String content, String h5url) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0 notify
            String CHANNEL_ID = "warn_id";
            String CHANNEL_NAME = "warn_name";

            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setBypassDnd(true);    //设置绕过免打扰模式
            channel.canBypassDnd();       //检测是否绕过免打扰模式
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);//设置在锁屏界面上显示这条通知
            channel.setDescription("description of this notification");
            channel.setLightColor(Color.GREEN);
            channel.setName("name of this notification");
            channel.setShowBadge(true);
//            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            channel.enableVibration(true);

            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            Intent intent = new Intent(this, ScanH5Activity.class);
            intent.putExtra("url", h5url);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
            PendingIntent pendingResult = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setPriority(1000)
                    .setAutoCancel(true)
//                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setNumber(3)
                    .setDefaults(Notification.DEFAULT_LIGHTS)
                    .setContentIntent(pendingResult)
                    .setOngoing(true);
            notificationManager.notify(id++, mBuilder.build());
        } else {
            //普通
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.logo);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            //设置小图标
            mBuilder.setSmallIcon(R.drawable.logo);
            //设置大图标
            mBuilder.setLargeIcon(bitmap);
            //设置标题
            mBuilder.setContentTitle(title);
            //设置通知正文
            mBuilder.setContentText(content);
            //设置摘要
//            mBuilder.setSubText("这是摘要");
            //设置是否点击消息后自动clean
            mBuilder.setAutoCancel(true);
            //显示指定文本
//            mBuilder.setContentInfo("Info");
            //与setContentInfo类似，但如果设置了setContentInfo则无效果
            //用于当显示了多个相同ID的Notification时，显示消息总数
            mBuilder.setNumber(2);
            //通知在状态栏显示时的文本
//            mBuilder.setTicker("在状态栏上显示的文本");
            //设置优先级
            mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
            //自定义消息时间，以毫秒为单位，当前设置为比系统时间少一小时
            mBuilder.setWhen(System.currentTimeMillis() - 3600000);
            //设置为一个正在进行的通知，此时用户无法清除通知
            mBuilder.setOngoing(true);
            //设置消息的提醒方式，震动提醒：DEFAULT_VIBRATE     声音提醒：NotificationCompat.DEFAULT_SOUND
            //三色灯提醒NotificationCompat.DEFAULT_LIGHTS     以上三种方式一起：DEFAULT_ALL
            mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
            //设置震动方式，延迟零秒，震动一秒，延迟一秒、震动一秒
//        mBuilder.setVibrate(new long[]{0, 1000, 1000, 1000});

            Intent intent = new Intent(this, ScanH5Activity.class);
            intent.putExtra("url", h5url);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
            mBuilder.setContentIntent(pIntent);

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(id++, mBuilder.build());
        }

    }


    public void cleanNotification(View view) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
        mNotificationManager.cancel(1);
    }

    private void startVibrate() {
        VirateUtil.vibrate(this, new long[]{1000, 1000, 1000, 1000}, 2);
    }

    private void cancleVibrate() {
        VirateUtil.virateCancle(this);
    }

    private void playMusic() {
        //加载默认音频，因为上面指定了，所以其默认是：RING_TYPE_MUSIC
//        soundPoolHelper = new SoundPoolHelper(4, SoundPoolHelper.TYPE_MUSIC)
//                .setRingtoneType(SoundPoolHelper.RING_TYPE_MUSIC)
//                //加载默认音频，因为上面指定了，所以其默认是：RING_TYPE_MUSIC
//                //happy1,happy2
//                .loadDefault(this)
//                .load(this, "a", R.raw.a);
//        soundPoolHelper.play("a", true);
        SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        soundPool.load(this, R.raw.a, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                soundPool.play(1,  //声音id
                        1, //左声道
                        1, //右声道
                        1, //优先级
                        -1, // 0表示不循环，-1表示循环播放
                        1);//播放比率，0.5~2，一般为1
            }
        });


    }

    @Override
    protected void onDestroy() {
        if (soundPoolHelper != null) {
            soundPoolHelper.release();
        }
        if (myServer != null) {
            myServer.stop();
        }
        super.onDestroy();
    }

    private void pullWarnInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("personId", ScanUserManager.getInstance().getWarnPersonId());
        params.put("status", "NO");
        params.put("messageType", "ALARM");
        currentPresent.pullWarnInfo(params, true);
    }

    @Override
    public void showWarnInfo(boolean result) {
        if (result) {
            StartIntentUtils.startIntentUtils(this, ScanH5Activity.class, "url", ScanURLBuilder.getHostUrl()+"/" + ScanURLBuilder.H5_URL);
        }
    }
}
