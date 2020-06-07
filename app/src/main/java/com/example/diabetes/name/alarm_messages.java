package com.example.diabetes.name;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.diabetes.name.base.BaseActivity2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

public class alarm_messages extends BaseActivity2 {
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_messages);
        overridePendingTransition(R.anim.anim_notmain2, R.anim.anim_notmain1);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView txttoolbar = (TextView) findViewById(R.id.txttoolbar);
        txttoolbar.setText("هشیار باش!!!");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tv=(TextView)findViewById(R.id.tv);
        int id=0;
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
             id =b.getInt("id");

        }
        //Toast.makeText(this,String.valueOf(id),Toast.LENGTH_SHORT).show();
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
       SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(User_Path + "profile_info.db",
                null, null);
        Cursor cr = db.rawQuery("select * from be_intelligent where id='"+id+ "'", null);
        cr.moveToFirst();
        final ArrayList<data_alarm_message> dv_list = new ArrayList<>();
        if (cr.getCount() != 0) {
            String CurrentString = cr.getString(1);

            adapter_alarm_message_recycle madapter = null;
            RecyclerView rv;
            rv = (RecyclerView) findViewById(R.id.rv);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
           rv.setLayoutManager(mLayoutManager);
           rv.setItemAnimator(new FadeInLeftAnimator());
            rv.setNestedScrollingEnabled(false);
            String[] separated = CurrentString.split("&&&");
           // Toast.makeText(this,String.valueOf(separated.length),Toast.LENGTH_SHORT).show();
         //   tv.setText(Html.fromHtml(cr.getString(1)));
            for(int i=0;i<separated.length;i++){
                data_alarm_message dv = new data_alarm_message();
                dv.setMatn(separated[i]);
                dv_list.add(dv);
            }
            madapter = new adapter_alarm_message_recycle(dv_list);
            SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);

            alphaAdapter.setFirstOnly(true);
            rv.setAdapter(alphaAdapter);
        }


    }
}
