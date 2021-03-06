package com.example.diabetes.name;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diabetes.name.base.BaseActivity2;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.content.ContentValues.TAG;

public class graph_feshar extends BaseActivity2 {
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    LineGraphSeries<DataPoint> series2, series1;
    String[] sb;
    private Toolbar toolbar;

    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        Log.d(TAG, "attachBaseContext");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feshar);
        toolbar = (Toolbar) findViewById(R.id.fesharappbar);
        setSupportActionBar(toolbar);
        TextView txttoolbar = (TextView) findViewById(R.id.txttoolbar);
        txttoolbar.setText("نمودار فشار خون");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PointsGraphSeries<DataPoint> series3;
        series3 = new PointsGraphSeries<DataPoint>(new DataPoint[]{});
        PointsGraphSeries<DataPoint> series4;
        series4 = new PointsGraphSeries<DataPoint>(new DataPoint[]{});

        File f = new File(User_Path);
        if (!f.exists()) {
            try {
                f.mkdirs();
                f.createNewFile();
                InputStream in = this.getAssets().open("profile_info.db");

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


        final GraphView graph = (GraphView) findViewById(R.id.feshargraph);

graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.setTitle("نمودار فشار خون (ماهیانه)");

        SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        final Cursor cr = db.rawQuery("select * from bp_tb order by o_y ,o_m , o_d  ", null);


        cr.moveToFirst();
        sb = new String[cr.getCount() + 1];

        if (cr.getCount() != 0) {
            series2 = new LineGraphSeries<DataPoint>(new DataPoint[]{});
            series1 = new LineGraphSeries<DataPoint>(new DataPoint[]{});
            int i = 0;
            do {


                String dtStart = String.valueOf(cr.getInt(cr.getColumnIndex("o_y")) % 1300 + "/" + String.valueOf(cr.getInt(cr.getColumnIndex("o_m")) + "/" + String.valueOf(cr.getInt(cr.getColumnIndex("o_d")))));

                JalaliCalendar dateandtime2 = new JalaliCalendar(cr.getInt(cr.getColumnIndex("o_y")), cr.getInt(cr.getColumnIndex("o_m"))-1, cr.getInt(cr.getColumnIndex("o_d")));

//---------------feshar bala

                DataPoint s = new DataPoint(dateandtime2.getTime(), cr.getInt(cr.getColumnIndex("value_1")));
//---------------feshar paein

                DataPoint s2 = new DataPoint(dateandtime2.getTime(), cr.getInt(cr.getColumnIndex("value_2")));
                sb[i] = dtStart;
                i++;
                series2.appendData(s2, false, 100);
                series1.appendData(s, false, 100);
                series3.appendData(s2, false, 100);
                series4.appendData(s, false, 100);

            } while (cr.moveToNext());
            graph.addSeries(series2);
            graph.addSeries(series1);
            graph.addSeries(series4);
            graph.addSeries(series3);
            series1.setColor(Color.RED);
            series4.setColor(Color.RED);
            series1.setTitle("فشار بالا");
            series2.setTitle("فشار پایین");


        }


        cr.moveToFirst();
        series3.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                PersianCalendar now = new PersianCalendar();
                now.setTimeInMillis((long) dataPoint.getX());

                Toast.makeText(getApplicationContext(), String.valueOf(dataPoint.getY())+"\n"+now.getPersianShortDate(), Toast.LENGTH_SHORT).show();
            }
        });
        series4.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                PersianCalendar now = new PersianCalendar();
                now.setTimeInMillis((long) dataPoint.getX());

                Toast.makeText(getApplicationContext(), String.valueOf(dataPoint.getY())+"\n"+now.getPersianShortDate(), Toast.LENGTH_SHORT).show();
            }
        });

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {


                    // show normal x values
                    PersianCalendar now = new PersianCalendar();
                    now.setTimeInMillis((long) value);
                    return now.getPersianShortDate();
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX);
                }
            }
        });
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
    }


    public Context getActivity() {
        return this;
    }

}
