package database;

import java.util.ArrayList;

import util.StringParser;

/** The UserData object is associated with all users in the Database. A database will parse
 * the data it has and create a UserData object.
 * 
 * @author Michael Kirby
 *
 */
public class UserData implements Data {
	
	/** The ID of this UserData */
	private int id;
	
	/** The username of this UserData */
	private String uname;
	
	/** The password of this UserData */
	private String pw;
	
	/** The list of modof of this UserData */
	private ArrayList<Integer> modof;
	
	/** The list of userof of this UserData */
	private ArrayList<Integer> userof;
	
	/** Constructs a UserData object using all it needs
	 * 
	 * @param id the id of the User, null if not a user yet.
	 * @param uname the username of the user.
	 * @param pw the password of the user.
	 * @param mod a String of group IDs separated by ~, (i.e. 1~4~9~  Make sure it ends in ~).
	 */
	public UserData(int id, String uname, String pw, String mod, String user) {   // I think we will need to have user id generated instead of having it as a parameter. -Bob F 10/22
		this.id = id;
		this.uname = uname;
		this.pw = pw;
		modof = StringParser.parseString(mod);
		userof = StringParser.parseString(user); //TODO change to user parameter
	}
	
	@Override //TODO
	public boolean validate() {
		if (uname == null || pw == null) { // Will need to check for modOfGroup when it is finalized
			return false;
		}
		return true;
	}

	@Override
	public int getId() {
		return id;
	}
	
	/** Return the username String associated with this UserData.
	 * 
	 * @return the username String associated with this UserData.
	 */
	public String getUName() {
		return uname;
	}
	
	/** Return the password String associated with this UserData.
	 * 
	 * @return the password String associated with this UserData.
	 */
	public String getPW() {
		return pw;
	}
	
	/** Return the ArrayList of type Integer that represent the groups that this user is mod of.
	 * 
	 * @return the ArrayList of type Integer that represent the groups that this user is mod of.
	 */ //TODO This maybe should parse it back into a string
	public ArrayList<Integer> getModOf() {
		return modof;
	}
	
	/** Return the ArrayList of type Integer that represent the groups that this user is user of.
	 * 
	 * @return the ArrayList of type Integer that represent the groups that this user is user of.
	 */ //TODO This maybe should parse it back into a string
	public ArrayList<Integer> getUserOf() {
		return userof;
	}
	public void setMod(int id){
		modof.add(id);
	}
	
	public void setUser(int id){
		userof.add(id);
	}
	
	public void unsetUser(int groupID){
		for(int i = 0; i<userof.size(); i++){
			if(userof.get(i)==groupID){
				userof.remove(i);
			}
		}
	}
	
	public void unsetMod(int groupID){
		for(int i = 0; i<modof.size(); i++){
			if(modof.get(i)==groupID){
				modof.remove(i);
			}
		}
	}
	
	public boolean isUserOf(int groupId) {
		return userof.contains(groupId);
	}
	
	public boolean isModOf(int groupId) {
		return modof.contains(groupId);
	}

}
