package com.example.diabetes.name;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import controller.Controller;
import entity.ConsumedItem;
import entity.ParameterDay;
import entity.Parameters;


public class TableView extends AppCompatActivity {

	LayoutInflater minflater;
	List<ConsumedItem>[] cons;
	List<ParameterDay> pd;
	Parameters[] totalToday;
	Typeface face;
	Toolbar toolbar;
	String[][] colors;
	String[][] colorsTotal;
	Toast toast;
	ProgressDialog progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		(new LongOperation(this)).execute();



		///Colors of rows and colors
		colors=new String[][]{{"#fff4b4","#e5dba2"},{"#e5e5e5","#e5e5e5"},
				{"#ecba51","#d4a749"},{"#e5e5e5","#e5e5e5"},{"#ff8772","#e57966"},{"#e5e5e5","#e5e5e5"}};
		colorsTotal=new String[][]{{"#ffffff","#e5e5e5"},{"#d3f4f4","#bddbdb"}};

        progress = new ProgressDialog(this,R.style.MyTheme);
        progress.setCancelable(false);
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progress.show();
	}

	///////////////////Header Column//////////////////////////////
	private void setConsumeItemsHeader() {
		minflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout data= (LinearLayout)findViewById(R.id.tostoredata);
		TableRow inside2Row = new TableRow(this);
		setHeaderColumns(inside2Row);

		TableRow mainRow = new TableRow(this);
		View insideRow = minflater.inflate(R.layout.report_meal, mainRow);
		TableLayout inside2Table = (TableLayout) insideRow
				.findViewById(R.id.insidetable);

		inside2Table.addView(inside2Row);

		TextView meal = (TextView) insideRow.findViewById(R.id.meal);

		meal.setBackgroundColor(Color.parseColor("#1fb5b5"));
		meal.setTypeface(face);
        meal.getLayoutParams().width= ViewGroup.LayoutParams.MATCH_PARENT;
        meal.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
		meal.setText(getString(R.string.Meal));

		// final add
		data.addView(mainRow);
	}

	private void setHeaderColumns(TableRow inside2Row) {
		View embeddedRow = minflater.inflate(R.layout.report_consumed_header_row, inside2Row);

		TextView tv1 = (TextView) embeddedRow.findViewById(R.id.textView1);
		tv1.setTypeface(face);
        tv1.setBackgroundColor(Color.parseColor("#1fb5b5"));
		tv1.setText(getString(R.string.Fat1));

		TextView tv2 = (TextView) embeddedRow.findViewById(R.id.textView2);
		tv2.setTypeface(face);
        tv2.setBackgroundColor(Color.parseColor("#22caca"));
		tv2.setText(getString(R.string.Protein1));

		TextView tv3 = (TextView) embeddedRow.findViewById(R.id.textView3);
		tv3.setTypeface(face);
		tv3.setText(getString(R.string.Carbohydrate1));
        tv3.setBackgroundColor(Color.parseColor("#1fb5b5"));

		TextView tv4 = (TextView) embeddedRow.findViewById(R.id.textView4);
		tv4.setTypeface(face);
        tv4.setBackgroundColor(Color.parseColor("#22caca"));
		tv4.setText(getString(R.string.Calorie1));

		TextView tv5 = (TextView) embeddedRow.findViewById(R.id.textView5);
		tv5.setTypeface(face);
		tv5.setText(getString(R.string.Quantity1));
        tv5.setBackgroundColor(Color.parseColor("#1fb5b5"));

		TextView tv6 = (TextView) embeddedRow.findViewById(R.id.textView6);
		tv6.setTypeface(face);
        tv6.setBackgroundColor(Color.parseColor("#22caca"));
		tv6.setText(getString(R.string.FoodAName));

	}

	//////////////////Consumed Rows//////////////////////////
	private void setConsumeItemsRows() {
		minflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout data= (LinearLayout)findViewById(R.id.tostoredata);
		for (int i = 0; i < cons.length; i++) {
			TableRow mainRow = new TableRow(this);
			View insideRow = minflater.inflate(R.layout.report_meal, mainRow);
			for (int j = 0; j < cons[i].size(); j++) {
				TableRow inside2Row = new TableRow(this);
				setConsumedRows(inside2Row, cons[i].get(j), colors[i]);
				TableLayout inside2Table = (TableLayout) insideRow
						.findViewById(R.id.insidetable);
				inside2Table.addView(inside2Row);
			}

			TextView meal = (TextView) insideRow.findViewById(R.id.meal);

			meal.setTypeface(face);
			meal.setText(getResources().getStringArray(R.array.array_name)[i]);

			View meallay = insideRow.findViewById(R.id.meal_layout);
			if (i % 2 == 0)
				meallay.setBackgroundColor(Color.parseColor("#ffffff"));
			else
				meallay.setBackgroundColor(Color.parseColor("#e5e5e5"));
			insideRow.findViewById(R.id.add_layout).setVisibility(View.VISIBLE);
			((TextView)insideRow.findViewById(R.id.textView97)).setTypeface(face);
			meallay.getLayoutParams().height = Math.max(
					meallay.getLayoutParams().height * (cons[i].size()),
					meallay.getLayoutParams().height);

			meallay.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					ViewGroup mainRow=(ViewGroup)view.getParent().getParent();
					int index=((ViewGroup)mainRow.getParent()).indexOfChild(mainRow)-1;
					Intent intent = new Intent(TableView.this, AddFoodActivity.class);
					String meal=getResources().getStringArray(R.array.array_name)[index];
					intent.putExtra("meal", meal);
					startActivity(intent);

				}
			});

			// final add
			data.addView(mainRow);
		}
	}



	private void setConsumedRows(TableRow inside2Row, final ConsumedItem ci,
			final String[] color) {
		final View embeddedRow = minflater.inflate(R.layout.report_consumed_item_row, inside2Row);

		TextView tv1 = (TextView) embeddedRow.findViewById(R.id.textView1);
		tv1.setTypeface(face);
		tv1.setText(String.valueOf(ci.getParams().getFat()));
		tv1.setBackgroundColor(Color.parseColor(color[1]));

		TextView tv2 = (TextView) embeddedRow.findViewById(R.id.textView2);
		tv2.setTypeface(face);
		tv2.setText(String.valueOf(ci.getParams().getProtein()));
		tv2.setBackgroundColor(Color.parseColor(color[0]));

		TextView tv3 = (TextView) embeddedRow.findViewById(R.id.textView3);
		tv3.setTypeface(face);
		tv3.setText(String.valueOf(ci.getParams().getCarbohydrate()));
		tv3.setBackgroundColor(Color.parseColor(color[1]));

		TextView tv4 = (TextView) embeddedRow.findViewById(R.id.textView4);
		tv4.setTypeface(face);
		tv4.setText(String.valueOf(ci.getParams().getCalorie()));
		tv4.setBackgroundColor(Color.parseColor(color[0]));

		TextView tv5 = (TextView) embeddedRow.findViewById(R.id.textView5);
		tv5.setTypeface(face);
		tv5.setText(String.valueOf(ci.getAmount()));
		tv5.setBackgroundColor(Color.parseColor(color[1]));

		TextView tv6 = (TextView) embeddedRow.findViewById(R.id.textView6);
		tv6.setTypeface(face);
		tv6.setText(ci.getName());
		tv6.setBackgroundColor(Color.parseColor(color[0]));

		ImageButton settings=(ImageButton) embeddedRow.findViewById(R.id.imageButton2);
		((LinearLayout)settings.getParent()).setBackgroundColor(Color.parseColor(color[0]));

		settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final View popup = embeddedRow.findViewById(R.id.ww);
				popup.setVisibility(View.VISIBLE);
				//popup.setBackgroundColor(Color.parseColor(color[0]));
				ImageView set = (ImageView) embeddedRow.findViewById(R.id.settings);
				set.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						popup.setVisibility(View.GONE);
					}
				});
				View edit = embeddedRow.findViewById(R.id.editlayout);
				edit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						final Dialog d = new Dialog(TableView.this);
						d.setContentView(R.layout.dialog);
						((TextView) d.findViewById(R.id.food_name)).setText(ci.getName());
						final EditText et = (EditText) d.findViewById(R.id.edit_unit);
						et.setText(String.valueOf(ci.getAmount()));
						Button confirm = ((Button) d.findViewById(R.id.dialog_edit));
						confirm.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								try {
									int amount = Integer.parseInt(et.getText().toString());
									Controller.updateItem(ci, amount, TableView.this);
									d.dismiss();
									startActivity(new Intent(TableView.this, TableView.class));
									finish();
								} catch (NumberFormatException e) {
									et.setText("");
									toast = Toast.makeText(TableView.this,
											getString(R.string.EnterAmount),
											Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
								}

							}
						});

						Button dismiss = ((Button) d.findViewById(R.id.dialog_cancel));
						dismiss.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								d.dismiss();
							}
						});
						d.show();
						///////////
					}
				});
				View delete = embeddedRow.findViewById(R.id.deletelayout);

				delete.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Controller.deleteItem(ci, TableView.this);
						startActivity(new Intent(TableView.this, TableView.class));
						finish();
					}
				});

			}
		});

	}

	///////////////////Set total Value Rows
	private void setTotalValues() {
		minflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		final LinearLayout tohide=(LinearLayout)findViewById(R.id.tohide);
		TableRow headerRow= new TableRow(this);
		View headerRowLayout=minflater.inflate(R.layout.report_total_row,headerRow);
		setTotalHeader(headerRowLayout);
		tohide.addView(headerRow);
		for (int j = 0; j < 6; j++) {
			TableRow row= new TableRow(this);
			View layoutview=minflater.inflate(R.layout.report_total_row,row);

			setTotalInMeal(layoutview, totalToday[j], j);
			tohide.addView(row);
		}

		for (int j = 0; j < 3; j++) {
			TableRow row= new TableRow(this);
			View layoutview=minflater.inflate(R.layout.report_total_row,row);

			if (pd.size() > j)
				setTotalInDay(layoutview, pd.get(j), j);
			else
				setTotalInDay(layoutview, null, j);


			tohide.addView(row);
		}
		final View totalHeader=findViewById(R.id.total_header_row);
		totalHeader.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				View arrayDown=findViewById(R.id.array_down);
				View arrayLeft=findViewById(R.id.array_left);

				if(tohide.getVisibility()==View.GONE) {
					tohide.setVisibility(View.VISIBLE);
					arrayDown.setVisibility(View.VISIBLE);
					arrayLeft.setVisibility(View.INVISIBLE);
				}
				else {
					tohide.setVisibility(View.GONE);
					arrayDown.setVisibility(View.INVISIBLE);
					arrayLeft.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	private void setTotalInMeal(View inside2Row, Parameters pd, int b) {
		int color = Color.parseColor(colorsTotal[b%2][0]);
		int color1=Color.parseColor(colorsTotal[b%2][1]);

		TextView tv1 = (TextView) inside2Row.findViewById(R.id.textView2);
		if (pd != null)
			tv1.setText(String.valueOf(pd.getFat()));
		tv1.setBackgroundColor(color);
		tv1.setTypeface(face);

		TextView tv2 = (TextView) inside2Row.findViewById(R.id.textView3);
		if (pd != null)
			tv2.setText(String.valueOf(pd.getProtein()));
		tv2.setBackgroundColor(color1);
		tv2.setTypeface(face);

		TextView tv3 = (TextView) inside2Row.findViewById(R.id.textView4);
		if (pd != null)
			tv3.setText(String.valueOf(pd.getCarbohydrate()));
		tv3.setBackgroundColor(color);
		tv3.setTypeface(face);

		TextView tv4 = (TextView) inside2Row.findViewById(R.id.textView5);
		if (pd != null)
			tv4.setText(String.valueOf(pd.getCalorie()));
		tv4.setBackgroundColor(color1);
		tv4.setTypeface(face);

		TextView tv6 = (TextView) inside2Row.findViewById(R.id.textView6);
		tv6.setTypeface(face);
		tv6.setBackgroundColor(color);
		SpannableString content = new SpannableString(getResources().getStringArray(R.array.array_name)[b]);
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		tv6.setText(content);



		TextView tv7 = (TextView) inside2Row.findViewById(R.id.textView7);
		tv7.setTypeface(face);
		tv7.setText(getResources().getString(R.string.TotalSumOfFoods));
		tv7.setBackgroundColor(color);
	}

	private void setTotalInDay(View inside2Row, ParameterDay pd, int b) {

		int color = Color.parseColor(colorsTotal[b%2][0]);
		int color1=Color.parseColor(colorsTotal[b%2][1]);

		TextView tv1 = (TextView) inside2Row.findViewById(R.id.textView2);
		if (pd != null)
			tv1.setText(String.valueOf(pd.getParams().getFat()));
		tv1.setBackgroundColor(color);
		tv1.setTypeface(face);

		TextView tv2 = (TextView) inside2Row.findViewById(R.id.textView3);
		if (pd != null)
			tv2.setText(String.valueOf(pd.getParams().getProtein()));
		tv2.setBackgroundColor(color1);
		tv2.setTypeface(face);

		TextView tv3 = (TextView) inside2Row.findViewById(R.id.textView4);
		if (pd != null)
			tv3.setText(String.valueOf(pd.getParams().getCarbohydrate()));
		tv3.setBackgroundColor(color);
		tv3.setTypeface(face);

		TextView tv4 = (TextView) inside2Row.findViewById(R.id.textView5);
		if (pd != null)
			tv4.setText(String.valueOf(pd.getParams().getCalorie()));
		tv4.setBackgroundColor(color1);
		tv4.setTypeface(face);

		TextView tv6 = (TextView) inside2Row.findViewById(R.id.textView6);
		tv6.setTypeface(face);
		SpannableString content = new SpannableString(getResources().getStringArray(R.array.days)[b]);
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		tv6.setText(content);
		tv6.setBackgroundColor(color);

		TextView tv7 = (TextView) inside2Row.findViewById(R.id.textView7);
		tv7.setTypeface(face);
		tv7.setText(getResources().getString(R.string.TotalSumOfFoods));
		tv7.setBackgroundColor(color);
	}

	private void setTotalHeader(View inside2Row) {

		TextView tv1 = (TextView) inside2Row.findViewById(R.id.textView2);
		tv1.setBackgroundColor(Color.parseColor("#22caca"));
		tv1.setTypeface(face);
		tv1.setText(getString(R.string.Fat1));

		TextView tv2 = (TextView) inside2Row.findViewById(R.id.textView3);
		tv2.setTypeface(face);
        tv2.setBackgroundColor(Color.parseColor("#1fb5b5"));
		tv2.setText(getString(R.string.Protein1));

		TextView tv3 = (TextView) inside2Row.findViewById(R.id.textView4);
		tv3.setTypeface(face);
		tv3.setText(getString(R.string.Carbohydrate1));
		tv3.setBackgroundColor(Color.parseColor("#22caca"));

		TextView tv4 = (TextView) inside2Row.findViewById(R.id.textView5);
		tv4.setTypeface(face);
        tv2.setBackgroundColor(Color.parseColor("#1fb5b5"));
		tv4.setText(getString(R.string.Calorie1));

		TextView tv5 = (TextView) inside2Row.findViewById(R.id.textView6);
		tv5.setBackgroundColor(Color.parseColor("#22caca"));
		tv5.setTypeface(face);

		TextView tv6 = (TextView) inside2Row.findViewById(R.id.textView7);
		tv6.setBackgroundColor(Color.parseColor("#22caca"));
		tv6.setTypeface(face);



	}

	@Override
	public void onBackPressed() {
		//startActivity(new Intent(TableView.this,MainActivity.class));
		//finish();
		super.onBackPressed();
		overridePendingTransition( R.anim.anim_main2, R.anim.anim_main1);
	}

	private class LongOperation extends AsyncTask<Void, Void, Void> {

		Context context;
		public LongOperation(Context context)
		{
			this.context=context;
		}
		@Override
		protected Void doInBackground(Void... params) {
			Controller.createNewParameterDay(context);
			cons = Controller.getEatenItems(context);
			pd = Controller.getTotalReport(context);
			totalToday= Controller.computeTotalParamsInMeals(cons);
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			setContentView(R.layout.report_main);

            overridePendingTransition(R.anim.anim_notmain2, R.anim.anim_notmain1);
			toolbar = (Toolbar) findViewById(R.id.reportbar);
			setSupportActionBar(toolbar);
			TextView txttoolbar = (TextView) findViewById(R.id.txttoolbar);
			txttoolbar.setText(R.string.MyTodayReport);
			getSupportActionBar().setDisplayShowHomeEnabled(true);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            face = Typeface.createFromAsset(getAssets(), "nazanin.TTF");

			setConsumeItemsHeader();
			setConsumeItemsRows();
			setTotalValues();
			final HorizontalScrollView s= (HorizontalScrollView) findViewById(R.id.scroll_table);
			s.postDelayed(new Runnable() {
				public void run() {
					s.fullScroll(ScrollView.FOCUS_RIGHT);
				}
			}, 100L);
			progress.dismiss();
		}

	}

}
