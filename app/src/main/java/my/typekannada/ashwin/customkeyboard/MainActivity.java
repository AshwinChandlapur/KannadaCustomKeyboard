package my.typekannada.ashwin.customkeyboard;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private InterstitialAd interstitial;
    final String PREFS_NAME = "MyPrefsFile";
    CardView promo;
    TextView rView,rView1;
    CardView card1;
    ImageView playButton;
    private RewardedVideoAd mRewardedVideoAd;

    private static final String AD_CARD = "ad_card";
    private static final String NEWS_PROMO_CARD="news_promo_card";
//    FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setDeveloperModeEnabled(BuildConfig.DEBUG)
//                .build();
//        mFirebaseRemoteConfig.setConfigSettings(configSettings);
//        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
//
//        long cacheExpiration = 24 * 60 * 60; // 1 Day
//
//        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
//            cacheExpiration = 0;
//        }
//
//        mFirebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if(mFirebaseRemoteConfig.getBoolean(AD_CARD)){
//                        card1.setVisibility(View.VISIBLE);
//                }else{
//                    card1.setVisibility(View.GONE);
//                }
//
//
//                if(mFirebaseRemoteConfig.getBoolean(NEWS_PROMO_CARD)){
//                    promo.setVisibility(View.VISIBLE);
//                }else{
//                    promo.setVisibility(View.GONE);
//                }
//
//
//            }
//        });


        card1 = (CardView)findViewById(R.id.card1);
        rView = (TextView)findViewById(R.id.rView);
        rView1 = (TextView)findViewById(R.id.rView1);


        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        loadRewardedVideoAd();
        playButton = (ImageView) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }else{
                    displayInterstitial();
                }
            }
        });


        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1924436259631090/6298664761");

        //Interstitial Ad Space
        AdRequest adRequests = new AdRequest.Builder()
                .addTestDevice("91BCA0B98362AF53D5488A3F87FA1614")
                .build();
        interstitial = new InterstitialAd(MainActivity.this);
        interstitial.setAdUnitId(getString(R.string.home_interstitial_id));
        interstitial.loadAd(adRequests);
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {

                card1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayInterstitial();
                    }
                });
            }
        });
        // Interstetial ad Finished


        //Banner Ad Space

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("E1C583B224120C3BEF4A3DB0177A7A37")
                .build();
        mAdView.loadAd(adRequest);

        //Banner ad finished

        promo = (CardView) findViewById(R.id.promos);
        promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=vadeworks.news.duniya"));
                startActivity(i);
            }
        });



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //get the Document object from the site. Enter the link of site you want to fetch
                    Document document = Jsoup.connect("http://ashwinchandlapur.github.io/SVGName/").get(); // this is the website string
                    //Get the text we want
                    final String title = document.select("h2").text().toString();
                    final String title1= document.select("h3").text().toString();
                    Log.d("String title is", title);
                    //set the title of text view
                    //Run this on ui thread because another thread cannot touch the views of main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            card1.setVisibility(View.VISIBLE);
                            //set both the text views
                            rView.setText(title);
//                            rView.setMovementMethod(new ScrollingMovementMethod());
                            rView1.setText(title1);

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

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



    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getResources().getString(R.string.rewarded_home),
                new AdRequest.Builder().build());
    }
}
