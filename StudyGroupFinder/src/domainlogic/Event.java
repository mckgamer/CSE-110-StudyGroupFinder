package domainlogic;

import database.Data;

public interface Event {
	
	/** Carries out event and updates Status Variable */
	void execute();
	
	/** Validates the {@link Data} object of this Event. */
	void validate();
	
	/** Returns this Events {@link Status} object 
	 * @return this Events {@link Status} object
	 */
	Status getStatus();
	
	/** Returns this Events {@link Data} object 
	 * @return this Events {@link Data} object
	 */
	Data getData();

}
