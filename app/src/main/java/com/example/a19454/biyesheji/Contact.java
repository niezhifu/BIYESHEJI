package com.example.a19454.biyesheji;

public class Contact {
	private int id;
	private String name=null;
	private String phone=null;
	private String number=null;
	private String qq=null;
	private String email=null;
	private String address=null;


	public Contact(){
		id=0;
		name="";
		phone="";
		number="";
		qq="";
		email="";
		address="";
	}

	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}

	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}
	public String getPhone(){
		return phone;
	}

	public void setNumber(String number){
		this.number = number;
	}
	public String getNumber(){
		return number;
	}

	public void setQq(String gender){
		this.qq = qq;
	}
	public String getQq(){
		return qq;
	}

	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return email;
	}

	public void setAddress(String address){
		this.address = address;
	}
	public String getAddress(){
		return address;
	}

}
