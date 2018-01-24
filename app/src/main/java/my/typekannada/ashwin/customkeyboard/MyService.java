package my.typekannada.ashwin.customkeyboard;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intents, int flags, int startId) {

        ///AlarmManager for Notification of Facts
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,22);
        calendar.set(Calendar.MINUTE,05);
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
        calendar1.set(Calendar.HOUR_OF_DAY,22);
        calendar1.set(Calendar.MINUTE,11);
        calendar1.set(Calendar.SECOND,00 );
        if(calendar1.before(Calendar.getInstance())) {
            calendar1.add(Calendar.DATE, 1);
        }
        Intent intent1 = new Intent((getApplicationContext()),Notification_receiver_pada.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(),200,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent1);
        ///Ends Here

        //TODO do something useful
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
