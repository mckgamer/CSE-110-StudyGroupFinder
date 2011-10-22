package domainlogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import database.MapDatabase;
import domainlogic.User;
import domainlogic.User.Logged;



public class StudyGroupSystemTest {
	@Test
	public void testLogInUser() {
		MapDatabase testData = new MapDatabase();
		StudyGroupSystem sgs = new StudyGroupSystem(testData);
		Logged status = sgs.login("Bob", "pw");
		assertNotNull(status);
		assertTrue( status == Logged.USER);
		assertTrue(sgs.isLogged());
		/*assertNotNull(temp);
		assertTrue(temp.getStatus() == Logged.USER);
		assertEquals(temp.getUserData().getUName(), "Mike");
		assertEquals(temp.getUserData().getPW(), "pw");
		*/
	}
}
