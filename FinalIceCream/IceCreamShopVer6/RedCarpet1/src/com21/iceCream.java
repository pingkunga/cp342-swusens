package com21;

public class iceCream {
	private int size_id; //เก็บ id ของสินค้า
	private String size_name; //เก็บ ชื่อ สินค้า
	private int price; //ราคา
	private int pro_id; //ส่วนลดถ้ามี
	private int discount;
	
	//กำหนดว่าใส่ได้เท่าไหร่
	private int max_ice_cream;
	private int count_ice = 0;
	private int max_topping;
	private int count_top = 0;
	
	//เก็บข้อมูลว่าเลือกอะไร
	private int ice[];
	private int top[];
	
	//****************************************************
	public void setSize_id(int size_id) {
		this.size_id = size_id;
	}
	public int getSize_id() {
		return size_id;
	}
	
	//****************************************************
	public void setSize_name(String size_name) {
		this.size_name = size_name;
	}
	public String getSize_name() {
		return size_name;
	}
	
	//****************************************************
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPrice() {
		return price;
	}
	
	//****************************************************
	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}
	public int getPro_id() {
		return pro_id;
	}
	
	//***************************************************
	public void setMax_ice_cream(int max_ice_cream) {
		this.max_ice_cream = max_ice_cream;
	}
	public int getMax_ice_cream() {
		return max_ice_cream;
	}
	
	//****************************************************
	public void setCount_ice(int count_ice) {
		this.count_ice = count_ice;
	}
	public int getCount_ice() {
		return count_ice;
	}
	
	//****************************************************
	public void setMax_topping(int max_topping) {
		this.max_topping = max_topping;
	}
	public int getMax_topping() {
		return max_topping;
	}
	
	//****************************************************
	public void setCount_top(int count_top) {
		this.count_top = count_top;
	}
	public int getCount_top() {
		return count_top;
	}
	
	//****************************************************
	public void initIce(){
		ice = new int[max_ice_cream];
	}
	public void setIce(int ice_id, int idx) {
		this.ice[idx] = ice_id;
	}
	public int getIce(int idx) {
		return ice[idx];
	}
	
	//****************************************************
	public void initTop(){
		top = new int[max_topping];
	}
	public void setTop(int top_id, int idx) {
		this.top[idx] = top_id;
	}
	public int getTop(int idx) {
		return top[idx];
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getDiscount() {
		return discount;
	}
	
	//****************************************************
	
	
	
}
