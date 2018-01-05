package vong.piler.her.vongruntime.util;

public class StringUtils {
	
	public static String removeLastCharacters(String str, int count) {
	    if (str != null && str.length() > count) {
	        str = str.substring(0, str.length() - count);
	    }
	    return str;
	}
}
