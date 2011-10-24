package domainlogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import database.GroupData;
import database.MapDatabase;
import database.UserData;
import domainlogic.User;
import domainlogic.User.Logged;



public class StudyGroupSystemTest {
	
	@Test
	public void testLogin() {
		MapDatabase testData = new MapDatabase();
		StudyGroupSystem sgs = new StudyGroupSystem(testData);
		Logged status = sgs.login("Bob", "pw");
		assertNotNull(status);
		assertTrue( status == Logged.USER);
		assertTrue(sgs.isLogged());

	}
	
	@Test
	public void testLoginNullStrings() {
		MapDatabase testData = new MapDatabase();
		StudyGroupSystem sgs = new StudyGroupSystem(testData);
		String uName="";
		String pw="";
		Logged status = sgs.login(uName, pw);
		assertNotNull(status);
		assertTrue( status == Logged.INVALID);
		assertTrue(!sgs.isLogged());

	}
	
	@Test
	public void testAddUser() {
		MapDatabase testData = new MapDatabase();
		StudyGroupSystem sgs = new StudyGroupSystem(testData);
		Status stat = new Status();
		UserData u = new UserData(1, "Roberto", "pw", "~");
		stat = sgs.createUser(u);
		System.out.println(stat.getStatus());
		assertEquals(stat.getStatus(), StatusType.SUCCESS);
		Logged status = sgs.login("Roberto", "pw");
		assertTrue( status == Logged.USER);
	}
	
	@Test
	public void testUpdateProfile(){
		MapDatabase testData = new MapDatabase();
		StudyGroupSystem sgs = new StudyGroupSystem(testData);
		Status stat;
		Logged status = sgs.login("Bob", "pw");
		assertNotNull(status);
		assertTrue( status == Logged.USER);
		assertTrue(sgs.isLogged());
		//login
		UserData u = new UserData(2, "Roberto", "pw", "~"); 
		stat = sgs.updateUserProfile(u);
		assertTrue(stat.getStatus() == StatusType.SUCCESS);
		//login
		status = sgs.login("Bob", "pw");
		assertTrue( status != Logged.USER);	
	}
	
	@Test
	public void createGroup() {
		MapDatabase testData = new MapDatabase();
		StudyGroupSystem sgs = new StudyGroupSystem(testData);
		Status stat;
		Logged status = sgs.login("Bob", "pw");		
		GroupData gd = new GroupData(1, "KickAss Group", "CSE 110", "1" , "1");
		stat = sgs.createNewGroup(gd);
		assertTrue(stat.getStatus() == StatusType.SUCCESS);
		GroupData gd2 = sgs.getGroup(3);
		System.out.println(gd2.getId());
		System.out.println(gd2.getName());
		System.out.println(gd2.getCourse());
	}
	
	@Test
	public void getGroup() {
		MapDatabase testData = new MapDatabase();
		StudyGroupSystem sgs = new StudyGroupSystem(testData);
		GroupData gd = sgs.getGroup(2);
		System.out.println(gd.getId());
		System.out.println(gd.getName());
		System.out.println(gd.getCourse());
		
	}
	
}
