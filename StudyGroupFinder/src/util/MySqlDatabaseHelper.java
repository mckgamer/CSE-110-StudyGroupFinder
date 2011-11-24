package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This is critical functionality for the {@link database.MySqlDatabase} class
 * but is located here to reduce clutter in that class
 * @author Mike Claffey
 */
public class MySqlDatabaseHelper {
	
	/* This class uses the connection established by the MySqlDatabase class */
	private Connection mySQLConnection;
	
	/* Shortcut to print to console during debugging */
	public static boolean showDebug = true;
	private static void print(String s) {
		if (showDebug) {System.out.println(s);}
	}		
	
	/**
	 * Constructor for the class.
	 * @param mySQLConnection - a {@link Connection} from {@link database.MySqlDatabase}
	 */
	public MySqlDatabaseHelper(Connection mySQLConnection) {
		this.mySQLConnection = mySQLConnection;
	}

	/**
	 * Execute a query in the SQL database and return results.
	 * <p>NOTE: The calling method should close the {@link Statement} to avoid memory leaks.</p>
	 * @param sqlCommand - a {@link String} properly formatted SQL query
	 * @return the {@link ResultsSet} of the query
	 */
	public ResultSet sqlQuery(String sqlCommand) {
	    Statement statement = null;
	    ResultSet results = null;
		try {
			statement = mySQLConnection.createStatement();
	        results = statement.executeQuery(sqlCommand);
		} catch (SQLException e) {
			print("Error sqlCommand=" + sqlCommand);
			e.printStackTrace();
		}
	    return results;
	}
	
	/**
	 * Execute an updatable query in the SQL database and return results.
	 * <p>NOTE: The calling method should close the {@link Statement} to avoid memory leaks.</p>
	 * @param sqlCommand - a {@link String} properly formatted SQL query
	 * @return the {@link ResultsSet} of the query
	 */
	public ResultSet sqlUpdatable(String sqlCommand) {
	    Statement statement = null;
	    ResultSet results = null;
		try {
			statement = mySQLConnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	        results = statement.executeQuery(sqlCommand);
		} catch (SQLException e) {
			print("Error sqlCommand=" + sqlCommand);
			e.printStackTrace();
		}
	    return results;
	}
	
	/** 
	 * Execute a non-query command in the SQL database
	 * @param sqlCommand - a {@link String} properly formatted SQL query
	 */
	public void sqlExecute(String sqlCommand){
		try {
			Statement statement = mySQLConnection.createStatement();
		    statement.executeUpdate(sqlCommand);
		    statement.close();
		} catch (SQLException e) {
			print("Error in sqlUpdate, sqlCommand=" + sqlCommand);
			e.printStackTrace();
		}
	}

	/** 
	 * Execute an insert command in the SQL database and return keys
	 * @param sqlCommand - a {@link String} properly formatted SQL query
	 */
	public ArrayList<Integer> sqlInsert(String sqlCommand){
	    Statement statement = null;
	    ArrayList<Integer> keys = new ArrayList<Integer>();
	    
		try {
			statement = mySQLConnection.createStatement();
		    statement.executeUpdate(sqlCommand);
		    
		    ResultSet res = statement.getGeneratedKeys();
		    while (res.next());
		    	keys.add(res.getInt(1));
		    
		    statement.close();
		} catch (SQLException e) {
			print("Error in sqlInsert, sqlCommand=" + sqlCommand);
			e.printStackTrace();
		}
		return keys;
	}

