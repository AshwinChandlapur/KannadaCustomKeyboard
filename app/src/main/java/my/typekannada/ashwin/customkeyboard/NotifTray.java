package my.typekannada.ashwin.customkeyboard;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.ads.InterstitialAd;

public class NotifTray extends AppCompatActivity {
    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;
    String s,s1,s3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_tray);
        Typeface myFont = Typeface.createFromAsset(this.getAssets(),"fonts/quicksand.otf");
        TextView heading=(TextView)findViewById(R.id.textss);
        heading.setTypeface(myFont);
        Button feedback=(Button)findViewById(R.id.feedback) ;

        /*YoYo.with(Techniques.Landing)
                .duration(2000)
                .repeat(1)
                .playOn(findViewById(R.id.imageView));*/



//Download Images
 /*       ImageView mimageView = (ImageView) findViewById(R.id.imageView);
        BitmapDrawable drawable = (BitmapDrawable) mimageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        File sdCardDirectory = new File("/sdcard/TypeKannada/");
        sdCardDirectory.mkdirs();
        s="sandal";
        s1=".jpg";
        s3=s+s1;
        //File sdCardDirectory = Environment.getExternalStorageDirectory();

        File image = new File(sdCardDirectory, s3);
        MediaScannerConnection.scanFile(this, new String[] { image.getPath() }, new String[] { "image/jpeg" }, null);
        boolean success = false;

        // Encode the file as a PNG image.
        FileOutputStream outStream;
        try {

            outStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
         100 to keep full quality of the image

            outStream.flush();
            outStream.close();
            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }if (success) {
            Toast.makeText(getApplicationContext(), "Image saved with success",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Error during image saving", Toast.LENGTH_LONG).show();
        }
*/
        //Download Images Code  ends here
        /*NativeExpressAdView adView = (NativeExpressAdView)findViewById(R.id.adView);

        AdRequest request = new AdRequest.Builder()
                .addTestDevice("E1C583B224120C3BEF4A3DB0177A7A37")
                .build();
        adView.loadAd(request);*/



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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NotifTray.this, MainActivity.class);
        startActivity(intent);
        finish();
        //Intent intent = new Intent(Intent.ACTION_MAIN);
        //intent.addCategory(Intent.CATEGORY_HOME);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //startActivity(intent);
    }


}
