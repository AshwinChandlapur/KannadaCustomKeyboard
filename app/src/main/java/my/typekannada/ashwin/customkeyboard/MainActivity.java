package my.typekannada.ashwin.customkeyboard;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.pushbots.push.Pushbots;


import my.typekannada.ashwin.customkeyboard.R;




public class MainActivity extends ActionBarActivity {
    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Interstitial Ad Space
        AdRequest adRequests = new AdRequest.Builder().build();

        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(MainActivity.this);
// Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));
        interstitial.loadAd(adRequests);
// Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
// Call displayInterstitial() function
                displayInterstitial();
            }
        });
        // Interstetial ad Finished

        Pushbots.sharedInstance().init(this);





        //Banner Ad Space

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
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
           // Intent k = new Intent(MainActivity.this,developer.class);
            //startActivity(k);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void langset(View view) {
        Intent j = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
        j.setClassName("com.android.settings", "com.android.settings.LanguageSettings");
        startActivity(j);

    }


}
