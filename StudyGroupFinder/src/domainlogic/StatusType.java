package domainlogic;

/** This enumeration holds the different states that an Event can be in.
 * 
 * @author Michael Kirby
 *
 */
public enum StatusType {
	
	/** The Data has been validated. */
	VALID, 
	/** The Data has been found Invalid. */
	INVALID, 
	/** The Event hasn't been executed, or validated yet. */
	UNSUBMITTED, 
	/** The Event was successfully executed. */
	SUCCESS, 
	/** There was a connection error during the execution. */
	CONNECTIONERROR, 
	/** There was some other type of error during execution. */
	PROGRAMERROR, 
	/** It just didn't work. */ //TODO this might not be used
	UNSUCCESSFUL

}
