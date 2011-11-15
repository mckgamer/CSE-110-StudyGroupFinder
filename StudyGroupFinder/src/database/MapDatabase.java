package database;
import domainlogic.Status;
import domainlogic.StatusType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import util.StringParser;

import domainlogic.User;
import domainlogic.User.Logged;


public class MapDatabase implements Database {

	private Map<Integer, Vector<String>> users;
	private Map<Integer, Vector<String>> groups;
	private int userIdCounter;
	private int groupIdCounter;
	
	public MapDatabase() {
		users = new HashMap<Integer, Vector<String>>();
		groups = new HashMap<Integer, Vector<String>>();
		userIdCounter =0;
		populateUsers();
		populateGroups();
	}
	
	/* Helper Method to Populate Dummy User database */
	private void populateUsers() {
		// User Name	//PW	//Moderator of group	// user ID
		//ID uname Password   List of groups to which the user is mod, unique user id
		int uniqueId = getUniqueUserId();
		String userIdString = Integer.toString(uniqueId);
		users.put(uniqueId, addData("Mike","pw","1~","~", userIdString));
		
		uniqueId = getUniqueUserId();
		userIdString = Integer.toString(uniqueId);
		users.put(uniqueId, addData("Bob","pw","2~", "~", userIdString));
	}
	
	
	/* Helper Method to Populate Dummy Groups database */
	private void populateGroups() {
		//ID  //Name  //Class studied  //List of mod users // List of Users // Group ID
		int uniqueGroupId = getUniqueGroupId();
		String groupIdString = Integer.toString(uniqueGroupId);
		groups.put(uniqueGroupId, addData("The Group","CSE 110","1~", "~", groupIdString));
		uniqueGroupId = getUniqueGroupId();
		groupIdString = Integer.toString(uniqueGroupId);
		groups.put(uniqueGroupId, addData("Bobs Group","CSE 101","2~", "~", groupIdString));
	}
	
	/* Helper Method For Populating database */
	private Vector<String> addData(String... info) {
		Vector<String> temp = new Vector<String>();
		for (String i : info) {
			temp.add(i);
		}
		return temp;
	}
	/* Create a unique user id for key of hash map*/
	private int getUniqueUserId(){
		this.userIdCounter += 1;
		return userIdCounter;
	}
	
	/* Create a unique group id for key of hash map*/
	private int getUniqueGroupId(){
		this.groupIdCounter += 1;
		return groupIdCounter;
	}
	
	/** Adds a Group to the database 
	 * Precondition: GroupData object is valid. All data object variables have been validated.
	 * Postcondition: Method will return a Status object that contains the result of the method call  
	 * @param gd the {@link GroupData} object to add.
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status addGroup(GroupData gd) {
		Status tempStatus = new Status();
		//get UserID
		int uID = gd.getMods().get(0);
		//Generate unique Group ID and convert it to a string
		int uniqueGroupId = getUniqueGroupId();
		String groupIdString = Integer.toString(uniqueGroupId);
		groups.put(uniqueGroupId, addData(gd.getName(), gd.getCourse(), StringParser.unParseArray(gd.getMods()) ,"~", groupIdString));
		//Update user profile
		UserData tempUD = getUser(uID);
		tempUD.setMod(uniqueGroupId);
		updateUser(tempUD);
		tempStatus.setStatus(StatusType.SUCCESS);
		return tempStatus;
	}
	
	/** Updates Group Data in Database
	 * Preconditions: gd is a valid GroupData object
	 * Postconditions: Returns a Status object that contains the result of the method call
	 * @param gd {@link GroupData} GroupData object
	 * @return {@link Status} object that holds information on what happened
	 */
	public Status updateGroup(GroupData gd){
		Status tempStatus = new Status(StatusType.UNSUCCESSFUL);
		String idString = Integer.toString(gd.getId());
		groups.put(gd.getId(), addData(gd.getName(), gd.getCourse(), StringParser.unParseArray(gd.getMods()), StringParser.unParseArray(gd.getUsers()), idString));
		tempStatus.setStatus(StatusType.SUCCESS);
		return tempStatus;
	}
	
	/** Gets the specified GroupData from the database.
	 * Precondition: The group id is an integer.
	 * Postcondition: Method will return the GroupData object associated with the id number 
	 * @param id the ID of the group to get.
	 * @return the {@link GroupData} of the group.
	 */
	public GroupData getGroup(int id) {
		if (groups.containsKey(id)) {
			Vector<String> temp = groups.get(id);
			GroupData found = new GroupData(id, temp.get(0), temp.get(1), temp.get(2), temp.get(3));
			return found;
		}
		return null;
	}

