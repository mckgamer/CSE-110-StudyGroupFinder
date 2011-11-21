package database;

import static org.junit.Assert.*;

import org.junit.Test;

import domainlogic.Status;
import domainlogic.User;
import domainlogic.User.Logged;

/** When You test a database implementation, you need to extend this */
public abstract class AbstractDatabaseTest {
	
	protected Database database;
	
	/** Test Add Group
	 */
	public abstract void testAddGroup();
	
	/** Test Get Group
	 */
	public abstract void testGetGroup();
	
	/** Test Add User
	 */
	public abstract void testAddUserToGroup();
	
	/** Test Remove User From Group
	 */
	public abstract void testRemoveUserFromGroup();
	
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
	public abstract void testGetUser();
	
	/** Updates Group Data in Database
	 */
	public abstract void testUpdateGroup();
	
	@Test
	public void testLogInUser() {
		User temp = database.login("Mike", "pw");
		assertNotNull(temp);
		assertTrue(temp.getStatus() == Logged.USER);
		assertEquals(temp.getUserData().getUName(), "Mike");
		assertEquals(temp.getUserData().getPW(), "pw");
	}
	
	@Test
	public void testLogInInvalidUser() {
		User temp = database.login("Mike", "pw2");
		assertNotNull(temp);
		assertTrue(temp.getStatus() == Logged.INVALID);
		assertNull(temp.getUserData());
	}
}
