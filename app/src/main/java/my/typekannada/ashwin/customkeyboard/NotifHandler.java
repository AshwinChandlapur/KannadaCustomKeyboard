package my.typekannada.ashwin.customkeyboard;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.ads.NativeExpressAdView;
import com.squareup.picasso.Picasso;


public class NotifHandler extends AppCompatActivity {


    private InterstitialAd interstitial;
    ImageView imgView;
    String sr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_general);


        Typeface myFont = Typeface.createFromAsset(this.getAssets(), "fonts/quicksand.otf");
        TextView heading = (TextView) findViewById(R.id.heading);
        heading.setTypeface(myFont);
        Button feedback = (Button) findViewById(R.id.feedback);

        NativeExpressAdView adView = (NativeExpressAdView)findViewById(R.id.adView);

        AdRequest request = new AdRequest.Builder()
                .addTestDevice("E1C583B224120C3BEF4A3DB0177A7A37")
                .build();
        adView.loadAd(request);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //interstial ad space
                AdRequest adRequests = new AdRequest.Builder().build();
                // Prepare the Interstitial Ad
                interstitial = new InterstitialAd(NotifHandler.this);
// Insert the Ad Unit ID
                interstitial.setAdUnitId(getString(R.string.notif_interstitial_id));
                interstitial.loadAd(adRequests);
// Prepare an Interstitial Ad Listener
                interstitial.setAdListener(new AdListener() {
                    public void onAdLoaded() {
// Call displayInterstitial() function
                        displayInterstitial();
                    }
                });
//interstital finished
                //Do something after 100ms
            }
        }, 7000);

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
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .build();


        Bundle extras = getIntent().getExtras();

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHeader_1.show();
            }
        });


        if (null != extras && getIntent().getExtras().containsKey("message") || getIntent().getExtras().containsKey("imgUrl")
                || getIntent().getExtras().containsKey("bigText")
                ) {
            TextView message = (TextView) findViewById(R.id.message);
            message.setTypeface(myFont);
            message.setText(extras.getString("bigText"));
            imgView = (ImageView) findViewById(R.id.imgView);
            sr = extras.getString("imgUrl");
            Picasso.with(this).load(sr).into(imgView);
            //Picasso.with(this).load(String.valueOf(sr)).into(imgView);}
        }
    }




   void displayInterstitial(){
        if (interstitial.isLoaded()  ) {
            interstitial.show();
        }
    }
}