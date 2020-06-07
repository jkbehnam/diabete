package com.example.diabetes.name;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.diabetes.name.base.BaseActivity2;

public class yadaavar extends BaseActivity2 {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yadaavar);

        overridePendingTransition(R.anim.anim_notmain2, R.anim.anim_notmain1);
        toolbar=(Toolbar)findViewById(R.id.yadavvarappbar);
        setSupportActionBar(toolbar);
        TextView txttoolbar=(TextView)findViewById(R.id.txttoolbar);
        txttoolbar.setText("یادآور ها");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        CardView cvdoctorreminder = (CardView)findViewById(R.id.cvdoctorvisit);
        cvdoctorreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(yadaavar.this,nobat.class);
                startActivity(intent);
            }
        });

        CardView cvghanreminder = (CardView)findViewById(R.id.cvghanreminder);
        cvghanreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(yadaavar.this,alarm.class);
                startActivity(intent);
            }
        });


     /*   Button btndoctorreminder = (Button)findViewById(R.id.doctorreminder);
        btndoctorreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(yadaavar.this,nobat.class);
                startActivity(intent);
            }
        });

        Button btnghandreminder = (Button)findViewById(R.id.ghandreminder);
        btnghandreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(yadaavar.this,alarm.class);
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
