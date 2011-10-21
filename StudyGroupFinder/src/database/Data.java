package database;

public interface Data {

	/** Validates that this data object is sufficient for use 
	 * 
	 * @return true if the instance is valid.
	 */
	boolean validate();
	
	/** Returns the id of the Data Object */
	int getId();
}