	/** Adds the specified user to the specified group
	 * Preconditions: userid and groupid are integers.
	 * Postconditions: return a Status object that contains the result of the method call  
	 * @param userid the ID of the user to add.
	 * @param groupid the ID of the group to add to.
	 * @return {@link Status} object that holds information on what happened. 
	 */
	public Status addUserToGroup(int userid, int groupid) {
		//Need to get the GroupData and UserData Objects
		// Need to add userid to group users Array List.
		UserData tempUD = this.getUser(userid);
		GroupData tempGD = this.getGroup(groupid);
		Status tempStatus = new Status();
		//Update GroupData
		tempGD.setUser(userid);
		//Update UserData
		tempUD.setUser(groupid);
		//UpdateGroup
		this.updateGroup(tempGD);
		//Update User
		this.updateUser(tempUD);
		tempStatus.setStatus(StatusType.SUCCESS);
		return tempStatus;
	}
	/** Deletes a Group
	 * Preconditions: groupID is an integer
	 * Postconditions: Returns a Status object that contains the result of the method call
	 * @param groupID
	 * @return {@link Status} object that holds information on what happened
	 */
	public Status deleteGroup(int groupid){

		Status tempStatus = new Status();
		GroupData tempGD = this.getGroup(groupid);
		ArrayList<Integer> tempUsers = tempGD.getUsers();
		UserData tempUserData;
		for(int i=0; i<tempUsers.size(); i++){
			tempUserData = this.getUser(tempUsers.get(i));
			//Remove reference of group from user data
			tempUserData.unsetUser(groupid);
			//Save user data
			this.updateUser(tempUserData);
		}
		tempUsers = tempGD.getMods();
		for(int i=0; i<tempUsers.size(); i++){
			tempUserData = this.getUser(tempUsers.get(i));
			//Remove reference of group from user data
			tempUserData.unsetMod(groupid);
			//Save user data
			this.updateUser(tempUserData);
		}
		groups.remove(groupid);
		tempStatus.setStatus(StatusType.SUCCESS);
		return tempStatus;
	}
	/** Gets User Data Object for Specified ID
	 * Preconditions: id is an integer
	 * Postconditions: Returns a UserData object for the specified id
	 * @param id
	 * @return {@link UserData} object containing the user data associated with the user id.
	 */
	public UserData getUser(int id) {
		Vector<String> temp = users.get(id);
		UserData tempUD = new UserData(id,temp.get(0), temp.get(1), temp.get(2), temp.get(3));
		return tempUD;
	}

	/** Removes the specified user from the specified group.
	 * Preconditions: userid and groupid are integers
	 * Postconditions: Returns a Status object that contains the result of the method call 
	 * @param userid the ID of the user to remove.
	 * @param groupid the ID of the group to remove from.
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status removeUserFromGroup(int userid, int groupid) {
		Status tempStatus = new Status();
		UserData tempUserData = getUser(userid);
		tempUserData.unsetUser(groupid);
		this.updateUser(tempUserData);
		GroupData tempGroupData = getGroup(groupid);
		tempGroupData.unsetUser(userid);
		this.updateGroup(tempGroupData);
		
		//Return Status
		tempStatus.setStatus(StatusType.SUCCESS);
		return tempStatus;
	}

	/** Gets user login info from database
	 * Preconditions: uname is an integer and pw is a String
	 * Postconditions: Returns a UserData object for the user specified by uname
	 * @param uname the username of the user.
	 * @param pw the password of the user.
	 * @return a {@link User} object for the logged in, or failed, user.
	 */
	public User login(String uname, String pw) {
		// TODO This is really bad but will work for now
		User user = new User(Logged.LOGGEDOFF, null);
		for(int i=1; i<=users.size();i++) {
			if (users.get(i).get(0).equals(uname) && users.get(i).get(1).equals(pw)) {
				user.setUserData(new UserData(i,users.get(i).get(0),users.get(i).get(1),users.get(i).get(2),users.get(i).get(3)));
				user.setStatus(Logged.USER);
				return user;
			}
		}
		user.setStatus(Logged.INVALID);
		return user;
	}

	/** Adds a User to the database 
	 * Preconditions: ud is a valid UserData object.
	 * Postconditions: Returns a Status object that contains the result of the method call
	 * @param ud the {@link UserData} object to add.
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status addUser(UserData ud) {
		Status tempStatus = new Status(StatusType.UNSUCCESSFUL);
		int uniqueID = getUniqueUserId();
		String uniqueStringID = Integer.toString(uniqueID);
		users.put(uniqueID, addData(ud.getUName(), ud.getPW(), "~", "~", uniqueStringID));
		tempStatus.setStatus(StatusType.SUCCESS);
		return tempStatus;
	}

	/** Updates a User in the database
	 * Preconditions: ud is a valid UserData object.
	 * Postconditions: Returns a Status object that contains the result of the method call
	 * @param ud {@link UserData} object with updated information. 
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status updateUser(UserData ud) {
		Status tempStatus = new Status(StatusType.UNSUCCESSFUL);
		String idString = Integer.toString(ud.getId());
		users.put(ud.getId(), addData(ud.getUName(), ud.getPW(),StringParser.unParseArray(ud.getModOf()), StringParser.unParseArray(ud.getUserOf()), idString));
		tempStatus.setStatus(StatusType.SUCCESS);
		return tempStatus;
	}

}
