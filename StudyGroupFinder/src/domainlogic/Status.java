package domainlogic;

/** Holds a StatusType, as well as a message useful for GUI */
public class Status {
	
	/** Message associated with this Status */
	String message;
	
	/** Current state of this Status */
	StatusType status = StatusType.UNSUBMITTED;
	
	/** Returns the state of the Status object.
	 * 
	 * @return the StatusType of this Status object.
	 */
	StatusType getStatus() {
		return status;
	}
	
	/** Returns the message associated with this Status.
	 * 
	 * @return the message associated with this Status.
	 */
	String getMessage() {
		return message;
	}
	
	/** Set the state of this Status object
	 * 
	 * @param stat the StatusType to set this Status to.
	 */
	void setStatus(StatusType stat) {
		status = stat;
	}
	
	/** Set the message for this Status
	 * 
	 * @param msg the message string to set it to.
	 */
	void setMessage(String msg) {
		message = msg;
	}

}
