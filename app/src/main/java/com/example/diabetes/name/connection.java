package com.example.diabetes.name;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diabetes.name.base.BaseActivity2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.content.ContentValues.TAG;

public class connection extends BaseActivity2 {
    private Toolbar toolbar;
    int auto=0;
    int auto2=0;
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
   EditText phone1, phone2,phone3,phone4;
    int firsttime=0;
    CheckBox auto_send,auto_send2;
    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        Log.d(TAG, "attachBaseContext");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        overridePendingTransition(R.anim.anim_notmain2, R.anim.anim_notmain1);
        toolbar = (Toolbar) findViewById(R.id.smsappbar);
        setSupportActionBar(toolbar);
        TextView txttoolbar=(TextView)findViewById(R.id.txttoolbar);
        txttoolbar.setText("ارتباطات");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //---------define edittexts-----------------------------------------------------------------------
        phone1 = (EditText) findViewById(R.id.phone_1);
        phone2 = (EditText) findViewById(R.id.phone_2);
        phone3 = (EditText) findViewById(R.id.phone_3);
        phone4 = (EditText) findViewById(R.id.phone_4);
        TextView ph_1=(TextView)findViewById(R.id.ph_1);
        TextView ph_2=(TextView)findViewById(R.id.ph_2);
        TextView ph_3=(TextView)findViewById(R.id.ph_3);
        TextView ph_4=(TextView)findViewById(R.id.ph_4);


        //--------file------------------------------------------------------------------------------------
        File f = new File(User_Path);
        if (!f.exists()) {
            try {
                f.mkdirs();
                f.createNewFile();
                InputStream in = getAssets().open("profile_info.db");

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

        //---------define edittexts-----------------------------------------------------------------------



        //-------check box 1---------------------------------------------------------------
        auto_send = (CheckBox) findViewById(R.id.box_auto_send);

        auto_send.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    auto = 1;
                } else {
                    auto = 0;
                }
            }
        });
        //-------check box 2---------------------------------------------------------------
        auto_send2 = (CheckBox) findViewById(R.id.box_auto_send_2);

        auto_send2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    auto2 = 1;
                } else {
                    auto2 = 0;
                }
            }
        });
        //---------save btn-----------------------------------------------------------------------
        Button btn_save = (Button) findViewById(R.id.btn_msg_submit);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                        SQLiteDatabase.CREATE_IF_NECESSARY, null);
                ContentValues cv = new ContentValues();

                cv.put("phone_1", phone1.getText().toString());
                cv.put("phone_2", phone2.getText().toString());
                cv.put("phone_3", phone3.getText().toString());
                cv.put("phone_4", phone4.getText().toString());
                cv.put("send_check_warning", auto);
                cv.put("send_check_danger", auto2);
                if (firsttime == 0) {
                    db.insert("message", null, cv);
                } else {
                    db.update("message", cv, null, null);
                }
                Toast.makeText(connection.this,"ثبت با موفقیت انجام شد", Toast.LENGTH_SHORT).show();

                db.close();

                ShowData_3();
            }
        });
        //--------send now-----------------------------------------------------------------------
        Button btn_send=(Button)findViewById(R.id.btn_send_now);
      btn_send.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                      SQLiteDatabase.CREATE_IF_NECESSARY, null);
              final Cursor cr = db.rawQuery("select * from glucose_tb order by glu_y , glu_m , glu_d ", null);


              cr.moveToLast();
              if (cr.getCount() != 0) {
                      class_glucose_day p = new class_glucose_day();
                  StringBuilder sb=new StringBuilder();
                  //sb.append(" در تا ریخ");
                  sb.append(cr.getInt(0));
                  sb.append("/");
                  sb.append(cr.getInt(1));
                  sb.append("/");
                  sb.append(cr.getInt(2));
                  //  sb.append(" در ساعت ");
                  sb.append("-");
                  sb.append(cr.getString(3));

                  String x = String.valueOf(cr.getInt(cr.getColumnIndex("value")));

                  sms_now_send s=new sms_now_send(sb,x,getApplication());
                  s.sendSMSMessage();
              }


              db.close();
          }



    });


        //---------------------------------------------------------------------------------------
        ShowData_3();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( R.anim.anim_main2, R.anim.anim_main1);
    }

    public void ShowData_3() {
        SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        Cursor cr = db.rawQuery("select * from message", null);

        cr.moveToFirst();
        if (cr.getCount() != 0) {
            firsttime = 1;

            if (!(cr.isNull(0))) {

                  phone1.setText(cr.getString(0));
            }
           if (!(cr.isNull(1)))
               phone2.setText(cr.getString(cr.getColumnIndex("phone_2")));
            if (!(cr.isNull(2)))
                phone3.setText(cr.getString(cr.getColumnIndex("phone_3")));
            if (!(cr.isNull(3)))
                phone4.setText(cr.getString(cr.getColumnIndex("phone_4")));
            if (cr.getInt(4)==0)
            {
             auto_send.setChecked(false);
            }
            else {
                auto_send.setChecked(true);
            }
            if (cr.getInt(5)==0)
            {
                auto_send2.setChecked(false);
            }
            else {
                auto_send2.setChecked(true);
            }

        }

    }
}
