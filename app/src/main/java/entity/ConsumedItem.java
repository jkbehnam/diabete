package entity;

import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import database.DatabaseInterface;
import database.columns.ConsumedItemColumns;

public class ConsumedItem extends Item {

	String date;
	String meal;
	int amount;


	public ConsumedItem(String date, String meal, int carbohydrate,
			int calorie, int protein, int fat) {
		super(new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date()),
				carbohydrate, calorie, protein, fat);
		this.date = date;
		this.meal = meal;
	}

	public ConsumedItem(String[] input) {
		super("", input[2], "", Integer.parseInt(input[4]), Integer
				.parseInt(input[5]), Integer.parseInt(input[6]), Integer
				.parseInt(input[7]), 0);
		this.amount = Integer.parseInt(input[3]);
		this.date = input[0];
		this.meal = input[1];
	}

	public ConsumedItem(String date, String meal, int amount, String[] input) {
		super(input[0], input[1], input[2],
				amount * Integer.parseInt(input[3]), amount
						* Integer.parseInt(input[4]), amount
						* Integer.parseInt(input[5]), amount
						* Integer.parseInt(input[6]), Integer
						.parseInt(input[7]));
		this.date = date;
		this.meal = meal;
		this.amount = amount;

	}

	@Override
	public boolean insert(SQLiteDatabase mDb) {
		return DatabaseInterface.insert(mDb, "Consumed_Item",
				ConsumedItemColumns.values(), getFieldsValue());
	}

	@Override
	public int delete(SQLiteDatabase mDb) {
		return DatabaseInterface.delete(mDb, "Consumed_Item", "date='" + date
						+ "' and meal='" + meal + "' and item_name='" + name + "'",
				null);
	}

	protected Object[] getFieldsValue() {
		return new Object[] { date, meal, name, amount,
				parameters.getCarbohydrate(), parameters.getCalorie(),
				parameters.getProtein(), parameters.getFat() };
	}

	public String getDate() {
		return date;
	}

	public String getMeal() {
		return meal;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int newAmount)
	{
		if(amount!=0)
		{
			parameters=new Parameters(newAmount*parameters.getCarbohydrate()/this.amount,newAmount*parameters.getCalorie()/this.amount,newAmount*parameters.getProtein()/this.amount,newAmount*parameters.getFat()/this.amount);
			this.amount=newAmount;
		}
		else
			this.amount=newAmount;

	}


}
