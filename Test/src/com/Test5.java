package com;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test5 {

	public static void main(String[] args) {
//        String regex = "<commandid>(.*)</commandid>";
//        String str = "aaaaaa<commandid>123234</commandid>aaaaaaaaaaaaa";
        //将字符串中的.替换成_，因为.是特殊字符，所以要用\.表达，又因为\是特殊字符，所以要用\\.来表达.
//        str = str.replaceAll(regex, "mmmmmmm");
//        System.out.println(str.replaceFirst("<commandid>*</commandid>", "%%"));  
		String today_date=new SimpleDateFormat("yyyyMMdd").format(new Date());
		String today_sql=" INSERT INTO illdomainresult_"+today_date+" (idcID,houseId,visitCount,protocol,house_id,serviceIp,decPort,domain,serviceType,firstFoundTime,lastFoundTime,reportType,commandId,isIp,top_domain,version,illegalType,currentState,isMS_user,regError,reg_domain,houseididcid) "
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,concat(houseid,idcid)) ";
		
		today_sql=today_sql.substring(0, today_sql.indexOf("VALUES"));
		System.out.println(today_sql);
	}

}
