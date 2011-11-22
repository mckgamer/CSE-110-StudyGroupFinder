package database;

import java.util.Vector;

/** 
 * This {@link Data} implementation is used for handling search results in Study Group
 * Finder.
 * 
 * @author Michael Kirby
 */
public class SearchData implements Data {

	/** These are the search terms associated with this SearchData */
	private Vector<String> terms;
	
	/** The results of this SearchData's execution */
	private Vector<String> results;
	
	@Override
	public boolean validate() {
		// TODO check that all the terms are ok, or there are any
		return false;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/** Returns the result vector.
	 * 
	 * @return the results of the search in Vector form.
	 */
	public Vector<String> getResults() {
		return results;
	}

}
