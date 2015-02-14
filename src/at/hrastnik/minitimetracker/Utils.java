package at.hrastnik.minitimetracker;

public class Utils {
	public static String nullSafeToString(Object o) {
		if (o == null) {
			return "N/A";
		} else {
			return o.toString();
		}
	}
}
