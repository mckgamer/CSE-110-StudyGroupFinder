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
	
	/** The results of this SearchData's execution */
	private ArrayList<? extends Data> results;

	/* Constructors */
	SearchData() {}
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
	
	/**
	 * Sets the results results
	 * @param results - and {@link ArrayList} of {@link Data} objects
	 * @return itself, a {@link SearchData} object 
	 * 
	 */
	public SearchData setResults(ArrayList<? extends Data> results) {
		this.results = results;
		return this;
	}
	
	/** Returns the result vector.
	 * 
	 * @return the results of the search in Vector form.
	 */
	public ArrayList<? extends Data> getResults() {
		return results;
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
		
		/* Error if field name is empty */
		if (fieldname.isEmpty())
			throw new RuntimeException();
				
		/* Address each term */
		StringTokenizer st = new StringTokenizer(getTerms(), ",");
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
	
	public static void main(String[] args) {
		SearchData sd = new SearchData();
		ArrayList<String> fieldnames = new ArrayList<String>();
		fieldnames.add("name");
		fieldnames.add("full_name");
		
		System.out.println("Testing getSql:");
		
		try {
			System.out.println("Missing fieldname: " + sd.getSql(""));
		} catch (Exception e) {
			System.out.println("Missing fieldname: successfully threw exception");
		}
		
		sd.setTerms("");
		System.out.println("Empty terms, single field =" + sd.getSql("name") + "=");
		System.out.println("Empty terms, multi fields =" + sd.getSql(fieldnames) + "=");
		
		sd.setTerms("bob");
		System.out.println("One term =" + sd.getSql("name") + "=");
		System.out.println("One term =" + sd.getSql(fieldnames) + "=");
		
		sd.setTerms("bob, mike,john");
		System.out.println("Multiple terms =" + sd.getSql("name") + "=");
		System.out.println("Multiple terms =" + sd.getSql(fieldnames) + "=");
		
	}
	
	public String toString() {
		String s = "";
		s = s + "Terms: " + this.terms + "\n";
		s = s + "Private terms: " + this.privateterms + "\n";
		if (results.size() == 0)
			s = s + "Results: empty" + "\n";
		else 
			for (Data d: results) s = s + d + "\n";
		return s;
	}

}
