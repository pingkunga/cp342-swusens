package com21;

public class Validation {

	//ตรวจว่าเป็น 0-9
	public boolean chknumber(char input){
		boolean result = true;
		try{
			Character c = new Character(input);
			int temp = Integer.parseInt(c.toString());
		}catch(Exception e){
			result = false;
			e.printStackTrace();
			
		}
		return result;
	}
}
