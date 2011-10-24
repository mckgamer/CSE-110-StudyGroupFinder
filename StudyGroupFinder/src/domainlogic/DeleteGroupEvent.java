package domainlogic;

import database.Data;
import database.GroupData;

/** This Event is created when either a Mod or and Admin hits the delete group button of a {@link GroupProfile}.
 * 
 * @author Michael Kirby
 *
 */
public class DeleteGroupEvent implements Event {

	/** The {@link GroupData} object that this Event will use to complete its task. */
	private GroupData group;
	
	/** The {@link Status} object associated with the current state of this Event. */
	private Status status;
	
	/** The {@link StudyGroupSystem} that this Event uses. */
	private StudyGroupSystem system;
	
	/** Construct a CreateStudyGroupEvent using a default status and null {@link GroupData} object */
	public DeleteGroupEvent(StudyGroupSystem sgs) {
		group = null;
		status = new Status();
		system = sgs;
	}
	
	@Override
	public void execute() {
		if (status.getStatus() != StatusType.INVALID) { //TODO I think for all events this needs to be VALID so it know validate() has already been called for sure
			//status = system.deleteGroup(group.getId());
		}
	}

	@Override
	public void validate() {
		// TODO Maybe double check that the user is allowed to do this? Either by idMod() or Logged status from sgs
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public Data getData() {
		return group;
	}

	@Override
	public void setData(Data data) {
		group = (GroupData)data;
	}

}
