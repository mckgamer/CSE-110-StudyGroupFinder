package domainlogic;

import database.UserData;

/** This is the main 'session' object for the program. There is one User object that represents
 *  the person using the program.
 *  
 * @author Michael Kirby
 *
 */
public class User {
	
	/** Enumeration for status off the user */
	public enum Logged { USER, ADMIN, INVALID, LOGGEDOFF }
	
	/** Specifies if the user is logged in */
	Logged status = Logged.LOGGEDOFF;
	
	/** The {@link UserData} of the current user. */
	UserData user = null;
	
	/** Constructs a User object for the current session
	 * 
	 * @param s the {@link Logged} status of this User.
	 * @param ud the {@link UserData} associated with this User.
	 */
	public User(Logged s, UserData ud) {
		status = s;
		user = ud;
	}
	
	/** Returns the {@link Logged} status of this User 
	 * 
	 * @return the {@link Logged} status of this user
	 */
	public Logged getStatus() {
		return status;
	}
	
	/** Sets the {@link UserData} of this User to the given UserData
	 * 
	 * @param ud the given {@link UserData}.
	 */
	public void setUserData(UserData ud) {
		user = ud;
	}

	/** Sets the {@link Logged} status of this User.
	 * 
	 * @param s the {@link Logged} status to set to.
	 */
	public void setStatus(Logged s) {
		status = s;
	}
	
	/** Updates the users Logged status to LOGGEDOFF and nullifies the UserData */
	public void logoff() {
		status = Logged.LOGGEDOFF;
		user = null;
	}
}
