package com.example.diabetes.name;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

// Custom Adapter for Spinner
public class doc_turn_CustomListAdapter extends BaseAdapter {

    int z = 0;
    private Context context1;
    private ArrayList<class_doc_turn> data;

    public Resources res;


    public doc_turn_CustomListAdapter(Context context, ArrayList<class_doc_turn> objects, int a) {


        context1 = context;
        data = new ArrayList<class_doc_turn>();
        data = objects;

    }


    @Override
    public int getCount() {
        return data.size();

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
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(context1).inflate(R.layout.custom_list_nobat, null);

        }


        TextView doc_date = (TextView) convertView.findViewById(R.id.doc_date);

        TextView doc_time = (TextView) convertView.findViewById(R.id.doc_time);
        TextView doc_name = (TextView) convertView.findViewById(R.id.doc_name);
        TextView doc_exp = (TextView) convertView.findViewById(R.id.doc_exp);
        TextView expl = (TextView) convertView.findViewById(R.id.explanation);
        Typeface ft = Typeface.createFromAsset(context1.getAssets(), "nazanin.TTF");
        doc_time.setTypeface(ft);
        expl.setTypeface(ft);
        doc_date.setTypeface(ft);
        doc_name.setTypeface(ft);

        doc_date.setText(String.valueOf(data.get(position).getTurn_y()) + "/" + String.valueOf(data.get(position).getTurn_m()) + "/" + String.valueOf(data.get(position).getTurn_d()));

        doc_time.setText(String.valueOf(data.get(position).getTurn_h()) + " : " + String.valueOf(data.get(position).getTurn_min()));
        doc_name.setText(data.get(position).getDoc_name());
        doc_exp.setText(data.get(position).getDoc_expert());
        expl.setText(data.get(position).getExplanation());


        JalaliCalendar dateandtime = null;
        JalaliCalendar.YearMonthDate dd2;

        Calendar c = Calendar.getInstance();
        c.get(Calendar.DAY_OF_YEAR);
        dd2 = new JalaliCalendar.YearMonthDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dd2 = dateandtime.gregorianToJalali(dd2);
        dd2.setMonth(dd2.getMonth() + 1);

        LinearLayout l=(LinearLayout)convertView.findViewById(R.id.nobat_layer);
        if (dd2.getYear() >= data.get(position).getTurn_y()) {
            if (dd2.getMonth() >= data.get(position).getTurn_m()) {
                if (dd2.getDate() > data.get(position).getTurn_d()) {

                    l.setBackgroundResource(R.drawable.list_nobat2);

                }else l.setBackgroundResource(R.drawable.list_nobat);
            }else l.setBackgroundResource(R.drawable.list_nobat);
        }else l.setBackgroundResource(R.drawable.list_nobat);




    return convertView;

}

}