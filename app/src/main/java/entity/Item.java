package entity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import database.DatabaseInterface;
import database.Queryable;
import database.columns.FoodColumns;


public class Item implements Queryable {

	

	String category;
	String name;
	String unit;
	Parameters parameters;
	int frequency;

	public Item(String name, int carbohydrate, int calorie, int protein, int fat) {
		this.parameters = new Parameters(carbohydrate, calorie, protein, fat);
		this.name=name;

	}

	public Item(String category, String name, String unit, int carbohydrate,
			int calorie, int protein, int fat, int frequency) {
		this.category = category;
		this.name = name;
		this.unit = unit;
		this.parameters = new Parameters(carbohydrate, calorie, protein, fat);
		this.frequency = frequency;
	}


	public Item(String[] input) {
		this(input[0], input[1], input[2], Integer.parseInt(input[3]), Integer
				.parseInt(input[4]), Integer.parseInt(input[5]), Integer
				.parseInt(input[6]), Integer.parseInt(input[7]));
	}

	public Parameters getParams() {
		return parameters;
	}

	@Override
	public boolean insert(SQLiteDatabase mDb) {
		DatabaseInterface.insert(mDb, "Food", FoodColumns.values(),
				getFieldsValue());
		return false;
	}

	@Override
	public int update(SQLiteDatabase mDb) {
		ContentValues cv = new ContentValues();
		cv.put(FoodColumns.Frequency.getString(), ++frequency);
		DatabaseInterface.update(mDb, "Food", cv,
				FoodColumns.Name.getString() + "='" + name + "'", null);
		return 0;
	}

	@Override
	public int delete(SQLiteDatabase mDb) {
		return 0;
	}

	protected Object[] getFieldsValue() {
		return new Object[] { category, name, unit,
				parameters.getCarbohydrate(), parameters.getCalorie(),
				parameters.getProtein(), parameters.getFat(), frequency };
	}
	
	public String getCategory() {
		return category;
	}

	public String getName() {
		return name;
	}

	public String getUnit() {
		return unit;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public int getFrequency() {
		return frequency;
	}
}
