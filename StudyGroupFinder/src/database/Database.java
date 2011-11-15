package database;

import domainlogic.Status;
import domainlogic.User;

/** The database interface for the Study Group System. */
public interface Database {

	/** Adds a Group to the database 
	 * Precondition: GroupData object is valid. All data object variables have been validated.
	 * Postcondition: Method will return a Status object that contains the result of the method call  
	 * @param gd the {@link GroupData} object to add.
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status addGroup(GroupData gd);
	
	/** Gets the specified GroupData from the database.
	 * Precondition: The group id is an integer.
	 * Postcondition: Method will return the GroupData object associated with the id number 
	 * @param id the ID of the group to get.
	 * @return the c of the group.
	 */
	public GroupData getGroup(int id);
	
	/** Adds the specified user to the specified group
	 * Preconditions: userid and groupid are integers.
	 * Postconditions: return a Status object that contains the result of the method call  
	 * @param userid the ID of the user to add.
	 * @param groupid the ID of the group to add to.
	 * @return {@link Status} object that holds information on what happened. 
	 */
	public Status addUserToGroup(int userid, int groupid);
	
	/** Removes the specified user from the specified group.
	 * Preconditions: userid and groupid are integers
	 * Postconditions: Returns a Status object that contains the result of the method call 
	 * @param userid the ID of the user to remove.
	 * @param groupid the ID of the group to remove from.
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status removeUserFromGroup(int userid, int groupid);
	
	/** Gets user login info from database
	 * Preconditions: uname is an integer and pw is a String
	 * Postconditions: Returns a UserData object for the user specified by uname
	 * @param uname the username of the user.
	 * @param pw the password of the user.
	 * @return a {@link User} object for the logged in, or failed, user.
	 */
	public User login(String uname, String pw);
	
	/** Adds a User to the database 
	 * Preconditions: ud is a valid UserData object.
	 * Postconditions: Returns a Status object that contains the result of the method call
	 * @param ud the {@link UserData} object to add.
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status addUser(UserData ud);
	
	/** Updates a User in the database
	 * Preconditions: ud is a valid UserData object.
	 * Postconditions: Returns a Status object that contains the result of the method call
	 * @param ud {@link UserData} object with updated information. 
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status updateUser(UserData ud);
	
	/** Deletes a Group
	 * Preconditions: groupID is an integer
	 * Postconditions: Returns a Status object that contains the result of the method call
	 * @param groupID an id for the group to delete
	 * @return {@link Status} object that holds information on what happened
	 */
	public Status deleteGroup(int groupID);
	
	/** Gets User Data Object for Specified ID
	 * Preconditions: id is an integer
	 * Postconditions: Returns a UserData object for the specified id
	 * @param id
	 * @return {@link UserData} object containing the user data associated with the user id.
	 */
	public UserData getUser(int id);
	
	/** Updates Group Data in Database
	 * Preconditions: gd is a valid GroupData object
	 * Postconditions: Returns a Status object that contains the result of the method call
	 * @param gd {@link GroupData} GroupData object
	 * @return {@link Status} object that holds information on what happened
	 */
	public Status updateGroup(GroupData gd);
}
