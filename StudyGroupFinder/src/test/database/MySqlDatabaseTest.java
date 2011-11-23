package database;

import org.junit.After;
import org.junit.Before;

public class MySqlDatabaseTest extends AbstractDatabaseTest {

	@Before
	public void setUp() throws Exception {
		database = new MySqlDatabase("jdbc:mysql://localhost:3306/testdb", "root", "");
	}
	
	@After
	public void tearDown() throws Exception {
		((MySqlDatabase)database).closeConnection();
	}
	
}
