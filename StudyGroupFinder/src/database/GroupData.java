package database;

import java.util.ArrayList;

import util.StringParser;

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
		mods = StringParser.parseString(modst);
		users = StringParser.parseString(userst);
	}
	
	@Override //TODO
	public boolean validate() {
		if (name != null && course != null && mods != null) {
			if (name.length() > 1 && course.length() > 1 && mods.size() >= 1)
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
