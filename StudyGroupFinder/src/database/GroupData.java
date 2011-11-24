package database;

import java.util.ArrayList;

import util.StringParser;

/** The GroupData object is associated with all groups in the Database. A database will parse
 * the data it has and create a GroupData object.
 * 
 * @author Michael Kirby
 *
 */
public class GroupData implements Data {

	/** ID of the group **/
	public int id;
	/** Name of the group **/
	public String name;
	/** Course to which the group belongs **/
	public String course;
	/** ArrayList of user ids that are moderators of the group **/
	public ArrayList<Integer> mods;
	/** ArrayList of user ids that are members (non-moderators) of the group **/
	public ArrayList<Integer> users;

	/** 
	 * Empty constructor
	 */
	public GroupData() {}
	
	/** Constructs a GroupData object using all it needs
	 * 
	 * @param id the id of the Group, null if not a group yet.
	 * @param name the name of the group.
	 * @param course the course of this group.
	 * @param modst a String of user IDs separated by ~, (i.e. 1~4~9~  Make sure it ends in ~).
	 * @param userst a String of user IDs separated by ~, (i.e. 1~4~9~  Make sure it ends in ~).
	 */
	public GroupData(int id, String name, String course, String modst, String userst) {
		this.id = id;
		this.name = name;
		this.course = course;
		mods = StringParser.parseString(modst);
		users = StringParser.parseString(userst);
	}

	/** Constructs a GroupData object using all it needs
	 * 
	 * @param id the id of the Group, null if not a group yet.
	 * @param name the name of the group.
	 * @param course the course of this group.
	 * @param modst a String of user IDs separated by ~, (i.e. 1~4~9~  Make sure it ends in ~).
	 * @param userst a String of user IDs separated by ~, (i.e. 1~4~9~  Make sure it ends in ~).
	 */
	public GroupData(int id, String name, String course, ArrayList<Integer> modList, ArrayList<Integer> userList) {
		this.id = id;
		this.name = name;
		this.course = course;
		this.mods = modList;
		this.users = userList;
	}

	@Override //TODO
	public boolean validate() {
		if (name != null && course != null && mods != null) {
			if (name.length() > 1 && course.length() > 1)
				return true;
		}
		return false;
	}

	/**
	 * Set user to the group
	 * @param id
	 */
	
	public void setUser(int id){
		users.add(id);
	}

	public void unsetUser(int userid){
		for(int i = 0; i<users.size(); i++){
			if(users.get(i)==userid){
				users.remove(i);
			}
		}
	}


	/**
	 * Add a mod to the Group
	 * @param id
	 */
	public void setMod(int id){
		mods.add(id);
	}
	
	public String toString() {
		String s = "";
		/** Produce name and course */
		s = 	"Group: " +
				"Name=" + this.name + ", " +
				"Course=" + this.course + ", ";
	
		/** Users */
		s = s + "Users(";
		for (int i: users) { s = s + i + ", "; }
		s = s + "), ";
		
		/** Mods */
		s = s + "Mods(";
		for (int i: mods) { s = s + i + ", "; }
		s = s + ")";
				
		return s;
		
	}

	@Override
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/** Returns the Name of this GroupData.
	 * 
	 * @return the name of this GroupData.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** Returns the course of this GroupData.
	 * 
	 * @return the course of this GroupData.
	 */
	public String getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(String course) {
		this.course = course;
	}

	/** Return an ArrayList<Integer> of the id of the users who are user.
	 * 
	 * @return an ArrayList<Integer> of the id of the users who are users.
	 */
	public ArrayList<Integer> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(ArrayList<Integer> users) {
		this.users = users;
	}

	/** Return an ArrayList<Integer> of the id of the users who are mods.
	 * 
	 * @return an ArrayList<Integer> of the id of the users who are mods.
	 */
	public ArrayList<Integer> getMods() {
		return mods;
	}	
	
	/**
	 * @param mods the mods to set
	 */
	public void setMods(ArrayList<Integer> mods) {
		this.mods = mods;
	}
	
}
