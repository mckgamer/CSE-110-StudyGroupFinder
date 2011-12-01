package database;

import java.util.ArrayList;

import org.junit.*;

import static org.junit.Assert.*;

import database.MySqlDatabase.InvalidDatabaseID;
import domainlogic.Status;
import domainlogic.StatusType;
import domainlogic.User;
import domainlogic.User.Logged;

/* This class can not be run directly as a JUnit test. Other classes 
 * such as MySqlDatabaseTest implement this class and must be used to
 * initiate the JUnit test, which will include all the methods below
 *  */

public abstract class AbstractDatabaseTest {
	
	protected Database db;
	
	private void assertSuccess(Status st) {
		assertEquals(StatusType.SUCCESS, st.getStatus());
	}
	
	private UserData createTempUser() {
		UserData u = new UserData();
		String randomSuffix = Long.toHexString(Double.doubleToLongBits(Math.random()));
		u.setUName("JunitUser" + randomSuffix);
		u.setPW("pw");
		u.setCourses("CSE 110");
		return u;
	}

	private GroupData createTempGroup() {
		GroupData g = new GroupData();
		String randomSuffix = Long.toHexString(Double.doubleToLongBits(Math.random()));
		g.setName("JunitGroup" + randomSuffix);
		g.setCourse("CSE 110, Bio 1");
		return g;
	}

	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testLoginAdmin() {
		User admin = db.login("admin", "pw");
		assertEquals(admin.getStatus(), Logged.ADMIN);		
	}

	@Test
	public final void testLoginUser() {
		User admin = db.login("mike", "pw");
		assertEquals(admin.getStatus(), Logged.USER);		
	}

	@Test
	public final void testGetUser() throws InvalidDatabaseID {
		UserData u = db.getUser(1);
		assertEquals(1, u.getId());
	}

	@Test(expected=InvalidDatabaseID.class)
	public final void testGetInvalidUser() throws InvalidDatabaseID  {
		db.getUser(0);
	}

	@Test
	public final void testCreateUser() {
		UserData u = createTempUser();
		int new_id = db.createUser(u.getUName());
		assert(new_id > 0);
	}
	
	@Test
	public final void testAddUser() {
		assertSuccess(db.addUser(createTempUser()));
	}

	@Test
	public final void testUpdateUser() throws InvalidDatabaseID {
		UserData u = db.getUser(1);
		String coureName = "Bio1";
		u.setCourses(coureName);
		/* Confirm success of update */
		assertSuccess(db.updateUser(u));
		UserData u2 = db.getUser(1);
		/* Confirm data is retrievable from database */
		assertEquals(coureName, u2.getCourses());		
	}

	@Test
	public final void testDeleteUser() {
		int new_id = db.createUser(createTempUser().getUName());
		assertSuccess(db.deleteUser(new_id));
	}

	@Test
	public final void testSearchUsers() {
		db.addUser(createTempUser());
		SearchData sd = new SearchData(); 
		sd.setTerms("JunitUser");
		sd.setResultData(db.searchUsers(sd));
		assert(sd.getResultData().size() > 0);
	}

	@Test
	public final void testSetMembershipUser() throws InvalidDatabaseID {
		int user_id = db.createUser(createTempUser().getUName());
		int group_id = db.createGroup(createTempGroup().getName());
		/* Assert that the method returns success */
		assertSuccess(db.setMembershipUser(user_id, group_id));
		/* Now assert the data can be retrieved */
		GroupData g = db.getGroup(group_id);
		assertEquals(user_id, ((int) g.getUsers().get(0)));
	}

	@Test
	public final void testSetMembershipMod() throws InvalidDatabaseID {
		int user_id = db.createUser(createTempUser().getUName());
		int group_id = db.createGroup(createTempGroup().getName());
		/* Assert that the method returns success */
		assertSuccess(db.setMembershipMod(user_id, group_id));
		/* Now assert the data can be retrieved */
		GroupData g = db.getGroup(group_id);
		assertEquals(user_id, ((int) g.getMods().get(0)));
	}

	@Test
	public final void testSetMembershipNone() throws InvalidDatabaseID {
		/* First add the user */
		int user_id = db.createUser(createTempUser().getUName());
		int group_id = db.createGroup(createTempGroup().getName());
		/* Assert that the method returns success */
		assertSuccess(db.setMembershipUser(user_id, group_id));
		/* Now assert the data can be retrieved */
		GroupData g = db.getGroup(group_id);
		assertEquals(user_id, ((int) g.getUsers().get(0)));
		
		/* Now remove the user */
		/* Assert that the method returns success */
		assertSuccess(db.setMembershipNone(user_id, group_id));
		GroupData g2 = db.getGroup(group_id);
		assert(g2.getUsers().size() == 0);
	}

	@Test
	public final void testGetGroup() throws InvalidDatabaseID {
		GroupData u = db.getGroup(1);
		assertEquals(1, u.getId());
	}

	@Test(expected=InvalidDatabaseID.class)
	public final void testGetInvalidGroup() throws InvalidDatabaseID  {
		db.getGroup(0);
	}

	@Test
	public final void testAddGroup() {
		GroupData g = createTempGroup();
		Status st = db.addGroup(g);
		assertSuccess(st);
	}

	@Test
	public final void testUpdateGroup() throws InvalidDatabaseID {
		String courseList = "Bio1, CSE12, Physics1";
		GroupData g = db.getGroup(1);
		g.setCourse(courseList);
		assertSuccess(db.updateGroup(g));
		GroupData g2 = db.getGroup(1);
		assertEquals(courseList, g2.getCourse());
	}

	@Test
	public final void testDeleteGroup() {
		int new_id = db.createGroup(createTempGroup().getName());
		assertSuccess(db.deleteUser(new_id));
	}

	@Test
	public final void testSearchGroups() {
		SearchData sd = new SearchData();
		sd.setTerms("JunitGroup");
		sd.setResultData(db.searchGroups(sd));
		assert(sd.getResultData().size() > 0);
	}

	@Test
	public final void testDeleteInactiveUsers() {
		java.util.Date d = new java.util.Date("1/1/2010");
		assertSuccess(db.deleteInactiveUsers(d));
	}

	@Test
	public final void testToString() {
		assertNotNull(db.toString());
	}
	
}
