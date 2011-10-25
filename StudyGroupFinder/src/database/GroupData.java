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

	/** The ID of this GroupData */
	int id;

	/** The name of this GroupData */
	String name;

	/** The course of this GroupData */
	String course;

	/** The list of mods of this GroupData as an ArrayList<Integer>.*/
	ArrayList<Integer> mods;

	/** The list of other users of this GroupData as an ArrayList<Integer>.*/
	ArrayList<Integer> users;

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

	@Override //TODO
	public boolean validate() {
		if (name != null && course != null && mods != null) {
			if (name.length() > 1 && course.length() > 1)
				return true;
		}
		return false;
	}

	public void unsetUser(int userid){
		for(int i = 0; i<users.size(); i++){
			if(users.get(i)==userid){
				users.remove(i);
			}
		}
	}

	@Override
	public int getId() {
		return id;
	}

	/** Returns the Name of this GroupData.
	 * 
	 * @return the name of this GroupData.
	 */
	public String getName() {
		return name;
	}

	/** Returns the course of this GroupData.
	 * 
	 * @return the course of this GroupData.
	 */
	public String getCourse() {
		return course;
	}

	/** Return an ArrayList<Integer> of the id of the users who are mods.
	 * 
	 * @return an ArrayList<Integer> of the id of the users who are mods.
	 */
	public ArrayList<Integer> getMods() {
		return mods;
	}
	/**
	 * Add a mod to the Group
	 * @param id
	 */
	public void setMod(int id){
		mods.add(id);
	}
	/**
	 * Set user to the group
	 * @param id
	 */
	public void setUser(int id){
		users.add(id);
	}
	/** Return an ArrayList<Integer> of the id of the users who are user.
	 * 
	 * @return an ArrayList<Integer> of the id of the users who are users.
	 */
	public ArrayList<Integer> getUsers() {
		return users;
	}

}
