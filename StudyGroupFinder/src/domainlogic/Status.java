package domainlogic;

/** Holds a {@link StatusType}, as well as a message useful for GUI 
 * 
 * @author Michael Kirby
 *
 */
public class Status {
	
	/** Message associated with this Status */
	private String message;
	
	/** Current state of this Status */
	private StatusType status = StatusType.UNSUBMITTED;
	
	/** Creates a default status with StatusType.UNSUBMITTED */
	public Status() {
		status = StatusType.UNSUBMITTED;
	}
	
	/** Creates a status of the type specified.
	 * 
	 * @param stat the StatusType to set this to.
	 */
	public Status(StatusType stat) {
		status = stat;
	}
	
	/** Returns the state of the Status object.
	 * 
	 * @return the StatusType of this Status object.
	 */
	public StatusType getStatus() {
		return status;
	}
	
	/** Returns the message associated with this Status.
	 * 
	 * @return the message associated with this Status.
	 */
	public String getMessage() {
		return message;
	}
	
	/** Set the state of this Status object
	 * 
	 * @param stat the StatusType to set this Status to.
	 */
	public void setStatus(StatusType stat) {
		status = stat;
	}
	
	/** Set the message for this Status
	 * 
	 * @param msg the message string to set it to.
	 */
	public void setMessage(String msg) {
		message = msg;
	}

}
