package com;
import java.net.InetAddress;
import java.net.UnknownHostException;
 
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;
public class NetWork {
	public static void main(String[] args) throws UnknownHostException, TextParseException {
//        InetAddress address = InetAddress.getByName("baidu.com");
//        System.out.println(address.getHostAddress());
         
        //查询域名对应的IP地址
        Lookup lookup = new Lookup("1321321adfaf.com", Type.A);
        lookup.run();
        if (lookup.getResult() != Lookup.SUCCESSFUL){
//            System.out.println("ERROR: " + lookup.getErrorString());
            return;
        }
        Record[] answers = lookup.getAnswers();
        for(Record rec : answers){
            System.out.println(rec.rdataToString());
        }
    }
}
