package domainlogic;

/** Holds a StatusType, as well as a message useful for GUI */
public class Status {
	
	//Class Variables
	private String message;
	
	
	/** Message associated with this Status */
	
	
	/** Current state of this Status */
	private StatusType status = StatusType.UNSUBMITTED;
	public Status() {
		status = StatusType.UNSUBMITTED;
	}
	
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
