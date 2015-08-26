package org.mugd.mugdapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.microsoft.windowsazure.notifications.NotificationsHandler;

/**
 * Created by akshit on 26-08-2015.
 */
public class PushNotificationHandler extends NotificationsHandler {

    private static final String TAG = "NotificationHandler";

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    Context ctx;

    @Override
    public void onRegistered(Context context,  final String gcmRegistrationId) {
        Log.v(TAG, "On Registered");
        super.onRegistered(context, gcmRegistrationId);

        new AsyncTask<Void, Void, Void>() {

            protected Void doInBackground(Void... params) {
                try {
                    MainActivity.mClient.getPush().register(gcmRegistrationId, null);
                    return null;
                }
                catch(Exception e) {
                    // handle error
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    @Override
    public void onReceive(Context context, Bundle bundle) {
        Log.v(TAG, "On Receive");
        ctx = context;

        String nhMessage = bundle.getString("message");
        String nhName = bundle.getString("name");

        //if(!MainActivity.isInForeground()) {
            sendNotification(nhMessage);
//        }else{
//            ChatArrayAdapter.addMessage(new ChatPublic(nhMessage,context,nhName));
//        }
    }

    private void sendNotification(String msg) {
        Log.v(TAG, "Send Notification");
        mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                new Intent(ctx, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("MugdApp")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setAutoCancel(true);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}
