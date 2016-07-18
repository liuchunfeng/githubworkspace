package com;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test8 {

	public static void main(String[] args) {
		String s = "<basicInfo>" + "<deleteInfo>" + "<idcId></idcId>"
				+ "</deleteInfo>" + "<timeStamp>qqq</timeStamp>"
				 + "<test/><test2/>"
				+ "</basicInfo>";

		String regEx = "<[\\w]+></[\\w]+>|<[\\w]+/>";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(s);
		while(mat.find()){
			for (int i = 0; i <= mat.groupCount(); i++) {
				if(mat.group(i) != null)
				System.out.println(mat.group(i));
			}
		}
		

	}

}
