import javax.swing.JPanel;

import database.Database;
import database.MySqlDatabase;
import domainlogic.StudyGroupSystem;
import gui.GUIFrame;

/** The main holding class for StudyGroupFinder. This initializes an instance of the program. */
public class Start extends JPanel {	
	
	/** The {@link Database} to use for the program. */
	static Database db = null;

	/** The {@link StudyGroupSystem} to use for the program. */
	static StudyGroupSystem system = new StudyGroupSystem();
	
	/** The {@link GUIFrame} to use for the program. */
	final static GUIFrame gui = new GUIFrame(system);
	
	/* Empty constructor, setup is done by setDatabase */
	Start() {}
	
	private static void setDatabase(String databaseMode) {
		
		/* local */
		if (databaseMode.equalsIgnoreCase("local")) {				
			System.out.print("Attempting to run on local MySql...");
			db = new MySqlDatabase("jdbc:mysql://localhost:3306/testdb", "root", "");
			System.out.println("Connected");

		/* remote-new */
		} else if (databaseMode.equalsIgnoreCase("remote-new")) {
			System.out.print("Attempting to create new remote instance of MySql...");
			db = new MySqlDatabase("jdbc:mysql://afiend.selfip.net:3306/study_prod", "studydb", "studydb");
			System.out.println("Connected...");
			((MySqlDatabase) db).buildDatabase();
			
		/* remote (any other, default) */
		} else { // includes "" or "remote" 
			System.out.print("Attempting to run on remote instance of MySql...");
			db = new MySqlDatabase("jdbc:mysql://afiend.selfip.net:3306/study_prod", "studydb", "studydb");
			System.out.println("Connected");
		}
		
		/* Having determined the database, set it in the StudyGroupSystem instance */
		system.setDatabase(db);
	}
	
	/** Called by main. Calls all of the logic of the program */
	private static void createAndShowGUI() {
		
        //gui.setContentPane(new Start()); 
        
		// Call the GUIFrame and make it run the program
        gui.runProgram();
        
        //Display the window.
        gui.pack();
        gui.setVisible(true);
	}
	
	/** The main method for the Study Group Finder. Calls the needed methods to run the program.
	 * 
	 * @param args - first command line option specifies database and can be:<ul>
	 * <li> <code>remote</code> = use remote server (default)
	 * <li> <code>remote-new</code> = user remote server but delete/reinitialize database 
	 * <li> <code>local</code> = use local instance of MySql (local server must be running) 
	 * </ul>
	 */
	public static void main(String[] args) {

		/* Determine database mode from first argument */
		String dbMode = "";
		if (args.length > 0) dbMode = args[0];
		setDatabase(dbMode);

		/* Run GUI */
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

	}
	
}
