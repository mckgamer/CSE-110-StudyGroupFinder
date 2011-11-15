package database;

import domainlogic.Status;
import domainlogic.StatusType;
import domainlogic.User;
import domainlogic.User.Logged;
import util.MySqlDatabaseHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * Implementation of the {@link Database} interface using a JDBC connection to a MySQL
 * database.
 * 
 * Additional functionality has been relocated to {@link util.MySqlDatabaseHelper} 
 * 
 * @author Mike Claffey
 */
public class MySqlDatabase implements Database {

	/* local variables for the database connection */
    private Connection mySQLConnection;
        
    /* database helper */
    private MySqlDatabaseHelper dbh;
    
	/* Initialize the driver */
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
    

	/**
	 * Self-test of all methods with extensive output to console
	 * @param args - not implemented
	 */
	public static void main(String[] args) {
		showDebug = true;
		Status st;
		
		print("--Initializing database and connection");
		MySqlDatabase db = new MySqlDatabase("jdbc:mysql://localhost:3306/testdb", "root", "");
		
		print("--Creating and populating database");
		db.dbh.buildDatabase();
		db.dbh.populateDatabase();
		
		print("--Logging in");
		User mike = db.login("mike", "pw");
		int mike_id = mike.getUserData().getId();
		print("Log in status: " + mike.getStatus());
		
		print("--Getting user data");
		UserData mikeData = db.getUserData(mike_id);
		print(mikeData.toString());
		
		print("--Updating user: mike to michael");
		UserData michaelData = new UserData(
				mikeData.getId(), "michael", mikeData.getPW(), mikeData.getModOf(), mikeData.getUserOf());
		st = db.updateUser(michaelData);
		print("Status: " + st.getStatus());
		db.dbh.printUsers();
		
		print("--Creating new user: bob, a member of group 1");
		UserData bob = new UserData(0, "bob", "pw", "~", "1~");
		st = db.addUser(bob);
		int bob_id = 2;
		print("Status: " + st.getStatus());
		db.dbh.printUsers();
		
		print("--Creating new group: group2 with no members");
		GroupData gp2 = new GroupData(0, "group2", "cse111", "~", "~");
		st = db.addGroup(gp2);
		int grp2_id = 2;
		print("Status: " + st.getStatus());	
		db.dbh.printGroups();
		
		print("--Getting group data of group2");
		GroupData gp3 = db.getGroup(grp2_id);
		print(gp3.toString());
		
		print("--Update group");
		print("Not yet implemented in Database interface");

		print("--Adding bob to group2");
		st = db.addUserToGroup(bob_id, grp2_id);
		print("Status: " + st.getStatus());	
		db.dbh.printMemberships();

		print("--Removing michael from group 1");
		st = db.removeUserFromGroup(mike_id, 1);
		print("Status: " + st.getStatus());	
		db.dbh.printMemberships();
		
		print("--Displaying final state of database");
		db.dbh.printDatabase();
		
		print("Closing connection");
		db.closeConnection();
		
	}    

