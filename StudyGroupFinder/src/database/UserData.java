package database;

import java.util.ArrayList;
import java.util.Date;

import util.StringParser;

/** The UserData object is associated with all users in the Database. A database will parse
 * the data it has and create a UserData object.
 * 
 * @author Michael Kirby
 *
 */
public class UserData implements Data {
	
	/** The ID of the user **/
	public int id;
	/** The login name of the user **/
	public String name;
	/** The login password of the user **/
	public String password;
	/** A comma delimited string of courses the user is interested in **/
	public String courses;
	/** The date of the last login, updated by database.login() method **/
	public Date last_login;
	/** ArrayList of group ids that the user is a moderator of **/
	public ArrayList<Integer> modof;
	/** ArrayList of group ids that the user is a member (non-moderator) of **/
	public ArrayList<Integer> userof;
	
	/** Construct an empty UserData object
	 * 
	 */
	public UserData() {}
	
	/** Constructor using Strings
	 * 
	 * @param id the id of the User, null if not a user yet.
	 * @param name the username of the user.
	 * @param password the password of the user.
	 * @param mod a String of group IDs separated by ~, (i.e. 1~4~9~  Make sure it ends in ~).
	 */
	public UserData(int id, String name, String password, String mod, String user) {   // I think we will need to have user id generated instead of having it as a parameter. -Bob F 10/22
		this.id = id;
		this.name = name;
		this.password = password;
		modof = StringParser.parseString(mod);
		userof = StringParser.parseString(user);
	}
	
	/** Constructor using ArraList for memberships
	 * 
	 * @param id the id of the User, null if not a user yet.
	 * @param name the username of the user.
	 * @param password the password of the user.
	 * @param modof is an {@link ArrayList} of integers
	 * @param userof is an {@link ArrayList} of integers
	 */
	public UserData(int id, String name, String password, ArrayList<Integer> modof, ArrayList<Integer> userof) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.modof = modof;
		this.userof = userof;
	}
	

	@Override
	public boolean validate() {
		if (name == null || password == null) {
			return false;
		}
		return true;
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
	
	public String toString() {
		String s = "";
		/** Produce name and password */
		s = 	"User: " +
				"ID=" + this.id + ", " + 
				"Name=" + this.name + ", " +
				"password=" + this.password + ", " +
				"courses=" + this.courses + ", ";

		/** Groups as user */
		s = s + "UserOf(";
		for (int i: userof) { s = s + i + ", "; }
		s = s + "), ";
		
		/** Groups as mod */
		s = s + "ModOf(";
		for (int i: modof) { s = s + i + ", "; }
		s = s + ")";
				
		return s;
	}

	@Override
	public int getId() {
		return id;
	}
	
	public String getUName() {
		return name;
	}

	public void setUName(String name) {
		this.name = name;
	}

	public String getPW() {
		return password;
	}

	public void setPW(String password) {
		this.password = password;
	}

	public String getCourses() {
		return courses;
	}

	public void setCourses(String courses) {
		this.courses = courses;
	}

	public ArrayList<Integer> getModOf() {
		return modof;
	}

	public void setModOf(ArrayList<Integer> modof) {
		this.modof = modof;
	}

	public ArrayList<Integer> getUserOf() {
		return userof;
	}

	public void setUserOf(ArrayList<Integer> userof) {
		this.userof = userof;
	}

	public Date getLast_login() {
		return last_login;
	}

	public void setId(int id) {
		this.id = id;
	}

}
