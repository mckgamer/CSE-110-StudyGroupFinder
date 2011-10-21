package domainlogic;

import database.UserData;

public class User {
	
	/** Enumeration for status off the user */
	enum Logged { USER, ADMIN, INVALID, LOGGEDOFF }
	
	/** Specifies if the user is logged in */
	Logged status = Logged.LOGGEDOFF;
	
	/** The {@link UserData} of the current user. */
	UserData user = null;
	
	/** Returns the Logged status of this User 
	 * 
	 * @return the Logged status of this user
	 */
	Logged getStatus() {
		return status;
	}
	
	/** Updates the users Logged status to LOGGEDOFF and nullifies the UserData */
	void logoff() {
		status = Logged.LOGGEDOFF;
		user = null;
	}

}
