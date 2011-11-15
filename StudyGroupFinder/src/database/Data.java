package database;

/** The Data interface is used throughout the system. Data's most important
 * attribute is that it is able to validate itself.
 * 
 * @author Michael Kirby
 */
public interface Data {

	/** Validates that this data object is sufficient for use 
	 * 
	 * @return true if the instance is valid.
	 */
	boolean validate();
	
	/** Returns the id of the Data Object */
	int getId();

	/** Produce a human readable summary of data contents **/
	String toString();
	
}
