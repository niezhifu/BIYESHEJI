package com.example.a19454.biyesheji;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
    private static final String DATABASE_NAME = "contact.db";
    private static final int DATABASE_VERSION = 1;
    private static String sql = "create table contact (" + "_id integer primary key autoincrement, "
                                + "name text, " + "phone text, " + "number text, " + "qq text, "
    		                    + "email text, " + "address text)";
    
	DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
