package com.example.diabetes.name;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diabetes.name.base.BaseActivity2;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class alarm extends BaseActivity2 {
    int when_;
    int i = 1;
    Toolbar toolbar;
    ListView lv;
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    String s2 = "";
    CheckBox silence;
    View alert_dialog_newitem;
    TimePickerDialog timePickerDialog;

    public void ShowData_3() {
        SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        Cursor cr = db.rawQuery("select * from alarm_tb", null);

        cr.moveToFirst();
        ArrayList<alarm_day> data_p = new ArrayList<alarm_day>();
        if (cr.getCount() != 0) {
            do {
                alarm_day p = new alarm_day();

                p.setClock(cr.getString(cr.getColumnIndex("clock")));
                p.setWhen_d(cr.getInt(cr.getColumnIndex("when_d")));
                p.setCheck_10min(cr.getInt(cr.getColumnIndex("min_10")));
                p.setCheck_2hour(cr.getInt(cr.getColumnIndex("hour_2")));


                data_p.add(p);


            } while (cr.moveToNext());
        }


        alarm_CustomListAdapter pd = new alarm_CustomListAdapter(this, data_p, 7);
        lv.setAdapter(pd);
        cr = db.rawQuery("select * from option_tb", null);
        cr.moveToFirst();
        if ((cr.getInt(0) == 1)) {
            silence.setChecked(true);
        } else {
            silence.setChecked(false);
        }

        db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        overridePendingTransition(R.anim.anim_notmain2, R.anim.anim_notmain1);
        silence = (CheckBox) findViewById(R.id.checkBox_alarm_silence);
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


        toolbar = (Toolbar) findViewById(R.id.alarmappbar);
        setSupportActionBar(toolbar);
        TextView txttoolbar = (TextView) findViewById(R.id.txttoolbar);
        txttoolbar.setText("یادآوری اندازه گیری قند خون");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //------save button----------------------------------------------------------------------------------------

        Button btn_save = (Button) findViewById(R.id.btn_alarm_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                        SQLiteDatabase.CREATE_IF_NECESSARY, null);
                ContentValues cv = new ContentValues();
                if (silence.isChecked())
                    cv.put("alarm_silent", 1);
                else
                    cv.put("alarm_silent", 0);
                db.update("option_tb", cv, null, null);
                Toast.makeText(alarm.this, "تغییرات ثبت شد", Toast.LENGTH_SHORT).show();

            }
        });
        //---click on list------------------------------------------------------------------------------

        lv = (ListView) findViewById(R.id.listView_alarm);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(alarm.this, "ثبت با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                String s = "";
                final alarm_day item = (alarm_day) parent.getItemAtPosition(position);
                switch (item.getWhen_d()) {
                    case 0:
                        s = "صرف صبحانه";
                        break;
                    case 1:
                        s = "صرف نهار";
                        break;
                    case 2:
                        s = "صرف شام";
                        break;
                    case 3:
                        s = "خواب ";
                        break;


                }
                when_ = item.getWhen_d();

                Calendar c = Calendar.getInstance();
                com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog tpd = com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog.newInstance(
                        new com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

                            }
                        }, c.get(Calendar.HOUR_OF_DAY),
                       c.get(Calendar.MINUTE),
                        true
                );
                tpd.setThemeDark(false);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("t", "Dialog was cancelled");
                    }
                });


                try {

                    tpd.show(getFragmentManager(), "t");
                    tpd.setOnTimeSetListener(new com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                            if (i == 1) {
                                SQLiteDatabase db = alarm.this.openOrCreateDatabase(User_Path + "profile_info.db",
                                        SQLiteDatabase.CREATE_IF_NECESSARY, null);
                                String id = String.valueOf(when_);
                                String[] strWhere = new String[]{id};
                                ContentValues cv6 = new ContentValues();
                                if (minute < 10)
                                    s2 = String.valueOf(hourOfDay) + ":0" + String.valueOf(minute);
                                else
                                    s2 = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                                cv6.put("clock", s2);
                                cv6.put("min_10", 0);
                                cv6.put("hour_2", 0);
                                db.update("alarm_tb", cv6, "when_d=?", strWhere);
                                //   Toast.makeText(_ctx, String.valueOf(String.valueOf(_listData.get(position).getPasscheck())), Toast.LENGTH_SHORT).show();
                                //  Toast.makeText(_ctx,String.valueOf(_listData.get(position).getSh_s()),Toast.LENGTH_SHORT).show();

                                db.close();
                                i = 1;
                                cancelAlarm(when_ + 10);
                                cancelAlarm(when_ + 20);
                                ShowData_3();
                            }
                        }
                    });
                } catch (Exception e) {

                }
            }
        });


        ShowData_3();

    }






    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onContextItemSelected(item);
    }

    private void cancelAlarm(int when) {
        Intent intent = new Intent(alarm.this, bcr_alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(alarm.this, when, intent, 0);
        AlarmManager alarmManager = (AlarmManager) alarm.this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_main2, R.anim.anim_main1);
    }
}
