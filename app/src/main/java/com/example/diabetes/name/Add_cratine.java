package com.example.diabetes.name;


import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class Add_cratine extends Fragment {
    int saveOrupadate = 0;
    String[] forupdate;
    FragmentTransaction ft;
    AlertDialog ald;
    AlertDialog.Builder builder;
    Spinner sp2;
    ListView lv;
    View alert_dialog_newitem;
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    public static int jalaliDaysInMonth[] = {31, 31, 31, 31, 31,
            31, 30, 30, 30, 30, 30, 29};
    JalaliCalendar.YearMonthDate dd2;
    ImageButton btn_delete;

    //____________________________________________________________________________________________________
    public void ShowData_3(ViewGroup co) {
        SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                null, null);
        final Cursor cr = db.rawQuery("select * from Creatine_tb order by o_y  ,o_m  , o_d    ", null);
        cr.moveToLast();
        ArrayList<class_others_day> data_p = new ArrayList<class_others_day>();
        int x = 0;
        if (cr.getCount() != 0) {
            do {
                class_others_day p = new class_others_day();


                p.setO_y(cr.getInt(cr.getColumnIndex("o_y")));
                p.setO_m(cr.getInt(cr.getColumnIndex("o_m")));
                p.setO_d(cr.getInt(cr.getColumnIndex("o_d")));
                p.setValue(cr.getInt(cr.getColumnIndex("value")));

                data_p.add(p);
                x++;
            } while (cr.moveToPrevious() && x < 15);
        }
        other_CustomListAdapter pd = new other_CustomListAdapter(co.getContext(), data_p, 12);
        lv.setAdapter(pd);


        db.close();
    }
//___________________________________________________________________________________________


    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View vw = (ViewGroup) inflater.inflate(R.layout.add_other, container, false);
//____________________text view todday____________________________________________________________________________________
        TextView tv_today = (TextView) vw.findViewById(R.id.calender_2);
        TableRow tr=(TableRow)vw.findViewById(R.id.row_height);
        tr.setVisibility(View.GONE);

        final TextView ctv = (TextView)vw.findViewById(R.id.top_row2).findViewById(R.id.sp_value);
        ctv.setText("کراتین");
        vw.findViewById(R.id.top_row).setVisibility(View.GONE);

        Button btn_new_insert = (Button) vw.findViewById(R.id.btn_new_insert);


        JalaliCalendar dateandtime = null;
        final Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String currentDateandTime = sdf.format(new Date());
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


        tv_today.setText(sb.toString());

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


        //---------button---------------

        //------alert dialog------------------------------------------------------------------------------------------
//        final TextView tx11 = (TextView) alert_dialog_newitem.findViewById(R.id.glu_when);

        //------alert dialog------------------------------------------------------------------------------------------
        //------alert dialog------------------------------------------------------------------------------------------
        builder = new AlertDialog.Builder(container.getContext());
        ald = builder.create();
        ald.getWindow().getAttributes().windowAnimations = R.style.dialog;
        alert_dialog_newitem = LayoutInflater.from(container.getContext()).inflate(R.layout.alert_insert_, null);
        final TextView tx11 = (TextView) alert_dialog_newitem.findViewById(R.id.glu_when);
        tx11.setVisibility(View.GONE);
        final EditText tx22 = (EditText) alert_dialog_newitem.findViewById(R.id.glu_value_ed);
        final EditText tx33 = (EditText) alert_dialog_newitem.findViewById(R.id.glu_y_ed);
        final EditText tx44 = (EditText) alert_dialog_newitem.findViewById(R.id.glu_m_ed);
        final EditText tx55 = (EditText) alert_dialog_newitem.findViewById(R.id.glu_d_ed);
        final EditText tx66 = (EditText) alert_dialog_newitem.findViewById(R.id.glu_time_ed);


        tx66.setVisibility(View.GONE);
        ald.setView(alert_dialog_newitem);
        Button btn_save = (Button) alert_dialog_newitem.findViewById(R.id.btn_saveg);
        btn_delete = (ImageButton) alert_dialog_newitem.findViewById(R.id.btn_delete);
        //------btn save in alert dialog--------------------------------------------------------------------------------------
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //day
                int x1= Integer.parseInt(tx55.getText().toString());
                //month
                int x2= Integer.parseInt(tx44.getText().toString());
                //year
                int x3= Integer.parseInt(tx33.getText().toString());

                if(!(tx22.getText().toString().equals(""))&&x1>0&&x1<32&&x2>0&&x2<13&&x3>1300&&x3<1500) {
                SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                        null, null);
                ContentValues cv = new ContentValues();

                //cv.put("when_d", item.getWhen_d());

                    cv.put("value", Integer.parseInt(tx22.getText().toString()));
                    cv.put("o_y", Integer.parseInt(tx33.getText().toString()));
                    cv.put("o_m", Integer.parseInt(tx44.getText().toString()));
                    cv.put("o_d", Integer.parseInt(tx55.getText().toString()));
                    if (saveOrupadate == 1) {
                        db.insert("Creatine_tb", "when_d", cv);
                    } else {
                        db.update("Creatine_tb", cv, "value='" + forupdate[2] + "' and o_m='" + forupdate[1] + "' and o_d='" + forupdate[0] + "'", null);
                    }

                    ShowData_3(container);


                    db.close();
                }else
                    Toast.makeText(container.getContext(),"مقدار یا تاریخ وارد شده نامعتبر است",Toast.LENGTH_SHORT).show();

                ald.cancel();
            }
        });


        //-------click on listview-----------------------------------------------------------------------------------------
        lv = (ListView) vw.findViewById(R.id.listView2);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveOrupadate = 0;
                btn_delete.setVisibility(View.VISIBLE);


                final class_others_day item = (class_others_day) parent.getItemAtPosition(position);


                tx22.setText(String.valueOf(item.getValue()));
                tx33.setText(String.valueOf(item.getO_y()));
                tx44.setText(String.valueOf(item.getO_m()));
                tx55.setText(String.valueOf(item.getO_d()));


                forupdate = new String[3];
                forupdate[0] = String.valueOf(item.getO_d());
                forupdate[1] = String.valueOf(item.getO_m());
                forupdate[2] = String.valueOf(item.getValue());


                //-----------delete button-----------------------------------------------

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase db = container.getContext().openOrCreateDatabase(User_Path + "profile_info.db",
                                SQLiteDatabase.CREATE_IF_NECESSARY, null);


                        db.delete("Creatine_tb", "value='" + forupdate[2] + "' and o_m='" + forupdate[1] + "' and o_d='" + forupdate[0] + "'", null);
                        db.close();
                        ShowData_3(container);
                        ald.cancel();
                    }
                });
                //---------------------------------------------------------------


                ald.show();
            }
        });
