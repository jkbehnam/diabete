package com.example.diabetes.name;


import android.Manifest;
import android.app.AlertDialog;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class Add_glucose extends Fragment {
    Calendar cal2;
    String[] forupdate;
    int saveOrupadate = 0;
    ListView lv;
    AlertDialog ald;
    AlertDialog ald2;
    AlertDialog ald_clock;
    AlertDialog.Builder builder;
    Spinner sp2;
    View alert_dialog_alert;
    View alert_dialog_newitem;
    View alert_dialog_datepicker;
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    public static int jalaliDaysInMonth[] = {31, 31, 31, 31, 31,
            31, 30, 30, 30, 30, 30, 29};
    public JalaliCalendar.YearMonthDate dd2;
    StringBuilder sb1;
    String x1;
    Context cx1;
    int i1;

    //__________show data__________________________________________________________________________________________
    public void ShowData_3(ViewGroup co, JalaliCalendar.YearMonthDate dd2) {
        SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                null, null);
        Cursor cr = db.rawQuery("select * from glucose_tb where glu_y='" + dd2.getYear() + "' and glu_m='" + dd2.getMonth() + "' and glu_d='" + dd2.getDate() + "'  order by when_d", null);


        cr.moveToFirst();
        ArrayList<class_glucose_day> data_p = new ArrayList<class_glucose_day>();


        if (cr.getCount() != 0) {
            do {
                class_glucose_day p = new class_glucose_day();

                p.setClock(cr.getString(cr.getColumnIndex("clock")));
                p.setwhen_d(cr.getInt(cr.getColumnIndex("when_d")));
                p.setGlu_y(cr.getInt(cr.getColumnIndex("glu_y")));
                p.setGlu_m(cr.getInt(cr.getColumnIndex("glu_m")));
                p.setGlu_d(cr.getInt(cr.getColumnIndex("glu_d")));
                p.setValue(cr.getInt(cr.getColumnIndex("value")));


                data_p.add(p);


            } while (cr.moveToNext());
        }


        glu_CustomListAdapter pd = new glu_CustomListAdapter(co.getContext(), data_p, 7);


        lv.setAdapter(pd);


        db.close();
    }
//___________________________________________________________________________________________

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View vw = (ViewGroup) inflater.inflate(R.layout.activity_insert_glucose, container, false);
//____________________text view todday____________________________________________________________________________________
        final TextView tv_today = (TextView) vw.findViewById(R.id.calendar);


