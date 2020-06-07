package com.example.diabetes.name;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.diabetes.name.base.BaseActivity2;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.content.ContentValues.TAG;

public class graph extends BaseActivity2 {

    private Toolbar toolbar;
    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        Log.d(TAG, "attachBaseContext");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        overridePendingTransition(R.anim.anim_notmain2, R.anim.anim_notmain1);
       toolbar=(Toolbar)findViewById(R.id.gapp_bar);
       setSupportActionBar(toolbar);
        TextView txttoolbar=(TextView)findViewById(R.id.txttoolbar);
      txttoolbar.setText("نمودار ها");
       getSupportActionBar().setDisplayShowHomeEnabled(true);
     getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        CardView cvghand = (CardView)findViewById(R.id.cvghand);
        cvghand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(graph.this, Glucose.class);
                startActivity(intent);

            }
        });

        CardView cvcharbi = (CardView)findViewById(R.id.cvcharbi);
        cvcharbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(graph.this,graph_charbi.class);
                startActivity(intent);
            }
        });

        CardView cva1c = (CardView)findViewById(R.id.cva1c);
        cva1c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(graph.this,graph_ivancy.class);
                startActivity(intent);
            }
        });

        CardView cvcratine = (CardView)findViewById(R.id.cvcratine);
        cvcratine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(graph.this,graph_cratine.class);
                startActivity(intent);
            }
        });

        CardView cvpressure = (CardView)findViewById(R.id.cvpressure);
        cvpressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(graph.this,graph_feshar.class);
                startActivity(intent);
            }
        });

        CardView cvureh = (CardView)findViewById(R.id.cvureh);
        cvureh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(graph.this,graph_weight.class);
                startActivity(intent);
            }
        });




      /*  Button btnghand = (Button)findViewById(R.id.button2);
        btnghand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        Button btncharbi = (Button)findViewById(R.id.button3);
        btncharbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(graph.this,graph_charbi.class);
                startActivity(intent);
            }
        });
        Button btnpressure = (Button)findViewById(R.id.button4);
        btnpressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(graph.this,graph_feshar.class);
                startActivity(intent);
            }
        });
        Button btnureh = (Button)findViewById(R.id.button5);
        btnureh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(graph.this,graph_weight.class);
                startActivity(intent);
            }
        });
        Button btncratine = (Button)findViewById(R.id.button6);
        btncratine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(graph.this,graph_cratine.class);
                startActivity(intent);
            }
        });
        Button btnivancy = (Button)findViewById(R.id.button7);
        btnivancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(graph.this,graph_ivancy.class);
                startActivity(intent);
            }
        });*/

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( R.anim.anim_main2, R.anim.anim_main1);
    }
}