//date picker------------------------------------------------------------------
        tx55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersianCalendar now = new PersianCalendar();

                final additem mainActivity = (additem) getActivity();


                final DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                            }
                        },
                        Integer.parseInt(tx33.getText().toString()),
                        Integer.parseInt(tx44.getText().toString())-1,
                        Integer.parseInt(tx55.getText().toString())
                );

                    /* Convert you context to an activity to get Fragment Manager */

                try {

                    dpd.show(mainActivity.getFragmentManager(), "Datepickerdialog");
                    dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                            tx33.setText(String.valueOf(year));
                            tx44.setText(String.valueOf(monthOfYear+1));
                            tx55.setText(String.valueOf(dayOfMonth));


                        }
                    });
                } catch (Exception e) {

                }
            }
        });
        tx33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersianCalendar now = new PersianCalendar();

                final additem mainActivity = (additem) getActivity();


                final DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                            }
                        },
                        Integer.parseInt(tx33.getText().toString()),
                        Integer.parseInt(tx44.getText().toString())-1,
                        Integer.parseInt(tx55.getText().toString())
                );

                    /* Convert you context to an activity to get Fragment Manager */

                try {

                    dpd.show(mainActivity.getFragmentManager(), "Datepickerdialog");
                    dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                            tx33.setText(String.valueOf(year));
                            tx44.setText(String.valueOf(monthOfYear+1));
                            tx55.setText(String.valueOf(dayOfMonth));


                        }
                    });
                } catch (Exception e) {

                }
            }
        });
        tx44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersianCalendar now = new PersianCalendar();

                final additem mainActivity = (additem) getActivity();


                final DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                            }
                        },
                        Integer.parseInt(tx33.getText().toString()),
                        Integer.parseInt(tx44.getText().toString())-1,
                        Integer.parseInt(tx55.getText().toString())
                );

                    /* Convert you context to an activity to get Fragment Manager */

                try {

                    dpd.show(mainActivity.getFragmentManager(), "Datepickerdialog");
                    dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                            tx33.setText(String.valueOf(year));
                            tx44.setText(String.valueOf(monthOfYear+1));
                            tx55.setText(String.valueOf(dayOfMonth));


                        }
                    });
                } catch (Exception e) {

                }
            }
        });

        //-------------------------------------------------------------------------------
//--------------------add new item---------------------

        btn_new_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tx33.setText(String.valueOf(dd2.getYear()));
                tx44.setText(String.valueOf(dd2.getMonth()));
                tx55.setText(String.valueOf(dd2.getDate()));
                tx22.setText("");
                btn_delete.setVisibility(View.GONE);
                saveOrupadate = 1;
                ald.show();
                // cv.put("when_d", item.getWhen_d());


            }
        });
        //------------------------------------------------------------------------------------------------


        ShowData_3(container);
        return vw;
    }


}