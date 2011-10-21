package database;

import java.util.ArrayList;

public class UserData implements Data {

	int id;
	String uname;
	String pw;
	ArrayList<Integer> modof;
	
	@Override //TODO
	public boolean validate() {
		if (uname != null && pw != null && modof != null) {
			return true;
		}
		return false;
	}

	@Override
	public int getId() {
		return id;
	}
	
	public String getUName() {
		return uname;
	}
	
	public String getPW() {
		return pw;
	}
	
	public ArrayList<Integer> getModOf() {
		return modof;
	}

}
