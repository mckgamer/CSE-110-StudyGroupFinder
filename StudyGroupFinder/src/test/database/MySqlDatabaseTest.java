package database;

import org.junit.*;

import static org.junit.Assert.*;

import domainlogic.Status;
import domainlogic.StatusType;

public class MySqlDatabaseTest extends AbstractDatabaseTest {

	static MySqlDatabase sqlDb;
	
	private static Status connectDefault(MySqlDatabase db) {
		return db.openConnection("jdbc:mysql://localhost:3306/testdb", "root", "");
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Initializing MySql database for MySqlDatabaseTest");
		sqlDb = new MySqlDatabase();
		connectDefault(sqlDb);
		sqlDb.buildDatabase(true);
		System.out.println("Successfully initialized MySql database for MySqlDatabaseTest");
	}

	@Before
	public void setUp() {super.db = sqlDb;}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		sqlDb.closeConnection();
	}

	@Test
	public void testOpenConnection() {
		Status st = connectDefault((MySqlDatabase)db);
		assertEquals(st.getStatus(), StatusType.SUCCESS);
	}
	
	@Test
	public void testCloseConnection() {
		Status st = ((MySqlDatabase)db).closeConnection();
		assertEquals(st.getStatus(), StatusType.SUCCESS);
		
		/* Re-establish connection for other tests */
		connectDefault((MySqlDatabase)db);
		assertEquals(st.getStatus(), StatusType.SUCCESS);
	}

}
