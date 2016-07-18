package search;

public class Test {

	public static void main(String[] args) throws PunyException {
		String zhongwen = PunycodeUtil.punycode2chinese("xn--80aaadgsaakp9cbhzi0abde5q.com");
		System.out.println(zhongwen);
	}

}
