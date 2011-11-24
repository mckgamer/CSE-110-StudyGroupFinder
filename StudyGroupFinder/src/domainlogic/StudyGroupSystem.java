package domainlogic;

import database.Database;
import database.GroupData;
import database.MapDatabase;
import database.MySqlDatabase.InvalidDatabaseID;
import database.SearchData;
import database.UserData;
import domainlogic.User;
import domainlogic.User.Logged;


/**
 * StudyGroupSystem class is the domain logic class for the Study Group System.
 * The gui of the system calls methods within this class to carry out user
 * methods. The class interfaces with the database. 
 * @author Robert Filiczkowski
 * Date Created 10/21/2011
 * Last Updated 11/15/2011
 */
public class StudyGroupSystem {
	
	//Class Variables
	private Database database = null;
	private User sgfUser;
	
	/**
	 * Class Constructor without database parameter
	 * Must subsequently use {@link setDatabase}
	 */
	public StudyGroupSystem() {} 
	
	/**
	 * Class Constructor that takes a Database as a parameter
	 * @param database
	 */
	public StudyGroupSystem(Database database){ 
		this.database = database;
	}
	
	/**
	 * Set or change the database of the system
	 * @param database is an implementation of the {@link Database} class, 
	 * such as {@link MapDatabase} or {@link database.MySqlDatabase}
	 */
	public void setDatabase(Database database) {
		this.database = database;
	}
	
	/**Login Method
	 * Preconditions: username and pw are Strings
	 * PostConditions: User will be logged into the system
	 * @param userName User name for login
	 * @param pw password for user
	 * @return Logged Enumerator Type
	 */
	public Logged login(String userName, String pw){
		//call database
		if(userName == null || pw == null)
			return Logged.INVALID;
		this.sgfUser = database.login(userName, pw);
		return sgfUser.getStatus();
	}
	
	/**Checks to see if user is logged in
	 * Precondition: None
	 * PostCondition: None
	 * @return Boolean True or False
	 */
	public boolean isLogged(){
		//Return user status
		if(sgfUser == null)
			return false;
		else if(sgfUser.getStatus() == Logged.USER || sgfUser.getStatus() == Logged.ADMIN)
			return true;
		else
			return false;
	}
	
	/** Creates a new user into database
	 * Preconditions: Valid UserData object
	 * PostConditions: Creates a new user in the database
	 * @param UserData Object
	 * @return {@link Status} Status object that holds information on what happened
	 */
	public Status createUser(UserData u){
		Status tempStatus = new Status();
		// Create user data object to send to the database
		UserData uData = u;
		
		//call to database to add user
		if(uData.validate())
			tempStatus = database.addUser(uData);
		
		return tempStatus;
	}
	/** Refreshes the user data from the database
	 * Preconditions: User is logged into the system user.getStatus() = users or admin
	 * Postconditions: updates current User object with data from database
	 * 
	 */
	public void refreshLoggedUser() {
		int user_id = sgfUser.getUserData().getId();
		try {
			sgfUser.setUserData(database.getUser(user_id));
		} catch (InvalidDatabaseID e) {
			System.err.println("Attempted to refreshLoggenUser with invalid user_id="+user_id);
			e.printStackTrace();
		}
	}
	
	/** Updates User Profile
	 * Preconditions: u is a valid UserData object
	 * Postconditions: Updates the user profile with data provided by user
	 * @param UserObject
	 * @return {@link Status} Status object that holds information on what happened
	 */
	public Status updateUserProfile(UserData u){
		Status tempStatus = new Status(StatusType.UNSUCCESSFUL);
		if(isLogged()){
			tempStatus = database.updateUser(u);
			return tempStatus;
		}
		else
			// Return program error if user is not logged in.
			return tempStatus;
	}
	
	/** User Loggoff
	 * Preconditions: user is logged into the system
	 * Postcondition: Changes the User objects status to LOGGEDOFF
	 * @return {@link Status} Status object that holds information on what happened
	 */
	public void logoff(){
		if (sgfUser != null) {
			sgfUser.logoff();
		}
	}

