package database;

import org.junit.After;
import org.junit.Before;

import database.MySqlDatabase.InvalidDatabaseID;

public class MySqlDatabaseTest extends AbstractDatabaseTest {

	@Before
	public void setUp() throws Exception {
		MySqlDatabase db = new MySqlDatabase();
		db.openConnection("jdbc:mysql://localhost:3306/testdb", "root", "");
	}
	
	@After
	public void tearDown() throws Exception {
		((MySqlDatabase)database).closeConnection();
	}

	@Override
	public void testAddGroup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testGetGroup() throws InvalidDatabaseID {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void testAddUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testDeleteUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testUpdateUser() {
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
