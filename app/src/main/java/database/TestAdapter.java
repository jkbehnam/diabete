package database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class TestAdapter {
	protected static final String TAG = "DataAdapter";

	private final Context mContext;
	private SQLiteDatabase mDb;
	private DataBaseHelper mDbHelper;

	public TestAdapter(Context context) {
		this.mContext = context;
		mDbHelper = new DataBaseHelper(mContext);
		createDatabase();
	}

	public TestAdapter createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
			open();
			close();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public TestAdapter open() throws SQLException {
		try {
			mDbHelper.openDataBase();
			mDb = mDbHelper.getReadableDatabase();
			
		} catch (SQLException mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public void executeQuery(String sql)
	{
		try {
			open();
			Cursor c = mDb.rawQuery(sql,null);

		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}


	public ArrayList<String[]> getData(String sql) {
		try {
			open();
			Cursor c = mDb.rawQuery(sql,null);
			return cursroToArrayList(c);

		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	private ArrayList<String[]> cursroToArrayList(Cursor c) {
		ArrayList<String[]> a = new ArrayList<String[]>();
		int count = c.getColumnCount();
		String[] temp;
		if (c.moveToFirst()) {
			do {
				temp = new String[count];
				for (int i = 0; i < count; i++)
					temp[i] = c.getString(i);
				a.add(temp);
			} while (c.moveToNext());
		}
		return a;
	}
	
	public  String[] ArrayListToOneColumnString(ArrayList<String[]> a)
	{
		int i=0;
		String[] s=new String[a.size()];
		for(String[] temp:a)
		{
			s[i++]=temp[0];
		}
		return s;
	}

	public boolean InsertData(Queryable object) {
		open();
		boolean temp= object.insert(mDb);
		close();
		return temp;
	}
	
	public int updateData(Queryable object)
	{
		open();
		int temp=object.update(mDb);
		close();
		return temp;
	}
	
	public int deleteData(Queryable object)
	{
		open();
		int temp=object.delete(mDb);
		close();
		return temp;
	}


}
