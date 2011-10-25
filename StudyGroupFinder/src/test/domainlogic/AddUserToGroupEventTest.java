package domainlogic;

import org.junit.Before;

import database.MapDatabase;

public class AddUserToGroupEventTest extends AbstractEventTest {

	@Before
	public void setUp() throws Exception {
		db = new MapDatabase();
		system = new StudyGroupSystem((MapDatabase)db); //TODO will have to delete cast after Database interface following
		event = new AddUserToGroupEvent(system, 1, 1); //This will need to change
	}
}
