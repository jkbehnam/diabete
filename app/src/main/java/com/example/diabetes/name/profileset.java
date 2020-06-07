package com.example.diabetes.name;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class profileset extends AppCompatActivity {
    private Toolbar toolbar;
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    String sex, disease_family;
    RadioGroup rg1, rg2;
    Spinner d_type_sp, disease_sp, cure_sp;
    EditText fname, lname, bd_d, bd_m, bd_y, children, d_d, d_m, d_y, ncode;
    int firsttime = 0;
    Button btsave;
    CheckBox chb_bp,chb_charbi,chb_asm,chb_heart,chb_kidney;
    TextView tv_age;
    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        Log.d("", "attachBaseContext");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileset);

        toolbar = (Toolbar) findViewById(R.id.profilesetappbar);
        setSupportActionBar(toolbar);
        TextView txttoolbar = (TextView) findViewById(R.id.txttoolbar);
        txttoolbar.setText("اطلاعات فردی");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        overridePendingTransition(R.anim.anim_notmain2, R.anim.anim_notmain1);
        ncode = (EditText) findViewById(R.id.et_ncode);
        fname = (EditText) findViewById(R.id.firstname_et);
        lname = (EditText) findViewById(R.id.lastname_et);
        bd_d = (EditText) findViewById(R.id.bday_et);
        bd_m = (EditText) findViewById(R.id.bmonth_et);
        bd_y = (EditText) findViewById(R.id.byear_et);
        children = (EditText) findViewById(R.id.childeren_et);
        d_d = (EditText) findViewById(R.id.dday_et);
        d_m = (EditText) findViewById(R.id.dmonth_et);
        d_y = (EditText) findViewById(R.id.dyear_et);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_age.setVisibility(View.GONE);


//----------------------------------------------------------------
         chb_bp=(CheckBox)findViewById(R.id.chb_bp);
         chb_charbi=(CheckBox)findViewById(R.id.chb_charbi);
         chb_asm=(CheckBox)findViewById(R.id.chb_asm);
         chb_heart=(CheckBox)findViewById(R.id.chb_heart);
         chb_kidney=(CheckBox)findViewById(R.id.chb_kidney);


        //---------------------------------------------
        String[] d_type = {" ديابت نوع 1", "ديابت نوع 2", " ديابت حاملگي", " ديابت همراه با بيماريهاي ديگر"};
        d_type_sp = (Spinner) findViewById(R.id.DT_spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, d_type);
        adapter1.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        d_type_sp.setAdapter(adapter1);
        //-----cancel---------------------------------------

    /*    String[] disease = {"ندارم", "فشار خون", "قلب", "چربی خون", "آسم", "کلیه"};
        disease_sp = (Spinner) findViewById(R.id.DI_spinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, disease);
        adapter2.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        disease_sp.setAdapter(adapter2);
        */
//-----------------------------------------------
        String[] cure = {"انسولین", "قرص","رژیم غذایی"};
        cure_sp = (Spinner) findViewById(R.id.CT_spinner);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cure);
        adapter3.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        cure_sp.setAdapter(adapter3);
//-----------------------------------------------


        rg1 = (RadioGroup) findViewById(R.id.sex_rg);
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rdb = (RadioButton) findViewById(checkedId);
                sex = rdb.getText().toString();
            }
        });
