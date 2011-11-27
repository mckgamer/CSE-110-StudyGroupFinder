package domainlogic;

import database.Data;
import database.GroupData;
import database.GroupUserData;

/** This event is created by a {@link gui.GroupProfile}. It is able to call the appropriate
 * methods to remove a user's moderator privileges from the group.
 * 
 * @author Michael Kirby
 */
public class DeModEvent implements Event {

	/** The {@link GroupUserData} object that this Event will use to complete its task. */
	private GroupUserData usergroup;
	
	/** The {@link Status} object associated with the current state of this Event. */
	private Status status;
	
	/** The {@link StudyGroupSystem} that this Event uses. */
	private StudyGroupSystem system;
	
	/** Construct a UpdateGroupProfileEvent using a default status and null {@link GroupData} object */
	public DeModEvent(StudyGroupSystem sgs) {
		usergroup = null;
		status = new Status();
		system = sgs;
	}
	
	@Override
	public void execute() {
		if (status.getStatus() != StatusType.INVALID) { //TODO I think for all events this needs to be VALID so it know validate() has already been called for sure
			status = system.removeModerator(usergroup.getId(), ((GroupUserData)usergroup).getGroupId());
		}
	}

	@Override
	public void validate() {
		if (usergroup.validate()) {
			if (usergroup.getId() != system.getLoggedUser().getId()) {
				status.setStatus(StatusType.VALID);
			} else {
				status.setStatus(StatusType.INVALID);
				status.setMessage("You cannot remove your moderator privileges this way!");
			}
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
		return usergroup;
	}

	@Override
	public void setData(Data data) {
		usergroup = (GroupUserData) data;
	}

}
