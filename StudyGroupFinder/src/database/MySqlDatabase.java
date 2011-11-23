package database;

import domainlogic.Status;
import domainlogic.StatusType;
import domainlogic.User;
import domainlogic.User.Logged;
import util.MySqlDatabaseHelper;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.CommunicationsException;

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
    private String connectionString = "";
        
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
		MySqlDatabase db = new MySqlDatabase();
		
		print("--Initializing database and connection");
		boolean use_local = true;
		if (use_local)
			db.openConnection("jdbc:mysql://localhost:3306/testdb", "root", "");
		else
			db.openConnection("jdbc:mysql://afiend.selfip.net:3306/study_test", "studydb", "studydb");
		
		print("--Creating and populating database");
		db.dbh.buildDatabase();
		db.dbh.populateDatabase();
		
		print("--Logging in");
		User mike = db.login("mike", "pw");
		int mike_id = mike.getUserData().getId();
		print("Log in status: " + mike.getStatus());
		
		print("--Getting user data");
		UserData mikeData = db.getUser(mike_id);
		print(mikeData.toString());
		
		print("--Updating user: mike to michael");
		UserData michaelData = new UserData(
				mikeData.getId(), "michael", mikeData.getPW(), mikeData.getModOf(), mikeData.getUserOf());
		st = db.updateUser(michaelData);
		print("Status: " + st.getStatus() + " - Message: " + st.getMessage());
		db.dbh.printUsers();
		
		print("--Creating new user: bob, a member of group 1");
		UserData bob = new UserData(0, "bob", "pw", "", "");
		st = db.addUser(bob);
		int bob_id = 2;
		db.setMembershipUser(bob_id, 1);
		print("Status: " + st.getStatus() + " - Message: " + st.getMessage());
		db.dbh.printUsers();
		
		print("--Creating new group: group2 with no members");
		GroupData gp2 = new GroupData(0, "group2", "cse111", "~", "~");
		st = db.addGroup(gp2);
		int grp2_id = 2;
		print("Status: " + st.getStatus() + " - Message: " + st.getMessage());
		db.dbh.printGroups();
		
		print("--Getting group data of group2");
		GroupData gp3 = db.getGroup(grp2_id);
		print(gp3.toString());
		
		print("--Update group2 to cse222");
		gp2.course = "cse222";
		st = db.updateGroup(gp2);
		print("Status: " + st.getStatus() + " - Message: " + st.getMessage());
		db.dbh.printGroups();

		print("--Adding bob to group2 as moderator");
		st = db.setMembershipMod(bob_id, grp2_id);
		print("Status: " + st.getStatus() + " - Message: " + st.getMessage());
		db.dbh.printMemberships();

		print("--Removing michael from group 1");
		st = db.setMembershipNone(mike_id, 1);
		print("Status: " + st.getStatus() + " - Message: " + st.getMessage());
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
    public MySqlDatabase() {}
    
    /**
     * Open a connection to a database
     * @param url - a {@link String} address of database, for example: <code> "jdbc:mysql://localhost:3306/testdb"</code>
     * @param user - a {@link String} user login name for database (example: "root")
     * @param password - a {@link String} login password for database (can be empty, "")
     * @return {@link Status} 
     **/
    public Status openConnection(String url, String user, String password) {
    	Status st = new Status(StatusType.UNSUCCESSFUL);

        try {
			this.mySQLConnection = DriverManager.getConnection(url, user, password);
        } catch (CommunicationsException e) {
        	System.err.println("");
        	throw new RuntimeException("Could not open connection with database: " + url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        dbh = new MySqlDatabaseHelper(this.mySQLConnection);
        connectionString = user + "@" + url;
        
        /* Set status */
        st.setStatus(StatusType.SUCCESS);
        st.setMessage("Connection opened");
        return st;
    }
    
	/**
	 * Close the JDBC connection to MySql database
	 * @return {@link Status}
	 */
	public Status closeConnection() {
		Status st = new Status(StatusType.UNSUCCESSFUL);
		
        try {
			mySQLConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        /* Return status */
        st.setStatus(StatusType.SUCCESS);
        st.setMessage("Connection closed");
        return st;
	}

    
    @Override
    public String toString() {
    	return connectionString;
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
			user.setUserData(getUser(user_id));
			user.setStatus(Logged.USER);		
		}
		return user;		
	}

	/**
	 * Delete all current data and build tables for database
	 */
	public void buildDatabase() {
		this.dbh.buildDatabase();		
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
	public UserData getUser(int id) {
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
	 * Find all users (excluding moderators) of a given group
	 * @param group_id - an integer for the group id to search
	 * @return an {@link ArrayList} of user_id integers that are in the group
	 */
	private ArrayList<Integer> getMembershipUsersOfGroup(int group_id) {
		ArrayList<Integer> memIds = getMembershipIds(
				"SELECT user_id FROM memberships " +
				"WHERE group_id = " + group_id + " AND is_mod = FALSE;");
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
	 * Find all groups that a user belongs to (excluding moderators)
	 * @param user_id - integer of user id to search
	 * @return an {@link ArrayList} of group id integers that the user belongs to
	 */
	private ArrayList<Integer> getMembershipGroupsForUser(int user_id) {
		ArrayList<Integer> memIds = getMembershipIds(
				"SELECT group_id FROM memberships " +
				"WHERE user_id = " + user_id + " AND is_mod=FALSE;");
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

		/* Add place holder row in user table */
		dbh.sqlExecute("INSERT INTO `users` (`name`, `password`) " +
				  "VALUES ('" + ud.getUName() + "', '" + ud.getPW() + "');");
		
		/* Get user id */
		ud.id = dbh.getMaxId("users");
		
		/* Add user properties */
		updateUser(ud);
		
		/* return status */
		st.setStatus(StatusType.SUCCESS);
		st.setMessage("User successfully added");
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
				
		st.setStatus(StatusType.SUCCESS);
		st.setMessage("User properties updated");
		return st;
	}

	/** UNUSED FUNCTIONS
	 * Mike C: I assume these are unneeded and am seeing if commenting them out
	 * breaks anything.
	 */
	
	/**
	 * Add or set the membership of a user for a set of groups
	 * @param user_id - integer of user id
	 * @param group_ids - an {@link ArrayList} of groups to apply the user to
	 * @param is_mod - a boolean to indicator if the user joins as a moderator (TRUE) or user (FALSE) 
	private void setMembership(int user_id, ArrayList<Integer> group_ids, boolean is_mod) {
		for (int i = 0; i < group_ids.size(); i++) {
			if (is_mod) {
				setMembershipMod(user_id, group_ids.get(i));
			} else {
				setMembershipUser(user_id, group_ids.get(i));
			}
		}
	}
	 */
	
	/**
	 * Add or set the membership of a set of users for a given group
	 * @param user_ids - an {@link ArrayList} of user id integers to apply to the group
	 * @param group_id - integer of group id
	 * @param is_mod - a boolean to indicator if all users joins as a moderator (TRUE) or user (FALSE)
	private void setMembership(ArrayList<Integer> user_ids, int group_id, boolean is_mod) {
		for (int i = 0; i < user_ids.size(); i++) {
			if (is_mod) {
				setMembershipMod(user_ids.get(i), group_id);
			} else {
				setMembershipUser(user_ids.get(i), group_id);
			}
		}
	}
	 */
	
	/**
	 * Remove a user from a group. Works even if user is not currently a member.
	 * @param user_id - integer of user id
	 * @param group_id - integer of group id
	 */
	public Status setMembershipNone(int user_id, int group_id) {
		Status st = new Status(StatusType.UNSUCCESSFUL);
		
		dbh.sqlExecute("DELETE memberships.* FROM memberships " +
				  "WHERE (user_id=" + user_id + " AND group_id=" + group_id + ");");
		
		/* Return status */
		st.setStatus(StatusType.SUCCESS);
		st.setMessage("User removed from group");
		return st;
		
	}
	/**
	 * Add a user to a group. Works regardless of user's current group status.
	 * @param user_id - integer of user id
	 * @param group_id - integer of group id
	 */
	public Status setMembershipUser(int user_id, int group_id) {
		Status st = new Status(StatusType.UNSUCCESSFUL);
		
		dbh.sqlExecute("INSERT INTO `memberships` (`user_id`, `group_id`, `is_mod`) " +
				  "VALUES ('" + user_id + "', '" + group_id + "', FALSE);");

		/* Return status */
		st.setStatus(StatusType.SUCCESS);
		st.setMessage("User added to group as non-moderator");
		return st;

	}
	/**
	 * Add a user to a group as moderator. Works regardless of user's current group status.
	 * @param user_id - integer of user id
	 * @param group_id - integer of group id
	 */
	public Status setMembershipMod(int user_id, int group_id) {
		Status st = new Status(StatusType.UNSUCCESSFUL);
		
		setMembershipNone(user_id, group_id);
		dbh.sqlExecute("INSERT INTO `memberships` (`user_id`, `group_id`, `is_mod`) " +
				  "VALUES ('" + user_id + "', '" + group_id + "', TRUE);");
		
		/* Return status */
		st.setStatus(StatusType.SUCCESS);
		st.setMessage("User added to group as moderator");
		return st;
		
	}
	
	
	/**
	 * @see database.Database#addGroup(database.GroupData)
	 */
	@Override
	public Status addGroup(GroupData gd) {
		Status st = new Status(StatusType.UNSUCCESSFUL);

		/* Create place holder row in groups table */
		dbh.sqlExecute("INSERT INTO `groups` (`name`) " +
				  "VALUES ('" + gd.getName() + "');");		

		/* Get group id */
		gd.id = dbh.getMaxId("groups");
				
		/* Update group properties */
		updateGroup(gd);
		
		/* Add moderator */
		setMembershipMod(gd.getMods().get(0), gd.id);
		
		/** return status **/
		st.setStatus(StatusType.SUCCESS);
		st.setMessage("Group added successfully");
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

	@Override
	public Status updateGroup(GroupData gd) {
		Status st = new Status(StatusType.UNSUCCESSFUL);
		
		/* Find the group in the database with update-able query*/
		ResultSet res = dbh.sqlUpdatable("SELECT * FROM `groups` " +
								 "WHERE (`id` = '" + gd.getId() + "');");
		
		/* Update the user fields */
		try {
			if (res.next()) {
				res.updateString("name", gd.name);
				res.updateString("course", gd.course);
				res.updateRow();
			}
			
			/* Close statement and result set */
			res.getStatement().close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		st.setStatus(StatusType.SUCCESS);
		st.setMessage("Group properties updated");
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




}
