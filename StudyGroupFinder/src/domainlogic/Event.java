package domainlogic;

import database.Data;

/** Events are created by the GUI to carry out whatever their task may be. They essentially interface
 * between the StudyGroupSystem and GUI.
 * @author Michael Kirby
 *
 */
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
	
	/** Sets the data object of this Event.
	 * 
	 * @param data the Data object to set to.
	 */
	void setData(Data data);

}