//-----------------------------------------------------------
        rg2 = (RadioGroup) findViewById(R.id.FA_rg);
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rdb = (RadioButton) findViewById(checkedId);
                disease_family = rdb.getText().toString();
            }
        });
        //----------------------------------------------------------------
      //  disease_sp.setSelection(0);
        d_type_sp.setSelection(1);
        cure_sp.setSelection(0);
        File f = new File(User_Path);
        if (!f.exists()) {
            try {
                f.mkdirs();
                f.createNewFile();
                InputStream in = getAssets().open("profile_info.db");

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
        btsave = (Button) findViewById(R.id.info_save);
        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                        SQLiteDatabase.CREATE_IF_NECESSARY, null);
                ContentValues cv = new ContentValues();

                    cv.put("ncode", ncode.getText().toString());
                    cv.put("fname", fname.getText().toString());
                    cv.put("lname", lname.getText().toString());
                    if(!bd_d.getText().toString().equals("")&&!bd_m.getText().toString().equals("")&&!bd_y.getText().toString().equals(""))
                    {
                        int x1= Integer.parseInt(bd_d.getText().toString());
                        //month
                        int x2= Integer.parseInt(bd_m.getText().toString());
                        //year
                        int x3= Integer.parseInt(bd_y.getText().toString());
                        if(1>0&&x1<32&&x2>0&&x2<13&&x3>1300&&x3<1500)
                        {
                            cv.put("bd_d", bd_d.getText().toString());
                            cv.put("bd_m", bd_m.getText().toString());
                            cv.put("bd_y", bd_y.getText().toString());

                        }
                        else {
                            Toast.makeText(profileset.this,"تاریخ تولد وارد شده معتبر نمیباشد",Toast.LENGTH_SHORT).show();
                            cv.put("bd_d", "");
                            cv.put("bd_m", "");
                            cv.put("bd_y", "");}
                    }
                   else {

                        cv.put("bd_d", "");
                        cv.put("bd_m", "");
                        cv.put("bd_y", "");
                    }

                    cv.put("sex", sex);
                    cv.put("child_no", children.getText().toString());

                    if(!d_d.getText().toString().equals("")&&!d_m.getText().toString().equals("")&&!d_y.getText().toString().equals(""))
                    {
                        int x1= Integer.parseInt(d_d.getText().toString());
                        //month
                        int x2= Integer.parseInt(d_m.getText().toString());
                        //year
                        int x3= Integer.parseInt(d_y.getText().toString());
                        if(1>0&&x1<32&&x2>0&&x2<13&&x3>1300&&x3<1500)
                        {
                            cv.put("d_d", d_d.getText().toString());
                            cv.put("d_m", d_m.getText().toString());
                            cv.put("d_y", d_y.getText().toString());
                        }
                        else {
                            Toast.makeText(profileset.this,"تاریخ تشخیص وارد شده معتبر نمیباشد",Toast.LENGTH_SHORT).show();
                            cv.put("d_d", "");
                            cv.put("d_m", "");
                            cv.put("d_y", "");
                        }
                    }
                    else {

                        cv.put("d_d", "");
                        cv.put("d_m", "");
                        cv.put("d_y", "");
                    }


                    cv.put("f_a", disease_family);
                    cv.put("d_type", d_type_sp.getSelectedItem().toString());

                    //------disease check box----------------------------------
                    int x=0;
                    if (chb_bp.isChecked()){x+=10000;}
                    if (chb_charbi.isChecked()){x+=1000;}
                    if (chb_asm.isChecked()){x+=100;}
                    if (chb_heart.isChecked()){x+=10;}
                    if (chb_kidney.isChecked()){x+=1;}
                    //-----------------------------------------
                    cv.put("disease", x);
                    cv.put("cure_t", cure_sp.getSelectedItem().toString());
                    // cv.put("phone_1", phone1.getText().toString());
                    // cv.put("phone_2", phone2.getText().toString());
                    if (firsttime == 0) {
                        db.insert("profile_info1", null, cv);
                    } else {
                        db.update("profile_info1", cv, null, null);
                    }
                    db.close();
                    Toast.makeText(profileset.this, "ثبت با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    finish();


            }
        });
        ShowData_3();
    }

    public void ShowData_3() {
        SQLiteDatabase db = openOrCreateDatabase(User_Path + "profile_info.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        Cursor cr = db.rawQuery("select * from profile_info1", null);

        cr.moveToFirst();
        if (cr.getCount() != 0) {
            firsttime = 1;
            //  btsave.setText("ویرایش");
            if (!(cr.isNull(0)))
                fname.setText(cr.getString(cr.getColumnIndex("fname")));
            if (!(cr.isNull(1)))
                lname.setText(cr.getString(cr.getColumnIndex("lname")));
            if (!(cr.isNull(14)))
                ncode.setText(cr.getString(cr.getColumnIndex("ncode")));


            if (!(cr.isNull(4))&&!(cr.isNull(3))&&!(cr.isNull(2))){
                bd_d.setText(cr.getString(cr.getColumnIndex("bd_d")));
                bd_m.setText(cr.getString(cr.getColumnIndex("bd_m")));
                bd_y.setText(cr.getString(cr.getColumnIndex("bd_y")));
                if(!cr.getString(cr.getColumnIndex("bd_d")).equals("")&&!cr.getString(cr.getColumnIndex("bd_m")).equals("")&&!cr.getString(cr.getColumnIndex("bd_d")).equals("")){
                JalaliCalendar.YearMonthDate dd3;
                JalaliCalendar dateandtime = null;
                dd3 = new JalaliCalendar.YearMonthDate(Integer.parseInt( cr.getString(cr.getColumnIndex("bd_y"))), Integer.parseInt( cr.getString(cr.getColumnIndex("bd_m")) ), Integer.parseInt( cr.getString(cr.getColumnIndex("bd_d"))));
                dd3 = dateandtime.jalaliToGregorian(dd3);

                tv_age.setVisibility(View.VISIBLE);
                tv_age.setText("سن :"+getAge(dd3.getYear(), dd3.getMonth(), dd3.getDate())+" سال");}}
            if (!(cr.isNull(6)))
                children.setText(cr.getString(cr.getColumnIndex("child_No")));
            if (!(cr.isNull(7)))
                d_d.setText(cr.getString(cr.getColumnIndex("d_d")));
            if (!(cr.isNull(8)))
                d_m.setText(cr.getString(cr.getColumnIndex("d_m")));
            if (!(cr.isNull(9)))
                d_y.setText(cr.getString(cr.getColumnIndex("d_y")));


            if (!(cr.isNull(5))) {
                if (cr.getString(cr.getColumnIndex("sex")).equals("مرد"))
                    rg1.check(R.id.male_rb);
                else rg1.check(R.id.female_rb);
            }
            if (!(cr.isNull(10))) {
                if (cr.getString(cr.getColumnIndex("f_a")).equals("بله"))
                    rg2.check(R.id.FA_yes_rb);
                else rg2.check(R.id.FA_no_rb);
            }
            if (!(cr.isNull(11))) {
                switch (cr.getString(cr.getColumnIndex("d_type"))) {
                    case " ديابت نوع 1":
                        d_type_sp.setSelection(0);
                        break;
                    case "ديابت نوع 2":
                        d_type_sp.setSelection(1);
                        break;
                    case " ديابت حاملگي":
                        d_type_sp.setSelection(2);
                        break;
                    case " ديابت همراه با بيماريهاي ديگر":
                        d_type_sp.setSelection(3);
                        break;


                }
            }
          String[] disease = {"ندارم", "فشار خون", "قلب", "چربی خون", "آسم", "کلیه"};

            if (!(cr.isNull(12))) {
              /*    switch (cr.getInt(cr.getColumnIndex("disease"))) {
                    case "ندارم":
                        disease_sp.setSelection(0);
                        break;
                    case "فشار خون":
                        disease_sp.setSelection(1);
                        break;
                    case "قلب":
                        disease_sp.setSelection(1);
                        break;
                    case "چربی خون":
                        disease_sp.setSelection(1);
                        break;
                    case "آسم":
                        disease_sp.setSelection(1);
                        break;
                    case "کلیه":
                        disease_sp.setSelection(1);
                        break;
                }
*/
            int x = cr.getInt(cr.getColumnIndex("disease"));
            if (x / 10000 >= 1) {
                chb_bp.setChecked(true);
                x %= 10000;
            }
            if (x / 1000 >= 1) {
                chb_charbi.setChecked(true);
                x %= 1000;
            }
            if (x / 100 >= 1) {
                chb_asm.setChecked(true);
                x %= 100;
            }
            if (x / 10 >= 1) {
                chb_heart.setChecked(true);
                x %= 10;
            }
            if (x / 1 >= 1) {
                chb_kidney.setChecked(true);
            }
        }
        if (!(cr.isNull(13))) {
            switch (cr.getString(cr.getColumnIndex("cure_t"))) {
                case "رژیم غذایی":
                    cure_sp.setSelection(2);
                    break;
                case "قرص":
                    cure_sp.setSelection(1);
                    break;
                case "انسولین":
                    cure_sp.setSelection(0);
                    break;
            }


        }
    }

        db.close();
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
        overridePendingTransition( R.anim.anim_main2, R.anim.anim_main1);
    }
    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}
