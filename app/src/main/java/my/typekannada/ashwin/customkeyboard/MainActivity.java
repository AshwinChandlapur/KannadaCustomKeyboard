package my.typekannada.ashwin.customkeyboard;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {
    private InterstitialAd interstitial;
    private FirebaseAnalytics mFirebaseAnalytics;
    final String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ///AlarmManager for Notification of Facts
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,30);
        calendar.set(Calendar.SECOND,00 );
        Intent intent = new Intent((getApplicationContext()),Notification_receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
        ///Ends Here

        ///AlarmManager for Notification of KannadaPada
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY,23);
        calendar1.set(Calendar.MINUTE,35);
        calendar1.set(Calendar.SECOND,00 );
        Intent intent1 = new Intent((getApplicationContext()),Notification_receiver.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(),0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent1);
        ///Ends Here





// Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putInt("ButtonId",R.id.KanKey);
        mFirebaseAnalytics.logEvent("Select_Keyboard",bundle);
        //OneSignal.startInit(this)
         //       .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
         //       .init();

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1924436259631090/6298664761");

        //Interstitial Ad Space
        AdRequest adRequests = new AdRequest.Builder()
                .addTestDevice("E1C583B224120C3BEF4A3DB0177A7A37")
                .build();
        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(MainActivity.this);
// Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.home_interstitial_id));
        interstitial.loadAd(adRequests);
// Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
// Call displayInterstitial() function
                displayInterstitial();
            }
        });
        // Interstetial ad Finished


        //Banner Ad Space

        AdView mAdView = (AdView) findViewById(R.id.adView);
       // mAdView.setAdSize(AdSize.SMART_BANNER);
        AdRequest adRequest = new AdRequest.Builder()
               .addTestDevice("E1C583B224120C3BEF4A3DB0177A7A37")
                .build();
        mAdView.loadAd(adRequest);

        //Banner ad finished


    }
    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
          //  Intent k = new Intent(MainActivity.this,deeplink.class);
         //   startActivity(k);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//Code For First TIme Opening The App.
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    if (settings.getBoolean("my_first_time", true)) {
                        //the app is being launched for first time, do something
                        //Notification Tray as Soon as User Installs
                        ///Test Notification Code
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
                        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                        mBuilder.setContentTitle("Mysore Sandal Soap ");
                        mBuilder.setContentText("Click here to Read more!");
                        mBuilder.setAutoCancel(true);
                        mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                        Intent resultIntent = new Intent(getApplicationContext(), NotifTray.class);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                        stackBuilder.addParentStack(NotifHandler.class);


                        // Adds the Intent that starts the Activity to the top of the stack
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        mBuilder.setContentIntent(resultPendingIntent);
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        // notificationID allows you to update the notification later on.
                        mNotificationManager.notify(0, mBuilder.build());
                        ///Test Notification code ends here
                        //Notification Tray Code Ends Here
                        // record the fact that the app has been started at least once
                        settings.edit().putBoolean("my_first_time", false).commit();
                    }
                    //Code for First TIme opening the App Ends Here
                }
            }, 3000);



            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        return super.onKeyDown(keyCode, event);
    }


    public void langset(View view) {

        Intent j = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
        j.setClassName("com.android.settings", "com.android.settings.LanguageSettings");
        startActivity(j);

    }


}
