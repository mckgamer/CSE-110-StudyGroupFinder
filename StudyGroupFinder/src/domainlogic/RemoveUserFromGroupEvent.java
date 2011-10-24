package domainlogic;

import database.Data;
import database.GroupData;

public class RemoveUserFromGroupEvent implements Event {

	//TODO I think I'm going to add another Data impl for this - Michael Kirby 10/24
	private int userId;
	private int groupId;
	
	/** The {@link Status} object associated with the current state of this Event. */
	private Status status;
	
	/** The {@link StudyGroupSystem} that this Event uses. */
	private StudyGroupSystem system;
	
	/** Construct a UpdateGroupProfileEvent using a default status and null {@link GroupData} object */
	public RemoveUserFromGroupEvent(StudyGroupSystem sgs, int userId, int groupId) {
		status = new Status();
		system = sgs;
		this.userId = userId;
		this.groupId = groupId;
	}
	
	@Override
	public void execute() {
		//status = system.removeUserFromGroup(userId, groupId);
		system.refreshLoggedUser();
	}

	@Override
	public void validate() {
		// TODO This needs to make sure the user is in the group and both user and group agree that they are
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public Data getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setData(Data data) {
		// TODO Auto-generated method stub
		
	}

}
