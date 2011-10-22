package database;

import java.util.ArrayList;

public class GroupData implements Data {
	
	int id;
	String name;
	String course;
	ArrayList<Integer> mods;
	
	
	public GroupData(int id, String name, String course, String mods) {
		this.id = id;
		this.name = name;
		this.course = course;
		// TODO
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

}
