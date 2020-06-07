package com.example.diabetes.name;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.diabetes.name.adapter.adapterRcycleMain;
import com.example.diabetes.name.base.BaseActivity2;
import com.example.diabetes.name.objects.FirstMenu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.content.ContentValues.TAG;

public class MainActivity extends BaseActivity2 {
    TextView txtstatus;
    private Toolbar toolbar;
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    SQLiteDatabase db;
    RecyclerView rcleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcleView=(RecyclerView)findViewById(R.id.MyGameMainActivity_recycle) ;
        txtstatus = (TextView) findViewById(R.id.txtstatus);
        toolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);
            TextView txttoolbar=(TextView)findViewById(R.id.txttoolbar);
           txttoolbar.setText("سامانه مدیریت دیابت");
      //  txttoolbar.setShadowLayer(15, 0, 20, R.color.button_text);
        Typeface ft = Typeface.createFromAsset(this.getAssets(), "Mj_Maveddat Black_0.ttf");
        txttoolbar.setTypeface(ft);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/iran_sans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
//------------------------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.bottonNav));

        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        // rcleView.setLayoutManager(layoutManager);
     /*   rouchuan.circlelayoutmanager.CircleLayoutManager circleLayoutManager = new rouchuan.circlelayoutmanager.CircleLayoutManager(getActivity());
        rcleView.setLayoutManager(circleLayoutManager);
        rcleView.addOnScrollListener(new rouchuan.circlelayoutmanager.CenterScrollListener());
        */
        //  rcleView.setLayoutManager(new HiveLayoutManager(HiveLayoutManager.VERTICAL));


        rcleView.setLayoutManager(layoutManager);
        ArrayList<FirstMenu> glist = new ArrayList<>();
        glist.add(new FirstMenu(getResources().getString(R.string.information), "profile"));
        glist.add(new FirstMenu(getResources().getString(R.string.add), "additem"));
        glist.add(new FirstMenu(getResources().getString(R.string.graph), "graph"));
        glist.add(new FirstMenu(getResources().getString(R.string.nobat), "reminder"));
        glist.add(new FirstMenu(getResources().getString(R.string.alarm), "alarms"));
        glist.add(new FirstMenu(getResources().getString(R.string.connections), "sms"));
        glist.add(new FirstMenu(getResources().getString(R.string.ansolinreminders), "insolin"));
        glist.add(new FirstMenu(getResources().getString(R.string.FoodReport), "chart_icon"));
        adapterRcycleMain madapter = new adapterRcycleMain(glist);
        rcleView.setAdapter(madapter);

        madapter.setOnCardClickListner(new adapterRcycleMain.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {
                Class context=null;
                switch (position) {
                    case 0:

                        context=profileset.class;
                        break;
                    case 1:
                        context=additem.class;
                        break;
                    case 2:

                        context=graph.class;
                        break;
                    case 3:

                        context=yadaavar.class;
                        break;
                    case 4:

                        context=messagepage.class;
                        break;
                    case 5:

                        context=connection.class;
                        break;
                    case 6:

                        context=bsh_suggestion_calculation.class;
                        break;
                    case 7:

                        context=TableView.class;
                        break;
                }

                Intent intent = new Intent(MainActivity.this,context );
                startActivity(intent);
            }
        });

        //---------------------------------------------
        overridePendingTransition(R.anim.anim_main2, R.anim.anim_main1);
        //permistion
        int MY_PERMISSIONS_REQUEST_READ_CONTACTS=0;
// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        //_____________________file checking______________________________________________________________________________________
        File f = new File(User_Path);
        if (!f.exists()) {
            try {
                f.mkdirs();
                f.createNewFile();
                InputStream in = this.getAssets().open("profile_info.db");

                OutputStream out = new FileOutputStream(User_Path + "profile_info.db");

                int read;
                byte[] buffer = new byte[1024];

                while ((read = in.read(buffer)) != -1) {

                    out.write(buffer, 0, read);

                }

                in.close();
                out.close();


            } catch (Exception exp) {


            }
        } else {
        }

//____________________________________________________________________________________________________
        db = openOrCreateDatabase(User_Path + "profile_info.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);

/*
        cr2.moveToLast();
        if (cr2.getCount() != 0) {


            class_glucose_day p = new class_glucose_day();


            int s = cr2.getInt(cr2.getColumnIndex("value"));

            if (s > 140) {
                  txtstatus.setText("هشدار");
            } else if (s < 140 && s > 70) {

                  txtstatus.setText("طبیعی");
            }


        }
*/


        db.close();
//---------------------------------------------
        Intent i = new Intent(this, bcr_nobat.class);
        i.setAction("start");
        sendBroadcast(i);
        //-------------------------------------



    }

    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        Log.d(TAG, "attachBaseContext");
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

        if ((id == R.id.aboutus)) {
// gheyere faal kardan safhe about
           // startActivity(new Intent(this, aboutus.class));
        }

        if ((id == R.id.exit)) {

            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        Cursor cr2 = db.rawQuery("select value from glucose_tb order by glu_y , glu_m , glu_d", null);


        cr2.moveToLast();
        if (cr2.getCount() != 0) {

            int s = cr2.getInt(cr2.getColumnIndex("value"));

            if (s > 140) {
                txtstatus.setText("هشدار");
                txtstatus.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.exercise_state_yellow, 0);
            } else if (s < 140 && s > 70) {

                txtstatus.setText("طبیعی");
                txtstatus.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.exercise_state_green, 0);
            }


        }


        //-------------------------
        TextView txnobat = (TextView) findViewById(R.id.txtnobat);
        Cursor cr = db.rawQuery("select * from d_turn order by turn_y  ,turn_m  , turn_d,turn_h,turn_min", null);
        cr.moveToFirst();

        int x = 400;
        if (cr.getCount() != 0) {
            do {

                JalaliCalendar dateandtime = null;
                JalaliCalendar.YearMonthDate dd2;
                Calendar c = Calendar.getInstance();
                dd2 = new JalaliCalendar.YearMonthDate(cr.getInt(cr.getColumnIndex("turn_y")), cr.getInt(cr.getColumnIndex("turn_m")) - 1, cr.getInt(cr.getColumnIndex("turn_d")));
                dd2 = dateandtime.jalaliToGregorian(dd2);
                Calendar c3 = Calendar.getInstance();
                c3.set(dd2.getYear(), dd2.getMonth(), dd2.getDate());



                Long d2 = c3.getTimeInMillis() - c.getTimeInMillis();
                long second = d2 / 1000;
                long minute = second / 60;
                long hour = minute / 60;
                long day = hour / 24;
                //Toast.makeText(getBaseContext(), String.valueOf(hour), Toast.LENGTH_SHORT).show();


                if (day >= 0 && day <= 7) {


                    String s3 = String.valueOf(day) + " روز دیگر";
                    if (day == 0)
                        s3 = " امروز";

                    String s2 = "شما " + s3 + " با پزشک " + cr.getString(cr.getColumnIndex("doc_expert")) + " وقت ملاقات دارید  ";


                    txnobat.setText(s2);
                    break;

                } else {
                    txnobat.setText("");
                }


            }
            while (cr.moveToNext());


            //Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();


        } else {
            //   txnobat.setText("");
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
