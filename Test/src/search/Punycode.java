package search;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Punycode {

	public static String CN_REGEX = "\\w*[\u4E00-\u9FA5]+[\u4E00-\u9FA5\\w]*";
	 public static Pattern CN_MATCH_PATTERN = Pattern.compile(CN_REGEX);
	 public static final String PUNYCODE_PREFIX = "xn--";
	 public static final Pattern PUNYCODE_MATCH_PATTERN = Pattern.compile(PUNYCODE_PREFIX + "[a-zA-Z0-9]+");
	 
	 /**
	  * 域名中文转Punycode
	  */
	 public static StringBuilder encode(String domain) throws ParseException {
	  Matcher matcher = CN_MATCH_PATTERN.matcher(domain);
	  StringBuilder encoded = new StringBuilder(domain);
	  int increment = 0;// 替换引起的字符串长度差异，用于定位
	  
	  while (matcher.find()) {
	   int start = matcher.start() + increment;
	   int end = matcher.end() + increment;
	   String group = matcher.group();
	   String replaceWith = Punycode.encode(new StringBuffer(group), null).toString();
	   encoded.replace(start, end, replaceWith);
	   encoded.insert(start, PUNYCODE_PREFIX);
	   
	   increment += replaceWith.length() + PUNYCODE_PREFIX.length() - group.length();
	  }
	  return encoded;
	 }
	 
	 /**
	  * 域名Punycode转中文
	  */
	 public static StringBuilder cncode(String domain) throws ParseException {
	  Matcher matcher = PUNYCODE_MATCH_PATTERN.matcher(domain);
	  StringBuilder decoded = new StringBuilder(domain);
	  int increment = 0;// 替换引起的字符串长度差异，用于定位

	  while (matcher.find()) {
	   int start = matcher.start() + increment;
	   int end = matcher.end() + increment;
	   String group = matcher.group();
	   String replaceWith = Punycode.decode(new StringBuffer(group.substring(PUNYCODE_PREFIX.length())), null).toString();
	   decoded.replace(start, end, replaceWith);
	   
	   increment += replaceWith.length() - group.length();
	  }
	  return decoded;
	 }
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
