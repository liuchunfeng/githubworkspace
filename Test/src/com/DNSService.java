package com;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
 



import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class DNSService {
	/**
	 * @param serverAddr DNS地址
	 * @param timeOut 连接超时时间
	 * @param type 查询类型
	 * @param address 查询地址
	 * @return
	 */
	public static List<String> search(String serverAddr, int timeOut, 
			String type, String address) {
		InitialDirContext context = null;
		List<String> resultList = new ArrayList<String>();
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put("java.naming.factory.initial",
					"com.sun.jndi.dns.DnsContextFactory");
			env.put("java.naming.provider.url", "dns://" + serverAddr + "/");
			env.put("com.sun.jndi.ldap.read.timeout", String.valueOf(timeOut));
			context = new InitialDirContext(env);
			String dns_attrs[] = { type };
			Attributes attrs = context.getAttributes(address, dns_attrs);
			
			Attribute attr = attrs.get(type);
			if (attr != null) {
				for (int i = 0; i < attr.size(); i++) {
					resultList.add((String) attr.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(context!=null){
				try {
					context.close();
				} catch (NamingException e) {
					
				}
			}
		}
		return resultList;
	}
	
	public static ArrayList getDNSRecs(String domain, String provider,
			   String... types) throws Exception {//NamingException, DnsAnysException
			  ArrayList results = new ArrayList(15);
			  // Old Java 1.3 style required you to provide an explicit DNS server.
			  // DirContext ictx = new InitialDirContext();
			  // Attributes attrs =
			  // ictx.getAttributes( "dns://" + DNS_SERVER + "/" + domain,
			  // types );
			   Hashtable env = new Hashtable();
			  env.put("java.naming.factory.initial",
			    "com.sun.jndi.dns.DnsContextFactory");
			  // 指定DNS
			   env.put(Context.PROVIDER_URL, "dns://" + provider);
			  // env.put(Context.PROVIDER_URL, "dns://" + "8.8.8.8");//指定DNS，8.8.8.8是谷歌的DNS，解析很快的。
			  env.put("com.sun.jndi.dns.timeout.initial", "1000");// 连接时间
			  env.put("com.sun.jndi.dns.timeout.retries", "1");// 连接次数
			  DirContext ictx = new InitialDirContext(env);
			  Attributes attrs = ictx.getAttributes(domain, types);
			  for (Enumeration e = attrs.getAll(); e.hasMoreElements();) {
			   Attribute a = (Attribute) e.nextElement();
			   int size = a.size();
			   for (int i = 0; i < size; i++) {
			    // MX string has priority (lower better) followed by associated
			    // mailserver
			    // A string is just IP
			    results.add((String) a.get(i));
			   }// end inner for
			  }// end outer for
			  if (results.size() == 0) {
			   System.err.println("Failed to find any DNS records for domain "
			     + domain);
			   throw new Exception("找不到对应域名的DNS结果。");//方便得不到IP使用备用DNS处理  DnsAnysException
			  }
			  return results;
			 }
 
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		/*List<String> resultList = search("202.106.0.20", 2000, "A","www.what21.com");
//		List<String> resultList = search("61.139.2.69", 2000, "A","basket.win007.com");
		for(String str : resultList){
			System.out.println(str);
		}*/
		
		ArrayList<String> results = getDNSRecs("www.what21.com","61.139.2.69","A");
		for(String str : results){
			System.out.println(str);
		}
	}
}
