package com;

public class Punycode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/* Punycode parameters */  
    static int TMIN = 1;  
    static int TMAX = 26;  
    static int BASE = 36;  
    static int INITIAL_N = 128;  
    static int INITIAL_BIAS = 72;  
    static int DAMP = 700;  
    static int SKEW = 38;  
    static char DELIMITER = '-';  
    /** 
     * Punycodes a unicode string. 
     * 
     * @param input Unicode string. 
     * @return Punycoded string. 
     */  
    public static String encode(String input)  
    {  
        int n = INITIAL_N;  
        int delta = 0;  
        int bias = INITIAL_BIAS;  
        StringBuilder output = new StringBuilder();  
        // Copy all basic code points to the output  
        int b = 0;  
        for (int i = 0; i < input.length(); i++)  
        {  
            char c = input[i];  
            if (isBasic(c))  
            {  
                output.Append(c);  
                b++;  
            }  
        }  
        // Append delimiter  
        if (b > 0)  
        {  
            output.Append(DELIMITER);  
        }  
        int h = b;  
        while (h < input.Length)  
        {  
            int m = int.MaxValue;  
            // Find the minimum code point >= n  
            for (int i = 0; i < input.Length; i++)  
            {  
                int c = input[i];  
                if (c >= n && c < m)  
                {  
                    m = c;  
                }  
            }  
            if (m - n > (int.MaxValue - delta) / (h + 1))  
            {  
                throw new Exception("OVERFLOW");  
            }  
            delta = delta + (m - n) * (h + 1);  
            n = m;  
            for (int j = 0; j < input.Length; j++)  
            {  
                int c = input[j];  
                if (c < n)  
                {  
                    delta++;  
                    if (0 == delta)  
                    {  
                        throw new Exception("OVERFLOW");  
                    }  
                }  
                if (c == n)  
                {  
                    int q = delta;  
                    for (int k = BASE; ; k += BASE)  
                    {  
                        int t;  
                        if (k <= bias)  
                        {  
                            t = TMIN;  
                        }  
                        else if (k >= bias + TMAX)  
                        {  
                            t = TMAX;  
                        }  
                        else  
                        {  
                            t = k - bias;  
                        }  
                        if (q < t)  
                        {  
                            break;  
                        }  
                        output.Append((char)digit2codepoint(t + (q - t) % (BASE - t)));  
                        q = (q - t) / (BASE - t);  
                    }  
                    output.Append((char)digit2codepoint(q));  
                    bias = adapt(delta, h + 1, h == b);  
                    delta = 0;  
                    h++;  
                }  
            }  
            delta++;  
            n++;  
        }  
        return output.ToString();  
    }  
    /** 
     * Decode a punycoded string. 
     * 
     * @param input Punycode string 
     * @return Unicode string. 
     */  
    public static String decode(String input)  
    {  
        int n = INITIAL_N;  
        int i = 0;  
        int bias = INITIAL_BIAS;  
        StringBuilder output = new StringBuilder();  
        int d = input.LastIndexOf(DELIMITER);  
        if (d > 0)  
        {  
            for (int j = 0; j < d; j++)  
            {  
                char c = input[j];  
                if (!isBasic(c))  
                {  
                    throw new Exception("BAD_INPUT");  
                }  
                output.Append(c);  
            }  
            d++;  
        }  
        else  
        {  
            d = 0;  
        }  
        while (d < input.Length)  
        {  
            int oldi = i;  
            int w = 1;  
            for (int k = BASE; ; k += BASE)  
            {  
                if (d == input.Length)  
                {  
                    throw new Exception("BAD_INPUT");  
                }  
                int c = input[d++];  
                int digit = codepoint2digit(c);  
                if (digit > (int.MaxValue - i) / w)  
                {  
                    throw new Exception("OVERFLOW");  
                }  
                i = i + digit * w;  
                int t;  
                if (k <= bias)  
                {  
                    t = TMIN;  
                }  
                else if (k >= bias + TMAX)  
                {  
                    t = TMAX;  
                }  
                else  
                {  
                    t = k - bias;  
                }  
                if (digit < t)  
                {  
                    break;  
                }  
                w = w * (BASE - t);  
            }  
            bias = adapt(i - oldi, output.Length + 1, oldi == 0);  
            if (i / (output.Length + 1) > int.MaxValue - n)  
            {  
                throw new Exception("OVERFLOW");  
            }  
            n = n + i / (output.Length + 1);  
            i = i % (output.Length + 1);  
            output.Insert(i, (char)n);  
            i++;  
        }  
        return output.ToString();  
    }  
    public static int adapt(int delta, int numpoints, bool first)  
    {  
        if (first)  
        {  
            delta = delta / DAMP;  
        }  
        else  
        {  
            delta = delta / 2;  
        }  
        delta = delta + (delta / numpoints);  
        int k = 0;  
        while (delta > ((BASE - TMIN) * TMAX) / 2)  
        {  
            delta = delta / (BASE - TMIN);  
            k = k + BASE;  
        }  
        return k + ((BASE - TMIN + 1) * delta) / (delta + SKEW);  
    }  
    public static bool isBasic(char c)  
    {  
        return c < 0x80;  
    }  
    public static int digit2codepoint(int d)  
    {  
        if (d < 26)  
        {  
            // 0..25 : 'a'..'z'  
            return d + 'a';  
        }  
        else if (d < 36)  
        {  
            // 26..35 : '0'..'9';  
            return d - 26 + '0';  
        }  
        else  
        {  
            throw new Exception("BAD_INPUT");  
        }  
    }  
    public static int codepoint2digit(int c)  
    {  
        if (c - '0' < 10)  
        {  
            // '0'..'9' : 26..35  
            return c - '0' + 26;  
        }  
        else if (c - 'a' < 26)  
        {  
            // 'a'..'z' : 0..25  
            return c - 'a';  
        }  
        else  
        {  
            try {
				throw new Exception("BAD_INPUT");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }  
    }  
}
