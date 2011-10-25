package database;

import domainlogic.Status;
import domainlogic.User;

/** The database interface for the Study Group System. */
public interface Database {

	/** Adds a Group to the database 
	 * 
	 * @param gd the {@link GroupData} object to add.
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status addGroup(GroupData gd);
	
	/** Gets the specified GroupData from the database.
	 * 
	 * @param id the ID of the group to get.
	 * @return the {@link GroupData} of the group.
	 */
	public GroupData getGroup(int id);
	
	/** Adds the specified user to the specified grouo
	 * 
	 * @param userid the ID of the user to add.
	 * @param groupid the ID of the group to add to.
	 * @return {@link Status} object that holds information on what happened. 
	 */
	public Status addUserToGroup(int userid, int groupid);
	
	/** Removes the specified user from the specified group.
	 * 
	 * @param userid the ID of the user to remove.
	 * @param groupid the ID of the group to remove from.
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status removeUserFromGroup(int userid, int groupid);
	
	/** Logs the user into the database
	 * 
	 * @param uname the username of the user.
	 * @param pw the password of the user.
	 * @return a {@link User} object for the logged in, or failed, user.
	 */
	public User login(String uname, String pw);
	
	/** Adds a User to the database 
	 * 
	 * @param ud the {@link UserData} object to add.
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status addUser(UserData ud);
	
	/** Updates a User in the database
	 * 
	 * @param ud {@link UserData} object with updated information. 
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status updateUser(UserData ud);
	
	public Status deleteGroup(int groupID);
	
	public UserData getUserData(int id);

	
	/** Closes database connection */
	public void closeConnection();
}
