package com.example.diabetes.name;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.diabetes.name.base.BaseActivity2;

import java.util.ArrayList;

public class Glucose extends BaseActivity2 {
Spinner sp;
    Toolbar toolbar;
    Intent intent;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glucose);
        overridePendingTransition(R.anim.anim_notmain2, R.anim.anim_notmain1);

        toolbar = (Toolbar)findViewById(R.id.ghandappbars);
        setSupportActionBar(toolbar);
        TextView txttoolbar=(TextView)findViewById(R.id.txttoolbar);
        txttoolbar.setText("نمودارهای قندخون");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

         sp=(Spinner)findViewById(R.id.sp_period);
        addItemsToSpinner();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                i = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CardView cvghabsobhane = (CardView)findViewById(R.id.cvghablsobhane);
        cvghabsobhane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(Glucose.this, graph_Ghablazsobh.class);
                intent.putExtra("period",i);
                startActivity(intent);

            }
        });

        CardView cvbadsobhane = (CardView)findViewById(R.id.cvbadsobhane);
        cvbadsobhane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(Glucose.this,graph_Badazsobh.class);
                intent.putExtra("period",i);
                startActivity(intent);
                ;
            }
        });


        CardView cvghablnahar = (CardView)findViewById(R.id.cvghablnahar);
        cvghablnahar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 intent = new Intent(Glucose.this,graph_Ghablnahar.class);
                intent.putExtra("period",i);
                startActivity(intent);

            }
        });

        CardView cvbadnahar = (CardView)findViewById(R.id.cvbadnahar);
        cvbadnahar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 intent = new Intent(Glucose.this,graph_Badnahar.class);
                intent.putExtra("period",i);
                startActivity(intent);

            }
        });


        CardView cvghablsham = (CardView)findViewById(R.id.cvghablsham);
        cvghablsham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(Glucose.this,graph_Ghablsham.class);
                intent.putExtra("period",i);
                startActivity(intent);

            }
        });


        CardView cvbadsham = (CardView)findViewById(R.id.cvbadsham);
        cvbadsham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Glucose.this,graph_Badsham.class);
                intent.putExtra("period",i);
                startActivity(intent);

            }
        });
        CardView cvmidnight = (CardView)findViewById(R.id.cvmidnight);
        cvmidnight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Glucose.this,graph_3midnight.class);
                intent.putExtra("period",i);
                startActivity(intent);

            }
        });






    }

    public void addItemsToSpinner() {

        ArrayList<String> list = new ArrayList<String>();
        list.add("دوره 3 ماهه");
        list.add("دوره 1 ماهه");
        list.add("دوره 2 هفته");
        list.add("دوره 1 هفته");


        // Custom ArrayAdapter with spinner item layout to set popup background

        Custom_up_SpinnerAdapter spinAdapter = new Custom_up_SpinnerAdapter(
                getApplicationContext(), list);
        sp.setAdapter(spinAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
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
        overridePendingTransition(R.anim.anim_main2, R.anim.anim_main1);
    }
}
