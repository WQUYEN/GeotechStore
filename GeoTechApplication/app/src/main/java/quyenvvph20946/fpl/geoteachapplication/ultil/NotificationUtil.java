package quyenvvph20946.fpl.geoteachapplication.ultil;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import quyenvvph20946.fpl.geoteachapplication.R;

public class NotificationUtil {
    public static void sendNotification(Context context, String content) {
        Notification notification = new NotificationCompat.Builder(context,MyApplication.CHANNEL_ID)
                .setContentTitle("Thông báo mới")
                .setContentText(content)
                .setSmallIcon(R.drawable.avatar1)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null) {
            Calendar calendar = Calendar.getInstance();
            int currentTimeInMillis = (int) calendar.getTimeInMillis();
            notificationManager.notify(currentTimeInMillis, notification);
        }
    }
}
