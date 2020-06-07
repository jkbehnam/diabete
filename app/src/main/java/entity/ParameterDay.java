package entity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import database.DatabaseInterface;
import database.Queryable;
import database.columns.ParameterDayColumns;

public class ParameterDay implements Queryable, Comparable<ParameterDay> {

	String date;
	Parameters params;

	public ParameterDay(String date, int carbo, int cal, int prot, int fat) {
		this.date = date;
		params = new Parameters(carbo, cal, prot, fat);
	}

	public ParameterDay(String[] input) {
		this(input[0], Integer.parseInt(input[1]), Integer.parseInt(input[2]),
				Integer.parseInt(input[3]), Integer.parseInt(input[4]));
	}

	public void addItem(int carbo, int cal, int prot, int fat) {
		params.addParams(carbo, cal, prot, fat);
	}

	public void addItem(Item item) {
		params.addParams(item.getParams().getCarbohydrate(), item.getParams()
				.getCalorie(), item.getParams().getProtein(), item.getParams()
				.getFat());
	}

	public void removeItem(Item item)
	{
		params.subtractParams(item.getParams().getCarbohydrate(), item.getParams()
				.getCalorie(), item.getParams().getProtein(), item.getParams()
				.getFat());
	}

	@Override
	public boolean insert(SQLiteDatabase mDb) {
		DatabaseInterface.insert(mDb, "Parameter_Day", ParameterDayColumns.values(),
				getFieldsValue());
		return false;
	}

	@Override
	public int update(SQLiteDatabase mDb) {
		ContentValues cv=new ContentValues();
		cv.put(ParameterDayColumns.Total_Carbohydrate.getString(), params.getCarbohydrate());
		cv.put(ParameterDayColumns.Total_Calorie.getString(), params.getCalorie());
		cv.put(ParameterDayColumns.Total_protein.getString(), params.getProtein());
		cv.put(ParameterDayColumns.Total_fat.getString(), params.getFat());


		DatabaseInterface.update(mDb, "Parameter_Day", cv, ParameterDayColumns.Date.getString() + "='" + date + "'", null);
		return 0;
	}

	@Override
	public int delete(SQLiteDatabase mDb) {
		DatabaseInterface.delete(mDb, "Parameter_Day", ParameterDayColumns.Date.getString() + "='" + date + "'", null);
		
		return 0;
	}

	@Override
	public int compareTo(ParameterDay another) {
		Date today=stringToDate(this.date);
		Date anday=stringToDate(this.date);
		if(today.compareTo(anday)==0)
			return 0;
		else if(today.compareTo(anday)>0)
			return 1;
		else return -1;
	}
	
	 private Date stringToDate(String aDate) {

	      if(aDate==null) return null;
	      ParsePosition pos = new ParsePosition(0);
	      SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
	      Date stringDate = simpledateformat.parse(aDate, pos);
	      return stringDate;            

	   }

	public static ParameterDay findMin(List<ParameterDay> pds) {
		ParameterDay min=pds.get(0);
		for(ParameterDay pd:pds)
			if(pd.compareTo(min)<0)
				min=pd;
		return min;
	}
	
	protected Object[] getFieldsValue() {
		return new Object[] { date, 
				params.getCarbohydrate(), params.getCalorie(),
				params.getProtein(), params.getFat() };
	}

	public static boolean containsdate(List<String[]> temp, String currentDate) {
		for(String[] a:temp)
		{
			
			if(a[0].compareTo(currentDate)==0)
				return true;
		}
		return false;
	}

	public Parameters getParams()
	{
		return params;
	}
}
