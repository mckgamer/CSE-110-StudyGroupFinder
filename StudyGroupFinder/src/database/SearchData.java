package database;

import java.util.ArrayList;
import java.util.StringTokenizer;
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
	/** The ids of data objects found from search execution */
	private Vector<Integer> resultIds = new Vector<Integer>();
	/** The data objects found from search execution */
	private ArrayList<? extends Data> resultData = new ArrayList<Data>();

	/* Constructors */
	public SearchData() {}
	SearchData(String terms) {this.terms = terms;}
	
	@Override
	public boolean validate() {
		return (this.terms != null || this.privateterms != null);
	}

	@Override
	public int getId() {
		return 0;
	}
	
	/** Returns the terms string.
	 * 
	 * @return the terms searched for.
	 */
	public String getTerms() {
		return terms;
	}
	
	/** Returns the terms string with the private terms string.
	 * 
	 * @return the terms searched for.
	 */
	public String getAllTerms() {
		return terms + ", " + privateterms;
	}
	
	/** Sets the terms string.
	 */
	public SearchData setTerms(String terms) {
		this.terms = terms;
		return this;
	}
	
	/** Sets the private terms string.
	 */
	public SearchData setPrivateTerms(String terms) {
		this.privateterms = terms;
		return this;
	}
	
	/** Returns the result vector.
	 * 
	 * @return the results of the search in Vector form.
	 */
	public Vector<Integer> getResults() {
		return resultIds;
	}
	
	/** Is able to remove specified results from the result vector.
	 * 
	 * @param remove the ArrayList of integers of the results to remove.
	 */
	public void removeResults(ArrayList<Integer> remove) {
		for (Integer i:remove) {
			resultIds.remove(i);
		}
	}
	
	/**
	 * Sets the results results
	 * @param results - and {@link Vector} of int object ids
	 * @return itself, a {@link SearchData} object 
	 * 
	 */
	public SearchData setResults(Vector<Integer> results) {
		this.resultIds = results;
		return this;
	}
	
	/**
	 * Returns the result Array of objects
	 * @return an {@link ArrayList} of {@link Data} objects
	 */
	public ArrayList<? extends Data> getResultData() {
		return resultData;
	}
	/**
	 * Sets the results results
	 * @param results - and {@link ArrayList} of {@link Data} objects
	 * @return itself, a {@link SearchData} object 
	 * 
	 */
	public SearchData setResultData(ArrayList<? extends Data> results) {
		this.resultData = results;
		/* Set result ids based on object collection */
		resultIds = new Vector<Integer>();
		for (Data d: results) resultIds.add(d.getId());
		return this;
	}
	
	/**
	 * Build a string to append to SQL WHERE clause based on terms
	 * <p>Example:<br>
	 * <code>
	 * sd.setTerms('mike, bob');<br>
	 * System.out.println(sd.getSql('username'));<br>
	 * > 'username' like '%mike% OR 'username' like '%bob%
	 * </code>
	 * @param fieldname - a {@link String} of the field name to include in clause
	 * @return a {link String} to append to SQL WHERE clause
	 * 
	 */
	public String getSql(String fieldname) {
		String sql = "";
		String terms = getAllTerms();
		
		/* Return empty result if terms is empty/null terms,  */
		if (terms == null || terms.isEmpty())
			return sql;
		
		/* Error if field name is empty */
		if (fieldname.isEmpty())
			throw new RuntimeException();
				
		/* Address each term */
		StringTokenizer st = new StringTokenizer(getAllTerms(), ",");
		while (st.hasMoreTokens()) {
			if (! sql.isEmpty()) sql = sql + " OR "; 
			sql = sql + "`" + fieldname + "` like '%" + st.nextToken().trim() + "%'";
		}
		return sql;
	}
	
	/**
	 * Recursive version of getSql for multiple field names
	 * @param fieldnames - an {@link ArrayList} of {@link String} fieldnames
	 * @return a {link String} to append to SQL WHERE clause
	 * @see {@link SearchData#getSql(String)}
	 */
	public String getSql(ArrayList<String> fieldnames) {
		String sql = "";
		
		for (String fieldname: fieldnames) {
			if (! sql.isEmpty()) sql = sql + " OR "; 
			sql = sql + getSql(fieldname);
		}
		
		return sql;
	}
	
	public String toString() {
		String s =	"Terms: " + this.terms + "\n" +
					"Private terms: " + this.privateterms + "\n";
		s = s + "ResultIds: " + resultIds.toString() + "\n";
		if (resultData.size() == 0)
			s = s + "ResultData: empty";
		else 
			for (Data d: resultData) {s = s + "\n" + d;}
		return s;
	}

}
