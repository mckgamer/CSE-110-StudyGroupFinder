package domainlogic;

import database.Data;
import database.UserData;

public class CreateUserEvent implements Event {

	private UserData user;
	private Status status;
	private StudyGroupSystem system;
	
	public CreateUserEvent() {
		user = null;
		status = new Status();
	}
	
	@Override
	public void execute() {
		if (status.getStatus() != StatusType.INVALID) {
			status = system.createUser(user);
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
