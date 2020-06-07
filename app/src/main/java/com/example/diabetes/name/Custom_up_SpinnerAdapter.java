package com.example.diabetes.name;


import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// Custom Adapter for Spinner
public class Custom_up_SpinnerAdapter extends ArrayAdapter<String> {

    private Context context1;
    private ArrayList<String> data;
    public Resources res;
    LayoutInflater inflater;

    public Custom_up_SpinnerAdapter(Context context, ArrayList<String> objects) {
        super(context, R.layout.spinner_row, objects);

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

        View row = inflater.inflate(R.layout.spinner_row, parent, false);

     TextView tvCategory = (TextView) row.findViewById(R.id.tvCategory);
  /*      Typeface ft=Typeface.createFromAsset(context1.getAssets(),"nazanin.TTF");
tvCategory.setTypeface(ft);
*/
        tvCategory.setText(data.get(position).toString());
        if (position == getCount()) {

            tvCategory.setHint("dsdsd");}

        return row;
    }
}