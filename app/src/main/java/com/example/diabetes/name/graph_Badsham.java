package com.example.diabetes.name;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.content.ContentValues.TAG;

public class graph_Badsham extends AppCompatActivity {
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    LineGraphSeries<DataPoint> series2, series_khatar1, series_khatar2;
    String[] sb;
    Toolbar toolbar;
    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        Log.d(TAG, "attachBaseContext");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badsham);

        overridePendingTransition(R.anim.anim_notmain2, R.anim.anim_notmain1);

        toolbar = (Toolbar) findViewById(R.id.badsham);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        TextView txttoolbar = (TextView) findViewById(R.id.txttoolbar);
        txttoolbar.setText("بعد از شام");

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


        final GraphView graph = (GraphView) findViewById(R.id.graph_badsham);


        graph.setTitle("نمودار قند بعد از شام");

        SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        final Cursor cr = db.rawQuery("select * from glucose_tb  where when_d ='"+5+"' order by glu_y , glu_m , glu_d ", null);


        cr.moveToFirst();
        sb = new String[cr.getCount() + 1];
        PointsGraphSeries<DataPoint> series3;
        series3 = new PointsGraphSeries<DataPoint>(new DataPoint[]{});
        if (cr.getCount() != 0) {

            series2 = new LineGraphSeries<DataPoint>(new DataPoint[]{});
            series_khatar1 = new LineGraphSeries<DataPoint>(new DataPoint[]{});
            series_khatar2 = new LineGraphSeries<DataPoint>(new DataPoint[]{});
            int i = 0;
//----------------------------------------------------------------------------------------

            Bundle extras = getIntent().getExtras();
            int p = extras.getInt("period");
            switch (extras.getInt("period")) {
                case 0:
                    p = 90;
                    break;
                case 1:
                    p = 30;
                    break;
                case 2:
                    p = 14;
                    break;
                case 3:
                    p = 7;
                    break;
            }
            int jalaliDaysInMonth[] = {31, 31, 31, 31, 31,
                    31, 30, 30, 30, 30, 30, 29};
            JalaliCalendar.YearMonthDate dd2;
            JalaliCalendar dateandtime = null;

            Calendar c = Calendar.getInstance();
            c.get(Calendar.DAY_OF_YEAR);
            dd2 = new JalaliCalendar.YearMonthDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            dd2 = dateandtime.gregorianToJalali(dd2);

            dateandtime = new JalaliCalendar(dd2.getYear(), dd2.getMonth(), dd2.getDate());
            int s = dateandtime.get_super2();
            //----------------------------------------------------------------------------------
            do {

//--------------------


                dateandtime = new JalaliCalendar(cr.getInt(cr.getColumnIndex("glu_y")), cr.getInt(cr.getColumnIndex("glu_m"))-1, cr.getInt(cr.getColumnIndex("glu_d")));
                int d = dateandtime.get_super2();
                int ss;



                if (dd2.getYear() > cr.getInt(cr.getColumnIndex("glu_y"))) {
                    int s2 = 0;
                    for (int i2 = cr.getInt(cr.getColumnIndex("glu_y")); i2 + 1 < dd2.getYear(); i2++) {
                        s2 = (dateandtime.isLeepYear(i2 + 1 - 1) ? 366 : 365) + s2;
                    }
                    ss = d + (dateandtime.isLeepYear(cr.getInt(cr.getColumnIndex("glu_y"))) ? 366 : 365) - s + s2;
                } else if (dd2.getYear() == cr.getInt(cr.getColumnIndex("glu_y")) && s-d >= 0) {
                    ss = s-d;

                } else {
                    ss = 100;
                }

//--------------------------
                if (ss <= p) {
                    String dtStart = String.valueOf(cr.getInt(cr.getColumnIndex("glu_y")) % 1300 + "/" + String.valueOf(cr.getInt(cr.getColumnIndex("glu_m")) + "/" + String.valueOf(cr.getInt(cr.getColumnIndex("glu_d")))));

                    JalaliCalendar dateandtime2 = new JalaliCalendar(cr.getInt(cr.getColumnIndex("glu_y")), cr.getInt(cr.getColumnIndex("glu_m")), cr.getInt(cr.getColumnIndex("glu_d")));

                    DataPoint s2 = new DataPoint(dateandtime2.getTime(), cr.getInt(cr.getColumnIndex("value")));
                    DataPoint s3 = new DataPoint(dateandtime2.getTime(), 70);

                    DataPoint s4 = new DataPoint(dateandtime2.getTime(), 180);
                    sb[i] = dtStart;
                    i++;
                    series2.appendData(s2, false, 100);
                    series3.appendData(s2, false, 100);


                    series_khatar2.appendData(s4, false, 100);
                    series_khatar1.appendData(s3, false, 100);

                }
            } while (cr.moveToNext());
            graph.addSeries(series2);
            graph.addSeries(series3);
            graph.addSeries(series_khatar1);
            graph.addSeries(series_khatar2);
            series_khatar1.setColor(Color.RED);
            series_khatar2.setColor(Color.RED);

            series_khatar1.setTitle("مقدار خطر");

            series2.setTitle("قند خون");
        }

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        cr.moveToFirst();
        series3.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), String.valueOf(dataPoint.getY()), Toast.LENGTH_SHORT).show();
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




    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_main2, R.anim.anim_main1);
    }
}
