package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import domainlogic.Status;
import domainlogic.StatusType;
import domainlogic.User;
import domainlogic.User.Logged;


public class MapDatabaseTest extends AbstractDatabaseTest {

	@Before
	public void setUp() throws Exception {
		database = new MapDatabase();
	}
	
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
	
	@Test
	public void updateUserProfile(){
		//Login as user
		User temp = database.login("Bob", "pw");
		assertTrue(temp.getStatus() == Logged.USER);
		//Alter User Data & Update Database
		UserData tempUserData= new UserData(2,"Roberto", "heybob", "modOf()");
		Status newStatus = database.updateUser(tempUserData);
		assertTrue(newStatus.getStatus() == StatusType.SUCCESS);
		// Check if database has been updated by logging in
		temp.setUserData(tempUserData);
		temp = database.login("Roberto", "heybob");
		assertTrue(temp.getStatus() == Logged.USER);
		// Attempt to login using old credentials
		temp = database.login("Bob", "pw");
		assertTrue(temp.getStatus() == Logged.INVALID);	
	}
	
}
