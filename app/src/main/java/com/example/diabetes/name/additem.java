package com.example.diabetes.name;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.diabetes.name.adapter.adapter_mental_ViewPager;
import com.example.diabetes.name.base.BaseActivity2;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.content.ContentValues.TAG;

public class additem extends BaseActivity2 {
    private Toolbar toolbar;
    private Spinner spinner_nav;
    FragmentManager fm;
    FragmentTransaction ft;
    Add_blood_pressure frag2;
    Add_glucose frag1;
    Add_ureh frag3;
    Add_cratine frag4;
    Add_ivancy frag5;
    Add_fat frag6;
    Add_weight frag7;
    SlidingTabLayout tabLayout_1;
    ViewPager pager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        Log.d(TAG, "attachBaseContext");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        

        toolbar=(Toolbar)findViewById(R.id.addappbar);
        setSupportActionBar(toolbar);
        TextView txttoolbar=(TextView)findViewById(R.id.txttoolbar);
        txttoolbar.setText("افزودن موارد");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.anim_notmain2, R.anim.anim_notmain1);


        tabLayout_1 = (SlidingTabLayout) findViewById(R.id.tl_1);
        pager = (ViewPager) findViewById(R.id.vp);

        reload();
    }
    public void reload() {

      //   final String[] mTitles = {"قند خون", "فشار خون", "وزن", "قند سه ماهه", "چربی", "اوره", "کراتین"};
        final String[] mTitles = {"کراتین","اوره","چربی","قند سه ماهه","وزن","فشار خون","قند خون"};
        mFragments.clear();
        mFragments.add(new Add_cratine());
        mFragments.add(new Add_ureh());
        mFragments.add(new Add_fat());
        mFragments.add(new Add_ivancy());
        mFragments.add(new Add_weight());
        mFragments.add(new Add_blood_pressure());
        mFragments.add(new Add_glucose());
        adapter_mental_ViewPager adapter = new adapter_mental_ViewPager(getSupportFragmentManager(), mFragments,mTitles
        );
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        tabLayout_1.setViewPager(pager);
        pager.setCurrentItem(6);
        // c.getFragmentManager().beginTransaction().detach().attach(c).commit();
    }
    public void addItemsToSpinner() {

        ArrayList<String> list = new ArrayList<String>();
        list.add("قند خون");
        list.add("فشار خون");
        list.add("وزن");
        list.add("قند سه ماهه");
        list.add("چربی");
        list.add("اوره");
        list.add("کراتین");


        // Custom ArrayAdapter with spinner item layout to set popup background

        Custom_up_SpinnerAdapter spinAdapter = new Custom_up_SpinnerAdapter(
                getApplicationContext(), list);
        spinner_nav.setAdapter(spinAdapter);

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
