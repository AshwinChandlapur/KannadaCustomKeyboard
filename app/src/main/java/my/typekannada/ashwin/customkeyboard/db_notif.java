package my.typekannada.ashwin.customkeyboard;

import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

public class db_notif extends AppCompatActivity {


    int randindex=0;
    //initiate cursor and point to null
    Cursor c=null;
    Cursor csec=null;

    //to get db length
    int db_length;

    int n_row;
    int c_row;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_notif);

//        Typeface myFont = Typeface.createFromAsset(this.getAssets(), "fonts/quicksand.otf");

        Button feedback=(Button)findViewById(R.id.feedback) ;
        ImageView imageView =(ImageView)findViewById(R.id.imgView);
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
        String s = c.getString(2);
        Log.i("Message",s);
        message.setText(s);
    }

}
