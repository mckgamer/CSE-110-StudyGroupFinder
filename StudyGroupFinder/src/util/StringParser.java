package util;

import java.util.ArrayList;

/** A utility class for parsing strings delimited by ~. (i.e. 1~2~3~)
 * 
 * @author Michael Kirby
 *
 */
public final class StringParser {

	/** Returns an ArrayList<Integer> from the parsed string.
	 * 
	 * @param string the string to parse.
	 * @return the resulting ArrayList.
	 */
	public static ArrayList<Integer> parseString(String string) {
		ArrayList<Integer> retur = new ArrayList<Integer>();
		if (string.length() > 1) {
			for(String i: string.split("~")) {
				retur.add(Integer.parseInt(i));
			}
		}
		return retur;
	}
	
	/** Returns an string from an ArrayList.
	 * 
	 * @param a the ArrayList to put in the string.
	 * @return the resulting string.
	 */
	public static String unParseArray(ArrayList<Integer> a) {
		if (a == null || a.isEmpty()) {
			return "~";
		}
		String retur = "";
		for(Integer i: a) {
			retur = retur + i;
			retur = retur + "~";
		}
		return retur;
	}
}
