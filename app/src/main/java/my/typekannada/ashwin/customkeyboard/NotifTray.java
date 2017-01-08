package my.typekannada.ashwin.customkeyboard;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotifTray extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_tray);
        Typeface myFont = Typeface.createFromAsset(this.getAssets(),"fonts/Kaushan.otf");
        TextView heading=(TextView)findViewById(R.id.textss);
        heading.setTypeface(myFont);
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(NotifTray.this,MainActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
