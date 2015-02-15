package at.hrastnik.minitimetracker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	public static String nullSafeToString(Object o) {
		if (o == null) {
			return "N/A";
		} else {
			return o.toString();
		}
	}
	
	
	public static String timestampToString(Date date) {
		if (date == null) {
			return "N/A";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	
}
