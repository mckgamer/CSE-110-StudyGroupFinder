package database;

import database.MySqlDatabase.InvalidDatabaseID;

/** When You test a database implementation, you need to extend this */
public abstract class AbstractDatabaseTest {
	
	protected Database database;
	
	/** Test Add Group
	 */
	public abstract void testAddGroup();
	
	/** Test Get Group
	 * @throws InvalidDatabaseID 
	 */
	public abstract void testGetGroup() throws InvalidDatabaseID;
	
	/** Test Add User
	 */
	public abstract void testSetMembershipUser();
	
	/** Test Add Moderator
	 */
	public abstract void testSetMembershipMod();
	
	/** Test Remove User From Group
	 */
	public abstract void testSetMembershipNone();
	
	/** Test Add User 
	 */
	public abstract void testAddUser();
	
	/** Delete User
	 */
	public abstract void testDeleteUser();
	
	/** Test Update User
	 */
	public abstract void testUpdateUser();
	
	/** Test Delete Group
	 */
	public abstract Void testDeleteGroup();
	
	/** Test Get User
	 */
	public abstract void testGetUser() throws InvalidDatabaseID;
	
	/** Updates Group Data in Database
	 */
	public abstract void testUpdateGroup();
	
}
