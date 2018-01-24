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

import my.typekannada.ashwin.customkeyboard.db_notif_Facts.db_notif;
import my.typekannada.ashwin.customkeyboard.db_notif_kannadapada.KannadaPada;

/**
 * Created by ashwinchandlapur on 12/01/18.
 */

public class Notification_receiver_pada extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent1){


        if ("android.intent.action.BOOT_COMPLETED".equals(intent1.getAction())) {
            Intent i = new Intent(context, MyApplication.class);  //MyActivity can be anything which you want to start on bootup...
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }else
        {
            buildNotification(context);
        }
    }

    public void buildNotification(Context context){
        NotificationManager notificationManager1 = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);


        Bitmap icon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.wordaday);

        NotificationCompat.BigPictureStyle bigPicture = new NotificationCompat.BigPictureStyle();
        bigPicture.bigPicture(icon1);

        Intent mainActivity1 = new Intent((context),KannadaPada.class);
        mainActivity1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context,0,mainActivity1,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent1)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Type Kannada ")
                .setContentText("Kannada Pada")
                .setStyle(bigPicture)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        notificationManager1.notify(200,builder1.build());
    }
}
