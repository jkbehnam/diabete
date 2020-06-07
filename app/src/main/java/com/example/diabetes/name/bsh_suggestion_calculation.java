package com.example.diabetes.name;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.diabetes.name.base.BaseActivity2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;

public class bsh_suggestion_calculation extends BaseActivity2 {
    public static int jalaliDaysInMonth[] = {31, 31, 31, 31, 31,
            31, 30, 30, 30, 30, 30, 29};
    public JalaliCalendar.YearMonthDate dd2;
    public JalaliCalendar.YearMonthDate dd3;
    public JalaliCalendar.YearMonthDate dd4;
    private Toolbar toolbar;
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    int H = 0;
    int T;
    int W = -1;
    int GoalA, GoalB, GoalM;
    Cursor cr;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions_calculation);
        //-----------------------------------------------
        overridePendingTransition(R.anim.anim_notmain2, R.anim.anim_notmain1);
        //-----toolbar-------------------------------------
        toolbar = (Toolbar) findViewById(R.id.ansolin_calculation);
        setSupportActionBar(toolbar);
        TextView txttoolbar = (TextView) findViewById(R.id.txttoolbar);
        txttoolbar.setText("توصیه های تصحیح میزان انسولین");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//-----read file---------------------------------------------------
        File f = new File(User_Path);
        if (!f.exists()) {
            try {
                f.mkdirs();
                f.createNewFile();
                InputStream in = getApplication().getAssets().open("profile_info.db");

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

        //---------------------------------------------------
        //-----tarikh emroz----------------------------------
        JalaliCalendar dateandtime = null;
        final Calendar c = Calendar.getInstance();

        //  Integer.parseInt(currentDateandTime)
        dd2 = new JalaliCalendar.YearMonthDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dd2 = dateandtime.gregorianToJalali(dd2);

        StringBuilder sb = new StringBuilder();
        dd2.setMonth(dd2.getMonth() + 1);
        sb.append(dd2.getYear());
        sb.append("/");
        sb.append(dd2.getMonth());
        sb.append("/");
        sb.append(dd2.getDate());

        Calendar cal2;
        cal2 = GregorianCalendar.getInstance();
        cal2.setTime(new Date());
        cal2.add(Calendar.DAY_OF_YEAR, -1);

        dd4 = new JalaliCalendar.YearMonthDate(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH));
        dd4 = dateandtime.gregorianToJalali(dd4);
        TextView tx_today = (TextView) findViewById(R.id.tx_today);
        tx_today.setText(sb.toString());
        //---------------------------------------------------
        //---------vazn--------------------------------------------------------
        final EditText et_weight = (EditText) findViewById(R.id.et_weight);
        final EditText et_age = (EditText) findViewById(R.id.et_age);
        db = openOrCreateDatabase(User_Path + "profile_info.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        cr = db.rawQuery("select * from weight_tb  order by o_y  ,o_m  , o_d ", null);
        cr.moveToLast();
        if (cr.getCount() != 0) {
            et_weight.setText(String.valueOf(cr.getInt(cr.getColumnIndex("value"))));

        }
        cr = db.rawQuery("select bd_y,bd_m,bd_d from profile_info1 ", null);
        cr.moveToFirst();
        if (cr.getCount() != 0) {
            try{  if (!cr.getString(cr.getColumnIndex("bd_y")).equals("") && !cr.getString(cr.getColumnIndex("bd_m")).equals("") && !cr.getString(cr.getColumnIndex("bd_d")).equals("")) {
                dd3 = new JalaliCalendar.YearMonthDate(Integer.parseInt(cr.getString(cr.getColumnIndex("bd_y"))), Integer.parseInt(cr.getString(cr.getColumnIndex("bd_m"))) - 1, Integer.parseInt(cr.getString(cr.getColumnIndex("bd_d"))));
                dd3 = dateandtime.jalaliToGregorian(dd3);


                et_age.setText(getAge(dd3.getYear(), dd3.getMonth(), dd3.getDate()));
            }}
            catch (RuntimeException e){}

        }


        //---sabeqe bimari--------------------------------------------------------------

        cr = db.rawQuery("select * from profile_info1", null);
        cr.moveToFirst();
        if (cr.getCount() != 0) {

            if (00000 != cr.getInt(cr.getColumnIndex("disease"))) {
                TextView tx_disease = (TextView) findViewById(R.id.tx_disease);
                H = 1;
                tx_disease.setText("دارم");
            } else {
            }
        }

        //-----qand khon-------------------------------------------------------------------------

//-----------spinner----------------------------------------------------------------

        Spinner spinner_nav = (Spinner) findViewById(R.id.sp_ansolin);
        ArrayList<String> list = new ArrayList<String>();
        list.add("ليسپرو");
        list.add("آسپارت ");
        list.add("گلولایزین ");
        list.add("ان پی اچ (شیری) ");
        list.add("لنت");
        list.add("رگولار (شفاف)");
        list.add("گلارژین (لانتوس)");

        list.add("دتمیر(لومیر)");
        list.add("ميکس");


        Custom_up_SpinnerAdapter spinAdapter = new Custom_up_SpinnerAdapter(
                getApplicationContext(), list);
        spinner_nav.setAdapter(spinAdapter);
        spinner_nav.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                T = position;
                SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                        SQLiteDatabase.CREATE_IF_NECESSARY, null);
                ContentValues cv = new ContentValues();
                cv.put("insulin_type", position);
                db.update("option_tb", cv, null, null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cr = db.rawQuery("select insulin_type from option_tb ", null);
        cr.moveToFirst();
        spinner_nav.setSelection(cr.getInt(cr.getColumnIndex("insulin_type")));

        //------last data------------------------------------------------------------------------------------


        Button today = (Button) findViewById(R.id.button_mohasebe);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mix=0;
                cr = db.rawQuery("select * from glucose_tb where glu_y='" + dd2.getYear() + "' and glu_m='" + dd2.getMonth() + "' and glu_d='" + dd2.getDate() + "'  order by when_d", null);
                cr.moveToFirst();

                final ArrayList<data_alarm_message> dv_list = new ArrayList<>();
                final ArrayList<data_alarm_message> dv_list2 = new ArrayList<>();
                if (cr.getCount() != 0) {

                    do {
                        data_alarm_message dv = new data_alarm_message();
                        data_alarm_message dv2 = new data_alarm_message();

                        String matn = "";
                        String mant2 = "";
                        String mant3 = "";
                        switch (cr.getInt(cr.getColumnIndex("when_d"))) {
                            case 0:
                                if (cr.getInt(cr.getColumnIndex("value")) >= 80 && cr.getInt(cr.getColumnIndex("value")) <= 130) {
                                    mant2 = "قند خون قبل از صبحانه شما ";
                                    mant3 = "بین 80 و 130 است  ,توصیه میشود زیر نظر پزشک ";
                                    matn = "تغییری در انسولین شب داده نشود";
                                    dv2.setMatn(mant2 + mant3 + "" + matn);

                                    dv_list2.add(dv2);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) > 130) {
                                    mant2 = "قند خون قبل از صبحانه شما ";
                                    mant3 = "بیشتر از 130 است  ,";
                                    matn = "قند خون ساعت سه نيمه شب را اندازه گرفته و به پزشک خود مراجعه کنيد";
                                    dv2.setMatn(mant2 + mant3 + "" + matn);
                                    dv_list2.add(dv2);
                                }
                                if (cr.getInt(cr.getColumnIndex("value")) < 80 && (T == 3 || T == 6)) {
                                    mant2 = "قند خون قبل از صبحانه شما ";
                                    mant3 = "کمتر از 80 است توصیه میشود زیر نظر پزشک ,";
                                    matn = "از انسولين شب 2 واحد کم شود.";
                                    dv2.setMatn(insulin_type(T)+mant2 + mant3 + "" + matn);

                                    dv_list2.add(dv2);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) < 80 && T == 8) {
                                    mant2 = "قند خون قبل از صبحانه شما ";
                                    mant3 = "کمتر از 80 است توصیه میشود زیر نظر پزشک ,";
                                    matn = "از انسولين شب 2 واحد کم شود.";
                                    dv2.setMatn(insulin_type(T)+mant2 + mant3 + "" + matn);

                                    dv_list2.add(dv2);

                                }

                                break;
                            case 1:
                                if (cr.getInt(cr.getColumnIndex("value")) >= 150 && cr.getInt(cr.getColumnIndex("value")) <= 180 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از صبحانه شما ";
                                    mant3 = "بین 150 و 180 است توصیه میشود زیر نظر پزشک ,";
                                    String s = "صبحانه";
                                    matn = "تغييری در ميزان انسولین " + s + " داده نشود.";
                                    dv2.setMatn(insulin_type(T)+mant2 + mant3 + matn);

                                    dv_list2.add(dv2);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) < 150 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از صبحانه شما ";
                                    mant3 = "کمتر از 150 است توصیه میشود زیر نظر پزشک ,";
                                    String s = "صبحانه";
                                    matn = "انسولين " + s + " در روز بعد 2 واحد کم شود.";
                                    dv2.setMatn(insulin_type(T)+mant2 + mant3 + matn);


                                    dv_list2.add(dv2);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) > 180 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از صبحانه شما ";
                                    mant3 = "بیشتر از 180 است توصیه میشود زیر نظر پزشک ,";
                                    String s = "صبحانه";
                                    matn = "انسولين " + s + " در روز بعد 2 واحد اضافه شود.";
                                    dv2.setMatn(insulin_type(T)+mant2 + mant3 + matn);


                                    dv_list2.add(dv2);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) > 140 && (T == 8)) {
                                    mix=1;}
                                break;
                            case 2:

                                break;
                            case 3:
                                if (cr.getInt(cr.getColumnIndex("value")) >= 150 && cr.getInt(cr.getColumnIndex("value")) <= 180 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از نهار شما ";
                                    mant3 = "بین 150 و 180 است توصیه میشود زیر نظر پزشک ,";
                                    String s = "نهار";
                                    matn = "تغييری در ميزان انسولین " + s + " داده نشود.";

                                    dv2.setMatn(insulin_type(T)+mant2 + mant3 + matn);

                                    dv_list2.add(dv2);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) < 150 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از نهار شما ";
                                    mant3 = "کمتر از 150 است توصیه میشود زیر نظر پزشک ,";
                                    String s = "نهار";
                                    matn = "انسولين " + s + " در روز بعد 2 واحد کم شود.";
                                    dv2.setMatn(insulin_type(T)+mant2 + mant3 + matn);


                                    dv_list2.add(dv2);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) > 180 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از نهار شما ";
                                    mant3 = "بیشتر از 180 است توصیه میشود زیر نظر پزشک ,";
                                    String s = "نهار";
                                    matn = "انسولين " + s + " در روز بعد 2 واحد اضافه شود.";
                                    dv2.setMatn(insulin_type(T)+mant2 + mant3 + matn);


                                    dv_list2.add(dv2);

                                }
                                break;
                            case 4:
                                if (cr.getInt(cr.getColumnIndex("value")) >= 80 && cr.getInt(cr.getColumnIndex("value")) <= 140 && (T == 8)) {
                                    mant2 = "قند خون قبل از شام شما ";
                                    mant3 = "بین 80 و 140 است توصیه میشود زیر نظر پزشک ,";
                                    matn = "تغييری در انسولين صبح داده نشود.";
                                    dv2.setMatn(insulin_type(T)+mant2 + mant3 + matn);

                                    dv_list2.add(dv2);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) < 80 && (T == 8)) {
                                    mant2 = "قند خون قبل از شام شما ";
                                    mant3 = "کمتر از 80 است توصیه میشود زیر نظر پزشک ,";
                                    matn = "از انسولين صبح 2 واحد کم کنيد.";
                                    dv2.setMatn(insulin_type(T)+mant2 + mant3 + matn);

                                    dv_list2.add(dv2);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) > 140 && (T == 8)&&mix==1) {
                                    mant2 = "قند خون بعد از صبحانه و قبل از شام شما ";
                                    mant3 = "بیشتر از 140 است توصیه میشود زیر نظر پزشک ,";
                                    matn = "به انسولين صبح 2 واحد اضافه کنيد" ;
                                    dv2.setMatn(insulin_type(T)+mant2 + mant3 + matn);

                                    dv_list2.add(dv2);

                                }


                                break;
                            case 5:

                                if (cr.getInt(cr.getColumnIndex("value")) >= 150 && cr.getInt(cr.getColumnIndex("value")) <= 180 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از شام شما ";
                                    mant3 = "بین 150 و 180 است توصیه میشود زیر نظر پزشک ,";
                                    String s = "شام";
                                    matn = "تغييری در ميزان انسولین " + s + " داده نشود.";
                                    dv2.setMatn(insulin_type(T)+mant2 + mant3 + matn);
                                    dv_list2.add(dv2);
                                }
                                if (cr.getInt(cr.getColumnIndex("value")) < 150 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از شام شما ";
                                    mant3 = "کمتر از 150 است توصیه میشود زیر نظر پزشک ,";
                                    String s = "شام";
                                    matn = "انسولين " + s + " در روز بعد 2 واحد کم شود.";
                                    dv2.setMatn(insulin_type(T)+mant2 + mant3 + matn);
                                    dv_list2.add(dv2);
                                }
                                if (cr.getInt(cr.getColumnIndex("value")) > 180 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از شام شما ";
                                    mant3 = "بیشتر از 180 است توصیه میشود زیر نظر پزشک ,";
                                    String s = "شام";
                                    matn = "انسولين " + s + " در روز بعد 2 واحد اضافه شود.";
                                    dv2.setMatn(insulin_type(T)+mant2 + mant3 + matn);


                                    dv_list2.add(dv2);

                                }

                                break;
                            case 6:


                                break;

                        }


                    } while (cr.moveToNext());
                }
                //int s565 = dd4.getMonth();
                mix=0;
                cr = db.rawQuery("select * from glucose_tb where glu_y='" + dd4.getYear() + "' and glu_m='" + (dd4.getMonth() + 1) + "' and glu_d='" + dd4.getDate() + "'  order by when_d", null);
                cr.moveToFirst();
                //     Toast.makeText(bsh_suggestion_calculation.this,dd4.getYear()+"/"+dd4.getMonth()+1+"/"+dd4.getDate(),Toast.LENGTH_SHORT).show();
                if (cr.getCount() != 0) {

                    do {
                        data_alarm_message dv = new data_alarm_message();


                        String matn = "";
                        String mant2 = "";
                        String mant3 = "";
                        switch (cr.getInt(cr.getColumnIndex("when_d"))) {
                            case 0:
                                if (cr.getInt(cr.getColumnIndex("value")) >= 80 && cr.getInt(cr.getColumnIndex("value")) <= 130) {
                                    mant2 = "قند خون قبل از صبحانه شما ";
                                    mant3 = "بین 80 و 130 بوده است توصیه میشود زیر نظر پزشک ,";
                                    matn = "تغییری در انسولین شب داده نشود";
                                    dv.setMatn(mant2 + mant3 + matn);

                                    dv_list.add(dv);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) > 130) {
                                    mant2 = "قند خون قبل از صبحانه شما ";
                                    mant3 = "بیشتر از 130 بوده است توصیه میشود زیر نظر پزشک ,";
                                    matn = "قند خون ساعت سه نيمه شب را اندازه گرفته و به پزشک خود مراجعه کنيد";
                                    dv.setMatn(mant2 + mant3 + matn);
                                    dv_list.add(dv);
                                }
                                if (cr.getInt(cr.getColumnIndex("value")) < 80 && (T == 3 || T == 6)) {
                                    mant2 = "قند خون قبل از صبحانه شما ";
                                    mant3 = "کمتر از 80 بوده است است توصیه میشود زیر نظر پزشک ,";
                                    matn = "از انسولين شب 2 واحد کم شود.";
                                    dv.setMatn(insulin_type(T)+mant2 + mant3 + matn);

                                    dv_list.add(dv);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) < 80 && T == 8) {
                                    mant2 = "قند خون قبل از صبحانه شما ";
                                    mant3 = "کمتر از 80 بوده است توصیه میشود زیر نظر پزشک ,";
                                    matn = "از انسولين شب 2 واحد کم شود.";
                                    dv.setMatn(insulin_type(T)+mant2 + mant3 + matn);

                                    dv_list.add(dv);

                                }

                                break;
                            case 1:
                                if (cr.getInt(cr.getColumnIndex("value")) >= 150 && cr.getInt(cr.getColumnIndex("value")) <= 180 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از صبحانه شما ";
                                    mant3 = "بین 150 و 180 بوده است توصیه میشود زیر نظر پزشک ,";
                                    String s = "صبحانه";
                                    matn = "تغييری در ميزان انسولین " + s + " داده نشود.";
                                    dv.setMatn(insulin_type(T)+mant2 + mant3 + matn);

                                    dv_list.add(dv);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) < 150 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از صبحانه شما ";
                                    mant3 = "کمتر از 150 بوده است توصیه میشود زیر نظر پزشک ,";
                                    String s = "صبحانه";
                                    matn = "انسولين " + s + " در روز بعد 2 واحد کم شود.";
                                    dv.setMatn(insulin_type(T)+mant2 + mant3 + matn);


                                    dv_list.add(dv);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) > 180 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از صبحانه شما ";
                                    mant3 = "بیشتر از 180 است توصیه میشود زیر نظر پزشک ,";
                                    String s = "صبحانه";
                                    matn = "انسولين " + s + " در روز بعد 2 واحد اضافه شود.";
                                    dv.setMatn(insulin_type(T)+mant2 + mant3 + matn);


                                    dv_list.add(dv);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) > 140 && (T == 8)) {
                                   mix=1;

                                }
                                break;
                            case 2:

                                break;
                            case 3:
                                if (cr.getInt(cr.getColumnIndex("value")) >= 150 && cr.getInt(cr.getColumnIndex("value")) <= 180 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از نهار شما ";
                                    mant3 = "بین 150 و 180 بوده است توصیه میشود زیر نظر پزشک ,";
                                    String s = "نهار";
                                    matn = "تغييری در ميزان انسولین " + s + " داده نشود.";
                                    dv.setMatn(insulin_type(T)+mant2 + mant3 + matn);

                                    dv_list.add(dv);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) < 150 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از نهار شما ";
                                    mant3 = "کمتر از 150 بوده است توصیه میشود زیر نظر پزشک ,";
                                    String s = "نهار";
                                    matn = "انسولين " + s + " در روز بعد 2 واحد کم شود.";
                                    dv.setMatn(insulin_type(T)+mant2 + mant3 + matn);


                                    dv_list.add(dv);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) > 180 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از نهار شما ";
                                    mant3 = "بیشتر از 180 است توصیه میشود زیر نظر پزشک ,";
                                    String s = "نهار";
                                    matn = "انسولين " + s + " در روز بعد 2 واحد اضافه شود.";
                                    dv.setMatn(insulin_type(T)+mant2 + mant3 + matn);


                                    dv_list.add(dv);

                                }
                                break;
                            case 4:
                                if (cr.getInt(cr.getColumnIndex("value")) >= 80 && cr.getInt(cr.getColumnIndex("value")) <= 140 && (T == 8)) {
                                    mant2 = "قند خون قبل از شام شما ";
                                    mant3 = "بین 80 و 140 بوده است توصیه میشود زیر نظر پزشک ,";
                                    matn = "تغييری در انسولين صبح داده نشود.";
                                    dv.setMatn(insulin_type(T)+mant2 + mant3 + matn);

                                    dv_list.add(dv);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) < 80 && (T == 8)) {
                                    mant2 = "قند خون قبل از شام شما ";
                                    mant3 = "کمتر از 80 بوده است توصیه میشود زیر نظر پزشک ,";
                                    matn = "از انسولين صبح 2 واحد کم کنيد.";
                                    dv.setMatn(insulin_type(T)+mant2 + mant3 + matn);

                                    dv_list.add(dv);

                                }
                                if (cr.getInt(cr.getColumnIndex("value")) > 140 && (T == 8)&&mix==1) {
                                    mant2 = "قند خون بعد از صبحانه و قبل از شام شما ";
                                    matn = "به انسولين صبح 2 واحد اضافه کنيد";
                                    mant3 = "بیشتر از 140 بوده است توصیه میشود زیر نظر پزشک ,";
                                    dv.setMatn(insulin_type(T)+mant2 + mant3 + matn);

                                    dv_list.add(dv);

                                }


                                break;
                            case 5:

                                if (cr.getInt(cr.getColumnIndex("value")) >= 150 && cr.getInt(cr.getColumnIndex("value")) <= 180 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از شام شما ";
                                    mant3 = "بین 150 و 180 بوده است توصیه میشود زیر نظر پزشک ,";
                                    String s = "شام";
                                    matn = "تغييری در ميزان انسولین " + s + " داده نشود.";
                                    dv.setMatn(insulin_type(T)+mant2 + mant3 + matn);
                                    dv_list.add(dv);
                                }
                                if (cr.getInt(cr.getColumnIndex("value")) < 150 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از شام شما ";
                                    mant3 = "کمتر از 150 بوده است توصیه میشود زیر نظر پزشک ,";
                                    String s = "شام";
                                    matn = "انسولين " + s + " در روز بعد 2 واحد کم شود.";
                                    dv.setMatn(insulin_type(T)+mant2 + mant3 + matn);
                                    dv_list.add(dv);
                                }
                                if (cr.getInt(cr.getColumnIndex("value")) > 180 && (T == 0 || T == 1 || T == 5)) {
                                    mant2 = "قند خون دو ساعت بعد از شام شما ";
                                    mant3 = "بیشتر از 180 است توصیه میشود زیر نظر پزشک ,";
                                    String s = "شام";
                                    matn = "انسولين " + s + " در روز بعد 2 واحد اضافه شود.";
                                    dv.setMatn(insulin_type(T)+mant2 + mant3 + matn);


                                    dv_list.add(dv);

                                }
                                break;
                            case 6:


                                break;

                        }


                    } while (cr.moveToNext());
                }
                adapter_tosiye_recycle madapter = null;
                RecyclerView rv;
                rv = (RecyclerView) findViewById(R.id.rv_today);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(bsh_suggestion_calculation.this);
                rv.setLayoutManager(mLayoutManager);
                rv.setItemAnimator(new ScaleInTopAnimator());
                RecyclerView rv2;
                rv2 = (RecyclerView) findViewById(R.id.rv_tomorow);
                RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(bsh_suggestion_calculation.this);
                rv2.setLayoutManager(mLayoutManager2);
                rv2.setItemAnimator(new ScaleInTopAnimator());


                //   tv.setText(Html.fromHtml(cr.getString(1)));

                madapter = new adapter_tosiye_recycle(dv_list);
                ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(madapter);
                alphaAdapter.setFirstOnly(true);
                rv.setAdapter(madapter);

                madapter = new adapter_tosiye_recycle(dv_list2);
                ScaleInAnimationAdapter alphaAdapter2 = new ScaleInAnimationAdapter(madapter);
                alphaAdapter2.setFirstOnly(true);
                rv2.setAdapter(madapter);


            }
        });


    }
    //button today--------------------------------------------------------------------------


    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public String adad(double x) {
        DecimalFormat numberFormat = new DecimalFormat("#0.00");
        return numberFormat.format(x);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_main2, R.anim.anim_main1);
    }
    public String insulin_type(int i){
        String a="در استفاده از انسولین ";
        String b="";

        String d="";


        switch (i){
            case 0:
                 b="کوتاه اثر ";
                d="ليسپرو";
                break;
            case 1:
                b="کوتاه اثر ";
                d="آسپارت";
                break;
            case 2:
                b="طولانی اثر ";
                d="گلولایزین";
                break;
            case 3:
                b="طولانی اثر ";
                d="ان پی اچ (شیری) ";
                break;
            case 4:

                d="لنت";
                break;
            case 5:
                d="رگولار (شفاف)";
                b="کوتاه اثر ";

                break;
            case 6:
                d="گلارژین (لانتوس)";

                break;
            case 7:
                d="دتمیر(لومیر)";

                break;
            case 8:

                d="ميکس";
                break;
            default:
                return "";


        }
        return a+b+d+" ,";
    }
}
