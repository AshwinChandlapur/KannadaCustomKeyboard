package my.typekannada.ashwin.customkeyboard;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import my.typekannada.ashwin.customkeyboard.db_notif_Facts.db_notif;
import my.typekannada.ashwin.customkeyboard.db_notif_kannadapada.KannadaPada;

/**
 * Created by ashwinchandlapur on 12/01/18.
 */

public class Notification_receiver_fact extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){


//        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
////            buildNotif(context);
//            Intent i = new Intent(context, MyApplication.class);  //MyActivity can be anything which you want to start on bootup...
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
//        }else{
//            buildNotif(context);
//
//        }

        buildNotif(context);
    }

    public void buildNotif(Context context)
    {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mainActivity = new Intent((context),db_notif.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,mainActivity,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Type Kannada")
                .setContentText("Kannada Fact")
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        notificationManager.notify(100,builder.build());
    }
}



