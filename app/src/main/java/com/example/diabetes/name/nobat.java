package com.example.diabetes.name;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diabetes.name.base.BaseActivity2;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class nobat extends BaseActivity2 implements View.OnClickListener {
    private Toolbar toolbar;
    AlertDialog ald;
    AlertDialog.Builder builder;
    View alert_dialog_newitem;
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    public static int jalaliDaysInMonth[] = {31, 31, 31, 31, 31,
            31, 30, 30, 30, 30, 30, 29};
    JalaliCalendar.YearMonthDate dd2;
    ListView lv;
    String[] forupdate;
    int saveOrupadate = 1;
    EditText ed_y, ed_m, ed_d, ed_h, ed_min, ed_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nobat);
        overridePendingTransition(R.anim.anim_notmain2, R.anim.anim_notmain1);
        //-----------------------------------------------------------

        toolbar = (Toolbar) findViewById(R.id.nobatappbar);
        setSupportActionBar(toolbar);
        TextView txttoolbar = (TextView) findViewById(R.id.txttoolbar);
        txttoolbar.setText("یادآور ملاقات پزشک");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //_____________________file checking______________________________________________________________________________________
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

//____________________________________________________________________________________________________


        //-----------------------------------------------------------
        TextView tvtime = (TextView) findViewById(R.id.textView12);

        //-----------------------------------------------------------------------------------
        JalaliCalendar dateandtime = null;
        final Calendar c = Calendar.getInstance();

        //  Integer.parseInt(currentDateandTime)
        dd2 = new JalaliCalendar.YearMonthDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dd2 = dateandtime.gregorianToJalali(dd2);
        StringBuilder sb = new StringBuilder();
        sb.append("  امروز  ");
        switch (c.get(Calendar.DAY_OF_WEEK)) {

            case 1:
                sb.append("یکشنبه  ");
                break;
            case 2:
                sb.append("دوشنبه  ");
                break;
            case 3:
                sb.append("سه شنبه  ");
                break;
            case 4:
                sb.append("چهارشنبه  ");
                break;
            case 5:
                sb.append("پنجشنبه  ");
                break;
            case 6:
                sb.append("جمعه  ");
                break;
            case 7:
                sb.append("شنبه  ");
                break;


        }
        dd2.setMonth(dd2.getMonth() + 1);
        sb.append(dd2.getYear());
        sb.append("/");
        sb.append(dd2.getMonth());
        sb.append("/");
        sb.append(dd2.getDate());


        tvtime.setText(sb.toString());
//-----------alert dialog----------------------------------------------------------------------------------
        builder = new AlertDialog.Builder(this);
        ald = builder.create();
        alert_dialog_newitem = LayoutInflater.from(this).inflate(R.layout.alert_nobat, null);
        ald.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ald.setView(alert_dialog_newitem);
        ed_y = (EditText) alert_dialog_newitem.findViewById(R.id.editText);
        ed_m = (EditText) alert_dialog_newitem.findViewById(R.id.editText3);
        ed_d = (EditText) alert_dialog_newitem.findViewById(R.id.editText2);
        ed_h = (EditText) alert_dialog_newitem.findViewById(R.id.editText6);
        ed_min = (EditText) alert_dialog_newitem.findViewById(R.id.editText7);
        ed_name = (EditText) alert_dialog_newitem.findViewById(R.id.editText4);

        final EditText ed_expl = (EditText) alert_dialog_newitem.findViewById(R.id.editText8);


        String[] doc = {"دیابت", "آزمایش های سه ماهه", "قلب", "کلیه", "چشم", "تغذیه"};
        final Spinner doct = (Spinner) alert_dialog_newitem.findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, doc);
        adapter2.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        doct.setAdapter(adapter2);

        final ImageButton btndelet = (ImageButton) alert_dialog_newitem.findViewById(R.id.delete);
