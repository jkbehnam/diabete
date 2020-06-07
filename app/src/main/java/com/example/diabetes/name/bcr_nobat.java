package com.example.diabetes.name;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * Created by behnam2 on 8/22/2015.
 */
public class bcr_nobat extends BroadcastReceiver {
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    NotificationManager nm;
    int i2 = 0;

    @Override

    public void onReceive(Context context, Intent intent) {

        //--------file------------------------------------------------------------------------------------
        File f = new File(User_Path);
        if (!f.exists()) {
            try {
                f.mkdirs();
                f.createNewFile();
                InputStream in = context.getAssets().open("profile_info.db");

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

        SQLiteDatabase db = context.openOrCreateDatabase(User_Path + "profile_info.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);

        Cursor cr = db.rawQuery("select * from d_turn", null);
        cr.moveToFirst();

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


                if (day >= 0 && day <= 7) {


                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                            context);


                    nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent intent2 = new Intent(context, nobat.class);


                    PendingIntent pending = PendingIntent.getActivity(context, 0, intent2, 0);

                    String s3 = String.valueOf(day) + " روز دیگر";
                    if (day == 0)
                        s3 = " امروز";

                    String s2 = "شما " + s3 + " با پزشک " + cr.getString(cr.getColumnIndex("doc_expert")) + " وقت ملاقات دارید  ";
                    notif(context,s2,"یادآور ملاقات با پزشک");
                    ++i2;
/*
                    mBuilder
                            .setTicker("یادآور ملاقات با پزشک")
                            .setContentText(s2)
                            .setSmallIcon(R.drawable.icon)

                            .setContentTitle("یادآور ملاقات با پزشک")
                            .setContentIntent(pending)
                            .setWhen(System.currentTimeMillis())


                            .setAutoCancel(true);


// Moves events into the expanded layout


                    nm.notify(i2, mBuilder.build());
                    */



                }

            }
            while (cr.moveToNext());


            //Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();


        }


        db.close();
    }

    public void notif(Context context,String message,String title){
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder;
        final String NOTIFICATION_CHANNEL_ID = "10001";
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // String message = "دوست عزیز جهت نظارت بر روند بهبودی شما و ارائه مشاوره بموقع لازم است اطلاعات لازم را در قسمت شاخص های روزانه ثبت نمایید.";
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                10001 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.icon);
        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(i2 /* Request Code */, mBuilder.build());
        i2++;
    }

}
