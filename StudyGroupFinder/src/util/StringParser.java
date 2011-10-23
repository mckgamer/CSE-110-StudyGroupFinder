package util;

import java.util.ArrayList;

public final class StringParser {

	public static ArrayList<Integer> parseString(String string) {
		ArrayList<Integer> retur = new ArrayList<Integer>();
		if (string.length() > 1) {
			for(String i: string.split("~")) {
				retur.add(Integer.parseInt(i));
			}
		}
		return retur;
	}
	
	public static String unParseArray(ArrayList<Integer> a) {
		String retur = "";
		for(Integer i: a) {
			retur = retur + i;
			retur = retur + "~";
		}
		return retur;
	}
}
