package database;

import android.database.sqlite.SQLiteDatabase;

public interface Queryable {

	boolean insert(SQLiteDatabase mDb);
	int update(SQLiteDatabase mDb);
	int delete(SQLiteDatabase mDb);
}
