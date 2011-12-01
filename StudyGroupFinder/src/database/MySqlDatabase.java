package database;

import domainlogic.Status;
import domainlogic.StatusType;
import domainlogic.User;
import domainlogic.User.Logged;
import util.MySqlDatabaseHelper;
import util.MySqlDatabaseHelper.DuplicateDatabaseEntry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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

	/** Connection object to database **/
    private Connection mySQLConnection;
    /** Description of current connection **/
    private String connectionString = "";
    /** Reference to database helper object **/
    private MySqlDatabaseHelper dbh;
    
    /* Exceptions */
    public class InvalidDatabaseID extends Exception {
		private static final long serialVersionUID = 1L;
		InvalidDatabaseID() {}
    	InvalidDatabaseID(String msg) {super(msg);}    	
    };    
    
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
		
		print("--Logging in as admin");
		User admin = db.login("admin", "pw");
		print("Log in status: " + admin.getStatus());
		
		print("--Logging in as user mike");
		User mike = db.login("mike", "pw");
		int mike_id = mike.getUserData().getId();
		print("Log in status: " + mike.getStatus());
		
		print("--Getting user data");
		UserData mikeData = null;
		try {
			mikeData = db.getUser(mike_id);
		} catch (InvalidDatabaseID e) {
			e.printStackTrace();
		}
		print(mikeData.toString());
		
		print("--Updating user: mike to michael");
		mikeData.name = "michael";
		st = db.updateUser(mikeData);
		print("Status: " + st.getStatus() + " - Message: " + st.getMessage());
		db.dbh.printUsers();
		
		print("--Creating new user: bob, a member of group 1");
		UserData bob = new UserData(0, "bob", "pw", "", "","");
		bob.courses = "cse110, bio2";
		st = db.addUser(bob);
		int bob_id = 4;
		db.setMembershipUser(bob_id, 1);
		print("Status: " + st.getStatus() + " - Message: " + st.getMessage());
		db.dbh.printUsers();
		
		print("--Try creating duplicate user name");
		st = db.addUser(bob);
		print("Status: " + st.getStatus() + " - Message: " + st.getMessage());
		
		print("--Creating new group: group2 with no members");
		GroupData gp2 = new GroupData(0, "group2", "cse111", "~", "~");
		st = db.addGroup(gp2);
		int grp2_id = 2;
		print("Status: " + st.getStatus() + " - Message: " + st.getMessage());
		db.dbh.printGroups();
		
		print("--Getting group data of group2");
		GroupData gp3;
		try {
			gp3 = db.getGroup(grp2_id);
			print(gp3.toString());
		} catch (InvalidDatabaseID e) {
			e.printStackTrace();
		}
		
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
		
		print("--Searching users for mich");
		db.addUser(new UserData(0, "michelle", "password", "", "",""));
		int michelle_id = 5;
		SearchData sd = new SearchData("mich");
		sd.setResultData(db.searchUsers(sd));
		print(sd.toString());
		
		print("--Searching groups for cse");
		sd.setTerms("cse");
		sd.setResultData(db.searchGroups(sd));
		print(sd.toString());
		
		print("--Searching groups for michael");
		sd.setTerms(mikeData.courses);
		sd.setResultData(db.searchGroups(sd));
		print(sd.toString());
		
		print("--Deleting user michelle");
		st = db.deleteUser(michelle_id);
		print("Status: " + st.getStatus() + " - Message: " + st.getMessage());		
		
		print("--Deleting group 2");
		st = db.deleteGroup(2);
		print("Status: " + st.getStatus() + " - Message: " + st.getMessage());	
		
		print("--Checking for inactive users");
		// TODO fix date constructor
		java.util.Date d = new java.util.Date("1/1/2009");
		int inactiveCount = db.deleteInactiveUsersCount(d);
		print("Inactive users: " + inactiveCount);
		
		print("--Deleting inactive users");
		st = db.deleteInactiveUsers(d);
		print("Status: " + st.getStatus() + " - Message: " + st.getMessage());	
		
		print("--Displaying final state of database");
		db.dbh.printDatabase();
		
		print("Closing connection");
		db.closeConnection();
		
	}    

	/* Shortcut to print to console during debugging */
    private static boolean showDebug = true;
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

	/**
	 * Delete all current data and build tables for database
	 */
	public void buildDatabase() {
		this.dbh.buildDatabase();		
	}
    
	/**
	 * Delete all current data, build tables and re-populate
	 */
	public void buildDatabase(boolean repopulate) {
		this.dbh.buildDatabase();		
		if (repopulate)
			this.dbh.populateDatabase();
	}
    
    @Override
    public String toString() {
    	return connectionString;
    }
    
	@Override
	public User login(String name, String password) {
		User user = new User(Logged.LOGGEDOFF, null);
		
		/* Attempt to locate user record */
		ResultSet res = dbh.sqlUpdatable("SELECT * FROM `users` " +
				 "WHERE (" +
				 "`name`='" + name + "'" +
				 " AND " +
				 "`password`='" + password + "'" +
				 ");");
		
		/* Get user data */
		try {
			if (res.next()) {
				UserData ud = rowToUserData(res);
				user.setUserData(ud);
				if (ud.is_admin)
					user.setStatus(Logged.ADMIN);
				else
					user.setStatus(Logged.USER);
				
				/* Update login time */
				java.sql.Date now_time = new java.sql.Date(new java.util.Date().getTime());
				res.updateDate("last_login", now_time);
				res.updateRow();
				
			} else {
				user.setStatus(Logged.INVALID);			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;		
	}

		
	/**
	 * @throws InvalidDatabaseID 
	 * @see database.Database#getUserData(int)
	 */
	@Override
	public UserData getUser(int id) throws InvalidDatabaseID {
		UserData ud = new UserData();
		
		try {
			/* Query for matching user */
			ResultSet res = dbh.sqlQuery("SELECT * FROM `users` " +
									 "WHERE (`id` = '" + id + "');");
			
			/* If results were found, save variables */
			if (res.next()) {
				ud = rowToUserData(res);
			} else {
				throw new InvalidDatabaseID("No user with id="+id);
			}

			/* Close statement and result set */
			res.getStatement().close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ud;
	}
	
	/**
	 * Transfer data from a row in the users table to a UserData object
	 * @param res - a {@link ResultSet} from the user table
	 * @return {@link UserData}
	 */
	private UserData rowToUserData(ResultSet res) {
		UserData ud = new UserData();
		
		try {
			ud.id = res.getInt("id");
			ud.name = res.getString("name");
			ud.password = res.getString("password");
			ud.courses = res.getString("courses");
			ud.is_admin = (res.getInt("is_admin") == 1); //Dumb mysql bug fix
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/* now query for memberships */
		ud.userof = getMembershipGroupsForUser(ud.id);
		ud.modof = getMembershipModsForUser(ud.id);				
				
		/* Return data*/
		return ud;
	}

	/**
	 * Transfer data from a row in the groups table to a GroupData object
	 * @param res - a {@link ResultSet} from the group table
	 * @return {@link GroupData}
	 */
	private GroupData rowToGroupData(ResultSet res) {
		GroupData gd = new GroupData();
		
		try {
			gd.id = res.getInt("id");
			gd.name = res.getString("name");
			gd.course = res.getString("course");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/* now query for memberships */
		gd.users = getMembershipUsersOfGroup(gd.id);
		gd.mods  = getMembershipModsOfGroup(gd.id);				
				
		/* Return data*/
		return gd;
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
	
	@Override
	public Status addUser(UserData ud) {
		Status st = new Status(StatusType.UNSUCCESSFUL);

		/* Create record in database */
		int new_id = createUser(ud.getUName());

		/* If successful, add other properties */
		if (new_id > 0) {
			/* Add user properties */
			ud.setId(new_id);
			updateUser(ud);	

			st.setStatus(StatusType.SUCCESS);
			st.setMessage("User successfully added");
		}
		
		/* return status */
		return st;
	}
	
	@Override
	public int createUser(String username) {
		int new_id = 0;
		
		try {
			/* Add to table */
			new_id = dbh.sqlInsert("INSERT INTO `users` (`name`, `password`) " +
					  "VALUES ('" + username + "', '');");
		} catch (DuplicateDatabaseEntry e) {
			if (showDebug) e.printStackTrace();
			new_id = -1;
		}
		return new_id;
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
				res.updateString("name", ud.name);
				res.updateString("password", ud.password);
				res.updateString("courses", ud.courses);
				res.updateBoolean("is_admin", ud.is_admin);
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
		
		setMembershipNone(user_id, group_id);
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
	 * @throws DuplicateDatabaseEntry 
	 * @see database.Database#addGroup(database.GroupData)
	 */
	@Override
	public Status addGroup(GroupData gd) {
		Status st = new Status(StatusType.UNSUCCESSFUL);

		/* Create place holder row in groups table */
		int new_id = createGroup(gd.getName());
		
		/* Exit if error */
		if (new_id <= 0) { return st; }
		
		/* Update group properties */
		updateGroup(gd);
		
		/* Add moderator */
		for (int mod: gd.getMods()) setMembershipMod(mod, gd.id);
		
		/* Update status */
		st.setStatus(StatusType.SUCCESS);
		st.setMessage("Group added successfully");

		/* return status */
		return st;
	}

	@Override
	public int createGroup(String groupname) {
		int new_id = 0;
		
		try {
			/* Add to table */
			new_id = dbh.sqlInsert("INSERT INTO `groups` (`name`) " +
					  "VALUES ('" + groupname + "');");
		} catch (DuplicateDatabaseEntry e) {
			if (showDebug) e.printStackTrace();
			new_id = -1;
		}
		return new_id;
	}
	
	/**
	 * @throws InvalidDatabaseID 
	 * @see database.Database#getGroup(int)
	 */
	@Override
	public GroupData getGroup(int id) throws InvalidDatabaseID {
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
			} else {
				throw new InvalidDatabaseID("No group with id="+id);
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
	
	@Override
	public Status deleteUser(int user_id) {
		Status st = new Status(StatusType.UNSUCCESSFUL);
		
		/** Delete memberships **/
		dbh.sqlExecute("DELETE memberships.* FROM memberships " +
				  "WHERE (user_id=" + user_id + ");");

		/** Delete user **/
		dbh.sqlExecute("DELETE users.* FROM users " +
				  "WHERE (id=" + user_id + ");");

		st.setStatus(StatusType.SUCCESS);
		st.setMessage("User and associated memberships deleted successfully");
		return st;		
	}
	
	/**
	 * Delete inactive users from database
	 * <p>Selects users with a last_login date older than the specified data parameter
	 * or whose last_login is NULL. Also deletes associated memberships.</p>
	 * @param d - the login cutoff {@link Date} before which users are considered inactive
	 * @return {@link Status} indicating whether delete was successful
	 * @see {@link #deleteInactiveUsersCount}, {@link #deleteInactiveGroups}
	 */
	public Status deleteInactiveUsers(Date d) {
		Status st = new Status(StatusType.UNSUCCESSFUL);
		java.sql.Date sqlDate = new java.sql.Date(d.getTime()); 
		
		// TODO: Don't delete users that are sole moderator of groups
		
		/* Delete memberships */
		dbh.sqlExecute("DELETE memberships.* " + 	 
				"FROM memberships " + 
				"JOIN users  " +
				"ON memberships.user_id=users.id " +
				"WHERE last_login < '" + sqlDate + "' " + 
				"OR last_login IS NULL;");
		
		/* Delete users */
		int rowCount = dbh.sqlExecute("DELETE users.* FROM users " +
				"WHERE last_login < '" + sqlDate + "' " + 
				"OR last_login IS NULL;");
		
		st.setStatus(StatusType.SUCCESS);
		st.setMessage("" + rowCount + " users and their associated memberships deleted successfully");
		return st;		
	}
	
	/**
	 * Count the number of inactive users in the database
	 * <p>Selects users with a last_login date older than the specified data parameter
	 * or whose last_login is NULL. Also deletes associated memberships.</p>
	 * @param d - the login cutoff {@link Date} before which users are considered inactive
	 * @return int indicating number of users that are inactive
	 * @see {@link #deleteInactiveUsers}
	 */
	public int deleteInactiveUsersCount(Date d) {
		int userCount = 0;
		java.sql.Date sqlDate = new java.sql.Date(d.getTime()); 
		ResultSet res = dbh.sqlQuery("SELECT COUNT(`id`) as 'user_count' " +
				"FROM `users` WHERE last_login < '" + sqlDate + "' " + 
				"OR last_login IS NULL;");
		try {
			if (res.next())
				userCount = res.getInt("user_count");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userCount;
	}
	
	/**
	 * Delete groups that do not have any users
	 * <strong>Not yet implemented</strong>
	 * <p>Selects users with a last_login date older than the specified data parameter
	 * or whose last_login is NULL. Also deletes associated memberships.</p>
	 * @return {@link Status} indicating whether delete was successful
	 * @see {@link #deleteInactiveUsers}
	 */
	public Status deleteInactiveGroups() {
		// TODO
		Status st = new Status(StatusType.UNSUCCESSFUL);
		
		/* Delete users */
//		int rowCount = dbh.sqlExecute("DELETE groups.* FROM groups " +
//				"WHERE ...");
//		
//		st.setStatus(StatusType.SUCCESS);
		st.setMessage("Not yet implemented");
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
		st.setMessage("Group and related memberships deleted successfully");
		return st;
	}
	
	@Override
	public ArrayList<UserData> searchUsers(SearchData criteria) {
		ArrayList<UserData> foundUsers = new ArrayList<UserData>(); 

		/* Build SQL WHERE clause */
		ArrayList<String> fieldnames = new ArrayList<String>();
		fieldnames.add("name");
		// fieldnames.add("courses");
		String sqlWhere = criteria.getSql(fieldnames);

		/* Build SQL query
		 * If sqlWhere is empty, return all records */
		String sqlQuery;
		if (sqlWhere.isEmpty())
			sqlQuery = "SELECT * from `users`;";
		else
			sqlQuery = "SELECT * from `users` WHERE " + sqlWhere + ";";
		
		
		try {
			/* Run query */
			ResultSet res = dbh.sqlQuery(sqlQuery);
			
			/* Aggregate results */
			while (res.next()) {
				foundUsers.add(rowToUserData(res));
			}
			
			/* Close statement and result set */
			res.getStatement().close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foundUsers;
	}
	
	@Override
	public ArrayList<GroupData> searchGroups(SearchData criteria) {
		ArrayList<GroupData> foundGroups = new ArrayList<GroupData>(); 

		/* Build SQL WHERE clause */
		ArrayList<String> fieldnames = new ArrayList<String>();
		fieldnames.add("name");
		fieldnames.add("course");
		String sqlWhere = criteria.getSql(fieldnames);
		
		/* Build SQL query
		 * If sqlWhere is empty, return all records */
		String sqlQuery;
		if (sqlWhere.isEmpty())
			sqlQuery = "SELECT * from `groups`;";
		else
			sqlQuery = "SELECT * from `groups` WHERE " + sqlWhere + ";";
		
		try {
			/* Run query */
			ResultSet res = dbh.sqlQuery(sqlQuery);
			
			/* Aggregate results */
			while (res.next()) {
				foundGroups.add(rowToGroupData(res));
			}
			
			/* Close statement and result set */
			res.getStatement().close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foundGroups;
	}




}
