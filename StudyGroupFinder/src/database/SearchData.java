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
	private String terms;
	
	/** These are the private search terms associated with this SearchData, ie suggested terms. */
	private String privateterms;
	
	/** The results of this SearchData's execution */
	private Vector<Integer> results;
	
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
	
	/** Returns the terms string.
	 * 
	 * @return the terms searched for.
	 */
	public String getTerms() {
		return terms;
	}
	
	/** Sets the terms string.
	 */
	public void setTerms(String terms) {
		this.terms = terms;
	}
	
	/** Sets the private terms string.
	 */
	public void setPrivateTerms(String terms) {
		this.privateterms = terms;
	}
	
	/** Sets the results vector.
	 */
	public void setResults(Vector<Integer> results) {
		this.results = results;
	}
	
	/** Returns the result vector.
	 * 
	 * @return the results of the search in Vector form.
	 */
	public Vector<Integer> getResults() {
		return results;
	}

}
