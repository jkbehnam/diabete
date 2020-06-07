package com.example.diabetes.name;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import entity.Item;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;


public class AddFoodActivity extends AppCompatActivity {

    List<String> freqList;
    List<String> allFoods;
    List<String> recentList;
    Typeface face;
    Toast toast;
    String meal;
    Toolbar toolbar;
    LayoutInflater minflater;
    ViewGroup main;
    ProgressDialog progress;
    AlertDialog ald;
    AlertDialog.Builder builder;
    View alert_dialog_newitem;
    String User_Path = "/data/data/com.example.diabetes.name/diabete/";
    ArrayAdapter<String> adapter;
    RecyclerView rv;
    AutoCompleteTextView act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food);
        overridePendingTransition(R.anim.anim_notmain2, R.anim.anim_notmain1);
        toolbar = (Toolbar) findViewById(R.id.reportbar);
        setSupportActionBar(toolbar);
        TextView txttoolbar = (TextView) findViewById(R.id.txttoolbar);
        txttoolbar.setText(R.string.AddFood);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        main = (ViewGroup) findViewById(R.id.addList);
        face = Typeface.createFromAsset(getAssets(), "nazanin.TTF");

        minflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        meal = getIntent().getStringExtra("meal");
        (new LongOperation(this)).execute();

        progress = new ProgressDialog(this, R.style.MyTheme);
        progress.setCancelable(false);
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progress.show();


    }

    private void set3rdRow() {
        final TableRow row = new TableRow(this);
        final ViewGroup convertView = (ViewGroup) minflater.inflate(R.layout.add_food_3rd_row, row);
        TextView edit = (TextView) convertView.findViewById(R.id.textView1);
        edit.setTypeface(face);
        edit.setText(R.string.Eaten_Foods);
        ImageView img = (ImageView) convertView.findViewById(R.id.imageView1);
        img.setImageResource(R.drawable.choose);
        final View chooseLayout = convertView.findViewById(R.id.choose_layout);
        convertView.findViewById(R.id.header_container).setBackgroundColor(Color.parseColor("#22caca"));
        convertView.findViewById(R.id.header1).setBackgroundColor(Color.parseColor("#22caca"));
        chooseLayout.setBackgroundColor(Color.parseColor("#22caca"));
        convertView.findViewById(R.id.header1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (chooseLayout.getVisibility() == View.GONE) {
                    chooseLayout.setVisibility(View.VISIBLE);
                    convertView.findViewById(R.id.header_container).setBackgroundColor(Color.parseColor("#45ffff"));
                    view.setBackgroundColor(Color.parseColor("#45ffff"));
                } else {
                    chooseLayout.setVisibility(View.GONE);
                    convertView.findViewById(R.id.ll).setVisibility(View.GONE);
                    ((AutoCompleteTextView) convertView.findViewById(R.id.autoCompleteTextView1)).setText("");
                    convertView.findViewById(R.id.header_container).setBackgroundColor(Color.parseColor("#22caca"));
                    view.setBackgroundColor(Color.parseColor("#22caca"));
                }
            }
        });

        View choose = convertView.findViewById(R.id.choose);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                AddFoodActivity.this, android.R.layout.simple_list_item_1, allFoods);
        act = (AutoCompleteTextView) convertView.findViewById(R.id.autoCompleteTextView1);
        act.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    convertView.findViewById(R.id.ll).setVisibility(View.GONE);
            }
        });
        act.setAdapter(adapter);


        convertView.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aldshow("s");
            }
        });

        //-------------------------------------------------------------------
        choose.setOnClickListener(new MyView3Row(act, convertView));
        main.addView(row);
    }

    public void aldshow(final String s) {     //--------------------------------------------------------------------
        builder = new android.app.AlertDialog.Builder(this);
        ald = builder.create();

        alert_dialog_newitem = LayoutInflater.from(this).inflate(R.layout.alert_addfood, null);
        ald.setView(alert_dialog_newitem);
        ald.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rv = (RecyclerView) alert_dialog_newitem.findViewById(R.id.recycle_);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new ScaleInTopAnimator());
        rv.setNestedScrollingEnabled(false);
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(User_Path + "profile_info.db",
                null, null);
        Cursor cr;
        if (s.equals("s")) {
            cr = db.rawQuery("select distinct category from Food order by order_row", null);
        } else {
            cr = db.rawQuery("select  name from Food where category='" + s + "' ", null);
        }
        final ArrayList<data_intelligent> dv_list = new ArrayList<>();
        adapter_intelligent_recycle2 madapter = null;

        cr.moveToFirst();
        if (cr.getCount() != 0) {

            do {
                //  Toast.makeText(this, cr.getString(cr.getColumnIndex("title")), Toast.LENGTH_SHORT).show();
                data_intelligent dv = new data_intelligent();
                dv.setTitle(cr.getString(0));
                //  dv.setId(cr.getInt(cr.getColumnIndex("Id")));
                dv_list.add(dv);
            } while (cr.moveToNext());
            //   Toast.makeText(this, dv_list.get(2).getTitle(), Toast.LENGTH_SHORT).show();
            madapter = new adapter_intelligent_recycle2(dv_list);
            SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
            alphaAdapter.setFirstOnly(true);
            rv.setAdapter(alphaAdapter);

        }
        madapter.setOnCardClickListner(new adapter_intelligent_recycle2.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, String position) {

                Toast.makeText(getApplication(), position, Toast.LENGTH_SHORT).show();
                if (s.equals("s")) {
                    ald.cancel();
                    aldshow(position);
                } else {
                    act.setText(position);
                    ald.cancel();
                }

            }
        });

        ald.show();
    }


    private void setTextViews(List<String> data, int mystring, int mypic) {

        TableRow row = new TableRow(this);
        final View convertView = minflater.inflate(R.layout.add_food_table, row);
        final View header1 = convertView.findViewById(R.id.header1);
        final View tabfav = convertView.findViewById(R.id.tableFav);
        header1.setBackgroundColor(Color.parseColor("#22caca"));
        convertView.findViewById(R.id.header_container).setBackgroundColor(Color.parseColor("#22caca"));
        tabfav.setBackgroundColor(Color.parseColor("#22caca"));
        header1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tabfav.getVisibility() == View.VISIBLE) {
                    tabfav.setVisibility(View.GONE);
                    convertView.findViewById(R.id.ll).setVisibility(View.GONE);
                    resetCheckMarkes(convertView);
                    header1.setBackgroundColor(Color.parseColor("#22caca"));
                    convertView.findViewById(R.id.header_container).setBackgroundColor(Color.parseColor("#22caca"));
                } else {
                    tabfav.setVisibility(View.VISIBLE);
                    header1.setBackgroundColor(Color.parseColor("#45ffff"));
                    convertView.findViewById(R.id.header_container).setBackgroundColor(Color.parseColor("#45ffff"));
                }

            }
        });
        TextView edit = (TextView) convertView.findViewById(R.id.textView1);

        edit.setTypeface(face);
        edit.setText(mystring);
        ImageView img = (ImageView) convertView.findViewById(R.id.imageView1);
        img.setImageResource(mypic);
        ViewGroup tvq;
        tvq = (ViewGroup) convertView.findViewById(R.id.l3);
        tvq.setOnClickListener(new MYView(convertView));
        ((TextView) tvq.getChildAt(0)).setText(data.get(0));
        ((TextView) tvq.getChildAt(0)).setTypeface(face);

        tvq = (ViewGroup) convertView.findViewById(R.id.l2);
        tvq.setOnClickListener(new MYView(convertView));
        ((TextView) tvq.getChildAt(0)).setText(data.get(1));
        ((TextView) tvq.getChildAt(0)).setTypeface(face);


        tvq = (ViewGroup) convertView.findViewById(R.id.l1);
        tvq.setOnClickListener(new MYView(convertView));
        ((TextView) tvq.getChildAt(0)).setText(data.get(2));
        ((TextView) tvq.getChildAt(0)).setTypeface(face);

        tvq = (ViewGroup) convertView.findViewById(R.id.l6);
        tvq.setOnClickListener(new MYView(convertView));
        ((TextView) tvq.getChildAt(0)).setText(data.get(3));
        ((TextView) tvq.getChildAt(0)).setTypeface(face);

        tvq = (ViewGroup) convertView.findViewById(R.id.l5);
        tvq.setOnClickListener(new MYView(convertView));
        ((TextView) tvq.getChildAt(0)).setText(data.get(4));
        ((TextView) tvq.getChildAt(0)).setTypeface(face);

        tvq = (ViewGroup) convertView.findViewById(R.id.l4);
        tvq.setOnClickListener(new MYView(convertView));
        ((TextView) tvq.getChildAt(0)).setText(data.get(5));
        ((TextView) tvq.getChildAt(0)).setTypeface(face);

        tvq = (ViewGroup) convertView.findViewById(R.id.l9);
        tvq.setOnClickListener(new MYView(convertView));
        ((TextView) tvq.getChildAt(0)).setText(data.get(6));
        ((TextView) tvq.getChildAt(0)).setTypeface(face);

        tvq = (ViewGroup) convertView.findViewById(R.id.l8);
        tvq.setOnClickListener(new MYView(convertView));
        ((TextView) tvq.getChildAt(0)).setText(data.get(7));
        ((TextView) tvq.getChildAt(0)).setTypeface(face);

        tvq = (ViewGroup) convertView.findViewById(R.id.l7);
        tvq.setOnClickListener(new MYView(convertView));
        ((TextView) tvq.getChildAt(0)).setText(data.get(8));
        ((TextView) tvq.getChildAt(0)).setTypeface(face);


        main.addView(row);

    }

    private void resetCheckMarkes(View convertView) {
        ImageView iv = (ImageView) convertView.findViewById(R.id.check1);
        iv.setVisibility(View.INVISIBLE);

        iv = (ImageView) convertView.findViewById(R.id.check1);
        iv.setVisibility(View.INVISIBLE);

        iv = (ImageView) convertView.findViewById(R.id.check2);
        iv.setVisibility(View.INVISIBLE);

        iv = (ImageView) convertView.findViewById(R.id.check3);
        iv.setVisibility(View.INVISIBLE);

        iv = (ImageView) convertView.findViewById(R.id.check4);
        iv.setVisibility(View.INVISIBLE);

        iv = (ImageView) convertView.findViewById(R.id.check5);
        iv.setVisibility(View.INVISIBLE);

        iv = (ImageView) convertView.findViewById(R.id.check6);
        iv.setVisibility(View.INVISIBLE);

        iv = (ImageView) convertView.findViewById(R.id.check7);
        iv.setVisibility(View.INVISIBLE);

        iv = (ImageView) convertView.findViewById(R.id.check8);
        iv.setVisibility(View.INVISIBLE);

        iv = (ImageView) convertView.findViewById(R.id.check9);
        iv.setVisibility(View.INVISIBLE);
    }


    class MYView implements View.OnClickListener {

        View convertView;

        public MYView(View convertView) {
            this.convertView = convertView;
        }

        @Override
        public void onClick(View v) {
            ViewGroup vg = (ViewGroup) v;
            if (((TextView) vg.getChildAt(0)).getText().toString()
                    .compareTo("") != 0) {
                final Item item = Controller.getSelectedItem(
                        ((TextView) vg.getChildAt(0)).getText().toString(),
                        AddFoodActivity.this);

                convertView.findViewById(R.id.ll).setVisibility(View.VISIBLE);

                resetCheckMarkes(convertView);
                vg.getChildAt(1).setVisibility(View.VISIBLE);
                vg.getChildAt(1);
                TextView unit = (TextView) convertView.findViewById(R.id.unit);
                String htmlString = "<u>" + item.getUnit() + "</u>";
                unit.setText(Html.fromHtml(htmlString));
                unit.setTypeface(face);

                TextView calorie = (TextView) convertView.findViewById(R.id.caloriev);
                calorie.setText(String.valueOf(item.getParameters()
                        .getCalorie())+" کیلو کالری");
                calorie.setTypeface(face);


               TextView carbohydrate = (TextView) convertView.findViewById(R.id.carbohydratev);
                carbohydrate.setText(String.valueOf(item.getParameters()
                        .getCarbohydrate())+" گرم");
                carbohydrate.setTypeface(face);

                TextView fat = (TextView) convertView.findViewById(R.id.fat);
                fat.setText(String.valueOf(item.getParameters().getFat())+" گرم");
                fat.setTypeface(face);

                TextView protein = (TextView) convertView.findViewById(R.id.proteinv);
                protein.setText(String.valueOf(item.getParameters()
                        .getProtein())+" گرم");
                protein.setTypeface(face);

                EditText editText = (EditText) convertView.findViewById(R.id.editText1);
                editText.setTypeface(face);

                View donwrite = convertView.findViewById(R.id.dont_write);
                donwrite.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        convertView.findViewById(R.id.ll).setVisibility(
                                View.GONE);
                        convertView.findViewById(R.id.tableFav).setVisibility(View.GONE);
                        resetCheckMarkes(convertView);
                        EditText editText = (EditText) convertView.findViewById(R.id.editText1);
                        editText.setText("");
                        convertView.findViewById(R.id.header_container).setBackgroundColor(Color.parseColor("#22caca"));
                        convertView.findViewById(R.id.header1).setBackgroundColor(Color.parseColor("#22caca"));

                    }
                });

                View write = convertView.findViewById(R.id.write);
                write.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            int amount = Integer
                                    .parseInt(((EditText) convertView.findViewById(R.id.editText1))
                                            .getText().toString());
                            boolean inserted = Controller.addRecentItem(
                                    meal,
                                    item.getName(), amount, AddFoodActivity.this);
                            if (!inserted)
                                throw new SQLiteConstraintException();
                            EditText editText = (EditText) convertView.findViewById(R.id.editText1);
                            editText.setText("");
                            toast = Toast.makeText(AddFoodActivity.this,
                                    AddFoodActivity.this.getString(R.string.FoodAdded),
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            convertView.findViewById(R.id.ll).setVisibility(View.GONE);
                            convertView.findViewById(R.id.tableFav).setVisibility(View.GONE);

                        } catch (SQLiteConstraintException e) {
                            toast = Toast.makeText(
                                    AddFoodActivity.this,
                                    item.getName()
                                            + " "
                                            + AddFoodActivity.this
                                            .getString(R.string.AddedBefore),
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            convertView.findViewById(R.id.ll).setVisibility(View.GONE);
                        } catch (NumberFormatException e) {
                            toast = Toast.makeText(AddFoodActivity.this,
                                    AddFoodActivity.this.getString(R.string.EnterAmount),
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        convertView.findViewById(R.id.tableFav).setVisibility(View.GONE);

                        resetCheckMarkes(convertView);
                        convertView.findViewById(R.id.header_container).setBackgroundColor(Color.parseColor("#22caca"));
                        convertView.findViewById(R.id.header1).setBackgroundColor(Color.parseColor("#22caca"));
                        EditText editText = (EditText) convertView.findViewById(R.id.editText1);
                        editText.setText("");
                    }

                });
            }


        }


    }

    class MyView3Row implements View.OnClickListener {
        AutoCompleteTextView act;
        ViewGroup convertview;

        public MyView3Row(AutoCompleteTextView act, ViewGroup convertview) {
            this.act = act;
            this.convertview = convertview;
        }

        @Override
        public void onClick(View v) {
            View temp = convertview.findViewById(R.id.ll);
            ((EditText) convertview.findViewById(R.id.editText1)).requestFocus();
            EditText editText = (EditText) convertview.findViewById(R.id.editText1);
            editText.setTypeface(face);
            act = (AutoCompleteTextView) convertview.findViewById(R.id.autoCompleteTextView1);
            try {
                final Item item = Controller.getSelectedItem(act.getText()
                        .toString(), AddFoodActivity.this);
                temp.setVisibility(View.VISIBLE);
                TextView unit = (TextView) convertview.findViewById(R.id.unit);
                String htmlString = "<u>" + item.getUnit() + "</u>";
                unit.setText(Html.fromHtml(htmlString));

                TextView calorie = (TextView) convertview.findViewById(R.id.caloriev);
                calorie.setTypeface(face);
                calorie.setText(String.valueOf(item.getParameters()
                        .getCalorie())+" کیلو کالری");

                TextView carbohydrate = (TextView) convertview.findViewById(R.id.carbohydratev);
                carbohydrate.setTypeface(face);
                carbohydrate.setText(String.valueOf(item.getParameters()
                        .getCarbohydrate())+" گرم");

                TextView fat = (TextView) convertview.findViewById(R.id.fat);
                fat.setTypeface(face);
                fat.setText(String.valueOf(item.getParameters().getFat())+" گرم");

                TextView protein = (TextView) convertview.findViewById(R.id.proteinv);
                protein.setTypeface(face);
                protein.setText(String.valueOf(item.getParameters()
                        .getProtein())+" گرم");

                View donwrite = convertview.findViewById(R.id.dont_write);
                donwrite.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        convertview.findViewById(R.id.ll).setVisibility(
                                View.GONE);
                        convertview.findViewById(R.id.choose_layout).setVisibility(View.GONE);
                        act.setText("");
                        EditText editText = (EditText) convertview.findViewById(R.id.editText1);
                        editText.setText("");
                        convertview.findViewById(R.id.header_container).setBackgroundColor(Color.parseColor("#22caca"));
                        convertview.findViewById(R.id.header1).setBackgroundColor(Color.parseColor("#22caca"));
                    }
                });

                View write = convertview.findViewById(R.id.write);
                write.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            int amount = Integer
                                    .parseInt(((EditText) convertview.findViewById(R.id.editText1))
                                            .getText().toString());
                            boolean inserted = Controller.addRecentItem(
                                    meal,
                                    item.getName(), amount, AddFoodActivity.this);
                            if (!inserted)
                                throw new SQLiteConstraintException();
                            toast = Toast.makeText(AddFoodActivity.this,
                                    AddFoodActivity.this.getString(R.string.FoodAdded),
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            convertview.findViewById(R.id.ll).setVisibility(View.GONE);
                        } catch (SQLiteConstraintException e) {
                            toast = Toast.makeText(
                                    AddFoodActivity.this,
                                    item.getName()
                                            + " "
                                            + AddFoodActivity.this
                                            .getString(R.string.AddedBefore),
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            convertview.findViewById(R.id.ll).setVisibility(View.GONE);
                        } catch (NumberFormatException e) {
                            toast = Toast.makeText(AddFoodActivity.this,
                                    AddFoodActivity.this.getString(R.string.EnterAmount),
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        convertview.findViewById(R.id.ll).setVisibility(
                                View.GONE);
                        convertview.findViewById(R.id.choose_layout).setVisibility(View.GONE);
                        act.setText("");
                        EditText editText = (EditText) convertview.findViewById(R.id.editText1);
                        editText.setText("");
                        convertview.findViewById(R.id.header_container).setBackgroundColor(Color.parseColor("#22caca"));
                        convertview.findViewById(R.id.header1).setBackgroundColor(Color.parseColor("#22caca"));

                    }
                });
            } catch (IndexOutOfBoundsException e) {
                toast = Toast.makeText(AddFoodActivity.this,
                        AddFoodActivity.this.getString(R.string.FoodNotFound),
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                convertview.findViewById(R.id.header_container).setBackgroundColor(Color.parseColor("#22caca"));
                convertview.findViewById(R.id.header1).setBackgroundColor(Color.parseColor("#22caca"));
            }

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddFoodActivity.this, TableView.class));
        finish();
    }

    private class LongOperation extends AsyncTask<Void, Void, Void> {

        Context context;

        public LongOperation(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            freqList = Controller.getFrequentItems(context);
            allFoods = Controller.getAllfoods(context);
            recentList = Controller.getRecentItems(context);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            setTextViews(freqList, R.string.List_Of_Favorite_Foods, R.drawable.favorite);
            setTextViews(recentList, R.string.List_Of_Recent_Foods, R.drawable.recent);
            set3rdRow();
            progress.dismiss();
        }

    }
}
