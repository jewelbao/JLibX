package com.jewel.libx.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/4/24
 */
public class NotificationUtil {

    /**
     * 发送不重复的通知Notification
     *
     * @param cls     接收的活动
     * @param title   通知标题
     * @param icon    通知小图标
     * @param message 内容
     * @param extras  扩展数据
     * @return 返回新建的Notification ID
     */
    public static int sendNotification(Context context, Class<?> cls, String title, int icon, String message, Bundle extras) {
        Intent mIntent = new Intent(context, cls);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mIntent.putExtras(extras);

        // 关键点在这个requestCode，这里使用的是当前系统时间，巧妙的保证了每次都是一个新的Notification产生
        int requestCode = (int) System.currentTimeMillis();
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                requestCode, mIntent, 0);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(title).setSmallIcon(icon)
                .setContentIntent(contentIntent).setContentText(message)
                .build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults = Notification.DEFAULT_ALL;

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(requestCode, notification);
        return requestCode;
    }

    /**
     * 删除通知
     *
     * @param notificationId 通知id
     */
    public static void clearNotification(Context context, int notificationId) {
        // 启动后删除之前我们定义的通知
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);

    }
}
