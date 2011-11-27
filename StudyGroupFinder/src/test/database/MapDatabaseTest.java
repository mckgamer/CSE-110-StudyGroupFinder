package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import database.MySqlDatabase.InvalidDatabaseID;
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
	public void testMapDatabase() {
		// Not necessary? MapDatabase()
	}

	@Test
	public void testAddGroup() {
		GroupData gd = new GroupData(1, "test group", "bio1","1","1");
		Status st = new Status();
		st = database.addGroup(gd);
		assertEquals("AddGroup status","SUCCESS", st.getStatus().toString());
	}

	@Test
	public void testGetGroup() throws InvalidDatabaseID {
		GroupData gd = database.getGroup(1);
		ArrayList<Integer> arl = new ArrayList<Integer>();
		arl.add(1);
		assertEquals("getGroup id", 1, gd.getId());
		assertEquals("getGroup name", "The Group", gd.getName());
		assertEquals("getGroup course", "CSE 110", gd.getCourse());
		assertEquals("getGroup users", arl, gd.getUsers());
	}

	@Test
	public void testAddUserToGroup() {
		// AddUserToGroup is not yet implemented in the MapDatabase class
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveUserFromGroup() {
		// AddUserToGroup is not yet implemented in the MapDatabase class
		fail("Not yet implemented");
	}

	@Test
	public void testLogin() {
		User usr = new User(Logged.LOGGEDOFF, null);
		
		// try logging in as a valid user and test user status
		usr = database.login("Mike", "pw");
		assertEquals("login valid user","USER",usr.getStatus().toString());
		
		//try an invalid login
		usr = database.login("Mike", "wrong-pw");
		assertEquals("login valid user","INVALID",usr.getStatus().toString());
		
		//try an non-existing user
		usr = database.login("no-user", "wrong-pw");
		assertEquals("login valid user","INVALID",usr.getStatus().toString());
	}

	@Test
	public void testAddUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testCloseConnection() {
		fail("Not yet implemented");
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
		UserData tempUserData= new UserData(2,"Roberto", "heybob", "~", "~","");
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
	
	@Test
	public void joinGroup() throws InvalidDatabaseID{
		database.setMembershipUser(2, 1);
		GroupData tempGD = database.getGroup(1);
		ArrayList<Integer> intArrList = new ArrayList<Integer>();
		intArrList = tempGD.getUsers();
		assertTrue(intArrList.contains(2));
	}
	
	@Test
	public void deleteGroup() throws InvalidDatabaseID{
		
		database.deleteGroup(1);
		GroupData tempGroup = database.getGroup(2);
		System.out.println(tempGroup.getName());
		database.getUser(1);	
		//Add Group  then delete it
		GroupData newGroup = new GroupData(1,"HEYBOB", "CES 110","1~", "1~");
		database.addGroup(newGroup);
		
		
		
	}

	@Override
	public void testDeleteUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Void testDeleteGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void testGetUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testUpdateGroup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testSetMembershipUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testSetMembershipMod() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testSetMembershipNone() {
		// TODO Auto-generated method stub
		
	}
}
