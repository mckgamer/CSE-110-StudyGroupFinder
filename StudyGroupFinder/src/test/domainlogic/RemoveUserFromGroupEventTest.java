package domainlogic;

import org.junit.Before;

import database.MapDatabase;

public class RemoveUserFromGroupEventTest extends AbstractEventTest {

	@Before
	public void setUp() throws Exception {
		db = new MapDatabase();
		system = new StudyGroupSystem((MapDatabase)db); //TODO will have to delete cast after Database interface following
		event = new RemoveUserFromGroupEvent(system, 1, 1); //This will need to change
	}
}
