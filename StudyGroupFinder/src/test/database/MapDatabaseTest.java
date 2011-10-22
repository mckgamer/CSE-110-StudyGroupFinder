package database;

import org.junit.Before;


public class MapDatabaseTest extends AbstractDatabaseTest {

	@Before
	public void setUp() throws Exception {
		database = new MapDatabase();
	}
	
}
