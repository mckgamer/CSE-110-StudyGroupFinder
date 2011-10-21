package database;

import domainlogic.Status;
import domainlogic.User;

import java.util.*;

public class VectorDatabase implements Database {
	
	private Vector<Vector<String>> users;
	private Vector<Vector<String>> groups;
	
	private int groupId = 3;
	private int userId = 3;
	
	/* Construct the Dummy Database */
	public VectorDatabase() {
		users = new Vector<Vector<String>>();
		populateUsers();
		populateGroups();
	}
	
	/* Helper Method to Populate Dummy User database */
	private void populateUsers() {
		//ID uname Password   List of groups to which the user is mod
		users.add(addData("1","Mike","pw","1~"));
		users.add(addData("2","Bob","pw","2~"));
	}
	
	/* Helper Method to Populate Dummy Groups database */
	private void populateGroups() {
		//ID  //Name  //Class studied  //List of mods
		groups.add(addData("1","The Group","CSE 110","1~"));
		groups.add(addData("2","Bobs Group","CSE 101","2~"));
	}
	
	/* Helper Method For Populating database */
	private Vector<String> addData(String... info) {
		Vector<String> temp = new Vector<String>();
		for (String i : info) {
			temp.add(i);
		}
		return temp;
	}
	
	@Override
	public Status addGroup(GroupData gd) {
		groups.add(addData(Integer.toString(groupId++), gd.getName(), gd.getClass(), gd.getMods()));
		return null;
	}

	@Override
	public GroupData getGroup(int id) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status addUser(UserData ud) {
		users.add(addData(Integer.toString(userId++), ud.getUName(), ud.getPW(), ud.getModOf()));
		return null;
	}

	@Override
	public Status updateUser(UserData ud) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		
	}

}
