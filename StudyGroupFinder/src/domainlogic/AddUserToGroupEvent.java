package domainlogic;

import database.Data;
import database.GroupData;
import database.GroupUserData;

/** This Event is created by a {@link gui.GroupProfile} when a user joins a group.
 * 
 * @author Michael Kirby
 *
 */
public class AddUserToGroupEvent implements Event {
	
	/** The {@link GroupUserData} object that this Event will use to complete its task. */
	private GroupUserData usergroup; //TODO use this in the constructor
	
	/** The {@link Status} object associated with the current state of this Event. */
	private Status status;
	
	/** The {@link StudyGroupSystem} that this Event uses. */
	private StudyGroupSystem system;
	
	/** Construct a UpdateGroupProfileEvent using a default status and null {@link GroupData} object */
	public AddUserToGroupEvent(StudyGroupSystem sgs, int userId, int groupId) {
		status = new Status();
		system = sgs;
		usergroup = new GroupUserData(userId, groupId);
	}
	
	@Override
	public void execute() {
		status = system.joinGroup(usergroup.getId(), usergroup.getGroupId());
		system.refreshLoggedUser();
	}

	@Override
	public void validate() {
		// TODO This needs to check that the user isnt already in the group, and make sure both group and user doesn't think they are
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
