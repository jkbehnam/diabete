package database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseInterface {

	public static boolean insert(SQLiteDatabase mDb, String tableName,
			Object[] fieldsLabel, Object[] fieldsValue) {
		try {
			ContentValues cv = new ContentValues();
			for (int j = 0; j < fieldsLabel.length; j++)
				cv.put(fieldsLabel[j].toString(), fieldsValue[j]!=null?fieldsValue[j].toString():null);
			if (mDb.insert(tableName, null, cv) != -1)
				Log.d("Save" + tableName, "informationsaved");
			else 
				throw new Exception();
			return true;

		} catch (Exception ex) {
			Log.d("SaveDate", ex.toString());
			return false;
		}
	}

	public static int delete(SQLiteDatabase mDb, String tableName,
			String where, String[] whereArgs) {
		int count;
		try {
			if ((count = mDb.delete(tableName, where, whereArgs)) > 0)
				Log.d("Delete:" + tableName, String.valueOf(count));
			return count;

		} catch (Exception ex) {
			Log.d("Delete", ex.toString());
			return -1;
		}

	}

	public static int update(SQLiteDatabase mDb, String tableName,
			ContentValues cv, String where, String[] whereArgs) {
		int count;
		try {
			if ((count = mDb.update(tableName, cv, where, whereArgs)) > 0)
				Log.d("Update:" + tableName, String.valueOf(count));
			return count;

		} catch (Exception ex) {
			Log.d("Update", ex.toString());
			return -1;
		}
	}


}
