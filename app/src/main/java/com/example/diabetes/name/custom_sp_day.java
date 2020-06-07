package com.example.diabetes.name;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// Custom Adapter for Spinner
public class custom_sp_day extends ArrayAdapter<spinner_day> {

    private Context context1;
    private ArrayList<spinner_day> data;
    public Resources res;
    LayoutInflater inflater;

    public custom_sp_day(Context context, ArrayList<spinner_day> objects) {
        super(context, R.layout.list_row, objects);

        context1 = context;
        data = objects;

        inflater = (LayoutInflater) context1
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    @Override
    public int getCount() {
        return super.getCount(); // you dont display last item. It is used as hint.
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.spinner_row_2, parent, false);
        TextView day = (TextView) row.findViewById(R.id.sp_day);
        TextView date = (TextView) row.findViewById(R.id.sp_date);
        TextView value = (TextView) row.findViewById(R.id.sp_value);
        value.setVisibility(View.GONE);


        day.setText(data.get(position).getDay());
        date.setText(data.get(position).getSb());


        return row;
    }
}