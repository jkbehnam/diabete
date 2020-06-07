package com.example.diabetes.name;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// Custom Adapter for Spinner
public class bp_CustomListAdapter extends BaseAdapter {

    int z = 0;
    private Context context1;
    private ArrayList<class_bp_day> data;

    public Resources res;


    public bp_CustomListAdapter(Context context, ArrayList<class_bp_day> objects, int a) {


        context1 = context;
       data = new ArrayList<class_bp_day>();
        data=objects;

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
        // return getCustomView(position, convertView, parent);
        if (convertView == null) {
            //if (_listData.get(position).getCount()> 10) {
            //   convertView = LayoutInflater.from(_ctx).inflate(R.layout.listitem_01, null);
            // }
            //else
            // {
            //     convertView = LayoutInflater.from(_ctx).inflate(R.layout.x, null);
            convertView = LayoutInflater.from(context1).inflate(R.layout.list_row_bp, null);

            // }

        }


        TextView when_d = (TextView) convertView.findViewById(R.id.sp_day);
        when_d.setVisibility(View.GONE);
        TextView date = (TextView) convertView.findViewById(R.id.sp_date);
        TextView value = (TextView) convertView.findViewById(R.id.sp_value);
        TextView value2 = (TextView) convertView.findViewById(R.id.sp_value2);





     //   if (position == Integer.parseInt(data.get(x).getwhen_d())) {

            date.setText(String.valueOf(data.get(position).getO_y())+"/"+String.valueOf(data.get(position).getO_m())+"/"+String.valueOf(data.get(position).getO_d()));
            value2.setText(String.valueOf(data.get(position).getValue_1()));
        value.setText(String.valueOf(data.get(position).getValue_2()));

/*switch (Integer.parseInt(data.get(x).getwhen_d())){
    case 0:when_d.setText("شنبه");
        break;
    case 1:when_d.setText("یکشنبه");
        break;
    case 2:when_d.setText("دوشنبه");
        break;
    case 3:when_d.setText("سه شنبه");
        break;
    case 4:when_d.setText("چهارشنبه");
        break;
    case 5:when_d.setText("پنجشمبه");
        break;
    case 6:when_d.setText("جمعه");
        break;

}*/
        /*    switch (data.get(position).getWhen_d()) {
                case 0:
                    when_d.setText("فروردین");
                    break;
                case 1:
                    when_d.setText("اردیبهشت");
                    break;
                case 2:
                    when_d.setText("خرداد");
                    break;
                case 3:
                    when_d.setText("تیر");
                    break;
                case 4:
                    when_d.setText("مرداد");
                    break;
                case 5:
                    when_d.setText("شهریور");
                    break;
                case 6:
                    when_d.setText("مهر");
                    break;
                case 7:
                    when_d.setText("آبان");
                    break;
                case 8:
                    when_d.setText("آذر");
                    break;
                case 9:
                    when_d.setText("دی");
                    break;
                case 10:
                    when_d.setText("بهمن");
                    break;
                case 11:
                    when_d.setText("اسفند");
                    break;


            }*/



        return convertView;

    }

}