//_____________________file checking______________________________________________________________________________________
        File f = new File(User_Path);
        if (!f.exists()) {
            try {
                f.mkdirs();
                f.createNewFile();
                InputStream in = container.getContext().getAssets().open("profile_info.db");

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
//--------list_insert-----------------------------------------------------------------------------
        sp2 = (Spinner) vw.findViewById(R.id.spinner2);

        addItemsToSpinner(container);
        sp2.setSelection(0);
        //---click on list------------------------------------------------------------------------------
        lv = (ListView) vw.findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveOrupadate = 0;
                ImageButton btn_delete = (ImageButton) alert_dialog_newitem.findViewById(R.id.btn_delete);
                btn_delete.setVisibility(View.VISIBLE);

                ald.show();
                final class_glucose_day item = (class_glucose_day) parent.getItemAtPosition(position);
                TextView tx11 = (TextView) alert_dialog_newitem.findViewById(R.id.glu_when);
                final EditText tx22 = (EditText) alert_dialog_newitem.findViewById(R.id.glu_value_ed);

                final EditText tx_h = (EditText) alert_dialog_newitem.findViewById(R.id.glu_h_ed);
                final EditText tx_min = (EditText) alert_dialog_newitem.findViewById(R.id.glu_min_ed);
                //--font set
             //   tx11.setTypeface(face);
            //    tx22.setTypeface(face);

               // tx_h.setTypeface(face);
               // tx_min.setTypeface(face);

                switch (item.getwhen_d()) {
                    case 0:
                        tx11.setText("قبل از صبحانه");
                        break;
                    case 1:
                        tx11.setText("2ساعت بعد از صبحانه");
                        break;
                    case 2:
                        tx11.setText("قبل از نهار");
                        break;
                    case 3:
                        tx11.setText("2ساعت بعد از نهار ");
                        break;
                    case 4:
                        tx11.setText("قبل از شام");
                        break;
                    case 5:
                        tx11.setText("2ساعت بعد از شام");
                        break;
                    case 6:
                        tx11.setText("نیمه شب(۳ صبح)");
                        break;

                }


                if (String.valueOf(item.getGlu_y()).equals("0")) {
                    tx22.setText("");
                } else {
                    tx22.setText(String.valueOf(item.getValue()));
                    forupdate = new String[4];
                    forupdate[0] = String.valueOf(item.getGlu_d());
                    forupdate[1] = String.valueOf(item.getGlu_m());
                    forupdate[2] = item.getClock();


                }


                String[] parts = item.getClock().split(":");

                tx_h.setText(parts[0]);
                tx_min.setText(parts[1]);
                Calendar c = Calendar.getInstance();

                if (tx_h.getText().toString().equals("--")) {
                    saveOrupadate = 1;
                    btn_delete.setVisibility(View.GONE);
                    tx_h.setText(String.valueOf(c.get(Calendar.HOUR_OF_DAY)));
                    tx_min.setText(String.valueOf(c.get(Calendar.MINUTE)));


                }
                tx_h.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                /*  tx66.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        ald_clock.show();
                        TimePicker tp=(TimePicker)alert_dialog_datepicker.findViewById(R.id.timePicker);
                        tp.setHour(c.get(Calendar.HOUR_OF_DAY));
                        tp.setMinute(c.get(Calendar.MINUTE));

                    //    StringBuilder sb2=new StringBuilder();
                      //  sb2.append(tp.getHour());

                     //   sb2.append(" : ");
                     //   sb2.append(tp.getMinute());
                     //  tx66.setText(sb2);
                    }
                });*/
//--------------------------------------------------

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase db = container.getContext().openOrCreateDatabase(User_Path + "profile_info.db",
                                SQLiteDatabase.CREATE_IF_NECESSARY, null);

                        db.delete("glucose_tb", "clock='" + forupdate[2] + "' and glu_m='" + forupdate[1] + "' and glu_d='" + forupdate[0] + "' and when_d='" + item.getwhen_d() + "'", null);

                        db.close();

                        ShowData_3(container, dd2);
                        ald.cancel();
                    }
                });

                //time picker------------------------------------------------------
                tx_h.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        TimePickerDialog tpd = TimePickerDialog.newInstance(
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

                                    }
                                }, Integer.parseInt(tx_h.getText().toString()),
                                Integer.parseInt(tx_min.getText().toString()),
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

                            tpd.show(getActivity().getFragmentManager(), "t");
                            tpd.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                    tx_h.setText(String.valueOf(hourOfDay));
                                    tx_min.setText(String.valueOf(minute));
                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                });
                tx_min.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        TimePickerDialog tpd = TimePickerDialog.newInstance(
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

                                    }
                                }, Integer.parseInt(tx_h.getText().toString()),
                                Integer.parseInt(tx_min.getText().toString()),
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

                            tpd.show(getActivity().getFragmentManager(), "t");
                            tpd.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                    tx_h.setText(String.valueOf(hourOfDay));
                                    tx_min.setText(String.valueOf(minute));
                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                });
                //-----------save button-----------------------------------------------
                Button btn_save = (Button) alert_dialog_newitem.findViewById(R.id.btn_saveg);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int x2 = Integer.parseInt(tx_h.getText().toString());

                        int x3 = Integer.parseInt(tx_min.getText().toString());
                        if (!(tx22.getText().toString().equals("0")) && !(tx22.getText().toString().equals("")) && x2 < 25 && x3 > 0 && x3 < 60) {

                            SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                                    null, null);
                            ContentValues cv = new ContentValues();
                            cv.put("when_d", item.getwhen_d());
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(tx_h.getText().toString());
                            sb2.append(":");
                            sb2.append(tx_min.getText().toString());


                            cv.put("clock", sb2.toString());
                            cv.put("value", Integer.parseInt(tx22.getText().toString()));


                            //------alert sms------------------------------------------------------------------
                            if (Integer.parseInt(tx22.getText().toString()) >= 180 || Integer.parseInt(tx22.getText().toString()) <= 70) {

                                builder = new AlertDialog.Builder(container.getContext());
                                ald2 = builder.create();
                                ald2.getWindow().getAttributes().windowAnimations = R.style.dialog;
                                alert_dialog_alert = LayoutInflater.from(container.getContext()).inflate(R.layout.alert_glu_up, null);
                                ald2.setView(alert_dialog_alert);
                                ald.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                TextView tx_alert = (TextView) alert_dialog_alert.findViewById(R.id.txt_alert);
                                Button btn_alert = (Button) alert_dialog_alert.findViewById(R.id.btn_alert);
                                btn_alert.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ald2.cancel();
                                    }
                                });
                                StringBuilder sb = new StringBuilder();
                                //sb.append(" در تا ریخ");
                               /*
                                sb.append(String.valueOf(item.getGlu_y()));
                                sb.append("/");
                                sb.append(String.valueOf(item.getGlu_m()));
                                sb.append("/");
                                sb.append(String.valueOf(item.getGlu_d()));
                                */
                                //  sb.append(" در ساعت ");
                                sb.append(String.valueOf(dd2.getYear()));
                                sb.append("/");
                                sb.append(String.valueOf(dd2.getMonth()));
                                sb.append("/");
                                sb.append(String.valueOf(dd2.getDate()));
                                sb.append("-");
                                sb.append(tx_h.getText().toString());
                                if (Integer.parseInt(tx22.getText().toString()) >= 180) {
                                    tx_alert.setText("بیش از حد مجاز است");
                                    Toast.makeText(container.getContext(), "اخطار : میزان قند خون شما بیش از حد مجاز است", Toast.LENGTH_SHORT).show();
                                }
                                if (Integer.parseInt(tx22.getText().toString()) <= 70) {
                                    tx_alert.setText("  کمتراز حد مجاز است");
                                    Toast.makeText(container.getContext(), "اخطار : میزان قند خون شما کمتر از حد مجاز است", Toast.LENGTH_SHORT).show();
                                }
                                if (Integer.parseInt(tx22.getText().toString()) >= 300 || Integer.parseInt(tx22.getText().toString()) <= 50) {
                                    String x = tx22.getText().toString();

                                    SQLiteDatabase db2 = openOrCreateDatabase(User_Path + "profile_info.db",
                                            null, null);
                                    Cursor cr = db.rawQuery("select phone_1,phone_2,send_check_danger from message", null);
                                    cr.moveToFirst();

                                    if (cr.getCount() != 0) {
                                        if ((cr.getInt(2) == 1) && (!cr.isNull(0) || !cr.isNull(1))) {
                                            checkAndroidVersion(sb, x, container.getContext(), 0);
                                        }
                                    }

                                } else {
                                    String x = tx22.getText().toString();

                                    SQLiteDatabase db2 = openOrCreateDatabase(User_Path + "profile_info.db",
                                            null, null);
                                    Cursor cr = db.rawQuery("select phone_3,phone_4,send_check_warning from message", null);
                                    cr.moveToFirst();

                                    if (cr.getCount() != 0) {
                                        if ((cr.getInt(2) == 1) && (!cr.isNull(0) || !cr.isNull(1))) {
                                            checkAndroidVersion(sb, x, container.getContext(), 1);
                                        }
                                    }


                                }

                                ald2.show();
                            }

                            //-----------------------------------------------------------------------------
                            cv.put("glu_y", Integer.parseInt(String.valueOf(dd2.getYear())));
                            cv.put("glu_m", Integer.parseInt(String.valueOf(dd2.getMonth())));
                            cv.put("glu_d", Integer.parseInt(String.valueOf(dd2.getDate())));
                            if (saveOrupadate == 1) {
                                db.insert("glucose_tb", null, cv);
                            } else {
                                db.update("glucose_tb", cv, "clock='" + forupdate[2] + "' and glu_m='" + forupdate[1] + "' and glu_d='" + forupdate[0] + "' and when_d='" + item.getwhen_d() + "'", null);
                            }

                            ShowData_3(container, dd2);

                            db.close();
                        } else
                            Toast.makeText(container.getContext(), "مقدار یا زمان وارد شده نامعتبر است", Toast.LENGTH_SHORT).show();
                        ald.cancel();
                    }
                });
                //---------------------------------------------------------------


            }
        });

        //---------button---------------

        //------alert dialog------------------------------------------------------------------------------------------
        builder = new AlertDialog.Builder(container.getContext());
        ald = builder.create();
        ald.getWindow().getAttributes().windowAnimations = R.style.dialog;
        alert_dialog_newitem = LayoutInflater.from(container.getContext()).inflate(R.layout.alert_insert_glu, null);
        ald.setView(alert_dialog_newitem);
        ald.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       /* ald_clock = builder.create();
        alert_dialog_datepicker = LayoutInflater.from(container.getContext()).inflate(R.layout.datepicker, null);
        ald_clock.setView(alert_dialog_datepicker);
        */


        //--------spinner on click----------------------------------------------------------------------------------------

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                cal2 = GregorianCalendar.getInstance();
                cal2.setTime(new Date());
                cal2.add(Calendar.DAY_OF_YEAR, -position);
                JalaliCalendar dateandtime = null;
                dd2 = new JalaliCalendar.YearMonthDate(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH));
                dd2 = dateandtime.gregorianToJalali(dd2);
                // ShowData_3(container, dd2);

                StringBuilder db = new StringBuilder();
                if (position == 0)
                    db.append("  امروز  ");
                db.append(" ");
                switch (cal2.get(Calendar.DAY_OF_WEEK)) {
                    case 1:
                        db.append("یکشنبه  ");
                        break;
                    case 2:
                        db.append("دوشنبه  ");
                        break;
                    case 3:
                        db.append("سه شنبه  ");
                        break;
                    case 4:
                        db.append("چهارشنبه  ");
                        break;
                    case 5:
                        db.append("پنجشنبه  ");
                        break;
                    case 6:
                        db.append("جمعه  ");
                        break;
                    case 7:
                        db.append("شنبه  ");
                        break;
                }
                dd2.setMonth(dd2.getMonth() + 1);
                db.append(" ");
                db.append(dd2.getYear() + "/" + (dd2.getMonth()) + "/" + dd2.getDate());

                //  db.append(date.getText().toString());
                tv_today.setText(db);
                ShowData_3(container, dd2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //-------------------------------------------

        return vw;
    }

    //------end-----------------------------
    //--------for listview--------------------------------
 /*   public void addItemsTolist(ViewGroup co) {

        ArrayList<String> list = new ArrayList<String>();
        list.add("شنبه");
        list.add("یکشنبه");
        list.add("دوشنبه");
        list.add("سه شنبه");
        list.add("چهارشنبه");
        list.add("بنجشنبه");
        list.add("جمعه");
        list.add("تاریخچه");


        Context cx = co.getContext();
        // Custom ArrayAdapter with list_insert item layout to set popup background

        glu_CustomListAdapter listAdapter = new glu_CustomListAdapter(
                cx, list);
        lv.setAdapter(listAdapter);
        lv.setSelection(4);

    }  */

    //------for spinner----------------------------------
    public void addItemsToSpinner(ViewGroup co) {
        Calendar cal = GregorianCalendar.getInstance();

        ArrayList<spinner_day> list2 = new ArrayList<spinner_day>();
        for (int i = 0; i < 7; i++) {
            spinner_day ll = new spinner_day();
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_YEAR, -i);


            JalaliCalendar dateandtime = null;
            final Calendar c = Calendar.getInstance();


            dd2 = new JalaliCalendar.YearMonthDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            dd2 = dateandtime.gregorianToJalali(dd2);


            switch (cal.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    ll.setDay("یکشنبه  ");
                    break;
                case 2:
                    ll.setDay("دوشنبه  ");
                    break;
                case 3:
                    ll.setDay("سه شنبه  ");
                    break;
                case 4:
                    ll.setDay("چهارشنبه   ");
                    break;
                case 5:
                    ll.setDay("پنجشنبه  ");
                    break;
                case 6:
                    ll.setDay("جمعه  ");
                    break;
                case 7:
                    ll.setDay("شنبه  ");
                    break;
            }


            StringBuilder db = new StringBuilder();
            dd2.setMonth(dd2.getMonth() + 1);
            db.append(dd2.getYear());
            db.append("/");
            db.append((dd2.getMonth()));
            db.append("/");
            db.append(dd2.getDate());


            ll.setSb(db);
            list2.add(ll);

        }


        Context cx = co.getContext();
        // Custom ArrayAdapter with list_insert item layout to set popup background

        custom_sp_day spinAdapter2 = new custom_sp_day(cx, list2);

        //Custom_down_SpinnerAdapter2 spinAdapter2 = new Custom_down_SpinnerAdapter2(cx, list2);
        sp2.setAdapter(spinAdapter2);
        sp2.setSelection(0);

    }

    public void send_sms() {
        switch (i1) {
            case 0:
                sms_danger s = new sms_danger(sb1, x1, cx1);
                s.sendSMSMessage();
                break;
            case 1:
                sms s2 = new sms(sb1, x1, cx1);
                s2.sendSMSMessage();
                break;
        }
    }

    public void checkAndroidVersion(StringBuilder sb, String x, Context cx, int i) {
        sb1 = sb;
        x1 = x;
        cx1 = cx;
        i1 = i;
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(cx, Manifest.permission.SEND_SMS);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 101);
                return;
            } else {
                send_sms();
            }
        } else {
            send_sms();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    send_sms();
                } else {

                    Toast.makeText(cx1, "SEND_SMS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}

