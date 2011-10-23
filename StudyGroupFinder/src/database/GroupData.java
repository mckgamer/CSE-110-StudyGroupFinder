package database;

import java.util.ArrayList;

public class GroupData implements Data {
	
	int id;
	String name;
	String course;
	ArrayList<Integer> mods;
	ArrayList<Integer> users;
	
	
	public GroupData(int id, String name, String course, String modst, String userst) {
		this.id = id;
		this.name = name;
		this.course = course;
		mods = new ArrayList<Integer>();
		users = new ArrayList<Integer>();
		for(String i: modst.split("~")) {
			mods.add(Integer.getInteger(i));
		}
		for(String i: userst.split("~")) {
			users.add(Integer.getInteger(i));
		}
	}
	
	@Override //TODO
	public boolean validate() {
		if (name != null && course != null && mods != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCourse() {
		return course;
	}
	
	public ArrayList<Integer> getMods() {
		return mods;
	}
	
	public ArrayList<Integer> getUsers() {
		return users;
	}

}
