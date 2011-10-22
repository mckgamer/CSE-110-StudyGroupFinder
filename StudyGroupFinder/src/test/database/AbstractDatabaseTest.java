package database;

import static org.junit.Assert.*;

import org.junit.Test;

import domainlogic.User;
import domainlogic.User.Logged;


public abstract class AbstractDatabaseTest {
	
	protected Database database;
	
	
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