//------------------------------------------------------------------------------------------
        Button btnnobat = (Button) findViewById(R.id.btnnobat);
        btnnobat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_y.setText(String.valueOf(dd2.getYear()));
                ed_m.setText(String.valueOf(dd2.getMonth()));
                ed_d.setText(String.valueOf(dd2.getDate()));
                Calendar c = Calendar.getInstance();
                ed_h.setText(String.valueOf(c.get(Calendar.HOUR_OF_DAY)));
                ed_min.setText(String.valueOf(c.get(Calendar.MINUTE)));
                saveOrupadate = 1;
                btndelet.setVisibility(View.GONE);
                ald.getWindow().getAttributes().windowAnimations = R.style.dialog;
                ald.show();
            }
        });

        //--------------------------------------------------doc_save-----------------------------------------------
        Button btnsave = (Button) alert_dialog_newitem.findViewById(R.id.doc_save);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                        SQLiteDatabase.CREATE_IF_NECESSARY, null);
                ContentValues cv = new ContentValues();

                //cv.put("when_d", item.getWhen_d());


                int x4 = Integer.parseInt(ed_h.getText().toString());

                int x5 = Integer.parseInt(ed_min.getText().toString());
                if (x4 >= 0 && x4 < 25 && x5 >= 0 && x5 < 60) {


                    int x3 = Integer.parseInt(ed_y.getText().toString());
                    //month
                    int x2 = Integer.parseInt(ed_m.getText().toString());
                    //year
                    int x1 = Integer.parseInt(ed_d.getText().toString());
                    if (1 > 0 && x1 < 32 && x2 > 0 && x2 < 13 && x3 > 1300 && x3 < 1500) {
                        cv.put("turn_y", Integer.parseInt(ed_y.getText().toString()));
                        cv.put("turn_m", Integer.parseInt(ed_m.getText().toString()));
                        cv.put("turn_d", Integer.parseInt(ed_d.getText().toString()));
                        cv.put("turn_h", Integer.parseInt(ed_h.getText().toString()));
                        cv.put("turn_min", Integer.parseInt(ed_min.getText().toString()));
                        cv.put("doc_name", ed_name.getText().toString());
                        cv.put("doc_expert", doct.getSelectedItem().toString());
                        cv.put("explanation", ed_expl.getText().toString());

                        if (saveOrupadate == 1) {

                            Toast.makeText(nobat.this, "save", Toast.LENGTH_SHORT).show();
                            db.insert("d_turn", null, cv);
                        } else {
                            db.update("d_turn", cv, "turn_y='" + forupdate[0] + "' and turn_m='" + forupdate[1] + "' and turn_d='" + forupdate[2] + "' and turn_h='" + forupdate[3] + "' and turn_min='" + forupdate[4] + "'", null);
                        }
                        db.close();
                        ShowData_3();

                    } else {
                        Toast.makeText(nobat.this, "تاریخ  وارد شده معتبر نمیباشد", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(nobat.this, "ساعت  وارد شده معتبر نمیباشد", Toast.LENGTH_SHORT).show();
                }


                ald.cancel();

            }
        });

        //-------click on listview-----------------------------------------------------------------------------------------
        lv = (ListView) findViewById(R.id.listView3);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveOrupadate = 0;
                btndelet.setVisibility(View.VISIBLE);


                final class_doc_turn item = (class_doc_turn) parent.getItemAtPosition(position);


                ed_y.setText(String.valueOf(item.getTurn_y()));
                ed_m.setText(String.valueOf(item.getTurn_m()));
                ed_d.setText(String.valueOf(item.getTurn_d()));
                ed_h.setText(String.valueOf(item.getTurn_h()));
                ed_min.setText(String.valueOf(item.getTurn_min()));
                ed_name.setText(item.getDoc_name());

                switch (item.getDoc_expert()) {
                    case "دیابت":
                        doct.setSelection(0);
                        break;
                    case "آزمایش های سه ماهه":
                        doct.setSelection(1);
                        break;
                    case "قلب":
                        doct.setSelection(2);
                        break;
                    case "کلیه":
                        doct.setSelection(3);
                        break;
                    case "چشم":
                        doct.setSelection(4);
                        break;
                    case "تغذیه":
                        doct.setSelection(5);
                        break;


                }
                ed_expl.setText(item.getExplanation());


                forupdate = new String[5];
                forupdate[0] = String.valueOf(item.getTurn_y());
                forupdate[1] = String.valueOf(item.getTurn_m());
                forupdate[2] = String.valueOf(item.getTurn_d());
                forupdate[3] = String.valueOf(item.getTurn_h());
                forupdate[4] = String.valueOf(item.getTurn_min());


                //-----------delete button-----------------------------------------------

                btndelet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase db = getApplication().openOrCreateDatabase(User_Path + "profile_info.db",
                                SQLiteDatabase.CREATE_IF_NECESSARY, null);


                        db.delete("d_turn", "turn_y='" + forupdate[0] + "' and turn_m='" + forupdate[1] + "' and turn_d='" + forupdate[2] + "' and turn_h='" + forupdate[3] + "' and turn_min='" + forupdate[4] + "'", null);
                        db.close();
                        ShowData_3();
                        ald.cancel();
                    }
                });
                //---------------------------------------------------------------


                ald.show();
            }
        });


        handleClicks();
        ShowData_3();
    }

    private void handleClicks() {
        ed_d.setOnClickListener(this);
        ed_m.setOnClickListener(this);
        ed_y.setOnClickListener(this);
        ed_h.setOnClickListener(this);
        ed_min.setOnClickListener(this);
    }

    //____________________________________________________________________________________________________
    public void ShowData_3() {
        SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        Cursor cr = db.rawQuery("select * from d_turn  ", null);
        cr.moveToFirst();
        ArrayList<class_doc_turn> data_p = new ArrayList<class_doc_turn>();

        if (cr.getCount() != 0) {
            do {
                class_doc_turn dt = new class_doc_turn();


                dt.setTurn_y(cr.getInt(cr.getColumnIndex("turn_y")));
                dt.setTurn_m(cr.getInt(cr.getColumnIndex("turn_m")));
                dt.setTurn_d(cr.getInt(cr.getColumnIndex("turn_d")));
                dt.setTurn_h(cr.getInt(cr.getColumnIndex("turn_h")));
                dt.setTurn_min(cr.getInt(cr.getColumnIndex("turn_min")));
                dt.setDoc_name(cr.getString(cr.getColumnIndex("doc_name")));
                dt.setDoc_expert(cr.getString(cr.getColumnIndex("doc_expert")));
                dt.setExplanation(cr.getString(cr.getColumnIndex("explanation")));


                data_p.add(dt);
            } while (cr.moveToNext());
        }
        doc_turn_CustomListAdapter pd = new doc_turn_CustomListAdapter(nobat.this, data_p, 12);
        lv.setAdapter(pd);


        db.close();
    }

    //___________________________________________________________________________________________
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_main2, R.anim.anim_main1);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.editText:
            case R.id.editText2:
            case R.id.editText3: {
                DatePickerDialog dpd2 = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                            }
                        },
                        Integer.parseInt(ed_y.getText().toString()),
                        Integer.parseInt(ed_m.getText().toString()) - 1,
                        Integer.parseInt(ed_d.getText().toString())
                );

                //  Convert you context to an activity to get Fragment Manager

                try {

                    dpd2.show(getFragmentManager(), "Datepickerdialog");
                    dpd2.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                            ed_y.setText(String.valueOf(year));
                            ed_m.setText(String.valueOf(monthOfYear + 1));
                            ed_d.setText(String.valueOf(dayOfMonth));
                        }
                    });
                } catch (Exception e) {

                }
                break;
            }
            case R.id.editText6:
            case R.id.editText7:

            case R.id.editText4:

            {
                Calendar c = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

                            }
                        }, Integer.parseInt(ed_h.getText().toString()),
                        Integer.parseInt(ed_min.getText().toString()),
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
                    tpd.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                            ed_h.setText(String.valueOf(hourOfDay));
                            ed_min.setText(String.valueOf(minute));
                        }
                    });
                } catch (Exception e) {

                }
                break;
            }

        }
    }
}
