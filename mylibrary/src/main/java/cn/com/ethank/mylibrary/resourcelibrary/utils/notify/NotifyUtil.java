package cn.com.ethank.mylibrary.resourcelibrary.utils.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by ping on 2017/6/20.
 */

public class NotifyUtil {
    protected static int notifyID = 05257500124; // start notification id  0525 保证唯一性  052575
    Context appContext;

    private NotifyUtil() {

    }

    public static NotifyUtil getInstance() {

        return Holder.notifyUtil;
    }

    public static class Holder {
        private static final NotifyUtil notifyUtil = new NotifyUtil();
    }

    public NotifyUtil setContext(Context context) {
        this.appContext = context;
        return Holder.notifyUtil;
    }

    public void sendMessage(NotifyBean notifyBean) {
        NotificationManager notificationManager = (NotificationManager) appContext.getSystemService(Context.NOTIFICATION_SERVICE);
// create and send notificaiton
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(appContext)
                .setSmallIcon(appContext.getApplicationInfo().icon)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL);

//        Intent lauchIntent = getLauchIntent(messageGeTui);
//        PendingIntent pendingIntent = PendingIntent.getActivity(appContext, notifyID, null, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentTitle(notifyBean.getTitle());
//        mBuilder.setTicker(messageGeTui.getContent());
        mBuilder.setContentText(notifyBean.getMessage());
//        mBuilder.setContentIntent(pendingIntent);
        // mBuilder.setNumber(notificationNum);
        Notification notification = mBuilder.build();

        notificationManager.notify(notifyID, notification);
    }

//    private Intent getLauchIntent(MessageGeTui messageGeTui) {
//        String business = messageGeTui.getPayload().getBusiness();
//        Intent intent = null;
//        switch (business) {
//            case "email":
//                //邮件
//                if (checkAppAlive()) {
//                    intent = GeTuiIntent.setEmailIntent(appContext, messageGeTui);
//                } else {
//                    //如果不存活的话启动页面
//                    intent = GeTuiIntent.setStartupIntent(appContext, messageGeTui);
//                }
//                break;
//            case "workflow":
//                //流程
//                if (checkAppAlive()) {
//                    intent = GeTuiIntent.setApprovalIntent(appContext, messageGeTui);
//                } else {
//                    //如果不存活的话启动页面
//                    intent = GeTuiIntent.setStartupIntent(appContext, messageGeTui);
//                }
//
//                break;
//            case "doc":
//                //新闻通知
//                if (checkAppAlive()) {
//                    intent = GeTuiIntent.setNewDetailIntent(appContext, messageGeTui);
//                } else {
//                    //如果不存活的话启动页面
//                    intent = GeTuiIntent.setStartupIntent(appContext, messageGeTui);
//                }
//                break;
//        }
//
//        return intent;
//    }

}
