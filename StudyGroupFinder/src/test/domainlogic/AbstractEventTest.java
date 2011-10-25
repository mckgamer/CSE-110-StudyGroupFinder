package domainlogic;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import database.Database;

/** When you test an Event you need to extend this. */
public abstract class AbstractEventTest {

	protected Database db;
	protected StudyGroupSystem system;
	protected Event event;
	
	@Test
	public void testGetStatusNotNull() {
		assertNotNull(event.getStatus());
	}
}
