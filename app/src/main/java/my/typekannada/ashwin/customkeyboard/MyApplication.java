package my.typekannada.ashwin.customkeyboard;

import android.app.AlarmManager;
import android.app.Application;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.util.Calendar;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //OneSignal.startInit(this).init();
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .init();

        ///AlarmManager for Notification of Facts
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,11);
        calendar.set(Calendar.MINUTE,02);
        calendar.set(Calendar.SECOND,00 );
        if(calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        Intent intent = new Intent((getApplicationContext()),Notification_receiver_fact.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
        ///Ends Here

        ///AlarmManager for Notification of KannadaPada
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY,20);
        calendar1.set(Calendar.MINUTE,02);
        calendar1.set(Calendar.SECOND,00 );
        if(calendar1.before(Calendar.getInstance())) {
            calendar1.add(Calendar.DATE, 1);
        }
        Intent intent1 = new Intent((getApplicationContext()),Notification_receiver_pada.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(),200,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent1);
        ///Ends Here
    }


    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            String bigText;
            String imgUrl;
            String targetUrl;

            if (data != null) {
                bigText = data.optString("bigText", null);
                imgUrl = data.optString("imgUrl",null);
                targetUrl = data.optString("targetUrl",null);
                Log.i(targetUrl,"TargetUrl");

                // if(!(bigText!=null && imgUrl!=null) && targetUrl!=null)
                if(bigText==null && imgUrl==null)
                {
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse(targetUrl));
                    i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }else {
                    Intent intent = new Intent(getApplicationContext(), oneSignal.class);
                    Log.i("OneSignal","Executed");
                    intent.putExtra("bigText", bigText);
                    intent.putExtra("imgUrl", imgUrl);
                    intent.putExtra("targetUrl",targetUrl);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }


                if (bigText != null)
                    Log.i("OneSignalExample", "customkey set with value: " + bigText);
                if (imgUrl != null)
                    Log.i("OneSignalExample", "customkey set with value: " + imgUrl);
                data.remove(bigText);//This is mandatory, because the Old JSON data will still be stored that causes error while opening newest notification
                data.remove(imgUrl);//
            }

        }
    }
}