package my.typekannada.ashwin.customkeyboard.db_notif_Facts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Random;

import my.typekannada.ashwin.customkeyboard.MainActivity;
import my.typekannada.ashwin.customkeyboard.R;

public class db_notif extends AppCompatActivity {


    int randindex=0;
    //initiate cursor and point to null
    Cursor c=null;
    Cursor csec=null;
    private InterstitialAd interstitial;


    TextView rView;

    //to get db length
    int db_length;

    int n_row;
    int c_row;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_notif);

        android.support.v7.app.ActionBar AB = getSupportActionBar();
        AB.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Interstitial Ad Space
        AdRequest adRequests = new AdRequest.Builder()
                .addTestDevice("E1C583B224120C3BEF4A3DB0177A7A37")
                .build();
        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(db_notif.this);
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

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    //get the Document object from the site. Enter the link of site you want to fetch
                    Document document = Jsoup.connect("http://ashwinchandlapur.github.io/SVGName/").get(); // this is the website string
                    //Get the text we want
                    final String title = document.select("h2").text().toString();
                    Log.d("String title is", title);
                    //set the title of text view
                    //Run this on ui thread because another thread cannot touch the views of main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //set both the text views
                            rView = (TextView)findViewById(R.id.rView);
                            rView.setText(title);
                            rView.setMovementMethod(new ScrollingMovementMethod());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();



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




//        ImageView imageView =(ImageView)findViewById(R.id.imgView);
        message = (TextView)findViewById(R.id.message);
//        message.setTypeface(myFont);

        //create a DBhelper instance to get cursor
        DatabaseHelper myDbHelper = new DatabaseHelper(db_notif.this);
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
                    .load(imgString)
                    .placeholder(R.color.colorPrimaryDark)
                    .into(imgView);

            Log.i("ImageURL is",imgString);
        }
        else{
            connected = false;
            Picasso.with(this.getApplicationContext())
                    .load(imgString)
                    .placeholder(R.color.colorPrimaryDark)
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



}
