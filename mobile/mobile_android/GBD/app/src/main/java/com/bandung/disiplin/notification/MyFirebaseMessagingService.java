package com.bandung.disiplin.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
//
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
import com.bandung.disiplin.activity.KukalDetail;
import com.simako.user.R;

/**
 * Created by yogi on 9/17/16.
 */
public class MyFirebaseMessagingService {//extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    int notif_id = 0;

    Context mContext;

    public static String NOTIF_TYPE = "type";
    public static String NOTIF_ID = "orderId";
    public static String NOTIF_TITLE = "title";
    public static String NOTIF_TEXT = "text";
    private Bitmap icon;
    private String tittle = "";
    private String message = "";
    private String image = "";
    private String date = "";
    private String lokasi;
    private String id_laporan="";

//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        // TODO: Handle FCM messages here.
//        // If the application is in the foreground handle both data and notification messages here.
//        // Also if you intend on generating your own notifications as a result of a received FCM
//        // message, here is where that should be initiated.
//        mContext = this;
//
////        Log.d(TAG, "From: " + remoteMessage.getFrom());
////        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
////        Log.d(TAG, "Notification Message Tittle: " + remoteMessage.getNotification().getTag());
//        tittle = remoteMessage.getData().get("judul_laporan");
//        message = remoteMessage.getData().get("kronologi");
//
//        image = remoteMessage.getData().get("foto");
//        lokasi = remoteMessage.getData().get("lokasi") + "," + remoteMessage.getData().get("kecamatan") + "," + remoteMessage.getData().get("kelurahan");
//        date = remoteMessage.getData().get("tanggal_laporan");
//        id_laporan = remoteMessage.getData().get("id_laporan");
//
//
//
//        sendNotification(message, tittle);
//    }
//
//
//    private void sendNotification(String data, String tittle) {
//        icon = BitmapFactory.decodeResource(mContext.getResources(),
//                R.mipmap.ic_launcher);
//
//        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Intent i = new Intent(mContext, KukalDetail.class);
//        i.putExtra("judul", tittle);
//        i.putExtra("kronologi", message);
//        i.putExtra("lokasi", lokasi);
//        i.putExtra("date", date);
//        i.putExtra("image", image);
//        i.putExtra("id_laporan", id_laporan);
//
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//
//        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext)
//                .setContentTitle(tittle)
//                .setAutoCancel(true)
//                .setSmallIcon(getNotificationIcon())
//                .setLargeIcon(icon)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(data))
//                .setContentText(data)
//                .setContentIntent(contentIntent)
//                .setVibrate(new long[]{1000})
//                .setLights(Color.CYAN, 3000, 3000)
//                .setSound(uri);
//
//        mNotificationManager.notify(notif_id, mBuilder.build());
//    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.ic_notification_user : R.mipmap.ic_launcher;
    }
}
