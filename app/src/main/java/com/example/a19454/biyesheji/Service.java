package com.example.a19454.biyesheji;

import android.content.Context;

import java.util.List;

public class Service {
	
	private DBControler dao=null;
	

	public Service(Context context){
		dao = new DBControler(context);
	}
	

	public boolean save(Contact contact){
		boolean flag = dao.save(contact);
		return flag;
	}
	

	public List getByName(String queryName){
		List list = dao.getByName(queryName);
		return list;
	}
	

	public Contact getById(int id){
		Contact contact = dao.getById(id);
		return contact;
	}
	

	public boolean update(Contact contact){
		boolean flag = dao.update(contact);
		return flag;
	}
	

	public void delete(int id){
		dao.delete(id);
	}
}
