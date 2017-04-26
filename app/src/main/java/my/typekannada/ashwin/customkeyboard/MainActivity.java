package my.typekannada.ashwin.customkeyboard;

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


public class MainActivity extends ActionBarActivity {
    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;
    private FirebaseAnalytics mFirebaseAnalytics;
    final String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        //Pushbots.sharedInstance().setCustomHandler(CustomHandler.class);
       // Pushbots.sharedInstance().init(this);
       // Pushbots.sharedInstance().debug(true);

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
   /* @Override
    public void onBackPressed() {
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
    }*/


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

       // Intent k = new Intent(MainActivity.this,NotifHandler.class);
         //startActivity(k);
        Intent j = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
        j.setClassName("com.android.settings", "com.android.settings.LanguageSettings");
        startActivity(j);

    }

   /* private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            String bigText;
            // bigText = data.optString("bigText", null);
            String imgUrl;
            //imgUrl = data.optString("imgUrl",null);

            if (data != null) {
                bigText = data.optString("bigText", null);
                imgUrl = data.optString("imgUrl",null);
                if(bigText!=null && imgUrl!=null) {
                    Intent intent = new Intent(getApplicationContext(), oneSignal.class);
                    intent.putExtra("bigText", bigText);
                    intent.putExtra("imgUrl", imgUrl);
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

            if (actionType == OSNotificationAction.ActionType.ActionTaken)
                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);

            // The following can be used to open an Activity of your choice.



            // Add the following to your AndroidManifest.xml to prevent the launching of your main Activity
            //  if you are calling startActivity above.
         /*
            <application ...>
              <meta-data android:name="com.onesignal.NotificationOpened.DEFAULT" android:value="DISABLE" />
            </application>

        }
    }*/


}
