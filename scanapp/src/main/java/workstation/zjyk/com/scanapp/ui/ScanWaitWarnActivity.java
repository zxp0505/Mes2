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
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.server.MessageEventBean;
import cn.com.ethank.mylibrary.resourcelibrary.server.MyServer;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.ScanWarnInfo;
import workstation.zjyk.com.scanapp.ui.present.ScanWaitWarnPresent;
import workstation.zjyk.com.scanapp.ui.views.ScanWaitWarnView;
import workstation.zjyk.com.scanapp.ui.webview.IWebPageView;
import workstation.zjyk.com.scanapp.ui.webview.MyJavascriptInterface;
import workstation.zjyk.com.scanapp.ui.webview.MyWebChromeClient;
import workstation.zjyk.com.scanapp.ui.webview.MyWebViewClient;
import workstation.zjyk.com.scanapp.util.CheckNetwork;
import workstation.zjyk.com.scanapp.util.ScanURLBuilder;
import workstation.zjyk.com.scanapp.util.ScanUserManager;
import workstation.zjyk.com.scanapp.util.SoundPoolHelper;
import workstation.zjyk.com.scanapp.util.VirateUtil;

/**
 * Created by zhangxiaoping on 2019/3/2 15:07
 */
public class ScanWaitWarnActivity extends ScanBaseActivity<ScanWaitWarnPresent> implements ScanWaitWarnView, IWebPageView {
    private SoundPoolHelper soundPoolHelper;
    private MyServer myServer;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.bt_refresh)
    Button btRefresh;
    private String currentUrl;
    private SoundPool soundPool;

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
        mRlTitle.setVisibility(View.GONE);
        startServer();
        initWebView();
        Intent intent = getIntent();
        String url = intent.getStringExtra("pathurl");
        String loadUrl = ScanURLBuilder.getHostUrl() + "/" + ScanURLBuilder.H5_URL + "&personId=" + ScanUserManager.getInstance().getWarnUserName();
        if (intent != null && !TextUtils.isEmpty(url)) {
            loadUrl = url;
        }
        showLoadingDialog("正在加载中...");
        webView.loadUrl(loadUrl);

        notification("a", "b","http://192.168.4.27/server/yike/andong!info.action?alarmId=efab724a-8159-4424-bd6f-b8e93e361d83" );

    }

    private void startServer() {
        try {
            myServer = new MyServer();
            myServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onEvent(MessageEventBean messageEventBean) {
        super.onEvent(messageEventBean);
        if (messageEventBean != null) {
            String message = messageEventBean.getMessage();
            if (!TextUtils.isEmpty(message)) {
                ScanWarnInfo scanWarnInfo = JSONObject.parseObject(message, ScanWarnInfo.class);
                String url = scanWarnInfo.getUrl();
                if(!TextUtils.isEmpty(url)){
                    notification(scanWarnInfo.getTitle(), scanWarnInfo.getMsg(),url );
                    playMusic();
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra("url");
            if (!TextUtils.isEmpty(url)) {
                showLoadingDialog("正在加载中...");
                webView.loadUrl(url);
            }
        }

    }
    //    @OnClick({R.id.send_wran, R.id.bt_play})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.send_wran:
//                notification(sendWran, "", "", "");
////                startVibrate();
//                break;
//            case R.id.bt_play:
////                cleanNotification(sendWran);
////                cancleVibrate();
//                playMusic();
//                break;
//        }
//    }


    private int id = 1;
private int requestCode = 1;
    public void notification(String title, String content, String h5url) {
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
            Intent intent = new Intent(this, ScanWaitWarnActivity.class);
            intent.putExtra("pathurl", h5url);
//            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
            PendingIntent pendingResult = PendingIntent.getActivity(this, requestCode++, intent, PendingIntent.FLAG_UPDATE_CURRENT);

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

            Intent intent = new Intent(this, ScanWaitWarnActivity.class);
            intent.putExtra("pathurl", h5url);
            PendingIntent pIntent = PendingIntent.getActivity(this, requestCode++, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
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
        releaseSoundPool();
        //加载默认音频，因为上面指定了，所以其默认是：RING_TYPE_MUSIC
//        soundPoolHelper = new SoundPoolHelper(4, SoundPoolHelper.TYPE_MUSIC)
//                .setRingtoneType(SoundPoolHelper.RING_TYPE_MUSIC)
//                //加载默认音频，因为上面指定了，所以其默认是：RING_TYPE_MUSIC
//                //happy1,happy2
//                .loadDefault(this)
//                .load(this, "a", R.raw.a);
//        soundPoolHelper.play("a", true);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        soundPool.load(this, R.raw.alarm, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                soundPool.play(1,  //声音id
                        1, //左声道
                        1, //右声道
                        1, //优先级
                        0, // 0表示不循环，-1表示循环播放 1播放两次
                        1);//播放比率，0.5~2，一般为1
            }
        });


    }

    private void releaseSoundPool(){
        if(soundPool != null){
            soundPool.release();
        }
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
        params.put("personId", ScanUserManager.getInstance().getWarnUserName());
        params.put("status", "NO");
        params.put("messageType", "ALARM");
        currentPresent.pullWarnInfo(params, true);
    }

    @Override
    public void showWarnInfo(boolean result) {
        if (result) {
//            StartIntentUtils.startIntentUtils(this, ScanH5Activity.class, "url", ScanURLBuilder.getHostUrl()+"/" + ScanURLBuilder.H5_URL);
            playMusic();
        }
    }


    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView() {
        WebSettings ws = webView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 不缩放
        webView.setInitialScale(100);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否新窗口打开(加了后可能打不开网页)
//        ws.setSupportMultipleWindows(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);

        MyWebChromeClient mWebChromeClient = new MyWebChromeClient(this);
        webView.setWebChromeClient(mWebChromeClient);
        // 与js交互
        webView.addJavascriptInterface(new MyJavascriptInterface(this), "injectedObject");
        webView.setWebViewClient(new MyWebViewClient(this));
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                return handleLongImage();
                return false;
            }
        });

    }

    @Override
    public void hindProgressBar() {
        hideLoadingDialog();
    }

    @Override
    public void showWebView() {

    }

    @Override
    public void hindWebView() {

    }

    @Override
    public void startProgress(int newProgress) {

    }

    @Override
    public void addImageClickListener() {

    }

    @Override
    public void fullViewAddView(View view) {

    }

    @Override
    public void showVideoFullView() {

    }

    @Override
    public void hindVideoFullView() {

    }

    @Override
    public void onReceivedError(int errorCode, String description) {
        ToastUtil.showInfoCenterShort("" + description);
        btRefresh.setVisibility(View.VISIBLE);
        if (!CheckNetwork.isNetworkConnected(this)) {

        }
    }

    public ViewGroup getVideoFullView() {
        return null;
    }

    @OnClick(R.id.bt_refresh)
    public void onViewClicked() {
        webView.loadUrl(currentUrl);
        btRefresh.setVisibility(View.GONE);
    }
}
