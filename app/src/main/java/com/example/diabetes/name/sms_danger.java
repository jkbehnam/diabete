package com.example.diabetes.name;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by behnam_b on 11/22/2015.
 */
public class sms_danger {
    String s;
    Context cx;
    StringBuilder stb;

    public sms_danger(StringBuilder sb, String a, Context bx) {
        s = a;
        cx = bx;
        stb = sb;
    }

    public void sendSMSMessage() {
        String User_Path = "/data/data/com.example.diabetes.name/diabete/";
        File f = new File(User_Path);
        if (!f.exists()) {
            try {
                f.mkdirs();
                f.createNewFile();
                InputStream in = cx.getAssets().open("profile_info.db");

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

        SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                null, null);
        Cursor cr = db.rawQuery("select phone_3,phone_4,send_check_danger from message", null);
        Cursor cr_name = db.rawQuery("select fname,lname from profile_info1", null);

        cr.moveToFirst();
        cr_name.moveToFirst();
        if (cr.getCount() != 0  ) {

            if (cr.getInt(2) == 1) {

                SmsManager smsManager = SmsManager.getDefault();
                if (!(cr.getString(0).equals(""))) {
                    if (cr_name.getCount() != 0 &&!(cr_name.getString(0).equals("")) && !(cr_name.getString(1).equals(""))) {
                        Toast.makeText(cx, "پیام خطر اول  ارسال شد", Toast.LENGTH_SHORT).show();
                        smsManager.sendTextMessage(cr.getString(0), null, "قند خون  " + cr_name.getString(0) + " " + cr_name.getString(1) + "\n" + " برابر" + s + " میباشد" + "\n" + stb, null, null);
                    } else {
                        Toast.makeText(cx, "پیام خطر اول  ارسال شد", Toast.LENGTH_SHORT).show();
                        smsManager.sendTextMessage(cr.getString(0), null, "قند خون  " +"این شماره" + "\n" + " برابر" + s + " میباشد" + "\n" + stb, null, null);
                        Toast.makeText(cx, "برای ارسال پیام اخطار باید نام کامل وارد شود", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(cx, "شماره موبایل برای ارسال پیام اخطار وارد نشده", Toast.LENGTH_SHORT).show();
                if (!(cr.getString(1).equals(""))) {
                    if (cr_name.getCount() != 0 &&!(cr_name.getString(0).equals("")) && !(cr_name.getString(1).equals(""))) {
                        Toast.makeText(cx, "پیام خطر دوم  ارسال شد", Toast.LENGTH_SHORT).show();
                        smsManager.sendTextMessage(cr.getString(1), null, "قند خون  " + cr_name.getString(0) + " " + cr_name.getString(1) + "\n" + " برابر" + s + " میباشد" + "\n" + stb, null, null);
                    } else{
                        Toast.makeText(cx, "پیام خطر دوم  ارسال شد", Toast.LENGTH_SHORT).show();
                        smsManager.sendTextMessage(cr.getString(1), null, "قند خون  " + "این شماره" + "\n" + " برابر" + s + " میباشد" + "\n" + stb, null, null);
                        Toast.makeText(cx, "برای ارسال پیام اخطار باید نام کامل وارد شود", Toast.LENGTH_SHORT).show();
                    } ;
                    //  Toast.makeText(cx, "برای ارسال پیام اخطار باید نام کامل وارد شود", Toast.LENGTH_SHORT).show();

                } else ;
                // Toast.makeText(cx, "شماره موبایل برای ارسال پیام اخطار وارد نشده", Toast.LENGTH_SHORT).show();


            }

        }
        db.close();
    }
}
