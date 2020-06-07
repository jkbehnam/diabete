package entity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import database.DatabaseInterface;
import database.Queryable;
import database.columns.RecentColumns;

public class Recent implements Queryable{

	String date;
	String meal;
	String name;
	
	public Recent(String date, String meal, String name) {
		this.date = date;
		this.meal = meal;
		this.name = name;
	}
	public Recent(String[] input)
	{
		this.date=input[0];
		this.meal=input[1];
		this.name=input[2];
	}
	@Override
	public boolean insert(SQLiteDatabase mDb) {
		DatabaseInterface.insert(mDb, "Recent", RecentColumns.values(),
				getFieldsValue());
		return false;
	}

	private Object[] getFieldsValue() {
		
		return new Object[]{date,meal,name};
	}

	@Override
	public int update(SQLiteDatabase mDb) {
		ContentValues cv=new ContentValues();
		cv.put(RecentColumns.Date.getName(), new SimpleDateFormat("yyyy-MM-dd")
		.format(new Date()));
		DatabaseInterface.update(mDb, "Recent", cv, RecentColumns.Date.getName() + "='" + date + "'", null);
		return 0;
	}

	@Override
	public int delete(SQLiteDatabase mDb) {
		DatabaseInterface.delete(mDb, "Recent", RecentColumns.Date.getName() + "='" + date + "'", null);
		return 0;
	}
	
	
	
}
