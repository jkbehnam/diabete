package com.example.diabetes.name;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.diabetes.name.base.BaseActivity2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.content.ContentValues.TAG;

public class messagepage extends BaseActivity2 {
    TextView txtstatus;
    private Toolbar toolbar;
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    ArrayAdapter<String> adapter;
    RecyclerView rv;
    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        Log.d(TAG, "attachBaseContext");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagepage);

        //  txtstatus = (TextView) findViewById(R.id.txtstatus);
        overridePendingTransition(R.anim.anim_notmain2, R.anim.anim_notmain1);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView txttoolbar = (TextView) findViewById(R.id.txttoolbar);
        txttoolbar.setText("هشیار باش!!!");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//_____________________file checking______________________________________________________________________________________
        File f = new File(User_Path);
        if (!f.exists()) {
            try {
                f.mkdirs();
                f.createNewFile();
                InputStream in = this.getAssets().open("profile_db.db");

                OutputStream out = new FileOutputStream(User_Path + "profile_db.db");

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

//____________________________________________________________________________________________________
        rv = (RecyclerView) findViewById(R.id.recycle_);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);
       rv.setNestedScrollingEnabled(false);
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(User_Path + "profile_info.db",
                null, null);
        Cursor cr = db.rawQuery("select * from be_intelligent", null);
        final ArrayList<data_intelligent> dv_list = new ArrayList<>();
        adapter_intelligent_recycle madapter = null;
        ArrayAdapter<String> adapter;

        cr.moveToFirst();
        if (cr.getCount() != 0) {

            do {
              //  Toast.makeText(this, cr.getString(cr.getColumnIndex("title")), Toast.LENGTH_SHORT).show();
                data_intelligent dv = new data_intelligent();
                dv.setTitle(cr.getString(cr.getColumnIndex("title")));
                dv.setId(cr.getInt(cr.getColumnIndex("Id")));
                dv_list.add(dv);
            } while (cr.moveToNext());
         //   Toast.makeText(this, dv_list.get(2).getTitle(), Toast.LENGTH_SHORT).show();
            madapter = new adapter_intelligent_recycle(dv_list);

            SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);

            alphaAdapter.setFirstOnly(true);
            rv.setAdapter(alphaAdapter);

        }
        madapter.setOnCardClickListner(new adapter_intelligent_recycle.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {
                Intent intent = new Intent(messagepage.this, alarm_messages.class);
                intent.putExtra("id",position);
                startActivity(intent);
            }

        });
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