	/* Shortcut to print to console during debugging */
    private static boolean showDebug = false;
    /**
     * Print is a shortcut to System.out.println that can be turned
     * off by setting the boolean showDebug
     * @param s is the {@link String} to display in the console
     * @return void
     */
	private static void print(String s) {
		if (showDebug) {System.out.println(s);}
	}	
	/**
	 * The constructor requires information to establish the connection
	 * to the MySQL database
	 * @param url - a {@link String} to the database, such as "jdbc:mysql://localhost:3306/testdb"
	 * @param user - a {@link String} specifying the database login name, typically "root"
	 * @param pass - a {@link String} specifying the database login password, typically "" for root
	 */
    MySqlDatabase(String url, String user, String pass) {    	
        try {
			this.mySQLConnection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        dbh = new MySqlDatabaseHelper(this.mySQLConnection);
    }
    
	/**
	 * @see database.Database#login(java.lang.String, java.lang.String)
	 */
	@Override
	public User login(String uname, String pw) {
		User user = new User(Logged.LOGGEDOFF, null);
		int user_id = loginGetUserId(uname, pw);
		if (user_id==0) {
			user.setStatus(Logged.INVALID);			
		} else {
			user.setUserData(getUserData(user_id));
			user.setStatus(Logged.USER);		
		}
		return user;		
	}

	/**
	 * A helper function for login() that finds the user id based on username
	 * and password. 
	 * @param uname - the {@link String} of the username
	 * @param password - the {@link String} of the password
	 * @return user_id - the int id of matching user. Returns 0 if no match.  
	 */
	private int loginGetUserId(String uname, String password) {
		int user_id = 0;
		try {
			/* Query for matching user */
			ResultSet res = dbh.sqlQuery("SELECT id FROM `users` " +
									 "WHERE (" +
									 "`name`='" + uname + "'" +
									 " AND " +
									 "`password`='" + password + "'" +
									 ");");
			
			/* If results were found, return the id */
			if (res.next()) {
				user_id = res.getInt("id");
			}
			
			/* Close statement and result set */
			res.getStatement().close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user_id;
		
	}
	
	/**
	 * @see database.Database#getUserData(int)
	 */
	@Override
	public UserData getUserData(int id) {
		UserData ud = null;
		String uname = "", password = "";
		
		try {
			/* Query for matching user */
			ResultSet res = dbh.sqlQuery("SELECT * FROM `users` " +
									 "WHERE (`id` = '" + id + "');");
			
			/* If results were found, save variables */
			if (res.next()) {
				uname = res.getString("name");
				password = res.getString("password");
			}

			/* Close statement and result set */
			res.getStatement().close();

		} catch (SQLException e) {
			e.printStackTrace();
			return ud;
		}	
				
		/* now query for memberships */
		ArrayList<Integer> userof = getMembershipGroupsForUser(id);
		ArrayList<Integer> modof  = getMembershipModsForUser(id);				
				
		/* Add data to UserData and return */
		ud = new UserData(id, uname, password, modof, userof);					
		return ud;
	}
	
	/**
	 * A helper function for finding which users belong to groups and
	 * vice versa. It takes SQL for a query and gathers all the values of the 
	 * first integer column into an array.
	 * 
	 * @param sqlCommand - a {@link String} for a properly formatted SQL query
	 * @return ids - an {@link ArrayList} of integers from the query results 
	 * @see getMembershipUsersOfGroup
	 * @see getMembershipModsOfGroup
	 * @see getMembershipGroupsForUser 
	 * @see getMembershipModsForUser
	 */
	private ArrayList<Integer> getMembershipIds(String sqlCommand) {
		ArrayList<Integer> memIds = new ArrayList<Integer>();
		ResultSet memRes = dbh.sqlQuery(sqlCommand);
		try {
			while (memRes.next()) {
				memIds.add(memRes.getInt(1));
			}
			
			/* Close statement and result set */
			memRes.getStatement().close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memIds;
	}
	/**
	 * Find all users (including mods) of a given group
	 * @param group_id - an integer for the group id to search
	 * @return an {@link ArrayList} of user_id integers that are in the group
	 */
	private ArrayList<Integer> getMembershipUsersOfGroup(int group_id) {
		ArrayList<Integer> memIds = getMembershipIds(
				"SELECT user_id FROM memberships " +
				"WHERE group_id = " + group_id + ";");
		return memIds;
	}
	/**
	 * Find all moderators of a group
	 * @param group_id - an integer for the group id to search
	 * @return an {@link ArrayList} of user_id integers that are moderators of the group
	 */
	private ArrayList<Integer> getMembershipModsOfGroup(int group_id) {
		ArrayList<Integer> memIds = getMembershipIds(
				"SELECT user_id FROM memberships " +
				"WHERE group_id = " + group_id + " AND is_mod = TRUE;");
		return memIds;
	}
	/** 
	 * Find all groups that a user belongs to
	 * @param user_id - integer of user id to search
	 * @return an {@link ArrayList} of group id integers that the user belongs to
	 */
	private ArrayList<Integer> getMembershipGroupsForUser(int user_id) {
		ArrayList<Integer> memIds = getMembershipIds(
				"SELECT group_id FROM memberships " +
				"WHERE user_id = " + user_id + ";");
		return memIds;
	}
	/**
	 * Find all groups that a user is a moderator of
	 * @param user_id - integer of user id to search
	 * @return an {@link ArrayList} of group id integers that the user is a moderator of
	 */
	private ArrayList<Integer> getMembershipModsForUser(int user_id) {
		ArrayList<Integer> memIds = getMembershipIds(
				"SELECT group_id FROM memberships " +
				"WHERE user_id = " + user_id + " AND is_mod=TRUE;");
		return memIds;
	}
	
	/**
	 * @see database.Database#addUser(database.UserData)
	 */
	@Override
	public Status addUser(UserData ud) {
		Status st = new Status(StatusType.UNSUCCESSFUL);

		/* Add data to user table */
		dbh.sqlExecute("INSERT INTO `users` (`name`, `password`) " +
				  "VALUES ('" + ud.getUName() + "', '" + ud.getPW() + "');");
		
		/* Get user id */
		int user_id = dbh.getMaxId("users");
		
		/* Add memberships */
		setMembership(user_id, ud.getUserOf(), false);
		setMembership(user_id, ud.getModOf(), true);

		/* return status */
		st.setStatus(StatusType.SUCCESS);
		return st;
	}
	
	/**
	 * @see database.Database#updateUser(database.UserData)
	 */
	@Override
	public Status updateUser(UserData ud) {
		Status st = new Status(StatusType.UNSUCCESSFUL);
		
		/* Find the user in the database with update-able query*/
		ResultSet res = dbh.sqlUpdatable("SELECT * FROM `users` " +
								 "WHERE (`id` = '" + ud.getId() + "');");
		
		/* Update the user fields */
		try {
			if (res.next()) {
				res.updateString("name", ud.getUName());
				res.updateString("password", ud.getPW());
				res.updateRow();
			}
			
			/* Close statement and result set */
			res.getStatement().close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/* Update the user memberships */
		setMembership(ud.getId(), ud.getUserOf(), false);
		setMembership(ud.getId(), ud.getModOf(), true);
		
		st.setStatus(StatusType.SUCCESS);
		return st;
	}

	/**
	 * Add or set the membership of a user for a set of groups
	 * @param user_id - integer of user id
	 * @param group_ids - an {@link ArrayList} of groups to apply the user to
	 * @param is_mod - a boolean to indicator if the user joins as a moderator (TRUE) or user (FALSE) 
	 */
	private void setMembership(int user_id, ArrayList<Integer> group_ids, boolean is_mod) {
		for (int i = 0; i < group_ids.size(); i++) {
			if (is_mod) {
				setMembershipMod(user_id, group_ids.get(i));
			} else {
				setMembershipUser(user_id, group_ids.get(i));
			}
		}
	}
	/**
	 * Add or set the membership of a set of users for a given group
	 * @param user_ids - an {@link ArrayList} of user id integers to apply to the group
	 * @param group_id - integer of group id
	 * @param is_mod - a boolean to indicator if all users joins as a moderator (TRUE) or user (FALSE)
	 */
	private void setMembership(ArrayList<Integer> user_ids, int group_id, boolean is_mod) {
		for (int i = 0; i < user_ids.size(); i++) {
			if (is_mod) {
				setMembershipMod(user_ids.get(i), group_id);
			} else {
				setMembershipUser(user_ids.get(i), group_id);
			}
		}
	}
	/**
	 * Remove a user from a group. Works even if user is not currently a member.
	 * @param user_id - integer of user id
	 * @param group_id - integer of group id
	 */
	private void setMembershipNone(int user_id, int group_id) {
		dbh.sqlExecute("DELETE memberships.* FROM memberships " +
				  "WHERE (user_id=" + user_id + " AND group_id=" + group_id + ");");
	}
	/**
	 * Add a user to a group. Works regardless of user's current group status.
	 * @param user_id - integer of user id
	 * @param group_id - integer of group id
	 */
	private void setMembershipUser(int user_id, int group_id) {
		setMembershipNone(user_id, group_id);
		dbh.sqlExecute("INSERT INTO `memberships` (`user_id`, `group_id`, `is_mod`) " +
				  "VALUES ('" + user_id + "', '" + group_id + "', FALSE);");
	}
	/**
	 * Add a user to a group as moderator. Works regardless of user's current group status.
	 * @param user_id - integer of user id
	 * @param group_id - integer of group id
	 */
	private void setMembershipMod(int user_id, int group_id) {
		setMembershipNone(user_id, group_id);
		dbh.sqlExecute("INSERT INTO `memberships` (`user_id`, `group_id`, `is_mod`) " +
				  "VALUES ('" + user_id + "', '" + group_id + "', TRUE);");
	}
	
	
	/**
	 * @see database.Database#addGroup(database.GroupData)
	 */
	@Override
	public Status addGroup(GroupData gd) {
		Status st = new Status(StatusType.UNSUCCESSFUL);

		/** Add data to groups table **/
		dbh.sqlExecute("INSERT INTO `groups` (`name`, `course`) " +
				  "VALUES ('" + gd.getName() + "', '" + gd.getCourse() + "');");		

		/** Get group id **/
		int group_id = dbh.getMaxId("groups");
		
		/** Add memberships **/
		setMembership(gd.getUsers(), group_id, false);
		setMembership(gd.getMods(), group_id, true);
		
		/** return status **/
		st.setStatus(StatusType.SUCCESS);
		return st;
	}

	/**
	 * @see database.Database#getGroup(int)
	 */
	@Override
	public GroupData getGroup(int id) {
		GroupData gd = null;
		String name = "", course = "";
		
		try {
			/** Query for matching group **/
			ResultSet res = dbh.sqlQuery("SELECT * FROM `groups` " +
									 "WHERE (`id` = '" + id + "');");
			
			/** If results were found, save variables **/
			if (res.next()) {
				name = res.getString("name");
				course = res.getString("course");
			}
			
			/* Close statement and result set */
			res.getStatement().close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return gd;
		}	

		/** now query for memberships **/
		ArrayList<Integer> users = getMembershipUsersOfGroup(id);
		ArrayList<Integer> mods  = getMembershipModsOfGroup(id);				

		/** Add data to GroupData **/
		gd = new GroupData(id, name, course, mods, users);
		return gd;
	}

	/**
	 * @see database.Database#addUserToGroup(int, int)
	 */
	@Override
	public Status addUserToGroup(int userid, int groupid) {
		Status st = new Status(StatusType.UNSUCCESSFUL);
		setMembershipUser(userid, groupid);
		st.setStatus(StatusType.SUCCESS);
		return st;
	}

	/**
	 * @see database.Database#removeUserFromGroup(int, int)
	 */
	@Override
	public Status removeUserFromGroup(int userid, int groupid) {
		Status st = new Status(StatusType.UNSUCCESSFUL);
		setMembershipNone(userid, groupid);
		st.setStatus(StatusType.SUCCESS);
		return st;
	}

	/**
	 * @see database.Database#deleteGroup(int)
	 */
	@Override
	public Status deleteGroup(int groupID) {
		Status st = new Status(StatusType.UNSUCCESSFUL);
		
		/** Delete memberships **/
		dbh.sqlExecute("DELETE memberships.* FROM memberships " +
				  "WHERE (group_id=" + groupID + ");");

		/** Delete group **/
		dbh.sqlExecute("DELETE groups.* FROM groups " +
				  "WHERE (id=" + groupID + ");");

		st.setStatus(StatusType.SUCCESS);
		return st;
	}


	/**
	 * Close the JDBC connection to MySql database
	 * @see database.Database#closeConnection()
	 */
	@Override
	public void closeConnection() {
        try {
			mySQLConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
