package domainlogic;

import database.Data;
import database.UserData;
import domainlogic.User.Logged;

/** This Event is created when either an Admin hits the delete user button of a {@link gui.UserProfile}.
 * 
 * @author Michael Kirby
 *
 */
public class DeleteUserEvent implements Event {

	/** The {@link UserData} object that this Event will use to complete its task. */
	private UserData user;
	
	/** The {@link Status} object associated with the current state of this Event. */
	private Status status;
	
	/** The {@link StudyGroupSystem} that this Event uses. */
	private StudyGroupSystem system;
	
	/** Construct a DeleteUserEvent using a default status and null {@link UserData} object */
	public DeleteUserEvent(StudyGroupSystem sgs) {
		user = null;
		status = new Status();
		system = sgs;
	}
	
	@Override
	public void execute() {
		if (status.getStatus() != StatusType.INVALID) { //TODO I think for all events this needs to be VALID so it know validate() has already been called for sure
			status = system.deleteUser(user.getId());
		}
	}

	@Override
	public void validate() {
		//TODO maybe this should check that the userdata is valid still, or the user exists?
		if (user.getId() == system.getLoggedUser().getId() || system.getUserStatus() == Logged.ADMIN) {
			status.setStatus(StatusType.VALID);
		} else {
			status.setStatus(StatusType.INVALID);
		}
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public Data getData() {
		return user;
	}

	@Override
	public void setData(Data data) {
		user = (UserData)data;
	}

}
