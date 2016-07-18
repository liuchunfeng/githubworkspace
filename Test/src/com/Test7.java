package com;

public class Test7 {
	
	public String aa(){
		try{
			return "123";
		}catch(Exception e){
			System.out.println("异常catch");
		}finally{
			System.out.println("异常finally");
		}
		
		return "shibai";
	}
}