	/** Gets User Data For logged In User
	 * Preconditions: User must be logged into system
	 * Postconditions: Returns the UserData object associated with the user.
	 * @return UserData Object of the logged-in user
	 */
	public UserData getLoggedUser(){
		return sgfUser.getUserData();
	}
	/**Gets user data for a specific id
	 * Preconditions: id is an integer
	 * Postconditions: Returns the UserData object associated to the id
	 * @param id
	 * @return UserData Object
	 */
	public UserData getUser(int id){
		try {
			return database.getUser(id);
		} catch (InvalidDatabaseID e) {
			System.err.println("Attempted to access invalid user id =" + id);
			System.err.println("Returning an empty UserData object");
			e.printStackTrace();
			return new UserData();
		}
	}
	
	/** Create a new group
	 * Preconditions: gd is a Valid GroupData object
	 * Postconditions: Creates a new study group and saves it into the database
	 * @param gd is a GroupData object
	 * @return {@link Status} Status object that holds information on what happened
	 */
	public Status createNewGroup(GroupData gd){
		gd.setMod(sgfUser.getUserData().getId());
		Status tempStatus = new Status();
		tempStatus = database.addGroup(gd);
		return tempStatus;
		
	}
	/** Gets Group Data from ID
	 * Preconditions: id is an integer
	 * Postconditions: gets group data from database
	 * @param id is an id of a group
	 * @return GroupData Object
	 */
	public GroupData getGroup(int id){
		try {
			return database.getGroup(id);
		} catch (InvalidDatabaseID e) {
			System.err.println("Attempted to access invalid group id =" + id);
			System.err.println("Returning an empty GroupData object");
			e.printStackTrace();
			return new GroupData();
		}
	}
	
	/** Update GroupData
	 * Preconditions: gd is a valid GroupData object
	 * Postconditions: Updates the group data in the database
	 * @param gd is a GroupData object
	 * @return Status object that holds information on what happened
	 */
	public Status updateGroupData(GroupData gd){
		Status tempStatus = new Status();
		tempStatus = database.updateGroup(gd);
		return tempStatus;
	}
	/** Delete Group
	 * Preconditions: groupID is an integer
	 * Postconditions: Deletes the group associated with groupID from the database
	 * @param groupID is the groupID to delete
	 * @return Status object that holds information on what happened
	 */
	public Status deleteGroup(int groupID){
		Status tempStatus = new Status();
		tempStatus = database.deleteGroup(groupID);
		return tempStatus;
	}
	
	/** Adds user to group
	 * Preconditions: uid and gid are integers
	 * Postconditions: Adds the specified user to the specified group
	 * @param uid is the user id
	 * @param gid is the group ide
	 * @return Status object that holds information on what happened
	 */
	public Status joinGroup(int uid, int gid){
		Status tempStatus = new Status();
		tempStatus = database.setMembershipUser(uid, gid);
		return tempStatus;
	}
	
	/** Remove a user form a group
	 * Preconditions: userID and groupID are integers
	 * Postconditions: Removes user associated with userID from the group associated with groupID
	 * @param userID is the userid of the user to remove from the group
	 * @param groupID is the groupid to remove the user from
	 * @return Status object that holds information on what happened
	 */
	public Status removeUserFromGroup(int userID, int groupID){
		Status tempStatus = database.setMembershipNone(userID, groupID);
		return tempStatus;
		
	}
	
	/** This method searches for groups in the database with the specified search data.
	 * 
	 * @param search the SearchData to use to search with.
	 * @return the SearchData modified with the results of the search added.
	 */
	public SearchData searchGroups(SearchData search) {
		return search.setResults(database.searchGroups(search));
	}
	
	/** This method searches for users in the database with the specified search data.
	 * 
	 * @param search the SearchData to use to search with.
	 * @return the SearchData modified with the results of the search added.
	 */
	public SearchData searchUsers(SearchData search) {
		return search.setResults(database.searchUsers(search));
	}
	
	/** For the suggested groups logic. This takes the logged in Users profile and converts
	 * to a filter string for their suggested groups.
	 * 
	 * @return the filter string.
	 */
	public String getSuggestedTerms() {
		//TODO getLoggedUser().getCourses() this should just add some terms that the user applys to
		return "cse";
	}
	
	/** This method returns the Logged status of the currently logged in user.
	 * 
	 * @return the Logged status of the currently logged in user.
	 */
	public Logged getUserStatus() {
		return sgfUser.getStatus();
	}
	
	/** 
	 * Return basic description of database implementation
	 */
	public String toString() {
		return database.toString();
	}
}
