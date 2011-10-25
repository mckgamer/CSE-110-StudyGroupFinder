package domainlogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import database.UserData;
import domainlogic.User.Logged;

public class UserTest {

	private User user;
	
	@Before
	public void setUp() throws Exception {
		user = new User(Logged.USER, new UserData(1,"Mike", "pw", "1~2~3~", "4~5~6~"));
	}
	
	@Test
	public void testUserNotNull() {
		assertNotNull(user);
	}
	
	@Test
	public void testGetStatus() {
		assertEquals(user.getStatus(), Logged.USER);
	}
	
	//TODO test all other methods
}
