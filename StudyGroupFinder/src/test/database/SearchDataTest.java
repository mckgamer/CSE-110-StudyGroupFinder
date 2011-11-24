package database;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SearchDataTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		/* The following is code used during development
		 * that could be converted to JUnit tests
		 */
		
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

}