	/**
	 * Return the maximum value of the 'id' field of a given table.
	 * Used to determine the id of the last added User or Group. 
	 * @param tableName - the {@link String} of the table to search
	 * @return an {@link int} of the maximum value in the id field
	 */
	public int getMaxId(String tableName) {
		int max_id = 0;
		ResultSet res = sqlQuery("SELECT MAX(`id`) FROM `" + tableName + "`;");
		try {
			if (res.next()) { max_id = res.getInt(1); }
			res.getStatement().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		return max_id;		
	}
	
	/**
	 * Print user, group and membership data to console
	 */
	public void printDatabase() {
		printUsers();
		printGroups();
		printMemberships();
	}
	
	/**
	 * Print user table to console
	 */
	public void printUsers() {
		print("Users Table: ");
		displaySqlResults("SELECT * FROM `users`;");
	}
	
	/**
	 * Print group table to console
	 */
	public void printGroups() {
		print("Groups Table: ");
		displaySqlResults("SELECT * FROM `groups`;");
	}
	
	/**
	 * Print membership view to console
	 */
	public void printMemberships() {
		print("Membership Query: ");
		displaySqlResults("SELECT * FROM `membership_info`;");
	}

	/**
	 * Build the tables and views of the database. Deletes all existing data.
	 */
	public void buildDatabase() {
		
		/** Create tables **/
		print("Creating user table");
		sqlExecute("DROP TABLE IF EXISTS `users`;");
		sqlExecute("CREATE TABLE `users` (" + 
				  "`id` INT NOT NULL AUTO_INCREMENT ," +
				  "`name` VARCHAR(45) NOT NULL ," +
				  "`password` VARCHAR(45) NOT NULL ," +
				  "`courses` VARCHAR(255) NULL ," +
				  "`last_login` DATETIME NULL ," +
				  "PRIMARY KEY (`id`) ," +
				  "UNIQUE INDEX `id_UNIQUE` (`id` ASC) ), " +
				  "UNIQUE INDEX `name_UNIQUE` (`name` ASC) );"
				  );
		
		print("Creating group table");
		sqlExecute("DROP TABLE IF EXISTS `groups`;");
		sqlExecute("CREATE TABLE `groups` (" +
				  "`id` INT NOT NULL AUTO_INCREMENT ," +
				  "`name` VARCHAR(45) NOT NULL ," +
				  "`course` VARCHAR(45) NULL ," +
				  "PRIMARY KEY (`id`) ," +
				  "UNIQUE INDEX `id_UNIQUE` (`id` ASC) );"
				  );
		
		print("Creating memberships table");
		sqlExecute("DROP TABLE IF EXISTS `memberships`;");
		sqlExecute("CREATE TABLE `memberships` (" +
				  "`id` INT NOT NULL AUTO_INCREMENT ," +
				  "`user_id` INT NOT NULL ," +
				  "`group_id` INT NOT NULL ," +
				  "`is_mod` BINARY NULL ," +
				  "PRIMARY KEY (`id`) ," +
				  "UNIQUE INDEX `id_UNIQUE` (`id` ASC) ," +
				  "UNIQUE INDEX `belong_once` (`user_id` ASC, `group_id` ASC) );"
				  );

		print("Creating membership view");
		sqlExecute("DROP VIEW IF EXISTS `membership_info`;");
		sqlExecute("CREATE VIEW `membership_info` AS " +
				  "SELECT " +
				  "memberships.id as 'membership_id', " +
				  "memberships.group_id, " +
				  "groups.name as group_name, " +
				  "memberships.user_id, " +
				  "users.name as user_name, " +
				  "memberships.is_mod " +
				  "FROM `memberships`  " +
				  "JOIN `users` ON memberships.user_id=users.id " + 
				  "JOIN groups ON memberships.group_id=groups.id " +
				  "ORDER BY group_id, user_id;"
				  );
	}
	
	/**
	 * Populate database with a single user and group
	 */
	public void populateDatabase() {
			
		/** Add data for a user, group and membership **/
		print("Adding user");
		sqlExecute("INSERT INTO `users` (`name`, `password`) VALUES ('mike', 'pw');");
		print("Adding group");
		sqlExecute("INSERT INTO `groups` (`name`, `course`) VALUES ('group1', 'cse110');");
		print("Adding membership");
		sqlExecute("INSERT INTO `memberships` (`user_id`, `group_id`, `is_mod`) VALUES ('1', '1', TRUE);");
	}	
	
	/**
	 * Takes a SQL query string, executes it, and displays results in console 
	 * @param sqlCommand - a {@link String} properly formatted SQL query
	 */
	public void displaySqlResults(String sqlCommand){
        ResultSet res = sqlQuery(sqlCommand);
		try {
			displayResultSet(res);
	        res.getStatement().close();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Displays a results set in the console.
	 * @param rs - a {@link ResultsSet} from a SQL query
	 * @throws SQLException
	 * @author <a href="http://www.freeopenbook.com/mysqlcookbook/mysqlckbk-CHP-9-SECT-5.html">http://www.freeopenbook.com/mysqlcookbook/mysqlckbk-CHP-9-SECT-5.html</a>
	 */
	public static void displayResultSet (ResultSet rs) throws SQLException
	{
	    java.sql.ResultSetMetaData md = rs.getMetaData();
	    int ncols = md.getColumnCount ( );
	    int nrows = 0;
	    int[ ] width = new int[ncols + 1];       // array to store column widths
	    StringBuffer b = new StringBuffer ( );   // buffer to hold bar line

	    // calculate column widths
	    for (int i = 1; i <= ncols; i++)
	    {
	        // some drivers return -1 for getColumnDisplaySize( );
	        // if so, we'll override that with the column name length
	        width[i] = md.getColumnDisplaySize (i);
	        if (width[i] < md.getColumnName (i).length ( ))
	            width[i] = md.getColumnName (i).length ( );
	        // isNullable( ) returns 1/0, not true/false
	        if (width[i] < 4 && md.isNullable (i) != 0)
	            width[i] = 4;
	    }

	    // construct +---+---... line
	    b.append ("+");
	    for (int i = 1; i <= ncols; i++)
	    {
	        for (int j = 0; j < width[i]; j++)
	            b.append ("-");
	        b.append ("+");
	    }

	    // print bar line, column headers, bar line
	    System.out.println (b.toString ( ));
	    System.out.print ("|");
	    for (int i = 1; i <= ncols; i++)
	    {
	        System.out.print (md.getColumnName (i));
	        for (int j = md.getColumnName (i).length ( ); j < width[i]; j++)
	            System.out.print (" ");
	        System.out.print ("|");
	    }
	    System.out.println ( );
	    System.out.println (b.toString ( ));

	    // print contents of result set
	    while (rs.next ( ))
	    {
	        ++nrows;
	        System.out.print ("|");
	        for (int i = 1; i <= ncols; i++)
	        {
	            String s = rs.getString (i);
	            if (rs.wasNull ( ))
	                s = "NULL";
	            System.out.print (s);
	            for (int j = s.length ( ); j < width[i]; j++)
	                System.out.print (" ");
	            System.out.print ("|");
	        }
	        System.out.println ( );
	    }
	    // print bar line, and row count
	    System.out.println (b.toString ( ));
	    if (nrows == 1)
	        System.out.println ("1 row selected");
	    else
	        System.out.println (nrows + " rows selected");
	}	
	
}
