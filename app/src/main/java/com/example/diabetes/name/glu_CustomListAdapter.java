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
public class glu_CustomListAdapter extends BaseAdapter {

    int z = 0;
    private Context context1;
    private ArrayList<class_glucose_day> data;

    public Resources res;


    public glu_CustomListAdapter(Context context, ArrayList<class_glucose_day> objects, int a) {


        context1 = context;
        data = new ArrayList<class_glucose_day>();
        for (int i = 0; i < a; i++) {
            if (objects.size() != 0 && objects.get(z).getwhen_d() == i) {
                data.add(objects.get(z));


                if (z < objects.size() - 1) {


                    z++;
                }
            } else {
                class_glucose_day p = new class_glucose_day();


                p.setClock("--:--");
                p.setwhen_d(i);

                p.setGlu_y(0000);
                p.setGlu_m(00);
                p.setGlu_d(00);
                p.setValue(00000);


                data.add(p);
            }
        }


    }


    @Override
    public int getCount() {
        return 7;

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
            convertView = LayoutInflater.from(context1).inflate(R.layout.list_row, null);


            // }
            // row = inflater.inflate(R.layout.list_row, parent, false);
        }


        TextView when_d = (TextView) convertView.findViewById(R.id.sp_day);
        TextView date = (TextView) convertView.findViewById(R.id.sp_date);
        TextView value = (TextView) convertView.findViewById(R.id.sp_value);


        //   if (position == Integer.parseInt(data.get(x).getwhen_d())) {

        date.setText(data.get(position).getClock());
        value.setText(String.valueOf(data.get(position).getValue()));

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
        switch (data.get(position).getwhen_d()) {
            case 0:
                when_d.setText("قبل از صبحانه");
                break;
            case 1:
                when_d.setText("بعد از صبحانه");
                break;
            case 2:
                when_d.setText("قبل از نهار");
                break;
            case 3:
                when_d.setText("بعد از نهار ");
                break;
            case 4:
                when_d.setText("قبل از شام");
                break;
            case 5:
                when_d.setText("بعد از شام");
                break;
            case 6:
                when_d.setText("3 صبح");
                break;

        }

        return convertView;

    }

}