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
import com.squareup.picasso.Picasso;

public class oneSignal extends AppCompatActivity {
    private InterstitialAd interstitial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_signal);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //interstial ad space
                AdRequest adRequests = new AdRequest.Builder().build();
                // Prepare the Interstitial Ad
                interstitial = new InterstitialAd(oneSignal.this);
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
        }, 4000);
        Typeface myFont = Typeface.createFromAsset(this.getAssets(), "fonts/quicksand.otf");

        Button feedback=(Button)findViewById(R.id.feedback) ;
        ImageView imageView =(ImageView)findViewById(R.id.imgView);
        TextView textView = (TextView)findViewById(R.id.message);
        textView.setTypeface(myFont);
        Intent intent = getIntent();
        String bigText = intent.getExtras().getString("bigText");
        textView.setText(bigText);

        String imgUrl = intent.getExtras().getString("imgUrl");
        Picasso.with(this).load(imgUrl).into(imageView);


        String targetUrl = intent.getExtras().getString("targetUrl");
        if(targetUrl!=null)
        {
            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.setData(Uri.parse(targetUrl));
            startActivity(i);
        }

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
    }

    void displayInterstitial(){
        if (interstitial.isLoaded()  ) {
            interstitial.show();
        }
    }
}