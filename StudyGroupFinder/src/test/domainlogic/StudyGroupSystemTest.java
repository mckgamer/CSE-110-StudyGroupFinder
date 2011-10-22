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
	public void testIfUserExists() {
		MapDatabase testData = new MapDatabase();
		StudyGroupSystem sgs = new StudyGroupSystem(testData);
		Logged status = sgs.login("Mike", "pw");
		assertNotNull(status);
		assertTrue( status == Logged.USER);
		assertTrue(sgs.isLogged());

	}
	
	@Test
	public void testAddUser() {
		MapDatabase testData = new MapDatabase();
		StudyGroupSystem sgs = new StudyGroupSystem(testData);
		Status stat = new Status();
		stat = sgs.createUser(3, "Roberto", "pw", "0~");
		System.out.println(stat.getStatus());
		assertEquals(stat.getStatus(), StatusType.SUCCESS);
		Logged status = sgs.login("Roberto", "pw");
		assertTrue( status == Logged.USER);
	}
}
