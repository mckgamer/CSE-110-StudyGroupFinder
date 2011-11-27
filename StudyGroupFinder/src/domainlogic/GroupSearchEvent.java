package domainlogic;

import database.Data;
import database.SearchData;

/** This Event is created whenever a User or Admin searches for a group.
 * 
 * @author Michael Kirby
 *
 */
public class GroupSearchEvent implements Event {

	/** The {@link SearchData} object that this Event will use to complete its task. */
	private SearchData search;
	
	/** The {@link Status} object associated with the current state of this Event. */
	private Status status;
	
	/** The {@link StudyGroupSystem} that this Event uses. */
	private StudyGroupSystem system;
	
	/** Construct a DeleteUserEvent using a default status and null {@link UserData} object */
	public GroupSearchEvent(StudyGroupSystem sgs) {
		search = null;
		status = new Status();
		system = sgs;
	}
	
	@Override
	public void execute() {
		search = system.searchGroups(search);
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public Data getData() {
		return search;
	}

	@Override
	public void setData(Data data) {
		search = (SearchData) data;
	}
	
	public void removeCurrent() {
		search.removeResults(system.getLoggedUser().getUserOf());
		search.removeResults(system.getLoggedUser().getModOf());
	}

}
