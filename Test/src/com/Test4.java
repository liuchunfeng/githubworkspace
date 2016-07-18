package com;

import java.util.UUID;

public class Test4 {

	public static void main(String[] args) {
		UUID uuid = UUID.randomUUID();
		String s = UUID.randomUUID().toString();// 用来生成数据库的主键id非常不错..
		System.out.println(s);
		
		System.out.println(getUUID());

	}
	aa

	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉"-"符号
		// return
		// s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
		return s;
	}
}
