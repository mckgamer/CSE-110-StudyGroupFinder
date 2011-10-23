package domainlogic;

import database.Data;
import database.UserData;

public class UpdateUserProfileEvent implements Event {

	/** The {@link UserData} object that this Event will use to complete its task. */
	private UserData user;
	
	/** The {@link Status} object associated with the current state of this Event. */
	private Status status;
	
	/** The {@link StudyGroupSystem} that this Event uses. */
	private StudyGroupSystem system;
	
	/** Construct a UpdateUserProfileEvent using a default status and null {@link UserData} object */
	public UpdateUserProfileEvent(StudyGroupSystem sgs) {
		user = null;
		status = new Status();
		system = sgs;
	}
	
	@Override
	public void execute() {
		if (status.getStatus() != StatusType.INVALID) {
			status = system.updateUserProfile(user);
		}
	}

	@Override
	public void validate() {
		if (user.validate()) {
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
