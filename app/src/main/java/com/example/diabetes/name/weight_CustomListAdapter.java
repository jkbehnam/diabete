package com.example.diabetes.name;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

// Custom Adapter for Spinner
public class weight_CustomListAdapter extends BaseAdapter {

    int z = 0;
    private Context context1;
    private ArrayList<weight_day> data;

    public Resources res;
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";


    public weight_CustomListAdapter(Context context, ArrayList<weight_day> objects) {


        context1 = context;
        data = new ArrayList<weight_day>();
        data = objects;

    }


    @Override
    public int getCount() {
        return data.size();

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

            convertView = LayoutInflater.from(context1).inflate(R.layout.list_row_weight4, null);


        }
        TextView date = (TextView) convertView.findViewById(R.id.list_date);
        TextView value = (TextView) convertView.findViewById(R.id.list_weight);
        TextView height = (TextView) convertView.findViewById(R.id.list_height);

        TextView bmi = (TextView) convertView.findViewById(R.id.list_bmi);
        TextView stu = (TextView) convertView.findViewById(R.id.list_situation);




        height.setText(String.valueOf(data.get(position).getHeight()));
        double x = data.get(position).getHeight();
        x = x / 100;


        double y = Integer.parseInt(String.valueOf(data.get(position).getValue()));
        double result = y / (x * x);
        DecimalFormat numberFormat = new DecimalFormat("#.0");
        System.out.println(numberFormat.format(x));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(numberFormat.format(result));
        bmi.setText(sb2);
        if (0 <= result && result <= 18.5) {
            stu.setText("کمبود وزن");
        } else if (18.5 < result && result <= 24.9) {

            stu.setText("طبیعی");
        } else if (25 < result && result <= 29.9) {
            stu.setText("اضافه وزن");
        } else if (30 < result && result <= 34.9) {
            stu.setText("چاقی درجه1");
        }else if (result >= 35) {
            stu.setText("چاقی درجه2");
        }


        if (String.valueOf(data.get(position).getHeight()).equals("0"))
        {
        stu.setText("نا معلوم");
           }





        date.setText( String.valueOf(data.get(position).getO_y()) + "/" + String.valueOf(data.get(position).getO_m()) + "/" + String.valueOf(data.get(position).getO_d()) );
        value.setText(String.valueOf(data.get(position).getValue()));



        return convertView;

    }

}