package utils;

import java.util.Date;

public class NumberUtils {

	public static int daniIzmedju(Date d2, Date d1) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

}
