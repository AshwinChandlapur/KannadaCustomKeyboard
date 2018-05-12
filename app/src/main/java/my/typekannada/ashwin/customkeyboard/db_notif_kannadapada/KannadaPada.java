package my.typekannada.ashwin.customkeyboard.db_notif_kannadapada;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import my.typekannada.ashwin.customkeyboard.NotifHandler;
import my.typekannada.ashwin.customkeyboard.NotifTray;
import my.typekannada.ashwin.customkeyboard.R;
import my.typekannada.ashwin.customkeyboard.db_notif_Facts.db_notif;


public class KannadaPada extends AppCompatActivity {

    int randindex=0;
    //initiate cursor and point to null
    Cursor c=null;
    Cursor csec=null;
    TextView rView;

    //to get db length
    int db_length;

    int n_row;
    int c_row;
    TextView message,request,show;
    private InterstitialAd interstitial;
    CardView promo,ad_card;
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kannada_pada);

        message = findViewById(R.id.message);
        request = findViewById(R.id.req);
        show = findViewById(R.id.show);
        promo =  findViewById(R.id.promos);
        ad_card = findViewById(R.id.ad_card);

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        loadRewardedVideoAd();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect("http://ashwinchandlapur.github.io/SVGName/").get(); // this is the website string
                    final String req = document.select("h2").text().toString();
                    final String sho= document.select("h3").text().toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            request.setText(req);
                            show.setText(sho);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();



        AdRequest adRequests = new AdRequest.Builder()
                .addTestDevice("E1C583B224120C3BEF4A3DB0177A7A37")
                .build();
        interstitial = new InterstitialAd(KannadaPada.this);

        interstitial.setAdUnitId(getString(R.string.notif_interstitial_id));
        interstitial.loadAd(adRequests);




        promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=vadeworks.news.duniya"));
                startActivity(i);
            }
        });

        ad_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double random = Math.random();
                if(random>0.5){
                    if (mRewardedVideoAd.isLoaded()) {
                        mRewardedVideoAd.show();
                    }else if(interstitial.isLoaded()){
                        interstitial.show();
                    }
                }else{
                    if (interstitial.isLoaded()) {
                        interstitial.show();
                    }else if(mRewardedVideoAd.isLoaded()){
                        mRewardedVideoAd.show();

                    }
                }
            }
        });




        Button feedback=(Button)findViewById(R.id.feedback) ;
        final MaterialStyledDialog dialogHeader_1 = new MaterialStyledDialog(this)
                .setIcon(R.mipmap.ic_launcher)
                .withDialogAnimation(true)
                .setTitle("Awesome!")
                .setDescription("Glad to see you liked Type Kannada! Your 5 Star Rating will help us Serve Better.")
                .setHeaderColor(R.color.orangeText)
                .setPositive("Give Us a Five", new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        final String appPackageName = getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                })
                .setNegative("Suggestions", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "ashwinchandlapur@gmail.com"));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Type Kannada Feedback");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .build();


        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHeader_1.show();
            }
        });




        DatabaseHelper_Pada myDbHelper = new DatabaseHelper_Pada(KannadaPada.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        //put cursor in allfacts table of facts db
        c = myDbHelper.query("allfacts", null, null, null, null, null, null);
        csec = myDbHelper.query("allfacts", null, null, null, null, null, null);

        //1st row of allfacts table
//        c.moveToFirst();

        //gettotal no of rows in table
        db_length=c.getCount();

        c_row=randnum();
        n_row=randnum();
        next();
    }



    public  int randnum()
    {
        Random r = new Random();
        return r.nextInt(db_length+1);
    }




    private void next() {
        c.moveToPosition(c_row);
        csec.moveToPosition(n_row);
        String factString = c.getString(2);
        String imgString = c.getString(3);
        Log.i("Message",factString);
        message.setText(factString);

//        ivinst=(ImageView)findViewById(R.id.hideme);
        ImageView imgView=(ImageView)findViewById(R.id.imgView);


        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;

            Picasso.with(this.getApplicationContext())
                    .load(R.drawable.wordaday)
                    .placeholder(R.drawable.wordaday)
                    .into(imgView);

            Log.i("ImageURL is",imgString);
        }
        else{
            connected = false;
            Picasso.with(this.getApplicationContext())
                    .load(R.drawable.wordaday)
                    .placeholder(R.drawable.wordaday)
                    .into(imgView);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        return super.onKeyDown(keyCode, event);
    }

    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getResources().getString(R.string.rewarded_home),
                new AdRequest.Builder().build());
    }

}



