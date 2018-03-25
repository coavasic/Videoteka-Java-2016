package utils;

public class StringUtils {
	public static String cpzPocetnoSlovo(String string) {
		String noviString = string.substring(0, 1).toUpperCase() + string.substring(1);
		return noviString;
	}

}
