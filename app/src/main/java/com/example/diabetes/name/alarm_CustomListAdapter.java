package com.example.diabetes.name;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

// Custom Adapter for Spinner
public class alarm_CustomListAdapter extends BaseAdapter {

    int z = 0;
    private Context context1;
    private ArrayList<alarm_day> data;
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    public Resources res;
    AlertDialog.Builder builder;
    View alert_dialog_newitem;
    TimePickerDialog timePickerDialog;
    AlertDialog ald;
    String s;
    int i = 0;

    public alarm_CustomListAdapter(Context context, ArrayList<alarm_day> objects, int a) {


        context1 = context;
        data = new ArrayList<alarm_day>();
        for (int i = 0; i < a; i++) {
            if (objects.size() != 0 && objects.get(z).getWhen_d() == i) {
                data.add(objects.get(z));


                if (z < objects.size() - 1) {


                    z++;
                }
            } else {
                alarm_day p = new alarm_day();


                p.setClock("--:--");
                p.setWhen_d(i);

                p.setCheck_2hour(0);
                p.setCheck_10min(0);


                data.add(p);
            }
        }


    }


    @Override
    public int getCount() {
        return 4;

        // you dont display last item. It is used as hint.
    }

    @Override
    public Object getItem(int position) {


        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // return getCustomView(position, convertView, parent);
        if (convertView == null) {
            //if (_listData.get(position).getCount()> 10) {
            //   convertView = LayoutInflater.from(_ctx).inflate(R.layout.listitem_01, null);
            // }
            //else
            // {
            convertView = LayoutInflater.from(context1).inflate(R.layout.list_row_alarm, null);


            // }
            // row = inflater.inflate(R.layout.list_row, parent, false);
        }
        s = "";
        final TextView when_d = (TextView) convertView.findViewById(R.id.sp_day);
        final TextView time_ = (TextView) convertView.findViewById(R.id.sp_time);
        time_.setText(data.get(position).getClock());
        final CheckBox ch_10min = (CheckBox) convertView.findViewById(R.id.ch_10min);
        final CheckBox ch_2h = (CheckBox) convertView.findViewById(R.id.ch_2h);





   /*     tr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(context1, String.valueOf(i), Toast.LENGTH_SHORT).show();
                //  openTimePickerDialog(false, when_d.getText().toString(),data.get(position).getWhen_d());
                //  showDialog(TIME_DIALOG_ID);

                Toast.makeText(context1, String.valueOf(time_.getTag()), Toast.LENGTH_SHORT).show();
                Toast.makeText(context1, time_.getTag().toString(), Toast.LENGTH_SHORT).show();
                timePickerDialog.show();


                //  else Toast.makeText(context1, s, Toast.LENGTH_SHORT).show();

            }
        });
        */


        //   if (position == Integer.parseInt(data.get(x).getwhen_d())) {


        switch (data.get(position).getWhen_d()) {
            case 0:
                when_d.setText("صرف صبحانه");
                break;
            case 1:
                when_d.setText("صرف نهار");
                break;
            case 2:
                when_d.setText("صرف شام");
                break;
            case 3:
                when_d.setText("خواب ");
                break;


        }

        if (data.get(position).getCheck_10min() == 1) {
            ch_10min.setChecked(true);
        } else {
            ch_10min.setChecked(false);

        }
        if (data.get(position).getCheck_2hour() == 1) {
            ch_2h.setChecked(true);
        } else {
            ch_2h.setChecked(false);

        }

        //------checkbox----------------------------------------------------------------
        ch_10min.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SQLiteDatabase db = context1.openOrCreateDatabase(User_Path + "profile_info.db",
                        SQLiteDatabase.CREATE_IF_NECESSARY, null);
                String id = String.valueOf(data.get(position).getWhen_d());
                String[] strWhere = new String[]{id};
                ContentValues cv6 = new ContentValues();
                if (ch_10min.isChecked()) {
                    cv6.put("min_10", 1);
                    String string = time_.getText().toString();
                    String[] parts = string.split(":");
                    String part1 = parts[0]; // 004
                    String part2 = parts[1];
                    setAlarm(Integer.parseInt(part1), Integer.parseInt(part2), data.get(position).getWhen_d() + 10, -10, 0);

                } else {
                    cv6.put("min_10", 0);
                    cancelAlarm(data.get(position).getWhen_d() + 10);

                }

                db.update("alarm_tb", cv6, "when_d=?", strWhere);
                //   Toast.makeText(_ctx, String.valueOf(String.valueOf(_listData.get(position).getPasscheck())), Toast.LENGTH_SHORT).show();
                //  Toast.makeText(_ctx,String.valueOf(_listData.get(position).getSh_s()),Toast.LENGTH_SHORT).show();

                db.close();
            }
        });
        //--------xheckbox--------------------------------------------------------------

        ch_2h.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SQLiteDatabase db = context1.openOrCreateDatabase(User_Path + "profile_info.db",
                        SQLiteDatabase.CREATE_IF_NECESSARY, null);
                String id = String.valueOf(data.get(position).getWhen_d());
                String[] strWhere = new String[]{id};
                ContentValues cv6 = new ContentValues();
                if (ch_2h.isChecked()) {
                    cv6.put("hour_2", 1);

                    String string = time_.getText().toString();
                    String[] parts = string.split(":");
                    String part1 = parts[0]; // 004
                    String part2 = parts[1];
                    setAlarm(Integer.parseInt(part1), Integer.parseInt(part2), data.get(position).getWhen_d() + 20, 0, 2);


                } else {

                    cv6.put("hour_2", 0);

                    cancelAlarm(data.get(position).getWhen_d() + 20);
                }

                db.update("alarm_tb", cv6, "when_d=?", strWhere);
                //   Toast.makeText(_ctx, String.valueOf(String.valueOf(_listData.get(position).getPasscheck())), Toast.LENGTH_SHORT).show();
                //  Toast.makeText(_ctx,String.valueOf(_listData.get(position).getSh_s()),Toast.LENGTH_SHORT).show();

                db.close();
            }
        });
        //    if (x < data.size() - 1) {
        //         x++;

        //     }

        //  } else {

         /*   StringBuilder ss = new StringBuilder();
            ss.append(position);
            Toast.makeText(context1, ss, Toast.LENGTH_SHORT).show();
*/
        //     when_d.setText(when_ds[position]);
        //     date.setText("--");
        //     value.setText("--");
        //   }
        //    y++;
        //    if (y == 7) {
        //        x = 0;
        //   y = 0;
        //    }

        return convertView;

    }

    private void setAlarm(int h, int m, int when, int m1, int h1) {

        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        if (m + m1 < 0) {
            m += 60;
            h -= 1;
        }
        if (h + h1 > 23) {
            h-=24;
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
        intent.putExtra("when",when);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context1, when, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context1.getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
        Toast.makeText(context1,"تنظیم یادآور برای ساعت "+ String.valueOf(h + h1) + ":" + String.valueOf(m + m1), Toast.LENGTH_SHORT).show();




    }

    private void cancelAlarm(int when) {
        Intent intent = new Intent(context1, bcr_alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context1, when, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context1.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context1,"یادآور لغو شد", Toast.LENGTH_SHORT).show();

    }
}