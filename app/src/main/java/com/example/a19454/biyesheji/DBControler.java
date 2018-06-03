package com.example.a19454.biyesheji;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBControler {
	
	private DBHelper database = null;

	public DBControler(Context context){
		database = new DBHelper(context);
	}
	
	public boolean save(Contact contact){
		SQLiteDatabase db = database.getWritableDatabase();
		if(contact != null){
			ContentValues value = new ContentValues();
			value.put("name", contact.getName());
			value.put("phone", contact.getPhone());
			value.put("number", contact.getNumber());
			value.put("qq", contact.getQq());
			value.put("email", contact.getEmail());
			value.put("address", contact.getAddress());
			db.insertOrThrow("contact", null, value);
			db.close();
			return true;
		}
		else{
			return false;
		}
	}
	

	public List getByName(String queryName){
		if(queryName == null || queryName.equals("")){
			return getAll();
		}	
		List list = null;
		SQLiteDatabase db = database.getReadableDatabase();
		String sql = "select * from contact where name like ? or phone like ?";
		String[] params = new String[]{"%"+queryName+"%", "%"+queryName+"%"};
		Cursor cursor = db.rawQuery(sql, params);
		
		list = new ArrayList();
		while(cursor.moveToNext()){
			Contact contact = new Contact();
			contact.setId(cursor.getInt(0));
			contact.setName(cursor.getString(1));
			contact.setPhone(cursor.getString(2));
			contact.setNumber(cursor.getString(3));
			contact.setQq(cursor.getString(4));
			contact.setEmail(cursor.getString(5));
			contact.setAddress(cursor.getString(6));
			list.add(contact);
		}
		cursor.close();
		db.close();
		return list;
	}
	

	public List getAll(){
		List list = null;
		SQLiteDatabase db = database.getReadableDatabase();
		String sql = "select * from contact";
		Cursor cursor = db.rawQuery(sql, null);
		
		list = new ArrayList();
		while(cursor.moveToNext()){
			Contact contact = new Contact();
			contact.setId(cursor.getInt(0));
			contact.setName(cursor.getString(1));
			contact.setPhone(cursor.getString(2));
			contact.setNumber(cursor.getString(3));
			contact.setQq(cursor.getString(4));
			contact.setEmail(cursor.getString(5));
			contact.setAddress(cursor.getString(6));
			list.add(contact);
		}
		cursor.close();
		db.close();
		return list;
	}

	public Contact getById(int id){
		Contact contact = null;
		if(id > 0){
			SQLiteDatabase db = database.getReadableDatabase();
			String sql = "select * from contact where _id=?";
			String[] params = new String[] {String.valueOf(id)};
			Cursor cursor = db.rawQuery(sql, params);
			if(cursor.moveToNext()){
				contact = new Contact();
				contact.setId(cursor.getInt(0));
				contact.setName(cursor.getString(1));
				contact.setPhone(cursor.getString(2));
				contact.setNumber(cursor.getString(3));
				contact.setQq(cursor.getString(4));
				contact.setEmail(cursor.getString(5));
				contact.setAddress(cursor.getString(6));
			}
			cursor.close();
			db.close();
		}
		return contact;
	}
	

	public boolean update(Contact contact){
		if(contact != null){
			SQLiteDatabase db = database.getWritableDatabase();
			ContentValues value = new ContentValues();
			value.put("name", contact.getName());
			value.put("phone", contact.getPhone());
			value.put("number", contact.getNumber());
			value.put("qq", contact.getQq());
			value.put("email", contact.getEmail());
			value.put("address", contact.getAddress());
			db.update("contact", value, "_id=?", new String[]{String.valueOf(contact.getId())});
			db.close();
			return true;
		}
		else{
			return false;
		}
	}
	

	public void delete(int id){
		if(id > 0){
			SQLiteDatabase db = database.getWritableDatabase();
			String sql = "delete from contact where _id = ?";
			Object[] params = new Object[]{String.valueOf(id)};
			db.execSQL(sql, params);
			db.close();
		}
	}
}


