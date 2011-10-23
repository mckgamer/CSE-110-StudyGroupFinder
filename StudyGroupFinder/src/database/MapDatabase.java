package database;
import domainlogic.Status;
import domainlogic.StatusType;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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
		//ID uname Password   List of groups to which the user is mod, unique user id
		int uniqueId = getUniqueUserId();
		String userIdString = Integer.toString(uniqueId);
		users.put(uniqueId, addData("Mike","pw","1~",userIdString));
		
		uniqueId = getUniqueUserId();
		userIdString = Integer.toString(uniqueId);
		users.put(uniqueId, addData("Bob","pw","2~", userIdString));
	}
	
	
	/* Helper Method to Populate Dummy Groups database */
	private void populateGroups() {
		//ID  //Name  //Class studied  //List of mods
		groups.put(getUniqueGroupId(), addData("The Group","CSE 110","1~"));
		groups.put(getUniqueGroupId(), addData("Bobs Group","CSE 101","2~"));
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
	@Override
	public Status addGroup(GroupData gd) {
		groups.put(getUniqueGroupId(), addData(gd.getName(), gd.getCourse(), "getMods()"));
		return null;
	}

	@Override
	public GroupData getGroup(int id) {
		if (groups.containsKey(id)) {
			Vector<String> temp = groups.get(id);
			GroupData found = new GroupData(id, temp.get(0), temp.get(1), temp.get(2));
			return found;
		}
		return null;
	}

	@Override
	public Status addUserToGroup(int userid, int groupid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status removeUserFromGroup(int userid, int groupid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User login(String uname, String pw) {
		// TODO This is really bad but will work for now
		User user = new User(Logged.LOGGEDOFF, null);
		for(int i=1; i<=users.size();i++) {
			if (users.get(i).get(0).equals(uname) && users.get(i).get(1).equals(pw)) {
				user.setUserData(new UserData(i,users.get(i).get(0),users.get(i).get(1),users.get(i).get(2)));
				user.setStatus(Logged.USER);
				return user;
			}
		}
		user.setStatus(Logged.INVALID);
		return user;
	}

	@Override
	public Status addUser(UserData ud) {
		Status tempStatus = new Status(StatusType.UNSUCCESSFUL);
		int uniqueID = getUniqueUserId();
		String uniqueStringID = Integer.toString(uniqueID);
		users.put(uniqueID, addData(ud.getUName(), ud.getPW(), "getModOf()", uniqueStringID));
		tempStatus.setStatus(StatusType.SUCCESS);
		return tempStatus;
	}

	@Override
	public Status updateUser(UserData ud) {
		Status tempStatus = new Status(StatusType.UNSUCCESSFUL);
		String idString = Integer.toString(ud.getId());
		users.put(ud.getId(), addData(ud.getUName(), ud.getPW(), "ModOf()", idString));
		tempStatus.setStatus(StatusType.SUCCESS);
		return tempStatus;
	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub

	}

}
