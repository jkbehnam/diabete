package com.example.diabetes.name;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

public class alarm_reset extends BroadcastReceiver {
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    private Context context1;

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_LONG).show();
        context1 = arg0;
        //--------file------------------------------------------------------------------------------------
        File f = new File(User_Path);
        if (!f.exists()) {
            try {
                f.mkdirs();
                f.createNewFile();
                InputStream in = arg0.getAssets().open("profile_info.db");

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

        SQLiteDatabase db = arg0.openOrCreateDatabase(User_Path + "profile_info.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);

        Cursor cr = db.rawQuery("select * from alarm_tb", null);

        cr.moveToFirst();

        if (cr.getCount() != 0) {
            do {



                if (cr.getInt(cr.getColumnIndex("min_10")) == 1) {
                    String string = cr.getString(cr.getColumnIndex("clock"));
                    String[] parts = string.split(":");
                    String part1 = parts[0]; // 004
                    String part2 = parts[1];
                    setAlarm(Integer.parseInt(part1), Integer.parseInt(part2), cr.getInt(cr.getColumnIndex("when_d")) + 10, -10, 0);

                }
                if (cr.getInt(cr.getColumnIndex("hour_2")) == 1) {
                    String string = cr.getString(cr.getColumnIndex("clock"));
                    String[] parts = string.split(":");
                    String part1 = parts[0]; // 004
                    String part2 = parts[1];
                    setAlarm(Integer.parseInt(part1), Integer.parseInt(part2), cr.getInt(cr.getColumnIndex("when_d")) + 20, 0, 2);

                }


            } while (cr.moveToNext());
        }


        db.close();
        MediaPlayer mp;
        mp = MediaPlayer.create(arg0, R.raw.ring1);
        mp.start();
    }

    private void setAlarm(int h, int m, int when, int m1, int h1) {

        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        if (m + m1 < 0) {
            m += 60;
            h -= 1;
        }
        if (h + h1 > 23) {
            h -= 24;
        }

        calSet.set(Calendar.HOUR_OF_DAY, h + h1);

        calSet.set(Calendar.MINUTE, m + m1);

        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if (calSet.compareTo(calNow) <= 0) {
            //Today Set time passed, count to tomorrow
            calSet.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(context1, bcr_alarm.class);
        intent.putExtra("when", when);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context1, when, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context1.getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
       // Toast.makeText(context1, "تنظیم یادآور برای ساعت " + String.valueOf(h + h1) + ":" + String.valueOf(m + m
       // 1), Toast.LENGTH_SHORT).show();
        Toast.makeText(context1,"یادآور لغو شد", Toast.LENGTH_SHORT).show();



    }



}