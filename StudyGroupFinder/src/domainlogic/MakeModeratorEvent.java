package domainlogic;

import database.Data;
import database.GroupData;
import database.GroupUserData;

/** This Event is created by a {@link gui.GroupProfile} when a Moderator or Admin makes a User a moderator
 * for the Group.
 * 
 * @author Michael Kirby
 *
 */
public class MakeModeratorEvent implements Event {

	/** The {@link GroupUserData} object that this Event will use to complete its task. */
	private GroupUserData usergroup;
	
	/** The {@link Status} object associated with the current state of this Event. */
	private Status status;
	
	/** The {@link StudyGroupSystem} that this Event uses. */
	private StudyGroupSystem system;
	
	/** Construct a UpdateGroupProfileEvent using a default status and null {@link GroupData} object */
	public MakeModeratorEvent(StudyGroupSystem sgs) {
		usergroup = null;
		status = new Status();
		system = sgs;
	}
	
	@Override
	public void execute() {
		if (status.getStatus() != StatusType.INVALID) { //TODO I think for all events this needs to be VALID so it know validate() has already been called for sure
			status = system.makeModerator(usergroup.getId(), ((GroupUserData)usergroup).getGroupId());
		}
	}

	@Override
	public void validate() {
		if (usergroup.validate()) {
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
		return usergroup;
	}

	@Override
	public void setData(Data data) {
		usergroup = (GroupUserData) data;
	}

}
